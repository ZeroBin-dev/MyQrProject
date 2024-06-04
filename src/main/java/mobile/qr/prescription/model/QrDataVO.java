package mobile.qr.prescription.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
public class QrDataVO implements Serializable {

  @Serial
  private static final long serialVersionUID = -8335058751319482115L;

  /**
   * 헤더정보
   */
  private INF infData;

  /**
   * 의료기관 정보
   */
  private HSP hspData;

  /**
   * 환자 정보
   */
  private PAT patData;

  /**
   * 처방전 정보
   */
  private PRE preData;

  /**
   * 약품 정보
   */
  private List<DOZ> dozList;

  /*************************************************/
  /** 파라미터 순서 변경 금지 */
  /*************************************************/
  @Getter
  @AllArgsConstructor
  public static class INF {
    private String infVerInfo; // 버전정보
    private String infIssuerCode; // 병원발행업체코드
    private String infPubDate; // 발행일자
  }

  @Getter
  @AllArgsConstructor
  public static class HSP {
    private String hspNurseSymbol; // 요양기관기호
    private String hspMedicalName; // 의료기관명칭
    private String hspUnused1; // 미사용1
    private String hspUnused2; // 미사용2
    private String hspUnused3; // 미사용3
    private String hspDoctorName; // 처방의성명
    private String hspDoctorType; // 처방의면허종별
    private String hspDoctorLicense; // 처방의면허번호
  }

  @Getter
  @AllArgsConstructor
  public static class PAT {
    private String patName; // 환자성명
    private String patSsno; // 환자주민등록번호
    private String patCertNum; // 증번호
    private String patCombSymbol; // 조합기호 또는 산재지정기관기호
    private String patCombName; // 조합명칭 또는 사업장명칭
    private String patVeteNum; // 보훈번호
  }

  @Getter
  @AllArgsConstructor
  public static class PRE {
    private String preType; // 보험구분
    private String preFantasyEtc; // 공상등구분
    private String preGrantNumber; // 교부번호
    private String preDepartTreat; // 진료과목
    private String preSpecSymbol; // 특정기호
    private String preInjuryCode; // 상병분류기호
    private String preDisasterDate; // 재해발생일
    private String prePeriodDate; // 사용기간
    private String preUsage; // 용법
    private String preNote; // 조제시참고사항
  }

  @Getter
  @AllArgsConstructor
  public static class DOZ {
    private String dozSalaryType; // 급여구분
    private String dozCodeType; // 코드구분
    private String dozDrugCode; // 약품코드
    private String dozDrugName; // 약품명
    private String dozSingleDose; // 일회투약량
    private String dozDailyDosage; // 일일투여횟수
    private String dozDosageDays; // 투약일수
    private String dozTotalAmount; // 총량소수점포함
    private String dozUsageCode; // 용법코드
    private String dozUsageDesc; // 용법설명 용법코드가있는경우생략
    private String dozPrnType; // PRN처방구분
    private String dozOpinion; // 처방의소견

    /** BASEDRUG 정보 */
    private BaseDrugModel baseDrugModel;
  }

}
