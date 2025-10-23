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
            new Person(new Name("Alice Pauline"), new Role("Mid"), new Rank("Gold"),
                    new Champion("Ahri"), getTagSet("friends")),
            new Person(new Name("Benson Meier"), new Role("Top"), new Rank("Silver"),
                    new Champion("Garen"), getTagSet("owesMoney", "friends")),
            new Person(new Name("Carl Kurz"), new Role("Jungle"), new Rank("Platinum"),
                    new Champion("Lee sin"), getTagSet()),
            new Person(new Name("Daniel Meier"), new Role("Adc"), new Rank("Gold"),
                    new Champion("Caitlyn"), getTagSet("friends")),
            new Person(new Name("Elle Meyer"), new Role("Support"), new Rank("Diamond"),
                    new Champion("Lulu"), getTagSet()),
            new Person(new Name("Fiona Kunz"), new Role("Mid"), new Rank("Master"),
                    new Champion("Zed"), getTagSet()),
            new Person(new Name("George Best"), new Role("Top"), new Rank("Iron"),
                    new Champion("Darius"), getTagSet()),
            new Person(new Name("Hoon Meier"), new Role("Support"), new Rank("Silver"),
                    new Champion("Leona"), getTagSet()),
            new Person(new Name("Eva Martinez"), new Role("Jungle"), new Rank("Grandmaster"),
                    new Champion("Fiora"), getTagSet()),
            new Person(new Name("Tom Jackson"), new Role("Jungle"), new Rank("Grandmaster"),
                    new Champion("Fiora"), getTagSet()),
            new Person(new Name("Olivia Clark"), new Role("Jungle"), new Rank("Bronze"),
                    new Champion("Cho'gath"), getTagSet()),
            new Person(new Name("Grace Wilson"), new Role("Mid"), new Rank("Bronze"),
                    new Champion("Neeko"), getTagSet()),
            new Person(new Name("Leo Miller"), new Role("Top"), new Rank("Grandmaster"),
                    new Champion("Brand"), getTagSet()),
            new Person(new Name("Tina Thompson"), new Role("Mid"), new Rank("Platinum"),
                    new Champion("Vladimir"), getTagSet()),
            new Person(new Name("Zoe Moore"), new Role("Mid"), new Rank("Silver"),
                    new Champion("Shen"), getTagSet()),
            new Person(new Name("Jack Miller"), new Role("Jungle"), new Rank("Challenger"),
                    new Champion("Leona"), getTagSet()),
            new Person(new Name("Eva Miller"), new Role("Mid"), new Rank("Grandmaster"),
                    new Champion("Braum"), getTagSet()),
            new Person(new Name("Frank Wilson"), new Role("Adc"), new Rank("Master"),
                    new Champion("Gnar"), getTagSet()),
            new Person(new Name("Leo Taylor"), new Role("Mid"), new Rank("Gold"),
                    new Champion("Jarvan iv"), getTagSet()),
            new Person(new Name("Penny Thompson"), new Role("Jungle"), new Rank("Diamond"),
                    new Champion("Yorick"), getTagSet()),
            new Person(new Name("Alice Jackson"), new Role("Adc"), new Rank("Challenger"),
                    new Champion("Renekton"), getTagSet()),
            new Person(new Name("Paul Harris"), new Role("Top"), new Rank("Grandmaster"),
                    new Champion("Neeko"), getTagSet()),
            new Person(new Name("Grace Miller"), new Role("Support"), new Rank("Bronze"),
                    new Champion("Ezreal"), getTagSet()),
            new Person(new Name("Frank Harris"), new Role("Support"), new Rank("Diamond"),
                    new Champion("Yuumi"), getTagSet()),
            new Person(new Name("Penny Martin"), new Role("Adc"), new Rank("Iron"),
                    new Champion("Milio"), getTagSet()),
            new Person(new Name("Olivia Moore"), new Role("Jungle"), new Rank("Iron"),
                    new Champion("Orianna"), getTagSet()),
            new Person(new Name("Sophia Garcia"), new Role("Adc"), new Rank("Grandmaster"),
                    new Champion("Rengar"), getTagSet()),
            new Person(new Name("Lily Harris"), new Role("Mid"), new Rank("Iron"),
                    new Champion("Rengar"), getTagSet()),
            new Person(new Name("Ian Wilson"), new Role("Adc"), new Rank("Gold"),
                    new Champion("Ornn"), getTagSet()),
            new Person(new Name("Alex Smith"), new Role("Mid"), new Rank("Bronze"),
                    new Champion("Graves"), getTagSet()),
            new Person(new Name("Ryan Wilson"), new Role("Jungle"), new Rank("Bronze"),
                    new Champion("Irelia"), getTagSet()),
            new Person(new Name("Sophia Martinez"), new Role("Mid"), new Rank("Master"),
                    new Champion("Kayn"), getTagSet()),
            new Person(new Name("David Taylor"), new Role("Top"), new Rank("Gold"),
                    new Champion("Nilah"), getTagSet()),
            new Person(new Name("Tina White"), new Role("Mid"), new Rank("Master"),
                    new Champion("Malzahar"), getTagSet()),
            new Person(new Name("Ian Moore"), new Role("Support"), new Rank("Silver"),
                    new Champion("Zoe"), getTagSet()),
            new Person(new Name("Ben Martin"), new Role("Jungle"), new Rank("Gold"),
                    new Champion("Mel"), getTagSet()),
            new Person(new Name("Noah Harris"), new Role("Mid"), new Rank("Master"),
                    new Champion("Ryze"), getTagSet()),
            new Person(new Name("Daisy Williams"), new Role("Jungle"), new Rank("Silver"),
                    new Champion("Samira"), getTagSet()),
            new Person(new Name("Penny Anderson"), new Role("Adc"), new Rank("Bronze"),
                    new Champion("Urgot"), getTagSet()),
            new Person(new Name("Ryan Martin"), new Role("Jungle"), new Rank("Silver"),
                    new Champion("Alistar"), getTagSet()),
            new Person(new Name("Max Williams"), new Role("Jungle"), new Rank("Diamond"),
                    new Champion("Taric"), getTagSet()),
            new Person(new Name("Olivia Brown"), new Role("Support"), new Rank("Iron"),
                    new Champion("Braum"), getTagSet()),
            new Person(new Name("Henry Williams"), new Role("Mid"), new Rank("Grandmaster"),
                    new Champion("Kindred"), getTagSet()),
            new Person(new Name("George Martinez"), new Role("Support"), new Rank("Gold"),
                    new Champion("Fiddlesticks"), getTagSet()),
            new Person(new Name("Kevin Thompson"), new Role("Mid"), new Rank("Challenger"),
                    new Champion("Braum"), getTagSet()),
            new Person(new Name("Henry Jackson"), new Role("Support"), new Rank("Silver"),
                    new Champion("Olaf"), getTagSet()),
            new Person(new Name("Mia Jackson"), new Role("Support"), new Rank("Challenger"),
                    new Champion("Zilean"), getTagSet()),
            new Person(new Name("Bella White"), new Role("Mid"), new Rank("Grandmaster"),
                    new Champion("Katarina"), getTagSet()),
            new Person(new Name("Lily Jackson"), new Role("Top"), new Rank("Bronze"),
                    new Champion("Bel'veth"), getTagSet()),
            new Person(new Name("Clara Miller"), new Role("Adc"), new Rank("Master"),
                    new Champion("Aphelios"), getTagSet()),
            new Person(new Name("Quinn Miller"), new Role("Mid"), new Rank("Grandmaster"),
                    new Champion("Akali"), getTagSet()),
            new Person(new Name("George White"), new Role("Top"), new Rank("Platinum"),
                    new Champion("Sivir"), getTagSet()),
            new Person(new Name("Oscar Wilson"), new Role("Top"), new Rank("Challenger"),
                    new Champion("Karma"), getTagSet()),
            new Person(new Name("Olivia Thompson"), new Role("Adc"), new Rank("Master"),
                    new Champion("Master yi"), getTagSet()),
            new Person(new Name("Ethan White"), new Role("Adc"), new Rank("Platinum"),
                    new Champion("Yuumi"), getTagSet()),
            new Person(new Name("Ryan White"), new Role("Adc"), new Rank("Iron"),
                    new Champion("Draven"), getTagSet()),
            new Person(new Name("Sam Martinez"), new Role("Jungle"), new Rank("Grandmaster"),
                    new Champion("Yuumi"), getTagSet()),
            new Person(new Name("Hannah Moore"), new Role("Mid"), new Rank("Silver"),
                    new Champion("Aphelios"), getTagSet()),
            new Person(new Name("Kevin Williams"), new Role("Support"), new Rank("Silver"),
                    new Champion("Veigar"), getTagSet()),
            new Person(new Name("Tom Wilson"), new Role("Mid"), new Rank("Challenger"),
                    new Champion("Sona"), getTagSet()),
            new Person(new Name("Kevin Davis"), new Role("Jungle"), new Rank("Bronze"),
                    new Champion("Kennen"), getTagSet()),
            new Person(new Name("Nora Martin"), new Role("Jungle"), new Rank("Grandmaster"),
                    new Champion("Jax"), getTagSet()),
            new Person(new Name("George Robinson"), new Role("Top"), new Rank("Platinum"),
                    new Champion("Ekko"), getTagSet()),
            new Person(new Name("Zoe Thompson"), new Role("Top"), new Rank("Bronze"),
                    new Champion("Cassiopeia"), getTagSet()),
            new Person(new Name("Fiona Miller"), new Role("Adc"), new Rank("Challenger"),
                    new Champion("Rumble"), getTagSet()),
            new Person(new Name("Kevin Harris"), new Role("Mid"), new Rank("Master"),
                    new Champion("Gangplank"), getTagSet()),
            new Person(new Name("Ivy Davis"), new Role("Jungle"), new Rank("Silver"),
                    new Champion("Sion"), getTagSet()),
            new Person(new Name("Zoe Anderson"), new Role("Top"), new Rank("Silver"),
                    new Champion("Vex"), getTagSet()),
            new Person(new Name("Grace Martinez"), new Role("Mid"), new Rank("Master"),
                    new Champion("Vel'koz"), getTagSet()),
            new Person(new Name("Tom Martinez"), new Role("Top"), new Rank("Bronze"),
                    new Champion("Sivir"), getTagSet()),
            new Person(new Name("Noah Davis"), new Role("Adc"), new Rank("Platinum"),
                    new Champion("Hecarim"), getTagSet()),
            new Person(new Name("Clara White"), new Role("Support"), new Rank("Gold"),
                    new Champion("Zac"), getTagSet()),
            new Person(new Name("Tina Jones"), new Role("Jungle"), new Rank("Silver"),
                    new Champion("Braum"), getTagSet()),
            new Person(new Name("Ivy Harris"), new Role("Top"), new Rank("Platinum"),
                    new Champion("Fiora"), getTagSet()),
            new Person(new Name("Clara Jones"), new Role("Support"), new Rank("Gold"),
                    new Champion("Aatrox"), getTagSet()),
            new Person(new Name("Ivy Wilson"), new Role("Support"), new Rank("Iron"),
                    new Champion("Cho'gath"), getTagSet()),
            new Person(new Name("Riley Wilson"), new Role("Support"), new Rank("Platinum"),
                    new Champion("Sona"), getTagSet()),
            new Person(new Name("Mia Thompson"), new Role("Jungle"), new Rank("Iron"),
                    new Champion("Rek'sai"), getTagSet()),
            new Person(new Name("Penny Jones"), new Role("Mid"), new Rank("Platinum"),
                    new Champion("Zyra"), getTagSet()),
            new Person(new Name("Alice Davis"), new Role("Adc"), new Rank("Iron"),
                    new Champion("Talon"), getTagSet()),
            new Person(new Name("Ethan Jackson"), new Role("Adc"), new Rank("Master"),
                    new Champion("Senna"), getTagSet()),
            new Person(new Name("Tom Davis"), new Role("Mid"), new Rank("Master"),
                    new Champion("Tristana"), getTagSet()),
            new Person(new Name("Grace Jackson"), new Role("Jungle"), new Rank("Silver"),
                    new Champion("Pantheon"), getTagSet()),
            new Person(new Name("Oscar Harris"), new Role("Jungle"), new Rank("Platinum"),
                    new Champion("Jax"), getTagSet()),
            new Person(new Name("Alex Thompson"), new Role("Jungle"), new Rank("Challenger"),
                    new Champion("Zilean"), getTagSet()),
            new Person(new Name("Ben Anderson"), new Role("Top"), new Rank("Master"),
                    new Champion("Kindred"), getTagSet()),
            new Person(new Name("Eva Jackson"), new Role("Adc"), new Rank("Master"),
                    new Champion("Illaoi"), getTagSet()),
            new Person(new Name("David Harris"), new Role("Adc"), new Rank("Silver"),
                    new Champion("Qiyana"), getTagSet()),
            new Person(new Name("Kevin Wilson"), new Role("Jungle"), new Rank("Platinum"),
                    new Champion("Xin zhao"), getTagSet()),
            new Person(new Name("Mia Garcia"), new Role("Top"), new Rank("Master"),
                    new Champion("Qiyana"), getTagSet()),
            new Person(new Name("Eva Clark"), new Role("Adc"), new Rank("Challenger"),
                    new Champion("Volibear"), getTagSet()),
            new Person(new Name("Eva Thomas"), new Role("Adc"), new Rank("Challenger"),
                    new Champion("Vex"), getTagSet()),
            new Person(new Name("Fiona Anderson"), new Role("Mid"), new Rank("Grandmaster"),
                    new Champion("Akali"), getTagSet()),
            new Person(new Name("Julie Smith"), new Role("Adc"), new Rank("Challenger"),
                    new Champion("Leona"), getTagSet()),
            new Person(new Name("Ben Jones"), new Role("Top"), new Rank("Diamond"),
                    new Champion("Udyr"), getTagSet()),
            new Person(new Name("Penny Smith"), new Role("Support"), new Rank("Iron"),
                    new Champion("Kha'zix"), getTagSet()),
            new Person(new Name("Ian Jackson"), new Role("Mid"), new Rank("Platinum"),
                    new Champion("Azir"), getTagSet()),
            new Person(new Name("Ethan Smith"), new Role("Top"), new Rank("Grandmaster"),
                    new Champion("Nasus"), getTagSet()),
            new Person(new Name("Oscar Williams"), new Role("Jungle"), new Rank("Iron"),
                    new Champion("Tryndamere"), getTagSet()),
            new Person(new Name("George Anderson"), new Role("Support"), new Rank("Silver"),
                    new Champion("Leblanc"), getTagSet()),
            new Person(new Name("Frank Martin"), new Role("Adc"), new Rank("Platinum"),
                    new Champion("Bard"), getTagSet()),
            new Person(new Name("Kate Brown"), new Role("Top"), new Rank("Master"),
                    new Champion("Warwick"), getTagSet()),
            new Person(new Name("Lily Anderson"), new Role("Support"), new Rank("Diamond"),
                    new Champion("Akali"), getTagSet()),
            new Person(new Name("Ben Brown"), new Role("Support"), new Rank("Gold"),
                    new Champion("Ziggs"), getTagSet())
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
