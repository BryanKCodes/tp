package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.MakeGroupCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class MakeGroupCommandParserTest {

    private final MakeGroupCommandParser parser = new MakeGroupCommandParser();

    @Test
    public void parse_validInput_success() throws ParseException {
        String input = " n/Alice n/Bob n/Cathy n/Derek n/Ella";

        List<Name> expectedNames = Arrays.asList(
                new Name("Alice"),
                new Name("Bob"),
                new Name("Cathy"),
                new Name("Derek"),
                new Name("Ella")
        );

        MakeGroupCommand expectedCommand = new MakeGroupCommand(expectedNames);

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_invalidNumberOfNames_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MakeGroupCommand.MESSAGE_USAGE);

        // fewer than 5 names
        String input1 = " n/Alice n/Bob n/Cathy n/Derek";
        assertParseFailure(parser, input1, expectedMessage);

        // more than 5 names
        String input2 = " n/Alice n/Bob n/Cathy n/Derek n/Ella n/Fiona";
        assertParseFailure(parser, input2, expectedMessage);
    }

    @Test
    public void parse_missingPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MakeGroupCommand.MESSAGE_USAGE);

        // missing n/ for one name
        String input = " n/Alice Bob n/Cathy n/Derek n/Ella";
        assertParseFailure(parser, input, expectedMessage);
    }
}
