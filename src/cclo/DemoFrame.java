/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 *
 * @author ben11
 */
public class DemoFrame extends JFrame {
	int y = 250;
	ArrayList isPeak = new ArrayList();
	JPanel voicePanel = new JPanel();
	static JButton btRecord = new JButton("Record");
	JButton btStop = new JButton("Stop");

	DemoFrame() {
		voicePanel.add(btRecord);
		voicePanel.add(btStop);
		add(voicePanel);
		setTitle("TEST");
		setBounds(255, 255, 1300, 1024);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	public void paint(Graphics g) {
		voicePanel.setBackground(Color.gray);
		btRecord.setBackground(Color.yellow);
		voicePanel.setBounds(0, 0, 1300, 200);

		if (y > 1024) {
			g.clearRect(0, 250, 1300, 1024);
			y = 250;
		}
		for (int i = 0; i < 1024; i++) {
			g.setColor(Color.red);
			if (isPeak.contains(i)) {
				g.fillOval(i + 10, y, 5, 5);
			}
		}
		y += 5;
	}

	public void putData(ArrayList Peak) {
		isPeak.clear();
		isPeak = Peak;
		repaint();
	}

	public static void main() {

	}
}
