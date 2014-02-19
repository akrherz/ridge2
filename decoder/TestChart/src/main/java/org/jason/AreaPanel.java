package org.jason;

import java.awt.Color;
import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jason.Burks
 */
public class AreaPanel extends LocalChartPanel {

    TimeSeries[] series;
    DateAxis axis;
    public AreaPanel( String title, String seriesNames, String yLabel) {
         super(title);
       String[] seriesNamesArray = seriesNames.split(",");
       setup(seriesNamesArray, title, yLabel);
    }
    public AreaPanel( String title, String[] seriesNames, String yLabel) {
         super(title);
        setup(seriesNames, title, yLabel);
    }

    private void setup(String[] seriesNames, String title, String yLabel) {
        TimeSeriesCollection coll = new TimeSeriesCollection();

        series = new TimeSeries[seriesNames.length];
        for (int i=0; i<seriesNames.length; ++i) {
            series[i] = new TimeSeries(seriesNames[i]);
            series[i].setMaximumItemCount(100);

            coll.addSeries(series[i]);
           
        }
         final JFreeChart chart = ChartFactory.createXYAreaChart(
            title,             // chart title
            "",               // domain axis label
            yLabel,
            coll, // data                // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

         chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        plot.setDomainAxis(0, new DateAxis());
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("M/d/y H:m:s"));

        panel = new ChartPanel(chart);
    }

    public void setDateAxisFormat(String dateAxisFormat) {
       axis.setDateFormatOverride(new SimpleDateFormat(dateAxisFormat));
    }

    public void addData(int seriesId, double value) {
        series[seriesId].add(new FixedMillisecond(System.currentTimeMillis()),value);
    }

    


}
