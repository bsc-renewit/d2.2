package es.bsc.autonomic.powermodeller.graphics;

/**
 * Created by jsubirat on 28/11/14.
 */


// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space


        import java.awt.*;
        import java.util.List;
        import javax.swing.JPanel;

        import es.bsc.autonomic.powermodeller.DataSet;
        import es.bsc.autonomic.powermodeller.configuration.CoreConfiguration;
        import org.jfree.chart.*;
        import org.jfree.chart.axis.DateAxis;
        import org.jfree.chart.plot.XYPlot;
        import org.jfree.chart.renderer.xy.XYDifferenceRenderer;
        import org.jfree.data.time.*;
        import org.jfree.data.xy.XYDataset;
        import org.jfree.data.xy.XYSeries;
        import org.jfree.data.xy.XYSeriesCollection;
        import org.jfree.ui.*;

public class TotalPowerAndPredictionDifference extends ApplicationFrame
{
    private static XYDataset data;
    private static String NAME = "Measured Power VS Predicted Power Difference";

    public TotalPowerAndPredictionDifference(DataSet ds)
    {
        super(NAME);
        data = dataSetToJFreeChartXYDataSet(ds);
        JPanel jpanel = createPanel();
        jpanel.setPreferredSize(new Dimension(500, 270));
        //jpanel.setPreferredSize(jpanel.getMaximumSize());
        getContentPane().add(jpanel);
    }

    /*private static XYDataset dataSetToJFreeChartXYDataSet(DataSet ds) {

        XYSeriesCollection xyseriescollection = new XYSeriesCollection();

        List<Double> column = ds.getCol(CoreConfiguration.PACTUAL_LABEL);
        XYSeries xyseries = new XYSeries(CoreConfiguration.PACTUAL_LABEL);
        for(int i = 0; i < column.size(); i++) {
            xyseries.add(i, column.get(i));
        }
        xyseriescollection.addSeries(xyseries);

        column = ds.getCol(CoreConfiguration.PPREDICTED_LABEL);
        xyseries = new XYSeries(CoreConfiguration.PPREDICTED_LABEL);
        for(int i = 0; i < column.size(); i++) {
            xyseries.add(i, column.get(i));
        }
        xyseriescollection.addSeries(xyseries);

        return xyseriescollection;
    }*/

    private static XYDataset dataSetToJFreeChartXYDataSet(DataSet ds)
    {
        TimeSeries pactual = new TimeSeries(CoreConfiguration.PACTUAL_LABEL);
        TimeSeries ppredicted = new TimeSeries(CoreConfiguration.PPREDICTED_LABEL);
        double d = 0.0D;
        double d1 = 0.0D;
        Day day = new Day();
        List<Double> pactualCol = ds.getCol(CoreConfiguration.PACTUAL_LABEL);
        List<Double> ppredictedCol = ds.getCol(CoreConfiguration.PPREDICTED_LABEL);
        for (int i = 0; i < ds.getSize(); i++)
        {
            pactual.add(day, pactualCol.get(i));
            ppredicted.add(day, ppredictedCol.get(i));
            day = (Day)day.next();
        }

        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();
        timeseriescollection.addSeries(pactual);
        timeseriescollection.addSeries(ppredicted);
        return timeseriescollection;
    }

    private static JFreeChart createChart()
    {
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(NAME, "Power (Watts)", "Power (Watts)", data, true, true, false);
        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        XYDifferenceRenderer xydifferencerenderer = new XYDifferenceRenderer(Color.green, Color.yellow, false);
        xydifferencerenderer.setRoundXCoordinates(true);
        xyplot.setDomainCrosshairLockedOnData(true);
        xyplot.setRangeCrosshairLockedOnData(true);
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);
        xyplot.setRenderer(xydifferencerenderer);
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        DateAxis dateaxis = new DateAxis("Samples");
        dateaxis.setTickLabelsVisible(false);
        dateaxis.setLowerMargin(0.0D);
        dateaxis.setUpperMargin(0.0D);
        xyplot.setDomainAxis(dateaxis);
        xyplot.setForegroundAlpha(0.5F);
        return jfreechart;
    }

    public static JPanel createPanel()
    {
        JFreeChart jfreechart = createChart();
        return new ChartPanel(jfreechart);
    }

    public void display()
    {
        this.pack();
        RefineryUtilities.centerFrameOnScreen(this);
        this.setVisible(true);
    }
}
