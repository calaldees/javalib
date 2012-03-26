package Utils.GUIHelpers.JInputDialog;

import java.awt.Color;
import javax.swing.JTextField;
import java.lang.reflect.Field;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;


import Utils.ReflectionUtils;

public class JBoundTextField extends JTextField implements DocumentListener {
  
  private static final long serialVersionUID = 0;
  
  private final Object o;
  private final Field  f;
  private final Color  color_default = getBackground();
  private final Color  color_invalid = Color.PINK;
  
  private static String getStringValue(Object o, Field f) {return ReflectionUtils.getFieldStringValue(o,f);}
  
  public JBoundTextField(Object o, Field f) {
    super(getStringValue(o,f));
    this.o = o;
    this.f = f;
    getDocument().addDocumentListener(this);
  }
  
  public void insertUpdate(DocumentEvent e)  {updateField();}
  public void removeUpdate(DocumentEvent e)  {updateField();}
  public void changedUpdate(DocumentEvent e) {}

  private void updateField() {
    ReflectionUtils.setField(f,o,getText());
    if (getStringValue().equals(getText())) {setBackground(color_default);}
    else                                    {setBackground(color_invalid);}
  }

  private String getStringValue() {return getStringValue(o,f);}
  
}
