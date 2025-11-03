package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;

public class StatsTest {

    private static final double EPS = 1e-6;

    // Helpers

    /** Mirrors Stats.calculateScore(float,int,float) for deterministic checks. */
    private static double expectedScore(float cpm, int gd15, float kda) {
        double kdaNorm = Math.min(kda / 3.0, 1.0);
        double csNorm = Math.min(cpm / 10.0, 1.0);
        double gdNorm = 1.0 / (1.0 + Math.exp(-gd15 / 500.0));
        double s = 10.0 * (0.45 * kdaNorm + 0.35 * csNorm + 0.20 * gdNorm);
        return Math.max(0.0, Math.min(s, 10.0));
    }

    /** Mirrors rounding in calculateAverageScore(): round to 1dp (float). */
    private static float round1(double v) {
        return (float) (Math.round(v * 10.0) / 10.0);
    }

    // Defaults & Basic Behavior

    @Test
    void defaultConstructor_initialState() {
        Stats stats = new Stats();
        assertEquals(0.0F, stats.getValue(), EPS);
        assertTrue(stats.getCsPerMinute().isEmpty());
        assertTrue(stats.getGoldDiffAt15().isEmpty());
        assertTrue(stats.getKdaScores().isEmpty());
        assertTrue(stats.getScores().isEmpty());
    }

    // Validation

    @Test
    void isValidStats_boundaries_valid() {
        assertTrue(Stats.isValidStats("0", "0", "0"));
        assertTrue(Stats.isValidStats("40", "10000", "200"));
        assertTrue(Stats.isValidStats("10.5", "-10000", "2.0"));
    }

    @Test
    void isValidStats_invalid() {
        // Non-numeric
        assertFalse(Stats.isValidStats("x", "0", "1"));
        assertFalse(Stats.isValidStats("1", "y", "1"));
        assertFalse(Stats.isValidStats("1", "0", "z"));

        // Out of range
        assertFalse(Stats.isValidStats("-0.1", "0", "0")); // cpm < 0
        assertFalse(Stats.isValidStats("40.1", "0", "0")); // cpm > 40
        assertFalse(Stats.isValidStats("0", "-10001", "0")); // gd15 < -10000
        assertFalse(Stats.isValidStats("0", "10001", "0")); // gd15 > 10000
        assertFalse(Stats.isValidStats("0", "0", "-0.01")); // kda < 0
        assertFalse(Stats.isValidStats("0", "0", "200.01")); // kda > 200
    }

    // Add & Average

    @Test
    void addLatestStats_appendsAndRecomputesAverage() {
        Stats s0 = new Stats();

        // First record
        Stats s1 = s0.addLatestStats("7.0", "1000", "2.2");
        assertNotEquals(s0, s1); // immutability-by-return-new
        assertEquals(List.of(7.0F), s1.getCsPerMinute());
        assertEquals(List.of(1000), s1.getGoldDiffAt15());
        assertEquals(List.of(2.2F), s1.getKdaScores());
        assertEquals(1, s1.getScores().size());

        double e1 = expectedScore(7.0F, 1000, 2.2F);
        assertEquals(round1(e1), s1.getValue(), EPS);

        // Second record
        Stats s2 = s1.addLatestStats("4.0", "-200", "0.7");
        assertNotEquals(s1, s2);
        assertEquals(2, s2.getCsPerMinute().size());
        double e2 = expectedScore(4.0F, -200, 0.7F);
        float avgRounded = round1((e1 + e2) / 2.0);
        assertEquals(avgRounded, s2.getValue(), EPS);
    }

    @Test
    void addLatestStats_invalid_throws() {
        Stats s0 = new Stats();
        assertThrows(IllegalArgumentException.class, () -> s0.addLatestStats("-1", "0", "1"));
        assertThrows(IllegalArgumentException.class, () -> s0.addLatestStats("0", "20000", "1"));
        assertThrows(IllegalArgumentException.class, () -> s0.addLatestStats("0", "0", "999"));
    }

    // Delete latest

    @Test
    void deleteLatestStats_onEmpty_returnsEqual() throws CommandException {
        Stats s = new Stats();
        assertThrows(CommandException.class, Stats.NOT_DELETED_MESSAGE, s::deleteLatestStats);

    }

