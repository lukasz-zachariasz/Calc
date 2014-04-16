/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calc;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.*;
import java.io.IOException;
import java.sql.Time;


/**
 *
 * @author Adm
 */
public class ImageOld 
{
    public static BufferedImage obraz;
    public static BufferedImage obrazG;
    public static BufferedImage obrazO;
    static int[] rHist;
    static int[] bHist;
    static int[] gHist;
    static int[] greyHist;
    static int height;
    static int width;
    static long t1;
    static long t2;
    
   static boolean loadImage(File file)
    {
        try
        {
        FileInputStream plik = new FileInputStream(file);
        obraz = javax.imageio.ImageIO.read(plik);
        height=obraz.getHeight();
        width=obraz.getWidth();
        return true;
        }
        catch(IOException IOEx){return false;}
    }
   
   private static int colorToRGB(int alpha, int red, int green, int blue) {
       int newPixel = 0;
       newPixel += alpha;
       newPixel = newPixel << 8;
       newPixel += red; newPixel = newPixel << 8;
       newPixel += green; newPixel = newPixel << 8;
       newPixel += blue;

       return newPixel;
   }

   
   static void toGray(BufferedImage original) {
            t1 = System.nanoTime();
	    int alpha, red, green, blue;
	    int newPixel;
	 
	    obrazG = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
	    int[] avgLUT = new int[766];
	    for(int i=0; i<avgLUT.length; i++) avgLUT[i] = (int) (i / 3);
	 
	    for(int i=0; i<original.getWidth(); i++) {
	        for(int j=0; j<original.getHeight(); j++) {
	 
	            // Get pixels by R, G, B
	            alpha = new Color(original.getRGB(i, j)).getAlpha();
	            red = new Color(original.getRGB(i, j)).getRed();
	            green = new Color(original.getRGB(i, j)).getGreen();
	            blue = new Color(original.getRGB(i, j)).getBlue();
	 System.out.println(alpha);
	            newPixel = red + green + blue;
	            newPixel = avgLUT[newPixel];
	            // Return back to original format
	            //newPixel =  colorToRGB(alpha, newPixel, newPixel, newPixel);
                    newPixel = (((((alpha<<8)+newPixel)<<8)+newPixel)<<8)+newPixel;
	            // Write pixels into image
	            obrazG.setRGB(i, j, newPixel);
	 
	        }
	    }
	 
	    //obrazG = avg_gray;
	 
	}
   
 /*  static void toGray(BufferedImage ob)
   {
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            obrazG = op.filter(ob, null);
   }*/
   
   static void histogram(BufferedImage ob)
   {
       int r=0;
       int b=0;
       int g=0;
       rHist=new int[256];
       bHist=new int[256];
       gHist=new int[256];
       for(int i=0;i<256;i++)
       {
           rHist[i]=0;
           bHist[i]=0;
           gHist[i]=0;
       }
       for(int i=0; i<ob.getWidth(); i++)
       {
           for(int j=0; j<ob.getHeight(); j++)
           {
               r = new Color(ob.getRGB(i,j)).getRed();
               b = new Color(ob.getRGB(i,j)).getBlue();
               g = new Color(ob.getRGB(i,j)).getGreen();
               rHist[r]++;
               bHist[b]++;
               gHist[g]++;
           }
       }
   }
   static void histogramOfGreyscale(BufferedImage ob)
   {
       int r=0;
       int b=0;
       int g=0;
       greyHist=new int[256];
       for(int i=0;i<255;i++)
       {
           greyHist[i]=0;
       }
       for(int i=0; i<ob.getWidth(); i++)
       {
           for(int j=0; j<ob.getHeight(); j++)
           {
               greyHist[new Color(ob.getRGB(i,j)).getGreen()]++;
           }
       }
   }
    static int otsuTreshold(BufferedImage original) {
 
    int[] histogram = greyHist;
    int total = height*width;
 
    float sum = 0;
    for(int i=0; i<256; i++) sum += i * histogram[i];
 
    float sumB = 0;
    int wB = 0;
    int wF = 0;
 
    float varMax = 0;
    int threshold = 0;
 
    for(int i=0 ; i<256 ; i++) {
        wB += histogram[i];
        if(wB == 0) continue;
        wF = total - wB;
 
        if(wF == 0) break;
 
        sumB += (float) (i * histogram[i]);
        float mB = sumB / wB;
        float mF = (sum - sumB) / wF;
 
        float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
 
        if(varBetween > varMax) {
            varMax = varBetween;
            threshold = i;
        }
    }
 
    return threshold;
 
}
        static BufferedImage toBinary(BufferedImage original) {
 
        int red;
        int ne;
 
        int threshold = otsuTreshold(original);
       
        obrazO = new BufferedImage(width, height, original.getType());
 
        for(int i=0; i<original.getWidth(); i++) 
        {
            for(int j=0; j<original.getHeight(); j++) 
            {
                red = new Color(original.getRGB(i, j)).getRed();
                int alpha = new Color(original.getRGB(i, j)).getAlpha();
                if(red > threshold) 
                {
                    ne = 255;
                }
                else 
                {
                    ne = 0;
                }
                ne = (((((alpha<<8)+ne)<<8)+ne)<<8)+ne;
                obrazO.setRGB(i, j, ne); 
 
            }
        }
    t2=System.nanoTime();
    System.out.println(t2-t1);
        return obrazO;
 
    }
   
}    

