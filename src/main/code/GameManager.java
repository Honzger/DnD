package main.code;

public class GameManager {
    private static GameManager instance;
    private MusicPlayer musicPlayer;
    
    private String music = "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/Hudba/";

    private GameManager() {
        // Initialize other game-wide settings or resources here
        musicPlayer = new MusicPlayer();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    // Example method to play music
    public void playMusic(String location) {
        musicPlayer.play(music + location);
    }

    // Example method to stop music
    public void stopMusic() {
        musicPlayer.stop();
    }
}
