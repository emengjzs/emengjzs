/*
 * Copyright (c) 2016. emengjzs. All rights reserved.
 */

package emengjzs.emengdb.db;

import java.nio.ByteBuffer;
import java.util.Comparator;

/**
 * Created by emengjzs on 2016/8/30.
 */
public class MemTable {
    public final static long MAX_SEQ = (0x01L << 56) - 1;

    private SkipList<Key> table;


    /**
     * keyCmp<Key> -> interCmp<internalKey> -> userCmp<?>
     */
    private KeyComparator keyComparator;

    public MemTable(InternalKeyComparator cmp) {
        keyComparator = new KeyComparator(cmp);
        table = new SkipList<>(keyComparator);
    }


    public void add(long seq, ValueType type, byte[] key, byte[] value) {
        table.insert((new Key(seq, type, key, value)));
    }



    /**
     * a general key comparator where the key to be compared
     * is the actual key inserted in skipList for implementation,
     * as the computed key contains the key defined by user, we need
     * the user-defined comparator to decide the position when putting a
     * entry
     */
    class KeyComparator implements Comparator<Key> {

        InternalKeyComparator interCmp;

        KeyComparator(InternalKeyComparator interCmp) {
            this.interCmp = interCmp;
        }


        /**
         * we need to extract the internal key
         * @param o1
         * @param o2
         * @return
         */
        @Override
        public int compare(Key o1, Key o2) {
            return interCmp.compare(
                    o1.getInternalKey(),
                    o2.getInternalKey());
        }
    }

    class Key {

        byte[] bytes;

        Key(long seq, ValueType type, byte[] key, byte[] value) {
            // bytes = new byte[4 + key.length + 8 + 4 + value.length];
            write(seq, type, key, value);
        }

        /**
         * encode the key, the format shows as below
         * +-----------------+--------+------+-------+---------------+--------+
         * |  key.length + 8 |   key  |  seq |  type |  value.length |  value |
         * +-----------------+--------+------+-------+---------------+--------+
         *          4          key.len    7       1           4        val.len
         *
         * @param seq
         * @param type
         * @param key
         * @param value
         */
        private void write(long seq, ValueType type, byte[] key, byte[] value) {
            // use byteBuffer to simply operation
            ByteBuffer bf = ByteBuffer.allocate(4 + key.length + 8 + 4 + value.length);
            bf.putInt(key.length + 8)
                    .put(key)
                    .putLong(seq << 8 | type.toByte())
                    .putInt(value.length)
                    .put(value);
            // copy vs no-copy ?
            this.bytes = bf.array();
        }


        // TODO: opt, reduce byte array copy?
        // prefix + user_key + seq + TYPE
        Slice getLengthPrefixedInternalKey() {
            return new Slice(bytes, 0, 4 + toByteBuffer().getInt());
        }

        // user_key + seq + TYPE
        Slice getInternalKey() {
            return new Slice(bytes, 4, toByteBuffer().getInt());
        }

        Slice getUserKey() {
            return new Slice(bytes, 4, toByteBuffer().getInt() - 8);
        }

        long getSeqFlag() {
            return new Slice(bytes, 4 + toByteBuffer().getInt() - 8, 8).toByteBuffer().getLong();
        }

        Slice getValue() {
            ByteBuffer byteBuffer = new Slice(bytes).toByteBuffer();
            int interKeySize = byteBuffer.getInt();
            byteBuffer.position(4 + interKeySize);
            int valueSize = byteBuffer.getInt();
            return new Slice(bytes, byteBuffer.position(), valueSize);
        }

        ByteBuffer toByteBuffer() {
            return  ByteBuffer.wrap(bytes);
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
