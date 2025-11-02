package seedu.address.logic.teammatcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.team.Team;
import seedu.address.model.team.exceptions.DuplicateChampionException;
import seedu.address.model.team.exceptions.MissingRolesException;
import seedu.address.testutil.PersonBuilder;

public class TeamMatcherTest {

    private final TeamMatcher teamMatcher = new TeamMatcher();

    @Test
    public void matchTeams_validPersons_formsOneTeam() throws Exception {
        // Create 5 persons with different roles
        Person top = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Person> persons = Arrays.asList(top, jungle, mid, adc, support);
        List<Team> teams = teamMatcher.matchTeams(persons);

        assertEquals(1, teams.size());
        assertEquals(5, teams.get(0).getPersons().size());
    }

    @Test
    public void matchTeams_tenPersons_formsTwoTeams() throws Exception {
        // Create 10 persons - 2 per role
        Person top1 = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Challenger").withChampion("Garen").build();
        Person top2 = new PersonBuilder().withName("Top2").withRole("top")
                .withRank("Gold").withChampion("Darius").build();

        Person jungle1 = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Master").withChampion("Lee Sin").build();
        Person jungle2 = new PersonBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Jarvan IV").build();

        Person mid1 = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Diamond").withChampion("Ahri").build();
        Person mid2 = new PersonBuilder().withName("Mid2").withRole("mid")
                .withRank("Bronze").withChampion("Zed").build();

        Person adc1 = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Platinum").withChampion("Jinx").build();
        Person adc2 = new PersonBuilder().withName("Adc2").withRole("adc")
                .withRank("Iron").withChampion("Ashe").build();

        Person support1 = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Emerald").withChampion("Leona").build();
        Person support2 = new PersonBuilder().withName("Support2").withRole("support")
                .withRank("Gold").withChampion("Thresh").build();

        List<Person> persons = Arrays.asList(
                top1, top2, jungle1, jungle2, mid1, mid2, adc1, adc2, support1, support2
        );

        List<Team> teams = teamMatcher.matchTeams(persons);

        assertEquals(2, teams.size());

        // First team should have higher ranked persons
        Team team1 = teams.get(0);
        assertTrue(team1.getPersons().contains(top1)); // Challenger
        assertTrue(team1.getPersons().contains(jungle1)); // Master
        assertTrue(team1.getPersons().contains(mid1)); // Diamond
    }

    @Test
    public void matchTeams_duplicateChampion_avoidsConflict() throws Exception {
        // Create scenario where top2 has same champion as top1
        // TeamMatcher should pick top1 for first team and top2 for second team
        Person top1 = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person top2 = new PersonBuilder().withName("Top2").withRole("top")
                .withRank("Silver").withChampion("Garen").build();

        Person jungle1 = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person jungle2 = new PersonBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Jarvan IV").build();

        Person mid1 = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person mid2 = new PersonBuilder().withName("Mid2").withRole("mid")
                .withRank("Silver").withChampion("Zed").build();

        Person adc1 = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person adc2 = new PersonBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();

        Person support1 = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();
        Person support2 = new PersonBuilder().withName("Support2").withRole("support")
                .withRank("Silver").withChampion("Thresh").build();

        List<Person> persons = Arrays.asList(
                top1, top2, jungle1, jungle2, mid1, mid2, adc1, adc2, support1, support2
        );

        List<Team> teams = teamMatcher.matchTeams(persons);

        // Should form 2 teams successfully
        assertEquals(2, teams.size());

        // Verify no duplicate champions in each team
        for (Team team : teams) {
            List<Person> members = team.getPersons();
            long uniqueChampions = members.stream()
                    .map(Person::getChampion)
                    .distinct()
                    .count();
            assertEquals(5, uniqueChampions);
        }
    }

    @Test
    public void matchTeams_missingRole_throwsMissingRolesException() {
        // Create persons but missing support role
        Person top = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();

        List<Person> persons = Arrays.asList(top, jungle, mid, adc);

        MissingRolesException exception = assertThrows(
                MissingRolesException.class, () -> teamMatcher.matchTeams(persons));

        assertTrue(exception.getMessage().contains("Support"));
    }

    @Test
    public void matchTeams_emptyList_throwsMissingRolesException() {
        List<Person> persons = Arrays.asList();

        assertThrows(
                MissingRolesException.class, () -> teamMatcher.matchTeams(persons));
    }

