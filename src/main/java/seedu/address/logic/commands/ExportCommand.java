package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.csv.CsvExporter;
import seedu.address.model.Model;


/**
 * Exports player or team data from the application into a CSV file.
 * <p>
 * This command enables users to save data from the in-memory model into
 * a structured {@code .csv} file, which can be imported later or shared externally.
 * <br>
 * Supported export targets:
 * <ul>
 *     <li><b>Players</b> — exported to {@code data/players.csv} by default</li>
 *     <li><b>Teams</b> — exported to {@code data/teams.csv} by default</li>
 * </ul>
 * Users can optionally specify a custom file path using the {@code to} keyword.
 * </p>
 *
 * <p><b>Example usages:</b></p>
 * <pre>
 *     export players
 *     export teams to data/my_teams.csv
 * </pre>
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports players or teams to CSV.\n"
            + "Parameters: export [players|teams] [to CUSTOM_PATH]\n"
            + "Examples: export players | export teams to data/my_teams.csv";

    private static final Path DEFAULT_PLAYERS_PATH = Paths.get("data/players.csv");
    private static final Path DEFAULT_TEAMS_PATH = Paths.get("data/teams.csv");

    /**
     * Represents which type of data the export operation targets — players or teams.
     */
    public enum Target { PLAYERS, TEAMS }

    private final Target target;
    private final Path customPath;

    /**
     * Constructs an {@code ExportCommand} specifying which data type to export
     * and an optional custom output file path.
     *
     * @param target the data type to export (players or teams); must not be {@code null}.
     * @param customPath an optional file path to export to; if {@code null}, a default is used.
     */
    public ExportCommand(Target target, Path customPath) {
        this.target = requireNonNull(target);
        this.customPath = customPath;
    }

    /**
     * Executes the export operation based on the command target.
     * <p>
     * The command resolves the correct output path (either default or user-specified),
     * and invokes the corresponding export method from {@link CsvExporter}.
     * </p>
     *
     * @param model the {@link Model} that holds the current application state and data.
     * @return a {@link CommandResult} containing a success message.
     * @throws CommandException if the export fails due to file I/O or model issues.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Path outputPath = resolveOutputPath();

        try {
            if (target == Target.PLAYERS) {
                CsvExporter.exportPlayers(model, outputPath);
            } else {
                CsvExporter.exportTeams(model, outputPath);
            }
            return new CommandResult(buildSuccessMessage(outputPath));
        } catch (Exception e) {
            throw new CommandException("Failed to export: " + e.getMessage());
        }
    }

    /**
     * Determines the output file path for the export operation.
     * If the user has specified a path, it is used; otherwise, the default
     * path corresponding to the target type is returned.
     *
     * @return the {@link Path} to which data should be exported.
     */
    private Path resolveOutputPath() {
        if (customPath != null) {
            return customPath;
        }
        return target == Target.PLAYERS ? DEFAULT_PLAYERS_PATH : DEFAULT_TEAMS_PATH;
    }

    /**
     * Builds a user-friendly success message after an export completes.
     *
     * @param path the output file path used in the export.
     * @return a formatted success message for display to the user.
     */
    private String buildSuccessMessage(Path path) {
        String dataType = target == Target.PLAYERS ? "player" : "team";
        return String.format("Exported %s data to %s.", dataType, path);
    }

    /**
     * Checks whether another object is equal to this {@code ExportCommand}.
     * Two export commands are considered equal if they have the same target
     * and custom path.
     *
     * @param other the object to compare with.
     * @return {@code true} if both commands are equal, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExportCommand)) {
            return false;
        }
        ExportCommand that = (ExportCommand) other;
        return target == that.target
                && java.util.Objects.equals(customPath, that.customPath);
    }

    /**
     * Returns the hash code of this command, consistent with {@link #equals(Object)}.
     *
     * @return a hash value based on the command’s target and custom path.
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(target, customPath);
    }
}



