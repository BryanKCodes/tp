package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        assertFalse(Name.isValidName("")); // empty string
        assertFalse(Name.isValidName(" ")); // spaces only
        assertFalse(Name.isValidName("^")); // only non-alphanumeric characters
        assertFalse(Name.isValidName("peter*")); // contains non-alphanumeric characters
        assertFalse(Name.isValidName("peter jack")); // contains space
        assertFalse(Name.isValidName("pe")); // less than 3 characters
        assertFalse(Name.isValidName("peterJackTheSecond")); // more than 16 characters

        // valid name
        assertTrue(Name.isValidName("faker")); // alphabets only
        assertTrue(Name.isValidName("Faker")); // with capital letters
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("Faker12345")); // alphanumeric characters
        assertTrue(Name.isValidName("Amy")); // exactly 3 characters
        assertTrue(Name.isValidName("FakerTheThird123")); // exactly 16 characters
    }

    @Test
    public void equals() {
        Name name = new Name("ValidName");

        // same values -> returns true
        assertTrue(name.equals(new Name("ValidName")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("OtherValidName")));
    }
}
