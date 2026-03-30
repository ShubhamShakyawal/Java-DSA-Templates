public class Main {

    // Method to perform KMP search
    public static int[] KMPSearch(String pattern, String text) {
        int m = pattern.length(), n = text.length();

        // Compute the prefix-suffix table (LPS array)
        int[] lps = computeLPSArray(pattern);

        int i = 0; // index for text
        int j = 0; // index for pattern

        // List to store starting indices of pattern matches
        java.util.ArrayList<Integer> matchIndices = new java.util.ArrayList<>();

        while (i < n) {
            // not matching
            while(i < n && pattern.charAt(j) != text.charAt(i)) i++;
            // matching
            while (i < n && j < m && pattern.charAt(j) == text.charAt(i)) {
                i++;
                j++;
            }
            // If a match is found
            if (j == m) 
                matchIndices.add(i - j); // Add match index

            // Mismatch after j matches
            j = lps[Math.max(0,j - 1)];
        }

        // Convert list of match indices to array and return
        return matchIndices.stream().mapToInt(Integer::intValue).toArray();
    }

    // Method to compute the LPS (Longest Prefix Suffix) array
    private static int[] computeLPSArray(String pattern) {
        int m = pattern.length();
        int[] lps = new int[m];

        int j = 0; // Length of the previous longest prefix suffix
        int i = 1; // Start from the second character

        while (i < m) {
            while (i < m && pattern.charAt(i) == pattern.charAt(j)) {
                j++;
                lps[i] = j;
                i++;
            } 
            j = lps[Math.max(0,j-1)]; // Reset j using the previous LPS value
            i++;
        }

        return lps;
    }

    // Main method for testing
    public static void main(String[] args) {
        String text = "ababcabcabababd";
        String pattern = "ababd";

        int[] result = KMPSearch(pattern, text);

        if (result.length == 0) {
            System.out.println("Pattern not found in the text.");
        } else {
            System.out.println("Pattern found at indices: ");
            for (int index : result) {
                System.out.print(index + " ");
            }
        }
    }
}
