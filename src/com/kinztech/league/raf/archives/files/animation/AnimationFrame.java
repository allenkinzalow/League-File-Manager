package com.kinztech.league.raf.archives.files.animation;

/**
 * Created by Allen Kinzalow on 7/3/2015.
 */
public class AnimationFrame {

    private float[] orientation;
    private float[] position;

    public AnimationFrame() {
        orientation = new float[4];
        position = new float[3];
    }

    public float[] getOrientation(){
        return orientation;
    }

    public float[] getPosition(){
        return position;
    }

}
