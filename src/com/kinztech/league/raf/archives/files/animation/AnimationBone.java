package com.kinztech.league.raf.archives.files.animation;

import java.util.ArrayList;

/**
 * Created by Allen Kinzalow on 7/3/2015.
 */
public class AnimationBone {

    private String name;
    private int id;
    private ArrayList<AnimationFrame> frames = new ArrayList<AnimationFrame>();

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<AnimationFrame> getFrames() {
        return frames;
    }

}
