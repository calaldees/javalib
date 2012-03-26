package Utils.File;

import java.io.File;

import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.leocate.xmlUtils.SerializeXmlDocument;

public class XMLDocumentManager {
  
  //------------------------------------------------------------------------------
  // Save Document
  //------------------------------------------------------------------------------
  
  public static void saveDocument(Document doc, String uri ) throws Exception {saveDocument(doc, new File(uri));}
  public static void saveDocument(Document doc, File   file) throws Exception {
    SerializeXmlDocument xml = new SerializeXmlDocument();
    xml.serializeXmlDoc(doc,file);
  }
  /*
  public static void saveDocument(Document doc, String uri ) throws Exception {saveDocument(doc, new File(uri));}
  public static void saveDocument(Document doc, File   file) throws Exception {
    doc.normalizeDocument();
    TransformerFactory.newInstance().newTransformer().transform(new DOMSource(doc), new StreamResult(file));
  }
*/
  
// This method writes a DOM document to a file
  /*
    public static void saveDocument(Document doc, String filename) {
        try {
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer aTransformer = tranFactory.newTransformer();

            Source src = new DOMSource(doc);
            Result dest = new StreamResult(new File(filename));
            aTransformer.transform(src, dest);
        } 
        catch (TransformerConfigurationException e) {
          Utils.ErrorHandeler.error("failed to save XML","gay");
        } 
        catch (TransformerException e) {
          Utils.ErrorHandeler.error("failed to save XML","gay2");
        }
    }
   */
  
  //------------------------------------------------------------------------------
  // Open Document
  //------------------------------------------------------------------------------
  
  public static Document openDocument(String uri) {
    try                 {return getDocumentBuilder().parse(uri );}
    catch (Exception e) {return null;}
  }
  public static Document openDocument(File file) {
    try                 {return getDocumentBuilder().parse(file);}
    catch (Exception e) {return null;}
  }
  
  private static DocumentBuilder getDocumentBuilder() {
    try                 {return DocumentBuilderFactory.newInstance().newDocumentBuilder();}
    catch (Exception e) {return null;}
  }
  
  //------------------------------------------------------------------------------
  // Document to String
  //------------------------------------------------------------------------------
  
  public static String getDocumentAsText(Document doc) {
    return getNodeAsText(doc.getDocumentElement(),"");
  }
  private static String getNodeAsText(Node n, String indent) {
    String s = indent + n.getNodeName() +"="+ n.getNodeValue() + "\n";
    if (n.hasAttributes() || n.hasChildNodes()) {
      s = s + getNodeAsText(n.getChildNodes(), indent+"  ");
    }
    return s;
  }
  private static String getNodeAsText(NodeList nl, String indent) {
    String s = "";
    if (nl!=null && nl.getLength()>0) {
      for (int i = 0 ; i < nl.getLength() ; i++) {
        s = s + getNodeAsText(nl.item(i), indent);
      }
    }
    return s;
  }
}
