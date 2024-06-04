package mobile.qr.prescription.comm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

  /**
   * 날짜포맷
   */
  public static String getDateStr() {
    return getDateStr("yyyy.MM.dd HH:mm:ss");
  }

  public static String getDateStr(String format) {
    return getDateStr(format, new Date());
  }

  public static String getDateStr(String format, Date dt) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
    return simpleDateFormat.format(dt);
  }
}
