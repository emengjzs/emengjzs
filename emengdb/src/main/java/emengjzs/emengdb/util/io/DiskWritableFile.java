/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.util.io;

import emengjzs.emengdb.db.Slice;

import java.io.*;

/**
 * Created by emengjzs on 2016/10/7.
 */
public class DiskWritableFile implements WritableFile {


    OutputStream out;
    FileOutputStream fileOutputStream;
    public DiskWritableFile(String path) throws FileNotFoundException {
        fileOutputStream = new FileOutputStream(path);
        out = new BufferedOutputStream(fileOutputStream);

    }


    @Override
    public void sync() throws IOException {
        out.flush();
        fileOutputStream.getFD().sync();
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        out.write(b, off, len);
    }

    @Override
    public void write(Slice data) throws IOException {
        data.outPut(out);
    }

    @Override
    public void write(byte b) throws IOException {
        out.write(b);
    }

    @Override
    public void close() throws IOException {
        out.close();
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }
}
