package UFO.MapEdit;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import Utils.XML.XMLLoad.Validatable;

import Tile.Cursor.SelectionHighlightIsometric;
import Tile.Map.MapFoundationTile;
import Tile.Renderers.MapRendererIsometric;

import UFO.terrain.UFOTerrainComponent;

public class OutlineFramework implements Validatable {
  
  private UFOTerrainComponent top_left;
  private UFOTerrainComponent top_right;
  private UFOTerrainComponent bottom_left;
  private UFOTerrainComponent bottom_right;
  private UFOTerrainComponent celling;
  private UFOTerrainComponent floor;
  private UFOTerrainComponent floor_base;
  
  private SelectionHighlightIsometric selection_highlight;
  
  public OutlineFramework() {}
  
  public OutlineFramework(UFOTerrainComponent top_left,
                          UFOTerrainComponent top_right,
                          UFOTerrainComponent bottom_left,
                          UFOTerrainComponent bottom_right,
                          UFOTerrainComponent celling,
                          UFOTerrainComponent floor,
                          UFOTerrainComponent floor_base) {
    this.top_left     = top_left;
    this.top_right    = top_right;
    this.bottom_left  = bottom_left;
    this.bottom_right = bottom_right;
    this.celling      = celling;
    this.floor        = floor;
    this.floor_base   = floor_base;
    getSelectionHighlight();
  }
  

  
  public SelectionHighlightIsometric getSelectionHighlight() {
    if (selection_highlight==null) {
      selection_highlight = new SelectionHighlightIsometric(top_left.getSprite(),
                                                            top_right.getSprite(),
                                                            bottom_left.getSprite(),
                                                            bottom_right.getSprite(),
                                                            celling.getSprite(),
                                                            floor.getSprite());
    }
    //return selection_highlight;
    throw new UnsupportedOperationException("The sprites are not offset");
    //Maybe it's worth modifying the selectionHighlight Object to automatically offset the images?
  }

  public MapFoundationTile getTopLeft()     {if (top_left    ==null) return null; return     top_left.getMapFoundationTile();}
  public MapFoundationTile getTopRight()    {if (top_right   ==null) return null; return    top_right.getMapFoundationTile();}
  public MapFoundationTile getBottomLeft()  {if (bottom_left ==null) return null; return  bottom_left.getMapFoundationTile();}
  public MapFoundationTile getBottomRight() {if (bottom_right==null) return null; return bottom_right.getMapFoundationTile();}
  public MapFoundationTile getCelling()     {if (celling     ==null) return null; return      celling.getMapFoundationTile();}
  public MapFoundationTile getFloor()       {if (floor       ==null) return null; return        floor.getMapFoundationTile();}
  public MapFoundationTile getFloorBase()   {if (floor_base  ==null) return null; return   floor_base.getMapFoundationTile();}
  
  public boolean isValid() {
    System.out.println("CHECK: "+toString());
    return top_left     != null &&
           top_right    != null &&
           bottom_left  != null &&
           bottom_right != null; // &&
           //celling      != null &&
           //floor        != null &&
           //floor_base   != null;
  }

  public String toString() {
    return "TL:"+top_left+" TR:"+top_right+" BL:"+bottom_left+" BR:"+bottom_right+" F:"+floor+" FB:"+floor_base+" C:"+celling;
  }
  
  public void preview(Graphics g, ImageObserver io) {
    try { top_left.getSprite().render(g,io);} catch (Exception e) {}
    try {top_right.getSprite().render(g,io);} catch (Exception e) {}
    try {    floor.getSprite().render(g,io);} catch (Exception e) {}
    g.translate(MapRendererIsometric.X_SIZE,0);
    try {drawOffset(g,io,bottom_left ,-MapRendererIsometric.X_JUMP, MapRendererIsometric.Y_JUMP);} catch (Exception e) {}
    try {drawOffset(g,io,bottom_right, MapRendererIsometric.X_JUMP, MapRendererIsometric.Y_JUMP);} catch (Exception e) {}
    try {drawOffset(g,io,celling     , 0                          ,-MapRendererIsometric.Z_JUMP);} catch (Exception e) {}
  }
  private void drawOffset(Graphics g, ImageObserver io, UFOTerrainComponent t, int x_offset, int y_offset) {
    g.drawImage(t.getSprite().getImage(),t.getSprite().getXoffset()+x_offset,t.getSprite().getYoffset()+y_offset,io);
  }
}