package leetcode;

public class KMPPreProcessing {

    public static int[] kMPPreProcessing(char[] pattern){
        int[] res = new int[pattern.length];
        res[0] = 0;
        int j = 0;
        for(int i =1; i<res.length;){
            char ichar = pattern[i];
            char jchar = pattern[j];
            if(ichar == jchar){
                res[i] = j+1;
                i++;
                j++;
            }else{
                if(j==0){
                    res[i] = 0;
                    i++;
                }else{
                    j = res[j-1];
                }
            }
        }
        return res;
    }

    //return index of first occurance; if not contains, return -1;
    public static int subString(String text, String pattern){
        int[] arr = kMPPreProcessing(pattern.toCharArray());
        int i =0;
        int j=0;
        while( i<text.length() && j< pattern.length()){
            if(text.charAt(i) == pattern.charAt(j)){
                i++;
                j++;
            }else{
                if(j==0){
                    i++;
                }else{
                    j=arr[j-1];
                }
            }

        }
        if(j==pattern.length()){
            return i-pattern.length();
        }else return  -1;

    }
    
}


// public static void main(String[] args){
//     char[] chars = "abcaby".toCharArray();
//     int[] res = KMPPreProcessing.kMPPreProcessing(chars);
//     for(int i : res)
//         System.out.print(i);

// }

// public static void main(String[] args){
//     String text = "abxabcabcaby";
//     String pattern = "abcaby";
//     System.out.print(KMPPreProcessing.subString(text, pattern));

// }
