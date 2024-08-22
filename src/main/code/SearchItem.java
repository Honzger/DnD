package main.code;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SearchItem extends JPanel {
    private static final long serialVersionUID = 1L;

    private File itemsDirectory = new File("C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/Items");
    private int maxItems = 10;
    private int itemsCount;
    private int width;
    private int height;
    private int borderWidth = Utility.scaleH(15);
    private int headerHeight = Utility.scaleH(100);
    private int TileSize = Utility.Tile;
    private String tileImage = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/Tiile2.png";
    private String borderL = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/borderL.png";
    private String borderU = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/borderU.png";
    private String borderR = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/borderR.png";
    private String borderD = "C:/Users/Mainuser/eclipse-workspace/DnD/src/Obrazky/borderD.png";
    private JsonReader jsonReader = new JsonReader("Items.json");
    public Thing[] Things;

    public SearchItem(int x, int y) {
        String[] itemNames = itemsDirectory.list();
        itemsCount = itemNames.length;
        Things = new Thing[Math.min(itemsCount, maxItems)];

        width = Utility.getWidth() / 4;
        height = Utility.getHeight();

        setBounds(x, y, width, height);
        setLayout(null);

        JLabel searchLabel = new JLabel();
        Font font = new Font("Lucida Handwriting", Font.BOLD, Utility.scaleH(32));
        searchLabel.setBounds(borderWidth, borderWidth, Utility.scaleW(230), headerHeight);
        searchLabel.setFont(font);
        searchLabel.setHorizontalAlignment(JLabel.CENTER);
        searchLabel.setText("Vyhledavac:");
        searchLabel.setOpaque(false);

        TexxtField textField = new TexxtField(Utility.scaleW(250), borderWidth, Utility.scaleW(200), Utility.scaleH(90));

        ScaledImage background = new ScaledImage(new Dimension(width, height), new Point(0, 0), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/background.jpg");
        ScaledImage borderLeft = new ScaledImage(new Dimension(borderWidth, height), new Point(0, 0), borderL);
        ScaledImage borderTop = new ScaledImage(new Dimension(width, borderWidth), new Point(0, 0), borderU);
        ScaledImage borderBottom = new ScaledImage(new Dimension(width, borderWidth), new Point(0, height - borderWidth), borderD);
        ScaledImage borderRight = new ScaledImage(new Dimension(borderWidth, height), new Point(width - borderWidth, 0), borderR);

        for (int j = 0; j < Things.length && j < maxItems; j++) {
            int border = Utility.scaleH(10);
            int tileY = j * (height - (headerHeight + 2 * borderWidth)) / maxItems + Utility.scaleH(border / 2) + headerHeight + borderWidth;
            int tileHeight = (height - (headerHeight + 2 * borderWidth)) / maxItems - Utility.scaleH(border);

            int[] dimensions = jsonReader.getNumbersForFileName(itemNames[j]);
            int itemWidth = dimensions[0];
            int itemHeight = dimensions[1];
            float aspectRatio = (float) itemWidth / (float) itemHeight;

            // Determine max dimensions for the item image
            int maxItemWidth = Utility.scaleW(80);
            int maxItemHeight = tileHeight;

            // Calculate the scaled dimensions
            int scaledWidth, scaledHeight;
            if (aspectRatio > 1) {
                scaledWidth = maxItemWidth;
                scaledHeight = (int) (maxItemWidth / aspectRatio);
            } else {
                scaledHeight = maxItemHeight;
                scaledWidth = (int) (maxItemHeight * aspectRatio);
            }

            // Center the item within the tile
            int centeredX = 2 * borderWidth + (maxItemWidth - scaledWidth) / 2;
            int centeredY = tileY + (maxItemHeight - scaledHeight) / 2;

            Thing item = new Thing(new Dimension(scaledWidth, scaledHeight), new Point(centeredX, centeredY), itemNames[j]);
            Things[j] = item;

            JLabel nameLabel = new JLabel();
            Font nameFont = new Font("Lucida Handwriting", Font.BOLD, Utility.scaleH(42));
            nameLabel.setBounds(2 * borderWidth + maxItemWidth, tileY, width - (2 * borderWidth + 2 * Utility.scaleW(80)), tileHeight);
            nameLabel.setFont(nameFont);
            nameLabel.setHorizontalAlignment(JLabel.LEFT);
            nameLabel.setText(itemNames[j].substring(0, 1).toUpperCase() + itemNames[j].substring(1, itemNames[j].length() - 4));
            nameLabel.setOpaque(false);

            JLabel sizeLabel = new JLabel();
            Font sizeFont = new Font("Lucida Handwriting", Font.BOLD, Utility.scaleH(50));
            sizeLabel.setBounds(width - (borderWidth + TileSize), tileY, TileSize, tileHeight);
            sizeLabel.setFont(sizeFont);
            sizeLabel.setText(dimensions[0] + "x" + dimensions[1]);
            sizeLabel.setOpaque(false);

            add(sizeLabel);
            add(nameLabel);
        }

        for (int i = 0; i < maxItems; i++) {
            int border = Utility.scaleH(10);
            int tileY = i * (height - (headerHeight + 2 * borderWidth)) / maxItems + Utility.scaleH(border / 2) + headerHeight + borderWidth;
            int tileHeight = (height - (headerHeight + 2 * borderWidth)) / maxItems - Utility.scaleH(border);
            ScaledImage tillImage = new ScaledImage(new Dimension(Utility.scaleW(80), tileHeight), new Point(2 * borderWidth, tileY), tileImage);

            if (i != 0) {
                ScaledImage destroyerImage = new ScaledImage(new Dimension(width, borderWidth), new Point(0, tileY - borderWidth), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/pstruh.png");
                add(destroyerImage);
            }

            add(tillImage);
        }

        add(searchLabel);
        add(textField);
        add(borderLeft);
        add(borderTop);
        add(borderRight);
        add(borderBottom);
        add(background);
    }

    public int getItemsCount() {
        return itemsCount;
    }
}
