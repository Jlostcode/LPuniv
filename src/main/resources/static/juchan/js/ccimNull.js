function checkNullCcim() {
    var ccim_title = document.getElementsByName('ccim_title')[0].value;
    var ccim_content = document.getElementsByName('ccim_content')[0].value;
    var ccim_videoID = document.getElementsByName('ccim_videoID')[0].value;
    var ccim_rt = document.getElementsByName('ccim_rt')[0].value;
    console.log(ccim_content);

    if (ccim_title.trim() === "") {
        showAlert("챕터명을 입력해주세요.");
        return false;
    }

    if (ccim_content.trim() === "" || ccim_content.trim() === "<p><br></p>") {
        showAlert("강의 내용을 입력해주세요.");
        return false;
    }

    if (ccim_videoID.trim() === "") {
        showAlert("챕터 영상 ID를 입력해주세요.");
        return false;
    }
    if (ccim_rt.trim() === "") {
        showAlert("영상 시간 확인을 클릭해주세요.");
        return false;
    }
    getVideoDuration();
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