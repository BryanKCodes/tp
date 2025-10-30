package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.csv.CsvImporter;
import seedu.address.logic.csv.exceptions.InvalidCsvException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Imports player data from a CSV file into the application's player database.
 * <p>
 * Supported headers include:
 * <ul>
 *     <li>{@code Name,Role,Rank,Champion}</li>
 *     <li>{@code Name,Role,Rank,Champion,Wins,Losses}</li>
 * </ul>
 * Each imported player is added to the model unless a duplicate already exists.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports players from a CSV.\n"
            + "Parameters: import players from FILEPATH\n"
            + "Example: import players from data/new_players.csv";

    private final Path path;

    /**
     * Constructs an {@code ImportCommand} with the specified file path.
     *
     * @param path the path to the CSV file to import; must not be {@code null}
     */
    public ImportCommand(Path path) {
        this.path = requireNonNull(path);
    }

    /**
     * Executes the import command by reading player data from the specified CSV file
     * and inserting valid entries into the model.
     * <p>
     * Provides a summary indicating how many players were imported, skipped as duplicates,
     * or rejected due to invalid data.
     *
     * @param model the model in which players are stored
     * @return a {@link CommandResult} containing the import summary message
     * @throws CommandException if the file is missing, invalid, or cannot be parsed
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            CsvImporter.Result result = CsvImporter.importPlayers(model, path);
            return new CommandResult(buildSuccessMessage(result));
        } catch (NoSuchFileException e) {
            throw new CommandException("Failed to import: file not found at " + path);
        } catch (InvalidCsvException | ParseException e) {
            throw new CommandException(e.getMessage());
        } catch (Exception e) {
            throw new CommandException("Failed to import: " + e.getMessage());
        }
    }

    private String buildSuccessMessage(CsvImporter.Result result) {
        StringBuilder msg = new StringBuilder(
                String.format("Imported %d player(s), skipped %d duplicate(s), %d invalid row(s).",
                        result.imported, result.duplicates, result.invalid));

        if (result.invalid > 0 && !result.sampleErrors.isEmpty()) {
            msg.append("\nExamples of invalid rows:\n  - ")
                    .append(String.join("\n  - ", result.sampleErrors));

            if (result.invalid > result.sampleErrors.size()) {
                msg.append("\n  ... (showing ")
                        .append(result.sampleErrors.size())
                        .append(" of ")
                        .append(result.invalid)
                        .append(" errors)");
            }
        }
        return msg.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ImportCommand)) {
            return false;
        }
        ImportCommand o = (ImportCommand) other;
        return path.equals(o.path);
    }
}


