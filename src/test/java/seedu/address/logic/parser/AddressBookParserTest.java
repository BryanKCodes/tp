package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TEAM;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GroupCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListTeamCommand;
import seedu.address.logic.commands.LoseCommand;
import seedu.address.logic.commands.MakeGroupCommand;
import seedu.address.logic.commands.WinCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.FilterPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.PersonUtil;

public class AddressBookParserTest {

    private final AddressBookParser parser = new AddressBookParser();

    @Test
    public void parseCommand_add() throws Exception {
        Person person = new PersonBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PersonUtil.getAddCommand(person));
        assertEquals(new AddCommand(person), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Person person = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(person).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PersonUtil.getEditPersonDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PERSON, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        FilterPersonDescriptor descriptor = new FilterPersonDescriptorBuilder()
                .withChampions("annie", "leblanc").build();
        assertTrue(parser.parseCommand(FilterCommand.COMMAND_WORD + " rk/gold") instanceof FilterCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_listteam_returnsListTeamCommand() throws Exception {
        assertTrue(parser.parseCommand(ListTeamCommand.COMMAND_WORD) instanceof ListTeamCommand);
        assertTrue(parser.parseCommand(ListTeamCommand.COMMAND_WORD + " 3") instanceof ListTeamCommand);
    }

    @Test
    public void parseCommand_makeGroupValid_success() throws Exception {
        String input = "makegroup n/Alice n/Bob n/Cathy n/Derek n/Ella";
        List<Name> expectedNames = Arrays.asList(
                new Name("Alice"), new Name("Bob"), new Name("Cathy"), new Name("Derek"), new Name("Ella")
        );
        MakeGroupCommand expectedCommand = new MakeGroupCommand(expectedNames);

        assertEquals(expectedCommand, parser.parseCommand(input));
    }

    @Test
    public void parseCommand_makeGroupInvalidNumber_throwsParseException() {
        String input = "makegroup n/Alice n/Bob"; // fewer than 5 names
        assertThrows(ParseException.class,
                String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT,
                        MakeGroupCommand.MESSAGE_USAGE), () -> parser.parseCommand(input));
    }

    @Test
    public void parseCommand_winCommand() throws Exception {
        WinCommand command = (WinCommand) parser.parseCommand(
                WinCommand.COMMAND_WORD + " " + INDEX_FIRST_TEAM.getOneBased());
        assertEquals(new WinCommand(INDEX_FIRST_TEAM), command);
    }

    @Test
    public void parseCommand_loseCommand() throws Exception {
        LoseCommand command = (LoseCommand) parser.parseCommand(
                LoseCommand.COMMAND_WORD + " " + INDEX_FIRST_TEAM.getOneBased());
        assertEquals(new LoseCommand(INDEX_FIRST_TEAM), command);
    }

    @Test
    public void parseCommand_group_returnsGroupCommand() throws Exception {
        assertTrue(parser.parseCommand(GroupCommand.COMMAND_WORD) instanceof GroupCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
                -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
