package com.kinztech.league.raf.archives.files.skeleton;

import com.kinztech.league.raf.archives.files.RiotFile;
import com.kinztech.league.raf.utilities.io.IOBuffer;

import java.io.File;

/**
 * Created by Allen Kinzalow on 7/3/2015.
 *
 * Skeleton file.
 */
public class SklFile extends RiotFile {

    Skeleton skeleton;

    /**
     * Super constructors
     * @param file
     */
    public SklFile(File file) { super(file); }
    public SklFile(byte[] file) { super(file); }

    public void decode() {
        IOBuffer buffer = new IOBuffer(fileData);
        skeleton = new Skeleton();
        skeleton.setId(buffer.readString(8));
        int version = buffer.readUnsignedLEInt();
        System.out.println("ID: " + skeleton.getId() + " Version: " + version);
        skeleton.setVersion(version);
        if(version == 1 || version == 2) {
            skeleton.setDesignerId(buffer.readUnsignedLEInt());

            int boneCount = buffer.readUnsignedLEInt();
            skeleton.setBoneCount(boneCount);
            System.out.println("Bone Count: " + boneCount);
            for(int bone = 0; bone < boneCount; bone++) {
                SkeletonBone sknBone = new SkeletonBone();
                sknBone.setName(buffer.readString(32).toLowerCase());
                System.out.println("SknBone: " + sknBone.getName());
                sknBone.setId(bone);
                sknBone.setParentId(buffer.readUnsignedLEInt());
                sknBone.setScale(buffer.readLEFloat());
                float[] orientation = new float[12];
                for(int i = 0; i < orientation.length; i++) {
                    orientation[i] = buffer.readLEFloat();
                }
                sknBone.setOrientation(orientation);
                float[] position = new float[3];
                position[0] = orientation[3];
                position[1] = orientation[7];
                position[2] = orientation[11];
                sknBone.setPosition(position);
                skeleton.getBones().add(sknBone);
            }

            if(version == 2) {
                int boneIDCount = buffer.readUnsignedLEInt();
                skeleton.setBoneIdCount(boneIDCount);
                for(int i = 0; i < boneIDCount; i++) {
                    skeleton.getBoneIds().add(buffer.readUnsignedLEInt());
                }
            }
        } else if(version == 0) {

        }
    }

    public Skeleton getSkeleton(){
        return skeleton;
    }

}
