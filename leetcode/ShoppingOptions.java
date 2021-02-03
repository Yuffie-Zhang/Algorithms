package leetcode;

// A customer wants to buy a pair of jeans, a pair of shoes, a skirt, and a top but has a limited budget in dollars. Given different pricing options for each product, determine how many options our customer has to buy 1 of each product. You cannot spend more money than the budgeted amount.

// Function Description:
// Complete the getNumberOfOptions function in the editor below. The function must return an integer which represents the number of options present to buy the four items.

// getNumberOfOptions has 5 parameters:
// int[] priceOfJeans: An integer array, which contains the prices of the pairs of jeans available.
// int[] priceOfShoes: An integer array, which contains the prices of the pairs of shoes available.
// int[] priceOfSkirts: An integer array, which contains the prices of the skirts available.
// int[] priceOfTops: An integer array, which contains the prices of the tops available.
// int dollars: the total number of dollars available to shop with.

// Constraints
// 1 ≤ a, b, c, d ≤ 10^3
// 1 ≤ dollars ≤ 10^9
// 1 ≤ price of each item ≤ 10^9
// Note: a, b, c and d are the sizes of the four price arrays

// Example
// Input:
// priceOfJeans = [2, 3]
// priceOfShoes = [4]
// priceOfSkirts = [2, 3]
// priceOfTops = [1, 2]
// budgeted = 10

// Output:
// 4

// Explanation:
// The customer must buy shoes for 4 dollars since there is only one option. This leaves 6 dollars to spend on the other 3 items. Combinations of prices paid for jeans, skirts, and tops respectively that add up to 6 dollars or less are [2, 2, 2], [2, 2, 1], [3, 2, 1], [2, 3, 1]. There are 4 ways the customer can purchase all 4 items.


class GetNumberOfOptions{
    public static int getNumberOfOptions(int[] priceOfJeans, int[] priceOfShoes,int[] priceOfSkirts,int[] priceOfTops,int dollars) {
        int res = 0;
        for(int i = 0;i<priceOfJeans.length;i++){
            int firstLevel = priceOfJeans[i];
            if(firstLevel >= dollars) break;
            for(int j = 0;j<priceOfShoes.length;j++){
                int secondLevel = firstLevel + priceOfShoes[j];
                if(secondLevel >= dollars) break;
                for(int m = 0;m<priceOfSkirts.length;m++){
                    int thirdLevel = secondLevel + priceOfSkirts[m];
                    if(thirdLevel >= dollars) break;
                    for(int n = 0;n<priceOfTops.length;n++){
                        int total = thirdLevel + priceOfTops[n];
                        if(total<=dollars){
                            res++;
                        }else break;
                    }
                }
            }
        }

        return res;
    }
}
