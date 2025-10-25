package seedu.address.logic.csv;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import seedu.address.logic.csv.exceptions.InvalidCsvException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;


/**
 * CSV importer for players. Supports multiple headers:
 * <ul>
 *   <li>{@code Name,Role,Rank,Champion}</li>
 *   <li>{@code Name,Role,Rank,Champion,Wins,Losses}</li>
 *   <li>{@code Name,Role,Rank,Champion,Wins,Losses,AvgGrade} (AvgGrade ignored)</li>
 *   <li>{@code Name,Role,Rank,Champion,Wins,Losses,WinRate%,AvgGrade} (WinRate% &amp; AvgGrade ignored)</li>
 * </ul>
 * <p>Win/Loss values are accepted but not injected into {@code Person} pre-merge; they are ignored safely.</p>
 */
public final class CsvImporter {

    /**
     * Result summary for an import operation.
     */
    public static final class Result {
        public final int imported;
        public final int duplicates;
        public final int invalid;

        /**
         * Constructs a result with counts.
         *
         * @param imported   number of rows successfully imported
         * @param duplicates number of rows skipped as duplicates
         * @param invalid    number of rows that failed validation/parsing
         */
        Result(int imported, int duplicates, int invalid) {
            this.imported = imported;
            this.duplicates = duplicates;
            this.invalid = invalid;
        }
    }

    private CsvImporter() {
        // utility class
    }

    /**
     * Imports players from the given CSV file path into the model.
     *
     * @param model the model to mutate
     * @param path  path to CSV file
     * @return a {@link Result} summary of the import
     * @throws IOException         when IO fails (e.g., file missing)
     * @throws InvalidCsvException when header/format is invalid
     * @throws ParseException      when values fail domain validation
     */
    public static Result importPlayers(Model model, Path path)
            throws IOException, InvalidCsvException, ParseException {
        requireNonNull(model);
        if (!Files.exists(path)) {
            throw new NoSuchFileException(path.toString());
        }

        int imported = 0;
        int duplicates = 0;
        int invalid = 0;

        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String header = br.readLine();
            if (header == null) {
                throw new InvalidCsvException("Empty CSV");
            }

            HeaderType type = HeaderType.from(header);
            if (type == HeaderType.UNKNOWN) {
                throw new InvalidCsvException("Unexpected header: " + header);
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                List<String> cols = parseCsvLine(line);

                try {
                    PlayerRow row = PlayerRow.parse(cols, type);
                    Person candidate = new Person(
                            new Name(row.name),
                            new Role(row.role),
                            new Rank(row.rank),
                            new Champion(row.champion),
                            Collections.<Tag>emptySet()
                    );

                    if (model.hasPerson(candidate)) {
                        duplicates++;
                    } else {
                        model.addPerson(candidate);
                        imported++;
                    }
                } catch (Exception ex) {
                    invalid++;
                }
            }
        }

        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new Result(imported, duplicates, invalid);
    }

    /**
     * Supported CSV header formats.
     */
    private enum HeaderType {
        BASIC,
        EXTENDED_WL, // 6 columns
        EXTENDED_WL_AVG, // 7 columns (ignores AvgGrade)
        EXTENDED_WL_WR_AVG, // 8 columns (ignores WinRate% and AvgGrade)
        UNKNOWN;

        static HeaderType from(String header) {
            String h = header == null ? "" : header.trim().toLowerCase();
            // Handle optional BOM (Byte Order Mark)
            if (!h.isEmpty() && h.charAt(0) == '\uFEFF') {
                h = h.substring(1);
            }
            if (h.equals("name,role,rank,champion")) {
                return BASIC;
            }
            if (h.equals("name,role,rank,champion,wins,losses")) {
                return EXTENDED_WL;
            }
            if (h.equals("name,role,rank,champion,wins,losses,avggrade")) {
                return EXTENDED_WL_AVG;
            }
            if (h.equals("name,role,rank,champion,wins,losses,winrate%,avggrade")) {
                return EXTENDED_WL_WR_AVG;
            }
            return UNKNOWN;
        }
    }

    private static final class PlayerRow {
        final String name;
        final String role;
        final String rank;
        final String champion;
        final int wins;
        final int losses;

        private PlayerRow(String name, String role, String rank, String champion, int wins, int losses) {
            this.name = name;
            this.role = role;
            this.rank = rank;
            this.champion = champion;
            this.wins = wins;
            this.losses = losses;
        }

        static PlayerRow parse(List<String> cols, HeaderType type) {
            switch (type) {
            case BASIC:
                if (cols.size() < 4) {
                    throw new IllegalArgumentException("bad cols");
                }
                return new PlayerRow(
                        cols.get(0).trim(),
                        cols.get(1).trim(),
                        cols.get(2).trim(),
                        cols.get(3).trim(),
                        0,
                        0
                );

            case EXTENDED_WL:
            case EXTENDED_WL_AVG:
            case EXTENDED_WL_WR_AVG:
                if (cols.size() < 6) {
                    throw new IllegalArgumentException("bad cols");
                }
                int wins = tryParseInt(cols.get(4).trim(), 0);
                int losses = tryParseInt(cols.get(5).trim(), 0);
                // If more columns exist (WinRate%, AvgGrade), they are intentionally ignored.
                return new PlayerRow(
                        cols.get(0).trim(),
                        cols.get(1).trim(),
                        cols.get(2).trim(),
                        cols.get(3).trim(),
                        wins,
                        losses
                );

            default:
                throw new IllegalArgumentException("unknown header type");
            }
        }

        private static int tryParseInt(String s, int def) {
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                return def;
            }
        }
    }

    /**
     * Minimal RFC4180 tokenizer supporting quotes and escaped quotes.
     *
     * @param line a single CSV line
     * @return a list of column values for the line
     */
    static List<String> parseCsvLine(String line) {
        ArrayList<String> out = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    sb.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                out.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        out.add(sb.toString());
        return out;
    }
}


