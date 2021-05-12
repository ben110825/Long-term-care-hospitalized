package cclo;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
public class SpecLineChart extends JFrame {

    final int FFTNo = 1024;
    XYSeries series1;
    Main main;
    // final ChartPanel chartPanel;

    public SpecLineChart(final String title, Main main_) {
        super(title);
        main = main_;

        final XYDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        //chartPanel.setPreferredSize(new java.awt.Dimension(1600, 400));
        //  setContentPane(chartPanel);
        this.setBounds(50, 50, 1200, 600);

        Container container = getContentPane();
        container.setLayout(new GridLayout(2, 1, 20, 20));
        JPanel h1Pan = new JPanel();
        h1Pan.setLayout(new GridLayout(2, 1, 5, 5));
        h1Pan.add(main.ioPan);
        JPanel q2Pan = new JPanel();
        q2Pan.setLayout(new GridLayout(2, 1, 5, 5));
        q2Pan.add(main.levName);
        q2Pan.add(main.arrowPan);
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
                "Frequency of Saxophone", // chart title
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
}
