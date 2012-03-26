package Utils.XML.XMLLoad;

public interface Constant {

  String   XML_EXTENSION  = "xml";
  String   XML_LINK_KEY = "link";
  String   XML_LIST_KEY = "[order_preservation]";
  String[] XML_PRIMARY_KEYS   = {"name", "id"};

  String   XML_EXTENSION_COMPRESSED = "xmlgzip";
  
  String[] DATA_EXTENSIONS = {XML_EXTENSION,XML_EXTENSION_COMPRESSED};
}
