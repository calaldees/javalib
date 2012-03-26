package Utils.GUIHelpers.JInputDialog;


import Utils.ObjectCloner;
import Utils.ReflectionUtils;
import java.awt.Frame;
import javax.swing.*;
import java.lang.reflect.Field;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import Utils.ErrorHandeler;
import Utils.XML.XMLLoad.LoadManager;
import Utils.GUIHelpers.SpringUtilities;

public class JInputDialog<T> extends JDialog implements ActionListener {
  
  private static final long serialVersionUID = 0;
  
  T obj_original = null;
  T obj_clone    = null;
  
  public JInputDialog(Frame frame, String title, Class<T> c) {
    super(frame,title,true);
    if (c==null) {throw new IllegalArgumentException();}
    obj_clone = ReflectionUtils.createObject(c);
    initDialog();
  }
  public JInputDialog(Frame frame, String title, T obj) {
    super(frame,title,true);
    if (obj==null) {throw new IllegalArgumentException();}
    this.obj_original = obj;
    this.obj_clone    = ObjectCloner.cloneObject(obj);
    initDialog();
  }
  private void initDialog() {
    if (obj_clone==null) {
      ErrorHandeler.error(this.getClass(),"Unable to create Dialog","Unable to create/clone object");
      dispose();
    }
    else {
      setContentPane(getJInputDialogContents(obj_clone));
      pack();
      setVisible(true);
    }
  }
  
  public T getCreatedObject() {return obj_original;}
  
  private JComponent getJInputDialogContents(Object o) {
    if (o==null) {return null;}
    JPanel dialog_content_pane = new JPanel();
    dialog_content_pane.setLayout(new BoxLayout(dialog_content_pane, BoxLayout.PAGE_AXIS));

    dialog_content_pane.add(getInputPanel(o));
    dialog_content_pane.add(createButtons());

    return dialog_content_pane;
  }
  
  private JComponent getInputPanel(Object o) {
    if (o==null) {return null;}
    JPanel field_panel = new JPanel(new SpringLayout());
    for (Field f : ReflectionUtils.getFields(o)) {
      JLabel      field_label     = new JLabel(f.getName(),JLabel.TRAILING);
      JComponent  field_component = null;
      if (f.getType().isPrimitive()) {field_component = new JBoundTextField(o,f);}
      else                           {field_component = getJInputDialogContents(ReflectionUtils.getFieldObject(o,f));}
      field_label.setLabelFor(field_component);
      field_panel.add(field_label);
      field_panel.add(field_component);
    }
    
    int GAP = 10;
    SpringUtilities.makeCompactGrid(field_panel, ReflectionUtils.getFields(o).length, 2, 
                                        GAP, GAP, //init x,y
                                        GAP, GAP/2);//xpad, ypad
    return field_panel;
  }
  
  private JComponent createButtons() {
    JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));

    JButton button_ok = new JButton("Ok");
    button_ok.addActionListener(this);
    panel.add(button_ok);
    
    JButton button_cancel = new JButton("Cancel");
    button_cancel.addActionListener(this);
    panel.add(button_cancel);
    
    //Match the SpringLayout's gap, subtracting 5 to make up for the default gap FlowLayout provides.
    //panel.setBorder(BorderFactory.createEmptyBorder(0, 0, GAP-5, GAP-5));
    return panel;
  }


  public void actionPerformed(ActionEvent e) {
    String action = e.getActionCommand();
    if (action.equals("Ok"))     {obj_original = obj_clone; dispose();}
    if (action.equals("Cancel")) {obj_original = null;      dispose();}
  }
  
  
}
