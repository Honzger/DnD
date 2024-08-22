package main.code;

public class AppState {
    private static AppState instance;
    private String background = "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/locations/Baziny.jpg";

    private AppState() {
        // Private constructor to prevent instantiation
    }

    public static synchronized AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
