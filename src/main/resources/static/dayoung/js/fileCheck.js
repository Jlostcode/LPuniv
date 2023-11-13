function validateForm() {
    var fileInput = document.getElementById('file');

    if (fileInput.files.length === 0) {
        alert('파일이 선택되지 않았습니다.');
        return false; // 폼 전송을 중단합니다.
    }

    return true; // 파일이 선택되었으면 폼을 제출합니다.
}