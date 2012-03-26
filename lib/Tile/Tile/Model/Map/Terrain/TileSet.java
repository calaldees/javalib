package Tile.Model.Map.Terrain;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Vector;

import Tile.Model.Map.MapFoundation;
import Tile.Model.Map.MapFoundationTile;
import Utils.XML.XMLLoad.Indexable;

public class TileSet implements Indexable {

  private final String name;
  private final Map<String, AbstractTileComponent> tiles     = new HashMap<String,AbstractTileComponent>();
  private final Map<String, MapFoundation        > prefabs   = new HashMap<String,MapFoundation>();
  private final Map<String, TileTemplate         > templates = new HashMap<String,TileTemplate>();

  public TileSet(String name) {
    this.name = name;
    //this.predefined_objects = predefined_objects;
  }

  public String                            getName()               {return name;}
  public Collection<AbstractTileComponent> getTerrainComponents()  {return   tiles.values();}
  public Collection<MapFoundation        > getPrefabs()            {return prefabs.values();}
  public Collection<MapFoundationTile    > getMapFoundationTiles() {
    Collection<MapFoundationTile> maptiles_from_components = new Vector<MapFoundationTile>();
    for (AbstractTileComponent component: getTerrainComponents()) {
      maptiles_from_components.add(component.getMapFoundationTile());
    }
    return maptiles_from_components;
  }

  public void addTemplate(Collection<TileTemplate> templates) {for (TileTemplate template: templates) {addTemplate(template);}}
  public void addTemplate(TileTemplate template             ) {
    //tile.setParent(this);
    templates.put(template.getName(), template);
  }
  
  public void addTerrainComponent(Collection<AbstractTileComponent> tiles) {for (AbstractTileComponent tile: tiles) {addTerrainComponent(tile);}}
  public void addTerrainComponent(AbstractTileComponent tile             ) {
    //tile.setParent(this);
    tiles.put(tile.getName(), tile);
  }

  public void addPrefab(Collection<MapFoundation> prefabs) {for (MapFoundation prefab: prefabs) {addPrefab(prefab);}}
  public void addPrefab(MapFoundation prefab) {
    //System.out.println("Adding Map: "+prefab.getName());
    prefabs.put(prefab.getName(),prefab);
  }
  
  public MapFoundation getPrefab(String name) {
    try                 {return prefabs.get(name);}
    catch (Exception e) {return null;}
  }

  public TileTemplate getTemplate(String name) {
    try                 {return templates.get(name);}
    catch (Exception e) {return null;}    
  }
  
  public AbstractTileComponent getTerrainComponent(String name) {
    try                 {return tiles.get(name);}
    catch (Exception e) {return null;}
  }

  public MapFoundationTile getMapFoundationTile(String name) {
    try                 {return tiles.get(name).getMapFoundationTile();}
    catch (Exception e) {return null;}
  }
  
  public String toString() {
    return getClass().getName()+":"+getName()+" Tiles="+getTerrainComponents().size();
  }
}