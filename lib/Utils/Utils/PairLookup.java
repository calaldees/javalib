package Utils;

import java.util.Map;
import java.util.HashMap;

public class PairLookup<A,B> {

  Map<A,B> a_b = new HashMap<A,B>();
  Map<B,A> b_a = new HashMap<B,A>();

  public PairLookup() {}

  public void addPair(A a, B b) {
    a_b.put(a,b);
    b_a.put(b,a);
  }

  private B lookupA(A a) {return a_b.get(a);}
  private A lookupB(B b) {return b_a.get(b);}

  private void lookup() {
    
  }
}
