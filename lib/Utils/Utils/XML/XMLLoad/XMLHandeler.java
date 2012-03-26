package Utils.XML.XMLLoad;

import Utils.ErrorHandeler;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;

import java.util.*;


class XMLHandeler extends DefaultHandler {

//-------------------------------------------------------------------------
// Static Variables
//-------------------------------------------------------------------------

  private static String   character_key = "character_data";
  private static String   LINKTAG       = "link:";


//-------------------------------------------------------------------------
// Variables
//-------------------------------------------------------------------------

  private String             character_data;
  private Stack<DataWrapper>     data_stack;
  private Locator                   locator;

  private DataWrapper data_final_return;
//-------------------------------------------------------------------------
// Public Methods
//-------------------------------------------------------------------------
  public DataWrapper getLoadedData() {return data_final_return;}
  
//-------------------------------------------------------------------------
// Public Methods (DefaultHandler overrides)
//-------------------------------------------------------------------------

  public void setDocumentLocator(Locator locator) {this.locator = locator;}

  public void startDocument() {
    character_data = null;
    data_stack     = new Stack<DataWrapper>();
  }

  public void endDocument() {}

  public void startElement(String uri, String localName, String qName, Attributes attributes) {
    DataWrapper data = new DataWrapper(getCurrentData());
    data_stack.push(data);
    // Merge Attributes into map
    if (attributes!=null) {
      for(int a=0 ; a<attributes.getLength() ; a++) {
        data.put(attributes.getQName(a), attributes.getValue(a));
      }
    }
    clearCharacterData();
  }

  public void endElement(String uri, String localName, String qName) {
    mergeCharacterDataWithCurrentData();

    DataWrapper data   = data_stack.pop();
    //DataWrapper parent = getCurrentData();

    data.finaliseAndMergeIntoParent(qName);
    if (data_stack.empty()) {data_final_return = data;}
    
    if (LoadProcessorManager.isRegisteredTag(qName)) {
      //DataWrapper data = new DataWrapper(current_map, getPrimaryKey(parent_map));
      Object processed_component = LoadProcessorManager.process(qName, data, locator);
      data.replaceThisDataWrapperWithProcessedObject(processed_component);
      //if (DataProcessorManager.catalogObjectsOfThisType(qName)) {
        //add(qName, data, processed_component));
      //}
    }
    else {}

  }

  public void characters(char[] ch, int start, int length) {
    try {
      String s = (new String(ch, start, length)).trim();
      if (s.length()>0) {
        if (character_data==null) {character_data = s;}
        else                      {character_data = character_data + "\n" + s;}
      }
    }
    catch (Exception e) {}
  }


  public void processingInstruction(String target, String data) {
    //System.out.println("TARGET:"+target+" DATA:"+data);
    if (target.equals(LINKTAG)) {
      String uri;
      if (data.startsWith("./")) {uri = getLocalPathURI()+data.substring(1);}
      else                       {uri = data;}
      XMLParser.parse(uri);
      //System.out.println("LOAD: "+uri);
    }
  }
  
  
//-------------------------------------------------------------------------
// Error Methods
//-------------------------------------------------------------------------

  public void error(SAXParseException e)       {ErrorHandeler.error("XML Error  ", getLocationInfo(), e);}
  public void fatalError(SAXParseException e)  {ErrorHandeler.error("XML Faital ", getLocationInfo(), e);}
  public void warning(SAXParseException e)     {ErrorHandeler.error("XML Warning", getLocationInfo(), e);}

  private String getLocationInfo() {
    return "Line    ="+locator.getLineNumber()   +"\n"+
           "Col     ="+locator.getColumnNumber() +"\n"+
           "PublicID="+locator.getPublicId()     +"\n"+
           "SystemID="+locator.getSystemId()     +"\n";
  }

//-------------------------------------------------------------------------
// Private Methods
//-------------------------------------------------------------------------


  private DataWrapper getCurrentData() {
    try                 {return data_stack.peek();}
    catch (Exception e) {return null;}
  }

  private void clearCharacterData()               {character_data = null;}
  private void mergeCharacterDataWithCurrentData() {
    try {
      if (character_data!=null) {
        getCurrentData().put(character_key, character_data);
      }
    }
    catch (Exception e) {}
    clearCharacterData();
  }

  public String getLocalPathURI() {
    String s = locator.getSystemId();
    return s.substring(0,s.lastIndexOf("/"));
  }

/*
  //private void add(Map<String,Object> map, String tag_name, Object obj_to_add                 ) {add(map,tag_name,obj_to_add,null);}
  private void add(String tag_name, DataWrapper data, Object obj_to_add) {
    if (tag_name!=null && data!=null && obj_to_add!=null) {
      String obj_name = data.getName();

      
      if (data!=null) { data.put(tag_name, obj_to_add); }
      // If there is an existing element
      else {
        DataWrapper        sub_data    = castData(sub_element);
        Collection<Object> sub_array   = castArray(sub_element);
        if      (sub_array  !=null) {sub_array.add(obj_to_add);} // If there is an array then we have no choice but to add to it
        else if (obj_name   ==null) {
          // If we cant name the current object we have to array it (reguardless of the processor not wanting arrays)
          Collection<Object> c = new Vector<Object>();
          for (Object o: sub_map.values()) {c.add(o);}
          c.add(obj_to_add);
          data.put(tag_name,c);
        }
        else if (sub_map    !=null) {sub_map.put(obj_name,obj_to_add);}
        else {Error.error(this,"XML Processing Error", "This should never happen, investigate!!!!");}
      }

    }
  }

  
  
  private String getPrimaryKey(Object o) {
    try                 {return ((DataWrapper)o).getPrimaryKey();}
    catch (Exception e) {return null;}
  }
*/



  private static DataWrapper castData(Object o) {
    try                 {return (DataWrapper)o;}
    catch (Exception e) {return null;}
  }

  private static String castString(Object o) {
    try                 {return (String)o;}
    catch (Exception e) {return null;}
  }

  /*
  private static Collection<Object> castArray(Object o) {
    try                 {return (Collection<Object>)o;}
    catch (Exception e) {return null;}
  }
*/


}
