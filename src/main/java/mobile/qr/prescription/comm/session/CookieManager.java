package mobile.qr.prescription.comm.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import mobile.qr.prescription.comm.exception.QrBizException;
import mobile.qr.prescription.comm.types.ExceptType;
import mobile.qr.prescription.comm.utils.CookieUtil;
import mobile.qr.prescription.comm.utils.HttpRequestUtil;
import mobile.qr.prescription.comm.utils.ObjectUtil;

import java.util.Base64;

public class CookieManager {

  public static HttpServletResponse getResponse() {
    return HttpRequestUtil.getCurrentResponse();
  }

  public static void put(final SessionKeys sessionKeys, final Object object) throws Exception {
    String jsonValue = ObjectUtil.objectToJsonString(object);
    Cookie cookie = new Cookie(sessionKeys.name(), Base64.getEncoder().encodeToString(jsonValue.getBytes()));
    cookie.setMaxAge(300); // 5분
    getResponse().addCookie(cookie);
  }

  public static <T> T getAs(SessionKeys sessionKeys, final Class<T> clazz) throws Exception {
    Cookie cookie = CookieUtil.getCookie(sessionKeys);

    if (cookie != null) {
      throw new QrBizException(ExceptType.COOKIE002); // 쿠키 값이 존재하지 않습니다.
    }

    String cookieValue = cookie.getValue();
    byte[] decodedBytes = Base64.getDecoder().decode(cookieValue);
    String decodedString = new String(decodedBytes);

    T readClass = ObjectUtil.jsonStringToObject(decodedString, clazz);

    if (!sessionKeys.usageClass.getClass().isInstance(readClass)) {
      throw new QrBizException(ExceptType.SESS001); // 쿠키 타입이 일치하지 않습니다.
    }
    return readClass;
  }
}
