package Utils.Internet.RSS;

import java.io.File;
import org.w3c.dom.*;

import Utils.File.XMLDocumentManager;

public class RSSManager {
  
  int max_items = 15;
  Document rss;
  String   uri;
  
  public RSSManager(String uri) {
    rss = XMLDocumentManager.openDocument(uri);
    if (rss==null) {
      //create a DOM structure
    }
    this.uri = uri;
  }

  public void setMaxItems(int max) {max_items = max;}

  public void addNewItem(String title, String link                                 ) {addNewItem(title,link,null);}
  public void addNewItem(String title, String link, String description             ) {addNewItem(title,link,description,null);}
  public void addNewItem(String title, String link, String description, String guid) {
    //add date as "now" date in the correct format "Tue, 03 Jun 2003 09:39:21 GMT" RFC822
    Element item = createItemElement(title,link,description,guid);
    Node    root = getRootChannelNode();
    try                 {root.insertBefore(item ,rss.getElementsByTagName("item").item(0));}
    catch (Exception e) {root.appendChild( item                                          );}
  }
  
  
  public void setSingleProperty(String property, String value) {
    //for catagotys, author fields, other additional stuff
    Node    root        = getRootChannelNode();
    Element new_element = createSingleTextElement(property,value);
    try {
      Node old_element = rss.getElementsByTagName(property).item(0);
      root.replaceChild(new_element,old_element);
    }
    catch (Exception e) {
      root.appendChild(new_element);
    }
  }
  
  public void save() {
    //remove items over max
    NodeList items = rss.getElementsByTagName("item");
    if (items.getLength()>max_items) {
      for (int i=max_items ; i<items.getLength() ; i++) {
        Node item_to_remove = items.item(i);
        item_to_remove.getParentNode().removeChild(item_to_remove);
      }
    }
    
    //update <lastBuildDate>
    setSingleProperty("lastBuildDate",getNow());
    
    try                 {XMLDocumentManager.saveDocument(rss, uri);}
    catch (Exception e) {Utils.ErrorHandeler.error(getClass(),"Unable to save RSS to XML "+uri,e);}
  }
  
  //----------------------------------------------------------------------------
  // Private Methods
  //----------------------------------------------------------------------------
  
  private Node getRootChannelNode() {
    NodeList nl = rss.getDocumentElement().getElementsByTagName("channel");
    if (nl.getLength()>0) {return nl.item(0);}
    else                  {return null;      }
  }

  private Element createItemElement(String title, String link, String description, String guid) {
    Element item = rss.createElement("item");
    item.appendChild(createSingleTextElement("title"      ,title      ));
    item.appendChild(createSingleTextElement("link"       ,link       ));
    item.appendChild(createSingleTextElement("description",description));
    item.appendChild(createSingleTextElement("guid"       ,guid       ));
    item.appendChild(createSingleTextElement("pubDate"    ,getNow()   ));
    return item;
  }
  
  private Element createSingleTextElement(String tag, String contents) {
    Element e = rss.createElement(tag);
    e.appendChild(rss.createTextNode(contents));
    return e;
  }
  private String getNow() {
    return "implement getNow() please";
  }
}
