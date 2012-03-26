package Utils.GUIHelpers;

import java.util.List;
import java.util.LinkedList;
import java.util.Vector;



public class Clipboard {


  
  private int capacity = 1;
  
  private LinkedList<Object>      clipboard = new LinkedList<Object>();
  private List<ClipboardListener> listeners = new Vector<ClipboardListener>();

  public void addClipboardListener(   ClipboardListener clipboad_listener) {listeners.add(   clipboad_listener);}
  public void removeClipboardListener(ClipboardListener clipboad_listener) {listeners.remove(clipboad_listener);}

  public boolean clipboardUsed() {
    if (clipboard.size()>=1) {return true;}
    return false;
  }

  public Object getCurrentClipboard() {return clipboard.getFirst();}

  public void addClipboard(Object o) {
    if (clipboard.size()>=capacity) {clipboard.removeLast();}
    clipboard.addFirst(o);
    fireClipboardEvent();
  }

  public void emptyClipboard() {clipboard.clear();}

  public void fireClipboardEvent() {
    for (ClipboardListener listener: listeners) {
      listener.clipboardStateChange();
    }
  }

}

