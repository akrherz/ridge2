/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jason;

import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Jason.Burks
 */
public class BarPanel extends LocalChartPanel {
   DefaultCategoryDataset dataset;
   String[] series;

  /** The meter panel. */
 
    public BarPanel( String title,String[] series, String label) {
        super(title);
        this.series = series;

         dataset =  new DefaultCategoryDataset();
         for (int i=0; i< series.length; ++i) {
             dataset.addValue(i, series[i], "");
         }
        final JFreeChart chart = ChartFactory.createBarChart3D(
            title,         // chart title
            "",               // domain axis label
            label,                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        chart.setBackgroundPaint(Color.WHITE);

         chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
            panel = new ChartPanel(new JFreeChart(title, plot));

    }

    
    public void addData(int seriesId, double value) {
        dataset.setValue(value, series[seriesId], "");
    }
}
