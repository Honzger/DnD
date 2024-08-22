package main.code;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

public class InventoryField extends JPanel {
    private static final long serialVersionUID = 1L;

    public int Tilesize = Utility.scaleH(113);
    public int BWithMe = Utility.scaleH(15);
    public Point location;
    private Dimension size;
    private String Tilename = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/backgroundInv.jpg";
    private String borderL = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/borderL.png";
    private String borderU = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/borderU.png";
    private String borderR = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/borderR.png";
    private String borderD = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/borderD.png";
    
    public int LavaLevel;

    private String[][] gridItems;
    private boolean[][] gridOccupied;

    public InventoryField(int centerX, int centerY, int widthT, int heightT) {
        gridOccupied = new boolean[heightT][widthT];
        SwingUtilities.invokeLater(() -> {
            // Calculate the size of the panel
            size = new Dimension(widthT * Tilesize + 2 * BWithMe, heightT * Tilesize + 2 * BWithMe);

            // Calculate the top-left corner position to center the panel
            int x = centerX - size.width / 2;
            int y = centerY - size.height / 2;
            // Set the bounds for the panel
            setBounds(x, y, size.width, size.height);
            setPreferredSize(new Dimension(size.width, size.height));
            setLayout(null); // Ensure absolute positioning

            // Create and add the border image
            ScaledImage RaundL = new ScaledImage(new Dimension(BWithMe, size.height), new Point(0, 0), borderL);
            ScaledImage RaundU = new ScaledImage(new Dimension(size.width, BWithMe), new Point(0, 0), borderU);
            ScaledImage RaundR = new ScaledImage(new Dimension(BWithMe, size.height), new Point(size.width - BWithMe, 0), borderR);
            ScaledImage RaundD = new ScaledImage(new Dimension(size.width, BWithMe), new Point(0, size.height - BWithMe), borderD);
            add(RaundL);
            add(RaundU);
            add(RaundR);
            add(RaundD);

            // Create and add the tile images
            for (int i = 0; i < heightT; i++) {
                for (int j = 0; j < widthT; j++) {
                    ScaledImage imi = new ScaledImage(new Dimension(Tilesize, Tilesize), new Point(BWithMe + j * Tilesize, BWithMe + i * Tilesize), Tilename);
                    add(imi);
                }
            }
        });
    }

    public void AdjustGridPlaced(Point P, Dimension D) {
        for (int i = 0; i < D.width / Tilesize; i++) {
            for (int j = 0; j < D.height / Tilesize; j++) {
                gridOccupied[P.y / Tilesize + j][P.x / Tilesize + i] = true;
            }
        }
    }

    public void AdjustGridRemoved(Point P, Dimension D) {
        for (int i = 0; i < D.width / Tilesize; i++) {
            for (int j = 0; j < D.height / Tilesize; j++) {
                gridOccupied[P.y / Tilesize + j][P.x / Tilesize + i] = false;
            }
        }
    }

    public boolean AdditionLegal(Point P, Dimension D) {
        // Check if the item is within the grid boundaries
        if (P.x < 0 || P.y < 0 || 
            (P.x + D.width) / Tilesize > gridOccupied[0].length ||
            (P.y + D.height) / Tilesize > gridOccupied.length) {
            return false;
        }
        
        for (int i = 0; i < D.width / Tilesize; i++) {
            for (int j = 0; j < D.height / Tilesize; j++) {
                // Ensure the indices are within valid bounds
                int gridX = P.x / Tilesize + i;
                int gridY = P.y / Tilesize + j;
                if (gridX < 0 || gridX >= gridOccupied[0].length || gridY < 0 || gridY >= gridOccupied.length) {
                    return false;
                }
                if (gridOccupied[gridY][gridX]) {
                    return false;
                }
            }
        }
        return true;
    }
}






