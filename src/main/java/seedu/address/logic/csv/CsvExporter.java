package seedu.address.logic.csv;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Utility class that exports {@link Person} and {@link Team} data from the model
 * into CSV files following RFC4180 minimal formatting.
 * <p>
 * Supports exporting both player and team datasets with consistent columns.
 * The exported files can later be re-imported using {@code CsvImporter}.
 */
public final class CsvExporter {
    private CsvExporter() {
    }

    /**
     * Exports all players currently stored in the model to a CSV file at the specified path.
     * <p>
     * The exported file includes the following columns:
     * <pre>
     * Name,Role,Rank,Champion,Wins,Losses,WinRate%,AvgGrade
     * </pre>
     * Average grade values are extracted reflectively if {@code Person#getStats()} exists;
     * otherwise, they are shown as {@code "-"}.
     *
     * @param model the {@link Model} containing players to export
     * @param out   the output file path; parent directories are created if missing
     * @throws IOException if an I/O error occurs during writing
     */
    public static void exportPlayers(Model model, Path out) throws IOException {
        requireNonNull(model);
        List<String> lines = new ArrayList<>();
        lines.add("Name,Role,Rank,Champion,Wins,Losses,WinRate%,AvgGrade");

        for (Person p : model.getAddressBook().getPersonList()) {
            int wins = WlReflect.wins(p);
            int losses = WlReflect.losses(p);
            int matches = wins + losses;
            String wr = matches == 0 ? "0.0" : String.format(java.util.Locale.US, "%.1f", (wins * 100.0) / matches);
            String avg = String.valueOf(p.getStats().getValue());
            lines.add(String.join(",",
                    csv(p.getName().toString()),
                    csv(p.getRole().toString()),
                    csv(p.getRank().toString()),
                    csv(p.getChampion().toString()),
                    Integer.toString(wins),
                    Integer.toString(losses),
                    wr,
                    avg
            ));
        }
        write(out, lines);
    }

    /**
     * Attempts to obtain the average grade string from the given person reflectively.
     * <p>
     * If {@code Person#getStats()} or {@code Stats#getValue()} is unavailable
     * (e.g., stats not merged yet), returns {@code "-"} as a fallback.
     *
     * @param person a {@link Person} object (treated reflectively)
     * @return the formatted average grade string, or {@code "-"} if unavailable
     */
    private static String tryGetAvgGrade(Object person) {
        try {
            Object stats = person.getClass().getMethod("getStats").invoke(person);
            if (stats == null) {
                return "-";
            }
            Object v = stats.getClass().getMethod("getValue").invoke(stats);
            return String.valueOf(v);
        } catch (ReflectiveOperationException e) {
            return "-"; // Stats not merged yet or method absent
        }
    }

    /**
     * Exports all teams currently stored in the model to a CSV file at the specified path.
     * <p>
     * The exported file includes the following columns:
     * <pre>
     * TeamId,Top,Jungle,Mid,Adc,Support,Wins,Losses,WinRate%
     * </pre>
     * Missing role assignments for teams are written as empty strings.
     *
     * @param model the {@link Model} containing teams to export
     * @param out   the output file path; parent directories are created if missing
     * @throws IOException if an I/O error occurs during writing
     */
    public static void exportTeams(Model model, Path out) throws IOException {
        requireNonNull(model);
        List<String> lines = new ArrayList<>();
        lines.add("TeamId,Top,Jungle,Mid,Adc,Support,Wins,Losses,WinRate%");

        for (Team t : model.getAddressBook().getTeamList()) {
            Map<String, String> roleToName = t.getPersons().stream()
                    .collect(Collectors.toMap(p -> p.getRole().toString(), p -> p.getName().toString()));

            int wins = WlReflect.wins(t);
            int losses = WlReflect.losses(t);
            int matches = wins + losses;
            String wr = matches == 0 ? "0.0" : String.format(java.util.Locale.US, "%.1f", (wins * 100.0) / matches);

            lines.add(String.join(",",
                    csv(t.getId()),
                    csv(roleToName.getOrDefault("Top", "")),
                    csv(roleToName.getOrDefault("Jungle", "")),
                    csv(roleToName.getOrDefault("Mid", "")),
                    csv(roleToName.getOrDefault("Adc", "")),
                    csv(roleToName.getOrDefault("Support", "")),
                    Integer.toString(wins),
                    Integer.toString(losses),
                    wr
            ));
        }
        write(out, lines);
    }

    /**
     * Writes the given lines to the specified file path in UTF-8 encoding.
     * Creates parent directories if necessary and truncates existing files.
     *
     * @param out   output path
     * @param lines lines of text to write
     * @throws IOException if writing fails
     */
    private static void write(Path out, List<String> lines) throws IOException {
        if (out.getParent() != null) {
            Files.createDirectories(out.getParent());
        }
        Files.write(out, lines, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Escapes a CSV value according to RFC4180 minimal rules.
     * <p>
     * Values containing commas, quotes, newlines, or leading/trailing spaces
     * are wrapped in double quotes, and internal quotes are doubled.
     *
     * @param v raw string value
     * @return escaped CSV-safe value
     */
    private static String csv(String v) {
        if (v == null) {
            return "";
        }
        boolean needsQuote = v.contains(",") || v.contains("\"") || v.contains("\n") || v.startsWith(" ")
                || v.endsWith(" ");
        String escaped = v.replace("\"", "\"\"");
        return needsQuote ? "\"" + escaped + "\"" : escaped;
    }
}

