package seedu.address.logic.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class WlReflectTest {

    static class NoStats { } // no getWins()/getLosses()

    static class WithStats {
        public Integer getWins() {
            return 3;
        }
        public Integer getLosses() {
            return 1;
        }
    }

    @Test
    void wins_missingMethod_returnsZero() {
        assertEquals(0, WlReflect.wins(new NoStats()));
    }

    @Test
    void wins_presentMethod_returnsValue() {
        assertEquals(3, WlReflect.wins(new WithStats()));
    }

    @Test
    void losses_presentMethod_returnsValue() {
        assertEquals(1, WlReflect.losses(new WithStats()));
    }
}