    @Test
    void deleteLatestStats_removesLast() throws CommandException {
        Stats s = new Stats()
                .addLatestStats("7.0", "1000", "2.2")
                .addLatestStats("9.0", "1200", "2.0");

        assertEquals(2, s.getScores().size());

        Stats sAfter = s.deleteLatestStats(); // removes 2nd record
        assertEquals(1, sAfter.getScores().size());

        double e1 = expectedScore(7.0F, 1000, 2.2F);
        assertEquals(round1(e1), sAfter.getValue(), EPS);
    }

    // Getters (defensive copies)

    @Test
    void getters_returnDefensiveCopies() {
        Stats s = new Stats().addLatestStats("5.0", "300", "1.2");

        int csSize = s.getCsPerMinute().size();
        int gdSize = s.getGoldDiffAt15().size();
        int kdSize = s.getKdaScores().size();
        int scSize = s.getScores().size();

        // Mutate returned lists
        List<Float> cs = s.getCsPerMinute();
        cs.add(9999F);
        List<Integer> gd = s.getGoldDiffAt15();
        gd.add(9999);
        List<Float> kd = s.getKdaScores();
        kd.add(9999F);
        List<Double> sc = s.getScores();
        sc.add(9999.0);

        // Internal sizes unchanged
        assertEquals(csSize, s.getCsPerMinute().size());
        assertEquals(gdSize, s.getGoldDiffAt15().size());
        assertEquals(kdSize, s.getKdaScores().size());
        assertEquals(scSize, s.getScores().size());
    }

    // equals/hashCode & toString

    @Test
    void equals_basic() throws CommandException {
        Stats a = new Stats()
                .addLatestStats("7.0", "1000", "2.2")
                .addLatestStats("4.0", "-200", "0.7");

        Stats b = new Stats()
                .addLatestStats("7.0", "1000", "2.2")
                .addLatestStats("4.0", "-200", "0.7");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        Stats c = b.deleteLatestStats();
        assertNotEquals(a, c);
    }

    @Test
    void toString_matchesValueString() {
        Stats s = new Stats()
                .addLatestStats("7.0", "1000", "2.2");
        assertEquals(Float.toString(s.getValue()), s.toString());
    }

    // --- Regex-only tests (shape, not range) ---

    @Test
    void floatRegex_validShapes() {
        // Accept non-negative integers (≤6 digits)
        assertTrue("0".matches(Stats.FLOAT_VALIDATION_REGEX));
        assertTrue("7".matches(Stats.FLOAT_VALIDATION_REGEX));
        assertTrue("000007".matches(Stats.FLOAT_VALIDATION_REGEX)); // leading zeros allowed
        assertTrue("123456".matches(Stats.FLOAT_VALIDATION_REGEX)); // 6 digits

        // Accept with 1–2 decimal digits
        assertTrue("0.0".matches(Stats.FLOAT_VALIDATION_REGEX));
        assertTrue("0.00".matches(Stats.FLOAT_VALIDATION_REGEX));
        assertTrue("12.3".matches(Stats.FLOAT_VALIDATION_REGEX));
        assertTrue("12.34".matches(Stats.FLOAT_VALIDATION_REGEX));
        assertTrue("000001.20".matches(Stats.FLOAT_VALIDATION_REGEX)); // leading zeros + 2 dp

        // Max integer digits with decimals
        assertTrue("123456.78".matches(Stats.FLOAT_VALIDATION_REGEX)); // 6 digits + 2 dp
    }

    @Test
    void floatRegex_invalidShapes() {
        // Too many integer digits
        assertFalse("1234567".matches(Stats.FLOAT_VALIDATION_REGEX)); // 7 digits
        assertFalse("1234567.89".matches(Stats.FLOAT_VALIDATION_REGEX));

        // Decimal part problems
        assertFalse("1.".matches(Stats.FLOAT_VALIDATION_REGEX)); // missing fraction
        assertFalse(".5".matches(Stats.FLOAT_VALIDATION_REGEX)); // missing integer part
        assertFalse("0.000".matches(Stats.FLOAT_VALIDATION_REGEX)); // >2 dp
        assertFalse("1.2.3".matches(Stats.FLOAT_VALIDATION_REGEX)); // multiple dots

        // Signs / format not allowed
        assertFalse("-1.0".matches(Stats.FLOAT_VALIDATION_REGEX)); // negative not allowed
        assertFalse("+1.0".matches(Stats.FLOAT_VALIDATION_REGEX)); // '+' not allowed
        assertFalse("1e3".matches(Stats.FLOAT_VALIDATION_REGEX)); // scientific notation not allowed

        // Whitespace or junk
        assertFalse(" 1.0".matches(Stats.FLOAT_VALIDATION_REGEX));
        assertFalse("1.0 ".matches(Stats.FLOAT_VALIDATION_REGEX));
        assertFalse("NaN".matches(Stats.FLOAT_VALIDATION_REGEX));
    }

