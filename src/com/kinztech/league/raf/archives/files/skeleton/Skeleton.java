package com.kinztech.league.raf.archives.files.skeleton;

import java.util.ArrayList;

/**
 * Created by Allen Kinzalow on 7/3/2015.
 */
public class Skeleton {

    private String id;
    private int version;
    private int designerId;
    private int boneCount;
    private ArrayList<SkeletonBone> bones = new ArrayList<SkeletonBone>();
    private int boneIdCount;
    private ArrayList<Integer> boneIds = new ArrayList<Integer>();

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setDesignerId(int designerId) {
        this.designerId = designerId;
    }

    public void setBoneCount(int boneCount) {
        this.boneCount = boneCount;
    }

    public void setBoneIdCount(int boneIdCount) {
        this.boneIdCount = boneIdCount;
    }

    public ArrayList<SkeletonBone> getBones() {
        return bones;
    }

    public ArrayList<Integer> getBoneIds() {
        return boneIds;
    }

}
