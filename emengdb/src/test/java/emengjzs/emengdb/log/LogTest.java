/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.log;

import emengjzs.emengdb.db.Slice;
import emengjzs.emengdb.util.PrimitiveWritableOutputStream;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


/**
 * Created by emengjzs on 2016/9/12.
 */
public class LogTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
   class StringVirtualWritableFile extends PrimitiveWritableOutputStream implements WritableFile {

       StringVirtualWritableFile() {
           super(new ByteArrayOutputStream());
       }

       @Override
       public void sync() throws IOException {

       }

       @Override
       public void write(Slice data) throws IOException {
            data.outPut(outputStream);
       }

       public byte[] getBytes() {
           return ((ByteArrayOutputStream) outputStream).toByteArray();
       }
   }

    LogWriter out;
    StringVirtualWritableFile sf;
    @Before
    public void bolckSizeSet() {
        LogFormat.K_BLOCK_SIZE = 32;
        sf = new StringVirtualWritableFile();
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
