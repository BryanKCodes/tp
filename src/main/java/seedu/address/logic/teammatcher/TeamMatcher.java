package seedu.address.logic.teammatcher;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.team.Team;

/**
 * A class responsible for matching unassigned persons into balanced teams.
 * Uses a role-based matching algorithm that considers player ranks and champions.
 *
 * This class follows the Single Responsibility Principle by focusing solely on team matching logic.
 */
public class TeamMatcher {

    private static final int TEAM_SIZE = 5;
    private static final Role[] REQUIRED_ROLES = {
        new Role("Top"),
        new Role("Jungle"),
        new Role("Mid"),
        new Role("Adc"),
        new Role("Support")
    };

    /**
     * Attempts to create balanced teams from a list of unassigned persons.
     *
     * Algorithm:
     * 1. Groups persons by role
     * 2. Sorts each role group by rank (highest to lowest)
     * 3. Iteratively forms teams by selecting one player per role
     * 4. Ensures no duplicate champions within each team
     *
     * @param unassignedPersons List of persons not currently in any team.
     * @return List of teams that can be formed.
     * @throws InsufficientPlayersException if there are not enough players to form at least one complete team.
     */
    public List<Team> matchTeams(List<Person> unassignedPersons) throws InsufficientPlayersException {
        // Group by role
        Map<Role, List<Person>> playersByRole = groupByRole(unassignedPersons);

        // Validate we have at least one player per role
        validateMinimumPlayers(playersByRole);

        // Create defensive copies and sort each role group by rank
        Map<Role, List<Person>> sortedPlayersByRole = createSortedCopy(playersByRole);

        // Form teams
        return formTeams(sortedPlayersByRole);
    }

    /**
     * Groups persons by their role.
     */
    private Map<Role, List<Person>> groupByRole(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getRole));
    }

    /**
     * Validates that there is at least one person for each required role.
     *
     * @throws InsufficientPersonsException if any role is missing.
     */
    private void validateMinimumPlayers(Map<Role, List<Person>> playersByRole)
            throws InsufficientPlayersException {
        for (Role role : REQUIRED_ROLES) {
            if (!playersByRole.containsKey(role) || playersByRole.get(role).isEmpty()) {
                throw new InsufficientPlayersException(
                        "Cannot form a team: No players available for role " + role + ".");
            }
        }
    }

    /**
     * Creates a sorted defensive copy of the players by role map.
     * Each role's player list is sorted by rank (highest to lowest).
     * Uses Rank's natural ordering (Comparable) in reverse.
     *
     * @param playersByRole Original map of players grouped by role (not modified).
     * @return New map with sorted copies of player lists.
     */
    private Map<Role, List<Person>> createSortedCopy(Map<Role, List<Person>> playersByRole) {
        Comparator<Person> rankComparator = Comparator.comparing(Person::getRank).reversed();
        Map<Role, List<Person>> sortedCopy = new HashMap<>();

        for (Map.Entry<Role, List<Person>> entry : playersByRole.entrySet()) {
            List<Person> sortedPlayers = new ArrayList<>(entry.getValue());
            sortedPlayers.sort(rankComparator);
            sortedCopy.put(entry.getKey(), sortedPlayers);
        }

        return sortedCopy;
    }

    /**
     * Forms as many complete teams as possible from the sorted role groups.
     * Ensures no duplicate champions within each team.
     * Modifies the input map by removing players as they are assigned to teams.
     *
     * @param playersByRole Map of roles to lists of players (will be modified).
     * @return List of teams formed from the players.
     */
    private List<Team> formTeams(Map<Role, List<Person>> playersByRole) {
        List<Team> teams = new ArrayList<>();

        // Keep forming teams until we can't form any more complete teams
        while (canFormTeam(playersByRole)) {
            List<Person> teamMembers = new ArrayList<>();

            // Try to select one player from each role
            for (Role role : REQUIRED_ROLES) {
                List<Person> availablePlayers = playersByRole.get(role);
                Optional<Person> selectedPlayer = selectPlayerWithoutChampionConflict(availablePlayers, teamMembers);

                if (selectedPlayer.isEmpty()) {
                    // Can't form a complete team without champion conflicts
                    // Put back the players we've selected and stop
                    return teams;
                }

                teamMembers.add(selectedPlayer.get());
                availablePlayers.remove(selectedPlayer.get());
            }

            // Successfully formed a team
            assert teamMembers.size() == TEAM_SIZE
                : "Team should have exactly " + TEAM_SIZE + " members before creation";
            teams.add(new Team(teamMembers));
        }

        return teams;
    }

    /**
     * Checks if we can potentially form at least one more complete team.
     */
    private boolean canFormTeam(Map<Role, List<Person>> playersByRole) {
        for (Role role : REQUIRED_ROLES) {
            if (!playersByRole.containsKey(role) || playersByRole.get(role).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Selects a player from the available list who doesn't have a champion conflict
     * with already selected team members.
     *
     * @param availablePlayers List of players to choose from.
     * @param selectedMembers Already selected team members.
     * @return The selected player, or Optional.empty() if no valid player can be found.
     */
    private Optional<Person> selectPlayerWithoutChampionConflict(List<Person> availablePlayers,
                                                                  List<Person> selectedMembers) {
        for (Person candidate : availablePlayers) {
            if (!hasChampionConflict(candidate, selectedMembers)) {
                return Optional.of(candidate);
            }
        }
        return Optional.empty();
    }

    /**
     * Checks if a candidate has a champion conflict with any of the selected members.
     */
    private boolean hasChampionConflict(Person candidate, List<Person> selectedMembers) {
        return selectedMembers.stream()
                .anyMatch(member -> candidate.getChampion().equals(member.getChampion()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        // All TeamMatcher instances are equal since they have no state
        return other instanceof TeamMatcher;
    }

    @Override
    public int hashCode() {
        // All instances return the same hash code since they're stateless
        return TeamMatcher.class.hashCode();
    }
}
