package dk.via.fleetforward.utility;

/**
 * @author Mario
 * @version 1.0.0
 * Utility class for common operations on strings.
 */
public class StringUtility {
    /**
     * Checks if a given string is null or empty.
     *
     * @param input the string to check
     * @return true if the input string is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String input)
    {
        return input == null || input.isEmpty();
    }

    /**
     * Checks if the input string matches any of the strings provided in the potentialMatches array.
     *
     * @param input the string to compare against the potential matches
     * @param potentialMatches the array of strings to compare with the input
     * @return true if the input matches any string in potentialMatches, false otherwise
     */
    public static boolean equalsAny(String input, String... potentialMatches)
    {
        if (input == null || potentialMatches.length == 0) return false;

        for (String match : potentialMatches)
        {
            if (input.equals(match)) return true;
        }
        return false;
    }
}
