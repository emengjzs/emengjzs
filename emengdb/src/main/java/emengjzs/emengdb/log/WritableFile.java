/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.log;

import emengjzs.emengdb.db.Slice;
import emengjzs.emengdb.util.PrimitiveWritable;

import java.io.IOException;

/**
 * Created by emengjzs on 2016/9/3.
 */
public interface WritableFile extends PrimitiveWritable{

    void sync() throws IOException;
    void write(Slice data) throws IOException;


}
