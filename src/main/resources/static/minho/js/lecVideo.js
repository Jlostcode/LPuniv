// DIV에 YouTube Iframe API 플레이어 호출
let CCIM_videoID = document.querySelector("#board_wrap_videoId").getAttribute("videoId");
//api key
const apiKey = 'AIzaSyArivYMriACjf4a5097KcqUOJLmAuFi0cw'
//채널아이디
// Channel
const channels
    = `https://www.googleapis.com/youtube/v3/channels?key=${apiKey}&id=${CCIM_videoID}&part=snippet,contentDetails,statistics`;

let schs_fnpo = 0;

function onYouTubeIframeAPIReady() {
    player = new YT.Player('youtubeVideo', {   // 호출할 위치의 div 컴포넌트 id
        width: '850',               // 가로 사이즈
        height: '500',               // 세로 사이즈
        videoId: CCIM_videoID,            // 비디오ID
        playerVars: {
            disablekb: 1,              // 키보드 입력 제한
            rel: 0
        },
        events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange,
            //     'onPlaybackRateChange': onPlayerPlaybackRateChange
        }
    });
}

// 플레이어가 준비되었을 때 실행되는 콜백 함수
function onPlayerReady(event) {
    event.target.loadVideoById(CCIM_videoID, schs_fnpo); // 비디오id, 마지막위치
    event.target.playVideo();// 이벤트 리스너 등록
}

// 플레이어 상태가 변경될 때 실행되는 콜백 함수
let popupInterval;
let recordInterval;
let finishInterval;

player.getDuration = function () {

};

function onPlayerStateChange(event) {
    RUN_TM = player.getDuration() - 5;
    schs_endpo = YT.PlayerState.ENDED;
    schs_endpo = schs_endpo === null ? endPo : schs_endpo;

    if (event.target.getCurrentTime() > Number(schs_endpo) + 1) {
        event.target.seekTo(schs_endpo);


        if (event.target.getCurrentTime() >= RUN_TM) {
            player.pauseVideo();
            player.seekTo(schs_fnpo);
        }

        if (popupInterval) clearInterval(popupInterval);
        if (recordInterval) clearInterval(recordInterval);
        if (finishInterval) clearInterval(finishInterval);

        finishPosition();
        finishInterval = setInterval(finishPosition, 1000);

        //5초마다 MAX_POSI와 현재 시간을 저장한다
        recordInterval = setInterval(updatePosition, 5000);
    }

//영상이 끝나더라도 이전으로 되돌려서 일시정지. 사용자가 영상의 끝으로 이동하는것을 따로 막기위해 필요하다
    if (event.data == YT.PlayerState.ENDED) {
        event.target.seekTo(event.target.getDuration() - 1);
        event.target.pauseVideo();
    }
}

// requestPost 함수 정의
schs_endpo = 1;

function requestPost(url, data) {
    // 실제로 요청을 보내는 코드를 여기에 추가
    // 예: fetch(url, { method: 'POST', body: data })

    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json' // 데이터 형식 지정
        },
        body: JSON.stringify(data) // 객체를 JSON 문자열로 변환하여 전송
    })
        .then(response => response.json()) // 응답을 JSON 형식으로 파싱
        .then(data => console.log(data)) // 처리된 데이터를 콘솔에 출력
        .catch(error => console.error('Error:', error)); // 오류 처리
}


//시간 기록
function updatePosition() {
    ccim_NO = document.querySelector("#board_wrap_ccim_NO").getAttribute("ccimNo");
    occ_NO = document.querySelector("#board_wrap_occ_NO").getAttribute("occNo");
    console.log(ccim_NO);
    console.log(occ_NO);
    //영상 마지막 기록 시간
    schs_fnpo = player.getCurrentTime();
    //영상 총 시간
    schs_endpo = schs_endpo > schs_fnpo ? schs_endpo : schs_fnpo; // 두개 변수 비교해서 참일시, 거짓일시 리턴 값

    const requestData = {
        schs_fnpo: schs_fnpo,
        schs_endpo: schs_endpo,
        ccim_NO: ccim_NO,
        occ_NO: occ_NO
    };
    console.log(requestData);

    //진도체크 테이블에 저장
    requestPost(`/listenLec/lecVideo?ccim_NO=${ccim_NO}&occ_NO=${occ_NO}`, requestData);
    // ([
    //     ["schs_fnpo", schs_fnpo],
    //     ["schs_endpo", schs_endpo]
    // ]))
    console.log(schs_fnpo);
    console.log(schs_endpo);
}

function finishPosition() {
    if (Math.floor(player.getCurrentTime()) >= RUN_TM) {
        player.pauseVideo();
    }
}