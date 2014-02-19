/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jason;

import org.jfree.chart.ChartPanel;

/**
 *
 * @author Jason.Burks
 */
public interface ChartInterface {

    public void addData(int seriesId, double value);

    public ChartPanel getPanel();



}
