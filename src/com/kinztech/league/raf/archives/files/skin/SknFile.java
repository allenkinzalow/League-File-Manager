package com.kinztech.league.raf.archives.files.skin;

import com.kinztech.league.raf.archives.files.RiotFile;
import com.kinztech.league.raf.utilities.io.IOBuffer;

import java.io.File;

/**
 * Created by Allen Kinzalow on 7/3/2015.
 *
 * Skin File.
 */
public class SknFile extends RiotFile {

    private Skin skin;

    /**
     * Super constructors
     * @param fileData
     */
    public SknFile(byte[] fileData) { super(fileData); }
    public SknFile(File fileData) { super(fileData); }

    @Override
    public void decode() {
        IOBuffer buffer = new IOBuffer(fileData);
        skin = new Skin();
        skin.setMagic(buffer.readUnsignedLEInt());
        int version = buffer.readUnsignedLEShort();
        skin.setVersion(version);
        skin.setObjectCount(buffer.readUnsignedLEShort());
        if(version == 1 || version == 2) {
            int materialCount = buffer.readUnsignedLEInt();
            skin.setMaterialCount(materialCount);
            for(int material = 0; material < materialCount; material++) {
                SkinMaterial skinMaterial = new SkinMaterial();
                String name = buffer.readString(64);
                skinMaterial.setName(name);
                System.out.println("Material Name: " + name);
                skinMaterial.setStartVertex(buffer.readUnsignedLEInt());
                skinMaterial.setVerticesCount(buffer.readUnsignedLEInt());
                skinMaterial.setStartIndex(buffer.readUnsignedLEInt());
                skinMaterial.setIndicesCount(buffer.readUnsignedLEInt());
                skin.getMaterialList().add(skinMaterial);
            }

            skin.setIndicesCount(buffer.readUnsignedLEInt());
            skin.setVerticesCount(buffer.readUnsignedLEInt());

            for(int index = 0; index < skin.getIndicesCount(); index++) {
                int indexValue = buffer.readUnsignedLEShort()/* + 1*/;
                //System.out.println("IV: " + indexValue);
                skin.getIndices().add(indexValue);
                System.out.println("Index: " + indexValue);
            }

            for(int vertex = 0; vertex < skin.getVerticesCount(); vertex++) {
                SkinVertex skinVertex = new SkinVertex();
                skinVertex.getPosition()[0] = buffer.readLEFloat();
                skinVertex.getPosition()[1] = buffer.readLEFloat();
                skinVertex.getPosition()[2] = buffer.readLEFloat();
                System.out.println("V: " + skinVertex.getPosition()[0] + " , " + skinVertex.getPosition()[1] + " , " + skinVertex.getPosition()[2]);
                for(int bone = 0; bone < 4; bone++) {
                    skinVertex.getBoneIndex()[bone] = buffer.readUnsignedByte();
                }

                skinVertex.getWeights()[0] = buffer.readLEFloat();
                skinVertex.getWeights()[1] = buffer.readLEFloat();
                skinVertex.getWeights()[2] = buffer.readLEFloat();
                skinVertex.getWeights()[3] = buffer.readLEFloat();
                System.out.println("Weights: " + skinVertex.getWeights()[0] + " , " + skinVertex.getWeights()[1] + " , " + skinVertex.getWeights()[2] + " , " + skinVertex.getWeights()[3]);

                skinVertex.getNormal()[0] = buffer.readLEFloat();
                skinVertex.getNormal()[1] = buffer.readLEFloat();
                skinVertex.getNormal()[2] = buffer.readLEFloat();
                System.out.println("N: " + skinVertex.getNormal()[0] + " , " + skinVertex.getNormal()[1] + " , " + skinVertex.getNormal()[2]);

                skinVertex.getTextureCoords()[0] = buffer.readLEFloat();
                skinVertex.getTextureCoords()[1] = buffer.readLEFloat();
                System.out.println("TX: " + skinVertex.getTextureCoords()[0] + " , " + skinVertex.getTextureCoords()[1]);
                skin.getVertices().add(skinVertex);
            }

        }
    }

    public Skin getSkin(){
        return skin;
    }

}
