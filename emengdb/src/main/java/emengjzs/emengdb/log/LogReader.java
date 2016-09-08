/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.log;

import emengjzs.emengdb.db.Slice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static emengjzs.emengdb.log.LogFileException.Type.RECORD_DATA_ERRPR;

/**
 * Created by emengjzs on 2016/9/3.
 */

/**
 * Not required to be thread safe
 */
public class LogReader implements LogFormat {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private LogErrorListener logErrorListener;

    private RandomAccessFile randomAccessFile;
    private long initialOffset;
    private ByteBuffer bf;

    LogReader(RandomAccessFile file, long initialOffset) {
        this.randomAccessFile = file;
        this.initialOffset = initialOffset;
        bf = ByteBuffer.allocate(K_BLOCK_SIZE);
    }

    private void seekToInitBlock() {
        long inBlockOffSet = initialOffset % K_BLOCK_SIZE;
        long blockOffSet = initialOffset - inBlockOffSet;
        try {
            randomAccessFile.seek(blockOffSet);
        } catch (IOException e) {
            log.error("InitialOffset overlap.");
        }
    }


    Slice readNextData() {
        return null;
    }


    Slice readNextRecord(ByteBuffer bf) throws LogFileException {
        try {
            if (bf.remaining() <= 0 && (! readNextBlock(bf))) {
                return null;
            }
            // CRC omit
            int crc = bf.getInt();
            int length = Short.toUnsignedInt(bf.getShort());
            RecordType type = RecordType.of(bf.get());

            if (bf.remaining() < length) {
                throw new LogFileException(RECORD_DATA_ERRPR);
            }

            // return a viewer of bytearray in bytebuffer to
            // avoid byte array copy.
            Slice record = new Slice(bf.array(), bf.arrayOffset() + bf.position(), length);




            if (crc != 0x12345678) {
                throw new LogFileException(LogFileException.Type.CRC_NOT_MATCH_ERROR);
            }

            if (log.isDebugEnabled()) {
                log.debug("[LOG FILE] Read: {}, {} - [{}]",
                        type.toString(), length, record.toByteString());
            }
            return record;

        } catch (IOException e) {
            e.printStackTrace();
            throw new LogFileException(LogFileException.Type.UNKNOWN_IO_ERROR, e);
        }

    }

    /**
     * read the next full block data from the physical file to the buffer
     *
     * @param bf
     */
    boolean readNextBlock(ByteBuffer bf) throws IOException {
        FileChannel channel = randomAccessFile.getChannel();
        bf.clear();
        // write to buffer
        int read = channel.read(bf);
        // set up to read from buffer
        bf.flip();
        return read > 0;
    }

}
