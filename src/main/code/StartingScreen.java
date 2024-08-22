package main.code;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLayeredPane;

public class StartingScreen implements GameState {
    private WindowFrame windowFrame;

    private Resizer bag;
    private Resizer map;
    private Dice dices;

    public StartingScreen(WindowFrame windowFrame) {
        this.windowFrame = windowFrame;
        setupScreen();
        handleInput();
    }

    private void setupScreen() {
        windowFrame.clearComponents();
        windowFrame.setBackground(AppState.getInstance().getBackground());

        // Create and add components specific to the starting screen
        bag = new Resizer(1.2, new Dimension(Utility.scaleW(240), Utility.scaleH(240)), new Point(Utility.scaleW(1680), Utility.scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/bag.png");
        map = new Resizer(1.2, new Dimension(Utility.scaleW(200), Utility.scaleH(200)), new Point(Utility.scaleW(20), Utility.scaleH(20)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/Compasss.png");
        dices = new Dice(Utility.scaleW(460), Utility.scaleH(560), Utility.scaleW(1690), Utility.scaleH(800), windowFrame.Kinkeys);

        // Add components to the window frame's layered pane
        windowFrame.layeredPane.add(windowFrame.bg, JLayeredPane.DEFAULT_LAYER);
        windowFrame.layeredPane.add(map, JLayeredPane.PALETTE_LAYER);
        windowFrame.layeredPane.add(bag, JLayeredPane.PALETTE_LAYER);
        windowFrame.layeredPane.add(dices, JLayeredPane.PALETTE_LAYER);

        // Repaint the window frame
        windowFrame.repaint();
    }

    @Override
    public void update() {
        // Implement update logic specific to the starting screen state if needed
    }

    @Override
    public void render(Graphics g) {
        // Use windowFrame's repaint to handle rendering instead of direct rendering
        windowFrame.repaint();
    }

    @Override
    public void handleInput() {
        bag.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                GameStateManager.getInstance().setState(new Inventory(windowFrame));
            }
        });

        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	GameStateManager.getInstance().setState(new Ambience(windowFrame));
            }
        });
    }
}

