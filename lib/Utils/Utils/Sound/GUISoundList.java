package Utils.Sound;

import Utils.GUIHelpers.LoadedItemList;
import Utils.GUIHelpers.LoadedSelectionListener;
import javax.swing.JPanel;

public class GUISoundList implements LoadedSelectionListener<Sound> {

  private JPanel panel_main = new JPanel();

  
  public GUISoundList() {
    LoadedItemList<Sound> list = new LoadedItemList<Sound>(Sound.class);
    //list.setCellRenderer(new RTSUnitTemplateCellRenderer());
    list.addLoadedSelectionListener(this);
    panel_main.add(list.getComponent());
    //panel_main.add(panel_unit);
  }

  public JPanel getComponent() {return panel_main;}

  public void itemSelected(Sound item) {
    item.play();
  }
  
}
