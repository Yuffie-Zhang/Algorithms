package leetcode;

import java.util.*;

public class TransactionLogs {

    static class Pair{
        String user;
        int count;
        public Pair(String user, int count){
            this.user = user;
            this.count = count;
        }
    }
    public static String[] func(String[][] logData, int threshold){
        HashMap<String, HashSet<Integer>> map = new HashMap<>();
        for(int i=0;i<logData.length;i++){
            String[] log = logData[i];
            for(String user: log){
                if(!map.containsKey(user)){
                    map.put(user, new HashSet<Integer>());
                }
                map.get(user).add(i);
            }
        }       
        PriorityQueue<Pair> q = new PriorityQueue<>(map.size(), (Pair a, Pair b) -> {
            return b.count - a.count;
        });
        for(String user : map.keySet()){
            HashSet<Integer> set = map.get(user);
            if(set.size()>=threshold){
                q.add(new Pair(user, set.size()));
            }
        }
        String[] res = new String[q.size()];
        int index =0;
        while(!q.isEmpty()){
            res[index++] = q.poll().user;
        }
        return res;

    }
    
}
