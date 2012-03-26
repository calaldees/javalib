package Utils.GUIHelpers;

import java.util.Collection;
import java.util.Vector;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;


public class JInternalFrameWithMenuItem extends JInternalFrame implements InternalFrameListener, ItemListener {
  
  private static final long serialVersionUID = 0;
  
  private static final JMenu                                  window_menu  = createWindowMenu();
  private static final ButtonGroup                            window_group = new ButtonGroup();
  private static final Collection<JInternalFrameWithMenuItem> frame_list   = new Vector<JInternalFrameWithMenuItem>();
  
  public    static JMenu                                  getWindowJMenu() {return window_menu;}
  protected static Collection<JInternalFrameWithMenuItem> getFrames()      {return frame_list;}
  
  private JCheckBoxMenuItem menu_item;
  
  public JInternalFrameWithMenuItem(String title                                                                                 ) {this(title,true,true,true,true);}
  public JInternalFrameWithMenuItem(String title, boolean resizeable, boolean closeable, boolean maximizable, boolean iconifiable) {
    super(title, resizeable, closeable, maximizable, iconifiable);
    addInternalFrameListener(this);
    //addInternalFrameListener(MapEditor.getListener());
    menu_item = new JCheckBoxMenuItem(getName());
    menu_item.addItemListener(this);
    window_menu.add(menu_item);
    window_group.add(menu_item);
    frame_list.add(this);
    setTitle(getTitle());
    setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
    setVisible(true);
  }

  private static JMenu createWindowMenu() {
    JMenu window = new JMenu("Window");
    window.add(ButtonManager.createJMenuItem("Tile Horizontaly", null ));
    window.addSeparator();
    window.add(ButtonManager.createJMenuItem("Next Map", null, "F6"));
    window.add(ButtonManager.createJMenuItem("Close"   , null, "F4"));
    window.addSeparator();
    return window;
  }
  
  public void dispose() {
    window_menu.remove(menu_item);
    window_group.remove(menu_item);
    frame_list.remove(this);
    super.dispose();
  }
  
//-------------------------------------------------------------------------
// Private
//-------------------------------------------------------------------------
  
  public void setTitle(String name) {
    super.setTitle(name);
    menu_item.setText(getTitle());
    menu_item.setActionCommand(getTitle());
    //setTitle(title+UFO.Constant.SEPARATOR+file);
  }

//-------------------------------------------------------------------------
// Listeners
//-------------------------------------------------------------------------


  public void itemStateChanged(ItemEvent e) {
    AbstractButton source = (AbstractButton)(e.getSource());
    if (source.isSelected()) {
      try{setSelected(true);}
      catch (Exception ex) {}
    }
  }

  public void internalFrameActivated(InternalFrameEvent e)   {menu_item.setState(true);}
  public void internalFrameClosed(InternalFrameEvent e)      {}
  public void internalFrameClosing(InternalFrameEvent e)     {dispose();}
  public void internalFrameDeactivated(InternalFrameEvent e) {menu_item.setState(false);}
  public void internalFrameDeiconified(InternalFrameEvent e) {}
  public void internalFrameIconified(InternalFrameEvent e)   {}
  public void internalFrameOpened(InternalFrameEvent e)      {}



}
