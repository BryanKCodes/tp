package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static java.util.Objects.requireNonNull;

public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports players from a CSV.\n"
            + "Parameters: import players from FILEPATH\n"
            + "Example: import players from data/new_players.csv";

    private final Path path;

    public ImportCommand(Path path) {
        this.path = requireNonNull(path);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            CsvImporter.Result summary = CsvImporter.importPlayers(model, path);
            String msg = String.format("Imported %d players, skipped %d duplicates, %d invalid row(s).",
                    summary.imported, summary.duplicates, summary.invalid);
            return new CommandResult(msg);
        } catch (NoSuchFileException e) {
            throw new CommandException("Failed to import: file not found.");
        } catch (InvalidCsvException e) {
            throw new CommandException("Invalid file format. Expected header: Name,Role,Rank,Champion.");
        } catch (IOException e) {
            throw new CommandException("Failed to import: " + e.getMessage());
        }
    }
}

