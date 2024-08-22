package main.code;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;
import javax.imageio.ImageIO;

public class RandomLineBorder extends JLabel {
    private static final long serialVersionUID = 3L;

    private Queue<Point> queueOne = new LinkedList<>();
    private Queue<Point> queueTwo = new LinkedList<>();

    private int colorone;
    private int colortwo;
    private BufferedImage image;
    private final List<Point> holesSnakeone;
    private final List<Point> holesSnaketwo;
    private int currentHoleIndexOne = 0;
    private int currentHoleIndexTwo = 6;
    private final Random random = new Random();

    private int pixelsPerCall = 10;
    private int width;
    private int height;

    // Specify hole coordinates explicitly
    private final Point[] holeSnakeoneCoordinates = {
        new Point(42, 0), new Point(69, 52), new Point(69, 181), new Point(69, 311), new Point(69, 442), 
        new Point(69, 571), new Point(69, 701), new Point(69, 831), new Point(69, 961), new Point(69, 1091), new Point(69, 1221)
    };
    
    private final Point[] holeSnaketwoCoordinates = {
        new Point(69, 0), new Point(69, 116), new Point(69, 247), new Point(69, 376), new Point(69, 507), 
        new Point(69, 636), new Point(69, 766), new Point(69, 896), new Point(69, 1026), new Point(69, 1156), new Point(69, 1286)
    };

    public RandomLineBorder(int width, int height, int x, int y, String imagePath) {
        setPreferredSize(new Dimension(width, height));
        setBounds(x, y, width, height);
        holesSnakeone = new ArrayList<>();
        holesSnaketwo = new ArrayList<>();
        try {
            image = ImageIO.read(new File(imagePath));
            this.width = image.getWidth();
            this.height = image.getHeight();
            fillHolesWithRandomColors();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillHolesWithRandomColors() {
        if (image == null) {
            return;
        }

        for (int i = 0; i < holeSnakeoneCoordinates.length; i++) {
            int x1 = holeSnakeoneCoordinates[i].x;
            int y1 = holeSnakeoneCoordinates[i].y;
            int x2 = holeSnaketwoCoordinates[i].x;
            int y2 = holeSnaketwoCoordinates[i].y;

            holesSnakeone.add(new Point(x1, y1));
            holesSnaketwo.add(new Point(x2, y2));
            
            if (i == 0) {
                // Set initial colors based on the first coordinates
                colorone = getRandomColor();
                colortwo = getRandomColor();
            }

            instantFloodFill(x1, y1, colorone);
            instantFloodFill(x2, y2, colortwo);
        }
        colorone = getRandomColor();
        colortwo = getRandomColor();
    }

    private int getRandomColor() {
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return (255 << 24) | (red << 16) | (green << 8) | blue;
    }

    private void instantFloodFill(int startX, int startY, int fillColor) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startX, startY));
        int baseColor = image.getRGB(startX, startY);

        while (!queue.isEmpty()) {
            Point p = queue.poll();
            int x = p.x;
            int y = p.y;

            if (x < 0 || x >= width || y < 0 || y >= height) {
                continue;
            }

            int rgba = image.getRGB(x, y);

            // Update the condition to be more explicit
            if (rgba == baseColor) { // Fill only pixels with the base color
                image.setRGB(x, y, fillColor);
                queue.add(new Point(x + 1, y));
                queue.add(new Point(x - 1, y));
                queue.add(new Point(x, y + 1));
                queue.add(new Point(x, y - 1));
            }
        }
    }

    public void floodFillOnePixel(Queue<Point> queue, int fillColor) {
        int pixelsChanged = 0;

        while (!queue.isEmpty() && pixelsChanged < pixelsPerCall) {
            Point p = queue.poll();
            int x = p.x;
            int y = p.y;
            if (x >= 0 && x < width && y >= 0 && y < height) {
                int originalColor = image.getRGB(x, y);

                if (originalColor != fillColor) {
                    image.setRGB(x, y, fillColor);
                    pixelsChanged++;

                    // Add neighboring pixels to the queue in the order down, right, left, up
                    if (y + 1 < height && image.getRGB(x, y + 1) == originalColor) {
                        queue.add(new Point(x, y + 1)); // down
                    }
                    if (x + 1 < width && image.getRGB(x + 1, y) == originalColor) {
                        queue.add(new Point(x + 1, y)); // right
                    }
                    if (x - 1 >= 0 && image.getRGB(x - 1, y) == originalColor) {
                        queue.add(new Point(x - 1, y)); // left
                    }
                    if (y - 1 >= 0 && image.getRGB(x, y - 1) == originalColor) {
                        queue.add(new Point(x, y - 1)); // up
                    }
                }
            }
        }
    }

    public void fillllll() {
        if (queueOne.isEmpty() && currentHoleIndexOne == 0) {
        	colorone = getRandomColor();
		}
        if (queueTwo.isEmpty() && currentHoleIndexTwo == 0) {
        	colortwo = getRandomColor();
		}

        if (queueOne.isEmpty()) {
            queueOne.add(holeSnakeoneCoordinates[currentHoleIndexOne]);
            currentHoleIndexOne = (currentHoleIndexOne + 1) % holeSnakeoneCoordinates.length;
        }

        if (queueTwo.isEmpty()) {
            queueTwo.add(holeSnaketwoCoordinates[currentHoleIndexTwo]);
            currentHoleIndexTwo = (currentHoleIndexTwo + 1) % holeSnaketwoCoordinates.length;
        }

        floodFillOnePixel(queueOne, colorone);
        floodFillOnePixel(queueTwo, colortwo);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
