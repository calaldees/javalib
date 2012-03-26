package Utils.GUIHelpers;


import java.util.Collection;
import java.util.Vector;
import java.util.HashMap;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


class ButtonManager implements ItemListener {

    private static final String CTRL_MASK = "control ";  //Used for JMenuItems and shortcut keys
    
//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------

  private static ItemListener                               button_group_listener = new ButtonManager();
  private static HashMap<String,Collection<AbstractButton>> buttons               = new HashMap<String,Collection<AbstractButton>>(); //All buttons indexed by name
  private static HashMap<String,Collection<AbstractButton>> checkbox_group        = new HashMap<String,Collection<AbstractButton>>(); //Checkbox buttons that are linked e.g Tool on menu and toolbar that need to both be depressed when one is depressed
  private static HashMap<String,ButtonGroup>                single_select_group   = new HashMap<String,ButtonGroup>();                //Only one tool can be selected at a time
  private static HashMap<String,String[]>                   menu_group            = new HashMap<String,String[]>(); //Groups of buttons that need to be enabled/disabled e.g. paste when the clipboard is empty

//-------------------------------------------------------------------------
// Dumby Constructor
// used to access itemStateListener
// mutter grumble, must be a better way of doing this
//-------------------------------------------------------------------------

  private ButtonManager() {}


//-------------------------------------------------------------------------
// Public Static Methods
//-------------------------------------------------------------------------




  //-------------------------------------------------------------------------
  // Button Creation
  //-------------------------------------------------------------------------

  public static JButton createButton(String name, ImageIcon icon, ActionListener listener) {
    JButton button = new JButton(icon);
    button.setName(name);
    button.setActionCommand(name);
    button.addActionListener(listener);
    sizeButton(button);
    addButton(button);
    return button;
  }

  public static JToggleButton createToggleButton(String name, ImageIcon icon,                         ItemListener listener                     ) {return createToggleButton(name,icon   ,     null,listener,null);}
  public static JToggleButton createToggleButton(String name, ImageIcon icon_up, ImageIcon icon_down, ItemListener listener                     ) {return createToggleButton(name,icon_up,icon_down,listener,null);}
  public static JToggleButton createToggleButton(String name, ImageIcon icon_up, ImageIcon icon_down, ItemListener listener, String select_group) {
    JToggleButton button = new JToggleButton(icon_up);
    button.setName(name);
    if (!buttons.containsKey(name)) {button.addItemListener(listener);}
    sizeButton(button);
    addButton(button);
    addToCheckBoxGroup(button);
    setSingleSelectGroup(select_group, button);
    if (icon_down   !=null) {button.setSelectedIcon(icon_down);}
    return button;
  }


  public static JMenuItem createJMenuItem(String name, ActionListener listener                                     ) {return createJMenuItem(name,listener,null,null);}
  public static JMenuItem createJMenuItem(String name, ActionListener listener, ImageIcon icon                     ) {return createJMenuItem(name,listener,icon,null);}
  public static JMenuItem createJMenuItem(String name, ActionListener listener,                 String shortcut_key) {return createJMenuItem(name,listener,null,shortcut_key);}
  public static JMenuItem createJMenuItem(String name, ActionListener listener, ImageIcon icon, String shortcut_key) {
    if (name==null) {return null;}
    JMenuItem item = new JMenuItem(name);
    item.setName(name);
    item.setActionCommand(name);
    item.addActionListener(listener);
    if (shortcut_key!=null) {item.setAccelerator(KeyStroke.getKeyStroke(CTRL_MASK + shortcut_key));}
    if (icon!=null) {item.setIcon(icon);}
    addButton(item);
    return item;
  }

  public static JCheckBoxMenuItem createJCheckBoxMenuItem(String name, ItemListener listener                                          ) {return createJCheckBoxMenuItem(name, listener, null,         null);}
  public static JCheckBoxMenuItem createJCheckBoxMenuItem(String name, ItemListener listener                     , String select_group) {return createJCheckBoxMenuItem(name, listener, null, select_group);}
  public static JCheckBoxMenuItem createJCheckBoxMenuItem(String name, ItemListener listener, String shortcut_key, String select_group) {
    JCheckBoxMenuItem item = new JCheckBoxMenuItem(name);
    item.setName(name);
    if (!buttons.containsKey(name)) {item.addItemListener(listener);} // Only assign a listener to the fist button of this name. Otherwise multiple events for the same event get fired (an event for each button in the group). When one changes the first WILL change and the event is triggered
    if (shortcut_key!=null) {item.setAccelerator(KeyStroke.getKeyStroke(CTRL_MASK + shortcut_key));}
    addButton(item);
    addToCheckBoxGroup(item);
    setSingleSelectGroup(select_group, item);
    return item;
  }

