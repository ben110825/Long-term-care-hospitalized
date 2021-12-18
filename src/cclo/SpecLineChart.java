package cclo;

import java.awt.*;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

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
import org.jfree.ui.RefineryUtilities;

/**
 * A simple demonstration application showing how to create a line chart using
 * data from an {@link XYDataset}.
 *
 */
public class SpecLineChart extends JFrame implements ActionListener {

	final int FFTNo = 1024;
	PeakFeature sampleFile;
	PeakFeature identificationFile;
	XYSeries series1;
	Main main;
	ProgressBar progressBar;
	JButton startRecordSampleBtn, startIdentifyBtn, stopRecordSampleBtn, stopIdentifyBtn;
	JComboBox<FeatureType> sampleType;
	String LoadFileName[]; // 所有讀進來的檔案名稱
	String[] featureType;
	String model = "";
	FeatureType chooseSampleType;
	boolean flag = false;
	// final ChartPanel chartPanel;
	public static JTextField jtf1; // 類別输入框

	public SpecLineChart(final String title, Main main_) {
		super(title);
		main = main_;
		featureType = new String[100];
		int i = 0;
		for (FeatureType f : FeatureType.values()) {
			featureType[i++] = f + "";
		}
		final XYDataset dataset = createDataset();
		final JFreeChart chart = createChart(dataset);
		final ChartPanel chartPanel = new ChartPanel(chart);
		ImageIcon icon = new ImageIcon("./icon/icon.png");
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		setIconImage(icon.getImage());
		// chartPanel.setPreferredSize(new java.awt.Dimension(1600, 400));
		// setContentPane(chartPanel);
		this.setBounds(50, 50, 1200, 600);
		Container container = getContentPane();// 创建一个容器，方便向框架内添加组件
		container.setLayout(new GridLayout(2, 1));

		JPanel h1Pan = new JPanel();
		h1Pan.setLayout(new GridLayout(2, 1, 5, 5));

		h1Pan.add(main.ioPan);
		JPanel q2Pan = new JPanel();
		q2Pan.setLayout(new GridLayout(2, 5, 5, 5));
		startRecordSampleBtn = new JButton("錄製樣本檔案");
		startRecordSampleBtn.setFont(new Font("標楷體", Font.BOLD, 40));
		startRecordSampleBtn.setBackground(Color.decode("#011627"));
		startRecordSampleBtn.setForeground(Color.decode("#2ec4b6"));
		startRecordSampleBtn.addActionListener(this);
		q2Pan.add(startRecordSampleBtn);

		stopRecordSampleBtn = new JButton("停止錄製樣本檔案");
		stopRecordSampleBtn.setFont(new Font("標楷體", Font.BOLD, 40));
		stopRecordSampleBtn.setBackground(Color.decode("#011627"));
		stopRecordSampleBtn.setForeground(Color.decode("#e71d36"));
		stopRecordSampleBtn.addActionListener(this);
		q2Pan.add(stopRecordSampleBtn);

		startIdentifyBtn = new JButton("進入辨識模式");
		startIdentifyBtn.setFont(new Font("標楷體", Font.BOLD, 40));
		startIdentifyBtn.setBackground(Color.decode("#011627"));
		startIdentifyBtn.setForeground(Color.decode("#2ec4b6"));
		startIdentifyBtn.addActionListener(this);
		q2Pan.add(startIdentifyBtn);

		stopIdentifyBtn = new JButton("退出辨識模式");
		stopIdentifyBtn.setFont(new Font("標楷體", Font.BOLD, 40));
		stopIdentifyBtn.setBackground(Color.decode("#011627"));
		stopIdentifyBtn.setForeground(Color.decode("#e71d36"));
		stopIdentifyBtn.addActionListener(this);
		q2Pan.add(stopIdentifyBtn);

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
		if (main.dff.model == "testAmbientSound_Mod") {
			JOptionPane.showMessageDialog(this, "目前正在測試環境音!!", "error", JOptionPane.WARNING_MESSAGE);
		} else {
			if (e.getSource() == startRecordSampleBtn) { // 按下錄製樣本檔案的按鈕
				if (main.dff.getModel() == "record_Mod")
					JOptionPane.showMessageDialog(this, "錄音中!!", "error", JOptionPane.WARNING_MESSAGE);
				else {
					main.dff.setModel("record_Mod"); // 更改model
					changeModel();
					String temp = (String) JOptionPane.showInputDialog(this, "選擇要錄製的樣本檔案", "錄製樣本檔案",
							JOptionPane.INFORMATION_MESSAGE, null, featureType, featureType[0]);
					for (FeatureType f : FeatureType.values()) { // String to FeatureType
						if ((f + "").equals(temp)) {
							chooseSampleType = f;
							break;
						}
					}
					JOptionPane.showMessageDialog(this, "目前錄製樣本種類: " + chooseSampleType, "開始錄音", 1);
				}

			} else if (e.getSource() == startIdentifyBtn ) { // 按下辨識模式的按鈕
				if(main.dff.getModel() == "identify_Mod")
					JOptionPane.showMessageDialog(this, "目前已經是辨識模式!!", "error", JOptionPane.WARNING_MESSAGE);
				else {
					main.dff.setModel("identify_Mod"); // 更改model
					changeModel();
					JOptionPane.showMessageDialog(this, "進入辨識模式");
				}

			} else if (e.getSource() == stopRecordSampleBtn) {
				if (main.dff.getModel() != "record_Mod")
					JOptionPane.showMessageDialog(this, "目前不是錄音模式!!", "error", JOptionPane.WARNING_MESSAGE);
				else {
					main.dff.setModel("normal_Mod");
					changeModel();
					JOptionPane.showMessageDialog(this, "停止錄音", "停止錄音", 0);
				}
			} else if (e.getSource() == stopIdentifyBtn ) {
				if(main.dff.getModel() != "identify_Mod") 
					JOptionPane.showMessageDialog(this, "目前不是辨識模式!!", "error", JOptionPane.WARNING_MESSAGE);
				else {
					main.dff.setModel("normal_Mod");
					changeModel();
					JOptionPane.showMessageDialog(this, "退出辨識模式", "退出辨識模式", 0);
				}
			}

		}

	}

	public void changeModel() {
		this.model = main.dff.getModel();
		String st = "";
		switch(model) {
			case "testAmbientSound_Mod":
				st = "測試環境音模式";
				break;
			case "normal_Mod":
				st = "一般模式";
				break;
			case "record_Mod":
				st = "錄音模式";
				break;
			case "identify_Mod":
				st = "辨識模式";
				break;
		}
		main.ioPan.tfModel.setText(st);

	}

}
