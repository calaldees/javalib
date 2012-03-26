package Utils.GUIHelpers;

import Utils.XML.XMLLoad.DataWrapper;
import Utils.XML.XMLLoad.LoadProcessor;
import Utils.XML.XMLLoad.LoadProcessorParseOnly;
import Utils.ImageLoader.ImageBlockLoader;
import Utils.Caster;

import java.util.Collection;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.event.ChangeListener;


//import static XMLSwingProcessor.ICON_PRESSED_SUFFIX;


//import static XMLSwingProcessor.ICON_PRESSED_SUFFIX;
//import static XMLSwingProcessor.BUTTON_GROUP;


public class XMLSwingProcessor {
  
  
  //-----------------------------------------------------------------------
  // Static Constants
  //-----------------------------------------------------------------------
  public static final String ICON_PRESSED_SUFFIX = " pressed";
  public static final String BUTTON_GROUP        = "group";
  public static final String SHORTCUT_KEY        = "shortcut";
  public static final String TOGGLE_BUTTON_CODE  = "toggle";
  public static final String BUTTON_TYPE         = "type";
  public static final String HEIGHT              = "height";
  public static final String WIDTH               = "width";
  
  public static final String TAG_TYPE            = "type";

  //-----------------------------------------------------------------------
  // Swing Processors Init
  //-----------------------------------------------------------------------  
  public XMLSwingProcessor(ActionListener a, ItemListener i, ChangeListener c) {
    new XMLJMenuLookAndFeelProcessor();
    new XMLJMenuViewSelectorProcessor();
    new XMLJMenuToolBarProcessor();
    new XMLJMenuBarProcessor();
    new XMLJMenuProcessor();
    new XMLJMenuItemProcessor(a,i);
    new XMLJSeparatorProcessor();
    new XMLJToolBarProcessor();
    new XMLJToolBarItemProcessor(a,i);
    new XMLJSliderProcessor(c);
    new XMLJPanelProcessor();
    new XMLBorderProcessor();
    new XMLLayoutManagerProcessor();
    new XMLMenuGroupProcessor();
  }
}


//-----------------------------------------------------------------------
// JMenuLookAndFeel
//-----------------------------------------------------------------------

class XMLJMenuLookAndFeelProcessor extends LoadProcessor<JMenu> {
  public XMLJMenuLookAndFeelProcessor() {super(JMenu.class, "JMenuLookAndFeel", false);}
  public JMenu create(             DataWrapper data) throws Exception {return LookAndFeel.getLookAndFeelJMenu();}
  public JMenu overlay(JMenu menu, DataWrapper data) throws Exception {throw new Exception("overlay not supported for " + getClass());}
}

//-----------------------------------------------------------------------
// JMenuViewSelector
//-----------------------------------------------------------------------

class XMLJMenuViewSelectorProcessor extends LoadProcessor<JMenu> {
  public XMLJMenuViewSelectorProcessor() {super(JMenu.class, "JMenuViewSelector", false);}
  public JMenu create(             DataWrapper data) throws Exception {return Utils.ModelViewControllerFramework.ViewManager.getViewMenu();}
  public JMenu overlay(JMenu menu, DataWrapper data) throws Exception {throw new Exception("overlay not supported for " + getClass());}
}

//-----------------------------------------------------------------------
// ToolBarMenu
//-----------------------------------------------------------------------

class XMLJMenuToolBarProcessor extends LoadProcessor<JMenu> {
  public XMLJMenuToolBarProcessor() {super(JMenu.class, "JMenuToolBar", false);}
  public JMenu create(             DataWrapper data) throws Exception {return ToolBarManager.getToolBarJMenu();}
  public JMenu overlay(JMenu menu, DataWrapper data) throws Exception {throw new Exception("overlay not supported for " + getClass());}
}



//-----------------------------------------------------------------------
// JMenuBar
//-----------------------------------------------------------------------

class XMLJMenuBarProcessor extends LoadProcessor<JMenuBar> {
  public XMLJMenuBarProcessor() {super(JMenuBar.class, "JMenuBar");}
  
