package Tile.Model.Map;


import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Collection;
import java.util.Vector;

import Utils.Types.Integer.Dimension3D;
import Utils.GUIHelpers.Undoable;
import Utils.GUIHelpers.UndoListener;

public class MapFoundationEditableUndoable extends MapFoundationEditable implements Undoable {

  //-------------------------------------------------------------------------
  // Variables
  //-------------------------------------------------------------------------
  private final UndoManager       undo_manager         = new UndoManager();  
  private       UndoableOperation current_operation;
  private       boolean           undo_manager_enabled = true;
  private       boolean           has_changed          = false;

  //-------------------------------------------------------------------------
  // Constructors
  //-------------------------------------------------------------------------
  
  public MapFoundationEditableUndoable(Dimension3D   d         ) {super(d);}
  public MapFoundationEditableUndoable(MapFoundation source_map) {super(source_map);}
  
  //-------------------------------------------------------------------------
  // Overides
  //-------------------------------------------------------------------------
  public MapFoundationEditableUndoable getClone() {
    MapFoundationEditableUndoable new_map = new MapFoundationEditableUndoable(this.getDimension3D());
    new_map.pasteSubMap(this,null,null);
    return new_map;
  }
  
  //-------------------------------------------------------------------------
  // Public
  //-------------------------------------------------------------------------
  
  public void    setUndoStatus(boolean enable) {       undo_manager_enabled = enable;}
  public boolean getUndoStatus(              ) {return undo_manager_enabled;         }
  
  public void newOperation(String name) {
    //System.out.println("New Op:"+name);
    if (undo_manager_enabled) {
      //try {throw new Exception("test");} catch (Exception e) {e.printStackTrace();}
      current_operation = new UndoableOperation(name);
      undo_manager.addOperation(current_operation);
    }
  }
  
  public boolean hasChanged()                          {return has_changed;}
  public void    setChangedStatus(boolean has_changed) {this.has_changed = has_changed;}
  
  //-------------------------------------------------------------------------
  // Undoable Implementation
  //-------------------------------------------------------------------------
  public void       addUndoListener(UndoListener undo_listsner) {undo_manager.addUndoListener(   undo_listsner);}
  public void    removeUndoListener(UndoListener undo_listsner) {undo_manager.removeUndoListener(undo_listsner);}
  public boolean canUndo()                 {return undo_manager.canUndo();}
  public boolean canRedo()                 {return undo_manager.canRedo();}
  public void    undo()                    {undo_manager.undo();}
  public void    redo()                    {undo_manager.redo();}
  public String  getPresentationName()     {return undo_manager.getPresentationName();}
  public String  getUndoPresentationName() {return undo_manager.getUndoPresentationName();}
  public String  getRedoPresentationName() {return undo_manager.getRedoPresentationName();}
  

  //-------------------------------------------------------------------------
  // Private Methods
  //-------------------------------------------------------------------------
  private void addUndoState(int x, int y, int z) {
    setChangedStatus(true);
    if (undo_manager_enabled) {
      //if (inBounds(x,y,z)) {
        MapFoundationTile tile = getTile(x,y,z);
        if (tile==null) {tile = new MapFoundationTile(); super.setTile(tile,x,y,z);} //this is just a rehash of the code already in getTileSafe() but scince that calls setTile(..) the orriding method in this class is called and an infinate loop apears
        getCurrentOpeation().addEdit(new UndoableEditTile(tile));
      //}
    }
  }
  private UndoableOperation getCurrentOpeation() {
    if (current_operation==null) {newOperation("unknown");}
    return current_operation;
  }

  //-------------------------------------------------------------------------
  // SetTile Interceptors
  //-------------------------------------------------------------------------
  public MapFoundationTile extractTile(int x, int y, int z, TileOperationOptions options)                          {addUndoState(x,y,z); return super.extractTile(x,y,z,options);}
  public void mergeTile(MapFoundationTile tile, TileOperationOptions options, int x, int y, int z )                {addUndoState(x,y,z); super.mergeTile(tile,options,x,y,z);}
  public void setTile(MapFoundationTile tile, int x, int y, int z)                                                 {addUndoState(x,y,z); super.setTile(tile,x,y,z);}
  public void setLayer(Object o, int x, int y, int z, LayerIdentifyer layer_id)                                    {addUndoState(x,y,z); super.setLayer(o,x,y,z,layer_id);}
  public void setLayer(Object o, int x, int y, int z                          )                                    {addUndoState(x,y,z); super.setLayer(o,x,y,z);}
  public boolean mergeLayer(Object o, TileOperationOptions options, int x, int y, int z                          ) {addUndoState(x,y,z); return super.mergeLayer(o,options,x,y,z);}
  public boolean mergeLayer(Object o, TileOperationOptions options, int x, int y, int z, LayerIdentifyer layer_id) {addUndoState(x,y,z); return super.mergeLayer(o,options,x,y,z,layer_id);}
  
}

