package main.code;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;

public class Resizer extends ScaledImage {
    private static final long serialVersionUID = 1L;
    private Dimension originalSize;
    private Point originalLocation;
    private double scaleFactor;
    private String imagePath;

    public Resizer(double scaleFactor, Dimension originalSize, Point location, String imagePath) {
        super(originalSize, location, imagePath);
        this.scaleFactor = scaleFactor;
        this.originalSize = originalSize;
        this.originalLocation = location;
        this.imagePath = imagePath;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resizeOnHover();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetSize();
            }
        });
    }
    public Resizer(Dimension originalSize, Point location, String imagePath) {
        super(originalSize, location, imagePath);
        this.scaleFactor = 1.2;
        this.originalSize = originalSize;
        this.originalLocation = location;
        this.imagePath = imagePath;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                resizeOnHover();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetSize();
            }
        });
    }

    private void resizeOnHover() {
        int newWidth = (int) (originalSize.width * scaleFactor);
        int newHeight = (int) (originalSize.height * scaleFactor);
        Dimension newSize = new Dimension(newWidth, newHeight);
        Point newLocation = new Point(originalLocation.x - (newWidth - originalSize.width) / 2, originalLocation.y - (newHeight - originalSize.height) / 2);

        setSize(newSize);
        setLocation(newLocation);
        scaleIconToLabelSize();
        revalidate();
        repaint();
    }

    private void resetSize() {
        setSize(originalSize);
        setLocation(originalLocation);
        scaleIconToLabelSize();
        revalidate();
        repaint();
    }

    private void scaleIconToLabelSize() {
        ImageIcon icon = new ImageIcon(imagePath);  // Reload the image every time
        java.awt.Image img = icon.getImage();
        java.awt.Image scaledImg = img.getScaledInstance(getWidth(), getHeight(), java.awt.Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImg));
    }
}