  public JMenuBar create(                   DataWrapper data) throws Exception {return overlay(new JMenuBar(), data);}
  public JMenuBar overlay(JMenuBar menubar, DataWrapper data) throws Exception {
    Collection<JMenu> menus = data.getObjectsOfType(JMenu.class);
    for (JMenu menu: menus) {menubar.add(menu);}
    return menubar;
  }
}

//-----------------------------------------------------------------------
// JMenu
//-----------------------------------------------------------------------

class XMLJMenuProcessor extends LoadProcessor<JMenu> {
  public XMLJMenuProcessor() {super(JMenu.class, "JMenu");}
  public JMenu create(             DataWrapper data) throws Exception {return overlay(new JMenu(data.getName(),true), data);}
  public JMenu overlay(JMenu menu, DataWrapper data) throws Exception {
    callSetMethods(menu,data);
    for (JComponent menu_item: data.getObjectsOfType(JComponent.class)) {
      if (Caster.castableTo(JSeparator.class,menu_item)) {menu.addSeparator();}
      else                                               {menu.add(menu_item);}
    }
    return menu;
  }
}

//-----------------------------------------------------------------------
// JMenuItem
//-----------------------------------------------------------------------

class XMLJMenuItemProcessor extends LoadProcessor<JMenuItem> {
  private ActionListener action_listener;
  private ItemListener   item_listener;
  
  public XMLJMenuItemProcessor(ActionListener action_listener, ItemListener item_listener) {
    super(JMenuItem.class, "JMenuItem");
    setListener(action_listener);
    setListener(  item_listener);
  }
  
  public JMenuItem create(DataWrapper data) throws Exception {
    JMenuItem item;
    ImageIcon  icon = null;
    try {icon = ImageBlockLoader.getImageIcon(data.getName());} catch (Exception e) {}
    
    if (data.isProperty(XMLSwingProcessor.BUTTON_TYPE, XMLSwingProcessor.TOGGLE_BUTTON_CODE)) { // Checkbox MenuItem
      item = ButtonManager.createJCheckBoxMenuItem(data.getName(),  item_listener,        data.getString(XMLSwingProcessor.SHORTCUT_KEY), data.getString(XMLSwingProcessor.BUTTON_GROUP));
    }
    else { // Normal JMenuItem
      item = ButtonManager.createJMenuItem(        data.getName(), action_listener, icon, data.getString(XMLSwingProcessor.SHORTCUT_KEY));
    }
    return overlay(item,data);
  }
  
  public JMenuItem overlay(JMenuItem item, DataWrapper data) throws Exception {
    callSetMethods(item,data);
    return item;
  }
  // Use the default overlay to call set??? Methods ... this way you can have an XML overlay to setText for multi langauges
  //public Object overlay(Object o, DataWrapper data) throws Exception {
  //  throw new Exception("Overlay not supported for "+JMenuItem.class.getName());
  //}
  
  private void setListener(ActionListener listener) {action_listener = listener;}
  private void setListener(ItemListener   listener) {  item_listener = listener;}
}


//-----------------------------------------------------------------------
// JSeparator
//-----------------------------------------------------------------------

class XMLJSeparatorProcessor extends LoadProcessor<JSeparator> {
  public XMLJSeparatorProcessor() {super(JSeparator.class,"JSeparator",false);}
  public JSeparator create(               DataWrapper data) throws Exception {return new JSeparator();}
  public JSeparator overlay(JSeparator j, DataWrapper data) throws Exception {throw new Exception("Overlay not supported for "+JSeparator.class.getName());}
}


//-----------------------------------------------------------------------
// JToolBar
//-----------------------------------------------------------------------

class XMLJToolBarProcessor extends LoadProcessor<JToolBar> {
  public XMLJToolBarProcessor() {super(JToolBar.class, "JToolBar");}
  
  public JToolBar create(                   DataWrapper data) throws Exception {return overlay(new JToolBar(data.getName()), data);}
  public JToolBar overlay(JToolBar toolbar, DataWrapper data) throws Exception {
    for (JComponent component: data.getObjectsOfType(JComponent.class)) {
      if (Caster.castableTo(JSeparator.class,component)) {toolbar.addSeparator();}
      else                                               {toolbar.add(component);}
    }
    return toolbar;
  }
}



