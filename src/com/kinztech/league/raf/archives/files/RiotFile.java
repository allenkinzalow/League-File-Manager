package com.kinztech.league.raf.archives.files;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by Allen Kinzalow on 7/3/2015.
 */
public abstract class RiotFile {

    /**
     * The raw data of this file type.
     */
    protected byte[] fileData;

    public RiotFile(File file) {
        try {
            fileData = new byte[(int)file.length()];
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            dis.readFully(fileData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RiotFile(byte[] fileData) {
        this.fileData = fileData;
    }

    /**
     * Decode the file.
     */
    public abstract void decode();

}
