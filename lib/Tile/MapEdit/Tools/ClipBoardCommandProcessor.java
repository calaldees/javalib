package MapEdit.Tools;

import Tile.Model.Map.MapFoundation;
import Tile.Model.Map.MapFoundationEditable;
import Tile.Model.Map.MapFoundationEditableUndoable;
import Utils.GUIHelpers.GUIManager;
import MapEdit.GUIComponents.MapFrame;

public class ClipBoardCommandProcessor {
  
  private static final String GROUP_NAME_CLIPBOARD_FULL = "clipboard_full";
  
  private final GUIManager    gui_manager;
  private       MapFoundation map_clipboard;

  public ClipBoardCommandProcessor(GUIManager gui_manager) {
    if (gui_manager==null) {throw new IllegalArgumentException();}
    this.gui_manager = gui_manager;
    //setClipboardMap(null);
  }
  
  private void setClipboardMap(MapFoundation map_clipboard) {
    this.map_clipboard = map_clipboard;
    if (map_clipboard==null) {gui_manager.chanageMenuGroupEnableState(GROUP_NAME_CLIPBOARD_FULL, false);}
    else                     {gui_manager.chanageMenuGroupEnableState(GROUP_NAME_CLIPBOARD_FULL, true );}
  }
  
  public void copy() {
    //System.out.println("COPY");
    setClipboardMap(getMapCopy());
  }
  
  public void cut() {
    //System.out.println("CUT");
    setClipboardMap(getMapCopy());
    MapFoundationEditable map           = MapFrame.getSelectedMap();
    if (map_clipboard!=null && map!=null) {map.eraseSelectedArea();}
  }
  
  public void paste() {
    //System.out.println("PASTE");
    if (map_clipboard!=null) {
      MapFoundationEditableUndoable m = MapFrame.getSelectedMap();
      if (m==null) {paste_as_new_map();}
      else {
        m.newOperation("paste");
        MapFrame.getSelectedMapComponent().setFloatingMap(map_clipboard,null);
        MapFrame.getSelectedMapComponent().repaintFloatingArea();
        //m.pasteSubMap(map_clipboard,m.getCursor(),null);
        //MapFrame.getSelectedMapComponent().repaint();
      }
    }
  }
  
  public void paste_as_new_map() {
    if (map_clipboard!=null) {
      gui_manager.addWindow(new MapFrame(MapFoundationEditable.clone(map_clipboard)));
    }
  }

  private MapFoundation getMapCopy() {
    MapFoundationEditable map = MapFrame.getSelectedMap();
    if (map==null) {return null;}
    return new MapFoundationEditable(map.getSubMap());
  }
}