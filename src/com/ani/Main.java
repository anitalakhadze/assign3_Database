package com.ani;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> ls = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        Iterator itr = ls.iterator();
        while (itr.hasNext()){
            itr.remove();
        }
        System.out.println(ls);
//        String[] arr = new String[5];
//        System.out.println(arr[0] == null);
    }
}
