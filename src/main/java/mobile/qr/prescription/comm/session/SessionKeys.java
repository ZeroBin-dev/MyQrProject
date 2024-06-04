package mobile.qr.prescription.comm.session;

import lombok.RequiredArgsConstructor;
import mobile.qr.prescription.model.QrDataVO;

@RequiredArgsConstructor
public enum SessionKeys {

  QR_DATA_VO("QR 데이터", QrDataVO.class);

  public final String desc;
  public final Class usageClass;
}
