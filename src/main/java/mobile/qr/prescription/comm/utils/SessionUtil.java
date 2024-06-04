package mobile.qr.prescription.comm.utils;

import mobile.qr.prescription.comm.session.SessionKeys;
import mobile.qr.prescription.comm.session.SessionManager;
import mobile.qr.prescription.model.QrDataVO;

public class SessionUtil {

  public static QrDataVO getQrDataVO() {
    QrDataVO qrDataVO = SessionManager.getAs(SessionKeys.QR_DATA_VO, QrDataVO.class);
    return ObjectUtil.isEmpty(qrDataVO) ? new QrDataVO() : qrDataVO;
  }

  public static void setQrDataVO(QrDataVO qrDataVO) {
    SessionManager.put(SessionKeys.QR_DATA_VO, qrDataVO);
  }

  public static void removeQrDataVO() {
    SessionManager.remove(SessionKeys.QR_DATA_VO);
  }

  public static boolean isQrData() {
    return getQrDataVO().getDozList() != null && getQrDataVO().getDozList().size() > 0;
  }
}
