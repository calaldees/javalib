package Physics.Simulation.Component.XMLHandelers;

import Utils.XML.XMLLoad.LoadProcessor;
import Utils.XML.XMLLoad.DataWrapper;

import Physics.Simulation.Component.Material;

public class XMLMaterialProcessor extends LoadProcessor<Material> {
  public XMLMaterialProcessor() {super(Material.class, "Material");}
  
  public Material create(             DataWrapper data) throws Exception {
    Material m = super.create(data);
    if (m==null) {System.out.println("CRAP :(");}
    else         {System.out.println(m.toString());}
    return m;
  }

}
