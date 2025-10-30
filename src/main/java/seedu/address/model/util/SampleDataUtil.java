package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Champion;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Rank;
import seedu.address.model.person.Role;
import seedu.address.model.person.Stats;
import seedu.address.model.tag.Tag;
import seedu.address.model.team.Team;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(
                UUID.randomUUID().toString(),
                new Name("Faker"), new Role("mid"), new Rank("diamond"),
                new Champion("Azir"), getTagSet(),
                new Stats()
                        .addLatestStats("5.4", "3400", "4.6")
                        .addLatestStats("4.8", "1100", "4.8")
                        .addLatestStats("8.9", "1850", "3.9"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Oner"), new Role("jungle"), new Rank("diamond"),
                new Champion("Xin zhao"), getTagSet(),
                new Stats()
                        .addLatestStats("9.5", "-1150", "5.7")
                        .addLatestStats("7.4", "-3850", "4.8")
                        .addLatestStats("4.4", "4950", "5.3"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Gumayusi"), new Role("adc"), new Rank("diamond"),
                new Champion("Xayah"), getTagSet(),
                new Stats()
                        .addLatestStats("8.9", "5000", "4.7")
                        .addLatestStats("9.5", "3350", "6.9")
                        .addLatestStats("6.5", "-1800", "5.4"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Zeus"), new Role("top"), new Rank("master"),
                new Champion("Jayce"), getTagSet(),
                new Stats()
                        .addLatestStats("5.5", "1600", "5.2")
                        .addLatestStats("3.7", "2850", "6.3")
                        .addLatestStats("10.3", "-2750", "3.6"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Keria"), new Role("support"), new Rank("challenger"),
                new Champion("Bard"), getTagSet(),
                new Stats()
                        .addLatestStats("8.5", "-200", "4.0")
                        .addLatestStats("3.8", "-2450", "5.5")
                        .addLatestStats("4.7", "1350", "4.7"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Deft"), new Role("adc"), new Rank("master"),
                new Champion("Caitlyn"), getTagSet(),
                new Stats()
                        .addLatestStats("5.8", "-2600", "5.9")
                        .addLatestStats("10.7", "-2800", "6.5")
                        .addLatestStats("3.7", "1000", "6.5"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Chovy"), new Role("mid"), new Rank("grandmaster"),
                new Champion("Yone"), getTagSet(),
                new Stats()
                        .addLatestStats("7.8", "-1650", "3.8")
                        .addLatestStats("4.7", "-3500", "3.4")
                        .addLatestStats("9.4", "3200", "4.4"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("ShowMaker"), new Role("mid"), new Rank("challenger"),
                new Champion("Katarina"), getTagSet(),
                new Stats()
                        .addLatestStats("8.3", "150", "5.0")
                        .addLatestStats("6.6", "650", "4.1")
                        .addLatestStats("8.1", "-450", "6.4"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Canyon"), new Role("jungle"), new Rank("emerald"),
                new Champion("Aatrox"), getTagSet(),
                new Stats()
                        .addLatestStats("8.4", "2700", "3.5")
                        .addLatestStats("3.9", "-250", "4.9")
                        .addLatestStats("5.6", "2400", "5.7"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Ruler"), new Role("adc"), new Rank("platinum"),
                new Champion("Caitlyn"), getTagSet(),
                new Stats()
                        .addLatestStats("6.7", "3650", "3.9")
                        .addLatestStats("5.6", "3450", "2.6")
                        .addLatestStats("9.6", "50", "5.1"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Scout"), new Role("mid"), new Rank("gold"),
                new Champion("Kassadin"), getTagSet(),
                new Stats()
                        .addLatestStats("3.6", "-1450", "3.7")
                        .addLatestStats("7.0", "-3250", "5.1")
                        .addLatestStats("5.9", "-1400", "4.9"),
                0, 3),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Knight"), new Role("mid"), new Rank("silver"),
                new Champion("Zoe"), getTagSet(),
                new Stats()
                        .addLatestStats("10.1", "2550", "6.2")
                        .addLatestStats("6.7", "-2900", "8.0")
                        .addLatestStats("4.0", "-3800", "6.8"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Meiko"), new Role("support"), new Rank("bronze"),
                new Champion("Sona"), getTagSet(),
                new Stats()
                        .addLatestStats("6.3", "3500", "4.4")
                        .addLatestStats("4.5", "-3650", "4.6")
                        .addLatestStats("7.9", "900", "4.4"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Peanut"), new Role("jungle"), new Rank("grandmaster"),
                new Champion("Teemo"), getTagSet(),
                new Stats()
                        .addLatestStats("8.3", "1250", "4.7")
                        .addLatestStats("3.9", "3900", "5.8")
                        .addLatestStats("7.5", "-850", "6.8"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Lehends"), new Role("support"), new Rank("challenger"),
                new Champion("Blitzcrank"), getTagSet(),
                new Stats()
                        .addLatestStats("9.1", "800", "3.9")
                        .addLatestStats("5.3", "2400", "3.8")
                        .addLatestStats("5.1", "-1550", "6.1"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Viper"), new Role("adc"), new Rank("emerald"),
                new Champion("Ezreal"), getTagSet(),
                new Stats()
                        .addLatestStats("6.3", "-1800", "4.1")
                        .addLatestStats("5.1", "-4750", "5.1")
                        .addLatestStats("3.6", "3150", "4.4"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Tarzan"), new Role("jungle"), new Rank("platinum"),
                new Champion("Nasus"), getTagSet(),
                new Stats()
                        .addLatestStats("6.5", "50", "4.9")
                        .addLatestStats("4.0", "1100", "3.9")
                        .addLatestStats("5.0", "250", "6.5"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Doinb"), new Role("mid"), new Rank("gold"),
                new Champion("Yasuo"), getTagSet(),
                new Stats()
                        .addLatestStats("7.1", "700", "5.3")
                        .addLatestStats("6.8", "-4450", "3.3")
                        .addLatestStats("7.3", "-3000", "4.4"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("TheShy"), new Role("top"), new Rank("silver"),
                new Champion("Darius"), getTagSet(),
                new Stats()
                        .addLatestStats("8.9", "0", "4.3")
                        .addLatestStats("6.0", "4900", "5.3")
                        .addLatestStats("9.3", "-850", "4.4"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Rookie"), new Role("mid"), new Rank("bronze"),
                new Champion("Talon"), getTagSet(),
                new Stats()
                        .addLatestStats("7.7", "-550", "4.5")
                        .addLatestStats("6.8", "4800", "5.5")
                        .addLatestStats("9.3", "2750", "5.2"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Uzi"), new Role("adc"), new Rank("iron"),
                new Champion("Ashe"), getTagSet(),
                new Stats()
                        .addLatestStats("9.4", "1300", "4.5")
                        .addLatestStats("10.7", "2700", "6.0")
                        .addLatestStats("5.9", "4000", "4.2"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("JackeyLove"), new Role("adc"), new Rank("diamond"),
                new Champion("Jinx"), getTagSet(),
                new Stats()
                        .addLatestStats("9.0", "4950", "3.8")
                        .addLatestStats("6.1", "-2350", "5.4")
                        .addLatestStats("7.2", "900", "6.9"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Ming"), new Role("support"), new Rank("master"),
                new Champion("Nautilus"), getTagSet(),
                new Stats()
                        .addLatestStats("8.5", "1300", "5.1")
                        .addLatestStats("4.5", "-2100", "5.1")
                        .addLatestStats("7.7", "1250", "6.8"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Karsa"), new Role("jungle"), new Rank("grandmaster"),
                new Champion("Shen"), getTagSet(),
                new Stats()
                        .addLatestStats("7.6", "-1000", "5.2")
                        .addLatestStats("7.3", "-1500", "3.6")
                        .addLatestStats("8.8", "4300", "5.7"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Baolan"), new Role("support"), new Rank("challenger"),
                new Champion("Soraka"), getTagSet(),
                new Stats()
                        .addLatestStats("5.0", "-150", "5.6")
                        .addLatestStats("8.3", "-5000", "6.2")
                        .addLatestStats("6.6", "3150", "5.1"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Clid"), new Role("jungle"), new Rank("emerald"),
                new Champion("Renekton"), getTagSet(),
                new Stats()
                        .addLatestStats("3.7", "-1050", "7.0")
                        .addLatestStats("4.3", "3050", "2.9")
                        .addLatestStats("8.4", "-1150", "4.3"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("BeryL"), new Role("support"), new Rank("platinum"),
                new Champion("Nami"), getTagSet(),
                new Stats()
                        .addLatestStats("8.4", "-4550", "5.1")
                        .addLatestStats("6.4", "2650", "4.7")
                        .addLatestStats("9.3", "4750", "4.5"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Zeka"), new Role("mid"), new Rank("gold"),
                new Champion("Syndra"), getTagSet(),
                new Stats()
                        .addLatestStats("4.8", "4100", "5.8")
                        .addLatestStats("6.6", "1300", "6.1")
                        .addLatestStats("9.8", "3750", "5.6"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Gala"), new Role("adc"), new Rank("silver"),
                new Champion("Vayne"), getTagSet(),
                new Stats()
                        .addLatestStats("4.8", "-50", "5.4")
                        .addLatestStats("10.8", "3000", "6.7")
                        .addLatestStats("8.5", "2100", "6.2"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Wei"), new Role("jungle"), new Rank("bronze"),
                new Champion("Tryndamere"), getTagSet(),
                new Stats()
                        .addLatestStats("9.7", "-300", "4.7")
                        .addLatestStats("8.2", "1850", "7.3")
                        .addLatestStats("10.6", "-750", "6.7"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Crisp"), new Role("support"), new Rank("iron"),
                new Champion("Janna"), getTagSet(),
                new Stats()
                        .addLatestStats("9.0", "4700", "6.1")
                        .addLatestStats("9.2", "4400", "3.1")
                        .addLatestStats("9.3", "4050", "2.2"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Lwx"), new Role("adc"), new Rank("master"),
                new Champion("Caitlyn"), getTagSet(),
                new Stats()
                        .addLatestStats("8.8", "-250", "5.1")
                        .addLatestStats("6.4", "1700", "5.2")
                        .addLatestStats("8.6", "4950", "5.2"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("FoFo"), new Role("mid"), new Rank("grandmaster"),
                new Champion("Galio"), getTagSet(),
                new Stats()
                        .addLatestStats("8.8", "-2600", "4.6")
                        .addLatestStats("7.3", "3400", "8.1")
                        .addLatestStats("9.7", "3850", "4.9"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Maple"), new Role("mid"), new Rank("challenger"),
                new Champion("Ahri"), getTagSet(),
                new Stats()
                        .addLatestStats("7.5", "4200", "5.3")
                        .addLatestStats("5.2", "4150", "4.1")
                        .addLatestStats("8.1", "4300", "7.4"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("SwordArt"), new Role("support"), new Rank("emerald"),
                new Champion("Thresh"), getTagSet(),
                new Stats()
                        .addLatestStats("6.0", "3150", "6.0")
                        .addLatestStats("5.5", "4800", "4.8")
                        .addLatestStats("7.6", "4050", "3.6"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Hanabi"), new Role("top"), new Rank("platinum"),
                new Champion("Riven"), getTagSet(),
                new Stats()
                        .addLatestStats("10.5", "2800", "4.1")
                        .addLatestStats("9.9", "4350", "5.6")
                        .addLatestStats("5.3", "4450", "5.0"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("River"), new Role("jungle"), new Rank("gold"),
                new Champion("Aatrox"), getTagSet(),
                new Stats()
                        .addLatestStats("6.5", "750", "3.9")
                        .addLatestStats("3.6", "3900", "3.0")
                        .addLatestStats("8.8", "-2600", "4.5"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("CoreJJ"), new Role("support"), new Rank("silver"),
                new Champion("Rakan"), getTagSet(),
                new Stats()
                        .addLatestStats("5.8", "-3250", "5.6")
                        .addLatestStats("9.7", "-4100", "6.7")
                        .addLatestStats("7.8", "2850", "4.5"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Bjergsen"), new Role("mid"), new Rank("bronze"),
                new Champion("Annie"), getTagSet(),
                new Stats()
                        .addLatestStats("5.6", "4350", "4.6")
                        .addLatestStats("5.1", "-750", "3.8")
                        .addLatestStats("4.4", "1050", "6.5"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Doublelift"), new Role("adc"), new Rank("iron"),
                new Champion("Xayah"), getTagSet(),
                new Stats()
                        .addLatestStats("8.9", "4750", "4.9")
                        .addLatestStats("10.7", "4200", "6.5")
                        .addLatestStats("4.8", "-4100", "3.7"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Sneaky"), new Role("adc"), new Rank("diamond"),
                new Champion("Ezreal"), getTagSet(),
                new Stats()
                        .addLatestStats("4.0", "-600", "5.9")
                        .addLatestStats("7.8", "-2950", "4.9")
                        .addLatestStats("4.9", "-3200", "4.3"),
                0, 3),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Meteos"), new Role("jungle"), new Rank("master"),
                new Champion("Teemo"), getTagSet(),
                new Stats()
                        .addLatestStats("10.6", "-1350", "5.6")
                        .addLatestStats("10.1", "4800", "4.6")
                        .addLatestStats("5.2", "-1300", "3.7"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Impact"), new Role("top"), new Rank("grandmaster"),
                new Champion("Malphite"), getTagSet(),
                new Stats()
                        .addLatestStats("3.6", "-4650", "6.1")
                        .addLatestStats("7.4", "-250", "3.1")
                        .addLatestStats("3.7", "2400", "5.3"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Xmithie"), new Role("jungle"), new Rank("challenger"),
                new Champion("Nasus"), getTagSet(),
                new Stats()
                        .addLatestStats("6.6", "-4650", "5.6")
                        .addLatestStats("6.3", "2350", "4.6")
                        .addLatestStats("6.9", "-4300", "7.0"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Aphromoo"), new Role("support"), new Rank("emerald"),
                new Champion("Lulu"), getTagSet(),
                new Stats()
                        .addLatestStats("10.3", "300", "6.6")
                        .addLatestStats("6.4", "1900", "5.8")
                        .addLatestStats("5.1", "3450", "6.3"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Blaber"), new Role("jungle"), new Rank("platinum"),
                new Champion("Shen"), getTagSet(),
                new Stats()
                        .addLatestStats("6.1", "300", "4.9")
                        .addLatestStats("6.6", "-3950", "4.6")
                        .addLatestStats("9.1", "-3300", "5.2"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Vulcan"), new Role("support"), new Rank("gold"),
                new Champion("Sona"), getTagSet(),
                new Stats()
                        .addLatestStats("7.8", "3850", "4.2")
                        .addLatestStats("6.3", "4450", "4.7")
                        .addLatestStats("6.2", "1500", "7.3"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Zven"), new Role("support"), new Rank("silver"),
                new Champion("Leona"), getTagSet(),
                new Stats()
                        .addLatestStats("9.9", "4000", "6.2")
                        .addLatestStats("6.7", "-4200", "5.6")
                        .addLatestStats("10.3", "600", "5.4"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Spica"), new Role("jungle"), new Rank("bronze"),
                new Champion("Renekton"), getTagSet(),
                new Stats()
                        .addLatestStats("10.3", "-2450", "4.4")
                        .addLatestStats("8.3", "-2050", "5.8")
                        .addLatestStats("7.8", "4050", "3.7"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Inspired"), new Role("jungle"), new Rank("iron"),
                new Champion("Tryndamere"), getTagSet(),
                new Stats()
                        .addLatestStats("4.4", "2200", "1.9")
                        .addLatestStats("5.3", "-750", "4.2")
                        .addLatestStats("7.3", "2050", "5.3"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Untara"), new Role("top"), new Rank("grandmaster"),
                new Champion("Teemo"), getTagSet(),
                new Stats()
                        .addLatestStats("9.1", "-2250", "4.9")
                        .addLatestStats("3.9", "-2750", "3.3")
                        .addLatestStats("6.8", "-3950", "3.3"),
                0, 3),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Kiin"), new Role("top"), new Rank("challenger"),
                new Champion("K'sante"), getTagSet(),
                new Stats()
                        .addLatestStats("8.7", "-4100", "5.4")
                        .addLatestStats("10.3", "-3400", "4.5")
                        .addLatestStats("4.5", "-4950", "4.3"),
                0, 3),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Morgan"), new Role("top"), new Rank("emerald"),
                new Champion("Renekton"), getTagSet(),
                new Stats()
                        .addLatestStats("5.9", "2200", "5.1")
                        .addLatestStats("6.5", "-3150", "3.7")
                        .addLatestStats("8.4", "3150", "2.9"),
                2, 1),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Doran"), new Role("top"), new Rank("platinum"),
                new Champion("Malphite"), getTagSet(),
                new Stats()
                        .addLatestStats("7.4", "2350", "4.5")
                        .addLatestStats("6.7", "1500", "4.5")
                        .addLatestStats("9.7", "850", "5.1"),
                3, 0),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Ireking"), new Role("top"), new Rank("diamond"),
                new Champion("Irelia"), getTagSet(),
                new Stats()
                        .addLatestStats("5.0", "-1700", "4.8")
                        .addLatestStats("6.7", "1400", "6.6")
                        .addLatestStats("3.6", "-1550", "4.5"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Adam"), new Role("top"), new Rank("silver"),
                new Champion("Garen"), getTagSet(),
                new Stats()
                        .addLatestStats("6.9", "4600", "5.2")
                        .addLatestStats("9.5", "-4750", "4.1")
                        .addLatestStats("5.5", "-300", "6.4"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Kingen"), new Role("top"), new Rank("bronze"),
                new Champion("Aatrox"), getTagSet(),
                new Stats()
                        .addLatestStats("6.7", "-4350", "4.6")
                        .addLatestStats("6.0", "-3250", "6.0")
                        .addLatestStats("4.8", "3550", "6.3"),
                1, 2),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Driver"), new Role("top"), new Rank("iron"),
                new Champion("Sion"), getTagSet(),
                new Stats()
                        .addLatestStats("5.5", "-1150", "5.8")
                        .addLatestStats("6.8", "-4750", "4.9")
                        .addLatestStats("9.0", "-200", "5.4"),
                0, 3),
            new Person(
                UUID.randomUUID().toString(),
                new Name("Bwipbo"), new Role("top"), new Rank("diamond"),
                new Champion("Mordekaiser"), getTagSet(),
                new Stats()
                        .addLatestStats("4.4", "-1550", "5.5")
                        .addLatestStats("8.5", "-3850", "4.7")
                        .addLatestStats("9.8", "-2800", "4.9"),
                0, 3),
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
