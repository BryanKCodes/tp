package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Role("mid"), new Rank("gold"), new Champion("Ahri"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Role("support"), new Rank("platinum"), new Champion("Lulu"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Role("adc"), new Rank("gold"), new Champion("Jinx"),
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Role("jungle"), new Rank("diamond"), new Champion("Lee Sin"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Role("top"), new Rank("silver"),
                    new Champion("Garen"),
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Role("mid"), new Rank("bronze"),
                    new Champion("Zed"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        Person[] samplePersons = getSamplePersons();

        for (Person samplePerson : samplePersons) {
            sampleAb.addPerson(samplePerson);
        }

        List<Person> teamRoster = Arrays.asList(Arrays.copyOfRange(samplePersons, 0, 5));
        Team sampleTeam = new Team(teamRoster);
        sampleAb.addTeam(sampleTeam);

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
