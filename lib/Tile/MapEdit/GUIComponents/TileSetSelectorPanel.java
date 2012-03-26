package MapEdit.GUIComponents;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import Utils.GUIHelpers.LoadedItemList;
import Tile.Model.Map.MapFoundationTile;
import MapEdit.GUIComponents.event.TileSelectedListener;
import MapEdit.Tools.MapTool;

public class TileSetSelectorPanel extends JPanel implements TileSelectedListener {
  
  public TileSetSelectorPanel() {
    super();
    LoadedItemList<Tile.Model.Map.Terrain.TileSet> tileset_list   = new LoadedItemList<Tile.Model.Map.Terrain.TileSet>(Tile.Model.Map.Terrain.TileSet.class);
    TileSetViewerSimple                      tileset_viewer = new TileSetViewerSimple();
    
    tileset_list.addLoadedSelectionListener(tileset_viewer);
    tileset_viewer.addTileSelectedListener(this);
    
    setLayout(new BorderLayout());
    add(tileset_list.getComponent()  , BorderLayout.WEST  );
    add(tileset_viewer.getComponent(), BorderLayout.CENTER);
  }

  public void selectTile(MapFoundationTile tile) {
    MapTool.setSelectedTile(tile);
  }
  
}
