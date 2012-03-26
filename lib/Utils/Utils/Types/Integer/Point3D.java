package Utils.Types.Integer;

import Utils.Types.*;

public interface Point3D extends Point2D {
  public int z();
  public boolean equals(Object o);
  public boolean equals(int x, int y, int z);
  public boolean isPositive();
}