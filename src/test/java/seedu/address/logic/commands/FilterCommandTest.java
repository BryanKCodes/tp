package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.FILTER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FILTER_AMY_AND_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHAMPION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RANK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.FilterPersonDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullDescriptor_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FilterCommand(null));
    }

    @Test
    public void equals() {
        final FilterCommand standardCommand = new FilterCommand(FILTER_AMY);

        // same values -> returns true
        FilterPersonDescriptor copyDescriptor = new FilterPersonDescriptor(FILTER_AMY);
        FilterCommand commandWithSameValues = new FilterCommand(copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        FilterPersonDescriptor anotherDescriptor = new FilterPersonDescriptorBuilder(FILTER_AMY)
                .withRanks("silver", "gold").build();
        // different ranks -> returns false
        assertFalse(standardCommand.equals(new FilterCommand(anotherDescriptor)));

        anotherDescriptor = new FilterPersonDescriptorBuilder(FILTER_AMY)
                .withRoles("mid", "top").build();
        // different roles -> returns false
        assertFalse(standardCommand.equals(new FilterCommand(anotherDescriptor)));

        anotherDescriptor = new FilterPersonDescriptorBuilder(FILTER_AMY)
                .withChampions("xayah", "rakan").build();
        // different champions -> returns false
        assertFalse(standardCommand.equals(new FilterCommand(anotherDescriptor)));
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() throws CommandException {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        FilterCommand command = new FilterCommand(FILTER_AMY_AND_BOB);
        command.execute(expectedModel);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON), model.getFilteredPersonList());
    }

    @Test
    public void descriptor_isAnyFieldFiltered_correctlyDetects() {
        FilterPersonDescriptor empty = new FilterPersonDescriptorBuilder().build();
        assertFalse(empty.isAnyFieldFiltered());

        FilterPersonDescriptor filled = new FilterPersonDescriptorBuilder()
                .withChampions(VALID_CHAMPION_AMY).build();
        assertTrue(filled.isAnyFieldFiltered());

        filled = new FilterPersonDescriptorBuilder()
                .withRanks(VALID_RANK_AMY).build();
        assertTrue(filled.isAnyFieldFiltered());

        filled = new FilterPersonDescriptorBuilder()
                .withRoles(VALID_ROLE_AMY).build();
        assertTrue(filled.isAnyFieldFiltered());
    }

    @Test
    public void toStringMethod() {
        FilterPersonDescriptor filterPersonDescriptor = new FilterPersonDescriptor();
        FilterCommand filterCommand = new FilterCommand(filterPersonDescriptor);
        String expected = FilterCommand.class.getCanonicalName() + "{filterPersonDescriptor="
                + filterPersonDescriptor + "}";
        assertEquals(expected, filterCommand.toString());
    }
}
