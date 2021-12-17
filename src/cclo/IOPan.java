/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cclo;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import javax.swing.*;

/**
 *
 * @author cclo
 */
public class IOPan extends JPanel {
    Main main;
    DecimalFormat df = new DecimalFormat("0.00");
    int sLevel = -1;
    double error = 0.0;
    Scorer father;
    JTextField tfModel = new JTextField();				
    JTextField tfIdentification = new JTextField();		//需要辨識的檔案(未知)
    JTextField tfResult = new JTextField();
    JLabel lbModel = new JLabel("<html><body>目前模式<body></html>");
    JLabel lbFile   = new JLabel("<html><body>錄音檔案<br><center>(未知)<center><body></html>");
    String name[] = {
        "0 Do", "0 Do+", "0 Re", "0 Re+", "0 Mi", "0 Fa", "0 Fa+", "0 Sol", "0 Sol+", "0 La", "0 La+", "0 Si",
        "1 Do", "1 Do+", "1 Re", "1 Re+", "1 Mi", "1 Fa", "1 Fa+", "1 Sol", "1 Sol+", "1 La", "1 La+", "1 Si",
        "2 Do", "2 Do+", "2 Re", "2 Re+", "2 Mi", "2 Fa", "2 Fa+", "2 Sol", "2 Sol+", "2 La", "2 La+", "2 Si",
        "3 Do", "3 Do+", "3 Re", "3 Re+", "3 Mi", "3 Fa", "3 Fa+", "3 Sol", "3 Sol+", "3 La", "3 La+", "3 Si",
        "4 Do", "4 Do+", "4 Re", "4 Re+", "4 Mi", "4 Fa", "4 Fa+", "4 Sol", "4 Sol+", "4 La", "4 La+", "4 Si",
        "5 Do", "5 Do+", "5 Re"};
//    String toneName[] = {"咳嗽", "打鼾", "噴嚏"};

    int levCounter[] = new int[100];
    int current;
    int max, cNo = 0, tNo = 0;
    int extLev;
    double ratio = 0.0;
    JTextField tfAcc = new JTextField();
    JLabel lbAcc = new JLabel("最高相似度");
//    JLabel lbTone = new JLabel("類別");
    JLabel lbResult = new JLabel("結果");
//    JComboBox cbTone = new JComboBox(toneName);
    static int mNo = 0;
    int bias = 0;
    public String selectedTone = "B-";
    
