/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.log;

/**
 * Created by emengjzs on 2016/9/2.
 */
public interface LogFormat {

    RecordType MAX_RECORD_SIZE = RecordType.LAST_TYPE;

    int K_BLOCK_SIZE = 32768;

    int K_HEADER_SIZE = 4 + 1 + 2;
}


