/*
 * ProcessXML.java
 *
 * Created on September 23, 2003, 7:23 PM
 */
package gov.noaa.nws.radardecoderlib.utilities;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;
import java.lang.reflect.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.net.*;
/**
 *
 * @author  Owner
 */
public class ProcessXML {
    
    /** Creates a new instance of ProcessXML */
    public ProcessXML() {
    }
    
//    public static void processXML(Object obj) {
//       Class myClass =  obj.getClass();
//       Method[] methods = myClass.getMethods();
//       for (int i =0; i<= methods.length-1; ++i) {
//           if (methods[i].getName().startsWith("readXML") == true ) {
//               try {
//                    methods[i].invoke(obj,null);
//               } catch (IllegalAccessException e) {
//                   System.out.println("Problem");
//               } catch (InvocationTargetException e) {
//               }
//           }
//       }  
//    }
//    public static void recordXML(Object obj) {
//       
//       Class myClass =  obj.getClass();
//       Method[] methods = myClass.getMethods();
//       for (int i =0; i<= methods.length-1; ++i) {
//           if (methods[i].getName().startsWith("recordXML") == true ) {
//               try {
//                    methods[i].invoke(obj,null);
//               } catch (IllegalAccessException e) {
//                   System.out.println("Problem");
//               } catch (InvocationTargetException e) {
//               }
//           }
//       }
//    }
    
    public static Document openXMLDocument(String filename) throws IOException {
        SAXBuilder builder = new SAXBuilder(false);
        Document xmlDoc;
        try {
         //   System.out.println("Filename "+filename);
            xmlDoc = builder.build(new File(filename));
        } catch (JDOMException e)  {
            System.out.println("JDOM Exception "+e);
            throw(new IOException("Problem with JDOM"));
        } catch (IOException e) {
            System.out.println("IOException "+e);
            throw(new IOException("Problem with IO"));
        }
        return(xmlDoc);
    }
    public static Document openXMLDocument(URL filename) throws IOException {
        SAXBuilder builder = new SAXBuilder(false);
        Document xmlDoc;
        try {
         //   System.out.println("Filename "+filename);
            xmlDoc = builder.build(filename);
        } catch (JDOMException e)  {
            System.out.println("JDOM Exception "+e);
            throw(new IOException("Problem with JDOM"));
        } catch (IOException e) {
            System.out.println("IOException "+e);
            throw(new IOException("Problem with IO"));
        }
        return(xmlDoc);
    }
    public static Document openXMLDocument(InputStream ins) throws IOException {
        SAXBuilder builder = new SAXBuilder(false);
        Document xmlDoc;
        try {
            xmlDoc = builder.build(ins);
        } catch (JDOMException e)  {
            System.out.println("JDOM Exception "+e);
            throw(new IOException("Problem with JDOM"));
        } catch (IOException e) {
            System.out.println("IOException "+e);
            throw(new IOException("Problem with IO"));
        }
        return(xmlDoc);
    }
    
    public static void saveXMLDocument(String fileLocation, Document doc) throws IOException {
        XMLOutputter fmt = new XMLOutputter();
//        fmt.setIndent("  "); // use two space indent
//        fmt.setNewlines(true); 
        try {
            FileOutputStream out = new FileOutputStream(new File(fileLocation));
            fmt.output(doc,out);
        } catch (FileNotFoundException e) {
            throw(new IOException("File Not Found"));
        } catch (IOException e) {
            throw(new IOException("IO Problem"));
        }
    }
    
    private static String findFilename(Object obj) {
        return(null);
    }
    
    public String grabXMLTemplate() {
        URL urltemplate = getClass().getResource("XMLTemplates/"+this.getClass().getName()+".xml");
        return(urltemplate.getPath()+urltemplate.getFile());
    }

    public static Element getXMLTemplate(URL urltemplate, String name) {
        try {
            Document doc= openXMLDocument(urltemplate);
            Element root = doc.getRootElement();
            Element e =root.getChild(name);
            e.detach();
            return(e);
        } catch(IOException e) {
            return(null);
        }
    }   
    public static Color getColor(Element e) {
        int red=255;
        int blue=0;
        int green=0;
        int alpha=100;
        if (e.getChild("red") != null ) {
            red = Integer.parseInt(e.getChild("red").getText());
        }
        if (e.getChild("green") != null ) {
            green = Integer.parseInt(e.getChild("green").getText());
        }
        if (e.getChild("blue") != null ) {
            blue = Integer.parseInt(e.getChild("blue").getText());
        }
        if (e.getChild("alpha") != null ) {
            alpha = Integer.parseInt(e.getChild("alpha").getText());
        }
        return(new Color(red,green,blue));
        
    }
    public static Font getFont(Element e) {
        String font="dialog";
        int style=0;
        int size=8;
        
        if (e.getChild("font") != null ) {
            font = e.getChild("font").getText();
        }
        if (e.getChild("style") != null ) {
            style = Integer.parseInt(e.getChild("style").getText());
        }
        if (e.getChild("size") != null ) {
            size = Integer.parseInt(e.getChild("size").getText());
        }
        return(new Font(font,style,size));
        
    }
    
    
    
}


