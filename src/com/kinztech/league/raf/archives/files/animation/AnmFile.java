package com.kinztech.league.raf.archives.files.animation;

import com.kinztech.league.raf.archives.files.RiotFile;
import com.kinztech.league.raf.utilities.io.IOBuffer;

import java.io.File;

/**
 * Created by Allen Kinzalow on 7/3/2015.
 *
 * Animation File.
 */
public class AnmFile extends RiotFile {

    public AnmFile(File file) { super(file); }
    public AnmFile(byte[] file) { super(file); }

    @Override
    public void decode() {
        IOBuffer buffer = new IOBuffer(fileData);
        Animation animation = new Animation();
        animation.setId(buffer.readString(8));
        System.out.println("Animation: " + animation.getId());
        int version = buffer.readUnsignedLEInt();
        animation.setVersion(version);

        if(version == 0 || version == 1 || version == 2 || version == 3) {
            animation.setMagic(buffer.readUnsignedLEInt());
            animation.setBoneCount(buffer.readUnsignedLEInt());
            animation.setFrameCount(buffer.readUnsignedLEInt());
            animation.setPlaybackFPS(buffer.readUnsignedLEInt());

            for(int bone = 0; bone < animation.getBoneCount(); bone++) {
                AnimationBone animBone = new AnimationBone();
                animBone.setName(buffer.readString(32).toLowerCase());
                System.out.println("AnimBone: " + animBone.getName());
                buffer.readUnsignedLEInt();

                for(int frame = 0; frame < animation.getFrameCount(); frame++) {
                    AnimationFrame animFrame = new AnimationFrame();
                    animFrame.getOrientation()[0] = buffer.readLEFloat();
                    animFrame.getOrientation()[1] = buffer.readLEFloat();
                    animFrame.getOrientation()[2] = buffer.readLEFloat();
                    animFrame.getOrientation()[3] = buffer.readLEFloat();

                    animFrame.getPosition()[0] = buffer.readLEFloat();
                    animFrame.getPosition()[1] = buffer.readLEFloat();
                    animFrame.getPosition()[2] = buffer.readLEFloat();
                    animBone.getFrames().add(animFrame);
                }
                animation.getBones().add(animBone);
            }
        } else if(version == 4) {

        }
    }
}
