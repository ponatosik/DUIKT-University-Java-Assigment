package com.bondarenko.universityAssigment.lab4;

public class Palindrome {
    public static boolean isPalindrome(String str) {
        if(str == null) {
            return true;
        }

        String reversed = new StringBuilder(str).reverse().toString();
        return str.compareToIgnoreCase(reversed) == 0;
    }
}
