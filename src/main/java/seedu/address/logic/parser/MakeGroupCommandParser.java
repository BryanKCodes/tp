package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.MakeGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parses input arguments and creates a new MakeGroupCommand object
 */
public class MakeGroupCommandParser implements Parser<MakeGroupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of MakeGroupCommand
     * and returns a MakeGroupCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public MakeGroupCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        // Ensure exactly 5 names are provided
        List<String> nameStrings = argMultimap.getAllValues(PREFIX_NAME);
        if (nameStrings.size() != 5) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MakeGroupCommand.MESSAGE_USAGE));
        }

        // Convert each string to a Name object
        List<Name> playerNames = new ArrayList<>();
        for (String nameStr : nameStrings) {
            playerNames.add(ParserUtil.parseName(nameStr));
        }

        return new MakeGroupCommand(playerNames);
    }
}