    public IOPan(Main main_) {
        main = main_;

        this.setLayout(new GridLayout(1, 8, 5, 5));
        lbModel.setFont(new Font("標楷體", Font.BOLD, 24));
        lbFile.setFont(new Font("標楷體", Font.BOLD, 24));
        lbAcc.setFont(new Font("標楷體", Font.BOLD, 24));
        tfModel.setFont(new Font("標楷體", Font.BOLD, 24));
        tfIdentification.setFont(new Font("標楷體", Font.BOLD, 24));
        tfAcc.setFont(new Font("標楷體", Font.BOLD, 24));
        lbResult.setFont(new Font("標楷體", Font.BOLD, 24));
        tfResult.setFont(new Font("標楷體", Font.BOLD, 24));

        lbModel.setHorizontalAlignment(JLabel.HORIZONTAL);
        lbFile.setHorizontalAlignment(JLabel.HORIZONTAL);
        lbAcc.setHorizontalAlignment(JLabel.HORIZONTAL);
        tfModel.setHorizontalAlignment(JLabel.HORIZONTAL);
        tfIdentification.setHorizontalAlignment(JLabel.HORIZONTAL);
        tfAcc.setHorizontalAlignment(JLabel.HORIZONTAL);
        lbResult.setHorizontalAlignment(JLabel.HORIZONTAL);
        tfResult.setHorizontalAlignment(JLabel.HORIZONTAL);

        //ImageIcon levelIcon = new ImageIcon(new ImageIcon("level.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        // lbModel.setIcon(levelIcon);
        lbModel.setOpaque(true);
        lbModel.setForeground(Color.WHITE);
        lbModel.setBackground(new Color(38, 77, 0));
        //tfModel.setBackground(new Color(38, 77, 0));
        //tfModel.setForeground(Color.WHITE);
        // lbFile.setIcon(levelIcon);
        lbFile.setOpaque(true);
        lbFile.setForeground(Color.WHITE);
        lbFile.setBackground(new Color(140, 60, 32));
        //tfIdentification.setBackground(new Color(70, 90, 140));
        //tfIdentification.setForeground(Color.WHITE);
        add(lbModel);
        add(tfModel);

        add(lbFile);
        add(tfIdentification);
        tfModel.setEditable(false);
        tfIdentification.setEditable(false);
        tfResult.setEditable(false);
        lbAcc.setOpaque(true);
        lbAcc.setForeground(Color.WHITE);
        lbAcc.setBackground(new Color(80, 0, 80));
        //tfAcc.setBackground(new Color(80, 0, 80));
//        tfAcc.setForeground(Color.WHITE);
//        lbTone.setOpaque(true);
//        lbTone.setForeground(Color.WHITE);
//        lbTone.setBackground(new Color(70, 90, 140));

        lbResult.setOpaque(true);
        lbResult.setForeground(Color.WHITE);
        lbResult.setBackground(new Color(15, 115, 140));

        add(lbAcc);
        add(tfAcc);
//
//        add(lbTone);
//        add(cbTone);

        
        add(lbResult);
        add(tfResult);
        
        // add(tfIdentification);
        tfAcc.setEditable(false);
        tfIdentification.setEditable(false);

//        cbTone.addItemListener(new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                selectedTone = cbTone.getSelectedItem().toString();
//                switch (selectedTone) {
//                    case "咳嗽":
//                        bias = 0;
//                        break;
//                    case "打鼾":
//                        bias = -5;
//                        break;
//                    case "噴嚏":
//                        bias = -2;
//                        break;
//                }
//                // System.out.println("Selected Tone: " + selectedTone);
//            }
//        });

    }

//    public synchronized void paintComponent(Graphics g) {
//
//        int flog;
//
//        flog = 0;
//
//        String level[] = {"Do", "Re", "Mi", "Fa", "Sol", "La", "Si", "Do^"};
//        double[][] mColor = {{238.0, 150.0, 150.0}, {244.0, 204.0, 150.0}, {255.0, 255.0, 100.0},
//        {100.0, 255.0, 100.0}, {150.0, 150.0, 255.0}, {150, 150, 228.0}, {210.0, 132.0, 240.0}, {255.0, 100.0, 178}, {100.0, 255.0, 255.0},
//        {255.0, 250.0, 240.0}};
//
//        g.clearRect(0, 0, 600, 600);
//
//        g.setFont(new Font("SansSerif", Font.BOLD, 24));
//        if (sLevel >= 0) {
//            g.drawString(name[sLevel], 20, 60);
//            g.drawString("誤差: " + df.format(error), 20, 90);
//        }
//    }
    int levCnt = 0, clev = -1; // confern level

    public boolean setLevel(int level_) {
        sLevel = level_;
        int txtLevel = sLevel + bias;
        if (txtLevel < 0) {
            sLevel = -1;
        }
        if (sLevel == clev) {
            levCnt++;
        } else {
            clev = sLevel;
            levCnt = 0;
        }

        if (sLevel != -1) {
            if (levCnt < 5) {
                return false;
            }
            if (sLevel + bias >= 0) {
          //      tfModel.setText(name[sLevel + bias]);
            }
         //   tfIdentification.setText(df.format(error));
            repaint();
            return true;
        } else {
          //  tfModel.setText("");
          //  tfIdentification.setText("");
            return false;
        }
    }

    boolean trig = false;
    int actNo = 0, silNo = 0;

    public void countLevel(int lev) {
        extLev = lev;
        if (lev == -1 && !trig) {
            return;
        } else if (lev == -1 && trig) {
            silNo++;
            if (silNo > 5) {
                trig = false;
                silNo = actNo = 0;
                ratio = (double) silNo / ((double) actNo + 0.1) * 100.0;
             //   tfAcc.setText(df.format(ratio) + " %");
                for (int i = 0; i < 100; i++) {
                    levCounter[i] = 0;
                }
                return;
            } else {
                actNo++;
                return;
            }
        }

        mNo = 0;   // silent no
        max = 0;
        actNo++;
        levCounter[lev]++;

        if (actNo > 40) {
            trig = true;
            ratio = (double) levCounter[lev] / ((double) actNo + 0.1) * 100.0;
//            tfAcc.setText(df.format(ratio) + " %");
        }
        /*
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
            if (levCounter[i] > 40) {
                trig = true;
            }

        } 
        
        if (trig) {
            tNo++;
        } else {
            cNo = tNo = 0;
        }
         
        if (trig) {
            ratio = (double) cNo / ((double) tNo + 0.1) * 100.0;
            tfAcc.setText(df.format(ratio) + " %");
            // repaint();
        }
         */
    }
    
    public void setError(double error_) {
        error = error_;
    }

    
}