//-----------------------------------------------------------------------
// JToolBarItem
//-----------------------------------------------------------------------

class XMLJToolBarItemProcessor extends LoadProcessor<AbstractButton> {
  private ActionListener action_listener;
  private ItemListener   item_listener;
  
  public XMLJToolBarItemProcessor(ActionListener action_listener, ItemListener item_listener) {
    super(AbstractButton.class, "JToolBarItem");
    setListener(action_listener);
    setListener(  item_listener);
  }
  
  public AbstractButton create(DataWrapper data) throws Exception {
    AbstractButton button;
    ImageIcon  icon = ImageBlockLoader.getImageIcon(data.getName());

    if (data.isProperty(XMLSwingProcessor.BUTTON_TYPE, XMLSwingProcessor.TOGGLE_BUTTON_CODE)) { // Checkbox MenuItem
      button = ButtonManager.createToggleButton(data.getName(), icon, ImageBlockLoader.getImageIcon(data.getName()+XMLSwingProcessor.ICON_PRESSED_SUFFIX), item_listener, data.getString(XMLSwingProcessor.BUTTON_GROUP));
    }
    else { // Normal JButton
      button = ButtonManager.createButton(data.getName(), icon, action_listener);
    }
    return button;
  }

  public AbstractButton overlay(AbstractButton o, DataWrapper data) throws Exception {throw new Exception("Overlay not supported for "+JMenuItem.class.getName());}
  
  private void setListener(ActionListener listener) {action_listener = listener;}
  private void setListener(ItemListener   listener) {  item_listener = listener;}
}


//-----------------------------------------------------------------------
// JSlider
//-----------------------------------------------------------------------

class XMLJSliderProcessor extends LoadProcessor<JSlider> {
  private ChangeListener listener;
  public XMLJSliderProcessor(ChangeListener listener) {
    super(JSlider.class,"JSlider");
    this.listener = listener;
  }

  public JSlider create(DataWrapper data) throws Exception {
    JSlider slider = new JSlider(SwingConstants.HORIZONTAL);
    slider.setBorder((new JButton()).getBorder());
    slider.addChangeListener(listener);
    
    ButtonManager.sizeComponent(slider,data.getInt(XMLSwingProcessor.WIDTH),Constant.BUTTON_SIZE.height);
    return overlay(slider,data);
  }
  public JSlider overlay(JSlider slider, DataWrapper data) throws Exception {
    callSetMethods(slider,data);
    return slider;
  }
}



//-----------------------------------------------------------------------
// JPanel
//-----------------------------------------------------------------------

class XMLJPanelProcessor extends LoadProcessor<JPanel> {
  public XMLJPanelProcessor() {super(JPanel.class,"JPanel",false);}
  
  public JPanel create(DataWrapper data) throws Exception {
    return overlay(new JPanel(), data);
  }
  
  public JPanel overlay(JPanel panel, DataWrapper data) throws Exception {
    callSetMethods(panel,data);
    for (JComponent component: data.getObjectsOfType(JComponent.class)) {
      panel.add(component);
    }
    return panel;
  }
}        



//-----------------------------------------------------------------------
// Border
//-----------------------------------------------------------------------
class XMLBorderProcessor extends LoadProcessor<Border> {
  public static final String TAG_BORDER_SIZE      = "size";
  public static final String TAG_BORDER_COLOR     = "color";
  public static final String TAG_BORDER_TITLE     = "title";
  public static final String TAG_BORDER_THICKNESS = "thickness";
  
  public enum BorderType {
    Empty,         //size
    Etched,
    Line,          //colour, size
    RaisedBevel,
    LoweredBevel,
    Matte,         //size + image
    Titled;         //String
    
