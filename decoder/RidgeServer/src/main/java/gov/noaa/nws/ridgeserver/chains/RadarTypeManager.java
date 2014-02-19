package gov.noaa.nws.ridgeserver.chains;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author jason
 */
public class RadarTypeManager {

    Map<String,RadarProductType> types = new HashMap<String,RadarProductType>();

    public void setTypes(Map<String,RadarProductType> types) {
        this.types = types;
    }

    public RadarProductType getRadarType(String typeName) throws Exception {
        if (types.containsKey(typeName)) {
            return(types.get(typeName));
        }
        throw new Exception("Type not found");
    }
    
    public String[] getListOfTypes() {
    	String[] typesOutput = new String[types.size()];
    	Iterator<String> iter = types.keySet().iterator();
    	int count =0;
    	while (iter.hasNext()) {
    		String type = iter.next();
    		typesOutput[count] = type;
    		++count;
    	}
    	return typesOutput;
    }

}
