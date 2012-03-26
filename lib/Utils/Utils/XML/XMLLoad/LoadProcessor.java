package Utils.XML.XMLLoad;

import java.io.File;
import java.util.Map;
import java.util.LinkedHashMap;
import org.xml.sax.Locator;

import Utils.ObjectCloner;

@SuppressWarnings("unchecked")
public class LoadProcessor<CatalogClass> {

//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------

  private String  tag;
  private Class   class_type;
  private boolean catalog_flag;

  private Map<String,CatalogClass> master_map = new LinkedHashMap<String,CatalogClass>();  // A map of all the items loaded by this processor
  private File                     source_path;



//-------------------------------------------------------------------------
// Constructor
//-------------------------------------------------------------------------

  public LoadProcessor(Class<CatalogClass> class_template                             ) {this(class_template,null,true   );}
  public LoadProcessor(Class<CatalogClass> class_template, String tag                 ) {this(class_template,tag ,true   );}
  public LoadProcessor(Class<CatalogClass> class_template            , boolean catalog) {this(class_template,null,catalog);}
  public LoadProcessor(Class<CatalogClass> class_template, String tag, boolean catalog) {
    this.class_type = class_template;
    this.tag        = tag;
    if (this.tag==null) {this.tag = autoGenearteTagFromClass(class_type);}
    setCatalogStatus(catalog);
    LoadProcessorManager.addProcessor(class_type, this.tag, this);
  }




//-------------------------------------------------------------------------
// Overide Methods - (Replace these for custom processing behaviour)
//-------------------------------------------------------------------------

  // Default implementation of these methods removes the burden of overlaying and creating objects
  // and performs a default Data2LocalFeilds (Automatic creation). Useful for objects that are only data.
  // Simply create a new DataProcessor with a Class and Tag and your objects will be made for you

  public CatalogClass create(DataWrapper data) throws Exception {
    return (CatalogClass)ObjectCreator.createObject(class_type, data);
  }

  public CatalogClass overlay(CatalogClass o, DataWrapper data) throws Exception {
    setFields(o, data);
    callSetMethods(o, data);
    return o;
  }

  protected void      setFields(CatalogClass o, DataWrapper data) throws Exception {ObjectCreator.setFields(o, data);}
  protected void callSetMethods(CatalogClass o, DataWrapper data)                  {ObjectCreator.callSetMethods(o, data);}


//-------------------------------------------------------------------------
// Protected Methods
//-------------------------------------------------------------------------

  protected void   setCatalogStatus(boolean b) {catalog_flag    = b;}

  protected File                      getLocation()  {return source_path;}
  protected Map<String,CatalogClass>  getProcessed() {return master_map;}

  //protected static CatalogClass getItem(Class class_type, String name) {return DataProcessorManager.getItem(  class_type,name);}
  //protected static Object getItem(String          tag, String name) {return DataProcessorManager.getItem(         tag,name);}
  protected CatalogClass getItem(String name) {
    try                 {return master_map.get(name);}
    catch (Exception e) {return null;}
  }

  
//-------------------------------------------------------------------------
// Private methods
//-------------------------------------------------------------------------

  private   CatalogClass              resolveLink(DataWrapper data) {
    return getItem(data.getLinkName());  //Use the current group to resolve link
  }


  // Convenince method for getting images - saved the extending class haveing to import ImageLoader
  //protected static java.awt.Image getImage(String name) {return Utils.ImageLoader.ImageLoader.getImage(name);}

//-------------------------------------------------------------------------
// Public Methods
//-------------------------------------------------------------------------

  public boolean catalogObjectsOfThisType() {return catalog_flag;}  // If this returns false then the objects are not stored, they are mearly processed
  public Class   getCatalogClassType()      {return class_type;}
  public String  getTagName()               {return tag;}


  // Called by the XML processor to process the map and return a properly constructed object. 
  // The processing relys on the implementation of the abstract methods:
  //  - overlay: to clone an existing object (which is recoved from the catalog) and modify it's values
  //  - create : to create a new object with the XML data provided of the correct class.
  // The returned object is cataloged by DataProcessorManager
  // The Locator is for objects that may need additional files (eg images) that are in the same directory as the XML datafile

  public Object process(DataWrapper data, Locator locator) throws Exception {
    //loator - this is here to alow the processor to know where the XML file is. this will help with loading images and other support files
    source_path      = locatorToDirectory(locator);      // Setting the path in this manner is NOT THREAD SAFE!
    if (data.isSingleLink()) {return resolveLink(data);} // If it just contains a single link element then automaticly resolve the link

    CatalogClass o;
    CatalogClass link_item     = getItem(data.getLinkName());  // If there is a link to another item then get the object it links to
    CatalogClass existing_item = getItem(data.getName()    );  // If there is an item with this name loaded already

    if      (link_item    !=null) {o = overlay( ObjectCloner.cloneObject(link_item), data);}  // If link exists then clone the object it links to and overlay the new data to make a new object based on the old one with new data overlayed
    else if (existing_item!=null) {o = overlay( existing_item                                       , data);}  // If existing item with same name - overlay new data onto the existing object
    else                          {o = create(                                                        data);}  // If we are not Overlaying then we need to create a new object from the Class template we have

    if (o instanceof Validatable) {
      if (!((Validatable)o).isValid()) {throw new ValidateException("Validate Failed "+data.getName()+" "+o.getClass().getName());}
    }
    
    String name;
    if (o instanceof Indexable) {name = ((Indexable)o).getName();}
    else                        {name = data.getName();}
    //BUG (unfortunately name is NEVER null ... data.getName() will always return a name, because it will always return the class name. This needs investigationg
    if (name==null) {
      try {name = (String)o.getClass().getMethod("getName").invoke(o);}
      catch (Exception e) {}
    }
    //System.out.println(""+name);
    
    if (name!=null && o!=null && catalogObjectsOfThisType()) {
      master_map.put(name, o);
    }

    return o;
  }

//-------------------------------------------------------------------------
// Private Methods
//-------------------------------------------------------------------------





  // This clips the URI "file:" from the beggining ... this will probably break things later
// BUG: cant use URL's from XML paths
  private static File locatorToDirectory(Locator locator) {
    try {
      String path = locator.getSystemId();
      path = path.substring(path.indexOf(":")+1);
      return (new File(path)).getParentFile();
    }
    catch (Exception e) {return null;}
  }

  
  private static String autoGenearteTagFromClass(Class c) {
    String class_name_full = c.getName();
    String class_name      = class_name_full.substring(class_name_full.lastIndexOf(".")+1);
    return class_name;
//Utils.ErrorHandeler.error(DataProcessor.class, "Method not implemented", "autoGenearteTagFromClass(Class class_name) not implemented yet");
//IMPLEMENT!!!
    //return null;
  }


}
