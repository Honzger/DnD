package main.code;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

public class Item extends ScaledImage {
    private static final long serialVersionUID = 1L;

    private final Queue<Point> burning = new LinkedList<>();
    private BufferedImage buffImage;
    private int pixelsPerCall = 10;
    public boolean isBurning;

    public Item(Dimension size, Point location, String imagePath) {
        super(size, location, imagePath);
        buffImage = toBufferedImage(image); // Convert initial image to BufferedImage
    }

    public void burn() {
        if (buffImage == null) {
            System.out.println("Buffimage is null");
            return;
        }

        int pixelsChanged = 0;
        while (!burning.isEmpty() && pixelsChanged < pixelsPerCall) {
            Point curr = burning.poll();
            if (curr.x < 0 || curr.x >= buffImage.getWidth() || curr.y < 0 || curr.y >= buffImage.getHeight()) {
                continue; // Skip if the point is out of bounds
            }

            int color = buffImage.getRGB(curr.x, curr.y);
            int alpha = (color >> 24) & 0xff; // Extract alpha value
            int red = (color >> 16) & 0xff; // Extract red channel
            int green = (color >> 8) & 0xff; // Extract green channel
            int blue = color & 0xff; // Extract blue channel

            // Adjust color channels based on the logic
            if (red < 255) {
                red = Math.min(255, red + 5);
            } else if (green > 0 || blue > 0) {
                green = Math.max(0, green - 5);
                blue = Math.max(0, blue - 5);
            } else if (alpha > 0) {
                alpha = Math.max(0, alpha - 5);
            } else {
                continue; // No changes needed
            }

            // Recreate the modified color
            int newColor = (alpha << 24) | (red << 16) | (green << 8) | blue;
            System.out.println(color + " " + newColor);
            buffImage.setRGB(curr.x, curr.y, newColor); // Apply modified color to BufferedImage
            pixelsChanged++;

            // Add neighboring pixels to the queue for further burning
            if (curr.x - 1 >= 0) burning.add(new Point(curr.x - 1, curr.y));
            if (curr.x + 1 < buffImage.getWidth()) burning.add(new Point(curr.x + 1, curr.y));
            if (curr.y - 1 >= 0) burning.add(new Point(curr.x, curr.y - 1));
            if (curr.y + 1 < buffImage.getHeight()) burning.add(new Point(curr.x, curr.y + 1));
            burning.add(curr);
        }

        if (burning.isEmpty()) {
            isBurning = false; // Stop burning if the queue is empty
        }

        repaint(); // Trigger repaint to update the displayed image
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (buffImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.drawImage(buffImage, 0, 0, getWidth(), getHeight(), null); // Draw BufferedImage
            g2d.dispose();
        }
    }

    public void startBurn(Point point) {
        if (point == null || point.x < 0 || point.x >= buffImage.getWidth() || point.y < 0 || point.y >= buffImage.getHeight()) {
            System.out.println("Invalid start point for burning: " + point);
            return;
        }

        isBurning = true;
        System.out.println("Item started burning at: " + point);
        burning.add(point); // Start burning from the given point
    }

    public Point getLowestPaintedPoint() {
        for (int y = buffImage.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < buffImage.getWidth(); x++) {
                int color = buffImage.getRGB(x, y);
                int alpha = (color >> 24) & 0xff; // Extract alpha value
                if (alpha != 0) {
                    return new Point(x, y); // Return the first non-transparent pixel
                }
            }
        }
        return null; // No visible pixels found
    }

    public static BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img; // Return the BufferedImage if it already is one
        }

        // Create a new BufferedImage and draw the Image onto it
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        return bimage;
    }
}
