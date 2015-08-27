package com.kinztech.league.raf.archives;

/**
 * Created by Allen Kinzalow on 7/2/2015.
 */
public class FileEntry {

    /**
     * The hash of the archives's path.
     */
    private int hash;

    /**
     * The offset of the data archive archives.
     */
    private int dataOffset;

    /**
     * The length of the data in the data archive archives.
     */
    private int dataSize;

    /**
     * The index of the path in the list decoded in the archive header.
     *  Value between 0 and pathlist - 1.
     */
    private int pathListIndex;

    /**
     * The archives data.
     */
    private byte[] fileData;

    public FileEntry(int hash, int dataOffset, int dataSize, int pathListIndex) {
        this.hash = hash;
        this.dataOffset = dataOffset;
        this.dataSize = dataSize;
        this.pathListIndex = pathListIndex;
        this.fileData = new byte[this.dataSize];
    }

    /**
     * Get the hash.
     * @return
     */
    public int getHash() {
        return hash;
    }

    /**
     * Return the data offset in the raf.dat archives
     * @return
     */
    public int getDataOffset() {
        return dataOffset;
    }

    /**
     * Return the length of the archives entry in the raf.dat archives.
     * @return
     */
    public int getDataSize() {
        return dataSize;
    }

    /**
     * Return the index of this archives entry in the path list decoded in the archive header.
     * @return
     */
    public int getPathListIndex() {
        return pathListIndex;
    }

    /**
     * Return the archives data for this entry.
     * @return
     */
    public byte[] getFileData() {
        return fileData;
    }

    /**
     * Set the archives data for this entry.
     * @param fileData
     */
    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

}