    @Test
    void intRegex_validShapes() {
        // Signed or unsigned, up to 6 digits
        assertTrue("0".matches(Stats.INT_VALIDATION_REGEX));
        assertTrue("-0".matches(Stats.INT_VALIDATION_REGEX)); // allowed by pattern
        assertTrue("7".matches(Stats.INT_VALIDATION_REGEX));
        assertTrue("-7".matches(Stats.INT_VALIDATION_REGEX));
        assertTrue("000123".matches(Stats.INT_VALIDATION_REGEX)); // leading zeros allowed
        assertTrue("123456".matches(Stats.INT_VALIDATION_REGEX)); // 6 digits
        assertTrue("-123456".matches(Stats.INT_VALIDATION_REGEX));
    }

    @Test
    void intRegex_invalidShapes() {
        // Too many digits
        assertFalse("1234567".matches(Stats.INT_VALIDATION_REGEX)); // 7 digits
        assertFalse("-1234567".matches(Stats.INT_VALIDATION_REGEX));

        // Plus sign not allowed; decimals not allowed
        assertFalse("+5".matches(Stats.INT_VALIDATION_REGEX));
        assertFalse("5.0".matches(Stats.INT_VALIDATION_REGEX));
        assertFalse("5.".matches(Stats.INT_VALIDATION_REGEX));
        assertFalse(".5".matches(Stats.INT_VALIDATION_REGEX));

        // Whitespace or junk
        assertFalse(" 10".matches(Stats.INT_VALIDATION_REGEX));
        assertFalse("10 ".matches(Stats.INT_VALIDATION_REGEX));
        assertFalse("1e3".matches(Stats.INT_VALIDATION_REGEX));
        assertFalse("--5".matches(Stats.INT_VALIDATION_REGEX));
    }

    @Test
    void isValidStats_shapeEdgeCases_thatStillPassRange() {
        // Leading zeros allowed and within range
        assertTrue(Stats.isValidStats("000040.00", "000000", "000200.00")); // 40.00, 0, 200.00
        assertTrue(Stats.isValidStats("000000.10", "-000001", "000000.00")); // 0.10, -1, 0.00

        // Minimal forms still valid
        assertTrue(Stats.isValidStats("0", "-0", "0")); // "-0" matches int regex and equals 0
        assertTrue(Stats.isValidStats("10.0", "5", "3.00"));
    }

    @Test
    void isValidStats_shapeLooksOk_butRangeFails() {
        // Regex passes, semantic ranges fail
        assertFalse(Stats.isValidStats("123456.78", "0", "1.0")); // cpm > 40
        assertFalse(Stats.isValidStats("1.0", "999999", "1.0")); // gd15 > 10000
        assertFalse(Stats.isValidStats("1.0", "0", "999999.99")); // kda > 200
    }

    @Test
    void isValidStats_plusSignAndSpaces_rejectedByShape() {
        // '+' sign not allowed by regex; spaces not allowed
        assertFalse(Stats.isValidStats("+1.0", "0", "0"));
        assertFalse(Stats.isValidStats("1.0", "+10", "0"));
        assertFalse(Stats.isValidStats("1.0", "0", "+0.5"));

        assertFalse(Stats.isValidStats(" 1.0", "0", "0"));
        assertFalse(Stats.isValidStats("1.0 ", "0", "0"));
        assertFalse(Stats.isValidStats("1.0", " 0", "0"));
        assertFalse(Stats.isValidStats("1.0", "0 ", "0"));
        assertFalse(Stats.isValidStats("1.0", "0", " 0"));
        assertFalse(Stats.isValidStats("1.0", "0", "0 "));
    }

    @Test
    void isValidStats_decimalEdgeCases() {
        // Exactly 2 dp allowed; more than 2 rejects
        assertTrue(Stats.isValidStats("3.50", "10", "2.75"));
        assertFalse(Stats.isValidStats("3.500", "10", "2.75"));
        assertFalse(Stats.isValidStats("3.", "10", "2")); // missing fractional digits
        assertFalse(Stats.isValidStats(".5", "10", "2")); // missing integer digits
    }

}
