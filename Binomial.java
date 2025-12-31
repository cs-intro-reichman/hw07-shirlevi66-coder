/** Computes the binomial(n,k) function. */
public class Binomial {

    public static void main(String[] args) {
        //// Uncomment the version of binomial that you want to test

        // System.out.println(binomial1(Integer.parseInt(args[0]), Integer.parseInt(args[1])));

        // System.out.println(binomial(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
    }

    // Computes the Binomial function, basic version.
    public static int binomial1(int n, int k) {
        if (k > n) return 0;
        if (k == 0 || k == n) return 1;
        return binomial1(n - 1, k) + binomial1(n - 1, k - 1);
    }

    // Computes the Binomial function, efficiently (memoization)
    public static int binomial(int n, int k) {
        if (k > n) return 0;

        int[][] memo = new int[n + 1][k + 1];

        // fill with -1
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= k; j++) {
                memo[i][j] = -1;
            }
        }

        return binomial(n, k, memo);
    }

    private static int binomial(int n, int k, int[][] memo) {
        if (k > n) return 0;
        if (k == 0 || k == n) return 1;

        if (memo[n][k] != -1) {
            return memo[n][k];
        }

        memo[n][k] = binomial(n - 1, k, memo) + binomial(n - 1, k - 1, memo);
        return memo[n][k];
    }
}
