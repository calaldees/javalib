package MapEdit.GUIComponents;

import Utils.ModelViewControllerFramework.View;
import java.io.File;
import java.util.Collection;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.event.InternalFrameEvent;

import Utils.File.FileOperations;
import Utils.GUIHelpers.JInternalFrameWithMenuItem;
import Utils.GUIHelpers.GUIManager;
import Utils.GUIHelpers.Undoable;
import Utils.GUIHelpers.UndoListener;

import Tile.Model.Map.MapFoundation;
import Tile.Model.Map.MapFoundationEditableUndoable;



import Utils.ModelViewControllerFramework.ViewManager;
import Utils.ModelViewControllerFramework.ViewSelectionListener;
import static MapEdit.GUIComponents.MapEditConstants.*;

public class MapFrame extends JInternalFrameWithMenuItem implements UndoListener {
  
  private static final long serialVersionUID = 0;

  //----------------------------------------------------------------------------
  // Static MapFrame Management
  //----------------------------------------------------------------------------

  {
    ViewManager.addViewSelectionListener(view_listener);
    //setSelectedMapComponent(null);
  }
  
  //private static MapComponentEditable selected_map_component;
  private static       MapFrame              selected_map_frame;
  private static final ViewSelectionListener view_listener = new MapFrameViewListener();

  public  static MapFrame                      getSelectedMapFrame()     {return selected_map_frame;}
  public  static MapComponentEditable          getSelectedMapComponent() {return getSelectedMapFrame().getMapComponent();}
  public  static MapFoundationEditableUndoable getSelectedMap() {
    if (getSelectedMapComponent()==null) {return null;}
    return getSelectedMapComponent().getMap();
  }
  private static void setSelectedMapFrame(MapFrame map_frame) {
    if (map_frame==getSelectedMapFrame()) {return;}
    selected_map_frame = map_frame;
    if (selected_map_frame==null) {
      GUIManager.chanageMenuGroupEnableState(GROUP_NAME_MAP_SELECTED,false);
      GUIManager.chanageMenuGroupEnableState(GROUP_NAME_UNDO        ,false);
      GUIManager.chanageMenuGroupEnableState(GROUP_NAME_REDO        ,false);
    }
    else {
      MapFoundationEditableUndoable map = getSelectedMap();
      GUIManager.chanageMenuGroupEnableState(GROUP_NAME_MAP_SELECTED, true);
      //GUIManager.chanageMenuGroupEnableState(GROUP_NAME_MAP_SAVEABLE, map.hasChanged()); //would be cool but we dont get map_updated events to chnage this
      //undoableStateChange(map);
      GUIManager.chanageMenuGroupEnableState(GROUP_NAME_UNDO        , map.canUndo());
      GUIManager.chanageMenuGroupEnableState(GROUP_NAME_REDO        , map.canRedo());
    }
  }
  
  public static Collection<MapFrame> getAllMapFrames() {
    //I HATE myself for this implemmentation it is totaly inefficent, it's just a hack, there must be abetter way. why cant I just cast the ***ing collection
    Collection<MapFrame> frames = new Vector<MapFrame>();
    for (JInternalFrameWithMenuItem f : getFrames()) {frames.add((MapFrame)f);}
    return frames;
  }


  //----------------------------------------------------------------------------
  // MapFrame (instance)
  //----------------------------------------------------------------------------

  private final MapComponentEditable map_component;
  private       File                 file;
  
  public MapFrame(MapFoundation                 map) {this(new MapFoundationEditableUndoable(map));}
  public MapFrame(MapFoundationEditableUndoable map) {
    super(map.getName());
    map.addUndoListener(this);
    map_component = new MapComponentEditable(map);
    this.add(new JScrollPane(map_component));
  }
  
  private MapComponentEditable getMapComponent() {return map_component;}
  
  public boolean hasChanged() {return map_component.getMap().hasChanged();}
  public File    getFile()          {return file;}
  public void    setFile(File file) {
    this.file = file;
    String map_name = FileOperations.getFilenameWithoutExtension(file); 
    setTitle(map_name);
    getMapComponent().getMap().setName(map_name);
  }
  
  public void close() {
    if (!hasChanged()) {dispose();}
    else {
      int choice = JOptionPane.showConfirmDialog(this, getMapComponent().getMap().getName()+" has changed, do you wish to save now?", "Map has changed", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
      if      (choice == JOptionPane.NO_OPTION    ) {dispose();}
      else if (choice == JOptionPane.YES_OPTION   ) {if (save()) {dispose();}}
      else if (choice == JOptionPane.CANCEL_OPTION) {}
    }
  }

  private boolean save() {
    setSelectedMapFrame(this);
    GUIManager.fireCommand("save");
    return !map_component.getMap().hasChanged();
  }
  
  public void undoableStateChange(Undoable undoable_object) {
    GUIManager.chanageMenuGroupEnableState(GROUP_NAME_UNDO, undoable_object.canUndo());
    GUIManager.chanageMenuGroupEnableState(GROUP_NAME_REDO, undoable_object.canRedo());
  }
  
  public void internalFrameClosing(InternalFrameEvent e)     {close();}
  public void internalFrameActivated(InternalFrameEvent e)   {super.internalFrameActivated(e);   setSelectedMapFrame(this);}
  public void internalFrameDeactivated(InternalFrameEvent e) {super.internalFrameDeactivated(e); setSelectedMapFrame(null);}
}


class MapFrameViewListener implements ViewSelectionListener {
  public void selectView(View v) {
    MapFrame.getSelectedMapComponent().setView(v);
    MapFrame.getSelectedMapFrame().repaint();
  }
}