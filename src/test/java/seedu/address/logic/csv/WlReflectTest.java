package seedu.address.logic.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class WlReflectTest {

    static class NoStats { } // no getWins()/getLosses()

    static class WithStatsInt {
        public Integer getWins() {
            return 3;
        }
        public Integer getLosses() {
            return 1;
        }
    }

    static class WithStatsString {
        public String getWins() {
            return "3";
        } // not Integer -> default
        public String getLosses() {
            return "1";
        } // not Integer -> default
    }

    @Test
    void wins_missingMethod_returnsZero() {
        assertEquals(0, WlReflect.wins(new NoStats()));
    }

    @Test
    void wins_presentInteger_returnsValue() {
        assertEquals(3, WlReflect.wins(new WithStatsInt()));
    }

    @Test
    void wins_presentNonInteger_returnsDefaultZero() {
        assertEquals(0, WlReflect.wins(new WithStatsString()));
    }
}

