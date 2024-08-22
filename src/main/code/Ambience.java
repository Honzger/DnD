package main.code;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.SwingUtilities;

public class Ambience implements GameState {
    private WindowFrame windowFrame;
    private Resizer exit;
    private Resizer leaved;
    private Resizer bright;
    private File folder;
    private File[] listOfFiles;
    private int sides;
    private int currSide = 0;
    private String[] bglocs;
    private MusicTile[] tiles;
    private final Point[] tilePositions = {
        new Point(Utility.scaleW(400), Utility.scaleH(300)), new Point(Utility.scaleW(960), Utility.scaleH(300)), new Point(Utility.scaleW(1520), Utility.scaleH(300)),
        new Point(Utility.scaleW(400), Utility.scaleH(780)), new Point(Utility.scaleW(960), Utility.scaleH(780)), new Point(Utility.scaleW(1520), Utility.scaleH(780))
    };

    public Ambience(WindowFrame windowFrame) {
        this.windowFrame = windowFrame;
        setupScreen();
        handleInput();
    }

    private void setupScreen() {
        folder = new File("Data/Img/locations/");
        listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            System.err.println("Folder does not exist or is empty: " + folder.getAbsolutePath());
            listOfFiles = new File[0];
        }
        bglocs = new String[listOfFiles.length];
        tiles = new MusicTile[bglocs.length];
        for (int i = 0; i < listOfFiles.length; i++) {
            bglocs[i] = listOfFiles[i].getName();
        }
        sides = bglocs.length;
        windowFrame.clearComponents();
        windowFrame.setBackground("Data/Img/backgroundspun.jpg");

        // Create and add exit button
        exit = new Resizer(new Dimension(Utility.scaleW(100), Utility.scaleH(100)), new Point(Utility.scaleW(1820), Utility.scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/cross.png");
        leaved = new Resizer(new Dimension(Utility.scaleW(100), Utility.scaleH(100)), new Point(Utility.scaleW(250), Utility.scaleH(490)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/sipkaL.png");
        bright = new Resizer(new Dimension(Utility.scaleW(100), Utility.scaleH(100)), new Point(Utility.scaleW(1570), Utility.scaleH(490)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/sipkaR.png");

        // Generate initial Music Tiles
        generateMusicTiles();
    }

    private void generateMusicTiles() {
        SwingUtilities.invokeLater(() -> {
            // Remove existing Music Tiles
            windowFrame.clearComponents();

            // Create and add music tiles
            for (int i = currSide * tilePositions.length; i < Math.min(bglocs.length, (currSide + 1) * tilePositions.length); i++) {
                String location = bglocs[i];
                int posX = tilePositions[i % tilePositions.length].x;
                int posY = tilePositions[i % tilePositions.length].y;

                tiles[i] = new MusicTile(location, posX, posY);

                tiles[i].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        AppState.getInstance().setBackground("Data/Img/locations/" + location);
                        //Utility.playMusic(location.replace("jpg", "mp3").replace("png", "mp3")); // Play music for the selected location
                        GameStateManager.getInstance().setState(new StartingScreen(windowFrame));
                    }
                });

                windowFrame.add(tiles[i]);
            }

            // Add navigation buttons and exit button
            if (sides > 6 && currSide != 0) {
                windowFrame.add(leaved);
            }
            if (sides > 6 && sides / 6 != currSide) {
                windowFrame.add(bright);
            }
            windowFrame.add(exit);
            windowFrame.add(windowFrame.bg);

            // Revalidate and repaint the window frame
            windowFrame.revalidate();
            windowFrame.repaint();
        });
    }

    @Override
    public void update() {
        // Implement update logic if needed
    }

    @Override
    public void render(Graphics g) {
        // Rendering is handled by Swing's repaint mechanism
        // No need to call setupScreen here
    }

    @Override
    public void handleInput() {
        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    GameStateManager.getInstance().setState(new StartingScreen(windowFrame));
                });
            }
        });

        leaved.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    currSide = (currSide - 1 + (sides / 6 + 1)) % (sides / 6 + 1);
                    generateMusicTiles();
                });
            }
        });

        bright.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SwingUtilities.invokeLater(() -> {
                    currSide = (currSide + 1) % (sides / 6 + 1);
                    generateMusicTiles();
                });
            }
        });
    }
}
