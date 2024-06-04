package mobile.qr.prescription.comm.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class HttpRequestUtil {

  /**
   * 현재 request 가져오기
   */
  public static HttpServletRequest getCurrentRequest() {
    try {
      return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 현재 response 가져오기
   */
  public static HttpServletResponse getCurrentResponse() {
    try {
      return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * 현재 세션 가져오기
   */
  public static HttpSession getSession() {
    return getCurrentRequest().getSession();
  }

  /**
   * 현재 클라이언트 접속 IP 가져오기
   */
  public static String getClientIP() {
    HttpServletRequest request = getCurrentRequest();

    String ip = request.getHeader("X-Forwarded-For");

    if (ip == null) {
      ip = request.getHeader("Proxy-Client-IP");
    }

    if (ip == null) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }

    if (ip == null) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }

    if (ip == null) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null) {
      ip = request.getRemoteAddr();
    }

    return ip;
  }

}
