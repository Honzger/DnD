package main.code;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Thing extends JLabel {
    private static final long serialVersionUID = 1L;
    public static final String FOLD = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/Items/";

    public int sizeX;
    public int sizeY;
    public String tttrring;

    public int initialWidth;
    public int initialHeight;
    public Point initialPosition;
    public int newWidth;
    public int newHeight;

    public boolean isBurning = false;

    private JsonReader reer = new JsonReader("Items.json");

    public Thing(Dimension dimension, Point location, String tttrring) {
        super();
        this.tttrring = tttrring;
        setSize(dimension);
        setLocation(location);
        createScaledLabel(FOLD + tttrring);
        setVisible(true);
        setOpaque(false);
    }

    public void resizeItem() {
        int[] sizes = reer.getNumbersForFileName(tttrring);
        sizeX = sizes[0];
        sizeY = sizes[1];
        newWidth = sizeX * Utility.scaleW(113);
        newHeight = sizeY * Utility.scaleH(113);
        setSize(newWidth, newHeight);
        createScaledLabel(FOLD + tttrring);
        revalidate();
    }

    public void storeInitialSizeAndPosition() {
        initialWidth = getWidth();
        initialHeight = getHeight();
        initialPosition = getLocation();
    }

    public void createScaledLabel(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        setIcon(scaledIcon);
        setBounds(getX(), getY(), getWidth(), getHeight());
    }
}











