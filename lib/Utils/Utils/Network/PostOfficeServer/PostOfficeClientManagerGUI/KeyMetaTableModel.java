package Utils.Network.PostOfficeServer.PostOfficeClientManagerGUI;

import Utils.Network.PostOfficeServer.KeyMetaData;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class KeyMetaTableModel implements TableModel {

  private List<KeyMetaData> keys;

  public KeyMetaTableModel() {}

  public void setKeyMetaData(Collection<KeyMetaData> keys) {
    if (keys instanceof List) {
      this.keys = (List<KeyMetaData>)keys;
    }
  }

  public int getRowCount()    {
    if (keys==null) {return 0;}
    return keys.size();
  }
  public int getColumnCount() {return 7;}

  public String getColumnName(int columnIndex) {
    String col_name = "";
    switch (columnIndex) {
      case 0: col_name = "Key";         break;
      case 1: col_name = "Created";     break;
      case 2: col_name = "Created By";  break;
      case 3: col_name = "Accessed";    break;
      case 4: col_name = "Accessed By"; break;
      case 5: col_name = "Modifyed";    break;
      case 6: col_name = "Modifyed By"; break;
      default: col_name = "";           break;
    }
    return col_name;
  }

  public Class<?> getColumnClass(int columnIndex) {
    Class<?> c;
    switch (columnIndex) {
      case 0: c = String.class;  break;
      case 1: c = Date.class;    break;
      case 2: c = String.class;  break;
      case 3: c = Date.class;    break;
      case 4: c = String.class;  break;
      case 5: c = Date.class;    break;
      case 6: c = String.class;  break;
      default: c = null;         break;
    }
    return String.class;
  }

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  public Object getValueAt(int rowIndex, int columnIndex) {
    Object o = null;
    try {
      KeyMetaData row = keys.get(rowIndex);
      switch (columnIndex) {
        case 0: o = row.getKey();                                        break;
        case 1: o = new Date(row.getHistoryCreated().getTimestamp()).toString();    break;
        case 2: o = row.getHistoryCreated().getWho();                    break;
        case 3: o = new Date(row.getHistoryAccessed().getTimestamp()).toString();   break;
        case 4: o = row.getHistoryAccessed().getWho();                   break;
        case 5: o = new Date(row.getHistoryModifyed().getTimestamp()).toString();   break;
        case 6: o = row.getHistoryModifyed().getWho();                   break;
        default: o = null;                                               break;
      }
    }
    catch (Exception e) {}
    return o;
  }

  public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    
  }

  public void addTableModelListener(TableModelListener l) {
    
  }

  public void removeTableModelListener(TableModelListener l) {
    
  }

}
