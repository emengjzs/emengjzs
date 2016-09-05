/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.log;

import java.io.RandomAccessFile;

/**
 * Created by emengjzs on 2016/9/3.
 */

/**
 * Not required to be thread safe
 */
public class LogReader {

    private RandomAccessFile randomAccessFile;
    private long initialOffset;


    LogReader(RandomAccessFile file, long initialOffset) {
        this.randomAccessFile = file;
        this.initialOffset = initialOffset;
    }



}
