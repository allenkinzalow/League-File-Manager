package com.kinztech.league.raf.archives;

/**
 * Created by Allen Kinzalow on 7/2/2015.
 */
public class PathEntry {

    /**
     * The offset of the path in the buffer.
     */
    int pathOffset;

    /**
     * The length of the path in bytes.
     */
    int pathLength;

    /**
     * The path.
     */
    String path = "";

    public PathEntry(int pathOffset, int pathLength) {
        this.pathOffset = pathOffset;
        this.pathLength = pathLength;
    }

    /**
     * Get the path offset.
     * @return
     */
    public int getPathOffset() {
        return pathOffset;
    }

    /**
     * Get the path length.
     * @return
     */
    public int getPathLength() {
        return pathLength;
    }

    /**
     * Set the entry's path.
     * @param path
     */
    public void setString(String path) {
        this.path = path;
    }

    /**
     * Return the path.
     * @return
     */
    public String getPath() {
        return path;
    }

}


