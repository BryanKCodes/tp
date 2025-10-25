package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.teammatcher.InsufficientPlayersException;
import seedu.address.logic.teammatcher.TeamMatcher;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * Automatically creates balanced teams from unassigned players.
 * Uses a role-based matching algorithm that considers player ranks and champions.
 */
public class GroupCommand extends Command {

    public static final String COMMAND_WORD = "group";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Automatically creates balanced teams from all unassigned players.\n"
            + "The algorithm groups players by role, sorts by rank, and ensures no duplicate champions per team.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Successfully created %1$d team(s):\n%2$s\n\n"
            + "%3$d player(s) remain unassigned.";
    public static final String MESSAGE_NO_TEAMS_FORMED = "No teams could be formed. "
            + "Ensure there is at least one unassigned player for each role (Top, Jungle, Mid, ADC, Support).";

    private final TeamMatcher teamMatcher;

    /**
     * Creates a GroupCommand with the default TeamMatcher.
     */
    public GroupCommand() {
        this(new TeamMatcher());
    }

    /**
     * Creates a GroupCommand with a specified TeamMatcher.
     * This constructor supports Dependency Inversion Principle by allowing injection of the matching strategy.
     *
     * @param teamMatcher The team matcher to use for forming teams.
     */
    public GroupCommand(TeamMatcher teamMatcher) {
        requireNonNull(teamMatcher);
        this.teamMatcher = teamMatcher;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get all unassigned persons
        List<Person> unassignedPersons = model.getUnassignedPersonList();
        int initialUnassignedCount = unassignedPersons.size();

        if (unassignedPersons.isEmpty()) {
            throw new CommandException("No unassigned players available to form teams.");
        }

        // Use TeamMatcher to form teams
        List<Team> teams;
        try {
            teams = teamMatcher.matchTeams(unassignedPersons);
        } catch (InsufficientPlayersException e) {
            throw new CommandException(e.getMessage());
        }

        if (teams.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TEAMS_FORMED);
        }

        // Add all teams to the model
        for (Team team : teams) {
            model.addTeam(team);
        }

        // Calculate remaining unassigned players
        int playersUsed = teams.size() * Team.TEAM_SIZE;
        assert playersUsed <= initialUnassignedCount : "Matcher should not use more players than available";
        int remainingPlayers = initialUnassignedCount - playersUsed;

        // Format the success message
        String teamsFormatted = formatTeams(teams);
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                teams.size(), teamsFormatted, remainingPlayers));
    }

    /**
     * Formats the list of teams for display.
     * Each team is displayed on a separate line with its number and members.
     */
    private String formatTeams(List<Team> teams) {
        assert !teams.isEmpty() : "formatTeams should only be called with non-empty teams list";
        return IntStream.range(0, teams.size())
                .mapToObj(i -> String.format("Team %d: %s", i + 1, teams.get(i).toDisplayString()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupCommand)) {
            return false;
        }

        GroupCommand otherCommand = (GroupCommand) other;
        return teamMatcher.equals(otherCommand.teamMatcher);
    }

    @Override
    public int hashCode() {
        return teamMatcher.hashCode();
    }
}
