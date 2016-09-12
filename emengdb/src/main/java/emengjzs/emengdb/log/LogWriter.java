/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.log;

import emengjzs.emengdb.db.Slice;
import emengjzs.emengdb.util.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by emengjzs on 2016/9/2.
 */
public class LogWriter extends LogFormat {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private WritableFile writableFile;

    private int blockOffset = 0;

    LogWriter(WritableFile writableFile) {
        this.writableFile = writableFile;
    }


    public void addData(Slice data) throws IOException {
        int leftSize = data.getLength();
        int blockLeftSize = 0;

        // the start index of the data to be read.
        int start = 0;

        RecordType type = RecordType.FIRST_TYPE;
        while (leftSize > 0) {
            blockLeftSize = K_BLOCK_SIZE - blockOffset;
            if (blockLeftSize < K_HEADER_SIZE) {

                if (blockLeftSize > 0) {
                    // fill zero
                    writableFile.write(new Slice(new byte[]{0, 0, 0, 0, 0, 0}, 0, blockLeftSize));
                }
                // next new bolck

                /*
                if (log.isDebugEnabled()) {
                    log.debug("[LOG FILE] New Block : -{}", blockLeftSize);
                }
*/
                blockOffset = 0;
                blockLeftSize = K_BLOCK_SIZE;
            }
            Validate.isTrue(K_BLOCK_SIZE - blockOffset >= K_HEADER_SIZE, "The block did not" +
                    "fill zero in the tail.");

            // last part of data, may be the whole data
            int blockAvailSize = blockLeftSize - K_HEADER_SIZE;
            if (blockAvailSize >= leftSize ) {
                if (type == RecordType.FIRST_TYPE)
                    type = RecordType.FULL_TYPE;
                else
                    type = RecordType.LAST_TYPE;
                blockOffset += addRecord(type, data, start, leftSize);
                start += leftSize;
                leftSize = 0;
            }
            else {

                blockOffset += addRecord(type, data, start, blockAvailSize);
                if (type == RecordType.FIRST_TYPE) {
                    type = RecordType.MIDDLE_TYPE;
                }
                start += (blockAvailSize);
                leftSize -= (blockAvailSize);
            }

        }
    }

    private int addRecord(RecordType type, Slice data, int start, int length) throws IOException {
        // omit the part of CRC
        writableFile.write((int) 0x12345678);
        writableFile.write((short) (length & 0xFFFF));
        writableFile.write((byte) type.id);
        writableFile.write(data.getSubSlice(start, length));
        writableFile.flush();

        if (log.isDebugEnabled()) {
            log.debug("[LOG FILE] Write: {}, {} - [{}]",
                    type.toString(),
                    length,
                    data.getSubSlice(start, length).toByteString());
        }

        return K_HEADER_SIZE + length;
    }
}
