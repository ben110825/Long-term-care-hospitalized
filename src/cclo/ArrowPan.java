/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author cclo
 */
public class ArrowPan extends JPanel {

    Main main;
    int level = 0, gap, width, height;

    BufferedImage arrow;

    public ArrowPan(Main main_) {
        main = main_;
        arrow = loadImage();
    }

    public void init() {
        width = this.getWidth();
        height = this.getHeight();
        gap = width / 50;
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        repaint();

    }

    public void drawArrow(int lev_) {
        level = lev_;
        repaint(); 
        level = lev_;

    }

    public void paint(Graphics g) {
        super.paintComponent(g);
        g.drawImage(arrow, gap * level, 0, gap, height, this);

    }

    private BufferedImage loadImage() {
        BufferedImage result = null;
        try {
            result = ImageIO.read(new File("arrow.jpg"));
        } catch (IOException e) {
            System.err.println("Errore, immagine non trovata");
        }

        return result;
    }

}
