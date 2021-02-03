package leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FiveStarSellers {
    public int fiveStarReviews(int[][] productRatings, int ratingsThreshold) {
        int productCount = productRatings.length;
        // target total score of products
        double targetScore = (double) ratingsThreshold * (double) productCount / 100;

        // get inital score of all products
        double initialScore = 0;
        for (int[] rating : productRatings) {
            initialScore += caculatePercentage(rating);
        }

        int res = 0;

        while (initialScore < targetScore) {
            // find the product that can bring most score improvement by adding 1 five star
            // review
            int maxImproveIndex = findMaxImprove(productRatings);
            initialScore -= caculatePercentage(productRatings[maxImproveIndex]);
            productRatings[maxImproveIndex][0] += 1;
            productRatings[maxImproveIndex][1] += 1;
            initialScore += caculatePercentage(productRatings[maxImproveIndex]);
            res++;
        }
        return res;
    }

    private int findMaxImprove(int[][] productRatings) {
        int res = 0;
        double maxImprove = 0;
        for (int i = 0; i < productRatings.length; i++) {
            int[] rating = productRatings[i];
            int[] improve = { rating[0] + 1, rating[1] + 1 };
            double diff = caculatePercentage(improve) - caculatePercentage(rating);
            if (diff > maxImprove) {
                res = i;
                maxImprove = diff;
            }
        }
        return res;

    }

    private double caculatePercentage(int[] rating) {
        return ((double) rating[0]) / ((double) rating[1]);
    }

    @Test
    public void test() {
        int ratingsThreshold = 77;
        int[][] productRatings = { { 4, 4 }, { 1, 2 }, { 3, 6 } };
        assertEquals(3, fiveStarReviews(productRatings, ratingsThreshold));

    }

}
