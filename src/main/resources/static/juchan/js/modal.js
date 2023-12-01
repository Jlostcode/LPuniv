// 다른 HTML 파일에서 모달을 열기
function openModalFromExternalHTML() {
    $.ajax({
        url: '/amc/modal',  // 다른 HTML 파일의 경로
        method: 'GET',
        success: function (data) {
            // 다른 HTML 파일의 내용을 가져옴
           $('#modalContainer').html(data);

            // 모달 열기
            openModal();
        },
        error: function () {
            console.error('Failed to load external HTML.');
        }
    });
}

// 모달 열기
function penModal() {
    document.getElementById('myModal').style.display = 'block';
}

// 모달 닫기
function loseModal() {
    document.getElementById('myModal').style.display = 'none';
}

// 모달 외부 클릭 시 닫기
window.onclick = function (event) {
    let modal = document.getElementById('myModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
};