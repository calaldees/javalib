package Utils;


public class MathHelper {

  public static long   diff(long   a,    long b) {return Math.abs(a-b);}
  public static int    diff(int    a,     int b) {return Math.abs(a-b);}
  public static float  diff(float  a,   float b) {return Math.abs(a-b);}
  public static double diff(double a,  double b) {return Math.abs(a-b);}
  
  public static boolean between(int a, int b, int value) {return (value>=Math.min(a,b) && value<Math.max(a,b));}
  
  public static int convertToSameSign(int reference, int value) {
    if (reference >= 0) {return  value;}
    else                {return -value;}
  }
  public static boolean differntSign(int a, int b) {
    if ((a<0 && b>0) ||(a>0 && b<0)) {return true;}
    return false;
  }
  
  
  public static int pushValue(int value, int push) {return value + convertToSameSign(value,push);}
  
  public static int pullValueToZero(int value, int pull) {
    int value_new = pushValue(value,-pull);
    if (differntSign(value_new,value)) {return 0;}
    return value_new;
  }

  public static int MinValueNotNegative(int... nums) {
    int min = Integer.MAX_VALUE;
    for (int i : nums) {
      if (i>=0 && i<min) {
        min = i;
      }
    }
    return min;
  }
}
