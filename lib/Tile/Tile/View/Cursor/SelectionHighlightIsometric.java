package Tile.View.Cursor;

import Utils.Types.Sprite;
import Utils.XML.XMLLoad.Validatable;

public class SelectionHighlightIsometric implements Validatable {
  
  private Sprite top_left;
  private Sprite top_right;
  private Sprite bottom_left;
  private Sprite bottom_right;
  private Sprite celling;
  private Sprite floor;
  
  public SelectionHighlightIsometric() {}
  public SelectionHighlightIsometric(Sprite top_left,
                                     Sprite top_right,
                                     Sprite bottom_left,
                                     Sprite bottom_right,
                                     Sprite celling,
                                     Sprite floor) {
    this.top_left     = top_left;
    this.top_right    = top_right;
    this.bottom_left  = bottom_left;
    this.bottom_right = bottom_right;
    this.celling      = celling;
    this.floor        = floor;
  }

  public Sprite getTopLeft()     {return top_left;}
  public Sprite getTopRight()    {return top_right;}
  public Sprite getBottomLeft()  {return bottom_left;}
  public Sprite getBottomRight() {return bottom_right;}
  public Sprite getCelling()     {return celling;}
  public Sprite getFloor()       {return floor;}
  
  public boolean isValid() {
    return top_left     != null &&
           top_right    != null &&
           bottom_left  != null &&
           bottom_right != null &&
           celling      != null &&
           floor        != null;
  }
  
  
  
}
