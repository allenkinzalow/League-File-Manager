package com.kinztech.league.raf.archives.files.animation;

import java.util.ArrayList;

/**
 * Created by Allen Kinzalow on 7/3/2015.
 */
public class Animation {

    private String id;
    private int version;
    private int magic;
    private int boneCount;
    private int frameCount;
    private int playbackFPS;
    private ArrayList<AnimationBone> bones = new ArrayList<AnimationBone>();

    public void setId(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setVersion(int version){
        this.version = version;
    }

    public void setMagic(int magic){
        this.magic = magic;
    }

    public int getBoneCount(){
        return boneCount;
    }

    public void setBoneCount(int boneCount){
        this.boneCount = boneCount;
    }

    public int getFrameCount(){
        return frameCount;
    }

    public void setFrameCount(int frameCount){
        this.frameCount = frameCount;
    }

    public void setPlaybackFPS(int playbackFPS){
        this.playbackFPS = playbackFPS;
    }

    public ArrayList<AnimationBone> getBones(){
        return bones;
    }

}
