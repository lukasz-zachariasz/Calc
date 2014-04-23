/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package calc;

import static calc.Image.colorToRGB;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_BYTE_BINARY;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adm
 */
public class Klaster {
    static int x;
    static int y;
    static List<Pixel> lista = new ArrayList<Pixel>();
    static BufferedImage img;
    Klaster(int a, int b)
    {
        x=a;
        y=b;
        lista=new ArrayList<Pixel>();
    }
    static void toImage()
    {
        int maxi=1;
        int maxy=1;
        for(Pixel p:lista)
        {
            int a=p.x-x;
            int b=p.y-y;
            if(a>maxi)maxi=a;
            if(b>maxy)maxy=b;
        }
        img = new BufferedImage(maxi,maxy,TYPE_BYTE_BINARY);
        for(int i=0; i<maxi; i++) 
            {
                for(int j=0; j<maxy; j++)
                {
                img.setRGB(i, j, colorToRGB(255,255,255));
                }
            }
        for(Pixel p:lista)
        {
            img.setRGB(p.x-x, p.y-y, 0);
        }
    }
    
}
