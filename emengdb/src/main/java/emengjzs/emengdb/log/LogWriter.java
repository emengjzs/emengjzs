/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.log;

import emengjzs.emengdb.db.Slice;
import emengjzs.emengdb.util.Validate;

/**
 * Created by emengjzs on 2016/9/2.
 */
public class LogWriter implements LogFormat {

    private WritableFile writableFile;

    private int blockOffset = 0;

    LogWriter(WritableFile writableFile) {
        this.writableFile = writableFile;
    }

    /**
     * - log file contains sequences of blocks.
     * - each may contains sequences of records, and their sizes may
     * be not the same.
     * - the record must stay only in a block
     * - data is divided into a few records.
     * +----------------------------------------+-----+---------+  \
     * |   record   |            record         | ... |  record |   |---> Block 32 KB
     * |--------------------------------------------------------+  /
     * |              record            |    record   |  record |
     * +--------------------------------------------------------+
     * |                        ...                             |
     * +--------------------------------------------------------+
     *
     * record:
     * +-------------------+--------------+--------+--------------------+
     * |        CRC        |    length    |  type  |        data        |
     * +-------------------+--------------+--------+--------------------+
     * |          4                2           1             length     |
     *
     * @param data
     */
    public void addData(Slice data) {
        int leftSize = data.getLength();
        int blockLeftSize = K_BLOCK_SIZE - blockOffset;
        int start = 0;
        RecordType type = RecordType.FIRST_TYPE;
        while (leftSize > 0) {
            if (blockLeftSize < K_HEADER_SIZE) {

                if (blockLeftSize > 0) {
                    // fill zero
                    writableFile.add(new Slice(new byte[]{0, 0, 0, 0, 0, 0}, 0, blockLeftSize));
                }
                // next new bolck
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
                if (type == RecordType.FIRST_TYPE) {
                    type = RecordType.MIDDLE_TYPE;
                }
                blockOffset += addRecord(type, data, start, blockAvailSize);
                start += (blockAvailSize);
                leftSize -= (blockAvailSize);
            }

        }
    }

    private int addRecord(RecordType type, Slice data, int start, int length) {
        // omit the part of CRC
        writableFile.add(new Slice(new byte[]{0x12, 0x34, 0x56, 0x78}));
        writableFile.add((short) (length & 0xFFFF));
        writableFile.add((byte) type.id);
        writableFile.add(data.getSubSlice(start, length));
        writableFile.flush();
        return K_HEADER_SIZE + length;
    }
}
