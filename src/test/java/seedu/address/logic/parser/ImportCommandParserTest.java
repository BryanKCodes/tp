package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ImportCommandParserTest {

    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_valid_success() throws Exception {
        ImportCommand cmd = parser.parse("persons from data/persons.csv");
        assertEquals(new ImportCommand(Paths.get("data/persons.csv")), cmd);
    }

    @Test
    public void parse_validCaseInsensitive_success() throws Exception {
        ImportCommand cmd = parser.parse("PERSONS FROM data/persons.csv");
        assertEquals(new ImportCommand(Paths.get("data/persons.csv")), cmd);
    }

    @Test
    public void parse_invalid_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("persons data/persons.csv"));
    }
}

