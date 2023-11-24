document.getElementById('loginForm').addEventListener('submit', function(event) {
    var loginId = document.getElementById('user_loginId').value;
    var password = document.getElementById('user_passwd').value;

    if (loginId === '' && password === '') {
        alert('아이디와 비밀번호를 입력해주세요.');
        event.preventDefault();
    } else if (loginId === '') {
        alert('아이디를 입력해주세요.');
        event.preventDefault();
    } else if (password === '') {
        alert('비밀번호를 입력해주세요.');
        event.preventDefault();
    }
});