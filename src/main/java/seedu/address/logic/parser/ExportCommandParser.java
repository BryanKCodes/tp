package seedu.address.logic.parser;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input into an {@link ExportCommand}.
 * <p>
 * Recognises the following valid patterns:
 * <ul>
 *   <li>{@code export players}</li>
 *   <li>{@code export teams}</li>
 *   <li>{@code export players to data/players.csv}</li>
 *   <li>{@code export teams to data/teams.csv}</li>
 * </ul>
 * If no {@code to/FILEPATH} is provided, a default path is used.
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    private static final String PLAYERS_KEYWORD = "players";
    private static final String TEAMS_KEYWORD = "teams";
    private static final String TO_KEYWORD = "to";

    /**
     * Parses the given user input and constructs an {@link ExportCommand}.
     *
     * @param args full user input string (excluding the command word)
     * @return an {@link ExportCommand} configured with target type and optional output path
     * @throws ParseException if the input does not conform to the expected format
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {
        String trimmed = args.trim().toLowerCase();

        ExportCommand.Target target;
        String remainder;

        if (trimmed.startsWith(PLAYERS_KEYWORD)) {
            target = ExportCommand.Target.PLAYERS;
            remainder = trimmed.substring(PLAYERS_KEYWORD.length()).trim();
        } else if (trimmed.startsWith(TEAMS_KEYWORD)) {
            target = ExportCommand.Target.TEAMS;
            remainder = trimmed.substring(TEAMS_KEYWORD.length()).trim();
        } else {
            throw new ParseException(ExportCommand.MESSAGE_USAGE);
        }

        Path customPath = null;
        if (!remainder.isEmpty()) {
            if (!remainder.startsWith(TO_KEYWORD + " ")) {
                throw new ParseException("Expected 'to' before file path.\n"
                        + ExportCommand.MESSAGE_USAGE);
            }

            String pathString = remainder.substring(TO_KEYWORD.length()).trim();
            if (pathString.isEmpty()) {
                throw new ParseException("File path cannot be empty after 'to'.");
            }
            if (!pathString.endsWith(".csv")) {
                throw new ParseException("File path must end with .csv");
            }
            customPath = Paths.get(pathString);
        }
        return new ExportCommand(target, customPath);
    }
}

