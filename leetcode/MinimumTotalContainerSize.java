package leetcode;

//dp[0] = [10,10.20,20,20,20,20] max of first j items
//dp[1] = [-1, (10+2 =12), 30, ]
//dp[i][j] = Math.min(dp[i-1][j-1] + p[j], dp[i-1][j-2] + Math.max(p[j], p[j-1])..) 
public class MinimumTotalContainerSize {
    public static int findMin(int[] p, int d) {
        int items = p.length;
        int[][] dp = new int[d][items];
        for (int j = 0; j < items; j++) {
            dp[0][j] = j == 0 ? p[0] : Math.max(dp[0][j - 1], p[j]);
        }
        for (int i = 1; i < d; i++) { // i : move items in i days
            for (int j = i; j < items; j++) { // j : move [0,j] items in i days
                dp[i][j] = Integer.MAX_VALUE;
                int curMax = p[j]; // curMax : max size of [k,j)
                for (int k = j - 1; k >= i - 1; k--) {
                    // k : move [0,k) items in i-1 days
                    int size = dp[i - 1][k] + curMax;
                    curMax = Math.max(curMax, p[k]);
                    dp[i][j] = Math.min(dp[i][j], size);
                }
            }
        }
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < items; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.println();
        }
        return dp[d - 1][items - 1];
    }
}
