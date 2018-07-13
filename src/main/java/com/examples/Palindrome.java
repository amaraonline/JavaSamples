package com.examples;

public class Palindrome {
    public static void main(String args[]){
        int numberToReverse = 123;

        System.out.println("Reverse number is : "+reverse(numberToReverse));
        System.out.println("1221 is palindram or not : "+isPalindram(1221));
    }

    private static int reverse(int numberToReverse) {
        int reverseNumber=0;
        while(numberToReverse !=0){
            reverseNumber = reverseNumber*10+numberToReverse%10;
            numberToReverse = numberToReverse/10;
        }
        return reverseNumber;
    }

    private static boolean isPalindram(int number){
        return number == reverse(number);
    }
}
