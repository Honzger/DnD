package main.code;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import javazoom.jl.player.advanced.jlap;

public class Inventory implements GameState {
    private WindowFrame windowFrame;
    private JLabel Text = new JLabel();
    private Color C = new Color(238, 218, 179);
    private InventoryField bagfield;
    private Thing[] iits;
    private List<Item> MyItems = new ArrayList<Item>();
    private String Fold = "Data/Img/Items/";
    private Resizer exit;
    private Resizer bag;
    private RandomLineBorder edge; // Comment out RandomLineBorder
    private ScaledImage fire;
    private Point initialClick;

    public Inventory(WindowFrame windowFrame) {
        this.windowFrame = windowFrame;
        setupScreen();
        handleInput(); // Ensure listeners are set up
    }

    private void setupScreen() {
        windowFrame.clearComponents();
        windowFrame.setBackground("C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/hPozadi.jpg");

        exit = new Resizer(1.2, new Dimension(Utility.scaleW(100), Utility.scaleH(100)), new Point(Utility.scaleW(400), Utility.scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/cross.png");
        bag = new Resizer(1.2, new Dimension(Utility.scaleW(300), Utility.scaleH(300)), new Point(Utility.scaleW(10), Utility.scaleH(70)), "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/bag.png");

        // Comment out RandomLineBorder initialization
        edge = new RandomLineBorder(Utility.scaleW(112), Utility.scaleH(1080), Utility.scaleW(300), Utility.scaleH(0), "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/myline.png");

        SearchItem search = new SearchItem(3 * Utility.getWidth() / 4, 0);

        fire = new ScaledImage(new Dimension(Utility.scaleW(1084), Utility.scaleH(150)), new Point(Utility.scaleW(356), Utility.scaleH(930)), "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/fireBG.jpg");

        iits = search.Things;

        Font font = new Font("Lucida Handwriting", Font.BOLD, Utility.scaleH(42));
        Text.setBounds(Utility.scaleW(0), Utility.scaleH(0), Utility.scaleW(300), Utility.scaleH(100));
        Text.setFont(font);
        Text.setHorizontalAlignment(JLabel.CENTER);
        Text.setForeground(C);
        Text.setText("Inventár");
        Text.setOpaque(false);

        windowFrame.setLayout(null); // Setting layout to null to manually set bounds of components

        for (Thing thing : iits) {
        	thing.setBounds(thing.getX() + windowFrame.getWidth() * 3 / 4, thing.getY(), thing.getWidth(), thing.getHeight());
            windowFrame.add(thing);
        }

        windowFrame.add(Text);
        windowFrame.add(search);
        windowFrame.add(edge);
        windowFrame.add(fire);
        windowFrame.add(bag);
        windowFrame.add(exit);
        windowFrame.add(windowFrame.bg);

        windowFrame.revalidate();
        windowFrame.repaint();
        windowFrame.setVisible(true);
    }

    @Override
    public void update() {
        edge.fillllll();
        edge.repaint();
        for (Item item : MyItems) {
            if (item.isBurning) {
                item.burn();
            }
        }
    }

    @Override
    public void render(Graphics g) {
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

        bag.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                bagfield = new InventoryField(Utility.getWidth() / 2, Utility.getHeight() / 2, 5, 5);
                bagfield.LavaLevel = fire.getLocation().y;

                Container contentPane = windowFrame.getContentPane();
                contentPane.add(bagfield);
                contentPane.setComponentZOrder(bagfield, 0);

                contentPane.revalidate();
                contentPane.repaint();
            }
        });