    public Border getBorder(DataWrapper data) {
      switch (this) {
        case Empty:
          if (data.containsKey(TAG_BORDER_SIZE)) {int size=data.getInt(TAG_BORDER_SIZE); return BorderFactory.createEmptyBorder(size,size,size,size);}
          else                                   {                                       return BorderFactory.createEmptyBorder();                   }
        case Etched:
          return BorderFactory.createEtchedBorder();
        case Line:
          Color color = Color.decode(data.getString(TAG_BORDER_COLOR));
          if (data.containsKey(TAG_BORDER_THICKNESS)) {return BorderFactory.createLineBorder(color, data.getInt(TAG_BORDER_THICKNESS));}
          else                                        {return BorderFactory.createLineBorder(color                                   );}
        case RaisedBevel:
          return BorderFactory.createRaisedBevelBorder();
        case LoweredBevel:
          return BorderFactory.createLoweredBevelBorder();
        case Matte:
          // Need to get image from DataWrapper
          break;
        case Titled:
          return BorderFactory.createTitledBorder(data.getString(TAG_BORDER_TITLE));
      }
      return null;
    }
  }
    
  public XMLBorderProcessor() {super(Border.class,"Border",false);}
  public Border create(DataWrapper data) throws Exception {
    return (Caster.castEnum(BorderType.class, data.getString(XMLSwingProcessor.TAG_TYPE))).getBorder(data);
  }
  //public Border overlay(Border border, DataWrapper data) throws Exception {throw new Exception("Overlay not supported for "+Border.class.getName());}
}



//-----------------------------------------------------------------------
// Layout Manager
//-----------------------------------------------------------------------
class XMLLayoutManagerProcessor extends LoadProcessor<LayoutManager> {
  public static final String TAG_LAYOUT_ROWS = "rows";
  public static final String TAG_LAYOUT_COLS = "cols";
  
  public enum LayoutType {
    Border,
    Box,
    Flow,
    Grid,
    Spring;
    
    public LayoutManager getLayoutManager(DataWrapper data) {
      switch (this) {
        case Border:
          return new BorderLayout();
        case Box:
          return new BoxLayout(null, BoxLayout.PAGE_AXIS);
        case Flow:
          return new FlowLayout();
        case Grid:
          return new GridLayout(data.getInt(TAG_LAYOUT_ROWS),data.getInt(TAG_LAYOUT_COLS));
        case Spring:
          return new SpringLayout();
      }
      return null;
    }
  }
    
  public XMLLayoutManagerProcessor() {super(LayoutManager.class,"Layout",false);}
  public LayoutManager create(DataWrapper data) throws Exception {
    LayoutManager layout_manager = (Caster.castEnum(LayoutType.class, data.getString(XMLSwingProcessor.TAG_TYPE))).getLayoutManager(data);
    //Twaking here? spring layout?
    return layout_manager;
  }
}


//-----------------------------------------------------------------------
// TextField
//-----------------------------------------------------------------------
class XMLJTextFieldProcessor extends LoadProcessor<JTextField> {  
  public static final String TAG_JTEXTFIELD_COLS = "size";
  public XMLJTextFieldProcessor() {super(JTextField.class,"JTextField",false);}
  
  public JTextField create(DataWrapper data) throws Exception {
    if (data.containsKey(TAG_JTEXTFIELD_COLS)) {new JTextField(data.getInt(TAG_JTEXTFIELD_COLS));}
    else                                       {new JTextField();}
    return null;
  }
}


//-----------------------------------------------------------------------
// Menu Button Group
//-----------------------------------------------------------------------
class XMLMenuGroupProcessor extends LoadProcessorParseOnly {
  public XMLMenuGroupProcessor() {super("MenuGroup");}
  public void parse(DataWrapper data) {
    //System.out.println("name:"+data.getName()+" buttons:"+data.getStrings("buttons"));
    ButtonManager.addMenuGroup(data.getName(), data.getStrings("buttons"));
    ButtonManager.chanageMenuGroupEnableState(data.getName(), data.getBool("default_state"));
    /*
    for (String key: data.keySet()) {
      String[] button_names = data.getStrings(key);
      if (button_names!=null) {
        ButtonManager.addMenuGroup(key, button_names);
      }
    }
    */
  }
}
