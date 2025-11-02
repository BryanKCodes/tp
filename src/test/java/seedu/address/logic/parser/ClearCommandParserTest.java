package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ClearCommand;

public class ClearCommandParserTest {

    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parse_validArgs_returnsClearCommand() {
        // The parser should succeed when the argument string is empty.
        assertParseSuccess(parser, "", new ClearCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE);

        // Test with a non-empty argument
        assertParseFailure(parser, "1", expectedErrorMessage);
    }
}
