package Tile.Model.Map;

import java.util.Map;
import java.util.HashMap;

public class LayerIdentifyer {

  public static enum MergeMode          {forbiden,   replace, merge}; //Try to merge this layer using custom merge handeler (see MergeableMapLayer) or just set the layer using the MergeModeBehaviorBelow
  public static enum MergeModeBehaviour {forbiden, reference, clone}; //When copying/cutting/pasting allow "reference"s (immutable objects ONLY), or clone (copy the object)
  public static enum LayerType          {system  ,    player, event, other};
  
  //Keep a register of Layers that have types associated with them
  //This is conveneince to just add tiles to the correct layer automaticaly
  private static Map<Class,LayerIdentifyer>  layers_by_class  = new HashMap<Class ,LayerIdentifyer>();
  private static Map<String,LayerIdentifyer> layers_by_string = new HashMap<String,LayerIdentifyer>();
  public  static LayerIdentifyer getLayerIdentifyer(Class  c                         ) {return layers_by_class.get (c);}
  public  static LayerIdentifyer getLayerIdentifyer(String name                      ) {return layers_by_string.get(name);}
  private static void            addLayerIdentifyer(Class  c   , LayerIdentifyer layer_id) {layers_by_class.put (c   ,layer_id);}
  private static void            addLayerIdentifyer(String name, LayerIdentifyer layer_id) {layers_by_string.put(name,layer_id);}
  
  private String name;
  private MergeMode          merge_mode           = MergeMode.forbiden;
  private MergeModeBehaviour merge_mode_behaviour = MergeModeBehaviour.forbiden;
  private boolean allow_save = false;
  private boolean allow_copy = false;
  private boolean allow_cut  = false;
  private boolean visable    = true;
  
  public LayerIdentifyer(String name         ) {
    if (name==null) {throw new IllegalArgumentException("No name given to layer");}
    this.name = name;
    addLayerIdentifyer(name,this);
  }
  public LayerIdentifyer(String name, Class c) {
    this(name);
    if (c!=null) {addLayerIdentifyer(c,this);}
  }
  public LayerIdentifyer(String name,          MergeMode merge_mode, MergeModeBehaviour merge_mode_behaviour) {this(name,null,merge_mode,merge_mode_behaviour);}
  public LayerIdentifyer(String name, Class c, MergeMode merge_mode, MergeModeBehaviour merge_mode_behaviour) {this(name,   c,merge_mode,merge_mode_behaviour,false,false,false,true);}
  public LayerIdentifyer(String name, Class c, MergeMode merge_mode, MergeModeBehaviour merge_mode_behaviour, boolean allow_save, boolean allow_cut, boolean allow_copy, boolean visable) {
    this(name,c);
    this.merge_mode           = merge_mode;
    this.merge_mode_behaviour = merge_mode_behaviour;
    this.allow_save = allow_save;
    this.allow_copy = allow_copy;
    this.allow_cut  = allow_cut;
    this.visable    = visable;
  }

  
  public String  getName()         {return name;}
  public boolean allowSaveLayer()  {return allow_save;} //Should this layer be serialised when saved, some layers like LOS may not be nessisaty to save
  //public boolean allowCopyLayer()  {return allow_copy;} //If the Layer is immutable then allow copying
  //public boolean allowCutLayer()   {return allow_cut; } //If the Layer is immutable then allow copying
  public boolean isVisable()       {return visable;   } //Hints to the rederer that this layer should be shown
  public MergeMode          getMergeMode()           {return merge_mode;}
  public MergeModeBehaviour getMergeModeBehaviour()  {return merge_mode_behaviour;}
  
  public Class getAutoType() {return null;} //If this layer can only have one type, this allows the conenience of just puting info onto the map without haveing to specity the layer identifyer
  
  //Layers can be catagotised as
  //  System: map tile data
  //  Player: LOS + other player related
  //  Event : Areas that trigger events
  //  Other : Multiplayer Beacons for teams?
  public LayerType getLayerType() {return null;}
  
  public int hashCode() {return name.hashCode();}
  public boolean equals(Object obj) {
    if (!(obj instanceof LayerIdentifyer)) {return false;}
    if (getName().equals((LayerIdentifyer)obj)) {return true;}
    return false;
  }

  public String toString() {return LayerIdentifyer.class + " "+ name;}
}