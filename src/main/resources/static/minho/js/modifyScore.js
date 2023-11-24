function modifyScore() {
    let submit_sc = document.getElementById("submit_sc3").value;
    let submit_no = document.getElementsByName("submit_no")[0].value;
    let amc_no = document.getElementsByName("amc_no")[0].value;
    let submit_sc2 = document.getElementsByName("amc_no")[0].value;

    if (!submit_sc) {
        showAlert("점수를 입력하지 않았습니다. 입력 부탁드립니다.");
    } else if (submit_sc > 100){
        showAlert("100점이 넘으면 기입할 수 없습니다.")
    }else if (submit_sc !== 0) {
        // 확인 대화 상자 표시
        let confirmResult = confirm('점수를 입력하시겠습니까?');
        if (confirmResult) {
            // 사용자가 확인을 누른 경우에만 Ajax 요청을 보냅니다.
            console.log("submit_sc=================="+submit_sc)
            $.ajax({
                type: 'POST', // 또는 'POST' 요청 방식
                url: '/submit/updateScore', // 컨트롤러의 URL을 적어주세요.
                data: {
                    submit_sc: submit_sc,
                    submit_no: submit_no,
                    amc_no: amc_no
                },
                success: function (data) {
                    showAlert("점수가 입력되었습니다.")
                    // 성공적으로 처리한 후의 동작
                    window.location.href = '/submit/listSubmit?amc_no='+amc_no;
                },
                error: function (x) {
                    console.log(x);
                    // 오류 발생 시 처리
                    showAlert('점수 입력 중 오류가 발생했습니다.');
                }
            });
        }
    } else if(submit_sc2 === 0) {
        showAlert("점수를 입력한 적이 없어서 수정이 불가능 합니다.")
    }
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


