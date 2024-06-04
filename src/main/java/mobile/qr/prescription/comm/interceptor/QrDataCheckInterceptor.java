package mobile.qr.prescription.comm.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mobile.qr.prescription.comm.annotation.QrDataCheck;
import mobile.qr.prescription.comm.utils.SessionUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class QrDataCheckInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    QrDataCheck qrDataCheck = handlerMethod.getMethodAnnotation(QrDataCheck.class);

    if (qrDataCheck != null && qrDataCheck.required()) {
      if (SessionUtil.isQrData()) {
        return true;
      } else {
        response.sendRedirect(request.getContextPath() + "/");
        return false;
      }
    }

    return true;
  }

}
