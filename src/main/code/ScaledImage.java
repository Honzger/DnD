package main.code;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import javax.swing.*;

public class ScaledImage extends JLabel {
    private static final long serialVersionUID = 1L;
    private ImageIcon imageIcon;
    protected Image image;

    public ScaledImage(Dimension size, Point location, String imagePath) {
        setBounds(location.x, location.y, size.width, size.height);
        CreateScaledIcon(imagePath, size);
    }

    private void CreateScaledIcon(String imagePath, Dimension size) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        image = img.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
        this.imageIcon = new ImageIcon(image);
        setIcon(this.imageIcon);
    }

    public void setIcon(String imagePath) {
        SwingUtilities.invokeLater(() -> {
            ImageIcon icon = new ImageIcon(imagePath);
            Image img = icon.getImage();
            Image scaledImage = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            super.setIcon(new ImageIcon(scaledImage));
        });
    }

    public void updateSize(Dimension newSize) {
        setSize(newSize);
        if (imageIcon != null) {
        	CreateScaledIcon(imageIcon.getDescription(), newSize);
        }
    }

    public void updateLocation(Point newLocation) {
        setLocation(newLocation);
    }
}
