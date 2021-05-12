/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import javax.swing.JPanel;

/**
 *
 * @author cclo
 */
public class LevName extends JPanel {

    Main main;
    int height;
    int width;
    int gap;
    String tName[] = {"A+", "B", "C", "C+", "D", "D+", "E", "F", "F+", "G", "G+", "A",
        "A+", "B", "C", "C+", "D", "D+", "E", "F", "F+", "G", "G+", "A",
        "A+", "B", "C", "C+", "D", "D+", "E", "F", "F+", "G", "G+", "A",
        "A+", "B", "C", "C+", "D", "D+", "E", "F", "F+", "G", "G+", "A",
        "A+", "B", "C", "C+", "D", "D+", "E", "F", "F+", "G", "G+", "A"};

    public LevName(Main main_) {
        super();
        main = main_;
        this.setBackground(Color.WHITE);
    }

    public void init() {
        height = this.getHeight();
        width = this.getWidth();
        gap = width / 50;
        // System.out.println("LevName Resized, width = " + width);
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        repaint();

    }

    Font pFont = new Font("Serif", Font.BOLD, 20);
    public void paint(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < 50; i++) {
            g.setFont(pFont);
            g.drawString(tName[i], gap * i + gap / 2 - 10, 40);
            g.drawLine(gap * i, 0, gap * i, height);
        }
        g.drawLine(gap * 36, 0, gap * 36, height);
    }

}
