package leetcode;

public class UtilizationChecks {
    public static int calculateInstance(int instances, int[] averageUtil) {
        for (int i = 0; i < averageUtil.length; i++) {
            if (averageUtil[i] < 25) {
                if (instances > 1) {
                    instances = (instances + 1) / 2;
                    i += 9;
                }
            } else if (averageUtil[i] > 60) {
                if (instances <= 1e8) {
                    instances *= 2;
                    i += 9;
                }
            }
        }
        return instances;
    }
}
