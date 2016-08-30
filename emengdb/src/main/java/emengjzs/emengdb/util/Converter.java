/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.util;

/**
 * Created by emengjzs on 2016/8/30.
 */
public class Converter {

    // little endian
    public static byte[] convertL(int int_) {
        byte[] bs = new byte[4];
        for (int i = 0; i < 4; i ++) {
            bs[i] = (byte) (0xFF & (int_ >> ((i) << 3)));
        }
        return bs;
    }

    // big endian
    public static byte[] convertB(int int_) {
        byte[] bs = new byte[4];
        for (int i = 0; i < 4; i ++) {
            bs[i] = (byte) (0xFF & (int_ >> ((3 - i) << 3)));
        }
        return bs;
    }
}
