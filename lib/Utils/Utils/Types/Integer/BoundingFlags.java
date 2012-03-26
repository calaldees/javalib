package Utils.Types.Integer;

public class BoundingFlags {
  
  public boolean top      = false;
  public boolean bottom   = false;
  public boolean left     = false;
  public boolean right    = false;
  public boolean floor    = false;
  public boolean celing   = false;
  public boolean inbounds = false;
  
  public BoundingFlags() {}
  
  public String toString() {
    return "top:"+top+" bottom:"+bottom+" left:"+left+" right:"+right+" floor:"+floor+" celing:"+celing+" inbounds:"+inbounds;
  }
  
  /*
  public void mergeFlags(BoundingFlags b) {
    if (b.top   ==true) {top=true;}
    if (b.bottom==true) {bottom=true;}
    if (b.left  ==true) {left=true;}
    if (b.right ==true) {right=true;}
    if (b.floor ==true) {floor=true;}
    if (b.celing==true) {celing=true;}
  }
   */
}
