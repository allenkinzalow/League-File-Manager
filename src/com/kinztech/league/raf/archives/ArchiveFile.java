package com.kinztech.league.raf.archives;

import com.kinztech.league.raf.utilities.io.IOBuffer;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Allen Kinzalow on 7/2/2015.
 */
public class ArchiveFile {

    /**
     * The archive archives.
     */
    private File riotArchiveFile;

    /**
     * The raw data.
     */
    private byte[] fileData;

    /**
     * The magic number - 0x18be0ef0
     */
    private int magicNumber;

    /**
     * The version of this archives.
     */
    private int version;

    /**
     * A value utilized by riot games.
     */
    private int managerIndex;

    /**
     * The offlist of the archives list in the archive archives.
     */
    private int fileListOffset;

    /**
     * The offlist of the path list in the archive archives.
     */
    private int pathListOffset;

    /**
     * The number of files.
     */
    private int fileListCount;

    /**
     * The number of paths.
     */
    private int pathListCount;

    /**
     * The size of bytes in a path list.
     */
    private int pathListSize;

    /**
     * An array list of the path entries defined in the riot archive archives.
     */
    private ArrayList<PathEntry> pathEntries = new ArrayList<PathEntry>();

    /**
     * The associated data archives with this riot archive archives.
     */
    private ArchiveDataFile archiveFileData;

    public boolean complete = false;

    /**
     * Construct a riot archive archives.
     * @param file
     */
    public ArchiveFile(File file) {
        this.riotArchiveFile = file;
        System.out.println(file.getAbsoluteFile());
        this.archiveFileData = new ArchiveDataFile(new File(file.getAbsolutePath() + ".dat"));
    }

    /**
     * Initiate the archive archives.
     */
    public void initiate() {
        try {
            fileData = new byte[(int) riotArchiveFile.length()];

            DataInputStream dis = new DataInputStream(new FileInputStream(riotArchiveFile));
            dis.readFully(fileData);
            dis.close();

            /**
             * Decode the archive header, archives info, archives data, and path info.
             */
            decodeArchive();

            /**
             * Write the entire archives archive.
             */
            writeArchive();

        } catch (Exception e) {
            System.out.println("Error decoding: " + riotArchiveFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    /**
     * Decode the entire riot archives archive.
     */
    private void decodeArchive() {
        IOBuffer buffer = new IOBuffer(fileData);

        /**
         * Header
         */
        this.magicNumber = buffer.readUnsignedLEInt();
        this.version = buffer.readUnsignedLEInt();
        this.managerIndex = buffer.readUnsignedLEInt();
        this.fileListOffset = buffer.readUnsignedLEInt();
        this.pathListOffset = buffer.readUnsignedLEInt();

        //System.out.println("Magic Number: " + this.magicNumber +  " Version: " + this.version + " Manager Index: " + this.managerIndex +  " File List Offset: " + this.fileListOffset +  " Path List Offset: " + this.pathListOffset);

        /**
         * File Entries
         */
        this.fileListCount = buffer.readUnsignedLEInt();
        //System.out.println("File List Count: " + this.fileListCount);
        for(int fileIndex = 0; fileIndex < this.fileListCount; fileIndex++) {
            int hash = buffer.readUnsignedLEInt();
            int dataOffset = buffer.readUnsignedLEInt();
            int dataSize = buffer.readUnsignedLEInt();
            int pathListIndex = buffer.readUnsignedLEInt();
            //System.out.println("File Hash: " + hash + " Data Offset: " + dataOffset + " Data Size: " + dataSize + " PLI: " + pathListIndex);
            archiveFileData.getFileEntries().add(new FileEntry(hash, dataOffset, dataSize, pathListIndex));
        }

        /**
         * Decode and store the archives data in the archive dat archives.
         */
        archiveFileData.initiate();

        /**
         * Path Entries
         */
        int pathStringOffset = buffer.getPosition();
        this.pathListSize = buffer.readUnsignedLEInt();
        this.pathListCount = buffer.readUnsignedLEInt();
        for(int pathIndex = 0; pathIndex < pathListCount; pathIndex++) {
            int pathOffset = buffer.readUnsignedLEInt();
            int pathLength = buffer.readUnsignedLEInt();
            //System.out.println("Path Offset: " + pathOffset + " Path Length: " + pathLength);
            pathEntries.add(new PathEntry(pathOffset, pathLength));
        }

        /**
         * Read the path strings.
         */
        for(PathEntry pathEntry : pathEntries) {
            buffer.setPosition(pathStringOffset + pathEntry.getPathOffset());
            pathEntry.setString(buffer.readString());
            //System.out.println("Path Offset: " + pathEntry.getPathOffset() + " Path Length: " + pathEntry.getPathLength() + " PLI: " + pathEntries.indexOf(pathEntry) + " Path String: " + pathEntry.getPath());
            //pathIndex++;
        }
    }

    /**
     * Write the entire archive to a folder.
     */
    private void writeArchive() {
        String sdirectory = "C:/Users/allen_000/Desktop/League Cache/";
        try {
            for (FileEntry fileEntry : archiveFileData.getFileEntries()) {
                String path = sdirectory + pathEntries.get(fileEntry.getPathListIndex()).getPath();
                //System.out.println("Writing: " + path);
                File directory = new File(path.substring(0,path.lastIndexOf('/')));
                if(!directory.exists()) {
                    //System.out.println("Making directory: " + directory.getAbsolutePath());
                    directory.mkdirs();
                } else
                    System.out.println(directory.getAbsoluteFile());
                File file = new File(path);
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
                dos.write(fileEntry.getFileData());
                dos.close();
                complete = true;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
