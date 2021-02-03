package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Test;

public class StorageOptimization {

    public static int findOptimization(int n, int m, int[] h, int[] v) {
        int hMax = helper(n, h);
        int vMax = helper(m, v);

        return (int) (((long) hMax * (long) vMax) % (1e9 + 7));
    }

    private static int helper(int len, int[] removedSeparator) {
        HashSet<Integer> removedSeparatorSet = new HashSet<>();
        for (int i : removedSeparator) {
            removedSeparatorSet.add(i);
        }
        // upper border (0) + positions of existing separators + lower border(len + 1)
        int[] separator = new int[len - removedSeparator.length + 2];
        int index = 0;
        for (int i = 0; i <= len + 1; i++) {
            if (!removedSeparatorSet.contains(i)) {
                separator[index++] = i;
            }
        }

        int res = 1;
        for (int i = 1; i < separator.length; i++) {
            int diff = separator[i] - separator[i - 1];
            res = Math.max(res, diff);
        }
        return res;
    }

    @Test
    public void test() {
        int n = 6;
        int m = 6;
        int[] h = { 4, 5 };
        int[] v = { 2 };
        assertEquals(6, findOptimization(n, m, h, v));
    }

}
