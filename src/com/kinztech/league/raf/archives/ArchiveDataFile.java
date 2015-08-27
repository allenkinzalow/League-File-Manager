package com.kinztech.league.raf.archives;

import com.kinztech.league.raf.utilities.io.IOBuffer;

import java.io.*;
import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Allen Kinzalow on 7/2/2015.
 */
public class ArchiveDataFile {

    /**
     * The data archives.
     */
    private File dataFile;

    /**
     * An array list of archives entries defined in the riot archive archives.
     */
    private ArrayList<FileEntry> fileEntries = new ArrayList<FileEntry>();

    /**
     * Construct a riot archive archives data instance with a given
     *  data archives.
     * @param dataFile
     */
    public ArchiveDataFile(File dataFile) {
        this.dataFile = dataFile;
    }

    /**
     * Setup and begin decoding individual files within the archive data archives.
     */
    public void initiate() {
        try {
            byte[] fileData = new byte[(int)dataFile.length()];
            DataInputStream dis = new DataInputStream(new FileInputStream(dataFile));
            dis.readFully(fileData);

            decodeFiles(fileData);
        } catch (Exception e) {
            System.out.println("Error Loading: " + dataFile.getAbsolutePath());
            e.printStackTrace();
        }
    }

    /**
     * Decode and store all of the individual files within the archive archives data.
     * @param fileData  The raw data of the archive dat archives.
     */
    public void decodeFiles(byte[] fileData) {
        IOBuffer buffer = new IOBuffer(fileData);
        try {
            for (FileEntry fileEntry : fileEntries) {
                byte[] entryData = new byte[fileEntry.getDataSize()];
                buffer.setPosition(fileEntry.getDataOffset());
                buffer.readBytes(entryData, fileEntry.getDataSize());

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                Inflater inflater = new Inflater();
                inflater.setInput(entryData);
                byte[] tmp = new byte[4 * 1024];
                try{
                    while(!inflater.finished()){
                        int size = inflater.inflate(tmp);
                        bos.write(tmp, 0, size);
                    }
                    byte[] uncompressed = bos.toByteArray();
                    bos.close();
                    fileEntry.setFileData(uncompressed);
                } catch (Exception e){
                    fileEntry.setFileData(entryData);
                }
                /*ByteArrayOutputStream baos = null;
                Inflater iflr = new Inflater();
                iflr.setInput(entryData);
                baos = new ByteArrayOutputStream();
                byte[] tmp = new byte[4*1024];
                try{
                    while(!iflr.finished()){
                        int size = iflr.inflate(tmp);
                        baos.write(tmp, 0, size);
                    }
                } catch (Exception ex){

                } finally {
                    try{
                        if(baos != null) baos.close();
                    } catch(Exception ex){}
                }
                fileEntry.setFileData(baos.toByteArray());*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Return the archives entries.
     * @return
     */
    public ArrayList<FileEntry> getFileEntries() {
        return fileEntries;
    }

    /**
     * Return a archives entry for its given path index.
     * @param index
     * @return
     */
    public FileEntry getEntryForPathIndex(int index) {
        for(FileEntry entry : fileEntries)
            if(entry.getPathListIndex() == index)
                return entry;
        return null;
    }

}
