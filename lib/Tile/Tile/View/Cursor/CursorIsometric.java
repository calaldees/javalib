package Tile.View.Cursor;

import Utils.Types.Sprite;

public class CursorIsometric implements Cursor {
  
  private Sprite front;
  private Sprite back;
  
  public CursorIsometric() {}
  public CursorIsometric(Sprite front, Sprite back) {
    this.front = front;
    this.back  = back;
  }

  public String getName() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public Sprite getFront() {return front;}
  public Sprite getBack()  {return back;}

  public boolean isValid() {
    if (front==null||
        back ==null)  {return false;}
    return true;
  }



  
}
