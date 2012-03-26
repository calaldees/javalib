package Utils.ImageLoader;

import Utils.XML.XMLLoad.LoadManager;
import Utils.XML.XMLLoad.LoadProcessorParseOnly;
import Utils.XML.XMLLoad.DataWrapper;

import java.awt.Point;


public class XMLImageTagHandeler extends LoadProcessorParseOnly {

  private static String IMAGE_TAG    = "IMG";
  private static String IMAGE_SOURCE = "SRC";

  public XMLImageTagHandeler() {super(IMAGE_TAG);}

  public void parse(DataWrapper data) {
    // Load Image
    String  filename = data.getString(IMAGE_SOURCE);
    ImageBlockLoader.load( LoadManager.autoLocateFile(getLocation(), filename) );
    
    //Names can be given to grid locations on images view additional aruguments
    for (String name : data.keySet()) {
      Point p = data.getPoint(name);
      if (p!=null) {ImageBlockLoader.addNameOverlay(name,p);}
    }
    
  }

}
