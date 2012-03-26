package Utils.XML.XMLLoad;

import java.util.EventObject;


public class LoadEventProgress extends EventObject {
  private static final long serialVersionUID = 0;
  
  private String name;
  private int index;
  private int length;

  public LoadEventProgress(Object obj, String name, int index, int length) {
    super(obj);
    this.name   = name;
    this.index  = index;
    this.length = length;
  }

  public String getName()   {return name;}
  public int    getIndex()  {return index;}
  public int    getLength() {return length;}

}
