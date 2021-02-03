package leetcode;
// https://aonecode.com/interview-question/maximum-bounded-array

public class MaxBoundedArray {

    public static int[] findMaxBoundedArray(int n, int lowerBound, int upperBound) {
        // (10-10)*2 +1
        // [10]
        if (n < 3 || n > (upperBound - lowerBound) * 2 + 1) {
            return null;
        }

        int[] res = new int[n];
        int range = upperBound - lowerBound + 1;
        if (range - 1 >= n) {
            // upper -1 , upper, upper-1, upper-2 , upper -3....
            res[0] = upperBound - 1;
            res[1] = upperBound;
            for (int i = 2; i < res.length; i++) {
                res[i] = res[i - 1] - 1;
            }
        } else {
            res[res.length - 1] = lowerBound;
            int i = res.length - 2;
            for (; i >= 0; i--) {
                res[i] = res[i + 1] + 1;
                if (res[i] == upperBound) {
                    break;
                }
            }
            i--;
            for (; i >= 0; i--) {
                res[i] = res[i + 1] - 1;
            }
        }
        return res;
    }
}
