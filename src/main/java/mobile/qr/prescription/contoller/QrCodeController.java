package mobile.qr.prescription.contoller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mobile.qr.prescription.comm.annotation.QrDataCheck;
import mobile.qr.prescription.comm.utils.SessionUtil;
import mobile.qr.prescription.service.QrCodeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class QrCodeController {

  private final QrCodeService qrCodeService;

  /**
   * 메인 페이지
   */
  @GetMapping(value = {"/main", "/index", "/"})
  public String userMain() {
    return "/views/index";
  }

  /**
   * 약품 리스트
   */
  @GetMapping(value = "/drugList")
  public String drugList(final Model model, @RequestParam String qrdata) throws Exception {
    this.qrCodeService.parseQrData(qrdata);
    model.addAttribute("QrDataVO", SessionUtil.getQrDataVO());
    return "/views/drugList";
  }

  /**
   * 복약정보 페이지 (탭)
   */
  @QrDataCheck(required = true)
  @GetMapping(value = "/drugInfoTab")
  public String drugInfoTab(final Model model, @RequestParam int index) {
    model.addAttribute("index", index);
    model.addAttribute("QrDataVO", SessionUtil.getQrDataVO());
    return "/views/drugInfoTab";
  }

  /**
   * 복약정보 페이지 (아코디언)
   */
  @QrDataCheck(required = true)
  @GetMapping(value = "/drugInfoAcc")
  public String drugInfoAcc(final Model model, @RequestParam int index) {
    model.addAttribute("index", index);
    model.addAttribute("QrDataVO", SessionUtil.getQrDataVO());
    return "/views/drugInfoAcc";
  }


}