    @Test
    public void matchTeams_allPersonsSameRole_throwsMissingRolesException() {
        // All 5 persons are top laners - impossible to form a team
        Person top1 = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person top2 = new PersonBuilder().withName("Top2").withRole("top")
                .withRank("Gold").withChampion("Darius").build();
        Person top3 = new PersonBuilder().withName("Top3").withRole("top")
                .withRank("Gold").withChampion("Sett").build();
        Person top4 = new PersonBuilder().withName("Top4").withRole("top")
                .withRank("Gold").withChampion("Gwen").build();
        Person top5 = new PersonBuilder().withName("Top5").withRole("top")
                .withRank("Gold").withChampion("Jax").build();

        List<Person> persons = Arrays.asList(top1, top2, top3, top4, top5);

        MissingRolesException exception = assertThrows(
                MissingRolesException.class, () -> teamMatcher.matchTeams(persons));

        // Should complain about missing Jungle (or any other missing role)
        assertTrue(exception.getMessage().contains("No persons available for role"));
    }

    @Test
    public void matchTeams_sevenPersons_formsOneTeamLeavesTwo() throws Exception {
        // 7 persons total - should form 1 complete team, leaving 2 unmatched
        Person top1 = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Challenger").withChampion("Garen").build();
        Person top2 = new PersonBuilder().withName("Top2").withRole("top")
                .withRank("Gold").withChampion("Darius").build();

        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();

        Person adc1 = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person adc2 = new PersonBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();

        Person support = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Person> persons = Arrays.asList(top1, top2, jungle, mid, adc1, adc2, support);
        List<Team> teams = teamMatcher.matchTeams(persons);

        // Should form exactly 1 team
        assertEquals(1, teams.size());

        // Team should contain highest-ranked top (top1 Challenger, not top2 Gold)
        Team team = teams.get(0);
        assertTrue(team.getPersons().contains(top1));
        // Team should contain highest-ranked adc (adc1 Gold, not adc2 Silver)
        assertTrue(team.getPersons().contains(adc1));
    }

    @Test
    public void matchTeams_fifteenPersons_formsThreeTeams() throws Exception {
        // 15 persons (3 per role) - should form 3 complete teams
        List<Person> persons = new ArrayList<>();

        // Create 3 persons per role with different ranks
        String[] roles = {"top", "jungle", "mid", "adc", "support"};
        String[] champions = {
            "Garen,Darius,Sett",
            "Lee Sin,Jarvan IV,Elise",
            "Ahri,Zed,Yasuo",
            "Jinx,Ashe,Caitlyn",
            "Leona,Thresh,Lulu"
        };
        String[] ranks = {"Challenger", "Gold", "Bronze"};

        for (int roleIndex = 0; roleIndex < roles.length; roleIndex++) {
            String[] champs = champions[roleIndex].split(",");
            for (int i = 0; i < 3; i++) {
                Person person = new PersonBuilder()
                        .withName(roles[roleIndex] + (i + 1))
                        .withRole(roles[roleIndex])
                        .withRank(ranks[i])
                        .withChampion(champs[i])
                        .build();
                persons.add(person);
            }
        }

        List<Team> teams = teamMatcher.matchTeams(persons);

        // Should form exactly 3 teams
        assertEquals(3, teams.size());
    }

    @Test
    public void matchTeams_championConflictPreventsSecondTeam_formsOneTeamOnly() throws Exception {
        // Create 10 persons where all persons in each role use the same champion
        // This should only allow forming 1 team
        Person top1 = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person top2 = new PersonBuilder().withName("Top2").withRole("top")
                .withRank("Silver").withChampion("Garen").build(); // Same champion

        Person jungle1 = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person jungle2 = new PersonBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Garen").build(); // Conflict with top2!

        Person mid1 = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person mid2 = new PersonBuilder().withName("Mid2").withRole("mid")
                .withRank("Silver").withChampion("Zed").build();

        Person adc1 = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person adc2 = new PersonBuilder().withName("Adc2").withRole("adc")
                .withRank("Silver").withChampion("Ashe").build();

        Person support1 = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();
        Person support2 = new PersonBuilder().withName("Support2").withRole("support")
                .withRank("Silver").withChampion("Thresh").build();

        List<Person> persons = Arrays.asList(
                top1, top2, jungle1, jungle2, mid1, mid2, adc1, adc2, support1, support2
        );

        List<Team> teams = teamMatcher.matchTeams(persons);

        // Should only form 1 team due to champion conflicts
        assertEquals(1, teams.size());
    }

    @Test
    public void matchTeams_exactlyFivePersons_formsOneTeam() throws Exception {
        // Test covers the case where we have exactly 5 persons (one per role)
        Person top = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Person> persons = Arrays.asList(top, jungle, mid, adc, support);
        List<Team> teams = teamMatcher.matchTeams(persons);

        // Should form exactly 1 team
        assertEquals(1, teams.size());
        assertEquals(5, teams.get(0).getPersons().size());
    }

