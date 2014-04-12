/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calc;

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
     public static void main(String[] args) throws IOException 
     {
   //      loadImage("test.bmp");
     }
     
   static boolean loadImage(File file)
    {
        try
        {
        FileInputStream plik = new FileInputStream(file);
        obraz = javax.imageio.ImageIO.read(plik);
        return true;
        }
        catch(IOException IOEx){return false;}
    }
   static BufferedImage toGray(BufferedImage ob)
   {
           ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
    ColorConvertOp op = new ColorConvertOp(cs, null);

  //  BufferedImage bufferedImage = new BufferedImage(200, 200,
    //    BufferedImage.TYPE_BYTE_INDEXED);
    obrazG = op.filter(ob, null);
       return null;
   }
}    

