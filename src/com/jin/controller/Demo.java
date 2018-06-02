package com.jin.controller;

import java.util.Arrays;

/**
 * Created by wuxia on 2018/5/31.
 */
public class Demo {
    public static void main(String[] args) {
        /*

         */
        int temp=0;
        int [] arr={5,3,8,1,2,9};
        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < arr.length-1; j++) {
                if (arr[j]>arr[j+1]) {
                    temp=arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
    }
}
