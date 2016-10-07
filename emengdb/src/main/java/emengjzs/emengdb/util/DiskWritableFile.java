/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.util;

import emengjzs.emengdb.db.Slice;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by emengjzs on 2016/10/7.
 */
public class DiskWritableFile implements WritableFile {


    FileOutputStream out;

    public DiskWritableFile(String path) throws FileNotFoundException {
        out = new FileOutputStream(path);

    }


    @Override
    public void sync() throws IOException {
        out.getFD().sync();
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
