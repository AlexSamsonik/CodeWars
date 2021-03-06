package com.codewars.fundamentals.number;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validate Credit Card Number
 * In this Kata, you will implement the Luhn Algorithm, which is used to help validate credit card numbers.
 * Given a positive integer of up to 16 digits, return true if it is a valid credit card number, and false if it is not.
 * Here is the algorithm:
 * Double every other digit, scanning from right to left, starting from the second digit (from the right).
 * Another way to think about it is: if there are an even number of digits, double every other digit starting
 * with the first;
 * if there are an odd number of digits, double every other digit starting with the second:
 * 1714 ==> [1*, 7, 1*, 4] ==> [2, 7, 2, 4]
 * 12345 ==> [1, 2*, 3, 4*, 5] ==> [1, 4, 3, 8, 5]
 * 891 ==> [8, 9*, 1] ==> [8, 18, 1]
 * If a resulting number is greater than 9, replace it with the sum of its own digits (which is the same as subtracting 9 from it):
 * [8, 18*, 1] ==> [8, (1+8), 1] ==> [8, 9, 1]
 * or:
 * [8, 18*, 1] ==> [8, (18-9), 1] ==> [8, 9, 1]
 * Sum all of the final digits:
 * [8, 9, 1] ==> 8 + 9 + 1 = 18
 * Finally, take that sum and divide it by 10. If the remainder equals zero, the original credit card number is valid.
 * 18 (modulus) 10 ==> 8 , which is not equal to 0, so this is not a valid credit card number
 */
public class Validate {
    public static boolean validate(String n) {
        List<Integer> intList = sti(n);
        if (intList.size() % 2 == 0) {
            for (int i = 0; i < intList.size(); i = i + 2) {
                intList.set(i, intList.get(i) * 2);
            }
        }
        if (intList.size() % 2 != 0) {
            for (int i = 1; i < intList.size(); i = i + 2) {
                intList.set(i, intList.get(i) * 2);
            }
        }
        return digitList(intList).stream().mapToInt(Integer::intValue).sum() % 10 == 0;
    }

    private static List<Integer> sti(String n) {
        String[] str = n.split("");
        return Arrays.stream(str).map(Integer::valueOf).collect(Collectors.toList());
    }

    private static List<Integer> digitList(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > 9) {
                list.set(i, list.get(i) - 9);
            }
        }
        return list;
    }

    /**
     * Best solution on CodeWars
     */
    public static boolean validateCodeWars(final String line) {
        boolean otherDigit = false;
        int sum = 0;

        // From the rightmost digit, which is the check digit, moving left
        for (int i = line.length() - 1; i >= 0; --i) {
            // convert char to number
            int digit = line.charAt(i) - '0';

            // double the value of every second digit
            if (otherDigit) {
                digit *= 2;
            }
            otherDigit ^= true;

            // if the product of this doubling operation is greater than 9
            // (for example, 7x2=14), then sum the digits of the products
            // (for example, 12:1+2=3, 14:1+4=5)
            if (digit > 9) {
                digit = digit / 10 + digit;
            }

            // Take the sum of all the digits
            sum += digit;
        }

        // If the sum modulo 10 is equal to 0 (if the sum ends in zero) then,
        // according to the Luhn formula, the number is valid; otherwise, it is not valid.
        return sum % 10 == 0;
    }
}