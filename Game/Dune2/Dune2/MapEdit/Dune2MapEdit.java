package Dune2.MapEdit;

import Utils.Config.ConfigManager;
import Utils.XML.XMLLoad.LoadProcessor;
import MapEdit.GUIComponents.MapEditFrame;

public class Dune2MapEdit extends MapEditFrame {
  protected void initXMLHandelers() {
    new LoadProcessor(Tile.Model.Map.Terrain.BaseTileComponent.class,"TILE");
    new RTS.GameObject.Unit.XMLUnitHandelers();
    new RTS.GameObject.Structure.XMLStructureHandelers();

    new RTS.View.XMLViewHandelers();
    new RTS.View.SpriteSet.XMLSpriteSetHandelers();
  }
  protected void initViews() {
    Dune2.View.Simple.ViewSimple.initViewSimple();
    Utils.ModelViewControllerFramework.ViewManager.setDefaultView("Dune2Original");
    //new RTS.Renderers.RTSStructureRenderer();
    //new Dune2.View.Tile2D.Dune2UnitRenderer();
  }
  protected void initTools() {
    //new MapToolBrushAutoEdge();
  }
  protected void initCommandProcessors() {
    addCommandProcessor(new Dune2MapEditGUICommand(getGUIManager()));
  }
  
  public Dune2MapEdit() {
    super("MapEditDune2.xml");
  }
  
  public static void main(String[] args) {
    ConfigManager.addToConfig(args);
    new Dune2MapEdit();
    Utils.XML.XMLLoad.LoadManager.listLoaded();
  }
}
