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


/**
 *
 * @author Adm
 */
public class Image 
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
   static void toGray(BufferedImage ob)
   {
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            obrazG = op.filter(ob, null);
   }
   
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
                ne = (((((alpha*256)+ne)*256)+ne)*256)+ne;
                obrazO.setRGB(i, j, ne); 
 
            }
        }
 
        return obrazO;
 
    }
   
}    

