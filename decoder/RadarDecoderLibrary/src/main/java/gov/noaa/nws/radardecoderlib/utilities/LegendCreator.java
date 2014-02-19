/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gov.noaa.nws.radardecoderlib.utilities;

import gov.noaa.nws.radardecoderlib.colorcurvemanager.BoundedColor;
import gov.noaa.nws.radardecoderlib.colorcurvemanager.BoundedStringColor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;
import java.util.Hashtable;

/**
 *
 * @author jason
 */
public class LegendCreator {
    BoundedColor[] colors;
    Hashtable labels;
    int numColors;
    int numLargeLabels=0;
    double amount = 20;
    int barWidth = 50;
    int width;
    int height;
    int topInset = 20;
    int sizeOfLargeLabel = 20;
    int bottomInset = 10;
    int leftInset = 5;
    int barTextSpacing = 5;
    BufferedImage image;
    Graphics2D g;
    String title = "";
    Font titleFont = new Font("Helvetica", Font.PLAIN, 12);
    Font labelFont = new Font("Helvetica", Font.PLAIN, 10);

    public LegendCreator(BoundedColor[] colors, Hashtable labels, Dimension dim) {
        this.colors = colors;
        this.labels = labels;
        numColors = this.colors.length;
        
        width = (int)dim.getWidth();
        height = (int)dim.getHeight();
        image = new BufferedImage((int)dim.getWidth(),(int)dim.getHeight(),BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D)image.getGraphics();
        countStringLabels();
        calculateLabelSize();
    }

    public void setTitleFont(Font f) {
        this.titleFont = f;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLabelFont(Font f){
        this.labelFont = f;
    }

    public void setBarWidth(int width) {
        this.barWidth = width;
    }

    public void setTopInset(int topInset) {
        this.topInset = topInset;
    }

    public void setBottomInset(int bottomInset) {
        this.bottomInset = bottomInset;
    }

    public void setLeftInset(int leftInset) {
        this.leftInset = leftInset;
    }
   public void setBarTextSpacing(int barTextSpacing) {
       this.barTextSpacing = barTextSpacing;
   }

    public BufferedImage getImage() {
        paint(g);
        return(image);
    }

    private void calculateLabelSize() {
        int amountLarge = 0;
       for (int i=0; i< numColors; ++i) {
           if (colors[i] instanceof BoundedStringColor) ++amountLarge;
       }
        int numNonLarge = numColors - amountLarge;
        amount = (height-topInset- bottomInset- amountLarge*sizeOfLargeLabel)/(double)numNonLarge;
    }

    private void countStringLabels() {

    }


    private void paint(Graphics2D graphics) {
        double currentLocation = topInset;
        int current = topInset;

        //Draw the title
        FontRenderContext frc = graphics.getFontRenderContext();
        graphics.setColor(Color.BLACK);
        TextLayout t1 = new TextLayout(title, titleFont, frc);
        FontMetrics fm = graphics.getFontMetrics(titleFont);
        int titleWidth = fm.stringWidth(title);
        t1.draw(graphics, (float)( (width / 2.0)-titleWidth/2.0),  (int)((topInset/2.0)+(t1.getAscent()/2.0)));
        //Draw boxes and labels
        for (int i = 0; i < colors.length; ++i) {
            if (colors[i] != null) {
                graphics.setColor(colors[i].getColor());
                if (colors[i] instanceof BoundedStringColor) {
                    Rectangle2D rect = new Rectangle2D.Double(leftInset, currentLocation, barWidth, sizeOfLargeLabel);
                    graphics.fill(rect);
                    currentLocation = currentLocation + sizeOfLargeLabel;
                } else {
                    Rectangle2D rect = new Rectangle2D.Double(leftInset, currentLocation, barWidth, amount);
                    graphics.fill(rect);
                    currentLocation = currentLocation + amount;
                }

                graphics.setColor(Color.BLACK);
                Object ob = labels.get(colors[i]);
                if ( ob != null) {
                        labels.remove(colors[i]);
                    String name = "";
                    if (ob instanceof DoubleLabel) {
                        double value = ((DoubleLabel)ob).getValue();
//                        double extra = (value*10)%10;
//                        if (extra == 0) {
                            NumberFormat nf = NumberFormat.getInstance();
                            nf.setMaximumFractionDigits(1);// set as you need
                            name = nf.format(value);
//                        }

                    } else if (ob instanceof StringLabel) {
                        name =  name = ((StringLabel)ob).getString();
                    }
                    TextLayout tl = new TextLayout(name, labelFont, frc);
                    if (ob instanceof StringLabel) {
                        Line2D line = new Line2D.Double(leftInset, currentLocation  - sizeOfLargeLabel / 2.0, leftInset + barWidth, currentLocation  - sizeOfLargeLabel / 2.0);
                       // graphics.draw(line);
                        tl.draw(graphics, leftInset + barWidth + barTextSpacing, (float) (currentLocation + tl.getBounds().getHeight() / 2.0 - sizeOfLargeLabel / 2.0));
                    } else {

                        Line2D line = new Line2D.Double(leftInset, currentLocation - amount / 2.0, leftInset + barWidth, currentLocation  - amount / 2.0);
                        //graphics.draw(line);
                        tl.draw(graphics, leftInset + barWidth + barTextSpacing, (float) (currentLocation + tl.getBounds().getHeight() / 2.0 - amount / 2.0));
                    }

                
                }
            }
        }

    }

}
