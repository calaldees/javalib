package Tile.GUIComponents;

import Tile.Model.Map.MapFoundation;
import Tile.View.MapFoundation.AbstractMapRenderer;
import Utils.ModelViewControllerFramework.View;
import Utils.ModelViewControllerFramework.ViewManager;
import Utils.StringIndexedObjects;
import Utils.Types.Integer.Point3D;
import Utils.Types.LinkedInteger;
import Utils.Types.LinkedIntegerListener;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;

public class MapComponent extends JComponent implements Scrollable, LinkedIntegerListener {

  private static final long serialVersionUID = 0;

  public static final String LABLENAME_LAYER = "layer";
    
  //-------------------------------------------------------------------------
  // Variables
  //-------------------------------------------------------------------------
  private   final MapFoundation              map;
  protected final StringIndexedObjects       rendering_options = new StringIndexedObjects();
  private   final LinkedInteger              layer             = new LinkedInteger(LABLENAME_LAYER,0,0,4);
  private   final Dimension                  component_size    = new Dimension(0,0);
  
  private AbstractMapRenderer r;
  
  public MapComponent(MapFoundation map) {
    this.map = map;
    setView(null);
    setLayerValue(map.getDimension3D().getHeight()-1);
  }
  
  //-------------------------------------------------------------------------
  // Public Component
  //-------------------------------------------------------------------------
  public MapFoundation getMap() {return map;}
  
  public LinkedInteger getLayerLinkedInteger()        {return layer;}
  public int           getLayerValue(               ) {return layer.getValue();}
  public void          setLayerValue(int layer_value) {layer.setValue(layer_value);}
  public void          setLayerValueUp()              {setLayerValue(getLayerValue()+1);}
  public void          setLayerValueDown()            {setLayerValue(getLayerValue()-1);}
  public void linkedValueUpdated(String name) {
    if (name.equals(LABLENAME_LAYER)) {
      rendering_options.add(LABLENAME_LAYER,getLayerValue());
      calcNewComponentSize(); //NOTE: investigate if this is drawing twice, calc..Size() invalidates and revalidiates the component, this may cause a repaint in addition to the repaint below
      repaint();
    }
  }
  
  public Dimension getMinimumSize()   {return component_size;}
  public Dimension getMaximumSize()   {return component_size;}
  public Dimension getPreferredSize() {return component_size;}

  public void setView(View view) {
    try {
      if (view==null) {view = ViewManager.getDefaultView();}
      r = (AbstractMapRenderer)view.getRenderer(MapFoundation.class);
    }
    catch (Exception e) {}
    calcNewComponentSize();
  }
  
  public void paint(Graphics g) {
    r.render(g,this,getMap(),rendering_options);
  }
  
  //-------------------------------------------------------------------------
  // Scrollable Component
  //-------------------------------------------------------------------------
  public Dimension getPreferredScrollableViewportSize() {return getPreferredSize();}
  public int       getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
    //TODO: this needs to be modifyed to take into account direction - tidy it up monkey!
    int inc = 0;
    if (orientation==SwingConstants.HORIZONTAL) {
      inc = visibleRect.x % r.getXJump();
      if (inc==0) {inc = r.getXJump();}
    }
    else if (orientation==SwingConstants.VERTICAL  ) {
      inc = visibleRect.y % r.getYJump();
      if (inc==0) {inc = r.getYJump();}
    }
    return inc;
  }
  public int       getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
    int inc = 0;
    if (orientation==SwingConstants.HORIZONTAL) {inc = r.getXJump()*5;}
    if (orientation==SwingConstants.VERTICAL  ) {inc = r.getXJump()*5;}
    //ISOMETRIC??
    //int inc = getScrollableUnitIncrement(visibleRect, orientation, direction);
    //if      (orientation==SwingConstants.HORIZONTAL) {inc+=visibleRect.width /UFO.Constant.X_JUMP/2*UFO.Constant.X_JUMP;}
    //else if (orientation==SwingConstants.VERTICAL  ) {inc+=visibleRect.height/UFO.Constant.Y_JUMP/2*UFO.Constant.Y_JUMP;}
    //return inc;
    return inc;
  }
  public boolean   getScrollableTracksViewportWidth() {return false;}
  public boolean   getScrollableTracksViewportHeight() {return false;}

  //-------------------------------------------------------------------------
  // Private
  //-------------------------------------------------------------------------
  
  // Calculate this JCompoennt size of the map in pixels
  private void calcNewComponentSize() {
    component_size.setSize(r.getComponentSize(getMap(),rendering_options));
    invalidate();
    revalidate(); //these may not both be needed ... they may be causing the component to paint twice .. investigate
  }

  protected Point3D   getMapTileLocation(Point   p) {return r.getMapTileLocation(getMap(),rendering_options,p);}
  protected Rectangle getClip           (Point3D p) {return r.getClip           (getMap(),rendering_options,p);}
 
}