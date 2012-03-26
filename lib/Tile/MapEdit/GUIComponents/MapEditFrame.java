package MapEdit.GUIComponents;


import java.awt.BorderLayout;
import Utils.GUIHelpers.GUIManager;
import Utils.XML.XMLLoad.LoadManager;
import MapEdit.Tools.ClipBoardCommandProcessor;
import MapEdit.Tools.MapToolCommandProcessor;
import MapEdit.Tools.StandardCommandProcessor;

public abstract class MapEditFrame {
  
  private GUIManager gui;
  
  public MapEditFrame(String xml_filename) {
    defaultXMLHandelers();
    initXMLHandelers();
    
    LoadManager.open(xml_filename);
    
    defaultViews();
    initViews();
    
    defaultTools();
    initTools();
    
    gui = new GUIManager("MapEdit");
    gui.addToFrame(new TileSetSelectorPanel(), BorderLayout.SOUTH );
    
    defaultCommandProcessors();
    initCommandProcessors();
  }
  
  protected void initXMLHandelers()      {}
  protected void initViews()             {}
  protected void initTools()             {} 
  protected void initCommandProcessors() {}
  
  protected GUIManager getGUIManager() {return gui;}
  protected void addCommandProcessor(Object cp) {gui.addCommandProcessor(cp);}
  
  private void defaultXMLHandelers() {
    GUIManager.initGUIManagerXMLHandelers();
    Tile.View.Cursor.XMLCursorManager.init();
    new Tile.Model.Map.Terrain.XMLTileSetHandeler();
    new Tile.Model.Map.Terrain.XMLTileTemplateHandeler();
    new Tile.Model.Map.XMLMapHandeler();
    new Tile.Model.Map.XMLMapSaver();
  }
  
  private void defaultViews() {
    
  }
  
  private void defaultTools() {
    new MapEdit.Tools.MapToolBrush();
    new MapEdit.Tools.MapToolSelect();
  }
  
  private void defaultCommandProcessors() {
    addCommandProcessor(new ClipBoardCommandProcessor(gui));
    addCommandProcessor(new MapToolCommandProcessor()     );
    addCommandProcessor(new StandardCommandProcessor(gui) );
  }
}
