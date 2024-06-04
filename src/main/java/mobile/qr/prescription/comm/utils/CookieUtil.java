package mobile.qr.prescription.comm.utils;

import jakarta.servlet.http.Cookie;
import mobile.qr.prescription.comm.session.CookieManager;
import mobile.qr.prescription.comm.session.SessionKeys;
import mobile.qr.prescription.model.QrDataVO;

public class CookieUtil {

  public static Cookie[] getAllCookies() {
    return HttpRequestUtil.getCurrentRequest().getCookies();
  }

  public static Cookie getCookie(SessionKeys sessionKeys) {
    Cookie[] cookies = getAllCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(sessionKeys.name())) {
          return cookie;
        }
      }
    }
    return null;
  }

  public static QrDataVO getQrDataVO() throws Exception {
    QrDataVO qrDataVO = CookieManager.getAs(SessionKeys.QR_DATA_VO, QrDataVO.class);
    return ObjectUtil.isEmpty(qrDataVO) ? new QrDataVO() : qrDataVO;
  }

  public static void setQrDataVO(QrDataVO qrDataVO) throws Exception {
    CookieManager.put(SessionKeys.QR_DATA_VO, qrDataVO);
  }

  public static boolean isQrData() throws Exception {
    return getQrDataVO().getDozList() != null && getQrDataVO().getDozList().size() > 0;
  }
}
