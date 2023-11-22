function checkNullAmc() {
    var amc_at = document.getElementsByName('amc_at')[0].value;
    var amc_ac = document.getElementsByName('amc_ac')[0].value;
    console.log(amc_at);

    if (amc_at.trim() === "") {
        showAlert("과제 제목을 입력해주세요.");
        return false;
    }

    if (amc_ac.trim() === "" || amc_ac.trim() === "<p><br></p>") {
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