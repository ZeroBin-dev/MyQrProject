package mobile.qr.prescription.service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
@Service
public class FileService {

  /**
   * FTP 정보
   */
  @Value("${spring.ftp.ip}")
  private String ftpIp;

  @Value("${spring.ftp.id}")
  private String ftpId;

  @Value("${spring.ftp.pw}")
  private String ftpPw;

  /**
   * 서버에 파일이 있는지 체크
   */
  public boolean existFile(String checkUrl) {
    HttpURLConnection connection = null;
    try {
      URL url = new URL(checkUrl);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("HEAD");

      // 연결 상태 코드 확인
      int responseCode = connection.getResponseCode();

      // 200 OK 상태 코드인 경우 파일이 존재함
      return responseCode == HttpURLConnection.HTTP_OK;
    } catch (Exception e) {
      // 예외 발생 시 파일이 존재하지 않음
      log.error("이미지 파일 없음 : " + checkUrl);
      return false;
    } finally {
      // 연결 해제
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  /**
   * 파일 다운로드
   */
  public void downImage(String imageUrl, String destinationFile, String ftpPath) {
    try {
      URL url = new URL(imageUrl);
      URLConnection conn = url.openConnection();

      InputStream inputStream = conn.getInputStream();

      OutputStream outputStream = new FileOutputStream(destinationFile);

      byte[] buffer = new byte[2048];
      int length;
      while ((length = inputStream.read(buffer)) != -1) {
        outputStream.write(buffer, 0, length);
      }

      outputStream.close();
      inputStream.close();

      log.info("이미지 다운로드 완료 : " + imageUrl);

      sendImage(destinationFile, ftpIp, ftpId, ftpPw, ftpPath);

    } catch (Exception e) {
      log.error(e.getMessage());
      log.error("이미지 다운로드 실패 : " + imageUrl);
    }

  }

  private static void sendImage(String localFilePath, String p_ftpIp, String p_ftpId, String p_ftpPw, String p_ftpPath) {
    JSch jsch = new JSch();
    Session session = null;
    ChannelSftp channelSftp = null;

    try {
      // 세션 설정
      session = jsch.getSession(p_ftpId, p_ftpIp, 22);
      session.setPassword(p_ftpPw);
      session.setConfig("StrictHostKeyChecking", "no");
      session.connect();

      // SFTP 채널 열기
      channelSftp = (ChannelSftp) session.openChannel("sftp");
      channelSftp.connect();

      // 로컬 파일 업로드
      File localFile = new File(localFilePath);
      String remoteFileName = localFile.getName();
      String remoteFilePath = p_ftpPath + "/" + remoteFileName;

      channelSftp.put(new FileInputStream(localFile), remoteFilePath);
      log.info("SFTP 전송 성공 [remoteFilePath] : " + remoteFilePath);
    } catch (Exception e) {
      // 오류 로깅
      log.error(e.getMessage());
      log.error("SFTP 전송 실패 [localFilePath] : " + localFilePath);
    } finally {
      // 연결 종료
      if (channelSftp != null) {
        channelSftp.disconnect();
      }
      if (session != null) {
        session.disconnect();
      }
    }
  }

}
