package Utils.ModelViewControllerFramework;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;

public class ViewManager {

  private static Map<String,View> views = new HashMap<String,View>();
  
  private static String default_view = "default";

  //----------------------------------------------------------------------------
  // Static Swing View Menu
  //----------------------------------------------------------------------------

  private static final JMenu            view_menu     = createWindowMenu();
  private static final ButtonGroup      view_group    = new ButtonGroup();
  private static final ViewMenuListener view_listener = new ViewMenuListener();

  public static JMenu getViewMenu() {return view_menu;}
  
  private static JMenu createWindowMenu() {
    JMenu window = new JMenu("View");
    /*
    window.add(ButtonManager.createJMenuItem("Tile Horizontaly", null ));
    window.addSeparator();
    window.add(ButtonManager.createJMenuItem("Next Map", null, "F6"));
    window.add(ButtonManager.createJMenuItem("Close"   , null, "F4"));
    window.addSeparator();
     */
    return window;
  }

  private static void addViewMenuItem(View v) {
    JCheckBoxMenuItem menu_item = new JCheckBoxMenuItem(v.getName());
    menu_item.setName(v.getName());
    menu_item.addItemListener(view_listener);
    view_menu.add(menu_item);
    view_group.add(menu_item);
  }
  public static void addViewSelectionListener(ViewSelectionListener listener) {view_listener.addViewSelectionListener(listener);}

  //----------------------------------------------------------------------------
  // View Manager
  //----------------------------------------------------------------------------


  public static void addView (View v) {
    addViewMenuItem(v);
    views.put(v.getName(), v);
  }
  public static View               getView (String view_name) {return views.get(view_name);}
  public static Collection<String> getViews()                 {return views.keySet();      }
  
  public static View getDefaultView() {
    if      (views.size()==0)                 {return null;}
    else if (views.containsKey(default_view)) {return getView(default_view);}
    else                                      {return views.values().iterator().next();}
  }
  public static void setDefaultView(String view_name) {default_view = view_name;}
}

//----------------------------------------------------------------------------
// JMenuItemListener Class
//----------------------------------------------------------------------------

class ViewMenuListener implements ItemListener {
  private final Collection<ViewSelectionListener> listeners = new ArrayList<ViewSelectionListener>();
  public void addViewSelectionListener(ViewSelectionListener listener) {listeners.add(listener);}
  public void itemStateChanged(ItemEvent e) {
    AbstractButton source = (AbstractButton)(e.getSource());
    if (source.isSelected()) {
      for (ViewSelectionListener listener : listeners) {
        listener.selectView(ViewManager.getView(source.getName()));
      }
    }
  }
}