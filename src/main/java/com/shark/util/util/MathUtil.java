package com.shark.util.util;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Match util
 *
 * @Author: SuLiang
 * @Date: 2018/10/23 0023
 */
public class MathUtil {

    /**
     * Calculate sum of n elements.
     *
     * @param es elements
     * @return the sum of the elements
     */
    public static Integer sum(int... es) {
        int sum = 0;
        for (int e : es) {
            sum += e;
        }
        return sum;
    }

    /**
     * Calculate log value
     *
     * @param base base number
     * @param x    real number
     * @return logarithmic
     */
    public static double log(int base, int x) {
        return Math.log(x) / Math.log(base);
    }

    private static void findConsecutiveNumbers(int startIndex, List<List<Integer>> consecutiveNumbers, List<Integer> inConsecutiveNumbers, int... numbers) {
        boolean isFirstIndex = consecutiveNumbers.isEmpty() && inConsecutiveNumbers.isEmpty();
        boolean isLastIndex = startIndex == numbers.length - 1;
        boolean preConsecutive = !isFirstIndex && Math.abs(numbers[startIndex] - numbers[startIndex - 1]) == 1;
        boolean postConsecutive = !isLastIndex && Math.abs(numbers[startIndex] - numbers[startIndex + 1]) == 1;
        boolean onlyOneIndex = isFirstIndex & isLastIndex;
        // Add numbers
        if (onlyOneIndex) {
            inConsecutiveNumbers.add(numbers[startIndex]);
        } else if (preConsecutive) {
            consecutiveNumbers.get(consecutiveNumbers.size() - 1).add(numbers[startIndex]);
        } else if (postConsecutive) {
            consecutiveNumbers.add(Lists.newArrayList(numbers[startIndex]));
        } else {
            inConsecutiveNumbers.add(numbers[startIndex]);
        }
        // Recursive
        if (!isLastIndex) {
            findConsecutiveNumbers(++startIndex, consecutiveNumbers, inConsecutiveNumbers, numbers);
        }
    }
}
