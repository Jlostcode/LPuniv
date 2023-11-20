$(document).ready(function() { //모달 시작
    $('#myModal').on('show.bs.modal', function () {
        $('#modalContent').load('/message/recmsg');
    });
});

function changePage(pageUrl) { //모달 화면 전환
    $('#modalContent').load(pageUrl);
}

function recDelMsg(msgNo) { // 받은 메시지 삭제
    if (confirm('메시지를 삭제하시겠습니까?')) {
        $.ajax({
            type: 'POST',
            url: '/message/recdel',
            data: { msgNo: msgNo },
            success: function() {
                alert("삭제되었습니다.");
                $('#modalContent').load('/message/recmsg');
            },
            error: function(error) {
                console.error('삭제 중 오류가 발생했습니다.', error);
            }
        });
    }
}

function recDelSearchMsg(msgNo, pageNo, searchInput, searchOp, div) { // 받은 메시지 삭제(검색 후 view에서 삭제)
    let url = `/message/senmsg?searchInput=${searchInput}&searchOp=${searchOp}&div=${div}`;
    if (confirm('메시지를 삭제하시겠습니까?')) {
        $.ajax({
            type: 'POST',
            url: '/message/recdel',
            data: {
                msgNo: msgNo,
                pageNo: pageNo,
                searchInput: searchInput,
                searchOp: searchOp,
                div: div
            },
            success: function() {
                alert("삭제되었습니다.");
                $('#modalContent').load(url);
            },
            error: function(error) {
                console.error('삭제 중 오류가 발생했습니다.', error);
            }
        });
    }
}

function senDelMsg(msgNo) { //보낸 메시지 삭제
    if (confirm('메시지를 삭제하시겠습니까?')) {
        $.ajax({
            type: 'POST',
            url: '/message/sendel',
            data: { msgNo: msgNo },
            success: function() {
                alert("삭제되었습니다.");
                $('#modalContent').load('/message/senmsg');
            },
            error: function(error) {
                console.error('삭제 중 오류가 발생했습니다.', error);
            }
        });
    }
}

function senDelSearchMsg(msgNo, pageNo, searchInput, searchOp, div) { //보낸 메시지 삭제(검색 후 view에서 삭제)
    // let url = `/message/senmsg?searchInput=${searchInput}&searchOp=${searchOp}&div=${div}`;
    if (confirm('메시지를 삭제하시겠습니까?')) {
        $.ajax({
            type: 'POST',
            url: '/message/sendel',
            data: {
                msgNo: msgNo,
                pageNo: pageNo,
                searchInput: searchInput,
                searchOp: searchOp,
                div: div
            },
            success: function() {
                alert("삭제되었습니다.");
                $('#modalContent').load(`/message/senmsg?searchInput=${searchInput}&searchOp=${searchOp}&div=${div}`);
            },
            error: function(error) {
                console.error('삭제 중 오류가 발생했습니다.', error);
            }
        });
    }
}

function recycleRecMsg(msgNo) { //휴지통에서 받은 메시지 복구
    if (confirm('메시지를 복구하시겠습니까?')) {
        $.ajax({
            type: 'POST',
            url: '/message/recyclerecmsg',
            data: { msgNo: msgNo },
            success: function() {
                alert("복구되었습니다.");
                $('#modalContent').load('/message/recycle');
            },
            error: function(error) {
                console.error('삭제 중 오류가 발생했습니다.', error);
            }
        });
    }
}

function recycleSenMsg(msgNo) { //휴지통에서 보낸 메시지 복구
    if (confirm('메시지를 복구하시겠습니까?')) {
        $.ajax({
            type: 'POST',
            url: '/message/recyclesenmsg',
            data: { msgNo: msgNo },
            success: function() {
                alert("복구되었습니다.");
                $('#modalContent').load('/message/recycle');
            },
            error: function(error) {
                console.error('복구 중 오류가 발생했습니다.', error);
            }
        });
    }
}

function recycleDelMsg(msgNo, div) { //휴지통에서 영구 삭제
    if (confirm('메시지를 영구적으로 삭제하시겠습니까?')) {
        $.ajax({
            type: 'POST',
            url: '/message/recycledelmsg',
            data: {
                msgNo: msgNo,
                div: div
            },
            success: function() {
                alert("영구적으로 삭제되었습니다.");
                $('#modalContent').load('/message/recycle');
            },
            error: function(error) {
                console.error('삭제 중 오류가 발생했습니다.', error);
            }
        });
    }
}

function recycleDelSearchMsg(msgNo, pageNo, searchInput, searchOp, div) { //휴지통에서 영구 삭제(검색 후 view에서 삭제)
    let url = `/message/senmsg?searchInput=${searchInput}&searchOp=${searchOp}&div=${div}`;
    if (confirm('메시지를 영구적으로 삭제하시겠습니까?')) {
        $.ajax({
            type: 'POST',
            url: '/message/recycledelmsg',
            data: {
                msgNo: msgNo,
                pageNo: pageNo,
                searchInput: searchInput,
                searchOp: searchOp,
                div: div
            },
            success: function() {
                alert("영구적으로 삭제되었습니다.");
                $('#modalContent').load(url);
            },
            error: function(error) {
                console.error('삭제 중 오류가 발생했습니다.', error);
            }
        });
    }
}

