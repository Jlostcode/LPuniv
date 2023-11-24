function sendData() {
    // 선택된 체크박스 값 가져오기
    let stud_no = [];
    $("input:checkbox[name=stud_no]:checked").each(function(){
        stud_no.push($(this).val());
    });

    let occ_NO = [];
    $("input:checkbox[name=occ_NO]:checked").each(function(){
        occ_NO.push($(this).val());
    });

    console.log(stud_no);
    console.log(occ_NO);
    // 선택된 값이 없는 경우 처리
    if(stud_no.length === 0 || occ_NO.length === 0) {
        showAlert("학생과 강의를 선택해 주세요.");
        return;
    }

    // Ajax로 서버로 선택된 값을 POST 방식으로 전송
    $.ajax({
        type: "POST",
        url: "/stuLec/stuList",
        data: {
           'stud_no[]': stud_no.map(Number),
           'occ_NO[]': occ_NO.map(Number)
        },
        traditional: true,
        success: function(response) {
            if (response.value === null){
                showAlert("이미 수강되어 있는 학생이 있습니다")
            }
            console.log(response);
            window.location.href = "/stuLec/stuList"
            showAlert("수강등록 되었습니다!");
        },
        error: function(xhr, status, error) {
            showAlert("이미 수강된 학생이 존재합니다." + error);
        }
    });
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