package MapEdit.GUIComponents;



import java.awt.Dimension;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.BevelBorder;

import Tile.Model.Map.MapFoundationTile;
import Tile.Model.Map.Terrain.AbstractTileComponent;
import Utils.ModelViewControllerFramework.ViewManager;



public class TileSetViewerSimple extends AbstractTileSetViewer {
  
  private JPanel      gui_component;
  private JScrollPane scroll_pane;
  
  public TileSetViewerSimple() {
    super();
    gui_component = new JPanel();
    scroll_pane   = new JScrollPane(gui_component,JScrollPane.VERTICAL_SCROLLBAR_NEVER,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
  }
  
  protected void update() {
    gui_component.removeAll();
    int count = 0;
    for (AbstractTileComponent tile: getTileSet().getTerrainComponents()) {
      if (!tile.getHideInEditor() || (getShowHiddenTilesState() && tile.getHideInEditor())) {
        gui_component.add(new TileViewerComponent(this,tile.getMapFoundationTile()));
        //count++;
      }
    }
    gui_component.invalidate();
    //gui_component.repaint();
    //System.out.println("COMPONENTS="+count);
  }
  
  public Component getComponent() {return scroll_pane;}
 
  void select(MapFoundationTile tile) {selectTile(tile);}
}



class TileViewerComponent extends JPanel implements MouseListener {
  
  private static final long serialVersionUID = 0;
  
  private static final Border border_hover    = BorderFactory.createBevelBorder(BevelBorder.RAISED);
  private static final Border border_unselect = BorderFactory.createEmptyBorder();
  private static final Border border_selected = BorderFactory.createEtchedBorder();
  
  private TileSetViewerSimple viewer;
  private MapFoundationTile   tile;
  
  public TileViewerComponent(TileSetViewerSimple viewer, MapFoundationTile tile) {
    super();
    this.tile   = tile;
    this.viewer = viewer;
    Dimension size = ViewManager.getDefaultView().getRenderer(MapFoundationTile.class).getComponentSize(tile, null);
    setTileComponentSize(size.width,size.height);
    addMouseListener(this);
  }
  
  private void setTileComponentSize(int x, int y) {
    Dimension d = new Dimension(x,y);
    setPreferredSize(d);
    setMaximumSize(d);
    setMinimumSize(d);
  }
  
  public void paintComponent(Graphics g) {
    g.clearRect(0,0,getWidth(),getHeight());
    ViewManager.getDefaultView().getRenderer(MapFoundationTile.class).render(g,this,tile,null);
    //g.setColor(java.awt.Color.BLACK);
    //g.drawRect(0,0,10,10);
  }

  public void mouseEntered(MouseEvent e)  {setBorder(border_hover   ); repaint();}
  public void mouseExited(MouseEvent e)   {setBorder(border_unselect); repaint();}
  public void mousePressed(MouseEvent e)  {setBorder(border_selected); repaint(); viewer.select(tile);}
  
  public void mouseClicked(MouseEvent e)  {}
  public void mouseReleased(MouseEvent e) {}
}