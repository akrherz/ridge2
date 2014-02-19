/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardata;

/**
 *
 * @author Jason.Burks
 */
public class CompositeRowData extends RadarData {
    private int[] data;
    private int rowNumber;

    public CompositeRowData(int[] data, int rowNumber) {
        this.data = data;
        this.rowNumber = rowNumber;
    }

    public int[] getData() {
        return data;
    }

    public int getRowNumber() {
        return rowNumber;
    }



}
