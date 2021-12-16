/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclo;


import javax.swing.JFrame;

/**
 *
 * @author ben11
 */
public class DemoFrame extends JFrame {
	Main main;
	PeakPanel panel;
	
	
	DemoFrame(Main main_) {
		main = main_;
		panel = new PeakPanel(main);
		this.add(panel);
		this.setTitle("Peak Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);	
	}

	
}
