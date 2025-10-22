package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.person.Person;
import seedu.address.model.team.Team;

/**
 * A utility class to help with building Team objects.
 */
public class TeamBuilder {

    private List<Person> persons;

    /**
     * Creates a {@code TeamBuilder} with the default details.
     */
    public TeamBuilder() {
        persons = new ArrayList<>();
    }

    /**
     * Initializes the TeamBuilder with the data of {@code teamToCopy}.
     */
    public TeamBuilder(Team teamToCopy) {
        persons = new ArrayList<>(teamToCopy.getPersons());
    }

    /**
     * Sets the {@code persons} of the {@code Team} that we are building.
     */
    public TeamBuilder withPersons(Person... persons) {
        this.persons = Arrays.asList(persons);
        return this;
    }

    public Team build() {
        return new Team(persons);
    }
}
