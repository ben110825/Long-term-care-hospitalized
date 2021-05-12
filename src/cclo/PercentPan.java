/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cclo;

import java.awt.*;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Melissa
 */
public class PercentPan extends JPanel {

    Main main;
    int levCounter[] = new int[100];
    int current;
    int max, cNo = 0, tNo = 0;
    int extLev;
    double ratio = 0.0;
    JTextField tfRatio = new JTextField();
    JTextField tfError = new JTextField();
    JLabel lbLevel = new JLabel("正確比例");
    JLabel lbError = new JLabel("--");
    DecimalFormat df = new DecimalFormat("00.0");
    static int mNo = 0;

    public PercentPan(Main main_) {
    
        lbLevel.setOpaque(true);
        lbLevel.setForeground(Color.WHITE);
        lbLevel.setBackground(new Color(80, 0, 80));
        tfRatio.setBackground(new Color(80, 0, 80));
        tfRatio.setForeground(Color.WHITE);
        lbError.setOpaque(true);
        lbError.setForeground(Color.WHITE);
        lbError.setBackground(new Color(70, 90, 140));
        tfError.setBackground(new Color(70, 90, 140));
        tfError.setForeground(Color.WHITE);


        add(lbLevel);
        add(tfRatio);
        add(lbError);
        add(tfError);
        tfRatio.setEditable(false);
        tfError.setEditable(false);

    }

    public void countLevel(int lev) {
        extLev = lev;
        if (lev == -1) {
            mNo++;
            if (mNo > 5) {
                mNo = cNo = tNo = 0;
                ratio = (double) cNo / ((double) tNo + 0.1) * 100.0;
                tfRatio.setText(df.format(ratio) + " %");
                for (int i = 0; i < 100; i++) {
                    levCounter[i] = 0;
                }
                return;
            }
        }

        mNo = 0;

        max = 0;
        boolean trig = false;
        for (int i = 0; i < 100; i++) {
            if (i == lev) {
                levCounter[lev]++;
                if (levCounter[lev] > 200) {
                    levCounter[lev] = 200;
                }
                if (levCounter[i] > 100) {
                    cNo++;
                }
            } else {
                if (levCounter[i] > 0) {
                    levCounter[i]--;
                }
            }
            if (levCounter[i] > 100) {
                trig = true;
            }

        }
        if (trig) {
            tNo++;
        } else {
            cNo = tNo = 0;
        }
        ratio = (double) cNo / ((double) tNo + 0.1) * 100.0;
        tfRatio.setText(df.format(ratio) + " %");
        // repaint();
    }
//    public synchronized void paintComponent(Graphics g) {
//
//        g.clearRect(0, 0, 600, 600);
//
//        g.setFont(new Font("SansSerif", Font.BOLD, 24));
//        g.drawString(ratio + "", 20, 50);
//        g.drawString(extLev + "", 20, 80);
//        if (ratio >= 0.5) {
//            g.drawString(ratio + "", 20, 50);
//            // g.drawString("Error = " + df.format(error), 20, 70);
//        }
//    }
}
