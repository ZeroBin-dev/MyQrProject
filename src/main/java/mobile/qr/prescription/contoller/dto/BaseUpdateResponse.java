package mobile.qr.prescription.contoller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseUpdateResponse {
  private String succYn = "N";
  private String msg = "";
}
