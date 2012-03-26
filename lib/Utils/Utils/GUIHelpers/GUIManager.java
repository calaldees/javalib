package Utils.GUIHelpers;

import java.awt.event.WindowEvent;
import java.util.Collection;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.*;

import Utils.ErrorHandeler;
import Utils.XML.XMLLoad.LoadManager;
import Utils.Config.ConfigManager;
import Utils.File.MyFileFilter;
import Utils.GUIHelpers.JInputDialog.JInputDialog;
import java.awt.event.WindowListener;


public class GUIManager {

  //-------------------------------------------------------------------------------
  // Static
  //-------------------------------------------------------------------------------
  private static CommandManager cm  = new CommandManager();
  static {
    LookAndFeel.setSystemLookAndFeel();
    new XMLSwingProcessor(cm,cm,cm);
  }
  public static void initGUIManagerXMLHandelers() {}
  public static void addCommandProcessor(Object o) {cm.addCommandProcessor(o);}
  public static void fireCommand(String name, Object... args) {cm.execute(name, args);}

  //----------------------------------------------------------------------------
  // Variables
  //----------------------------------------------------------------------------

  private JFileChooser file_chooser = new JFileChooser(ConfigManager.get(ConfigManager.default_path));
  private JFrame       frame;
  //private JMenu        menu_window  = new JMenu("Window");
  private JDesktopPane desktop      = new JDesktopPane();
  //private JPanel       toolbar_panel = new JPanel();
  
  //----------------------------------------------------------------------------
  // Constructor
  //----------------------------------------------------------------------------
  
  public GUIManager(String name) {
    initFrame(name);
  }

  //----------------------------------------------------------------------------
  // Add
  //----------------------------------------------------------------------------
  
  
  public void addFileFilter(String ext, String type_name) {
    file_chooser.addChoosableFileFilter(new MyFileFilter(ext,type_name));
  }

  //----------------------------------------------------------------------------
  // Init
  //----------------------------------------------------------------------------
  private void initFrame(String name) {
    frame = new JFrame(name);
    frame.setLayout(new BorderLayout());

    //frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    frame.addWindowListener(cm);
    Utils.ErrorHandeler.reportTo(frame);

    JMenuBar menu_bar = LoadManager.getItem(JMenuBar.class, name);
    //JToolBar tool_bar = LoadManager.getItem(JToolBar.class, name);
    if (menu_bar!=null) {
      menu_bar.add(JInternalFrameWithMenuItem.getWindowJMenu());
      frame.setJMenuBar(menu_bar);
    }
    
    frame.add(desktop, BorderLayout.CENTER);

    JPanel toolbar_panel = new JPanel();
    frame.add(toolbar_panel, BorderLayout.PAGE_START);
    ToolBarManager.attachGUItoToolBarMenu(toolbar_panel);
    
    frame.pack();
    frame.setExtendedState(frame.MAXIMIZED_BOTH);
    frame.setVisible(true);
  }
  //----------------------------------------------------------------------------
  // Toolbar
  //----------------------------------------------------------------------------
  public void addToolBar(JToolBar toolbar) {
    if (toolbar!=null) {frame.add(toolbar, BorderLayout.PAGE_START);}
  }
  public void removeToolBar(JToolBar toolbar) {
    if (toolbar!=null) {frame.remove(toolbar);}
  }
  
  //----------------------------------------------------------------------------
  // Dialog
  //----------------------------------------------------------------------------

  public <T> T showDialog(String title, Class<T> c) {
    JInputDialog<T> dialog = new JInputDialog<T>(frame,title,c);
    return dialog.getCreatedObject();
  }
  public <T> T showDialog(String title, T o) {
    JInputDialog<T> dialog = new JInputDialog<T>(frame,title,o);
    return dialog.getCreatedObject();
  }

  public void showDialog(JComponent component, String title) {
    JDialog dialog = new JDialog(frame,title,true);
    dialog.add(component);
    dialog.pack();
    dialog.setVisible(true);
  }
  
  public File dialogOpen() {
    int returnVal = file_chooser.showOpenDialog(frame);
    if (returnVal == JFileChooser.APPROVE_OPTION) {return file_chooser.getSelectedFile();}
    else                                          {return null;}
  }

  public File dialogSave() {
    int returnVal = file_chooser.showSaveDialog(frame);
    if (returnVal == JFileChooser.APPROVE_OPTION) {return file_chooser.getSelectedFile();}
    else                                          {return null;}
  }

  //----------------------------------------------------------------------------
  // Window Management
  //----------------------------------------------------------------------------
  public void addWindow(JInternalFrameWithMenuItem interal_frame) {
    desktop.add(interal_frame);
    interal_frame.setResizable(true);
    interal_frame.pack();
    interal_frame.setVisible(true);
  }
/*
  public void addWindow(JInternalFrame interal_frame) {
    //Create menu item for this internalframe in JMenu-Window
    JMenuItem windows_menu_item = new JMenuItem(interal_frame.getTitle());
    windows_menu_item.setActionCommand(interal_frame.getName());
    windows_menu_item.addActionListener(this);
    menu_window.add(windows_menu_item);
    
    desktop.add(interal_frame);
    
    interal_frame.setResizable(true);
    interal_frame.pack();
    interal_frame.setVisible(true);
  }
  public void removeWindow(JInternalFrame interal_frame) {
    desktop.remove(interal_frame);
    //Remove the JMenuItem associated with this from from the JMenu-Window
    for (Component c : menu_window.getMenuComponents()) {
      if (c.getName().equals(interal_frame.getName())) {menu_window.remove(c); break;}
    }
  }
*/  
  public void addToFrame(Component comp, Object constraints) {frame.add(comp, constraints);}
  public void removeFromFrame(Component comp)                {frame.remove(comp);}

  /*
  //Listener for JMenu-Window items to select a JInternalFrame when selected
  public void actionPerformed(ActionEvent e) {
    String command = e.getActionCommand();
    for (JInternalFrame internal_frame: desktop.getAllFrames()) {
      if (internal_frame.getTitle().equals(command)) {
        try {internal_frame.setSelected(true);} catch (PropertyVetoException ex) {ErrorHandeler.error(this.getClass(),"Connot Focus JInternalFrame",ex);}
        break;
      }
    }
   }
   */
  public static void chanageMenuGroupEnableState(String group, boolean state) {
    ButtonManager.chanageMenuGroupEnableState(group, state);
  }

}