function checkNullOcc() {
    // 각 입력 필드의 값을 가져와서 null 체크
    var occ_title = document.getElementsByName('occ_title')[0].value;
    var occ_content = document.getElementsByName('occ_content')[0].value;
    var occ_ROT = document.getElementsByName('occ_ROT')[0].value;
    var occ_teachInfo = document.getElementsByName('occ_teachInfo')[0].value;
    var occ_kakao = document.getElementsByName('occ_kakao')[0].value;

    if (occ_title.trim() === "") {
        showAlert("강의명을 입력해주세요.");
        return false;
    }

    if (occ_content.trim() === "") {
        showAlert("강의 내용을 입력해주세요.");
        return false;
    }

    if (occ_ROT.trim() === "") {
        showAlert("교육과정 총 시간을 입력해주세요.");
        return false;
    }

    if (occ_teachInfo.trim() === "") {
        showAlert("강사 소개/커리큘럼을 입력해주세요.");
        return false;
    }

    if (occ_kakao.trim() === "") {
        showAlert("카카오 단톡방 링크를 입력해주세요.");
        return false;
    }

    return true;
}

function showAlert(message) {
    Swal.fire({
        icon: 'warning',
        iconColor:'#12192c',
        title: '알림',
        text: message,
        confirmButtonColor: '#3085d6',
        confirmButtonText: '확인'
    });
}