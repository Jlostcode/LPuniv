$(document).ready(function() {
    $('#userForm').submit(function(event) {
        var isFormValid = true; // 폼이 유효한지 여부를 나타내는 변수

        // 각 필드에 대한 검증 수행
        if (
            isFieldNullOrEmpty('#user_tp') ||
            isFieldNullOrEmpty('#user_nm') ||
            isFieldNullOrEmpty('#user_tel') ||
            isFieldNullOrEmpty('#user_loginId') ||
            isFieldNullOrEmpty('#user_passwd') ||
            isFieldNullOrEmpty('#user_email') ||
            isFieldNullOrEmpty('#user_brdt') ||
            isFieldNullOrEmpty('#user_gen') ||
            isFieldNullOrEmpty('#user_signdate')) {
            isFormValid = false;
        }

        // 폼이 유효하지 않으면 제출을 막음
        if (!isFormValid) {
            alert('공백 없이 기재해주세요');
            event.preventDefault(); // 폼 제출 막기
        } else {
            alert('등록되었습니다');
        }
    });
});

// 필드가 null이거나 trim된 상태인지 확인하는 함수
function isFieldNullOrEmpty(fieldId) {
    var fieldValue = $(fieldId).val().trim();
    return fieldValue === '' || fieldValue === null;
}