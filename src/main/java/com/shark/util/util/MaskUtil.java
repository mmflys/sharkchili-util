package com.shark.util.util;

import com.google.common.collect.Lists;

import java.util.List;

public class MaskUtil {

    public static int setTrueBit(int mask, int index) {
        return mask | 1 << index;
    }

    public static int setFalseBit(int mask, int index) {
        return mask & ~(1 << index);
    }

    public static boolean trueBit(int mask, int index) {
        int result= mask & 1 << index;
        return result==1;
    }


    public static boolean falseBit(int mask, int index) {
        int result= mask & 1 << index;
        return result==0;
    }

    public static List<Integer> split(int mask){
        List<Integer> bitList= Lists.newArrayList();
        int highest=NumberUtil.highestBinaryBitOneIndex(mask);
        for (int i = 0; i < highest; i++) {
            boolean isTrue=trueBit(mask,i);
            if (isTrue){
                bitList.add(i);
            }
        }
        return bitList;
    }
}
