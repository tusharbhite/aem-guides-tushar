package com.tushar.aem.guides.core.utils;

public final class StringUtils {

    // Private constructor to prevent instantiation
    private StringUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Checks if the CharSequence is null or empty.
     *
     * @param cs the CharSequence to check
     * @return true if the CharSequence is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Concatenates two strings with a space in between.
     *
     * @param s1 the first string
     * @param s2 the second string
     * @return the concatenated string
     */
    public static String concatenateWithSpace(String s1, String s2) {
        if (isNullOrEmpty(s1)) {
            return s2;
        }
        if (isNullOrEmpty(s2)) {
            return s1;
        }
        return s1 + " " + s2;
    }
}
