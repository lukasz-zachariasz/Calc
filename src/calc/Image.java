package calc;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
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
    static short[] rHist=new short[256];
    static short[] bHist=new short[256];
    static short[] gHist=new short[256];
    static int alpha;
    static int red;
    static int blue;
    static int green;
    static int newPixel;
    static short[] greyHist=new short[256];
    static int height;
    static int width;
    static int type;
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
        type=obraz.getType();
        alpha = new Color(obraz.getRGB(1, 1)).getAlpha();
        System.gc();
        return true;
        }
        catch(IOException IOEx){return false;}
    }
      static void getPix(int i, int j, BufferedImage obraz)
        {
	            red = new Color(obraz.getRGB(i, j)).getRed();
	            green = new Color(obraz.getRGB(i, j)).getGreen();
	            blue = new Color(obraz.getRGB(i, j)).getBlue();
        }
      static void createImages()
        {
            //t1=System.nanoTime();
          obrazG = new BufferedImage(width, height, type);
          obrazO = new BufferedImage(width, height, type);
        }
   
      static int colorToRGB(int red, int green, int blue) 
        { 
            return newPixel = (((((alpha<<8)+red)<<8)+green)<<8)+blue;
        }
   private static void fillPix(int newPixel, BufferedImage img, int i, int j)
   {
       img.setRGB(i, j, newPixel);
   }
static void greyMastah()
{
    	    int[] avgLUT = new int[766];
	    for(int i=0; i<avgLUT.length; i++) avgLUT[i] = (int) (i / 3);
            
 	    for(int i=0; i<width; i++) 
            {
               //if(i%100==0) System.gc(); //tym modyfikujemy czas/zuzycie ramu
	        for(int j=0; j<height; j++) 
                {
                    getPix(i,j,obraz);
                    rHist[red]++;                                               //histogram
                    bHist[blue]++;                                              //histogram
                    gHist[green]++;                                             //histogram
                    newPixel = red + green + blue;
                    newPixel = avgLUT[newPixel];                                //newPixel=kolor szarosci   
                    greyHist[newPixel]++;                                       //hisogram dla szarosci
                    fillPix(colorToRGB(newPixel,newPixel,newPixel),obrazG,i,j);
                    
                }
            }
            System.gc();
}
    static int otsuTreshold(BufferedImage original) {
    int total = height*width;
    float sum = 0;
    for(int i=0; i<256; i++) sum += i * greyHist[i];
    float sumB = 0;
    int wB = 0;
    int wF;
    float varMax = 0;
    int threshold = 0;
    for(int i=0 ; i<256 ; i++) {
        wB += greyHist[i];
        if(wB == 0) continue;
        wF = total - wB;
        if(wF == 0) break;
        sumB += (float) (i * greyHist[i]);
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
        static BufferedImage toBinary(BufferedImage original) 
        {       
        int ne;
        int threshold = otsuTreshold(original);
 
        for(int i=0; i<width; i++) 
        {
            //if(i%100==0) System.gc(); //tym modyfikujemy czas/zuzycie ramu
            for(int j=0; j<height; j++) 
            {
                red = new Color(original.getRGB(i, j)).getRed();
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
        //t2=System.nanoTime();
        //System.out.println(t2-t1);
        System.gc();
        return obrazO;
 
        }
   
}    

