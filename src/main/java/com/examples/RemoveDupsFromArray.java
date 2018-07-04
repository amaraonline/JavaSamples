package com.examples;

public class RemoveDupsFromArray {
    public static void main(String[] args) {
        int[] a = { 1, 2, 3, 4,0,0,5, 6, 1, 2, 3, 4, 5, 6, 3, 1, 4, 52, 1, 45 };
        removeDups(a);
    }

private static void removeDups(int[] a) {
    int[] result = new int[a.length];
    int j = 0;
    int count = 0;
    boolean zeroExist = false;
    for (int i : a) {
        if(i==0 && !zeroExist){
            result[j++] = i;
            zeroExist = true;
            count++;
        }
        if (!isExist(result, i)) {
            result[j++] = i;
            count++;
        }
    }
    System.out.println(count + "_____________");
    for (int i=0;i<count;i++) {
        System.out.println(result[i]);
    }

    // return result;
}

private static boolean isExist(int[] result, int i) {
    for (int j : result) {
        if (j == i) {
            return true;
        }
    }
    return false;
}
}