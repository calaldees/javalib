package Tile.View;

import Tile.View.Cursor.CursorSet;
import Utils.ModelViewControllerFramework.ViewWithMultipleRenderers;

public class TileView extends ViewWithMultipleRenderers {

  private CursorSet cursors = new CursorSet();

  public TileView(String name) {super(name);}

  public CursorSet getCursorSet() {return cursors;}
}
