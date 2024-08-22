package main.code;

public class Utility {
	private static MusicPlayer musicPlayer = new MusicPlayer();
    public static String music = "";
    public static int Tile = scaleW(113);

    public static void playMusic(String musicPath) {
        if (!musicPath.equals(music)) {
            musicPlayer.stop();
            musicPlayer.play(musicPath);
            music = musicPath;
        }
    }
    
    public static void stopMusic() {
        musicPlayer.stop();
        music = "";
    }
    public static int scaleW(int width) {
        return ScreenManager.getInstance().scaleW(width);
    }

    public static int scaleH(int height) {
        return ScreenManager.getInstance().scaleH(height);
    }

    public static int getWidth() {
        return ScreenManager.getInstance().getScreenWidth();
    }

    public static int getHeight() {
        return ScreenManager.getInstance().getScreenHeight();
    }
    
    public static void loadMusic(String key, String filePath) {
        MusicManager.loadMusic(key, filePath);
    }

    public static String getCurrentMusicTrack() {
        return MusicManager.getCurrentTrack();
    }
}
