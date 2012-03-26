
package Utils.GUIHelpers;

import Utils.XML.XMLLoad.LoadManager;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.AbstractButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JToolBar;


class ToolBarManager implements ItemListener {

  private static JMenu  toolbar_menu = new JMenu("Toolbars");
  private        JPanel toolbar_panel;
  
  private ToolBarManager(JPanel toolbar_panel) {this.toolbar_panel = toolbar_panel;}
  
  public static JMenu getToolBarJMenu() {return toolbar_menu;}
    
  public static void attachGUItoToolBarMenu(JPanel toolbar_panel) {
    if (toolbar_panel==null) {throw new IllegalArgumentException();}
    ToolBarManager listener = new ToolBarManager(toolbar_panel);
    toolbar_menu.removeAll();
    for (JToolBar toolbar : LoadManager.getGroupList(JToolBar.class)) {
      JCheckBoxMenuItem toolbar_menu_item = ButtonManager.createJCheckBoxMenuItem(toolbar.getName(),listener);
      toolbar_menu_item.setSelected(true);
      toolbar_menu.add(toolbar_menu_item);
    }
  }
  
  public void itemStateChanged(ItemEvent e) {
    AbstractButton source    = (AbstractButton)(e.getSource());
    String         name      = source.getName();
    boolean        selected  = source.isSelected();

    if (selected) {toolbar_panel.add   (LoadManager.getItem(JToolBar.class,name));}
    else          {toolbar_panel.remove(LoadManager.getItem(JToolBar.class,name));}
    //toolbar_panel.invalidate();
    toolbar_panel.validate();
  }
 
}