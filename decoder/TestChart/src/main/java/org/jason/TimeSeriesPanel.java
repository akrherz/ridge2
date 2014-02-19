package org.jason;

import java.awt.Color;
import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
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
public class TimeSeriesPanel extends LocalChartPanel {

    TimeSeries[] series;
    DateAxis axis;
    public TimeSeriesPanel(String title,String seriesNames, String yLabel) {
         super(title);
       String[] seriesNamesArray = seriesNames.split(",");
       setup(seriesNamesArray, title, yLabel);
    }
    public TimeSeriesPanel( String title,String[] seriesNames, String yLabel) {
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
         JFreeChart chart = ChartFactory.createTimeSeriesChart(
            title,  // title
            "Date",             // x-axis label
            yLabel,   // y-axis label
            coll,            // data
            true,               // create legend?
            true,               // generate tooltips?
            false               // generate URLs?
        );
         chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(false);
            renderer.setBaseShapesFilled(false);
            renderer.setDrawSeriesLineAsPath(true);
        }

        axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("M/d/y H:m:s"));

        panel = new ChartPanel(chart);
    }

    public void addData(int seriesId, double value) {
        series[seriesId].add(new FixedMillisecond(System.currentTimeMillis()),value);
    }

    public void setDateAxisFormat(String dateAxisFormat) {
       axis.setDateFormatOverride(new SimpleDateFormat(dateAxisFormat));
    }


}
