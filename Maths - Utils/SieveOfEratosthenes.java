import java.util.ArrayList;

class SieveOfEratosthenes {
    void sieveOfEratosthenes(int n)  {
        // Create a boolean array "prime[0..n]" and
        // initialize all entries it as true. A value in
        // prime[i] will finally be false if i is Not a
        // prime, else true.
        boolean prime[] = new boolean[n + 1];
        for (int i = 2; i <= n; i++)
            prime[i] = true;

        for (int p = 2; p * p <= n; p++) {
            // If prime[p] is not changed, then it is a
            // prime
            if (prime[p] == true) {
                // Update all multiples of p greater than or
                // equal to the square of it numbers which
                // are multiple of p and are less than p^2
                // are already been marked.
                for (int i = p * p; i <= n; i += p)
                    prime[i] = false;
            }
        }

        // Print all prime numbers
        for (int i = 2; i <= n; i++) {
            if (prime[i] == true)
                System.out.print(i + " ");
        }
    }
    static int MAXN = Integer.MAX_VALUE;

    static int spf[] = new int[MAXN];

    // Calculating SPF (Smallest Prime Factor) for every
    // number till MAXN.
    // Time Complexity : O(nloglogn)
    static void sieve()
    {
        for(int i=1;i<MAXN;i++)
            spf[i] = i; // spf of every no is itself

        for(int i=2;i<MAXN;i++) {
            if(spf[i] == i) {
                for(int j=2*i;j<MAXN;j+=i) {
                    if(spf[j] == j)
                        spf[j] = i;
                }
            }
        }
    }

    // A O(log n) function returning primefactorization
    // by dividing by smallest prime factor at every step
    static ArrayList<Integer> getFactorization(int x)
    {
        ArrayList<Integer> ret = new ArrayList<>();
        while (x != 1) {
            ret.add(spf[x]);
            x = x / spf[x];
        }
        return ret;
    }
}
