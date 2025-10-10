package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    private enum RoleName {
        TOP("top"),
        JUNGLE("jungle"),
        MID("mid"),
        ADC("adc"),
        SUPPORT("support");

        private final String name;

        RoleName(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Role must be one of the following: top, jungle, mid, adc, support.";

    public final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        this.value = role;
    }

    /**
     * Returns true if a given string is a valid RoleName.
     */
    public static boolean isValidRole(String test) {
        for (RoleName r : RoleName.values()) {
            if (r.getName().equalsIgnoreCase(test)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return value.equals(otherRole.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
