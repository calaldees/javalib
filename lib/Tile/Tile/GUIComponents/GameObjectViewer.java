package Tile.GUIComponents;


import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Method;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.BoxLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import Tile.Model.GameObject.GameObject;
import Tile.Model.GameObject.GameObjectTemplate;
import Utils.GUIHelpers.GUIBoundToObjectFactory;
import Utils.GUIHelpers.LoadedItemList;
import Utils.GUIHelpers.LoadedSelectionListener;
import Utils.Types.DirectionDiscreet;
import Utils.ModelViewControllerFramework.ViewManager;
import Utils.ModelViewControllerFramework.ViewRenderer;


public class GameObjectViewer {

  private JPanel panel_main = new JPanel();
  //private  controls;
  
  
  public GameObjectViewer(Class c) {
    GameObjectControlPanel controls = new GameObjectControlPanel();
    
    LoadedItemList<GameObjectTemplate> list = new LoadedItemList<GameObjectTemplate>(c);
    list.setCellRenderer(new RTSUnitTemplateCellRenderer());
    list.addLoadedSelectionListener(controls);
    
    panel_main.setLayout(new BorderLayout());
    panel_main.add(list.getComponent(), BorderLayout.WEST);
    panel_main.add(controls           , BorderLayout.EAST);
  }

  public JPanel getComponent() {return panel_main;}

}



class RTSUnitTemplateCellRenderer extends JLabel implements ListCellRenderer {
  
  public RTSUnitTemplateCellRenderer() {setOpaque(true);}

  public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    setText(getGameObjectName(value));
    setIcon(createIcon(value));
    setIconTextGap(5);

    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
    }
    else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
    }

    setEnabled(list.isEnabled());
    setFont(list.getFont());
    setOpaque(true);

    return this;
  }
  
  private String getGameObjectName(Object obj) {
    if (obj instanceof GameObjectTemplate) {return ((GameObjectTemplate)obj).getName();}
    else                                   {return                       obj.toString();}
  }
  
  private ImageIcon createIcon(Object obj) {
    ImageIcon return_icon = null;
    try {
      GameObjectTemplate template    = (GameObjectTemplate)obj;
      GameObject         game_object = template.createGameObject();
      game_object.setDirection(DirectionDiscreet.S.getDirection());
      ViewRenderer r = ViewManager.getDefaultView().getRenderer(game_object.getClass());
      Dimension sprite_size = r.getComponentSize(game_object, null);//template.getSpriteSet().getSize();
      BufferedImage temp_image = new BufferedImage(sprite_size.width,sprite_size.height,BufferedImage.TYPE_INT_ARGB);
      r.render(temp_image.getGraphics(), null, game_object,null);
      return_icon = new ImageIcon(temp_image);
    }
    catch (Exception e) {e.printStackTrace();}
    return return_icon;
  }
}







class GameObjectControlPanel extends JPanel implements LoadedSelectionListener<GameObjectTemplate>, ChangeListener {
  private GameObjectDisplayPanel game_object_panel    = new GameObjectDisplayPanel();
  private JPanel                 game_object_controls = new JPanel();
  private GameObject             o;
  
  public GameObjectControlPanel() {
    setLayout(new BorderLayout());
    game_object_controls.setLayout(new BoxLayout(game_object_controls,BoxLayout.Y_AXIS));
    add(game_object_panel   , BorderLayout.NORTH);
    add(game_object_controls, BorderLayout.SOUTH);
  }
  
  public void setGameObjectTemplate(GameObjectTemplate template) {
    o = null;
    if (template!=null) {o = template.createGameObject();}
    game_object_panel.setGameObject(o);
    reInitControls();
  }
  
  public void itemSelected(GameObjectTemplate item) {setGameObjectTemplate(item);} 
  
  public void reInitControls() {
    game_object_controls.removeAll();
    Method[] methods = Utils.ReflectionUtils.getMethods(o, "set");
    for(Method m : methods) {
      game_object_controls.add(GUIBoundToObjectFactory.getComponent(o, m,this));
    }
    game_object_controls.revalidate();
  }

  public void stateChanged(ChangeEvent e) {game_object_panel.repaint();}
}
class GameObjectDisplayPanel extends JPanel {
  private GameObject o;
  public GameObjectDisplayPanel() {
    setSizeSimple(new Dimension(100,100));
  }
  public void setGameObject(GameObject o) {
    this.o = o;
    if (o!=null) {
      //System.out.println("selected:"+o.getTemplate().getName());
      //setSizeSimple(o.getTemplate().getSpriteSet().getSize().getAWTDimension());
    }
    repaint();
  }
  public void paint(Graphics g) {
    g.fillRect(0,0,getWidth(),getHeight());
    try {ViewManager.getDefaultView().render(g, this,o,null);}
    catch (Exception e) {}
  }
  private void setSizeSimple(Dimension d) {
    setPreferredSize(d);
    setMinimumSize(d);
    setMaximumSize(d);    
  }
}
