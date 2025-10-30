package seedu.address.logic.parser;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input into an {@link ImportCommand}.
 * <p>
 * Recognises commands of the form:
 * <ul>
 *   <li>{@code import players from data/players.csv}</li>
 * </ul>
 * Extracts the file path after the keyword {@code from} and constructs
 * an {@link ImportCommand} with that path.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    private static final String PLAYERS_KEYWORD = "players";
    private static final String FROM_KEYWORD = "from";

    /**
     * Parses the given user input and returns an {@link ImportCommand}.
     * <p>
     * Expected format: {@code players from FILEPATH}.
     *
     * @param args full user input string (excluding the command word)
     * @return an {@link ImportCommand} that imports players from the specified file path
     * @throws ParseException if the input does not match the required format
     */
    @Override
    public ImportCommand parse(String args) throws ParseException {
        String trimmed = args.trim();

        if (!trimmed.toLowerCase().startsWith(PLAYERS_KEYWORD)) {
            throw new ParseException(ImportCommand.MESSAGE_USAGE);
        }

        String afterPlayers = trimmed.substring(PLAYERS_KEYWORD.length()).trim();

        if (!afterPlayers.toLowerCase().startsWith(FROM_KEYWORD)) {
            throw new ParseException(ImportCommand.MESSAGE_USAGE);
        }

        String pathString = afterPlayers.substring(FROM_KEYWORD.length()).trim();

        if (pathString.isEmpty()) {
            throw new ParseException("File path cannot be empty.\n" + ImportCommand.MESSAGE_USAGE);
        }

        try {
            Path path = Paths.get(pathString);
            return new ImportCommand(path);
        } catch (Exception e) {
            throw new ParseException("Invalid file path: " + pathString);
        }
    }
}

