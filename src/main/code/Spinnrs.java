package main.code;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public class Spinnrs {
    private int currentNumberIndex = 0;
    private int currentImageIndex = 0;

    private final String[] imagePaths = {
        "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/kostky/d4/4.2.png", 
        "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/kostky/d6/6.png",
        "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/kostky/d8/8.png", 
        "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/kostky/d10/0.png",
        "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/kostky/d12/12.png", 
        "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/kostky/d20/20.png"
    };

    private final int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public ScaledImage visdice;
    public JLabel visnr;

    public Spinnrs(Dimension dimensionN, Point pointN, Dimension dimensionI, Point pointI, boolean[] nre) {
        visdice = new ScaledImage(dimensionI, pointI, imagePaths[currentImageIndex]);
        visnr = new JLabel();

        Font font = new Font("Lucida Handwriting", Font.BOLD, 48 * dimensionN.height / 50);
        visnr.setFont(font);
        visnr.setBounds(pointN.x + Utility.scaleW(35), pointN.y, dimensionN.width, dimensionN.height);
        visnr.setText(String.valueOf(numbers[currentNumberIndex]));
        visnr.setHorizontalAlignment(JLabel.CENTER);

        visdice.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
                visdice.setIcon(imagePaths[currentImageIndex]);
            }
        });

        visnr.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i = 0; i < nre.length; i++) {
                    if (nre[i]) {
                        currentNumberIndex = i - 2;
                        break;
                    }
                }
                currentNumberIndex = (currentNumberIndex + 1) % numbers.length;
                visnr.setText(String.valueOf(numbers[currentNumberIndex % numbers.length]));
            }
        });
    }

    public int rightnr() {
        return (currentNumberIndex + 1) % numbers.length;
    }

    public int dicesides() {
        int[] sides = {4, 6, 8, 10, 12, 20};
        return sides[currentImageIndex % sides.length];
    }
}
