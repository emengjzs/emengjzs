/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.util.io;

import emengjzs.emengdb.db.Slice;

import java.io.IOException;

/**
 * Created by emengjzs on 2016/9/3.
 */
public interface WritableFile extends PrimitiveWritable {

    void sync() throws IOException;
    void write(Slice data) throws IOException;


}
