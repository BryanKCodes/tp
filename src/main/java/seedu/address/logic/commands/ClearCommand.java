package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Clears all players and teams from SummonersBook. This action is irreversible.\n"
            + "Parameters: None\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "SummonersBook has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof ClearCommand;
    }
}