//------------------------------------------------------------------------------
// Class UndoManager
//------------------------------------------------------------------------------
class UndoManager implements Undoable {
  private final int undo_limit = 4;

  private final Deque<UndoableOperation> operations_undo = new LinkedList<UndoableOperation>();
  private final Deque<UndoableOperation> operations_redo = new LinkedList<UndoableOperation>();
  private final Collection<UndoListener> undo_listeners  = new Vector<UndoListener>();
  
  public void addOperation(UndoableOperation op) {
    if (operations_undo.size() > undo_limit) {operations_undo.removeFirst();}
    operations_undo.addLast(op);
    operations_redo.clear();
    fireUndoableStateChange();
  }
  
  public void    addUndoListener(UndoListener undo_listsner) {undo_listeners.add(   undo_listsner);}
  public void removeUndoListener(UndoListener undo_listsner) {undo_listeners.remove(undo_listsner);}
  
  public boolean canUndo() {if (operations_undo.size()>0) {return true;} return false;}
  public boolean canRedo() {if (operations_redo.size()>0) {return true;} return false;}
  
  public void undo() {undoOperation(operations_undo,operations_redo);}
  public void redo() {undoOperation(operations_redo,operations_undo);}

  private void undoOperation(Deque<UndoableOperation> a, Deque<UndoableOperation> b) {
    UndoableOperation op = a.pollLast();
    op.undo();
    b.addLast(op);
    fireUndoableStateChange();
  }
  
  public String getPresentationName() {
    throw new IllegalStateException();
  }

  public String getUndoPresentationName() {
    try                 {return operations_undo.getLast().getName();}
    catch (Exception e) {return null;}
  }

  public String getRedoPresentationName() {
    try                 {return operations_redo.getLast().getName();}
    catch (Exception e) {return null;}
  }

  private void fireUndoableStateChange() {
    for (UndoListener listener : undo_listeners) {
      listener.undoableStateChange(this);
    }
  }
}

//------------------------------------------------------------------------------
// Class Undoable Operation
//------------------------------------------------------------------------------
class UndoableOperation implements UndoableEdit {
  private final String              name;
  private final Deque<UndoableEdit> edits         = new LinkedList<UndoableEdit>();
  private       boolean             reverse_order = false;
  public UndoableOperation(String name) {this.name = name;}
  public String getName() {return name;}
  public void addEdit(UndoableEdit edit) {
    if (reverse_order) {edits.addLast(edit);}
    else               {edits.addFirst(edit);}
  }
  public void undo() {
    Iterator<UndoableEdit> i;
    if (reverse_order) {i = edits.descendingIterator();}
    else               {i = edits.iterator();}
    for ( ; i.hasNext() ; ) {i.next().undo();}
    reverse_order = !reverse_order;
  }
}

//------------------------------------------------------------------------------
// Interface Undoable Edit
//------------------------------------------------------------------------------
interface UndoableEdit {
  public void undo();
}

//------------------------------------------------------------------------------
// MapFoundationTile Undable Edit
//------------------------------------------------------------------------------
class UndoableEditTile implements UndoableEdit {
  private final MapFoundationTile tile_ref;
  private       MapFoundationTile tile_orginal_state;
  //private final MapFoundationTile tile_original;
  //private final MapFoundationTile tile_original;
  public UndoableEditTile(MapFoundationTile tile) {
    tile_ref = tile;
    if (tile_ref!=null) {tile_orginal_state = tile_ref.getCopy();}
  }
  public void undo() {
    MapFoundationTile tile_original_state_2 = tile_ref.getCopy();
    tile_ref.setTile(tile_orginal_state);
    tile_orginal_state = tile_original_state_2;
  }
}