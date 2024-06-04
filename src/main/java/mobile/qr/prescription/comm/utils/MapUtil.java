package mobile.qr.prescription.comm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.Map;

public class MapUtil {

  private Map _map;
  private String[] _keys;
  private String[] _values;
  private String[] _defaultValues;

  //Map -> Dto
  public static MapUtil from(final Map map) {
    MapUtil _this = new MapUtil();
    _this._map = map;
    return _this;
  }

  // MapUtil.from(rtnMap).to(xxDto.class)
  public <T> T to(final Class<T> classDef) {
    ObjectMapper mapper = new ObjectMapper();

    try {
      return (new ObjectMapper()).readValue(new Gson().toJson(_map), classDef);
    } catch (Exception e) {
      return null;
    }
  }

  // Map의 특정 key 데이터 제거
  public static void removeDataMap(Map<String, Object> dataInfo, String strArray) {
    for (String key : strArray.split(",")) {
      if (dataInfo.containsKey(key)) {
        dataInfo.remove(key);
      }
    }
  }

  // Map의 특정 key 데이터 추가
  public static void pushMapToMap(Map<String, Object> dataInfo, Map<String, ?> params, String strArray) {
    for (String key : strArray.split(",")) {
      if (params.containsKey(key.trim())) {
        dataInfo.put(key, null != params.get(key) ? params.get(key) : "");
      }
    }
  }

}
