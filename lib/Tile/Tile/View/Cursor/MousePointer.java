package Tile.View.Cursor;

import Utils.XML.XMLLoad.Validatable;
import java.awt.Image;


public class MousePointer implements Validatable {
  
  private Image[] pointer;
  
  public MousePointer() {}
  public MousePointer(Image pointer) {setPointer(pointer);}
  
  public void setPointer(Image pointer) {
    if (pointer==null) {throw new IllegalArgumentException();}
    this.pointer    = new Image[1];
    this.pointer[0] = pointer;
  }
  public void setPointer(Image[] pointers) {
    if (pointers==null || pointers[0]==null) {throw new IllegalArgumentException();}
    pointer = pointers;
  }

  public Image getImage() {return getImage(0);}
  public Image getImage(int frame) {return pointer[frame%pointer.length];}

  public boolean isValid() {
    if (pointer==null || pointer[0]==null) {return false;}
    return true;
  }
}
