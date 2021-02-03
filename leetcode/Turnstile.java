package leetcode;
// https://leetcode.com/discuss/interview-question/699973/
// public static void main(String args[]){
 

//     int[] arrTime = {0, 0, 1, 5};
//     int[] direction = {0, 1, 1, 0};
//     int[] expected = {2, 0, 1, 5};
//     Turnstile.test(arrTime, direction, expected, 1);
  
//     int[] arrTime2 = {1, 2, 4};
//     int[] direction2 = {0, 1, 1};
//     int[] expected2 = {1, 2, 4};
//     Turnstile.test(arrTime2, direction2, expected2, 2);
    
//     int[] arrTime3 = {1, 1};
//     int[] direction3 = {1, 1};
//     int[] expected3 = {1, 2};
//     Turnstile.test(arrTime3, direction3, expected3, 3);
  
//     int[] arrTime4 = {1, 1, 3, 3, 4, 5, 6, 7, 7};
//     int[] direction4 = {1, 1, 0, 0, 0, 1, 1, 1, 1};
//     int[] expected4 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
//     Turnstile.test(arrTime4, direction4, expected4, 4);
//     //20134
//     int[] arrTime5 = {0, 0, 1, 3,3};
//     int[] direction5 = {0, 1, 1, 0,1};
//     int[] expected5 = {2,0,1,3,4};
//     Turnstile.test(arrTime5, direction5, expected5, 5);


// }

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Turnstile {

    public static void test(int[] arrTime, int[] direction, int[] expected, int index) {
        System.out.println("Run test case " + index);
        int[] res = getTimes(arrTime, direction);
        System.out.print("Return result: ");
        for (int i : res)
            System.out.print(i);
        System.out.println();
        
        for (int i : expected)
            System.out.print(i);
        System.out.println();
    }

    static class Person{
        int direction;
        int index;
        public Person(int direction, int index){
            this.direction = direction;
            this.index = index;
        }
    }

    public static int[] getTimes(int[] time, int[] direction){
        if(time.length ==0) return new int[0];
        int[] res = new int[time.length];
        PriorityQueue<Integer> q = new PriorityQueue<>();
        HashMap<Integer, List<Person>> map = new HashMap<>();

        for(int i = 0; i< time.length;i++){
            Person p = new Person(direction[i],i);
            if(!map.containsKey(time[i])){
                q.add(time[i]);
                map.put(time[i], new LinkedList<>());
            }
            map.get(time[i]).add(p);
        }
        int lastTime = -10;
        int lastDirection = 1;

        while(!q.isEmpty()){
            int curTime = q.poll();
            List<Person> people = map.get(curTime);
            
            PriorityQueue<Person> queue;
            if(curTime - lastTime == 1 && lastDirection == 0){
                //0 first
                queue =  new PriorityQueue<>(people.size(), (Person a, Person b) -> {
                    if(a.direction != b.direction){
                        return a.direction - b.direction;
                    }else{
                        return a.index - b.index;
                    }
                });
            }else{
                //1 first
                queue =  new PriorityQueue<>(people.size(), (Person a, Person b) -> {
                    if(a.direction != b.direction){
                        return b.direction - a.direction;
                    }else{
                        return a.index - b.index;
                    }
                });

            }
            for(Person p : people){
                queue.add(p);
            }
            Person curPerson = queue.poll();
            res[curPerson.index] = curTime;
            lastTime = curTime;
            lastDirection = curPerson.direction;
            people.remove(curPerson);
            if(!people.isEmpty()){
                curTime += 1;
                if(!map.containsKey(curTime)){
                    q.add(curTime);
                    map.put(curTime, people);
                }else{
                    map.get(curTime).addAll(people);
                }
            }
            

        }
        return res;

    }
    
}
