package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports players or teams to CSV.\n"
            + "Parameters: export [players|teams] [to/FILEPATH]\n"
            + "Examples:\n"
            + "  export players\n"
            + "  export teams to/data/teams.csv";

    private final Target target; // enum { PLAYERS, TEAMS }
    private final Path path;     // may be null -> use default

    public ExportCommand(Target target, Path path) {
        this.target = requireNonNull(target);
        this.path = path;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Path out = (path != null)
                ? path
                : (target == Target.PLAYERS ? Paths.get("data/players.csv") : Paths.get("data/teams.csv"));
        try {
            if (target == Target.PLAYERS) {
                CsvExporter.exportPlayers(model.getAddressBook(), out); // or model.getPersonList()
                return new CommandResult("Exported player data to " + out + ".");
            } else {
                CsvExporter.exportTeams(model.getAddressBook(), out);
                return new CommandResult("Exported team data to " + out + ".");
            }
        } catch (IOException e) {
            throw new CommandException("Failed to export: " + e.getMessage());
        }
    }

    public enum Target {PLAYERS, TEAMS}
}

