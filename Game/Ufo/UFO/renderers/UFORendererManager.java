package UFO.renderers;


public class UFORendererManager {

  private UFORendererManager() {}

  public static void initRenderers() {
    new UFOMapTileRenderer();
    new Tile.Renderers.MapRendererIsometric();
  }
  
}
