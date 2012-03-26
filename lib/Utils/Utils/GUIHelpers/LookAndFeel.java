package Utils.GUIHelpers;

import java.awt.Frame;
import java.awt.Window;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.AbstractButton;
import javax.swing.SwingUtilities;
import javax.swing.JRootPane;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;



public class LookAndFeel implements ItemListener {

  //-------------------------------------------------------------
  // Static Control of Look and Feel
  //-------------------------------------------------------------
  
  public static String[] getLookAndFeelNames() {
    UIManager.LookAndFeelInfo[] look_and_feels      = UIManager.getInstalledLookAndFeels();
    String[]                    look_and_feel_names = new String[look_and_feels.length];
    for (int counter=0 ; counter<look_and_feels.length ; counter++) {
      look_and_feel_names[counter] = look_and_feels[counter].getName();
    }
    return look_and_feel_names;
  }
  
  public static void setLookAndFeel(String look_and_feel_name) {
    for (UIManager.LookAndFeelInfo look_and_feel : UIManager.getInstalledLookAndFeels()) {
      if (look_and_feel.getName().equals(look_and_feel_name)) {
        try {
          UIManager.setLookAndFeel(look_and_feel.getClassName());
          for (Frame frame : Frame.getFrames()) {
            SwingUtilities.updateComponentTreeUI(frame);
            for (Window win : frame.getOwnedWindows()) {
              SwingUtilities.updateComponentTreeUI(win);
              //win.validate();
            }
            //frame.validate();
          }
        }
        catch (Exception e) {Utils.ErrorHandeler.error("Unable to change look and feel","error changing look and feel",e);}
      }
    }
  }
  
  public static void setSystemLookAndFeel() {
    try {javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());} catch (Exception e) {}
  }

  private static void updateFrames() {

  }
  
  //-------------------------------------------------------------
  // JMenu with avalable Look and Feels + Listener
  //-------------------------------------------------------------
  
  public static JMenu getLookAndFeelJMenu() {return new LookAndFeel().getMenu();}
  
  public JMenu getMenu() {
    JMenu menu = new JMenu("Look and Feel", true);
    for (String name : getLookAndFeelNames()) {
      menu.add(ButtonManager.createJCheckBoxMenuItem(name,this,menu.getText()));
    }
    return menu;
  }
  
  //-------------------------------------------------------------
  // Listener to change Look and Feel
  //-------------------------------------------------------------
  
  public void itemStateChanged(ItemEvent e) {
    AbstractButton source = (AbstractButton)(e.getSource());
    if (source.isSelected()) {
      setLookAndFeel(source.getName());
      //JRootPane root = source.getRootPane();
      //SwingUtilities.updateComponentTreeUI(root);
      //root.doLayout();
    }
  }

}