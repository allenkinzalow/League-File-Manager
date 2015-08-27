package com.kinztech.league.raf.utilities.io;

/**
 * Created by Allen Kinzalow on 7/2/2015.
 */
public class IOBuffer {

    byte[] buffer;
    int position;

    /**
     * Construct an IOBuffer with a given byte array
     * @param buffer
     */
    public IOBuffer(byte[] buffer) {
        this.buffer = buffer;
    }

    /**
     * Read Little Endian Unsigned Integer
     * 0, 8, 16, 24
     * @return
     */
    public int readUnsignedLEInt() {
        this.position += 4;
        return (this.buffer[this.position - 4] & 255) + ((this.buffer[this.position - 3] & 255) << 8) + ((this.buffer[this.position - 2] & 255) << 16) + ((this.buffer[this.position - 1] & 255) << 24);
    }

    /**
     * Read Unsigned Integer
     * 24 16 8 0
     * @return
     */
    public int readUnsignedInt() {
        this.position += 4;
        return (this.buffer[this.position - 1] & 255) + ((this.buffer[this.position - 2] & 255) << 8) + ((this.buffer[this.position - 3] & 255) << 16) + ((this.buffer[this.position - 4] & 255) << 24);
    }

    /**
     * Read Little Endian Unsigned Medium
     * 0, 8, 16
     * @return
     */
    public int readUnsignedLEMedium() {
        this.position += 3;
        return (this.buffer[this.position - 3] & 255) + ((this.buffer[this.position - 2] & 255) << 8) + ((this.buffer[this.position - 1] & 255) << 16);
    }

    /**
     * Read Unsigned Medium
     * 16 8 0
     * @return
     */
    public int readUnsignedMedium() {
        this.position += 3;
        return (this.buffer[this.position - 1] & 255) + ((this.buffer[this.position - 2] & 255) << 8) + ((this.buffer[this.position - 3] & 255) << 16);
    }

    /**
     * Read Little Endian Unsigned Short
     * 0, 8, 16
     * @return
     */
    public int readUnsignedLEShort() {
        this.position += 2;
        return (this.buffer[this.position - 2] & 255) + ((this.buffer[this.position - 1] & 255) << 8);
    }

    /**
     * Read Unsigned Short
     * 16 8 0
     * @return
     */
    public int readUnsignedShort() {
        this.position += 2;
        return (this.buffer[this.position - 1] & 255) + ((this.buffer[this.position - 2] & 255) << 8);
    }

    public Float readLEFloat() {
        return Float.intBitsToFloat(this.readUnsignedLEInt());
    }

    public Float readFloat() {
        return Float.intBitsToFloat(this.readUnsignedInt());
    }

    /**
     * Read an unsigned byte.
     * @return
     */
    public int readUnsignedByte() {
        this.position += 1;
        return this.buffer[this.position - 1] & 255;
    }

    /**
     * Read a signed byte.
     * @return
     */
    public int readByte() {
        this.position += 1;
        return this.buffer[this.position - 1];
    }

    /**
     * Set the buffer's position.
     * @param position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Return the buffer's position.
     * @return
     */
    public int getPosition() {
        return position;
    }

    /**
     * Read bytes from the buffer to a byte array with a given length.
     * -Starts from the current position!
     * @param ai
     * @param length
     */
    public void readBytes(byte[] ai, int length) {
        for(int pos = 0; pos < length; pos++)
            ai[pos] = buffer[position + pos];
    }

    /**
     * Read a string with a null terminating 0x00
     * @return
     */
    public String readString() {

        int start = position;
        while (readByte() != 0);
        int len = position - start;

        byte[] str = new byte[len];
        position = start;
        readBytes(str, len - 1);

        return new String(str, 0, len - 1);
    }

    public String readString(int size) {
        byte[] str = new byte[size];
        readBytes(str, size - 1);
        position += size;
        return new String(str, 0 , size - 1);
    }

}
