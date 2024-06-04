package mobile.qr.prescription.comm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ObjectUtils;

public class ObjectUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static boolean isEmpty(Object obj) {
    return ObjectUtils.isEmpty(obj);
  }

  public static boolean isNotEmpty(Object obj) {
    return !ObjectUtils.isEmpty(obj);
  }

  public static Object nvl(Object obj, Object defaultValue) {
    return isNotEmpty(obj) ? obj : defaultValue;
  }


  /**
   * Object -> String
   */
  public static String objectToJsonString(Object obj) throws Exception {
    return objectMapper.writeValueAsString(obj);
  }

  public static <T> T jsonStringToObject(String jsonString, Class<T> clazz) throws Exception {
    return objectMapper.readValue(jsonString, clazz);
  }


}
