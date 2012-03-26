package Utils.XML.XMLLoad;


class XMLFindTagHandeler extends LoadProcessor<Object> {
  
//-------------------------------------------------------------------------
// Processing for the FIND tag
//-------------------------------------------------------------------------
// this will perform a lookup for a previously loaded object and pass it up to
// the datawrapper. This allows the lookup of tiles for a map in xml etc.
  
  public XMLFindTagHandeler() {super(Object.class, "FIND", false);}
  
  public Object overlay(Object o, DataWrapper data) {throw new IllegalStateException();}
  public Object create(           DataWrapper data) {
    //Object o = 
    //System.out.println("FINDING="+data.getName()+" FOUND="+o);
    return LoadManager.getItem(data.getString("type"),data.getName());
  }  
}