    @Test
    public void matchTeams_roleKeyMissingInMap_throwsMissingRolesException() {
        // Create persons with only 4 out of 5 required roles (no Top role at all)
        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Lee Sin").build();
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Person> persons = Arrays.asList(jungle, mid, adc, support);

        MissingRolesException exception = assertThrows(
                MissingRolesException.class, () -> teamMatcher.matchTeams(persons));

        // Should complain about missing Top role
        assertTrue(exception.getMessage().contains("Top"));
    }

    @Test
    public void matchTeams_unavoidableInitialConflict_throwsDuplicateChampionException() {
        // Arrange: Create a set of 5 players where the highest-ranked (and only)
        // top and jungle players have the same champion.
        Person top = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungle = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Garen").build(); // Unavoidable conflict
        Person mid = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        List<Person> persons = Arrays.asList(top, jungle, mid, adc, support);

        // Act & Assert
        DuplicateChampionException exception = assertThrows(
                DuplicateChampionException.class, () -> teamMatcher.matchTeams(persons));

        // Optionally, assert that the exception message contains the conflicting champion
        assertTrue(exception.getMessage().contains("Garen"));
    }

    @Test
    public void matchTeams_skipsHigherRankedPlayerToAvoidConflict() throws Exception {
        // Arrange:
        // Team 1 should pick top1 (Garen).
        // For the jungle role, it should skip the higher-ranked jungle1 (Garen)
        // and pick the lower-ranked jungle2 (Lee Sin) to avoid a conflict.
        Person top1 = new PersonBuilder().withName("Top1").withRole("top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungle1 = new PersonBuilder().withName("Jungle1").withRole("jungle")
                .withRank("Gold").withChampion("Garen").build(); // Higher-ranked, but conflicts
        Person jungle2 = new PersonBuilder().withName("Jungle2").withRole("jungle")
                .withRank("Silver").withChampion("Lee Sin").build(); // Lower-ranked, no conflict
        Person mid1 = new PersonBuilder().withName("Mid1").withRole("mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc1 = new PersonBuilder().withName("Adc1").withRole("adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support1 = new PersonBuilder().withName("Support1").withRole("support")
                .withRank("Gold").withChampion("Leona").build();

        // Add enough players for a second team so jungle1 can be placed later
        Person top2 = new PersonBuilder().withName("Top2").withRole("top")
                .withRank("Bronze").withChampion("Darius").build();
        Person mid2 = new PersonBuilder().withName("Mid2").withRole("mid")
                .withRank("Bronze").withChampion("Zed").build();
        Person adc2 = new PersonBuilder().withName("Adc2").withRole("adc")
                .withRank("Bronze").withChampion("Ashe").build();
        Person support2 = new PersonBuilder().withName("Support2").withRole("support")
                .withRank("Bronze").withChampion("Lulu").build();


        List<Person> persons = Arrays.asList(
                top1, top2, jungle1, jungle2, mid1, mid2, adc1, adc2, support1, support2
        );

        List<Team> teams = teamMatcher.matchTeams(persons);

        assertEquals(2, teams.size());

        // The first team should have skipped jungle1 for jungle2
        Team firstTeam = teams.get(0);
        assertTrue(firstTeam.getPersons().contains(jungle2),
                "First team should contain the non-conflicting Silver jungler");
        assertTrue(!firstTeam.getPersons().contains(jungle1),
                "First team should not contain the conflicting Gold jungler");

        // The second team should contain the remaining players, including jungle1
        Team secondTeam = teams.get(1);
        assertTrue(secondTeam.getPersons().contains(jungle1),
                "Second team should contain the leftover Gold jungler");
    }


    @Test
    public void findConflictingPersons_directConflictInSecondRole_returnsCorrectPair() {
        // Arrange
        Person topLaner = new PersonBuilder().withName("Player1").withRole("Top")
                .withRank("Gold").withChampion("Darius").build();
        Person jungler = new PersonBuilder().withName("Player2").withRole("Jungle")
                .withRank("Gold").withChampion("Darius").build();
        Person midLaner = new PersonBuilder().withName("Player3").withRole("Mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Player4").withRole("Adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support = new PersonBuilder().withName("Player5").withRole("Support")
                .withRank("Gold").withChampion("Lulu").build();

        List<Person> persons = List.of(topLaner, jungler, midLaner, adc, support);
        Map<Role, List<Person>> sortedPersonsByRole = teamMatcher
                .createSortedCopy(teamMatcher.groupByRole(persons));

        // Act
        Person[] conflict = teamMatcher.findConflictingPersons(sortedPersonsByRole);

        // Assert: The conflict should be between the jungler and the top laner.
        List<Person> conflictingPair = Arrays.asList(conflict);
        assertEquals(2, conflictingPair.size());
        assertTrue(conflictingPair.contains(jungler));
        assertTrue(conflictingPair.contains(topLaner));
    }

    @Test
    public void findConflictingPersons_conflictInLaterRole_returnsCorrectPair() {
        // Arrange
        Person topLaner = new PersonBuilder().withName("Player1").withRole("Top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungler = new PersonBuilder().withName("Player2").withRole("Jungle")
                .withRank("Gold").withChampion("Vi").build();
        Person midLaner = new PersonBuilder().withName("Player3").withRole("Mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Player4").withRole("Adc")
                .withRank("Gold").withChampion("Garen").build(); // Conflicts with Top
        Person support = new PersonBuilder().withName("Player5").withRole("Support")
                .withRank("Gold").withChampion("Lulu").build();

        List<Person> persons = List.of(topLaner, jungler, midLaner, adc, support);
        Map<Role, List<Person>> sortedPersonsByRole = teamMatcher
                .createSortedCopy(teamMatcher.groupByRole(persons));

        // Act
        Person[] conflict = teamMatcher.findConflictingPersons(sortedPersonsByRole);

        // Assert: The conflict should be between the ADC and the top laner.
        List<Person> conflictingPair = Arrays.asList(conflict);
        assertEquals(2, conflictingPair.size());
        assertTrue(conflictingPair.contains(adc));
        assertTrue(conflictingPair.contains(topLaner));
    }

    @Test
    public void findConflictingPersons_forcedConflictWhenNoAlternative_returnsCorrectPair() {
        // Arrange: This tests the core simulation logic.
        // 1. Top (Malphite) is selected.
        // 2. Jungle has two options: Malphite (Gold) and Vi (Silver). The algorithm must skip the Gold player
        //    and select Vi to avoid conflict.
        // 3. Mid has only one option, Malphite, which now conflicts with the selected Top laner.
        Person topLaner = new PersonBuilder().withName("Player1").withRole("Top")
                .withRank("Gold").withChampion("Malphite").build();
        Person jungler1 = new PersonBuilder().withName("Player2").withRole("Jungle")
                .withRank("Gold").withChampion("Malphite").build();
        Person jungler2 = new PersonBuilder().withName("Player3").withRole("Jungle")
                .withRank("Silver").withChampion("Vi").build();
        Person midLaner = new PersonBuilder().withName("Player4").withRole("Mid")
                .withRank("Gold").withChampion("Malphite").build();
        Person adc = new PersonBuilder().withName("Player5").withRole("Adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support = new PersonBuilder().withName("Player6").withRole("Support")
                .withRank("Gold").withChampion("Lulu").build();

        List<Person> persons = List.of(topLaner, jungler1, jungler2, midLaner, adc, support);
        Map<Role, List<Person>> sortedPersonsByRole = teamMatcher
                .createSortedCopy(teamMatcher.groupByRole(persons));

        Person[] conflict = teamMatcher.findConflictingPersons(sortedPersonsByRole);

        // Assert: The conflict should be between the mid laner (who couldn't be placed) and the top laner.
        List<Person> conflictingPair = Arrays.asList(conflict);
        assertEquals(2, conflictingPair.size());
        assertTrue(conflictingPair.contains(midLaner));
        assertTrue(conflictingPair.contains(topLaner));
    }

    @Test
    public void findConflictingPersons_whenTeamIsFormable_throwsIllegalStateException() {
        // Arrange: A perfectly valid team with no conflicts. The method should not be called in this case.
        Person topLaner = new PersonBuilder().withName("Player1").withRole("Top")
                .withRank("Gold").withChampion("Garen").build();
        Person jungler = new PersonBuilder().withName("Player2").withRole("Jungle")
                .withRank("Gold").withChampion("Vi").build();
        Person midLaner = new PersonBuilder().withName("Player3").withRole("Mid")
                .withRank("Gold").withChampion("Ahri").build();
        Person adc = new PersonBuilder().withName("Player4").withRole("Adc")
                .withRank("Gold").withChampion("Jinx").build();
        Person support = new PersonBuilder().withName("Player5").withRole("Support")
                .withRank("Gold").withChampion("Lulu").build();

        List<Person> persons = List.of(topLaner, jungler, midLaner, adc, support);
        Map<Role, List<Person>> sortedPersonsByRole = teamMatcher
                .createSortedCopy(teamMatcher.groupByRole(persons));

        // The method should throw an exception because its precondition (a conflict exists) is violated.
        assertThrows(IllegalStateException.class, () -> {
            teamMatcher.findConflictingPersons(sortedPersonsByRole);
        });
    }

}
