package leetcode;

import java.util.*;

public class DistinctSubstring {
    
    public static List<String> findSubstring(String text, int k){
        List<String> res = new LinkedList<>();
        char[]  chars = text.toCharArray();
        if (chars.length == 0) return res;
        HashSet<Character> set = new HashSet<>();
        int start = 0;
        int last = 0;
        int tocheck = 1;
        set.add(chars[0]);
        while(tocheck < chars.length){
            char cur = chars[tocheck];
            if(!set.contains(cur)){
                set.add(cur);
                last = tocheck;
                if(last - start +1 == k){
                    //add to result
                    String tmp = text.substring(start, last+1);
                    if(!res.contains(tmp)){
                        res.add(tmp);
                    }
                    set.remove(chars[start++]);
                }
                tocheck++;
            }else{
                set.remove(chars[start++]);
                if(last <start){
                    last = start;
                }
            }
        }
        return res;
    }
}
