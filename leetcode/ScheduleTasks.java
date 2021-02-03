package leetcode;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Test;

public class ScheduleTasks {

    public static int calculateTime(int num, int[] power, int tasks) {
        PriorityQueue<Integer> powerQueue = new PriorityQueue<>(power.length, (Integer a, Integer b) -> {
            return b - a;
        });
        for (int i : power) {
            powerQueue.add(i);
        }
        int res = 0;
        while (tasks > 0) {
            int cur = powerQueue.poll();
            tasks -= cur;
            cur /= 2;
            powerQueue.add(cur);
            res++;
        }
        return res;
    }

    @Test
    public void test() {
        int num = 5;
        int[] power = { 4, 2, 8, 3, 5 };
        int tasks = 19;
        assertEquals(4, calculateTime(num, power, tasks));
    }
}
