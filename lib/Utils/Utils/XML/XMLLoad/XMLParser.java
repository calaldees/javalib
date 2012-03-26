package Utils.XML.XMLLoad;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


class XMLParser {

  public static DataWrapper parse(String      uri      ) {return parseDataSource(      uri);}
  public static DataWrapper parse(File        file     ) {return parseDataSource(     file);}
  public static DataWrapper parse(InputStream stream_in) {return parseDataSource(stream_in);}
  
  public static DataWrapper parseDataSource(Object data_source) {
      XMLHandeler h = new XMLHandeler();
      try                 {parse(data_source,h);}
      catch (Exception e) {XMLError(e);}
      return h.getLoadedData();
  }

  private static void parse(Object data_source, DefaultHandler handeler) throws SAXException, IOException, ParserConfigurationException {
    SAXParserFactory sax_factory = SAXParserFactory.newInstance();
    sax_factory.setNamespaceAware(false);
    sax_factory.setValidating(false);
    SAXParser sax_parser = sax_factory.newSAXParser();
    if      (data_source instanceof File       ) {sax_parser.parse(       (File)data_source,handeler);}
    else if (data_source instanceof InputStream) {sax_parser.parse((InputStream)data_source,handeler);}
    else if (data_source instanceof String     ) {sax_parser.parse(     (String)data_source,handeler);}
    else                                         {throw new IllegalArgumentException("Unknown datasource type");}
  }
  
  private static void XMLError(Exception e) {
    Utils.ErrorHandeler.error("XML Parser Failed", e.getMessage() ,e);
    e.printStackTrace();
  }

}
