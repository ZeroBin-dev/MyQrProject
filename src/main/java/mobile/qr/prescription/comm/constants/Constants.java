package mobile.qr.prescription.comm.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 현재는 미사용
 */
@Component
@PropertySource(value = "classpath:/properties/constants.properties")
public class Constants {

  public static String BASE_USER_ID;

  @Value("${base.user.id}")
  public void setBaseUserId(String data) {
    BASE_USER_ID = data;
  }

  public static String BASE_USER_NAME;

  @Value("${base.user.name}")
  public void setBaseUserName(String data) {
    BASE_USER_NAME = data;
  }

}
