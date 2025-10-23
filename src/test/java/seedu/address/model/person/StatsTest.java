package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

public class StatsTest {

    private static double expectedScore(int cpm, int gd15, float kda) {
        double kdaNorm = Math.min(kda / 8.0, 1.0);
        double csNorm = Math.min(cpm / 10.0, 1.0);
        double gdNorm = 1.0 / (1.0 + Math.exp(-gd15 / 800.0));
        double s = 10.0 * (0.45 * kdaNorm + 0.35 * csNorm + 0.20 * gdNorm);
        s = Math.max(0.0, Math.min(s, 10.0));
        return s;
    }

    private static String fmt1(double v) {
        return String.format("%.1f", v);
    }

    // Defaults & Basic Behavior

    @Test
    void defaultConstructor_initialState() {
        Stats stats = new Stats();
        assertEquals("0.0", stats.getValue());
        assertTrue(stats.getCsPerMinute().isEmpty());
        assertTrue(stats.getGoldDiffAt15().isEmpty());
        assertTrue(stats.getKdaScores().isEmpty());
        assertTrue(stats.getScores().isEmpty());
    }

    // Validation

    @Test
    void isValidStats_boundaries_valid() {
        assertTrue(Stats.isValidStats("0", "0", "0")); // all mins
        assertTrue(Stats.isValidStats("40", "10000", "200")); // all maxs
        assertTrue(Stats.isValidStats("10", "-10000", "2")); // mixed
    }

    @Test
    void isValidStats_invalid() {
        // Non-numeric
        assertFalse(Stats.isValidStats("x", "0", "1"));
        assertFalse(Stats.isValidStats("1", "y", "1"));
        assertFalse(Stats.isValidStats("1", "0", "z"));

        // Out of range
        assertFalse(Stats.isValidStats("-1", "0", "0")); // cpm < 0
        assertFalse(Stats.isValidStats("41", "0", "0")); // cpm > 40
        assertFalse(Stats.isValidStats("0", "-10001", "0")); // gd15 < -10000
        assertFalse(Stats.isValidStats("0", "10001", "0")); // gd15 > 10000
        assertFalse(Stats.isValidStats("0", "0", "-0.1")); // kda < 0
        assertFalse(Stats.isValidStats("0", "0", "200.1")); // kda > 200
    }

    // Add & Average

    @Test
    void addLatestStats_appendsAndRecomputesAverage() {
        Stats s0 = new Stats();

        // First record
        Stats s1 = s0.addLatestStats("7", "1000", "2.2");
        assertNotEquals(s0, s1); // immutability
        assertEquals(1, s1.getCsPerMinute().size());
        assertEquals(1, s1.getGoldDiffAt15().size());
        assertEquals(1, s1.getKdaScores().size());
        assertEquals(1, s1.getScores().size());
        double e1 = expectedScore(7, 1000, 2.2f);
        assertEquals(fmt1(e1), s1.getValue());

        // Second record
        Stats s2 = s1.addLatestStats("4", "-200", "0.7");
        assertNotEquals(s1, s2);
        assertEquals(2, s2.getCsPerMinute().size());
        double e2avg = (e1 + expectedScore(4, -200, 0.7f)) / 2.0;
        assertEquals(fmt1(e2avg), s2.getValue());
    }

    @Test
    void addLatestStats_invalid_throws() {
        Stats s0 = new Stats();
        // cpm < 0
        assertThrows(IllegalArgumentException.class, () -> s0.addLatestStats("-1", "0", "1"));
        // gd > 10000
        assertThrows(IllegalArgumentException.class, () -> s0.addLatestStats("0", "20000", "1"));
        // kda > 200
        assertThrows(IllegalArgumentException.class, () -> s0.addLatestStats("0", "0", "999"));
    }

    // Delete latest

    @Test
    void deleteLatestStats_onEmpty_returnsEqual() {
        Stats s0 = new Stats();
        Stats s1 = s0.deleteLatestStats();
        // Equal by content; toString/value should match; equals compares lists+value
        assertEquals(s0, s1);
        assertEquals("0.0", s1.getValue());
    }

    @Test
    void deleteLatestStats_removesLast() {
        Stats s0 = new Stats();
        Stats s1 = s0.addLatestStats("7", "1000", "2.2"); // score e1
        Stats s2 = s1.addLatestStats("9", "1200", "2.0"); // score e2

        assertEquals(2, s2.getScores().size());

        Stats s3 = s2.deleteLatestStats(); // should remove e2
        assertEquals(1, s3.getScores().size());
        double e1 = expectedScore(7, 1000, 2.2f);
        assertEquals(fmt1(e1), s3.getValue());
    }

    // Getters (defensive copies)

    @Test
    void getters_returnDefensiveCopies() {
        Stats s = new Stats().addLatestStats("5", "300", "1.2");
        int csSize = s.getCsPerMinute().size();
        int gdSize = s.getGoldDiffAt15().size();
        int kdSize = s.getKdaScores().size();
        int scSize = s.getScores().size();

        // Mutate the returned lists
        List<Float> cs = s.getCsPerMinute();
        cs.add(9999F);
        List<Integer> gd = s.getGoldDiffAt15();
        gd.add(9999);
        List<Float> kd = s.getKdaScores();
        kd.add(9999f);
        List<Double> sc = s.getScores();
        sc.add(9999.0);

        // Internal sizes unchanged
        assertEquals(csSize, s.getCsPerMinute().size());
        assertEquals(gdSize, s.getGoldDiffAt15().size());
        assertEquals(kdSize, s.getKdaScores().size());
        assertEquals(scSize, s.getScores().size());
    }

    // Equality basics

    @Test
    void equals_basic() {
        Stats a = new Stats()
                .addLatestStats("7", "1000", "2.2")
                .addLatestStats("4", "-200", "0.7");

        Stats b = new Stats()
                .addLatestStats("7", "1000", "2.2")
                .addLatestStats("4", "-200", "0.7");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        Stats c = b.deleteLatestStats();
        assertNotEquals(a, c);
    }
}
