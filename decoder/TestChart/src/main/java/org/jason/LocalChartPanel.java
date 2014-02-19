/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jason;

import java.awt.Dimension;
import java.awt.Point;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author Jason.Burks
 */
public abstract class LocalChartPanel implements ChartInterface {
     public String title;
     public ChartPanel panel;
     public Dimension size = new Dimension(500,500);
     public Point location = new Point(0,0);

     public LocalChartPanel(String title) {
         this.title = title;
     }

      public ChartPanel getPanel() {
        return(panel);
    }

      public void setSize(Dimension size){
          this.size = size;

      }

      public void setLocation(Point location) {
          this.location = location;
      }

      public Point getLocation() {
          return(location);
      }

      public Dimension getSize() {
          return(size);
      }

      public String getTitle() {
          return title;
      }
}
