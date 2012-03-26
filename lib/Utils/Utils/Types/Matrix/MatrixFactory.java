package Utils.Types.Matrix;

import Utils.Types.Axis;

public class MatrixFactory {
  
  public static Matrix getIdentity() {
    Matrix m = new Matrix(2,2);
    m.setIndex(1,0,0);
    m.setIndex(1,1,1);
    return m;
  }
  
  public static Matrix getRotation(double radians) {
    Matrix m = getIdentity();
    m.setIndex((float)Math.cos(radians),0,0);
    m.setIndex((float)Math.sin(radians),1,0);
    m.setIndex((float)Math.sin(radians),0,1);
    m.setIndex((float)Math.cos(radians),1,1);
    return m;
  }
  public static Matrix getTranslation() {return null;}
  
  public static Matrix getReflection(Axis... axis) {
    Matrix m = getIdentity();
    for (Axis a : axis) {
      if (a==Axis.X) {m.setIndex(-1,1,1);}
      if (a==Axis.Y) {m.setIndex(-1,0,0);}
      if (a==Axis.Z) {m.setIndex(-1,2,2);}
    }
    return m;
  }
  
  public static Matrix getEnlargement(float factor                  ) {return getStretch(factor,factor);}
  public static Matrix getStretch    (float factor_x, float factor_y) {
    Matrix m = getIdentity();
    m.setIndex(factor_x, 0,0);
    m.setIndex(factor_y, 1,1);
    return m;
  }
  
}
