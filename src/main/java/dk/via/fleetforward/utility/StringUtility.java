package dk.via.fleetforward.utility;

public class StringUtility {
    public static boolean isNullOrEmpty(String input)
    {
        return input == null || input.isEmpty();
    }

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
