package Utils.XML.XMLSave;

import Utils.ReflectionUtils;
import Utils.XML.XMLLoad.LoadManager;
import java.lang.reflect.Field;



public class SaveProcessor<SaveableClass> {
  
  private String               tag;
  private Class<SaveableClass> class_type;
  
  public SaveProcessor(Class<SaveableClass> c, String tag) {
    if (c==null || tag==null) {throw new IllegalArgumentException();}
    this.class_type = c;
    this.tag        = tag;
    SaveProcessorManager.addProcessor(this);
  }
  
  public String               getTag()       {return tag;}
  public Class<SaveableClass> getClassType() {return class_type;}
  
  public void save(SaveHandeler s, SaveableClass o) {
    SaveWrapper save_wrapper = s.getSaveWrapper(getTag());
    save(save_wrapper,o);
    s.writeSaveWrapper(save_wrapper);
  }
  
  public void save(SaveWrapper save, SaveableClass o) {
    for (Field field : ReflectionUtils.getFields(o)) {
      boolean original_accessible_state = field.isAccessible();
      field.setAccessible(true);
      String field_name = field.getName();
      Class  field_type = field.getType();
      if (ReflectionUtils.isStringAbleType(field)) {
        save.addField(field_name, ReflectionUtils.getFieldStringValue(o,field));
      }
      else if (LoadManager.isRegisteredClass(field.getType())) {
        try                 {save.addField(field_name, ReflectionUtils.getName(field.get(o)));}
        catch (Exception e) {}
      }
      else if (SaveProcessorManager.isSaveableClass(field_type) || field_type.isAssignableFrom(XMLSavable.class)) {
        try                 {save.addObject(field.get(o));}
        catch (Exception e) {}
      }
      field.setAccessible(original_accessible_state);
    }
  }
  
}