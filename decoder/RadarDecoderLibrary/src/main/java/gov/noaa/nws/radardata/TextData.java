/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardata;

/**
 *
 * @author Jason.Burks
 */
public class TextData extends SymbolData {
    String text;
    public TextData(double i, double j, String text) {
        super(i, j);
        this.text = text;
    }

    public String getText() {
        return text;
    }



}
