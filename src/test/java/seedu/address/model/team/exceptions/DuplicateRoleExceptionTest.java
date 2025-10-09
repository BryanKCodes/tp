package seedu.address.model.team.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class DuplicateRoleExceptionTest {

    @Test
    public void constructor_validPersons_success() {
        Person person1 = new PersonBuilder().withName("Alice")
                .withRole("mid").withChampion("Ahri").build();
        Person person2 = new PersonBuilder().withName("Bob")
                .withRole("mid").withChampion("Zed").build();

        DuplicateRoleException exception = new DuplicateRoleException(person1, person2);

        assertEquals(person1, exception.getPerson1());
        assertEquals(person2, exception.getPerson2());
        assertTrue(exception.getMessage().contains("mid"));
    }
}
