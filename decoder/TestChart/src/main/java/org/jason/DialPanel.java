/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jason;

import java.awt.Font;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialCap;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.DialTextAnnotation;
import org.jfree.chart.plot.dial.DialValueIndicator;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.data.general.DefaultValueDataset;

/**
 *
 * @author Jason.Burks
 */
public class DialPanel extends LocalChartPanel {
    DefaultValueDataset data = new DefaultValueDataset(0.0);
StandardDialScale scale;

  /** The meter panel. */
 
    public DialPanel(String title, String label) {
        super(title);
        DialPlot plot = new DialPlot(data);
         plot.setView(0.0, 0.0, 1.0, 1.0);
            plot.setDialFrame(new StandardDialFrame());

            plot.setBackground(new DialBackground());
            DialTextAnnotation annotation1 = new DialTextAnnotation(label);
            annotation1.setFont(new Font("Dialog", Font.BOLD, 14));
            annotation1.setRadius(0.7);

            plot.addLayer(annotation1);

            DialValueIndicator dvi = new DialValueIndicator(0);
            plot.addLayer(dvi);

            scale = new StandardDialScale(0,
                    100, -120, -300, 10.0, 4);
            scale.setMajorTickIncrement(10);
            scale.setMinorTickCount(5);
            scale.setTickRadius(0.88);
            scale.setTickLabelOffset(0.15);
            scale.setTickLabelFont(new Font("Dialog", Font.PLAIN, 14));
            plot.addScale(0, scale);

            plot.addPointer(new DialPointer.Pin());

            DialCap cap = new DialCap();
            plot.setCap(cap);
            panel = new ChartPanel(new JFreeChart(title, plot));

    }

    public void setMaxValue(double maxValue) {
        scale.setUpperBound(maxValue);
    }
    public void setMinValue(double minValue) {
        scale.setLowerBound(minValue);
    }

   

    public void addData(int seriesId, double value) {
        data.setValue(value);
    }
}
