$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: "/profile",
        success: function(response) {
            // 서버에서 받은 데이터 중 사용자 이름을 가져와 프로필 카드에 표시
            let userName = response.userName; // 예를 들어, 서버에서 userName으로 보낸다고 가정
            let session = response.session;
            let type = "";
            if (session && session.user_tp) {
                let user_tp = session.user_tp;
                if (user_tp === 1) {
                    type = "수강생";
                    // 프로필 카드의 HTML 요소에 사용자 이름을 삽입
                    console.log(userName);
                    document.getElementById('profile-usertitle-name').innerHTML = userName + ' ' + type + ' 님' + '<br> 환영합니다.';
                } else if (user_tp === 2) {
                    type = "강사";
                    // 프로필 카드의 HTML 요소에 사용자 이름을 삽입
                    console.log(userName);
                    document.getElementById('profile-usertitle-name').innerHTML = userName + ' ' + type + ' 님' + '<br> 환영합니다.';
                } else if (user_tp === 3) {
                    type = "관리자";
                    // 프로필 카드의 HTML 요소에 사용자 이름을 삽입
                    console.log(userName);
                    document.getElementById('profile-usertitle-name').innerHTML = type + ' 님' + '<br> 환영합니다.';
                }
            }

        },
        error: function(xhr, status, error) {
            // 에러 처리
            console.error(error);
        }
    });
});

document.getElementById('logoutButton').addEventListener('click', function() {
    window.location.href = '/logout';
});