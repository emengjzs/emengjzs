/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.db;

import emengjzs.emengdb.util.Converter;

import java.nio.ByteBuffer;
import java.util.Comparator;

/**
 * Created by emengjzs on 2016/8/30.
 */
public class MemTable {
    public final static long MAX_SEQ = (0x01L << 56) - 1;

    private SkipList<byte[]> table;

    private KeyComparator keyComparator;

    public MemTable(Comparator<byte[]> cmp) {
        keyComparator = new KeyComparator(cmp);
        table = new SkipList<>(keyComparator);
    }


    public void add(long seq, byte[] key, byte[] value) {

    }

    /**
     * a general key comparator where the key to be compared
     * is the actual key inserted in skipList for implementation,
     * as the computed key contains the key defined by user, we need
     * the user-defined comparator to decide the position when putting a
     * entry
     */
    class KeyComparator implements Comparator<byte[]> {

        Comparator<byte[]> interCmp;

        KeyComparator(Comparator<byte[]> interCmp) {
            this.interCmp = interCmp;
        }

        @Override
        public int compare(byte[] o1, byte[] o2) {
            return 0;
        }
    }

    class Key {

        byte[] bytes;

        Key(long seq, ValueType type, byte[] key, byte[] value) {
            // bytes = new byte[4 + key.length + 8 + 4 + value.length];
            write(seq, type, key, value);
        }

        private void write(long seq, ValueType type, byte[] key, byte[] value) {
            // use byteBuffer to simply operation
            ByteBuffer bf = ByteBuffer.allocate(4 + key.length + 8 + 4 + value.length);
            bf.putInt(key.length + 8)
                    .put(key)
                    .putLong(seq << 8 | type.toByte())
                    .putInt(value.length)
                    .put(value);
            bf.flip();
            // copy vs no-copy ?
            this.bytes = bf.array();
        }

        /*
        private int write(int start, byte[] bytes) {
            if (start + bytes.length >= bytes.length) {
                throw new ArrayIndexOutOfBoundsException();
            }
            for (byte b : bytes) {
                bytes[start++] = b;
            }
            return start;
        }
        */

    }

}
