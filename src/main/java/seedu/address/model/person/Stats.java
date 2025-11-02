package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Immutable, value-type container for a player's cumulative performance statistics.
 *
 * <p>Each {@code Stats} instance holds historical series for:
 * <ul>
 *   <li>CS per minute (CPM) — {@code List<Float>}</li>
 *   <li>Gold difference at 15:00 (GD15) — {@code List<Integer>}</li>
 *   <li>KDA ratio — {@code List<Float>}</li>
 *   <li>Per-match composite scores (0–10) — {@code List<Double>}</li>
 * </ul>
 *
 * <p>Instances are treated as immutable from the outside: mutating operations (e.g. adding/removing
 * the latest record) return a <em>new</em> {@code Stats} with defensively-copied lists
 * and a recomputed average {@link #value}.
 */
public class Stats {

    /** User-facing constraints text aggregated for argument validation errors. */
    public static final String MESSAGE_CONSTRAINTS =
            "Creeps score per minute (cpm) must be an integer or decimal between 0.00 and 40.00 "
                    + "(at most 2 decimal digits, at most 6 digits in the integer part);\n"
                    + "Gold difference at 15m (gd15) must be an integer between -10 000 and 10 000 "
                    + "(at most 6 digits in the integer part);\n"
                    + "Kill/Death/Assist (kda) must be an integer or decimal between 0.00 and 200.00 "
                    + "(at most 2 decimal digits, at most 6 digits in the integer part)";

    /** Message used when trying to delete from an empty stats history. */
    public static final String NOT_DELETED_MESSAGE = "This Player has no statistics record to be deleted";

    /** Inclusive upper/lower bounds for semantic range checks. */
    public static final float MAX_CPM = 40.0F;
    public static final float MIN_CPM = 0.0F;
    public static final int MIN_GD15 = -10000;
    public static final int MAX_GD15 = 10000;
    public static final float MIN_KDA = 0.0F;
    public static final float MAX_KDA = 200.0F;

    /**
     * Shape validation for non-negative decimal with up to 6 integer digits and optional 1–2 fractional digits.
     * <p>Examples accepted: {@code 0}, {@code 7}, {@code 12.3}, {@code 12.34}.
     * Examples rejected: {@code .5}, {@code 5.}
     */
    public static final String FLOAT_VALIDATION_REGEX = "^\\d{1,6}(?:\\.\\d{1,2})?$";

    /**
     * Shape validation for a signed integer with up to 6 digits in the absolute value.
     * <p>Examples accepted: {@code -5000}, {@code 0}, {@code 999999}. Example rejected: {@code 1000000}.
     */
    public static final String INT_VALIDATION_REGEX = "^-?\\d{1,6}$";

    /** Average of {@link #scores}, rounded to 1 decimal place. */
    public final float value;

    /** Historical CS per minute values (chronological). */
    public final ArrayList<Float> csPerMinute;

    /** Historical gold difference at 15:00 values (chronological). */
    public final ArrayList<Integer> goldDiffAt15;

    /** Historical KDA values (chronological). */
    public final ArrayList<Float> kdaScores;

    /** Historical per-match composite scores in [0, 10] (chronological). */
    public final ArrayList<Double> scores;

    /**
     * Creates an empty {@code Stats} with no history; {@link #value} is {@code 0.0F}.
     */
    public Stats() {
        this.csPerMinute = new ArrayList<>();
        this.goldDiffAt15 = new ArrayList<>();
        this.kdaScores = new ArrayList<>();
        this.scores = new ArrayList<>();
        this.value = calculateAverageScore();
    }

    /**
     * Internal constructor used by "mutators" to produce a new immutable snapshot.
     * All lists are assumed to be new defensive copies owned by this instance.
     *
     * @param csPerMinute historical CPM values
     * @param goldDiffAt15 historical GD15 values
     * @param kdaScores historical KDA values
     * @param scores historical composite scores
     */
    public Stats(ArrayList<Float> csPerMinute,
                 ArrayList<Integer> goldDiffAt15,
                 ArrayList<Float> kdaScores,
                 ArrayList<Double> scores) {
        this.csPerMinute = csPerMinute;
        this.goldDiffAt15 = goldDiffAt15;
        this.kdaScores = kdaScores;
        this.scores = scores;
        this.value = calculateAverageScore();
    }

    /**
     * Returns a new {@code Stats} with one more match appended to all series.
     *
     * <p>Validation occurs in two layers:
     * <ol>
     *   <li>Regex “shape” checks: {@link #FLOAT_VALIDATION_REGEX} for CPM/KDA and
     *       {@link #INT_VALIDATION_REGEX} for GD15.</li>
     *   <li>Semantic range checks: {@link #MIN_CPM}–{@link #MAX_CPM}, {@link #MIN_GD15}–{@link #MAX_GD15},
     *       and {@link #MIN_KDA}–{@link #MAX_KDA}.</li>
     * </ol>
     *
     * @param cpm  CS per minute as string (non-negative decimal, ≤2 d.p., ≤6 integer digits)
     * @param gd15 Gold difference at 15:00 as string (signed integer, ≤6 digits)
     * @param kda  KDA as string (non-negative decimal, ≤2 d.p., ≤6 integer digits)
     * @return a new {@code Stats} instance containing the appended values and recomputed average
     * @throws IllegalArgumentException if any input fails shape or range validation
     */
    public Stats addLatestStats(String cpm, String gd15, String kda) {
        requireAllNonNull(cpm, gd15, kda);
        checkArgument(isValidStats(cpm, gd15, kda), MESSAGE_CONSTRAINTS);

        // Safe to parse after validation.
        float floatCpm = Float.parseFloat(cpm);
        int intGd15 = Integer.parseInt(gd15);
        float floatKda = Float.parseFloat(kda);
        double newScore = calculateScore(floatCpm, intGd15, floatKda);

        var cs = new ArrayList<>(this.csPerMinute);
        var gd = new ArrayList<>(this.goldDiffAt15);
        var kd = new ArrayList<>(this.kdaScores);
        var sc = new ArrayList<>(this.scores);

        cs.add(floatCpm);
        gd.add(intGd15);
        kd.add(floatKda);
        sc.add(newScore);

        return new Stats(cs, gd, kd, sc);
    }

    /**
     * Returns a new {@code Stats} with the most recent (last) entry removed from all series.
     *
     * @return a new {@code Stats} reflecting the removal
     * @throws CommandException if there is no entry to remove (all lists are empty)
     */
    public Stats deleteLatestStats() throws CommandException {
        var cs = new ArrayList<>(this.csPerMinute);
        var gd = new ArrayList<>(this.goldDiffAt15);
        var kd = new ArrayList<>(this.kdaScores);
        var sc = new ArrayList<>(this.scores);

        if (cs.isEmpty()) {
            throw new CommandException(NOT_DELETED_MESSAGE);
        }

        cs.remove(cs.size() - 1);
        gd.remove(gd.size() - 1);
        kd.remove(kd.size() - 1);
        sc.remove(sc.size() - 1);

        return new Stats(cs, gd, kd, sc);
    }

    /**
     * Returns the current average composite score of {@link #scores}, rounded to 1 decimal place.
     */
    public float getValue() {
        return this.value;
    }

    /** @return a defensive copy of the CPM history (chronological). */
    public ArrayList<Float> getCsPerMinute() {
        return new ArrayList<>(csPerMinute);
    }

    /** @return a defensive copy of the GD15 history (chronological). */
    public ArrayList<Integer> getGoldDiffAt15() {
        return new ArrayList<>(goldDiffAt15);
    }

    /** @return a defensive copy of the KDA history (chronological). */
    public ArrayList<Float> getKdaScores() {
        return new ArrayList<>(kdaScores);
    }

    /** @return a defensive copy of the composite score history (chronological). */
    public ArrayList<Double> getScores() {
        return new ArrayList<>(scores);
    }

    /**
     * Validates the textual inputs for a single match against both shape and range constraints.
     *
     * @param cpm  CS per minute (string)
     * @param gd15 Gold difference at 15:00 (string)
     * @param kda  KDA (string)
     * @return {@code true} if all three values match the required regex patterns and semantic ranges
     */
    public static boolean isValidStats(String cpm, String gd15, String kda) {
        if (!cpm.matches(FLOAT_VALIDATION_REGEX)
                || !kda.matches(FLOAT_VALIDATION_REGEX)
                || !gd15.matches(INT_VALIDATION_REGEX)) {
            return false;
        }
        float x;
        int y;
        float z;
        try {
            x = Float.parseFloat(cpm);
            y = Integer.parseInt(gd15);
            z = Float.parseFloat(kda);
        } catch (NumberFormatException e) {
            return false;
        }

        if (x < MIN_CPM || x > MAX_CPM) {
            return false;
        }
        if (y < MIN_GD15 || y > MAX_GD15) {
            return false;
        }
        if (z < MIN_KDA || z > MAX_KDA) {
            return false;
        }

        return true;
    }

    /**
     * Returns {@code value} formatted to one decimal place (e.g., {@code "7.3"}).
     */
    @Override
    public String toString() {
        return String.format("%.1f", value);
    }

    /**
     * Equality is defined by equality of all historical series <em>and</em> the averaged value.
     * This aligns with the value semantics of the class (two stats objects represent the same history).
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Stats)) {
            return false;
        }

        Stats otherStats = (Stats) other;
        return csPerMinute.equals(otherStats.csPerMinute)
                && goldDiffAt15.equals(otherStats.goldDiffAt15)
                && kdaScores.equals(otherStats.kdaScores)
                && Float.compare(value, otherStats.value) == 0;
    }

    /**
     * Hash code derived from {@code value} only.
     * <p><b>Note:</b> If you intend to store {@code Stats} in hash-based collections and rely on full
     * history equality, consider also hashing the series (CPM/GD15/KDA/scores)
     * for consistency with {@link #equals(Object)}.
     */
    @Override
    public int hashCode() {
        return Float.hashCode(value);
    }

    /**
     * Computes a per-match composite score in the range [0, 10] using:
     * <ul>
     *   <li>Normalized KDA (capped at 3.0 → 1.0)</li>
     *   <li>Normalized CS/min (capped at 10 → 1.0)</li>
     *   <li>Logistic transform of GD15 for smooth saturation</li>
     * </ul>
     * Weights: KDA 45%, CS 35%, GD15 20%.
     *
     * @param cpm non-negative CS per minute
     * @param gd15 signed gold diff at 15:00
     * @param kda non-negative KDA
     * @return score in [0, 10]
     */
    private double calculateScore(float cpm, int gd15, float kda) {
        double kdaNorm = Math.min(kda / 3.0, 1.0);
        double csNorm = Math.min(cpm / 10.0, 1.0);
        double gdNorm = 1.0 / (1.0 + Math.exp(-gd15 / 500.0));
        double score = 10.0 * (0.45 * kdaNorm + 0.35 * csNorm + 0.20 * gdNorm);
        return Math.max(0.0, Math.min(score, 10.0));
    }

    /**
     * Computes the arithmetic mean of {@link #scores} rounded to one decimal place.
     * Returns {@code 0.0F} when there is no history.
     */
    private float calculateAverageScore() {
        if (scores.isEmpty()) {
            return 0.0F;
        }
        double total = scores.stream().reduce(0.0, Double::sum);
        double avg = total / scores.size();
        return (float) (Math.round(avg * 10.0) / 10.0);
    }
}
