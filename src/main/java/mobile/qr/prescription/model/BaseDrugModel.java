package mobile.qr.prescription.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * BASEDRUG 테이블 모델
 */

@Getter
@Setter
public class BaseDrugModel {

  /**
   * System
   */
  private int index; // 배열 인덱스

  /**
   * BASEDRUG 테이블
   */
  private String idx; // BASEDRUG 키
  private String drugcd; // 약품코드
  private String drugName; // 약품명
  private String drugEnm; // 약품영문명
  private String upsoName; // 업소명
  private String cobName; // 제조
  private String clsCode; // 클래스코드
  private String clsName; // 클래스명
  private String stmt; // 상태
  private String charact; // 설명
  private String drugBox; // 포장단위
  private String effect; // 효능
  private String dosage; // 복용
  private String caution; // 주의

  /**
   * IDENTIFY 테이블
   */
  private String imgCode; // 이미지코드

  /**
   * HIRA_CODE 테이블
   */
  private String unitNm; // 단위명

  /**
   * KPA_CD_PICTOGRAM_SUB 테이블
   */
  private String picCodes; // 픽토그램코드 나열(,로 구분)

  /**
   * 이미지 경로
   */
  private String imgUrl; // 약품 이미지 경로
  private ArrayList<String> pictogramList; // 주의사항 픽토그램 Url 리스트
}
