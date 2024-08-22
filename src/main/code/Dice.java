package main.code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.util.Random;
import java.util.ArrayList;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Dice extends JPanel {
    private static final long serialVersionUID = 2L;
    
	private String mainfolder = "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/kostky/";
	private int dicesize = 60;
    private static int width;
    private static int height;
    private int x;
    private int y;
    private ArrayList<ScaledImage> currentDice;
    private int summ;
    JLabel summer = new JLabel();
    ScaledImage bacgcounter;
    private Color annie;
	boolean isAHundred = false;

    public Dice(int width, int height, int middlex, int middley, boolean[] b) {
    	Dice.width = width;
    	Dice.height = height;
        this.x = middlex - (width / 2);
        this.y = middley - (height / 2);
        setBounds(this.x, this.y, width, height);
        setLayout(null);
        currentDice = new ArrayList<>();
        
        JLabel twosdie = new JLabel();
        JLabel onehdie = new JLabel();

		ScaledImage okraj = new ScaledImage(new Dimension(scaleW(460), scaleH(460)),new Point(scaleW(0), scaleH(100)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/bg5.png");
		ScaledImage bacg0 = new ScaledImage(new Dimension(scaleW(460), scaleH(460)), new Point(scaleW(0), scaleH(100)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/background.jpg");
		ScaledImage okrajk = new ScaledImage(new Dimension(scaleW(100), scaleH(100)), new Point(scaleW(260), scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/GoldOkraj.png");
		ScaledImage okraj2d6 = new ScaledImage(new Dimension(scaleW(80), scaleH(80)), new Point(scaleW(80), scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/GoldOkraj.png");
		ScaledImage okrajd100 = new ScaledImage(new Dimension(scaleW(80), scaleH(80)), new Point(scaleW(0), scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/GoldOkraj.png");
		ScaledImage okrajc = new ScaledImage(new Dimension(scaleW(100), scaleH(100)), new Point(scaleW(160), scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/GoldOkraj.png");
		ScaledImage bacgk = new ScaledImage(new Dimension(scaleW(100), scaleH(100)), new Point(scaleW(260), scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/backgroundspun.jpg");
		ScaledImage okrajcounter = new ScaledImage(new Dimension(scaleW(102), scaleH(100)), new Point(scaleW(360), scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/GoldOkraj.png");
		ScaledImage bacg2d6 = new ScaledImage(new Dimension(scaleW(80), scaleH(80)), new Point(scaleW(80), scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/backgroundspun.jpg");
		ScaledImage bacgd100 = new ScaledImage(new Dimension(scaleW(80), scaleH(80)), new Point(scaleW(0), scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/backgroundspun.jpg");
		ScaledImage bacgc = new ScaledImage(new Dimension(scaleW(160), scaleH(100)), new Point(scaleW(140), scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/backgroundspun.jpg");
		bacgcounter = new ScaledImage(new Dimension(scaleW(100), scaleH(100)), new Point(scaleW(360), scaleH(0)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/backgroundspun.jpg");
		ScaledImage buttonb = new ScaledImage(new Dimension(scaleW(140), scaleH(20) + 1), new Point(scaleW(0), scaleH(80)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/backgroundspun.jpg");
		ScaledImage button = new ScaledImage(new Dimension(scaleW(140), scaleH(20) + 1), new Point(scaleW(10), scaleH(80)), "C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/knopf.png");
		Spinnrs spinnrs = new Spinnrs(new Dimension(scaleW(75), scaleH(50)), new Point(scaleW(235), scaleH(25)), new Dimension(scaleW(50), scaleH(50)), new Point(scaleW(185), scaleH(25)), b);
		
		Font fonto = new Font("Lucida Handwriting", Font.BOLD, scaleH(15));
		Font fontt = new Font("Lucida Handwriting", Font.BOLD, scaleH(20));		
		Font fonts = new Font("Lucida Handwriting", Font.BOLD, scaleH(48));
		summer.setBounds(scaleW(355), scaleH(0), scaleW(100), scaleH(100));
		summer.setFont(fonts);
		summer.setText(String.valueOf(summ));
		summer.setHorizontalAlignment(JLabel.CENTER);
		annie = summer.getForeground();
		onehdie.setBounds(scaleW(0), scaleH(0), scaleW(75), scaleW(80));
		onehdie.setFont(fonto);
		onehdie.setText("1d100");
		onehdie.setHorizontalAlignment(JLabel.CENTER);
		twosdie.setBounds(scaleW(80), scaleH(0), scaleW(80), scaleH(80));
		twosdie.setFont(fontt);
		twosdie.setText("2d6");
		twosdie.setHorizontalAlignment(JLabel.CENTER);

		okraj2d6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                clearCurrentDice();
				Random random = new Random();
				int fnr = random.nextInt(6) + 1;
				int snr = random.nextInt(6) + 1;
				summ = fnr + snr;
				String locf = mainfolder + "d6/" + fnr + ".png";
				String locs = mainfolder + "d6/" + snr + ".png";
				ScaledImage firstdice = new ScaledImage(new Dimension(scaleW(dicesize), scaleH(dicesize)), new Point(scaleW(120), scaleH(300)), locf);
				ScaledImage seconddice = new ScaledImage(new Dimension(scaleW(dicesize), scaleH(dicesize)), new Point(scaleW(280), scaleH(300)), locs);
				addDice(firstdice, seconddice);
				isAHundred = false;
			}
		});

		okrajd100.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                clearCurrentDice();
				Random random = new Random();
				int fnr = random.nextInt(10);
				int snr = random.nextInt(10);
				summ = fnr * 10 + snr;
				String locf = mainfolder + "d10/" + fnr + ".png";
				String locs = mainfolder + "d10/" + snr + ".png";
				ScaledImage firstdice = new ScaledImage(new Dimension(scaleW(dicesize), scaleH(dicesize)), new Point((120), scaleH(300)), locf);
				ScaledImage seconddice = new ScaledImage(new Dimension(scaleW(dicesize), scaleH(dicesize)), new Point(scaleW(280), scaleH(300)), locs);
				addDice(firstdice, seconddice);
				isAHundred = false;
			}
		});
		
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                clearCurrentDice();
                String loc;
				summ = 0;
				Random random = new Random();
				
				int[][] x_coor = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 230, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 150, 310, 0, 0, 0, 0, 0, 0, 0, 0 }, { 100, 230, 360, 0, 0, 0, 0, 0, 0, 0 }, 
						{ 150, 150, 310, 310, 0, 0, 0, 0, 0, 0 }, { 100, 100, 230, 360, 360, 0, 0, 0, 0, 0 }, 
						{ 150, 150, 150, 310, 310, 310, 0, 0, 0, 0 }, { 100, 100, 100, 230, 360, 360, 360, 0, 0, 0 }, 
						{ 100, 100, 100, 230, 230, 360, 360, 360, 0, 0 }, { 100, 100, 100, 230, 230, 230, 360, 360, 360, 0 }, 
						{ 100, 100, 100, 230, 230, 230, 230, 360, 360, 360 } };

		        int[][] y_coor = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 330, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
		        		{ 330, 330, 0, 0, 0, 0, 0, 0, 0, 0 }, { 200, 330, 460, 0, 0, 0, 0, 0, 0, 0 }, 
		        		{ 250, 410, 250, 410, 0, 0, 0, 0, 0, 0 }, { 200, 460, 330, 200, 460, 0, 0, 0, 0, 0 }, 
		        		{ 200, 330, 460, 200, 330, 460, 0, 0, 0, 0 }, { 200, 330, 460, 330, 200, 330, 460, 0, 0, 0 }, 
		        		{ 200, 330, 460, 270, 430, 200, 330, 460, 0, 0 }, { 200, 330, 460, 200, 330, 460, 200, 330, 460, 0 }, 
		        		{ 200, 330, 460, 165, 275, 385, 495, 200, 330, 460 } };
		        
				for (int i = 0; i < spinnrs.rightnr(); i++) {
					int dthrow = random.nextInt(spinnrs.dicesides()) + 1;
					if (spinnrs.dicesides() == 4) {
						summ += dthrow;
						int athrow = random.nextInt(3) + 1;
						loc = mainfolder + "d" + spinnrs.dicesides() + "/" + dthrow + "." + athrow + ".png";
					}
					else if (spinnrs.dicesides() == 10) {
						dthrow--;
						if (dthrow == 0) {
							summ += 10;
						} else summ += dthrow;
						loc = mainfolder + "d" + spinnrs.dicesides() + "/" + dthrow + ".png";
					}
					else {
						summ += dthrow;
						loc = mainfolder + "d" + spinnrs.dicesides() + "/" + dthrow + ".png";
					}
					addDice(new ScaledImage (new Dimension(scaleW(dicesize), scaleH(dicesize)), new Point(scaleW(x_coor[spinnrs.rightnr()][i] - dicesize / 2), scaleH(y_coor[spinnrs.rightnr()][i] - dicesize / 2)), loc));
				}
				isAHundred = false;
			}
		});

		add(summer);
		add(okrajcounter);
		add(bacgcounter);
		add(okraj);
		add(bacg0);
		add(okrajk);
		add(okraj2d6);
		add(okrajd100);
		add(okrajc);
		add(bacgk);
		add(bacg2d6);
		add(bacgd100);
		add(bacgc);
		add(button);
		add(buttonb);
		add(onehdie);
		add(twosdie);
		add(spinnrs.visdice);
		add(spinnrs.visnr);
        setComponentZOrder(spinnrs.visdice, 0);
        setComponentZOrder(spinnrs.visnr, 0);
        setComponentZOrder(onehdie, 0);
        setComponentZOrder(twosdie, 0);
    }
    
    private void clearCurrentDice() {
        for (ScaledImage dice : currentDice) {
            remove(dice);
        }
        currentDice.clear();
    }

    private void addDice(ScaledImage... dice) {
        for (ScaledImage die : dice) {
            add(die);
            setComponentZOrder(die, 0);
            currentDice.add(die);
        }
        if(summ >= 100) {
        	isAHundred = true;
        }
        if(!isAHundred) {
        	bacgcounter.setIcon("C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/backgroundspun.jpg");
        	summer.setForeground(annie);
    	} else {
        	summ = summ % 100;
        	bacgcounter.setIcon("C:/Users/Mainuser/eclipse-workspace/Drd4/src/Obrazky/fireBG.jpg");
        	summer.setForeground(Color.YELLOW);
        }
    	summer.setText(String.valueOf(summ));
        revalidate();
        repaint();
    }

	private static int scaleW(int toscale) {
		return toscale * width / 460;
	}
	
	private static int scaleH(int toscale) {
		return toscale * height / 560;
	}
}
