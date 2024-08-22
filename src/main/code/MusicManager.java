package main.code;

import java.util.HashMap;
import java.util.Map;

public class MusicManager {
    private static Map<String, String> musicFiles = new HashMap<>();
    private static MusicPlayer musicPlayer = new MusicPlayer();
    private static String currentTrack;

    // Load a music file into the music manager
    public static void loadMusic(String key, String filePath) {
        musicFiles.put(key, filePath);
    }

    // Play a loaded music file
    public static void playMusic(String key) {
        if (musicFiles.containsKey(key)) {
            stopMusic(); // Stop any currently playing music
            currentTrack = key;
            musicPlayer.play(musicFiles.get(key));
        } else {
            System.out.println("Music key not found: " + key);
        }
    }

    // Stop the current playing music
    public static void stopMusic() {
        musicPlayer.stop();
        currentTrack = null;
    }

    // Get the current playing music track
    public static String getCurrentTrack() {
        return currentTrack;
    }
}
