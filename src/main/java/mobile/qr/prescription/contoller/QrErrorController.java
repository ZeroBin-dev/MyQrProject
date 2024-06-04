package mobile.qr.prescription.contoller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import mobile.qr.prescription.comm.exception.QrBizException;
import mobile.qr.prescription.comm.utils.StringUtil;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@ControllerAdvice
public class QrErrorController implements ErrorController {

  /**
   * QrBizException 처리
   */
  @ExceptionHandler(QrBizException.class)
  public String handleQrBizException(QrBizException ex, Model model) {
    model.addAttribute("code", ex.getError().getCode());
    model.addAttribute("msg", ex.getMessage());
    return "error/error";
  }

  @RequestMapping("/error")
  public String handlerError(HttpServletRequest request, HttpServletResponse response, Model model) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

    int statusCode = Integer.parseInt(status.toString());
    response.setStatus(statusCode);

    model.addAttribute("code", status.toString());
    model.addAttribute("msg", StringUtil.nvl(errorMessage.toString(), "잘못된 접근 입니다."));
    return "error/error";
  }

}
