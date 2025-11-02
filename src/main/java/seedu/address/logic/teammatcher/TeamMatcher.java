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
import seedu.address.model.team.exceptions.DuplicateChampionException;
import seedu.address.model.team.exceptions.MissingRolesException;

/**
 * A class responsible for matching unassigned persons into balanced teams.
 * Uses a role-based matching algorithm that considers person ranks and champions.
 *
 * This class follows the Single Responsibility Principle by focusing solely on team matching logic.
 */
public class TeamMatcher {

    private static final int TEAM_SIZE = 5;
    private static final List<Role> REQUIRED_ROLES = List.of(
        new Role("Top"),
        new Role("Jungle"),
        new Role("Mid"),
        new Role("Adc"),
        new Role("Support")
    );

    /**
     * Attempts to create balanced teams from a list of unassigned persons.
     *
     * Algorithm:
     * 1. Groups persons by role
     * 2. Sorts each role group by rank (highest to lowest)
     * 3. Iteratively forms teams by selecting one person per role
     * 4. Ensures no duplicate champions within each team
     *
     * @param unassignedPersons List of persons not currently in any team.
     * @return List of teams that can be formed.
     * @throws MissingRolesException if there are not enough persons to form at least one complete team.
     * @throws DuplicateChampionException if teams cannot be formed due to duplicate champion conflicts.
     */
    public List<Team> matchTeams(List<Person> unassignedPersons)
            throws MissingRolesException, DuplicateChampionException {
        // Group by role
        Map<Role, List<Person>> personsByRole = groupByRole(unassignedPersons);

        // Validate we have at least one person per role
        validateMinimumPersons(personsByRole);

        // Create defensive copies and sort each role group by rank
        Map<Role, List<Person>> sortedPersonsByRole = createSortedCopy(personsByRole);

        // Validate at least one team can be formed
        validateNoInitialConflict(sortedPersonsByRole);

        // Form teams
        return formTeams(sortedPersonsByRole);
    }

    /**
     * Groups persons by their role.
     */
    Map<Role, List<Person>> groupByRole(List<Person> persons) {
        return persons.stream()
                .collect(Collectors.groupingBy(Person::getRole));
    }

    /**
     * Validates that there is at least one person for each required role.
     *
     * @throws MissingRolesException if any role is missing.
     */
    private void validateMinimumPersons(Map<Role, List<Person>> personsByRole)
            throws MissingRolesException {
        List<Role> missingRoles = REQUIRED_ROLES.stream()
                .filter(role -> !personsByRole.containsKey(role) || personsByRole.get(role).isEmpty())
                .collect(Collectors.toList());

        if (!missingRoles.isEmpty()) {
            throw new MissingRolesException(missingRoles);
        }
    }

    /**
     * Validates that at least one conflict-free team can be formed.
     *
     * @throws DuplicateChampionException if it's impossible to form even a single team.
     */
    private void validateNoInitialConflict(Map<Role, List<Person>> personsByRole) throws DuplicateChampionException {
        if (trySelectTeamMembers(personsByRole).isEmpty()) {
            Person[] conflict = findConflictingPersons(personsByRole);
            throw new DuplicateChampionException(conflict[0], conflict[1]);
        }
    }

    /**
     * Creates a sorted defensive copy of the persons by role map.
     * Each role's person list is sorted by rank (highest to lowest).
     * Uses Rank's natural ordering (Comparable) in reverse.
     *
     * @param personsByRole Original map of persons grouped by role (not modified).
     * @return New map with sorted copies of person lists.
     */
    Map<Role, List<Person>> createSortedCopy(Map<Role, List<Person>> personsByRole) {
        Comparator<Person> rankComparator = Comparator.comparing(Person::getRank).reversed();
        Map<Role, List<Person>> sortedCopy = new HashMap<>();

        for (Map.Entry<Role, List<Person>> entry : personsByRole.entrySet()) {
            List<Person> sortedPersons = new ArrayList<>(entry.getValue());
            sortedPersons.sort(rankComparator);
            sortedCopy.put(entry.getKey(), sortedPersons);
        }

        return sortedCopy;
    }

