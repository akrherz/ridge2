package gov.noaa.nws.radardecoderlib.utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.jdom.Element;

public class CreateMapServerPalette {

	public CreateMapServerPalette(String inputFile, String outputFile, boolean append){
		
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(outputFile,append));
			Element root = ProcessXML.openXMLDocument(CreateMapServerPalette.class.getResource(inputFile)).getRootElement();
			List<Element> levels = root.getChildren("Level");
			Iterator<Element> levelIter = levels.iterator();
			while (levelIter.hasNext()) {
				Element current = levelIter.next();
				out.write(current.getChildText("red")+","+current.getChildText("green")+","+current.getChildText("blue")+","+current.getChildText("alpha")+"\n");
				System.out.println(current.getChildText("red")+","+current.getChildText("green")+","+current.getChildText("blue")+","+current.getChildText("alpha"));
			}
			
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new CreateMapServerPalette("/colorcurves/SRMColorCurveManager.xml","/tmp/output.txt", false);
		new CreateMapServerPalette("/colorcurves/ReflectivityColorCurveManager.xml","/tmp/output.txt", true);
		new CreateMapServerPalette("/colorcurves/VelocityColorCurveManager.xml","/tmp/output.txt", true);
		new CreateMapServerPalette("/colorcurves/NTPColorCurveManager.xml","/tmp/output.txt", true);
		new CreateMapServerPalette("/colorcurves/NETNVLColorCurveManager.xml","/tmp/output.txt", true);
		new CreateMapServerPalette("/colorcurves/N1_3PColorCurveManager.xml","/tmp/output.txt", true);
	}

}
