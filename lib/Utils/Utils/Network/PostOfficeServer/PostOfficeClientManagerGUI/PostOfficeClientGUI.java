package Utils.Network.PostOfficeServer.PostOfficeClientManagerGUI;

import Utils.Network.PostOfficeServer.KeyMetaData;
import Utils.Network.PostOfficeServer.PostOfficeClientConnectionCallback;
import Utils.Network.PostOfficeServer.PostOfficeClientRecieveListener;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PostOfficeClientGUI implements ActionListener, ListSelectionListener, PostOfficeClientRecieveListener {

  private static final String name_key_search  = "name_key_search";
  private static final String name_button_send = "name_button_send";

  private       PostOfficeClientConnectionCallback connection;
  private final JTextField                         key_search;
  private final KeyMetaTableModel                  key_meta_model             = new KeyMetaTableModel();
  private final JTable                             key_data_table             = new JTable();
  private final JScrollPane                        key_data_table_scroll_pane = new JScrollPane(key_data_table);
  private final JTextArea                          data_component             = new JTextArea();

  private String key_selected;

  public static void main(String[] args) {
    new PostOfficeClientGUI(args[0], Integer.decode(args[1]));
  }

  public PostOfficeClientGUI(String host, int port) {
    JFrame frame = new JFrame(PostOfficeClientGUI.class.getName());
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Keys Panel
    JPanel keys_panel = new JPanel();
    keys_panel.setLayout(new BorderLayout());
    
    key_search = new JTextField();
    key_search.setName(name_key_search);
    //key_search.setColumns(10);
    key_search.addActionListener(this);
    keys_panel.add(key_search, BorderLayout.NORTH);

    //key_data_table.setPreferredSize(new Dimension(600,200));
    key_data_table.setAutoCreateRowSorter(true);
    key_data_table.setFillsViewportHeight(true);
    key_data_table.getSelectionModel().addListSelectionListener(this);
    key_data_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    keys_panel.add(key_data_table_scroll_pane, BorderLayout.CENTER);


    // Data Panel
    JPanel data_panel = new JPanel();
    data_panel.setLayout(new BorderLayout());

    JButton send_data = new JButton("Set Data");
    send_data.setName(name_button_send);
    send_data.addActionListener(this);
    data_panel.add(send_data, BorderLayout.NORTH);

    data_component.setLineWrap(true);
    JScrollPane data_scroll = new JScrollPane(data_component, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    data_panel.add(data_scroll, BorderLayout.CENTER);


    // Split Pane
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,keys_panel, data_panel);
    splitPane.setOneTouchExpandable(true);
    splitPane.setDividerLocation(400);

    // Frame
    frame.add(splitPane, BorderLayout.CENTER);

    frame.pack();
    frame.setVisible(true);

    try                 {
      connection = new PostOfficeClientConnectionCallback(host, port);
      connection.addPostOfficeClientRecieveListener(this);
    }
    catch (Exception e) {
      System.out.println("Connection Failed");
    }
  }

  //------------------------------------------------------------------------------
  // Listeners
  //------------------------------------------------------------------------------

  public void actionPerformed(ActionEvent e) {
    String name = ((JComponent)e.getSource()).getName();
    if (name.equals(name_key_search)) {
      connection.getKeys(key_search.getText());
      key_search.setText("");
    }
    if (name.equals(name_button_send)) {
      connection.putData(getKeySelected(), data_component.getText());
    }
  }

  public void valueChanged(ListSelectionEvent e) {
    if (!e.getValueIsAdjusting()) {
      ListSelectionModel list_selection = (ListSelectionModel)e.getSource();
      int model_index = key_data_table.convertRowIndexToModel(list_selection.getLeadSelectionIndex());
      setKeySelected((String)key_meta_model.getValueAt(model_index, 0));
    }
  }

  //------------------------------------------------------------------------------
  // Network Events
  //------------------------------------------------------------------------------

  public void recieveData(String key, String data) {
    data_component.setText(data);
    //System.out.println("Recieved:"+data);
  }

  public void recieveKeyList(Collection<String> key_list) {
    System.out.println("keylist");
  }
  
  public void recieveKeyMetaData(KeyMetaData keydata) {}

  public void recieveKeys(Collection<KeyMetaData> keys) {
    key_meta_model.setKeyMetaData(keys);
    key_data_table.setModel(key_meta_model);

    key_data_table_scroll_pane.updateUI();
  }
  public void recieveStateList(Collection<String> states) {}

  //------------------------------------------------------------------------------
  // Private
  //------------------------------------------------------------------------------

  private void setKeySelected(String key) {
    connection.unsubscribe(key_selected);
    connection.subscribe(key);
    connection.getData(key);
    key_selected = key;
    //connection.getData(key_selected);
  }
  private String getKeySelected() {
    return key_selected;
  }

}