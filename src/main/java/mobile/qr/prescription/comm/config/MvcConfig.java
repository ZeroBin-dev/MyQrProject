package mobile.qr.prescription.comm.config;

import lombok.RequiredArgsConstructor;
import mobile.qr.prescription.comm.annotation.QrDataCheck;
import mobile.qr.prescription.comm.interceptor.QrDataCheckInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.*;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

  private final QrDataCheckInterceptor qrDataCheckInterceptor;
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**", "/resources/**")
      .addResourceLocations("classpath:/templates/", "classpath:/static/")
      .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));

    registry.addResourceHandler("/images/**")
      .addResourceLocations("classpath:/static/images/")
      .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(qrDataCheckInterceptor)
      .excludePathPatterns("/css/**/")
      .excludePathPatterns("/js/**/")
      .excludePathPatterns("/images/**/")
      .excludePathPatterns("/favicon.ico");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {

  }

}
