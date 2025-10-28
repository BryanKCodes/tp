package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.csv.CsvExporter;
import seedu.address.model.Model;


/**
 * Exports person or team data from the application into a CSV file.
 * <p>
 * Supports two export targets:
 * <ul>
 *     <li>Persons — exported to {@code data/persons.csv} by default.</li>
 *     <li>Teams — exported to {@code data/teams.csv} by default.</li>
 * </ul>
 * Users can optionally specify a custom file path using the {@code to/} prefix.
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports persons or teams to CSV.\n"
            + "Parameters: export [persons|teams] [to/FILEPATH]\n"
            + "Examples:\n"
            + "  export persons\n"
            + "  export teams to/data/teams.csv";

    /**
     * Represents the exportable data categories within the application.
     */
    public enum Target { PERSONS, TEAMS }

    private final Target target;
    private final Path outPathOrNull; // null -> default path

    /**
     * Constructs an {@code ExportCommand} with the specified target and output path.
     *
     * @param target         the export target (either {@code PERSONS} or {@code TEAMS})
     * @param outPathOrNull  optional file path; if {@code null}, a default path is used
     */
    public ExportCommand(Target target, Path outPathOrNull) {
        this.target = requireNonNull(target);
        this.outPathOrNull = outPathOrNull; // may be null
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Path out = outPathOrNull != null
                ? outPathOrNull
                : (target == Target.PERSONS ? Paths.get("data/persons.csv") : Paths.get("data/teams.csv"));
        try {
            if (target == Target.PERSONS) {
                CsvExporter.exportPersons(model, out); // see util below
                return new CommandResult("Exported person data to " + out + ".");
            } else {
                CsvExporter.exportTeams(model, out);
                return new CommandResult("Exported team data to " + out + ".");
            }
        } catch (Exception e) {
            throw new CommandException("Failed to export: " + e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ExportCommand)) {
            return false;
        }
        ExportCommand o = (ExportCommand) other;
        return target == o.target
                && ((outPathOrNull == null && o.outPathOrNull == null)
                || (outPathOrNull != null && outPathOrNull.equals(o.outPathOrNull)));
    }
}


