/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jason;

import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author Jason.Burks
 */
public class Pie3DPanel extends LocalChartPanel {
   DefaultPieDataset dataset;
   String[] categories;

  /** The meter panel. */
 
    public Pie3DPanel( String title, String[] categories,String label) {
        super(title);
        this.categories = categories;
         dataset =  new DefaultPieDataset();
         for (int i=0; i< categories.length; ++i) {
             dataset.setValue(categories[i], 0);
         }
        JFreeChart chart = ChartFactory.createPieChart3D(title,dataset,true,true,false);
        chart.setBackgroundPaint(Color.WHITE);

         final PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(300);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        plot.setNoDataMessage("No data to display");
        plot.setBackgroundPaint(Color.WHITE);
        
            panel = new ChartPanel(new JFreeChart(title, plot));

    }

    
    public void addData(int seriesId, double value) {
        dataset.setValue(categories[seriesId],value);
    }
}
