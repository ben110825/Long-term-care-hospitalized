package cclo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class PeakPanel extends JPanel{
	static final int SCREEN_WIDTH = 1024;
	static final int SCREEN_HEIGHT = 1200;
	static final int ARRAYLIST_LENGTH = 5;
	ArrayList<ArrayList>peak = new ArrayList();
	int y = 0;
	public PeakPanel() {
		// TODO Auto-generated constructor stub
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.white);
		this.setFocusable(true);

	}
	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		g.setColor(Color.red);
		y = 0;

		for(int i=0;i<peak.size();i++) {
			for(int j=0;j<peak.get(i).size();j++) {
				g.fillOval((int) peak.get(i).get(j), y, 5, 5);
			}
			y+=5;

		}
		
			
		
	}
	public void updatePanel(ArrayList arraylist) {
		peak.add(arraylist);
		repaint();
	}
	
}
