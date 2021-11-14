package cclo;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
public class SpecLineChart extends JFrame implements ActionListener, KeyListener {
	final int FFTNo = 1024;
	PeakFeature sampleFile;
	PeakFeature identificationFile;
	XYSeries series1;
	Main main;
	JButton startRecordingButton, stopRecordingButton, clearButton, loadFileButton, compareButton;
	String LoadFileName[]; // 所有讀進來的檔案名稱
	boolean flag = false;
	// final ChartPanel chartPanel;
	public static JTextField jtf1; // 類別输入框

	public SpecLineChart(final String title, Main main_) {
		super(title);
		main = main_;
		final XYDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		// chartPanel.setPreferredSize(new java.awt.Dimension(1600, 400));
		// setContentPane(chartPanel);
		this.setBounds(50, 50, 1200, 600);

		Container container = getContentPane();// 创建一个容器，方便向框架内添加组件
		container.setLayout(new GridLayout(2, 1, 20, 20));

		JPanel h1Pan = new JPanel();
		h1Pan.setLayout(new GridLayout(2, 1, 5, 5));

		h1Pan.add(main.ioPan);
		JPanel q2Pan = new JPanel();
		q2Pan.setLayout(new GridLayout(2, 5, 5, 5));
//		startRecordingButton = new JButton("開始錄音");
//		startRecordingButton.setFont(new Font("標楷體", Font.BOLD, 40));
//		startRecordingButton.addActionListener(this);
//		q2Pan.add(startRecordingButton);
//		stopRecordingButton = new JButton("停止錄音");
//		stopRecordingButton.setFont(new Font("標楷體", Font.BOLD, 40));
//		stopRecordingButton.addActionListener(this);
//		q2Pan.add(stopRecordingButton);
//		clearButton = new JButton("清空檔案");
//		clearButton.setFont(new Font("標楷體", Font.BOLD, 40));
//		clearButton.addActionListener(this);
//		q2Pan.add(clearButton);
//		loadFileButton = new JButton("載樣本檔");
//		loadFileButton.setFont(new Font("標楷體", Font.BOLD, 40));
//		loadFileButton.addActionListener(this);
//		q2Pan.add(loadFileButton);
//		compareButton = new JButton("進行辨識");
//		compareButton.setFont(new Font("標楷體", Font.BOLD, 40));
//		compareButton.addActionListener(this);
//		q2Pan.add(compareButton);
//		jtf1 = new JTextField("");// 類別輸入框
//		jtf1.setFont(new Font("標楷體", Font.BOLD, 40));
//		q2Pan.add(jtf1);

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
		final JFreeChart chart = ChartFactory.createXYLineChart("Sound waveform diagram", // chart title
				"Frequency", // x axis label
				"Amplitude", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, false, // include legend
				true, // tooltips
				false // urls
		);

		// NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
		chart.setBackgroundPaint(Color.white);

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
		// legend.setDisplaySeriesShapes(true);
		// get a reference to the plot for further customisation...
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		// plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
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
		// series1.clear();
		for (int i = 0; i < FFTNo; i++) {
			series1.update((Number) i, freq[i]);
		}
	}

