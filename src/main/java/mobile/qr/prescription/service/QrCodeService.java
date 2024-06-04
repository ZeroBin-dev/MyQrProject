package mobile.qr.prescription.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mobile.qr.prescription.comm.exception.QrBizException;
import mobile.qr.prescription.comm.types.ExceptType;
import mobile.qr.prescription.comm.utils.SessionUtil;
import mobile.qr.prescription.comm.utils.StringUtil;
import mobile.qr.prescription.dao.QrCodeDAO;
import mobile.qr.prescription.model.BaseDrugModel;
import mobile.qr.prescription.model.QrDataVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * QR 처방전 Service
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QrCodeService {

  private final QrCodeDAO qrCodeDAO;

  @Value("${spring.cdn.url}")
  private String cdnUrl;

  @Value("${spring.cdn.pictogram-url}")
  private String cdnPicUrl;

  @Value("${spring.kpa.url}")
  private String kpaUrl;

  @Value("${spring.kpa.pictogram-url}")
  private String kpaPicUrl;

  @Value("${spring.kpa.down-path}")
  private String downPath;

  @Value("${spring.kpa.pic-down-path}")
  private String picDownPath;

  @Value("${spring.file.ext}")
  private String ext;

  @Value("${spring.kpa.default-image}")
  private String defaultImage;

  @Value("${spring.qr.decode-url}")
  private String decodeUrl;

  @Value("${spring.ftp.path}")
  private String ftpPath;

  @Value("${spring.ftp.pic-path}")
  private String ftpPicPath;

  private final FileService fileService;


  /**
   * QR 데이터 파싱하기
   */
  public void parseQrData(String qrdata) throws Exception {
    String url = decodeUrl + URLEncoder.encode(qrdata, "UTF-8");
    String decString = httpGet(url);
    log.info("dec : " + decString);

    // json 으로 파싱
    ObjectMapper mapper = new ObjectMapper();
    JsonNode jsonData = mapper.readTree(decString);

    JsonNode resultCd = jsonData.get("result_cd");
    if (resultCd != null && "S0000".equals(resultCd.asText())) {
      String resultData = jsonData.get("result_data").asText();
      if (StringUtil.isNotEmpty(resultData)) {

        QrDataVO qrDataVO = new QrDataVO();
        List<QrDataVO.DOZ> dozList = new ArrayList<>();

        String[] titles = resultData.split("\\@"); // 1: INF, 2: HSP, 3: PAT, 4: PRE, 5: DOZ

        for (int i = 1; i < titles.length; i++) {
          String title = titles[i];
          String[] qrDataList = title.split("\\|", -1);

          if ("INF".equals(qrDataList[0])) {
            qrDataVO.setInfData(new QrDataVO.INF(
              qrDataList[1], qrDataList[2], qrDataList[3]
            ));

          } else if ("HSP".equals(qrDataList[0])) {
            qrDataVO.setHspData(new QrDataVO.HSP(
              qrDataList[1], qrDataList[2], qrDataList[3], qrDataList[4],
              qrDataList[5], qrDataList[6], qrDataList[7], qrDataList[8]
            ));

          } else if ("PAT".equals(qrDataList[0])) {
            qrDataVO.setPatData(new QrDataVO.PAT(
              qrDataList[1], qrDataList[2], qrDataList[3], qrDataList[4],
              qrDataList[5], qrDataList[6]
            ));

          } else if ("PRE".equals(qrDataList[0])) {
            qrDataVO.setPreData(new QrDataVO.PRE(
              qrDataList[1], qrDataList[2], qrDataList[3], qrDataList[4],
              qrDataList[5], qrDataList[6], qrDataList[7], qrDataList[8],
              qrDataList[9], qrDataList[10]
            ));

          } else if ("DOZ".equals(qrDataList[0])) {
            int size = qrDataList.length / 12;
            int param = 1;
            for (int j = 0; j < size; j++) {

              BaseDrugModel baseDrugModel = this.qrCodeDAO.selectBaseDrug(qrDataList[param + 2]);
              baseDrugModel.setIndex(j);

              /**
               * 약품 이미지 경로 설정 및 다운로드
               */
              String imgCode = baseDrugModel.getImgCode();
              String imgUrl = defaultImage;
              if (imgCode != null && StringUtil.isNotEmpty(imgCode)) {
                String cdnPath = cdnUrl + baseDrugModel.getImgCode() + "." + ext;
                String kpaPath = kpaUrl + baseDrugModel.getImgCode() + "." + ext;

                if (this.fileService.existFile(cdnPath)) {
                  imgUrl = cdnPath; // cdn 서버에 파일이 있는지 있다면 cdn 경로
                } else if (this.fileService.existFile(kpaPath)) {
                  imgUrl = kpaPath; // kpa 서버에 파일이 있다면 kpa 경로

                  /**
                   * 약품이미지 다운로드
                   */
                  String localDownPath = downPath + baseDrugModel.getImgCode() + "." + ext;
                  Thread thread = new Thread(() -> this.fileService.downImage(kpaPath, localDownPath, ftpPath));
                  thread.start();
                }
              }
              baseDrugModel.setImgUrl(imgUrl);

              /**
               * 주의사항 픽토그램 경로 설정 및 다운로드
               */
              String picCodes = baseDrugModel.getPicCodes();
              ArrayList<String> pictogramList = new ArrayList<>();

              if (StringUtil.isNotEmpty(picCodes)) {
                String[] picList = picCodes.split(",");
                for (int p = 0; p < picList.length; p++) {
                  String cdnPicPath = cdnPicUrl + picList[p] + "." + ext;
                  String kapPicPath = kpaPicUrl + picList[p] + "." + ext;

                  if (this.fileService.existFile(cdnPicPath)) {
                    pictogramList.add(cdnPicPath); // cdn 서버에 파일이 있는지 있다면 cdn 경로
                  } else if (this.fileService.existFile(kapPicPath)) {
                    pictogramList.add(kapPicPath); // kpa 서버에 파일이 있다면 kpa 경로

                    /**
                     * 픽토그램 다운로드
                     */
                    String localDownPath = picDownPath + picList[p] + "." + ext;
                    Thread thread = new Thread(() -> this.fileService.downImage(kapPicPath, localDownPath, ftpPicPath));
                    thread.start();
                  }
                }
              }
              baseDrugModel.setPictogramList(pictogramList);

              /**
               * DOZ 맵핑
               */
              dozList.add(new QrDataVO.DOZ(
                qrDataList[param], qrDataList[param + 1], qrDataList[param + 2], qrDataList[param + 3],
                qrDataList[param + 4], qrDataList[param + 5], qrDataList[param + 6], qrDataList[param + 7],
                qrDataList[param + 8], qrDataList[param + 9], qrDataList[param + 10], qrDataList[param + 11],
                baseDrugModel
              ));

              param += 12;
            }

            qrDataVO.setDozList(dozList);
          }

        }

        SessionUtil.setQrDataVO(qrDataVO);
      } else {
        throw new QrBizException(ExceptType.PARSE002);
      }
    } else {
      throw new QrBizException(ExceptType.PARSE001);
    }


  }

  private static String httpGet(String url) throws Exception {
    StringBuilder response = new StringBuilder();
    HttpURLConnection connection = null;
    BufferedReader reader = null;

    URL urlObj = new URL(url);
    connection = (HttpURLConnection) urlObj.openConnection();
    connection.setRequestMethod("GET");

    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
    } else {
      throw new QrBizException(ExceptType.PARSE003);
    }

    reader.close();
    connection.disconnect();

    return response.toString();
  }


}
