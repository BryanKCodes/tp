package seedu.address.logic.commands;

import java.util.Objects;
import java.util.Optional;

import seedu.address.model.team.Team;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    private final boolean showTeamStats;
    private final Team teamToShow; // nullable

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, boolean exit) {
        this(feedbackToUser, showHelp, exit, false, null);
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, false, false, null);
    }

    /**
     * Full constructor used internally.
     *
     * @param feedbackToUser message for the result display
     * @param showHelp whether to show help window
     * @param exit whether to exit the app
     * @param showTeamStats whether to show team stats window
     * @param teamToShow team entity to display (nullable)
     */
    public CommandResult(String feedbackToUser,
                         boolean showHelp,
                         boolean exit,
                         boolean showTeamStats,
                         Team teamToShow) {
        this.feedbackToUser = Objects.requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.exit = exit;
        this.showTeamStats = showTeamStats;
        this.teamToShow = teamToShow;
    }

    /**
     * Factory method to create a result that opens the Team stats window.
     *
     * @param message feedback line for the result display
     * @param team team to show
     * @return a {@code CommandResult} configured to show the Team stats window
     */
    public static CommandResult showTeamStats(String message, Team team) {
        return new CommandResult(message, false, false, true, team);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public boolean isShowTeamStats() {
        return showTeamStats;
    }

    public Optional<Team> getTeamToShow() {
        return Optional.ofNullable(teamToShow);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit
                && showTeamStats == otherCommandResult.showTeamStats
                && Objects.equals(teamToShow, otherCommandResult.teamToShow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit, showTeamStats, teamToShow);
    }

    @Override
    public String toString() {
        return CommandResult.class.getCanonicalName()
                + "{feedbackToUser=" + feedbackToUser
                + ", showHelp=" + showHelp
                + ", exit=" + exit + "}";
    }
}