    /**
     * Forms as many complete teams as possible from the sorted role groups.
     * Ensures no duplicate champions within each team.
     * Modifies the input map by removing persons as they are assigned to teams.
     *
     * @param personsByRole Map of roles to lists of persons (will be modified).
     * @return List of teams formed from the persons.
     */
    private List<Team> formTeams(Map<Role, List<Person>> personsByRole) {
        List<Team> teams = new ArrayList<>();

        while (canFormTeam(personsByRole)) {
            Optional<List<Person>> teamMembers = trySelectTeamMembers(personsByRole);

            if (teamMembers.isEmpty()) {
                break;
            }

            List<Person> members = teamMembers.get();
            teams.add(new Team(members));

            // Remove selected persons from their role pools
            for (Person member : members) {
                personsByRole.get(member.getRole()).remove(member);
            }
        }

        return teams;
    }

    /**
     * Attempts to select a full team of 5 without champion conflicts.
     */
    private Optional<List<Person>> trySelectTeamMembers(Map<Role, List<Person>> personsByRole) {
        List<Person> teamMembers = new ArrayList<>();
        for (Role role : REQUIRED_ROLES) {
            Optional<Person> selectedPerson = selectPersonWithoutChampionConflict(
                    personsByRole.get(role), teamMembers);

            if (selectedPerson.isEmpty()) {
                return Optional.empty();
            }
            teamMembers.add(selectedPerson.get());
        }
        assert teamMembers.size() == TEAM_SIZE : "Team should have exactly " + TEAM_SIZE + " members";
        return Optional.of(teamMembers);
    }

    /**
     * Finds the two persons causing the first unavoidable champion conflict.
     * Simulates the team selection process to pinpoint why team formation failed.
     * This should only be called after {@code trySelectTeamMembers} returns empty.
     *
     * @param personsByRole Map of available persons, sorted by rank for each role.
     * @return An array containing the two persons with the conflicting champion.
     */
    Person[] findConflictingPersons(Map<Role, List<Person>> personsByRole) {
        List<Person> selectedSoFar = new ArrayList<>();

        for (Role role : REQUIRED_ROLES) {
            List<Person> candidatesForRole = personsByRole.get(role);
            Optional<Person> selectedPerson = selectPersonWithoutChampionConflict(candidatesForRole, selectedSoFar);

            if (selectedPerson.isPresent()) {
                selectedSoFar.add(selectedPerson.get());
            } else {
                // Failure point: No candidate for this role can be selected.
                Person highestRankedCandidate = candidatesForRole.get(0);
                for (Person alreadySelected : selectedSoFar) {
                    if (highestRankedCandidate.getChampion().equals(alreadySelected.getChampion())) {
                        return new Person[]{highestRankedCandidate, alreadySelected};
                    }
                }
                throw new IllegalStateException("Conflict detected but players could not be identified.");
            }
        }
        throw new IllegalStateException("A conflict was expected but not found.");
    }

    /**
     * Checks if we can potentially form at least one more complete team.
     */
    private boolean canFormTeam(Map<Role, List<Person>> personsByRole) {
        for (Role role : REQUIRED_ROLES) {
            if (!personsByRole.containsKey(role) || personsByRole.get(role).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Selects a person from the available list who doesn't have a champion conflict
     * with already selected team members.
     *
     * @param availablePersons List of persons to choose from.
     * @param selectedMembers Already selected team members.
     * @return The selected person, or Optional.empty() if no valid person can be found.
     */
    private Optional<Person> selectPersonWithoutChampionConflict(List<Person> availablePersons,
                                                                  List<Person> selectedMembers) {
        for (Person candidate : availablePersons) {
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
