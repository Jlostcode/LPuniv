// DIV에 YouTube Iframe API 플레이어 호출
let CCIM_videoID = document.querySelector("#board_wrap_videoId").getAttribute("videoId");

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
            //     'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange,
            //     'onPlaybackRateChange': onPlayerPlaybackRateChange
        }
    });
}

// 플레이어가 준비되었을 때 실행되는 콜백 함수
function onPlayerReady(event) {
    // 플레이어가 준비되면 이곳에 코드를 추가합니다.
    // 예: event.target.playVideo();
}

// 플레이어 상태가 변경될 때 실행되는 콜백 함수
let popupInterval;
let recordInterval;
let finishInterval;
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
function requestPost(url, data) {
    // 실제로 요청을 보내는 코드를 여기에 추가
    // 예: fetch(url, { method: 'POST', body: data })
    fetch("/listenLec/saveFnpo", {
        method: 'POST',
        body: schs_fnpo, schs_endpo
    })
}

//시간 기록
function updatePosition() {
    //영상 마지막 기록 시간
    schs_fnpo = player.getCurrentTime();
    //영상 총 시간
    schs_endpo = schs_endpo > schs_fnpo ? schs_endpo : schs_fnpo; // 두개 변수 비교해서 참일시, 거짓일시 리턴 값

    //진도체크 테이블에 저장
    requestPost("/listenLec/saveFnpo", new URLSearchParams([
        ["schs_fnpo", schs_fnpo],
        ["schs_endpo", schs_endpo]
    ]))
}

function finishPosition(){
    if(Math.floor(player.getCurrentTime()) >= RUN_TM){
        player.pauseVideo();
    }
}