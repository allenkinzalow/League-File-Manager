package com.kinztech.league;


import com.kinztech.league.raf.archives.files.animation.AnmFile;
import com.kinztech.league.raf.archives.files.skeleton.SklFile;
import com.kinztech.league.raf.archives.files.skin.SknFile;

import java.io.File;

/**
 * Created by Allen Kinzalow on 7/2/2015.
 */
public class Application {

    public static void main(String[] args) {
        //ArchiveExplorer explorer = new ArchiveExplorer();
        //explorer.navigate();
        //SklFile skl = new SklFile(new File("C:\\Users\\allen_000\\Desktop\\League Cache\\DATA\\Characters\\Shaco\\Skins\\Base\\Shaco.skl"));
        //skl.decode();
        //SknFile skn = new SknFile(new File("C:\\Users\\allen_000\\Desktop\\League Cache\\DATA\\Characters\\Shaco\\Skins\\Base\\Shaco.skn"));
        //skn.decode();
        AnmFile anm = new AnmFile(new File("C:\\Users\\allen_000\\Desktop\\League Cache\\DATA\\Characters\\Shaco\\Skins\\Base\\Animations\\Shaco_Idle1.anm"));
        anm.decode();
    }

}
