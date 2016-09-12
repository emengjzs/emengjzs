/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by emengjzs on 2016/9/12.
 */
public class PrimitiveWritableOutputStream extends OutputStream implements PrimitiveWritable {


    protected OutputStream outputStream;


    protected PrimitiveWritableOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void write(byte b) throws IOException {
        outputStream.write(b);
    }

    @Override
    public void write(int b) throws IOException {
        PrimitiveWritable.super.write(b);
    }

    public void flush() throws IOException {
        outputStream.flush();
    }

    public void close() throws IOException {
        outputStream.close();
    }
}
