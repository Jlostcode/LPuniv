function checkNullAmc() {
    var submit_ct = document.getElementsByName('submit_ct')[0].value;
    if (submit_ct.trim() === "" || submit_ct.trim() === "<p><br></p>") {
        showAlert("과제 내용을 입력해주세요.");
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