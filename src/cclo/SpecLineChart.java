package cclo;

import java.awt.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a line chart using
 * data from an {@link XYDataset}.
 *
 */
public class SpecLineChart extends JFrame implements ActionListener{

    final int FFTNo = 1024;
	boolean hasLoadFile = false; //用於檢測有無讀檔
	PeakFeature simpleFile;
	PeakFeature identificationFile;
    XYSeries series1;
    Main main;
    JButton startRecordingButton, stopRecordingButton, clearIdentificationFileButton, loadFileButton, compareButton;
    // final ChartPanel chartPanel;
    public static JTextField jtf1; //類別输入框

    public SpecLineChart(final String title, Main main_) {
        super(title);
        main = main_;
        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.setPreferredSize(new java.awt.Dimension(1600, 400));
        //  setContentPane(chartPanel);
        this.setBounds(50, 50, 1200, 600);

        Container container = getContentPane();//创建一个容器，方便向框架内添加组件
        container.setLayout(new GridLayout(2, 1, 20, 20));

        JPanel h1Pan = new JPanel();
        h1Pan.setLayout(new GridLayout(2, 1, 5, 5));

        h1Pan.add(main.ioPan);
        JPanel q2Pan = new JPanel();
        q2Pan.setLayout(new GridLayout(2, 5, 5, 5));
        startRecordingButton = new JButton("開始錄音");
        startRecordingButton.setFont(new Font("Serif", Font.BOLD, 40));
        startRecordingButton.addActionListener(this);
        q2Pan.add(startRecordingButton);
        stopRecordingButton = new JButton("停止錄音");
        stopRecordingButton.setFont(new Font("Serif", Font.BOLD, 40));
        stopRecordingButton.addActionListener(this);
        q2Pan.add(stopRecordingButton);
        clearIdentificationFileButton = new JButton("清空辨識檔");
        clearIdentificationFileButton.setFont(new Font("Serif", Font.BOLD, 40));
        clearIdentificationFileButton.addActionListener(this);
        q2Pan.add(clearIdentificationFileButton);
        loadFileButton= new JButton("載樣本檔");
        loadFileButton.setFont(new Font("Serif", Font.BOLD, 40));
        loadFileButton.addActionListener(this);
        q2Pan.add(loadFileButton);
        compareButton = new JButton("進行辨識");
        compareButton.setFont(new Font("Serif", Font.BOLD, 40));
        compareButton.addActionListener(this);
        q2Pan.add(compareButton);
        jtf1 = new JTextField("");//類別輸入框
        jtf1.setFont(new Font("Serif", Font.BOLD, 40));
        q2Pan.add(jtf1);
        
//        q2Pan.add(main.levName);
//        q2Pan.add(main.arrowPan);
        h1Pan.add(q2Pan);
        container.add(h1Pan);
        container.add(chartPanel);
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                main.levName.init();
                main.arrowPan.init();
            }
        });
        this.pack();
    }

    /**
     * Creates a sample dataset.
     *
     * @return a sample dataset.
     */
    private XYDataset createDataset() {

        series1 = new XYSeries("Spectrum");
        series1.setMaximumItemCount(FFTNo + 1);
        for (int i = 0; i < FFTNo; i++) {
            series1.addOrUpdate((Number) i, 50.0 + Math.random() * 100.0);
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series1);

        return dataset;

    }

    /**
     * Creates a chart.
     *
     * @param dataset the data for the chart.
     *
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {

        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Sound waveform diagram", // chart title
                "Frequency", // x axis label
                "Amplitude", // y axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                false, // include legend
                true, // tooltips
                false // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
        //      legend.setDisplaySeriesShapes(true);
        // get a reference to the plot for further customisation...
        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
        //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        plot.setRenderer(renderer);

        // change the auto tick unit selection to integer units only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        final NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setRange(new Range(0, 210));
        // rangeAxis.setRange(new Range(50.0, 150.0));
        rangeAxis.setRange(new Range(5, 15.0));
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.

        return chart;

    }

    public void updateSpec(double freq[]) {
        //series1.clear();
        for (int i = 0; i < FFTNo; i++) {
            series1.update((Number) i, freq[i]);
        }
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    /**
     * Starting point for the demonstration application.
     *
     * @param args ignored.
     */
    public static void main(final String[] args) {

        final SpecLineChart demo = new SpecLineChart("Music Spectrum", null);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

    }
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == startRecordingButton) {	
			if(main.dff.getRecordFlag()) {
				JOptionPane.showMessageDialog(this,"錄音中");
			}
			else {
				main.dff.setRecordFlag(true);	

				JOptionPane.showMessageDialog(this,"開始錄音");
				//main.dff.startpeakRecord();

			}
			
		}else if(e.getSource() ==stopRecordingButton)
		{
			if(main.dff.getRecordFlag()) {
				main.dff.setRecordFlag(false);

				JOptionPane.showMessageDialog(this,"存至:"+main.dff.getStoredFilePath()+main.dff.temp.getTime());
				//main.dff.stoppeakRecord();				

			}
			else {
				JOptionPane.showMessageDialog(this,"未錄音");
			}
		}
		else if(e.getSource() == loadFileButton) {	
			JFileChooser chooser = new JFileChooser();
			int returnValue = chooser.showOpenDialog(null); 
			String st = chooser.getSelectedFile().getAbsolutePath();
			JOptionPane.showMessageDialog(this,"讀取至:"+st);
			main.dff.setLoadedFile(st);
			simpleFile = new PeakFeature();
			simpleFile = main.dff.loadFile();		//讀入樣本檔
			hasLoadFile = true;
		}
		else if(e.getSource() == clearIdentificationFileButton) {
			identificationFile = new PeakFeature();
		}
		else if(e.getSource() == compareButton && hasLoadFile && !identificationFile.isEmpty()) {	//比對未完成
			System.out.println("Simple: "+simpleFile.getPeak());
			System.out.println("Identification: "+identificationFile.getPeak());

			identificationFile.analize(simpleFile);
		}else {
			JOptionPane.showMessageDialog(this,"尚未載入樣本檔案，無法比對");
		}
		

	}

	

}
