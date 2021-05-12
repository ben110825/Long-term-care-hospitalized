package cclo;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.*;

public class Scorer implements Share {

    double unit = 21.533;
    int FFTNo = 1024;
    // shared data 
    String tFileName = "temp.txt";
    String lFileName = "lattice.txt";
    File tFile, lFile;
    FileWriter fileWriter;
    BufferedWriter bufWriter;
    STATE tFileState = STATE.CLOSE;
    STATE lFileState = STATE.CLOSE;
    public STATE latState = STATE.EMPTY;
    // ---- window 
    Main main;
    public IOPan ioPan;
    // PercentPan percPan;
    // Container container = this.getContentPane();
    // ---- lattice and trainer
    // ---- IO 
    DecimalFormat df = new DecimalFormat("0.00");
    Toaster toaster = new Toaster();
    int inIndex = 0;
    public Timer matchTimer;
    String name[] = {
        "0 Do", "0 Do+", "0 Re", "0 Re+", "0 Mi", "0 Fa", "0 Fa+", "0 Sol", "0 Sol+", "0 La", "0 La+", "0 Si",
        "1 Do", "1 Do+", "1 Re", "1 Re+", "1 Mi", "1 Fa", "1 Fa+", "1 Sol", "1 Sol+", "1 La", "1 La+", "1 Si",
        "2 Do", "2 Do+", "2 Re", "2 Re+", "2 Mi", "2 Fa", "2 Fa+", "2 Sol", "2 Sol+", "2 La", "2 La+", "2 Si",
        "3 Do", "3 Do+", "3 Re", "3 Re+", "3 Mi", "3 Fa", "3 Fa+", "3 Sol", "3 Sol+", "3 La", "3 La+", "3 Si",
        "4 Do", "4 Do+", "4 Re", "4 Re+", "4 Mi", "4 Fa", "4 Fa+", "4 Sol", "4 Sol+", "4 La", "4 La+", "4 Si",
        "5 Do", "5 Do+", "5 Re"};
    double base2[] = new double[61];
    double base[] = {
        117.0, 123.0, 131.0, 139.0, 147.0, 156.0, 165.0, 175.0,
        185.0, 196.0, 208.0, 222.0, 233.0, 247.0, 262.0, 277.0,
        294.0, 311.0, 330.0, 349.0, 370.0, 392.0, 415.0, 440.0,
        466.0, 494.0, 523.0, 554.0, 587.0, 622.0, 659.0, 698.0,
        740.0, 784.0, 831.0, 880.0, 932.0, 988.0, 1047.0, 1109.0,
        1175.0, 1245.0, 1319.0, 1397.0, 1480.0, 1568.0, 1661.0, 1760.0,
        1865.0, 1976.0, 2093.0, 2217.0, 2349.0, 2489.0, 2637.0, 2794.0,
        2960.0, 3135.0, 3322.0, 3520.0, 3729.0
    }; //    public ArrayList<ArrayList<IncNode>> latticeArray = new ArrayList<ArrayList<IncNode>>();

    public Scorer(Main main_) {
        main = main_;
        //this.setBounds(1050, 250, 200, 400);
        ioPan = main.ioPan;
        // percPan = main.percPan;

        for (int i = 0; i < 61; i++) {
            base2[i] = i;
        }
    }

    int currentType = 0;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Calendar cal = Calendar.getInstance();
    double mDist = 0.0;

    public void resetLevel() {
        ioPan.setLevel(-1);

    }

    public double mulError(double x, double y) {
        double error;
        int mul = (int) x / (int) y;
        double diff = x - y * (double) mul;
        double errorValue = y / 2.0 - Math.abs(diff - y / 2.0);
        error = errorValue / y;
        return error;
    }

    public void matchLevel(boolean isPeak[]) {
        int minP = -1;
        double error[] = {
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};

        int pCount = 0;
        for (int i = 0; i < 200; i++) {
            if (isPeak[i]) {

                pCount++;
                if (pCount > 8) {
                    break;
                }
                if (minP == -1) {
                    minP = i;
                }

                double freq = unit * (double) i;
                for (int j = 0; j < 60; j++) {
                    if (minP == i) {
                        if (Math.abs(freq - base[j]) / base[j] > 0.2) {
                            error[j] += 1.0;
                        }
                    }

                    error[j] += mulError(freq, base[j]);
                }
            }
        }
        double mScore = Double.MAX_VALUE;
        int mId = -1;
        for (int i = 0; i < 60; i++) {
            if (error[i] < mScore) {
                mScore = error[i];
                mId = i;
            }
        }
        //System.out.println("\n Min Error Score : " + mScore/pCount);
        if (mScore / pCount < 0.2) {
            ioPan.setError(mScore / pCount);
            if (ioPan.setLevel(mId)) {
                main.arrowPan.drawArrow(mId);
                ioPan.countLevel(mId);
            }
        } else {
            ioPan.setLevel(-1);
            ioPan.countLevel(-1);
        }
    }
}
