package com.example.song.greathua;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by 33105 on 2018/4/17.
 */

public class arithmetic {
    @Test
    public void testAddArray() {
        int[] arr1 = new int[]{1, 3, 5, 7, 8, 10};
        int[] arr2 = new int[]{2, 4, 6, 9, 11, 12};
        addArray(arr1, arr2);
    }

    @Test
    public void testGetIndex(){
        int[] arr = new int[]{1,4,8,9,20};
        System.out.println(Arrays.toString(getAddIndex(5,arr))) ;
        System.out.println(Arrays.toString(getAddIndex2(13,arr))) ;
    }
    @Test
    public void testNonRepet(){
        System.out.println( nonRepetStr("abcdaedfxb"));
    }

    /**
     * 求Str中最长不重复的子串
     * @param s
     * @return
     */
    public int nonRepetStr(String s){
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            // try to extend the range [i, j]
            if (!set.contains(s.charAt(j))){
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - i);
            }
            else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    /**
     * 给一个数组arr ，一个key
     * 查找两个下标 i , j ，使得arr[i]+ arr[j] = key
     */
    public int[] getAddIndex(int key, int[] array) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            map.put(array[i], i);
        }
        for (int i = 0; i < array.length; i++) {
            if (map.get(key - array[i]) != null) {
                return new int[]{i, map.get(key - array[i])};
            }
        }
        return null;
    }
    public int[] getAddIndex2(int key, int[] array) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            if (map.get(key - array[i]) != null) {
                return new int[]{i, map.get(key - array[i])};
            }
            map.put(array[i], i);
        }
        return null;
    }


    /**
     * 两个有序数组相加
     */
    public void addArray(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        int index1 = 0;
        int index2 = 0;
        int i = 0;

        while (index1 < arr1.length && index2 < arr2.length) {
            if (arr1[index1] < arr2[index2]) {
                result[i] = arr1[index1++];
            } else {
                result[i] = arr2[index2++];
            }
            i++;
        }
        while (index1 < arr1.length) {
            result[i++] = arr1[index1++];
        }

        while (index2 < arr2.length) {
            result[i++] = arr2[index2++];
        }
        System.out.println(Arrays.toString(result));
    }
}
