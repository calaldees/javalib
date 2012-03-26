package UFO.MapEdit;

import Utils.Config.ConfigManager;
import Utils.XML.XMLLoad.LoadProcessor;
import MapEdit.GUIComponents.MapEditFrame;


public class UFOMapEdit extends MapEditFrame {
  
  protected void initXMLHandelers() {
    UFO.XMLHandelers.XMLManager.initXMLHandelers();
    new LoadProcessor(UFO.MapEdit.OutlineFramework.class,"OUTLINE");
  }
  protected void initRenderers() {
    UFO.renderers.UFORendererManager.initRenderers();
  }
  protected void initTools() {
    new UFO.MapEdit.MapToolOutline();
  }
  protected void initCommandProcessors() {
    addCommandProcessor(new UFOMapEditGUICommand(getGUIManager()));
  }
  
  
  public UFOMapEdit() {
    super("MapEditUFO.xml");
    //Utils.XMLObjectLoader.LoadManager.listLoaded();
  }
    
  public static void main(String[] args) {
    ConfigManager.addToConfig(args);
    new UFOMapEdit();
  }
}