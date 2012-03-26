package Tile.View.Cursor;

import Utils.Types.Sprite;

public class SelectionHighlight2D implements Cursor {

  private String name;

  private Sprite left;
  private Sprite right;
  private Sprite top;
  private Sprite bottom;
  private Sprite in_bounds;

  public String getName() {return name;}

  public Sprite getLeft()     {return left;}
  public Sprite getRight()    {return right;}
  public Sprite getTop()      {return top;}
  public Sprite getBottom()   {return bottom;}
  public Sprite getInBounds() {return in_bounds;}

  
  public boolean isValid() {
    return ((left   != null &&
             right  != null &&
             top    != null &&
             bottom != null)
             ||
             (in_bounds != null));

  }




}