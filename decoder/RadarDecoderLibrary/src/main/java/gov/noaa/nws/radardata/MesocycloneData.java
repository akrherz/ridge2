/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardata;

/**
 *
 * @author Jason.Burks
 */
public class MesocycloneData extends SymbolData {
    double radius;
    String name;
   

	public MesocycloneData(double i, double j, double radius) {
        super(i, j);
        this.radius = radius;
    }

    public double getRadius() {
        return(radius);
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
    

}
