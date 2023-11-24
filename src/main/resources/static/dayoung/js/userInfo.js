
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('btn').addEventListener('click', function() {
        openModalhehe();
        // window.location.href = '/dayoung/modify'; // 버튼을 누르면 /dayoung/modify 페이지로 이동
    });

    document.getElementById('btn2').addEventListener('click', function() {
        var confirmed = confirm('정말 탈퇴하시겠습니까?'); // 확인 창 띄우기

        if (confirmed) {
            $.ajax({
                url: '/dayoung/deleteDate',
                method: 'POST',
                success: function() {
                    console.log('그리드 데이터가 성공적으로 업데이트되었습니다.');
                    alert('탈퇴되었습니다.');
                    window.location.href = '/logout'; // 탈퇴 후 로그인 페이지로 이동

                },
                error: function(error) {
                    console.error('데이터 업데이트 오류:', error);
                }
            });
            console.log('탈퇴가 진행됩니다.');
        } else {
            // 취소 시 동작 (여기서는 임시로 콘솔에 출력)
            console.log('탈퇴가 취소되었습니다.');
        }
    });
});

function openModalhehe() {
    document.getElementById('myModal').style.display = 'block';
}

function closeModalhehe() {
    document.getElementById('myModal').style.display = 'none';
}


function checkPassword() {
    var currentPassword = document.getElementById('current_password').value;
    console.log(currentPassword)
    if (currentPassword.trim() !== '') {
        // AJAX 요청 - 비밀번호 확인
        $.ajax({
            url: '/dayoung/checkPassword', // 비밀번호 확인을 위한 엔드포인트
            method: 'GET',
            data: { currentPassword: currentPassword }, // 입력된 비밀번호를 서버로 전송
            success: function(response) {
                console.log('response:' + response);
                let result = response.status;
                console.log('result:' + result);
                if (result === 'success') {
                    // 비밀번호가 일치할 때 탈퇴 처리를 진행할 수 있도록 설정
                    window.location.href = '/dayoung/modify';
                    // openCenteredPopup('/dayoung/modify', '_blank', 600, 400);
                } else {
                    alert('비밀번호가 일치하지 않습니다.');
                }
            },
            error: function(error) {
                console.error('비밀번호 확인 중 오류:', error);
            }
        });
    } else {
        console.error('비밀번호를 입력하세요');
    }
}

function confirmDelete() {
    var confirmed = confirm('정말 탈퇴하시겠습니까?'); // 확인 창 띄우기

    if (confirmed) {
        $.ajax({
            url: '/dayoung/deleteDate',
            method: 'POST',
            success: function(response) {
                console.log('탈퇴가 진행되었습니다.');
                alert('탈퇴되었습니다.');
                window.location.href = '/login'; // 탈퇴 후 로그인 페이지로 이동
            },
            error: function(error) {
                console.error('데이터 업데이트 오류:', error);
            }
        });
    } else {
        console.log('탈퇴가 취소되었습니다.');
    }
}


// function openCenteredPopup(url, title, w, h) {
//     // 팝업의 좌표를 계산하여 화면 중앙에 위치시킵니다.
//     const left = screen.width / 2 - w / 2;
//     const top = screen.height / 2 - h / 2;
//
//     // 팝업 창을 엽니다.
//     const popupWindow = window.open(
//         url,
//         title,
//         'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=' +
//         w +
//         ', height=' +
//         h +
//         ', top=' +
//         top +
//         ', left=' +
//         left
//     );
//
//     return popupWindow;
// }