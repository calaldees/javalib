package Utils.GUIHelpers;

import java.util.Collection;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JComponent;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import Utils.ErrorHandeler;
import Utils.XML.XMLLoad.LoadManager;
import Utils.XML.XMLLoad.LoadEventListener;
import Utils.XML.XMLLoad.LoadEventProgress;

//
// A wrapper class that maps the items loaded from XML data to a JList that auto updates from load events
// Selected list items are reported to ListSelectionListener<T>
//

public class LoadedItemList<T> implements ListSelectionListener, LoadEventListener {

//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------
  private JList       list       = new JList();
  private JComponent  component;
  private Class<T>    list_class;
  
//-------------------------------------------------------------------------
// Constructors
//-------------------------------------------------------------------------
  public LoadedItemList(Class<T> list_class) {
    if (list_class==null) {throw new IllegalArgumentException("passed null class");}
    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    list.addListSelectionListener(this);
    component = new JScrollPane(list);
    this.list_class = list_class;
    LoadManager.addLoadEventListener(this);
    refreshListData();
  }

  public LoadedItemList(Class<T> list_class, ListCellRenderer renderer) {
    this(list_class);
    setCellRenderer(renderer);
  }
//-------------------------------------------------------------------------
// Public Methods
//-------------------------------------------------------------------------
  public void setCellRenderer(ListCellRenderer renderer) {list.setCellRenderer(renderer);}
  
  public JComponent getComponent() {return component;}
  
  public void refreshListData() {
    try                 {list.setListData(LoadManager.getGroupList(list_class).toArray());}
    catch (Exception e) {ErrorHandeler.error(this.getClass().getName(), "List unable to refresh contents",e);}
  }
  
//-------------------------------------------------------------------------
// Listeners - When an object is selected notify the listeners
//-------------------------------------------------------------------------
  private Collection<LoadedSelectionListener<T>> listeners = new Vector<LoadedSelectionListener<T>>();
  public boolean    addLoadedSelectionListener(LoadedSelectionListener<T> listener) {return listeners.add(listener);}
  public boolean removeLoadedSelectionListener(LoadedSelectionListener<T> listener) {return listeners.remove(listener);}
  
//-------------------------------------------------------------------------
// Interface - ListSelectionListener (when an item from the JList is selected)
//-------------------------------------------------------------------------
  @SuppressWarnings("unchecked")
  public void valueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      T selected_item = null;
      try                         {selected_item = (T)(((JList)e.getSource()).getSelectedValue());}
      catch (Exception exception) {ErrorHandeler.error(this.getClass().getName(), "Unable to select item", exception);}
      for (LoadedSelectionListener<T> listener: listeners) {
        listener.itemSelected(selected_item);
      }
    }
  }
  
//-------------------------------------------------------------------------
// Interface - LoadEventListener (when a file has finished loading and the list needs updating)
//-------------------------------------------------------------------------

  public void eventLoadProgress(LoadEventProgress e) {}
  public void eventLoadStarted()                     {}
  public void eventLoadComplete()                    {refreshListData();}
}
