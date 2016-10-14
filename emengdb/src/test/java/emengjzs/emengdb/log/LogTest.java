/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.log;

import emengjzs.emengdb.db.Slice;
import emengjzs.emengdb.util.io.OutputStreamWrapWritableFile;
import emengjzs.emengdb.util.io.PrimitiveWritable;
import emengjzs.emengdb.util.io.WritableFile;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;


/**
 * Created by emengjzs on 2016/9/12.
 */
public class LogTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    ByteArrayOutputStream outputStream;
    WritableFile sf;
    LogWriter out;

    @Before
    public void bolckSizeSet() {
        LogFormat.K_BLOCK_SIZE = 32;
        outputStream = new ByteArrayOutputStream();
        sf = new PrimitiveWritable(new OutputStreamWrapWritableFile(outputStream));
        out = new LogWriter(sf);
    }

    @Test
    public void testWrite() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i ++) {
            sb.append("sdfdfdfdfrtrtr");
        }
        try {
            out.addData(new Slice(sb.toString()));
            out.addData(new Slice("1232323232323232323435"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