function searchMsg(div) { //검색
    let searchInput = document.getElementById("searchInput").value;
    let searchOp = document.getElementById("searchOp").value;

    if (div === 'rec') {
        let url = `/message/recmsg?searchInput=${searchInput}&searchOp=${searchOp}&div=${div}`;
        $.ajax({
            type: "GET",
            url: "/message/recmsg",
            data: {
                searchInput: searchInput,
                searchOp: searchOp,
                div: div
            },
            success: function () {
                $('#modalContent').load(url);
            },
            error: function (error) {
                console.log("검색 중 오류가 발생했습니다.", error);
            }
        });
    } else if (div === 'sen') {
        let url = `/message/senmsg?searchInput=${searchInput}&searchOp=${searchOp}&div=${div}`;
        $.ajax({
            type: "GET",
            url: "/message/senmsg",
            data: {
                searchInput: searchInput,
                searchOp: searchOp,
                div: div
            },
            success: function () {
                $('#modalContent').load(url);
            },
            error: function (error) {
                console.log("검색 중 오류가 발생했습니다.", error);
            }
        });
    } else if (div === 'recycle') {
        let url = `/message/recycle?searchInput=${searchInput}&searchOp=${searchOp}&div=${div}`;
        $.ajax({
            type: "GET",
            url: "/message/recycle",
            data: {
                searchInput: searchInput,
                searchOp: searchOp,
                div: div
            },
            success: function () {
                $('#modalContent').load(url);
            },
            error: function (error) {
                console.log("검색 중 오류가 발생했습니다.", error);
            }
        });
    }
}

$(document).ready(function () { //메시지 작성
    $('#submit').click(function (e) {
        e.preventDefault();
        let senderNo = $('input[name="sen-no"]').val();
        let senderNm = $('input[name="sen-nm"]').val();

        let selectedOptions = $('select[name="rec-select"] option:selected');
        let receivers = [];

        selectedOptions.each(function () {
            let values = $(this).val().split(':');
            let receiverNm = values[0];
            let str = values[1];
            let receiverNo = parseInt(str);

            receivers.push({
                receiverNo: receiverNo,
                receiverNm: receiverNm
            });
        });

        let title = $('input[name="title"]').val();
        let content = $('textarea[name="content"]').val();
        let error = false;
        console.log(receivers[0]);
        if(!title || !content){
            alert("제목과 내용을 입력해주세요.");
            error = true;
        }
        if(receivers.length === 0){
            alert("수신자를 한 명 이상 선택해주세요");
            error = true;
        }

        if(!error){
            $.ajax({
                type: 'POST',
                url: '/message/msgwrite',
                contentType: 'application/json',
                dataType: 'text',
                data: JSON.stringify({
                    senderNo: senderNo,
                    senderNm: senderNm,
                    receivers: receivers,
                    title: title,
                    content: content
                }),
                success: function () {
                    alert("작성 성공");
                    $('#modalContent').load('/message/senmsg');
                },
                error: function (xhr, status, error) {
                    console.log(xhr.responseText);
                    alert("오류 발생: " + error);
                }
            });
        }
    });
});

$(document).ready(function () { //메시지 수정
    $('#submit1').click(function (e) {
        e.preventDefault();
        let msgNo = $('input[name="msg-no"]').val();
        let title = $('input[name="title"]').val();
        let content = $('textarea[name="content"]').val();
        let error = false;

        if(!title || !content){
            alert("제목과 내용을 입력해주세요.");
            error = true;
        }

        if(!error){
            $.ajax({
                type: 'POST',
                url: '/message/msgupdate',
                data: {
                    msgNo: msgNo,
                    title: title,
                    content: content
                },
                success: function () {
                    alert("수정 성공");
                    $('#modalContent').load('/message/senmsg');
                },
                error: function () {
                    alert("오류")
                }
            });
        }
    });
});

// document.addEventListener("DOMContentLoaded", function () {
//     setInterval(updateMessageCount, 3000);
// });

// document.addEventListener("DOMContentLoaded", function () {// 현재 URL이 특정 페이지인 경우에만 setInterval 실행
//     if (window.location.pathname === "/message/recmsg") {
//         setInterval(updateMessageCount, 3000);
//     }
// });

function updateMessageCount() {
    fetch("/message/recmsg")
        .then(response => response.text())
        .then(data => {
            const match = data.match(/<span id="msg-cnt"[^>]*>(.*?)<\/span>/);
            if (match && match[1]) {
                const msgCntValue = match[1].trim();
                document.getElementById("msg-cnt").innerText = msgCntValue;
            } else {
                console.error("Error: Unable to find msgCnt in HTML");
            }
        })
        .catch(error => console.error("Error fetching message count:", error));
}

function filterRecipients() {
    const searchValue = document.getElementById('rec-search').value;
    const selectElement = document.getElementById('rec-select');
    const options = selectElement.getElementsByTagName('option');

    let hasMatchingOption = false;

    for (let i = 0; i < options.length; i++) {
        if (options[i].text.includes(searchValue)) {
            options[i].style.display = '';
            hasMatchingOption = true;
        } else {
            options[i].style.display = 'none';
        }
    }
}

function updateInputValue() {
    const selectElement = document.getElementById('rec-select');
    const selectedOptions = Array.from(selectElement.selectedOptions);

    let currentInputValue = document.getElementById('rec-search').value;
    console.log(currentInputValue);
    selectedOptions.forEach(option => {
        const optionText = option.text;

        if (!currentInputValue.includes(optionText)) {
            currentInputValue += optionText + ', ';
            console.log(currentInputValue);
        } else {
            currentInputValue = currentInputValue.replace(optionText + ', ', '');
            console.log(currentInputValue);
        }
    });

    document.getElementById('rec-search').value = currentInputValue;
}