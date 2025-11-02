package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void equals() {
        HelpCommand helpCommand = new HelpCommand();

        // null -> returns false
        assertFalse(helpCommand.equals(null));

        // same object -> returns true
        assertTrue(helpCommand.equals(helpCommand));

        // same type -> returns true
        HelpCommand otherHelpCommand = new HelpCommand();
        assertTrue(helpCommand.equals(otherHelpCommand));

        // different types -> returns false
        assertFalse(helpCommand.equals(1));
    }
}
