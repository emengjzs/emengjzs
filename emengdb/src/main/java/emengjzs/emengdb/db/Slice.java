/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.db;

import java.nio.charset.Charset;

/**
 * Created by emengjzs on 2016/8/30.
 */
public class Slice {

    private final byte[] bytes;


    Slice(String s) {
        bytes = s.getBytes();
    }

    Slice(byte[] bytes) {
        this.bytes = bytes;
    }

    Slice() {
        bytes = new byte[0];
    }



    public int length() {
        return bytes.length;
    }

    public String toString() {
        return new String(bytes, Charset.defaultCharset());
    }
}