	// ****************************************************************************
	// * JFREECHART DEVELOPER GUIDE *
	// * The JFreeChart Developer Guide, written by David Gilbert, is available *
	// * to purchase from Object Refinery Limited: *
	// * *
	// * http://www.object-refinery.com/jfreechart/guide.html *
	// * *
	// * Sales are used to provide funding for the JFreeChart project - please *
	// * support us so that we can continue developing free software. *
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
		if (e.getSource() == startRecordingButton && !main.dff.testAmbientSoundFlag) {
			if (main.dff.getRecordFlag()) {
				JOptionPane.showMessageDialog(this, "錄音中");
			} else {
				try {
					TimeUnit.SECONDS.sleep(1); // 暫停一秒
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				main.dff.setRecordFlag(true);
				JOptionPane.showMessageDialog(this, "開始錄音");
			}

		}else if(main.dff.testAmbientSoundFlag){
			JOptionPane.showMessageDialog(this, "測試環境音中!!","warning",JOptionPane.WARNING_MESSAGE);
		}
		else if (e.getSource() == stopRecordingButton) { // 暫停錄音並把此聲音讀入程式

			if (main.dff.getRecordFlag()) {
				main.dff.setRecordFlag(false);
				main.dff.setStoredFileName(main.dff.tempPF.getTime(), main.dff.tempPF.type);
				System.out.println(main.dff.getStoredFileName());
				main.ioPan.tfIdentification.setText("辨識檔已準備");
				// String st = main.dff.getStoredFileName();
				// JOptionPane.showMessageDialog(this, "存至:" + st);
				// main.dff.setLoadedFile(st); // LoadedFile儲存路徑變數可以不用,可以直接包在 function loadFile裡
				// identificationFile = new PeakFeature();
				// identificationFile = main.dff.loadFile(st); // 讀入辨識檔

			} else {
				JOptionPane.showMessageDialog(this, "未錄音");
			}
		} else if (e.getSource() == loadFileButton) {
			LoadFileName = new String[100];
			JOptionPane.showMessageDialog(this, "請選擇要讀取的檔案資料夾");
			try {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File("./sound_source")); // 設定初始資料夾
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);// 設定只讀取資料夾
				int returnValue = chooser.showOpenDialog(null);
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					flag = true;
					File folder = new File(chooser.getSelectedFile().getAbsolutePath());

					main.ioPan.tfSample.setText(chooser.getSelectedFile().getName());

					int i = 0;
					for (File file : folder.listFiles()) { // 讀取每個檔名
						if (!file.isDirectory()) {
							// System.out.println(file.getPath());
							LoadFileName[i++] = file.getPath();
							System.out.println(file.getPath());

						}
					}
				}

				

			} catch (NullPointerException exception) {

			}

//			JFileChooser chooser = new JFileChooser();
//			chooser.setCurrentDirectory(new File("./sound_source"));
//			int returnValue = chooser.showOpenDialog(null);
//			try {
//				String st = chooser.getSelectedFile().getAbsolutePath();
//				JOptionPane.showMessageDialog(this,"讀取至:"+st);
//				//main.dff.setLoadedFile(st);
//				sampleFile = new PeakFeature();
//				sampleFile = main.dff.loadFile(st);		//讀入樣本檔
//				main.ioPan.tfSample.setText(chooser.getSelectedFile().getName());
//
//			}
//			catch(NullPointerException exception) {
//				
//			}

		} else if (e.getSource() == clearButton) {
			main.ioPan.tfIdentification.setText("");
			main.ioPan.tfSample.setText("");
			main.ioPan.tfAcc.setText("");
			main.ioPan.tfResult.setText("");
			main.dff.tempPF = new PeakFeature();
			sampleFile = new PeakFeature();
			identificationFile = new PeakFeature();
			flag = false;
		} else if (e.getSource() == compareButton && identificationFile != null ) {
			LoadFileName = new String[100];
			String compareFolder = "./sound_source/test";
			File folder = new File(compareFolder);

			main.ioPan.tfSample.setText(compareFolder);

			int i = 0;
			for (File file : folder.listFiles()) { // 讀取每個檔名
				if (!file.isDirectory()) {
					// System.out.println(file.getPath());
					LoadFileName[i++] = file.getPath();
					System.out.println(file.getPath());

				}
			}
			System.out.println(identificationFile.getPeak());
			i = 0;
			int maxSimilarity = 0; // 最高相似度
			boolean flag = false;
			String maxSimilarityLocation = "";
			while (LoadFileName[i] != null) {
				sampleFile = new PeakFeature();
				sampleFile = main.dff.loadFile(LoadFileName[i]); // 讀入樣本檔
				// System.out.println(i);
				double lengthRatio = (double) sampleFile.getCountRecord()
						/ (double) identificationFile.getCountRecord(); // 長度比例
				if (lengthRatio > 1.3 || lengthRatio < 0.7) {
					System.out.println("長度相差過大");
					System.out.println("============================");
					i++;
					if (LoadFileName[i] == null)
						continue;

				}

				int resultFromLCS = Compare.lcs(sampleFile, identificationFile);
				int acc = (int) (100 * ((double) resultFromLCS / (double) sampleFile.getCountRecord()));
				System.out.println("辨識檔案總數: " + identificationFile.getCountRecord());
				System.out.println("辨識檔案相似度: "
						+ 100 * ((double) resultFromLCS / (double) identificationFile.getCountRecord()) + " %");

				System.out.println("樣本種類: " + sampleFile.getType());
				System.out.println("樣本檔案總數: " + sampleFile.getCountRecord());
				System.out.println("辨識種類: " + identificationFile.getType());
				System.out.println("LCS結果: " + resultFromLCS);
				System.out.println("樣本檔案相似度: " + acc + " %");
				System.out.println("============================");
				if (acc > maxSimilarity) { // 紀錄最像的
					flag = true;
					maxSimilarity = acc;
					maxSimilarityLocation = LoadFileName[i];
				}
//				if ((double) resultFromLCS / (double) sampleFile.getCountRecord() > 0.6)
//					identificationFile.setType(sampleFile.getType()); // 改變type

				i++;
			}
			if (flag)
				sampleFile = main.dff.loadFile(maxSimilarityLocation);
			// 	main.dff.tempPF.setType(sampleFile.getType());
			if (maxSimilarity > 60) { // 把type存回檔案
				identificationFile.setType(sampleFile.getType());
			} 
			try {
				// System.out.println("storedFileName " + storedFileName);
				main.dff.setStoredFilePath("sound_source/identification_"+identificationFile.type+"/");
				main.dff.setStoredFileName(identificationFile.getTime(), identificationFile.type);
				
				String st = main.dff.getStoredFileName();
				System.out.println(st);
				File dir_file = new File(st);
				dir_file.createNewFile();
				BufferedWriter buw = new BufferedWriter(new FileWriter(st));
				buw.write(identificationFile.gsonout());
				buw.close();
				System.out.println("儲存完畢" + st);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				
			main.ioPan.tfAcc.setText(maxSimilarity + "%");
			main.ioPan.tfResult.setText(maxSimilarity > 60 ? identificationFile.getType() + "" : "-1");

		}
		else
			JOptionPane.showMessageDialog(this,"尚未載入樣本檔案或辨識檔案，無法比對");
	}
