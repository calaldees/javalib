package MapEdit.Tools;

import java.util.EventObject;
import java.awt.event.MouseEvent;

import Tile.Model.Map.MapFoundationEditableUndoable;

import MapEdit.GUIComponents.MapComponentEditable;


public class MapToolEvent extends EventObject {
  
  private MapComponentEditable component;
  private MouseEvent           mouse_event;
  
  public MapToolEvent(MapComponentEditable component, MouseEvent e) {
    super(component);
    this.component   = component;
    this.mouse_event = e;
  }
  
  public MapComponentEditable          getMapComponent() {return component;}
  public MapFoundationEditableUndoable getMap()          {return component.getMap();}
  public MouseEvent                    getMouseEvent()   {return mouse_event;}

}
