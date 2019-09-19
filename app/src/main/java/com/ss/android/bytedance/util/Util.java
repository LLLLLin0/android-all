package com.ss.android.bytedance.util;

import java.util.List;

/**
 * Created on 2019-09-19.
 * linzhipeng.1996@bytedance.com
 */
public class Util {

    public static void swapListValue(List<String> list, int indexA, int indexB) {
        String temp = list.get(indexA);
        list.set(indexA, list.get(indexB));
        list.set(indexB, temp);
    }
}
