package MapEdit.GUIComponents;

import java.util.Collection;
import java.util.Vector;
import java.awt.Component;

import Tile.Model.Map.Terrain.TileSet;
import Tile.Model.Map.MapFoundationTile;
import MapEdit.GUIComponents.event.TileSelectedListener;
import Utils.GUIHelpers.LoadedSelectionListener;

public abstract class AbstractTileSetViewer implements LoadedSelectionListener<TileSet> {
  
//-------------------------------------------------------------------------
// Current TileSet
//-------------------------------------------------------------------------
  private TileSet tileset;
  private void setTileSet(TileSet tileset) {
    this.tileset = tileset;
    update();
  }
  protected TileSet getTileSet() {return tileset;}
  
//-------------------------------------------------------------------------
// Show Hidden Tile Status
//-------------------------------------------------------------------------
  private boolean show_hidden_tiles = false;
  public void    setShowHiddenTilesState(boolean show) {
    show_hidden_tiles = show;
    update();
  }
  public boolean getShowHiddenTilesState() {return show_hidden_tiles;}
  
//-------------------------------------------------------------------------
// TileSelected Listeners - When an tile is selected notify the listeners
//-------------------------------------------------------------------------
  private Collection<TileSelectedListener> listeners = new Vector<TileSelectedListener>();
  public boolean    addTileSelectedListener(TileSelectedListener listener) {return listeners.add(listener);}
  public boolean removeTileSelectedListener(TileSelectedListener listener) {return listeners.remove(listener);}
  
  protected void selectTile(MapFoundationTile tile) {
    for (TileSelectedListener listener: listeners) {listener.selectTile(tile);}
  }
  
//-------------------------------------------------------------------------
// LoadedSelectionListener - when a tileset is selected from a LoadedItemList (JList)
//-------------------------------------------------------------------------
  public void itemSelected(TileSet selected_tileset) {setTileSet(selected_tileset);}
  
//-------------------------------------------------------------------------
// Abstract Methods
//-------------------------------------------------------------------------
  protected abstract void      update(); //The Component needs updating because of 1.) TileSet Change 2.) HiddenStaus Change 3.) Other update needed
  public    abstract Component getComponent();

}