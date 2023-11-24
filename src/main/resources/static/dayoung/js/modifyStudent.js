

function closeModal() {
    document.getElementById('myModal').style.display = 'none';
}

function openMainModal() {
    document.getElementById('mainModal').style.display = 'block';
}

function closeMainModal() {
    document.getElementById('mainModal').style.display = 'none';
}


function changePassword() {
    var new_password = document.getElementById('new_password').value;
    var confirm_password = document.getElementById('confirm_password').value;

    if ( new_password === ''||new_password===null || confirm_password=== null|| confirm_password === '') {
        alert('수정할 정보를 입력해주세요');
        return;
    }else if (new_password !=confirm_password){
        alert('비밀번호 정보가 일치하지 않습니다.');
        return;
        // }else if(new_password==confirm_password){
        //     var data = {
        //         new_password: new_password
        //     };
    }else {
        $.ajax({
            url: '/dayoung/modifyPw', // 비밀번호 확인을 위한 엔드포인트
            method: 'GET',
            data: { newPassword: new_password }, // 입력된 비밀번호를 서버로 전송
            success: function(response) {
                console.log('response:' + response);
                let result = response.status;
                console.log('result:' + result);
                if (result === 'success') {
                    // 비밀번호가 일치할 때 탈퇴 처리를 진행할 수 있도록 설정
                    window.location.href = '/dayoung/modify';

                } else {
                    alert('비밀번호가 일치하지 않습니다.');
                }
            },
            error: function(error) {
                console.error('비밀번호 확인 중 오류:', error);
            }
        });
    }


    // 요청 보내기var xhr = new XMLHttpRequest();
    // xhr.open('GET', '/dayoung/modifyPw', true);
    // xhr.setRequestHeader('Content-Type', 'application/json');
    //
    // xhr.onload = function () {
    //     if (xhr.status === 200) {
    //         alert('비밀번호 변경 성공');
    //         closeModal(); // 모달 닫기 등 추가 작업 수행
    //     } else {
    //         alert('비밀번호 변경 실패');
    //     }
    // };
    //
    // var data = {
    //     new_password: new_password
    // };
    // xhr.send(JSON.stringify(data));
}

function validateForm() {
    var telValue = document.getElementById('user_tel').value;
    if (telValue === '') {
        alert('수정할 정보를 입력해주세요');
        return false;
    }
    return true;
}

function openModal() {
    Swal.fire({
        title: '비밀번호 변경',
        html: `
            <div>
                <input type="password" class="swal2-input" id="new_password" name="new_password" placeholder="새 비밀번호"/>
                <input type="password" class="swal2-input" id="confirm_password" name="confirm_password" placeholder="비밀번호 재확인"/>
            </div>
        `,
        showCancelButton: true,
        confirmButtonText: '확인',
        cancelButtonText: '취소',
        inputValidator: (value) => {
            const newPassword = document.getElementById('new_password').value;
            const confirmPassword = document.getElementById('confirm_password').value;
            console.log('새 비밀번호:', newPassword);
            console.log('비밀번호 재확인:', confirmPassword);
            if (!newPassword || !confirmPassword) {
                return '새 비밀번호와 비밀번호 확인을 모두 입력하세요';
            }

            if (newPassword !== confirmPassword) {
                return '비밀번호가 일치하지 않습니다';
            }
        },
    }).then((result) => {
        if (result.isConfirmed) {
            const newPassword = document.getElementById('new_password').value;
            const confirmPassword = document.getElementById('confirm_password').value;

            console.log('새 비밀번호:', newPassword);
            console.log('비밀번호 재확인:', confirmPassword);

            if (!newPassword || !confirmPassword) {
                showAlert('수정할 정보를 입력해주세요');
                return;
            } else if (newPassword !== confirmPassword) {
                showAlert('비밀번호 정보가 일치하지 않습니다.');
                return;
            } else {
                // 변경할 비밀번호 정보가 일치하는 경우
                showAlert('비밀번호가 정상적으로 변경되었습니다.')
                changePassword(); // 비밀번호 변경 함수 호출
            }
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