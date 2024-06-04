package mobile.qr.prescription.comm.exception;

import lombok.Getter;
import mobile.qr.prescription.comm.types.ExceptType;

@Getter
public class QrBizException extends RuntimeException {
  private ExceptType error;

  public QrBizException(ExceptType e){
    super(e.getMessage());
    this.error = e;
  }

  public QrBizException(ExceptType e, String message){
    super(message);
    this.error = e;
  }

}
