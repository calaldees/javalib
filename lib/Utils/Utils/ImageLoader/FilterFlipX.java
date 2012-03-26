package Utils.ImageLoader;

import java.awt.*;
import java.awt.image.*;

public class FilterFlipX extends ImageFilter {
   int owidth,oheight;
   int intPixels[];

   public FilterFlipX() {}

   public void setDimensions(int width,int height) {
      owidth = width;
      oheight = height;
      intPixels = new int[owidth*oheight];
      consumer.setDimensions(owidth,oheight);
   }

   public void setHints(int hints) {
      consumer.setHints(0|SINGLEPASS);
   }

   public void setPixels(int x, int y, int w, int h,
                           ColorModel cm, int pixels[],
                           int offset, int scansize) {

      for(int row=0;row<h;row++) {
         for(int col=0;col<w;col++) {
            intPixels[((y+row)*w)+w-(x+col)-1] =
               cm.getRGB(pixels[offset+row*scansize+col]);
         }
      }
   }

   public void setPixels(int x, int y, int w, int h,
                           ColorModel cm, byte pixels[],
                           int offset,int scansize) {

      for(int row=0;row<h;row++) {
         for(int col=0;col<w;col++) {
            intPixels[((y+row)*w)+w-(x+col)-1] =
               cm.getRGB(pixels[offset+row*scansize+col]&0x0ff);
         }
      }
   }

   public void imageComplete(int status) {
      if(status==IMAGEERROR||status==IMAGEABORTED) {
         consumer.imageComplete(status);
         return;
      }

      consumer.setPixels(0,0,owidth,oheight,
         ColorModel.getRGBdefault(),intPixels,0,owidth);

      consumer.imageComplete(status);
   }
}

