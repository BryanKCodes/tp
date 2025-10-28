package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.ExportCommand.Target;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExportCommandParserTest {

    private final ExportCommandParser parser = new ExportCommandParser();

    @Test
    public void parse_personsNoPath_success() throws Exception {
        ExportCommand cmd = parser.parse("persons");
        // equals() implemented on ExportCommand -> content equality works
        assertEquals(new ExportCommand(Target.PERSONS, null), cmd);
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("invalid"));
    }

    @Test
    public void parse_personsUpperCase_success() throws Exception {
        ExportCommand cmd = parser.parse("PERSONS");
        assertEquals(new ExportCommand(Target.PERSONS, null), cmd);
    }

    @Test
    public void parse_invalidExtraToken_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("persons somethingElse"));
    }

    @Test
    public void parse_teamsWithPath_success() throws Exception {
        ExportCommand cmd = parser.parse("teams to/data/t.csv");
        assertEquals(new ExportCommand(Target.TEAMS, Paths.get("data/t.csv")), cmd);
    }

}

