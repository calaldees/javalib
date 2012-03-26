package Utils.Types.Matrix;

public class Matrix {
  float[][] matrix;
  
  public Matrix(            ) {this(2,2);}
  public Matrix(int n, int m) {matrix = new float[n][m];}
  public Matrix(float[][] matrix) {} //copy array
  public Matrix(Matrix m)         {} //copy object
  
  public float getIndex(int... i) {
    try {
      if (i.length == 1) {return matrix[i[0]][0   ];}
      if (i.length == 2) {return matrix[i[0]][i[1]];}
    }
    catch (Exception e) {}
    return 0;
  }
  
  public void setIndex(float v, int... i) {
    try {
      if (i.length == 1) {matrix[i[0]][0   ] = v;}
      if (i.length == 2) {matrix[i[0]][i[1]] = v;}
    }
    catch (Exception e) {}
  }
  
}