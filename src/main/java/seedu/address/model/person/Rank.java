package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's rank in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRank(String)}
 */
public class Rank {

    private enum RankName {
        IRON("iron"),
        BRONZE("bronze"),
        SILVER("silver"),
        GOLD("gold"),
        PLATINUM("platinum"),
        EMERALD("emerald"),
        DIAMOND("diamond"),
        MASTER("master"),
        GRANDMASTER("grandmaster"),
        CHALLENGER("challenger");

        private final String name;

        RankName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static final String MESSAGE_CONSTRAINTS =
            "Rank must be one of the following: iron, bronze, silver, gold, platinum, "
                    + "emerald, diamond, master, grandmaster, challenger.";

    public final String value;

    /**
     * Constructs a {@code Rank }.
     *
     * @param rank A valid rank.
     */
    public Rank(String rank) {
        requireNonNull(rank);
        checkArgument(isValidRank(rank), MESSAGE_CONSTRAINTS);
        this.value = rank.substring(0, 1).toUpperCase() + rank.substring(1).toLowerCase();;
    }

    /**
     * Returns true if a given string is a valid RankName.
     */
    public static boolean isValidRank(String test) {
        for (RankName r : RankName.values()) {
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
        if (!(other instanceof Rank)) {
            return false;
        }

        Rank otherRank = (Rank) other;
        return value.equals(otherRank.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
