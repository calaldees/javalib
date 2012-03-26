package Tile.View.Cursor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CursorSet {

  private Map<String,Cursor> cursors = new HashMap<String,Cursor>();

  public void               addCursor(Cursor    c)  {cursors.put(c.getName(), c);}
  public Cursor             getCursor(String name)  {return cursors.get(name);}
  public Collection<Cursor> getCursors()            {return cursors.values();}

}