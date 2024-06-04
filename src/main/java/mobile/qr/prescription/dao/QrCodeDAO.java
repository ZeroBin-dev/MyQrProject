package mobile.qr.prescription.dao;

import mobile.qr.prescription.model.BaseDrugModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QrCodeDAO {

  /**
   * 약품 상세정보 조회
   */
  BaseDrugModel selectBaseDrug(final String rpCodeCut);
}
