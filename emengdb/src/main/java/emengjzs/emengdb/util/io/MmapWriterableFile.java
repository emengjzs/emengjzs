/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.util.io;

import emengjzs.emengdb.db.Slice;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by emengjzs on 2016/10/11.
 */
public class MmapWriterableFile implements WritableFile {
    RandomAccessFile rw;
    private FileChannel fileChannel;
    private MappedByteBuffer mmapBuffer;
    private int mapSize = 1 << (16 - 1);
    private long fileOffset;
    private final int MAX_MAP_SIZE = 1 << 24;
    private boolean isLastMapSync = true;


    public MmapWriterableFile(String fileName, long offset) throws IOException {

        this.fileOffset = offset;
        rw = new RandomAccessFile(fileName, "rw");
        fileChannel = rw.getChannel();
        mmapBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, fileOffset, 0);
    }

    @Override
    public void sync() throws IOException {
        if (! isLastMapSync) {
            this.rw.getFD().sync();
            isLastMapSync = true;
        }
        mmapBuffer.force();
    }

    @Override
    public void write(Slice data) throws IOException {
        write(data.array(), data.getStart(), data.getLength());
    }

    void unmapCurrentMap() {
        // mmapBuffer.force();
        // asume gc will collect this.
        fileOffset += mmapBuffer.capacity();
        isLastMapSync = true;
        mmapBuffer = null;
    }

    void resizeMap() throws IOException {
        if (mapSize < MAX_MAP_SIZE) {
            mapSize <<= 1;
        }
        // resize the file
        this.rw.setLength(fileOffset + mapSize);
        mmapBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, fileOffset, mapSize);
    }

    @Override
    public void write(byte b) throws IOException {
        write(new byte[] {b});
    }

    @Override
    public void write(byte b[], int off, int len) throws IOException {
        int left = len;
        int offset = off;
        while (left > 0) {
            if (mmapBuffer.remaining() <= 0) {
                unmapCurrentMap();
                resizeMap();
            }
            int writable = left < mmapBuffer.remaining() ? left : mmapBuffer.remaining();
            mmapBuffer.put(b, offset, writable);
            left -= writable;
            offset += writable;
        }
    }

    @Override
    public void write(byte[] b) throws IOException {
        write(b, 0, b.length);
    }


    @Override
    public void close() throws IOException {
        // sync();
        int unused = mmapBuffer.remaining();
        unmapCurrentMap();
        fileChannel.close();
        rw.close();
    }

    @Override
    public void flush() throws IOException {

    }
}
