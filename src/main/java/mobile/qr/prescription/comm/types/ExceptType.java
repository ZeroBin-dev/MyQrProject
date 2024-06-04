package mobile.qr.prescription.comm.types;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT) // ENUM 클래스 한글사용
public enum ExceptType {

  /******************/
  /** API Exception */
  /******************/
  RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, "E0001", "런타임 오류가 발생하였습니다."),
  ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, "E0002", "권한이 없습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0003", "서버 통신중 오류가 발생하였습니다."),

  /******************/
  /** BIZ Exception */
  /******************/
  SESS001(HttpStatus.UNAUTHORIZED, "SESS001", "세션 타입이 일치하지 않습니다."),

  COOKIE001(HttpStatus.UNAUTHORIZED, "COOKIE001", "쿠키 타입이 일치하지 않습니다."),
  COOKIE002(HttpStatus.UNAUTHORIZED, "COOKIE002", "쿠키 값이 존재하지 않습니다."),

  PARSE001(HttpStatus.INTERNAL_SERVER_ERROR, "PARSE001", "데이터가 정상적으로 파싱되지 않았습니다."),
  PARSE002(HttpStatus.INTERNAL_SERVER_ERROR, "PARSE002", "QR 데이터가 정상적이지 않습니다."),
  PARSE003(HttpStatus.INTERNAL_SERVER_ERROR, "PARSE003", "복호화 서비스 호출에 문제가 발생하였습니다.");


  // 컬럼 정의
  private final HttpStatus status;
  private final String code;
  private final String message;

}
