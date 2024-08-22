package main.code;

import javax.swing.*;
import java.awt.*;

// MusicTile class
public class MusicTile extends JPanel {
    private static final long serialVersionUID = 1L;

    private String base = "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/locations/";
    private int TWidth = Utility.scaleW(400);
    private int THeight = Utility.scaleH(300);
    private int Textfield = Utility.scaleH(40);
    private ScaledImage border = new ScaledImage(new Dimension(TWidth, THeight - Textfield), new Point(0, Textfield), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/bg5.png");
    private ScaledImage loc;
    private JLabel Text = new JLabel();

    public MusicTile(String name, int x, int y) {
        this.setLayout(null);
        this.setBounds(x - TWidth / 2, y - THeight / 2, TWidth, THeight);
        this.setOpaque(false);
        setBackground(Color.LIGHT_GRAY); // Temporary background to visualize the tile
        setVisible(true);

        this.loc = new ScaledImage(new Dimension(TWidth, THeight - Textfield), new Point(0, Textfield), base + name);
        Font font = new Font("Lucida Handwriting", Font.BOLD, Utility.scaleH(40));
        Text.setBounds(Utility.scaleW(0), Utility.scaleH(0), TWidth, Textfield);
        Text.setFont(font);
        Text.setHorizontalAlignment(JLabel.CENTER);
        Text.setText(name.substring(0, name.length() - 4));
        Text.setOpaque(false);

        this.add(Text);
        this.add(border);
        this.add(loc);
    }
}
