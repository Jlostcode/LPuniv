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

function recycleRecMsg(msgNo) { //휴지통에서 받은 메시지 복구
    if (confirm('메시지를 복구하시겠습니까?')) {
        $.ajax({
            type: 'POST',
            url: '/message/recyclerecmsg',
            data: { msgNo: msgNo },
            success: function() {
                alert("삭제되었습니다.");
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

        let selectedOption = $('select[name="rec-select"] option:selected');
        let values = selectedOption.val().split(':');
        let receiverNm = values[0];
        let str = values[1];
        let receiverNo = parseInt(str);

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
                url: '/message/msgwrite',
                data: {
                    senderNo: senderNo,
                    senderNm: senderNm,
                    receiverNo: receiverNo,
                    receiverNm: receiverNm,
                    title: title,
                    content: content
                },
                success: function () {
                    alert("작성 성공");
                    $('#modalContent').load('/message/senmsg');
                },
                error: function () {
                    alert("오류")
                }
            });
        }
    });
});

$(document).ready(function () { //메시지 작성
    $('#submit1').click(function (e) {
        e.preventDefault();
        let msgNo = $('input[name="msg-no"]').val();
        console.log(msgNo);
        let title = $('input[name="title"]').val();
        console.log(title);
        let content = $('textarea[name="content"]').val();
        console.log(content);
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