  //-------------------------------------------------------------------------
  // Remove Button
  //-------------------------------------------------------------------------
  public static void removeButton(AbstractButton button) {
    String name = button.getName();
    if (buttons.containsKey(name)) {      
      buttons.get(name).remove(button);
      if (buttons.get(name).size()==0) {buttons.remove(name);}
    }
    if (checkbox_group.containsKey(name)) {
      checkbox_group.get(name).remove(button);
      if (checkbox_group.get(name).size()==0) {checkbox_group.remove(name);}
    }
    for (ButtonGroup group : single_select_group.values()) {
      group.remove(button);
    }
  }

  //-------------------------------------------------------------------------
  // Public Helpers
  //-------------------------------------------------------------------------

  public static void sizeButton(AbstractButton button) {
    sizeComponent(button, Constant.BUTTON_SIZE);
  }
  
  public static void sizeComponent(JComponent c, int x, int y) {
    if (x<=0) {x=c.getWidth(); }
    if (y<=0) {x=c.getHeight();}
    sizeComponent(c, new Dimension(x,y));
  }
  public static void sizeComponent(JComponent c, Dimension d) {
    c.setPreferredSize(d);
    c.setMinimumSize(d);
    c.setMaximumSize(d);
  }



  //-------------------------------------------------------------------------
  // Enable/Disable Buttons
  //-------------------------------------------------------------------------

  public static void addMenuGroup(String name, String[] button_names) {
    menu_group.put(name,button_names);
  }
  
  public static void chanageMenuGroupEnableState(String group, boolean state) {
    if (menu_group.containsKey(group)) {
      changeButtonEnableState(menu_group.get(group), state);
    }
    else {Utils.ErrorHandeler.error(ButtonManager.class, "Button Group does not exist", "The button group "+group+" has not been defined");}
  }
  
  private static void changeButtonEnableState(String[] names, boolean state) {
    if (names!=null && names.length>0) {
      for (String name : names) {changeButtonEnableState(name, state);}
    }
  }
  
  private static void changeButtonEnableState(String name, boolean state) {
    if (buttons.containsKey(name)) {
      for (AbstractButton button : buttons.get(name)) {
        button.setEnabled(state);
      }
    }
    //catch (Exception e) {}
  }

//-------------------------------------------------------------------------
// Single Group Select Managemenet
//-------------------------------------------------------------------------
  
  private static void setSingleSelectGroup(String group, AbstractButton button) {
    if (group!=null) {getSingleSelectGroup(group).add(button);}
  }
  private static ButtonGroup getSingleSelectGroup(String name) {
    ButtonGroup group = null;
    try {group = single_select_group.get(name);} catch (Exception e) {}
    if (group==null) {
      group = new ButtonGroup(); 
      single_select_group.put(name,group);
    }
    return group;
  }

//-------------------------------------------------------------------------
// Private Methods
//-------------------------------------------------------------------------

  private static void addButton(AbstractButton button) {
    String name = button.getName();
    if (buttons.containsKey(name)) {buttons.get(name).add(button);}
    else {
      Vector<AbstractButton> button_list = new Vector<AbstractButton>();
      button_list.add(button);
      buttons.put(name,button_list);
    }
  }

  private static void addToCheckBoxGroup(AbstractButton button                   ) {addToCheckBoxGroup(button,button.getName());}
  private static void addToCheckBoxGroup(AbstractButton button, String group_name) {
    Collection<AbstractButton> group = checkbox_group.get(group_name);
    if (group==null) {
      group = new Vector<AbstractButton>();
      checkbox_group.put(group_name, group);
    }
    group.add(button);
    button.addItemListener(button_group_listener);
  }



//-------------------------------------------------------------------------
// Item Listener
// used for checkbox groups to keep the same state
//-------------------------------------------------------------------------


  public void itemStateChanged(ItemEvent e) {
    AbstractButton source = (AbstractButton)(e.getSource());
    String         name   = source.getName();
    boolean        state  = source.isSelected();

    for (AbstractButton button : checkbox_group.get(name)) {
      button.setSelected(state);
    }
  }

}
