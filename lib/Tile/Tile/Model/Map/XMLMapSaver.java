package Tile.Model.Map;

import Utils.Types.Integer.Point3DUpdatable;
import Utils.XML.XMLLoad.Indexable;
import Utils.XML.XMLLoad.LoadManager;
import Utils.XML.XMLSave.SaveHandeler;
import Utils.XML.XMLSave.SaveProcessor;

public class XMLMapSaver extends SaveProcessor<MapFoundation> {
  
  public XMLMapSaver() {super(MapFoundation.class,"MAP");}
 
  public void save(SaveHandeler s, MapFoundation map) {
    s.print("<MAP name=\""+map.getName()+"\">\n");
    s.print("<MAPFOUNDATION size=\""+map.getDimension3D().toString()+"\"/>\n");
    Point3DUpdatable p = new Point3DUpdatable();
    for ( ; p.z<map.getDimension3D().getHeight() ; p.z++) {
      for ( ; p.y<map.getDimension3D().getLength() ; p.y++) {
        for ( ; p.x<map.getDimension3D().getWidth()  ; p.x++) {
//System.out.println("GETTILE: "+p);
          MapFoundationTile tile = map.getTile(p);
          if (tile!=null && tile.layerCount()>0) {
            s.print("<MAPTILE location=\""+p+"\">");
            
            for (LayerIdentifyer id : tile.getLayers()) {
              Object l = tile.getLayer(id);
              if (l instanceof Indexable && id.allowSaveLayer()) {
                String tag = LoadManager.getTagFromClass(l.getClass());
                if (tag!=null) {
                  s.print("<FIND type=\""+tag+"\" name=\""+((Indexable)l).getName()+"\"/>");
                }
              }
            }
            
            s.print("</MAPTILE>\n");
          }
        }
        p.x=0;
      }
      p.y=0;
    }
    p.z=0;
    s.print("</MAP>\n");
  }
}
