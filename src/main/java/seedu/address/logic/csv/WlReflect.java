package seedu.address.logic.csv;

import java.lang.reflect.Method;


/**
 * Reflection-based helper for retrieving win/loss statistics from {@code Person} or {@code Team} objects.
 * <p>
 * Provides a lightweight, dependency-free way to access methods such as {@code getWins()} and
 * {@code getLosses()} without introducing compile-time dependencies. If those methods are absent,
 * the helper returns {@code 0} by default.
 */
public final class WlReflect {
    private WlReflect() {
        // utility class; prevent instantiation
    }

    /**
     * Attempts to invoke {@code getWins()} on the given object reflectively.
     * Returns {@code 0} if the method is not present or invocation fails.
     *
     * @param o the target object
     * @return the number of wins, or {@code 0} if unavailable
     */
    public static int wins(Object o) {
        return invokeInt(o, "getWins", 0);
    }

    /**
     * Attempts to invoke {@code getLosses()} on the given object reflectively.
     * Returns {@code 0} if the method is not present or invocation fails.
     *
     * @param o the target object
     * @return the number of losses, or {@code 0} if unavailable
     */
    public static int losses(Object o) {
        return invokeInt(o, "getLosses", 0);
    }

    /**
     * Invokes a no-argument method reflectively on the given object and returns an integer result.
     * If the method is missing, inaccessible, or fails, returns the provided default value.
     *
     * @param o    the target object
     * @param name the method name to invoke
     * @param def  the default value to return if invocation fails
     * @return the integer result of the method, or {@code def} on failure
     */
    private static int invokeInt(Object o, String name, int def) {
        try {
            Method m = o.getClass().getMethod(name);
            Object v = m.invoke(o);
            return (v instanceof Integer) ? (Integer) v : def;
        } catch (Throwable t) {
            return def; // method not present yet -> treat as 0
        }
    }
}

