package com.kinztech.league.raf.archives;

import java.io.File;

/**
 * Created by Allen Kinzalow on 7/2/2015.
 */
public class ArchiveExplorer {
    
    private final static String FILE_PATH = "C:/Riot Games/League of Legends/RADS/projects/lol_game_client/filearchives/";

    /**
     * Navigate through all of the archives archives and load each one.
     */
    public void navigate() {
        try {
            File directory = new File(FILE_PATH);
            for (File file : directory.listFiles()) {
                for (final File subFile : file.listFiles()) {
                    if (subFile.getName().endsWith(".raf")) {
                        System.out.println("Writing: " + subFile.getAbsolutePath());
                        ArchiveFile archiveFile = new ArchiveFile(subFile);
                        archiveFile.initiate();
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
