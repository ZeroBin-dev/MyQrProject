// Post API 호출
function postApi(url, params, successCallback, errorCallback) {
  $.ajax({
    type: 'POST',
    url: url,
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    },
    data: JSON.stringify(params),
    success: function (data) {
      if (typeof successCallback === 'function') {
        successCallback(data);
      }
    },
    error: function (err) {
      if (typeof errorCallback === 'function') {
        errorCallback(err);
      }
    }
  });
}

/**
 * 주민번호로 성별 가져오기
 */
function getGender(ssno) {
  // 주민등록번호 형식을 확인하는 정규 표현식
  var regex = /^[0-9]{6}(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2][0-9]|3[0-1])[0-9]{6}$|^[0-9]{7}(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2][0-9]|3[0-1])[0-9]{3}$|^[0-9]{13}$/;

  // 주민등록번호 형식이 맞는지 확인
  if (!regex.test(ssno)) {
    console.log('올바른 주민등록번호 형식이 아닙니다.');
    return 'E';
  }

  // 성별 추출
  var genderNum = ssno.charAt(6);
  var gender = (genderNum % 2 === 1) ? 'M' : 'W';

  return gender;
}

/**
 * 주민번호로 만나이 계산하기
 */
function getManAge(ssno) {
  // 주민등록번호 형식을 확인하는 정규 표현식
  var regex = /^[0-9]{6}(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2][0-9]|3[0-1])[0-9]{6}$|^[0-9]{7}(?:0[1-9]|1[0-2])(?:0[1-9]|[1-2][0-9]|3[0-1])[0-9]{3}$|^[0-9]{13}$/;

  // 주민등록번호 형식이 맞는지 확인
  if (!regex.test(ssno)) {
    console.log('올바른 주민등록번호 형식이 아닙니다.');
    return '0';
  }

  // 생년월일 추출
  var birthYear = "";

  if (parseInt(ssno.charAt(6), 10) > 2) {
    birthYear = '20' + ssno.substring(0, 2);
  } else {
    birthYear = '19' + ssno.substring(0, 2);
  }


  var birthMonth = parseInt(ssno.substring(ssno.length === 13 ? 2 : 4, ssno.length === 13 ? 4 : 6), 10);
  var birthDay = parseInt(ssno.substring(ssno.length === 13 ? 4 : 6, ssno.length === 13 ? 6 : 8), 10);

  var today = new Date();
  var currentYear = today.getFullYear();
  var currentMonth = today.getMonth() + 1;
  var currentDay = today.getDate();

  var age = currentYear - parseInt(birthYear, 10);
  if (currentMonth < birthMonth || (currentMonth === birthMonth && currentDay < birthDay)) {
    age--;
  }

  return age;
}

function getDateFormat(infDate) {
  // 입력된 날짜 형식을 "YYYYMMDDhhmmss"에서 "YYYY.MM.DD hh:mm:ss"로 변환하는 함수

  // 입력된 날짜 형식을 "YYYY-MM-DDThh:mm:ss"로 변환하여 Date 객체 생성
  var year = infDate.substring(0, 4);
  var month = infDate.substring(4, 6);
  var day = infDate.substring(6, 8);
  var hour = infDate.substring(8, 10);
  var minute = infDate.substring(10, 12);
  var second = infDate.substring(12, 14);

  // 요일 정보 구하기
  var date = new Date(year, month - 1, day);
  var days = ['일', '월', '화', '수', '목', '금', '토'];
  var dayOfWeek = days[date.getDay()];

  // 변환된 형식으로 반환
  return year + '.' + month + '.' + day + ' (' + dayOfWeek + ') ' + hour + ':' + minute + ':' + second;
}