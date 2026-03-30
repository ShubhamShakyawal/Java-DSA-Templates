import java.util.ArrayList;
import java.util.List;

public class Main {
    // Z fuction - z[i] = length of prefix matching from index i.
    // Function to compute the Z array
    public static int[] computeZ(String s) {
        int n = s.length();
        int[] z = new int[n];
        int left = 0, right = 0;

        for (int i = 1; i < n; i++) {
            // stay within the range [left,right]. Don't go beyond the characters we are seen so far.
            if (i <= right) 
                z[i] = Math.min(right - i + 1, z[i - left]);
            
            // expand the window. i.e. more characters can match
            while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i]))
                z[i]++;
            
            // if window increased. update the variables
            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }
        }
        return z;
    }

    // Function to find all occurrences of a pattern in a text
    public static List<Integer> findPattern(String text, String pattern) {
        String combined = pattern + "$" + text; // Concatenate pattern and text with a delimiter
        int[] z = computeZ(combined);

        List<Integer> result = new ArrayList<>();
        int patternLength = pattern.length();

        // Check for pattern matches in the Z array
        for (int i = patternLength + 1; i < z.length; i++) {
            if (z[i] == patternLength) {
                result.add(i - patternLength - 1); // Calculate the starting index of the match
            }
        }

        return result;
    }

    public static void main(String[] args) {
        String text = "abracadabra";
        String pattern = "abra";

        List<Integer> matches = findPattern(text, pattern);

        if (matches.isEmpty()) {
            System.out.println("No matches found.");
        } else {
            System.out.println("Pattern found at indices: " + matches);
        }
    }
}
