package Utils.GUIHelpers;

public interface Undoable {
  public boolean canUndo();
  public boolean canRedo();
  public void undo();
  public void redo();
  public String getPresentationName();
  public String getUndoPresentationName();
  public String getRedoPresentationName();
  public void    addUndoListener(UndoListener undo_listsner);
  public void removeUndoListener(UndoListener undo_listsner);
}