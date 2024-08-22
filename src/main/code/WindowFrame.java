package main.code;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.Serializable;
import javax.swing.*;

public class WindowFrame extends JFrame implements Serializable {
    private static final long serialVersionUID = 1L;
    public JLabel bg;
    private String background = "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/locations/Baziny.jpg";
    public boolean[] Kinkeys = new boolean[10];
    private int Width;
    private int Height;
    public JLayeredPane layeredPane;

    public WindowFrame() {
        GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = graphics.getDefaultScreenDevice();

        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        DisplayMode displayMode = device.getDisplayMode();
        Width = displayMode.getWidth();
        Height = displayMode.getHeight();

        device.setFullScreenWindow(this);
        validate();

        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, Width, Height);
        setContentPane(layeredPane);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    close();
                } else if (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9) {
                    Kinkeys[e.getKeyCode() - KeyEvent.VK_0] = true;
                } else if (e.getKeyCode() == KeyEvent.VK_O) {
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() >= KeyEvent.VK_0 && e.getKeyCode() <= KeyEvent.VK_9) {
                    Kinkeys[e.getKeyCode() - KeyEvent.VK_0] = false;
                }
            }
        });

        setFocusable(true);
        setVisible(true);
    }

    public int getScreenWidth() {
        return Width;
    }

    public int getScreenHeight() {
        return Height;
    }

    public void setBackground(String loc) {
        bg = new JLabel();
        bg.setBounds(0, 0, Width, Height);
        ImageIcon icon = new ImageIcon(loc);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(Width, Height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        bg.setIcon(scaledIcon);
    }

    public void clearComponents() {
        layeredPane.removeAll();
        repaint();
    }

    public void close() {
        dispose();
        System.exit(0);
    }
}

