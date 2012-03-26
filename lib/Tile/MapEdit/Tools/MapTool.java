package MapEdit.Tools;

import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.HashMap;
import javax.swing.JComponent;
import Tile.Model.Map.MapFoundationTile;
import MapEdit.GUIComponents.event.TileSelectedListener;


public abstract class MapTool implements TileSelectedListener {
  
  //---------------------------------------------------------------
  // Static
  //---------------------------------------------------------------
  private static Map<String,MapTool> tools         = new HashMap<String,MapTool>();
  private static MapTool             selected_tool = null;

  public static MapTool getSelectedTool() {return selected_tool;}
  public static boolean getSelectedToolEnableSelection() {
    MapTool tool = getSelectedTool();
    if (tool!=null) {return tool.getEnableSelection();}
    else            {return false;}
  }
  public static void applySelectedTool(MapToolEvent e) {
    MapTool tool = getSelectedTool();
    if (tool!=null) {tool.applyTool(e);}
  }
  public static void activate(String name) {
    MapTool tool = getMapTool(name);
    if (tool!=null) {tool.activate();}
  }
  private static MapTool getMapTool(String name)  {return tools.get(name);}
  private static void    addMapTool(MapTool tool) {tools.put(tool.getName(), tool);}
  
  private static MapFoundationTile selected_tile;
  protected MapFoundationTile getSelectedTile() {return selected_tile;}
  public static void setSelectedTile(MapFoundationTile tile) {
    if (getSelectedTool()==null) {selected_tile = tile;}
    else                         {getSelectedTool().selectTile(tile);}
  }
  
  //---------------------------------------------------------------
  // Private Variables
  //---------------------------------------------------------------
  
  private final String     tool_name;
  private final boolean    allow_auto_undo;
  private       JComponent control;
  private       boolean    enable_selection;

  
  public MapTool(String tool_name, JComponent control                                                   ) {this(tool_name,control,false,true);}
  public MapTool(String tool_name, JComponent control, boolean enable_selection, boolean allow_auto_undo) {
    if (tool_name==null) {throw new IllegalArgumentException();}
    this.tool_name       = tool_name;
    this.allow_auto_undo = allow_auto_undo;
    this.control         = control;
    //control.setName(getName());
    //setCursor(cursor);
    setEnableSelection(enable_selection);
    addMapTool(this);
  }
  
  public String  getName()            {return tool_name;}
  public boolean getEnableSelection() {return enable_selection;}
  public void    setEnableSelection(boolean enabled) {enable_selection = enabled;}
  public void activate() {
    selected_tool = this;
    //MapEditor.getToolFrame().setToolControl(control);
    //MapComponentEditor.setTool(selected);
  }
  
  public void selectTile(MapFoundationTile tile) {
    selected_tile = tile;
    //System.out.println("SelectedTile:"+tile);
  }
  
  private void applyTool(MapToolEvent e) {
    if (allow_auto_undo) {
      if (e.getMouseEvent().getButton()==MouseEvent.BUTTON1 && e.getMouseEvent().getModifiersEx()!=0) {
        e.getMap().newOperation(getName());
      }
    }
    apply(e);
  }
  
  public abstract void apply(MapToolEvent e);
  
}
