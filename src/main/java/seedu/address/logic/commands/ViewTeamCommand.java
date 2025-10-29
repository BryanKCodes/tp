package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.team.Team;

/**
 * Displays the details of a specific {@code Team} in a popup window.
 * <p>Uses the provided list index to compute a friendly label
 * such as "Team 1", which is shown instead of the internal team ID.</p>
 */
public class ViewTeamCommand extends Command {
    public static final String COMMAND_WORD = "viewteam";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows team details in a popup window.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Showing %1$s";

    private final Index index;

    public ViewTeamCommand(Index index) {
        requireNonNull(index, "Index cannot be null");
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model, "Model cannot be null");
        List<Team> teams = model.getFilteredTeamList();
        if (index.getZeroBased() >= teams.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }
        Team team = teams.get(index.getZeroBased());
        assert team != null : "Team at valid index should not be null";

        String label = "Team " + index.getOneBased(); // user-facing team number
        return CommandResult.showTeamStats(String.format(MESSAGE_SUCCESS, label), team);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ViewTeamCommand)) {
            return false;
        }

        ViewTeamCommand otherCommand = (ViewTeamCommand) other;
        return index.equals(otherCommand.index);
    }

    @Override
    public int hashCode() {
        return index.hashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .toString();
    }
}

