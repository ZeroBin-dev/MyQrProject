package mobile.qr.prescription.comm.session;

import jakarta.servlet.http.HttpSession;
import mobile.qr.prescription.comm.exception.QrBizException;
import mobile.qr.prescription.comm.types.ExceptType;
import mobile.qr.prescription.comm.utils.HttpRequestUtil;

public class SessionManager {

  private static HttpSession getSession() {
    return HttpRequestUtil.getSession();
  }

  private static boolean exists(final SessionKeys sessionKeys) {
    return getSession().getAttribute(sessionKeys.name()) != null;
  }

  public static void put(final SessionKeys sessionKeys, final Object object) {
    if (!sessionKeys.usageClass.isInstance(object)) {
      throw new QrBizException(ExceptType.SESS001); // 세션타입이 일치하지 않습니다.
    }
    getSession().setAttribute(sessionKeys.name(), object);
  }

  public static void remove(final SessionKeys sessionKeys) {
    getSession().removeAttribute(sessionKeys.name());
  }

  public static Object get(final SessionKeys sessionKeys) {
    return exists(sessionKeys) ? getSession().getAttribute(sessionKeys.name()) : null;
  }

  public static <T> T getAs(SessionKeys sessionKeys, final Class<T> clazz) {
    if (!sessionKeys.usageClass.getClass().isInstance(clazz)) {
      throw new QrBizException(ExceptType.SESS001); // 세션타입이 일치하지 않습니다.
    }
    return (T) get(sessionKeys);
  }
}
