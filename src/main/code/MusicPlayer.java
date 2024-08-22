package main.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class MusicPlayer {
	private String base = "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Hudba/";
    private AdvancedPlayer player;
    private Thread musicThread;

    public void play(String musicName) {
        musicThread = new Thread(() -> {
            try {
                File musicFile = new File(base + musicName);
                InputStream inputStream = new FileInputStream(musicFile);
                player = new AdvancedPlayer(inputStream);
                player.play();
            } catch (JavaLayerException | FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            stop();
        }));
        musicThread.start();
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
        if (musicThread != null && musicThread.isAlive()) {
            musicThread.interrupt();
        }
    }
}