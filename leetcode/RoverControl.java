package leetcode;

public class RoverControl {

    public static int getPosition(int size, String[] commands){
        int i = 0;
        int j = 0;
        for (String s : commands) {
            switch (s) {
                case "RIGHT":
                if (j<size-1) {
                    j = j+1;
                }

                break;
                case "UP":
                if (i > 0) {
                    i--;
                }

                    
                break;
                case "DOWN":
                if (i < size - 1) {
                    i++;
                }
                break;
                case "LEFT":
                if ( j > 0) {
                    j--;
                }
                break;
            }
        
        }
        return i * size +j ;
    }
    
}
