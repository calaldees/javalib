package Utils;

import java.util.Calendar;
import java.util.Date;

import Utils.Caster;
import Utils.MathHelper;
import Utils.RegExHelper;
import java.util.List;


public class DateHelper {
  private static final long millisecond_second = 1000;
  private static final long millisecond_minuet = 60*millisecond_second;
  private static final long millisecond_hour   = 60*millisecond_minuet;
  private static final long millisecond_day    = 24*millisecond_hour;
  private static final long millisecond_week   =  7*millisecond_day;
  
  public static long getNow() {return Calendar.getInstance().getTimeInMillis();}

  public static long differenceInMilliSeconds(Calendar cal) {return differenceInMilliSeconds(cal.getTimeInMillis());}
  public static long differenceInSeconds(     Calendar cal) {return differenceInSeconds(     cal.getTimeInMillis());}
  public static long differenceInMinutes(     Calendar cal) {return differenceInMinutes(     cal.getTimeInMillis());}
  public static long differenceInHours(       Calendar cal) {return differenceInHours(       cal.getTimeInMillis());}
  public static long differenceInDays(        Calendar cal) {return differenceInDays(        cal.getTimeInMillis());}
  public static long differenceInWeeks(       Calendar cal) {return differenceInWeeks(       cal.getTimeInMillis());}
  
  public static long differenceInMilliSeconds(Date date) {return differenceInMilliSeconds(date.getTime());}
  public static long differenceInSeconds(     Date date) {return differenceInSeconds(     date.getTime());}
  public static long differenceInMinutes(     Date date) {return differenceInMinutes(     date.getTime());}
  public static long differenceInHours(       Date date) {return differenceInHours(       date.getTime());}
  public static long differenceInDays(        Date date) {return differenceInDays(        date.getTime());}
  public static long differenceInWeeks(       Date date) {return differenceInWeeks(       date.getTime());}
  
  public static long differenceInMilliSeconds(long time_stamp) {return MathHelper.diff(getNow(),time_stamp);                   }  
  public static long differenceInSeconds(     long time_stamp) {return MathHelper.diff(getNow(),time_stamp)/millisecond_second;}
  public static long differenceInMinutes(     long time_stamp) {return MathHelper.diff(getNow(),time_stamp)/millisecond_minuet;}
  public static long differenceInHours(       long time_stamp) {return MathHelper.diff(getNow(),time_stamp)/millisecond_hour;  }
  public static long differenceInDays(        long time_stamp) {return MathHelper.diff(getNow(),time_stamp)/millisecond_day;   }
  public static long differenceInWeeks(       long time_stamp) {return MathHelper.diff(getNow(),time_stamp)/millisecond_week;  }

  
  public static boolean passedTimeInterval(long time_to_check, String interval) {return passedTimeInterval(time_to_check,parseInterval(interval));}
  public static boolean passedTimeInterval(long time_to_check, long   interval) {
    if (differenceInMilliSeconds(time_to_check) > interval) {return  true;}
    else                                                    {return false;}
  }
  
  public static long parseInterval(String s) {
    long interval = 0; //base intervle, this will be multiplied and added to based on items in the string passed
    if      (s.contains("week") ) {interval = interval + millisecond_week;  }
    else if (s.contains("day") )  {interval = interval + millisecond_day;   }
    else if (s.contains("hour"))  {interval = interval + millisecond_hour;  }
    else if (s.contains("min") )  {interval = interval + millisecond_minuet;}
    List<String> findings = RegExHelper.search(s, "([\\d[\\d\\d]])"); // Find either 1 or 2 didgets together
    if (findings.size()>0) {
      int multiplyer = Caster.castInt(findings.get(0));
      if (multiplyer>0) {interval = interval * multiplyer;}
    }
    return interval;
  }
}