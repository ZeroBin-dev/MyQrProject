package mobile.qr.prescription.contoller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BaseResponse {
  private String resultCd = "200";
  private String errorYn = "N";
}
