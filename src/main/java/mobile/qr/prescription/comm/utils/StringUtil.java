package mobile.qr.prescription.comm.utils;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringUtil {

  public static boolean isNotEmpty(String str) {
    return str != null && !str.isEmpty();
  }

  public static boolean isEmpty(String str) {
    return !isNotEmpty(str);
  }

  public static String nvl(String str, String defaultValue) {
    return isNotEmpty(str) ? str : defaultValue;
  }

  // 같은값 n개 이하 사용 여부 체크
  public static boolean validDupStr(final String str, int limit) {
    // 값은값 grouping 후 count
    Map<Character, Long> countMap = str.chars()
      .mapToObj(c -> (char) c)
      .collect(Collectors.groupingBy(
        Function.identity(), Collectors.counting()
      ));

    // 모든 value 값이 n 이하인지 확인
    return countMap.values().stream().allMatch(value -> value <= limit);
  }
}
