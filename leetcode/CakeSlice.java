package leetcode;

import java.util.Arrays;

public class CakeSlice {
    public static int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);
        
        long hMax = helper(horizontalCuts, h);
        long vMax = helper(verticalCuts, w);
        long area =  vMax * hMax;
        int res =    (int) (area % (1e9+7));
        return res;
        
    }
    
    private static long helper(int[] arr, int len){
        long res = 0;
        for(int i = 0; i < arr.length; i++){
            int diff = 0;
            if(i==0){
                diff = arr[0];
            }
            else{
                diff = arr[i] - arr[i-1];
            }
                
            res = Math.max(res, diff);
        }
        res = Math.max(res, len - arr[arr.length-1]);
        return res;
    }
}