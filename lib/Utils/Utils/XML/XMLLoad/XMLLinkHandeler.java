package Utils.XML.XMLLoad;

public class XMLLinkHandeler extends LoadProcessorParseOnly {
  
  private static final String file_link = "href";
  
  public XMLLinkHandeler() {super("A");}

  public void parse(DataWrapper data) {
    LoadManager.open(data.getString(file_link));
  }
  
}
