package leetcode;
// https://leetcode.com/discuss/interview-question/699973/

import static org.junit.Assert.assertArrayEquals;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import org.junit.Test;

public class Turnstile {

    @Test
    public void test() {
        int[] arrTime = { 0, 0, 1, 5 };
        int[] direction = { 0, 1, 1, 0 };
        int[] expected = { 2, 0, 1, 5 };
        int[] res = getTimes(arrTime, direction);
        assertArrayEquals(expected, res);
        int[] arrTime2 = { 1, 2, 4 };
        int[] direction2 = { 0, 1, 1 };
        int[] res2 = getTimes(arrTime2, direction2);
        int[] expected2 = { 1, 2, 4 };
        assertArrayEquals(expected2, res2);
        int[] arrTime3 = { 1, 1 };
        int[] direction3 = { 1, 1 };
        int[] res3 = getTimes(arrTime3, direction3);
        int[] expected3 = { 1, 2 };
        assertArrayEquals(expected3, res3);
        int[] arrTime4 = { 1, 1, 3, 3, 4, 5, 6, 7, 7 };
        int[] direction4 = { 1, 1, 0, 0, 0, 1, 1, 1, 1 };
        int[] res4 = getTimes(arrTime4, direction4);
        int[] expected4 = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        assertArrayEquals(expected4, res4);
        int[] arrTime5 = { 0, 0, 1, 3, 3 };
        int[] direction5 = { 0, 1, 1, 0, 1 };
        int[] res5 = getTimes(arrTime5, direction5);
        int[] expected5 = { 2, 0, 1, 3, 4 };
        assertArrayEquals(expected5, res5);
    }

    static class Person {
        int direction;
        int index;

        public Person(int direction, int index) {
            this.direction = direction;
            this.index = index;
        }
    }

    public static int[] getTimes(int[] time, int[] direction) {
        if (time.length == 0)
            return new int[0];
        int[] res = new int[time.length];
        // keep tracking of time, put smallest time on the top
        PriorityQueue<Integer> q = new PriorityQueue<>();
        // sotres key = time, value = a list of Person that is waiting on this time
        HashMap<Integer, List<Person>> map = new HashMap<>();
        // initialize q and map
        for (int i = 0; i < time.length; i++) {
            Person p = new Person(direction[i], i);
            if (!map.containsKey(time[i])) {
                q.add(time[i]);
                map.put(time[i], new LinkedList<>());
            }
            map.get(time[i]).add(p);
        }
        // stores last passed timestamp and direction.
        int lastTime = -10;
        int lastDirection = 1;

        while (!q.isEmpty()) {
            int curTime = q.poll();
            // currnt waiting people
            List<Person> people = map.get(curTime);
            // queue will sort people. Depends on last direction, it may sort differently
            PriorityQueue<Person> queue;
            if (curTime - lastTime == 1 && lastDirection == 0) {
                // 0 first
                queue = new PriorityQueue<>(people.size(), (Person a, Person b) -> {
                    if (a.direction != b.direction) {
                        return b.direction - a.direction;
                    } else {
                        return b.index - a.index;
                    }
                });
            } else {
                // 1 first
                queue = new PriorityQueue<>(people.size(), (Person a, Person b) -> {
                    if (a.direction != b.direction) {
                        return a.direction - b.direction;
                    } else {
                        return b.index - a.index;
                    }
                });
            }
            // use queue to find most priority people
            for (Person p : people) {
                queue.add(p);
                if (queue.size() == 2) {
                    queue.poll();
                }
            }
            // people on top of queue can go in cur time
            Person curPerson = queue.poll();
            res[curPerson.index] = curTime;
            lastTime = curTime;
            lastDirection = curPerson.direction;
            // remove curPerson from waiting list of this second
            people.remove(curPerson);
            // set time+1 for other people. They will be processed in t+1 loop.
            if (!people.isEmpty()) {
                curTime += 1;
                if (!map.containsKey(curTime)) {
                    q.add(curTime);
                    map.put(curTime, people);
                } else {
                    map.get(curTime).addAll(people);
                }
            }
        }
        return res;

    }

}
