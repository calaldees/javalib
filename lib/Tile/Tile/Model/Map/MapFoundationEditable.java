package Tile.Model.Map;

import Utils.Types.Integer.*;



public class MapFoundationEditable extends MapFoundationSelectable {

//-------------------------------------------------------------------------
// Static
//-------------------------------------------------------------------------
  public static MapFoundation clone(MapFoundation map) {
    MapFoundationEditable map_editable = new MapFoundationEditable(map);
    MapFoundationEditable map_clone    = map_editable.getClone();
    return new MapFoundation(map_clone);
  }
  
//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------
  
  private static final TileOperation operationCopy  = new TileOperationCopy();
  private static final TileOperation operationPaste = new TileOperationPaste();
  private static final TileOperation operationClear = new TileOperationClear();
 

  
//-------------------------------------------------------------------------
// Constructors
//-------------------------------------------------------------------------
  
  public MapFoundationEditable(Dimension3D   d         ) {super(d);}
  public MapFoundationEditable(MapFoundation source_map) {super(source_map);}
  

  private void clearMap() {
    selectAll();
    eraseSelectedArea();
    selectNone();
  }


//-------------------------------------------------------------------------
// Public Submap Methods
//-------------------------------------------------------------------------
  public MapFoundationEditable getClone() {
    MapFoundationEditable new_map = new MapFoundationEditable(this.getDimension3D());
    new_map.pasteSubMap(this,null,null);
    return new_map;
  }

  public MapFoundation getSubMap() {
    try {
      Cube          encompasing_selection = getSelectedAreasEncompasingCube();
      MapFoundation new_map               = new MapFoundation(encompasing_selection.getDimension3D());
      Point3D       ref_point             =                   encompasing_selection.getPoint3D();
      //System.out.println("Selected:"+encompasing_selection);
      //System.out.println("NewMap:"+new_map.getDimension3D());
      mapOperation(operationCopy, new_map, ref_point, null);
      return new_map;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  public void pasteSubMap(MapFoundation map, Point3D paste_reference, TileOperationOptions options) {
    mapOperation(operationPaste, map, paste_reference, options);
  }


  public void eraseSelectedArea() {
    mapOperation(operationClear, null, null, null);
  }


//-------------------------------------------------------------------------
// Private SubMap Methods
//-------------------------------------------------------------------------


  private void mapOperation(TileOperation op, MapFoundation map, Point3D reference_point, TileOperationOptions options) {
    boolean original_state_null_selection = false;
    if (!hasSelectedArea()) {selectAll(); original_state_null_selection=true;}
    if (reference_point==null) {reference_point=Point3DImmutable.ZERO;}
    //System.out.println("NewOp");
    Point3DUpdatable primary_map___reference_point = new Point3DUpdatable();
    Point3DUpdatable secondary_map_reference_point = new Point3DUpdatable();
    Dimension3D      secondary_map_size;
    if (map!=null) {secondary_map_size = map.getDimension3D();}
    else           {secondary_map_size = getSelectedAreasEncompasingCube().getDimension3D();}
    //Point3D          translation                   = getSelectedAreasReferencePoint();
    
    //for (Cube selection : getSelections()) {
    //  Point3D     sp = selection.getPoint3D();
    //  Dimension3D sd = selection.getDimension3D();
      
      //primary_map___reference_point.setPoint(sp);
      //secondary_map_reference_point.setPoint(reference_point);
      //secondary_map_reference_point.add(sp);
//sp.z()-translation.z()
      for (primary_map___reference_point.z=reference_point.z(), secondary_map_reference_point.z=0 ; primary_map___reference_point.z()<reference_point.z()+secondary_map_size.getHeight() ; primary_map___reference_point.z++, secondary_map_reference_point.z++) {
        //secondary_map_reference_point.z++;
        for (primary_map___reference_point.y=reference_point.y(), secondary_map_reference_point.y=0 ; primary_map___reference_point.y()<reference_point.y()+secondary_map_size.getLength() ; primary_map___reference_point.y++, secondary_map_reference_point.y++) {
          //secondary_map_reference_point.y++;
          for (primary_map___reference_point.x=reference_point.x(), secondary_map_reference_point.x=0 ; primary_map___reference_point.x()<reference_point.x()+secondary_map_size.getWidth() ; primary_map___reference_point.x++, secondary_map_reference_point.x++) {
            //secondary_map_reference_point.x++;
            //System.out.println("Pri:"+primary_map___reference_point+" Sec:"+secondary_map_reference_point);
            if (isInsideSelection(primary_map___reference_point) && 
               (map==null || map.inBounds(secondary_map_reference_point)) ) {
              op.tileOperation(this,
                               primary_map___reference_point,
                               map,
                               secondary_map_reference_point,
                               options);
            }
          }
        }
      //}
    }
    if (original_state_null_selection) {
      selectNone();
    }
  }
 
  
}
  




//-------------------------------------------------------------------------
// TileOperation Class's
//-------------------------------------------------------------------------


interface TileOperation {
  public void tileOperation(MapFoundation map_primary, 
                            Point3D primary_reference_point,
                            MapFoundation map_secondary, 
                            Point3D secondary_reference_point, 
                            TileOperationOptions options);
}


class TileOperationCopy implements TileOperation {
  public void tileOperation(MapFoundation map_primary, 
                            Point3D primary_reference_point,
                            MapFoundation map_secondary, 
                            Point3D secondary_reference_point, 
                            TileOperationOptions options) {
    map_secondary.mergeTile(map_primary.getTile(primary_reference_point), 
                            options,
                            secondary_reference_point);
  }
}

class TileOperationPaste implements TileOperation {
  public void tileOperation(MapFoundation map_primary, 
                            Point3D primary_reference_point,
                            MapFoundation map_secondary, 
                            Point3D secondary_reference_point, 
                            TileOperationOptions options) {
    map_primary.mergeTile(map_secondary.getTile(secondary_reference_point), 
                          options,
                          primary_reference_point);
  }
}

class TileOperationClear implements TileOperation {
  public void tileOperation(MapFoundation map_primary, 
                            Point3D primary_reference_point,
                            MapFoundation map_secondary, 
                            Point3D secondary_reference_point, 
                            TileOperationOptions options) {
    map_primary.setTile(null, primary_reference_point);
  }
}
