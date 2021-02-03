package leetcode;

import java.util.*;

public class CutoffRanks {
    public static int countLevelUp(int cutOffRank, int[] scores) {
        Integer[] sortedScores = Arrays.stream(scores).boxed().toArray(Integer[]::new);
        Arrays.sort(sortedScores, Collections.reverseOrder());

        int lowestScore = sortedScores[cutOffRank - 1];

        int res = 0;

        for (int i : sortedScores) {
            if (i > 0 && i >= lowestScore) {
                res++;
            } else {
                break;
            }
        }
        return res;
    }
}
