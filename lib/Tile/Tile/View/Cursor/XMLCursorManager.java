package Tile.View.Cursor;

import Utils.XML.XMLLoad.LoadProcessor;


public class XMLCursorManager {
  
  private XMLCursorManager() {}
  
  public static void init() {
    //new LoadProcessor(MousePointer.class);
    new LoadProcessor(Cursor2D.class);
    new LoadProcessor(SelectionHighlight2D.class);
    new LoadProcessor(CursorIsometric.class);
    new LoadProcessor(SelectionHighlightIsometric.class);
  }
}

/*

//-----------------------------------------------------------------------
// Pointer
//-----------------------------------------------------------------------
class XMLPointerProcessor extends DataProcessor<MousePointer> {
  public XMLPointerProcessor() {super(MousePointer.class, "POINTER");}
  public MousePointer create(DataWrapper data) throws Exception {
    MousePointer pointer = new MousePointer( Utils.ImageLoader.ImageLoader.getImage(data.getInt("location")) );
    return super.overlay(pointer, data);
  }
  public MousePointer overlay(MousePointer m, DataWrapper data) throws Exception {throw new Exception("Overlay not supported for "+MousePointer.class.getName());}
}

//-----------------------------------------------------------------------
// IsometricCursor
//-----------------------------------------------------------------------

class XMLIsometricCursorProcessor extends DataProcessor<IsometricCursor> {
  public XMLIsometricCursorProcessor() {super(IsometricCursor.class, "IsometricCursor");}
  public IsometricCursor create(DataWrapper data) throws Exception {
    IsometricCursor cursor = new IsometricCursor();
    //Utils.ImageLoader.ImageLoader.getSprite(data.getInt("front")), Utils.ImageLoader.ImageLoader.getSprite(data.getInt("back"))
    return super.overlay(cursor, data);
  }
  public IsometricCursor overlay(IsometricCursor m, DataWrapper data) throws Exception {throw new Exception("Overlay not supported for "+IsometricCursor.class.getName());}
}
 */