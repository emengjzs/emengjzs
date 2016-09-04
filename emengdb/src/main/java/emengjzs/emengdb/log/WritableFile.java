/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.log;

import emengjzs.emengdb.db.Slice;

/**
 * Created by emengjzs on 2016/9/3.
 */
public interface WritableFile {

    void sync();
    void close();
    void flush();
    void add(Slice data);
    void add(byte b);
    void add(short s);
    void add(int i);
    void add(long l);

}
