package Tile.View.Cursor;

import Utils.Types.Sprite;


public class Cursor2D implements Cursor {

  private String   name;
  private Sprite[] cursor;
 
  public void setCursor2D(Sprite cursor) {
    if (cursor==null) {throw new IllegalArgumentException();}
    this.cursor = new Sprite[1];
    this.cursor[0] = cursor;
  }
  public void setCursor2D(Sprite[] cursor) {
    if (cursor==null || cursor[0]==null) {throw new IllegalArgumentException();}
    this.cursor = cursor;
  }

  public String getName() {return name;}
  
  public Sprite getCursor()          {return getCursor(0);}
  public Sprite getCursor(int frame) {return cursor[frame%cursor.length];}

  public boolean isValid() {
    if (cursor==null || cursor[0]==null) {return false;}
    return true;
  }


}