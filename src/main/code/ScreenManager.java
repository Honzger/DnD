package main.code;

import java.awt.Dimension;
import java.awt.Toolkit;

public class ScreenManager {
    private static ScreenManager instance;
    private Dimension screenSize;

    private ScreenManager() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager();
        }
        return instance;
    }

    public int getScreenWidth() {
        return screenSize.width;
    }

    public int getScreenHeight() {
        return screenSize.height;
    }

    public int scaleW(int width) {
        return (int) ((double) width / 1920 * screenSize.width);
    }

    public int scaleH(int height) {
        return (int) ((double) height / 1080 * screenSize.height);
    }
}
