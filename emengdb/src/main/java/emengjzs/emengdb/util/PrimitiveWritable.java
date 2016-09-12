/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.util;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * Created by emengjzs on 2016/9/12.
 */
public interface PrimitiveWritable extends Closeable, Flushable {


    void write(byte b) throws IOException;

    default void write(byte b[]) throws IOException {
        write(b, 0, b.length);
    }

    default void write(byte b[], int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if ((off < 0) || (off > b.length) || (len < 0) ||
                ((off + len) > b.length) || ((off + len) < 0)) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return;
        }
        for (int i = 0 ; i < len ; i++) {
            write(b[off + i]);
        }
    }

    default void write(boolean val) throws IOException {
        write((byte) (val ? 1 : 0));
    }

    default void write(char val) throws IOException {
        write ((byte) (val >>> 8));
        write ((byte) (val      ));
    }

    default void write(short val) throws IOException {
        write ((byte) (val >>> 8));
        write ((byte) (val      ));
    }

    default void write(int val) throws IOException {
        write( (byte) (val >>> 24));
        write( (byte) (val >>> 16));
        write( (byte) (val >>>  8));
        write( (byte) (val       ));
    }

    default void write(long val) throws IOException {
        write( (byte) (val >>> 56));
        write( (byte) (val >>> 48));
        write( (byte) (val >>> 40));
        write( (byte) (val >>> 32));
        write( (byte) (val >>> 24));
        write( (byte) (val >>> 16));
        write( (byte) (val >>>  8));
        write( (byte) (val       ));
    }

    default void write(double val) throws IOException {
        write(Double.doubleToLongBits(val));
    }

    default void write(String val) throws IOException {
        write(val.getBytes());
    }




}