//	 main.ioPan.tfAcc.setText(maxSimilarity+" %");

//			double lengthRatio = (double)sampleFile.getCountRecord()/(double)identificationFile.getCountRecord(); //長度比例
//			System.out.println("simple length: "+sampleFile.getCountRecord());
//			System.out.println("identification length: "+identificationFile.getCountRecord());

//			if(lengthRatio > 1.2 || lengthRatio < 0.8) {	//暫時先不用
//				System.out.println("長度相差過多");
//			}

//			int resultFromLCS = Compare.lcs(sampleFile, identificationFile);
//			System.out.println("LCS結果: "+resultFromLCS);
//			System.out.println("樣本檔案總數: "+ sampleFile.getCountRecord());
//			System.out.println("樣本檔案相似度: "+100*((double)resultFromLCS/(double)sampleFile.getCountRecord())+" %");
//			System.out.println("辨識檔案總數: "+ identificationFile.getCountRecord());
//			System.out.println("辨識檔案相似度: "+100*((double)resultFromLCS/(double)identificationFile.getCountRecord())+" %");
//			int acc = (int) (100*((double)resultFromLCS/(double)sampleFile.getCountRecord()));
//			main.ioPan.tfAcc.setText(acc+" %");
//			if((double)resultFromLCS/(double)sampleFile.getCountRecord() > 0) {		//若相似
//				main.ioPan.tfResult.setText(sampleFile.getType().toString());	
//				identificationFile.setType(sampleFile.getType());			//改變type
//				try {
//					//	System.out.println("storedFileName " + storedFileName);
//						String st = main.ioPan.tfIdentification.getText();
//						File dir_file = new File(st);
//						dir_file.createNewFile();
//						BufferedWriter buw = new BufferedWriter(new FileWriter(st));
//						buw.write(identificationFile.gsonout());
//						buw.close();
//						System.out.println("儲存完畢"+st);
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//			}
//			else
//				main.ioPan.tfResult.setText("-1");

	// identificationFile.analize(sampleFile);
//		}else {
//			JOptionPane.showMessageDialog(this,"尚未載入樣本檔案或辨識檔案，無法比對");
//		}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
			if (main.dff.getRecordFlag()) {
				JOptionPane.showMessageDialog(this, "錄音中");
			} else {
				main.dff.setRecordFlag(true);
				JOptionPane.showMessageDialog(this, "開始錄音");
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
