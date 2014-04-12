/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calc;

import java.awt.image.BufferedImage;
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
}    

