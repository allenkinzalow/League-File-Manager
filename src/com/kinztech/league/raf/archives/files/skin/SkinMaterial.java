package com.kinztech.league.raf.archives.files.skin;

/**
 * Created by Allen Kinzalow on 7/3/2015.
 */
public class SkinMaterial {

    private String name;
    private int startVertex;
    private int verticesCount;
    private int startIndex;
    private int indicesCount;

    public void setName(String name) {
        this.name = name;
    }

    public void setStartVertex(int startVertex) {
        this.startVertex = startVertex;
    }

    public void setVerticesCount(int verticesCount) {
        this.verticesCount = verticesCount;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public void setIndicesCount(int indicesCount) {
        this.indicesCount = indicesCount;
    }

}