        for (Thing thing : iits) {
        	thing.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                	thing.storeInitialSizeAndPosition();
                	thing.resizeItem();
                    windowFrame.layeredPane.setLayer(thing, JLayeredPane.DRAG_LAYER); // Bring to the front during drag
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    Point dropPoint = e.getPoint();

                    if (bagfield != null) {
                        Point inPoint = new Point(
                                e.getLocationOnScreen().x - bagfield.getLocationOnScreen().x - ((e.getLocationOnScreen().x - bagfield.getLocationOnScreen().x - bagfield.BWithMe) % bagfield.Tilesize),
                                e.getLocationOnScreen().y - bagfield.getLocationOnScreen().y - ((e.getLocationOnScreen().y - bagfield.getLocationOnScreen().y - bagfield.BWithMe) % bagfield.Tilesize)
                        );
                        AddItem(thing.getSize(), inPoint, thing.tttrring);
                    }
                    thing.setSize(thing.initialWidth, thing.initialHeight);
                    thing.setLocation(thing.initialPosition);
                    thing.createScaledLabel(Thing.FOLD + thing.tttrring);
                    windowFrame.layeredPane.setLayer(thing, JLayeredPane.PALETTE_LAYER); // Reset to normal layer
                }
            });

        	thing.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point currentMousePosition = SwingUtilities.convertPoint(thing, e.getPoint(), windowFrame.layeredPane);
                    Point newLocation = new Point(
                            currentMousePosition.x - Utility.Tile / 2,
                            currentMousePosition.y - Utility.Tile / 2
                    );

                    thing.setLocation(newLocation);
                }
            });
        }
    }

    public void AddItem(Dimension inDim, Point inPoint, String name) {
        if (bagfield.AdditionLegal(inPoint, inDim)) {
        	bagfield.AdjustGridPlaced(inPoint, inDim);

            Item Addition = new Item(inDim, inPoint, Fold + name);
            
            MyItems.add(Addition);

            Addition.setName(name);
            Addition.setBounds(inPoint.x + bagfield.getLocation().x, inPoint.y + bagfield.getLocation().y, inDim.width, inDim.height);

            windowFrame.layeredPane.add(Addition, JLayeredPane.PALETTE_LAYER);

            Addition.addMouseListener(new MouseAdapter() {
                private Point initialLocation;

                @Override
                public void mousePressed(MouseEvent e) {
                    initialClick = e.getPoint();
                    initialLocation = Addition.getLocation();
                    bagfield.AdjustGridRemoved(new Point(initialLocation.x - bagfield.getX(), initialLocation.y - bagfield.getY()), inDim);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                	if (!Addition.isBurning) {
	                	Point releasePoint = SwingUtilities.convertPoint(Addition, e.getPoint(), bagfield);
	
	                    Point gridAlignedPoint = new Point(
	                            releasePoint.x - ((releasePoint.x - bagfield.BWithMe) % bagfield.Tilesize) - (initialClick.x / bagfield.Tilesize) * bagfield.Tilesize,
	                            releasePoint.y - ((releasePoint.y - bagfield.BWithMe) % bagfield.Tilesize) - (initialClick.y / bagfield.Tilesize) * bagfield.Tilesize
	                    );
	
	                    if (bagfield.AdditionLegal(gridAlignedPoint, inDim)) {
	                    	windowFrame.layeredPane.remove(Addition);
	                        AddItem(inDim, gridAlignedPoint, Addition.getName());
	                        bagfield.AdjustGridRemoved(new Point(initialLocation.x - bagfield.getX(), initialLocation.y - bagfield.getY()), inDim);
	                    } else {
	                        Addition.setLocation(initialLocation);
	                        bagfield.AdjustGridPlaced(new Point(initialLocation.x - bagfield.getX(), initialLocation.y - bagfield.getY()), inDim);
	                    }
	
	                    windowFrame.revalidate();
	                    windowFrame.repaint();
                	}
                }
            });

            Addition.addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                	if (!Addition.isBurning) {
                        Point current = MouseInfo.getPointerInfo().getLocation();
                        if (current.y + Addition.getLowestPaintedPoint().y > fire.getY()) {
                        	Addition.startBurn(Addition.getLowestPaintedPoint());
                        }
                        int x = current.x - initialClick.x;
                        int y = current.y - initialClick.y;
                        Addition.setLocation(x, y);                		
                	}
                }
            });
        }
    }
}
