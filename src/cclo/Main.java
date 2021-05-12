package cclo;

import edu.cmu.sphinx.frontend.*;
import edu.cmu.sphinx.frontend.filter.Preemphasizer;
import edu.cmu.sphinx.frontend.window.RaisedCosineWindower;
// import edu.cmu.sphinx.frontend.transform.DiscreteFourierTransform;
import edu.cmu.sphinx.frontend.util.Microphone;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.Timer;
import org.jfree.ui.RefineryUtilities;

public class Main implements Share {

    Microphone mic;
    DataBlocker dataBlock;
    Preemphasizer preEmp;
    RaisedCosineWindower windower;
    DiscreteFourierTransform dff;
    // public Scorer incFr;
    public Scorer scorer;
    public SpecLineChart freqFr;
    public IOPan ioPan;
    public LevName levName;
    public ArrowPan arrowPan;
    public DemoFrame tFrame;
    // public PercentPan percPan;
    public int nodeType;
    public int width, height;

    public Main() {
    }

    public void initVoice() {
        mic = new Microphone(
                44100, // int sampleRate
                16, // int bitsPerSample, 
                1, // int channels,
                true, // boolean bigEndian, 
                true, // boolean signed
                true, // boolean closeBetweenUtterances, 
                10, // int msecPerRead, 
                false, // boolean keepLastAudio,
                "average", // String stereoToMono
                0, // int selectedChannel, 
                "default", // String selectedMixerIndex
                6400 // int audioBufferSize
        );
        dataBlock = new DataBlocker(10.0);
        preEmp = new Preemphasizer(0.97);
        windower = new RaisedCosineWindower(0.46, 25.625f, 10.0f);
        dff = new DiscreteFourierTransform(2048, false);
        dff.setAED(this);

        mic.initialize();
        dataBlock.initialize();
        preEmp.initialize();
        windower.initialize();
        dff.initialize();

        dff.setPredecessor(windower);
        windower.setPredecessor(preEmp);
        //windower.setPredecessor(dataBlock);
        preEmp.setPredecessor(dataBlock);
        dataBlock.setPredecessor(mic);
    }

    public void initFrames() {
        // specFr = new SpecGram(this);
        levName = new LevName(this);
        arrowPan = new ArrowPan(this);

        ioPan = new IOPan(this);

        // percPan = new PercentPan(this);
        scorer = new Scorer(this);
        // specFr.setVisible(false);
        // scorer.setVisible(false);
        freqFr = new SpecLineChart("薩克斯風音準測試軟體", this);
        // freqFr.pack();
        // RefineryUtilities.centerFrameOnScreen(freqFr);
        tFrame  = new DemoFrame();
        freqFr.setVisible(true);
        levName.setVisible(true);
        freqFr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        Main aed = new Main();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double sWidth = screenSize.getWidth();
        double sHeight = screenSize.getHeight();


        aed.initVoice();
        aed.initFrames();


        //++ Window size
        aed.freqFr.setBounds(150, 150, (int) sWidth - 300, (int) sHeight - 300);

        aed.levName.init();
        
        //start the microphone or exit if the programm if this is not possible
        if (!aed.mic.startRecording()) {
            System.out.println("Cannot start microphone.");
            System.exit(1);
        }

        while (true) {
            try {
                Data input = aed.dff.getData();
                DoubleData output = (DoubleData) input;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}
