// YouTube API 키
 const apiKey = 'AIzaSyArivYMriACjf4a5097KcqUOJLmAuFi0cw';

// YouTube 동영상 ID
const CCIM_videoID = document.getElementById('board_wrap_videoId').getAttribute('videoId');
console.log(CCIM_videoID);

// 동영상 플레이어 변수
let player;

// 마지막으로 기록된 시간
let schs_fnpo = document.querySelector("#board_wrap_fnpo").getAttribute("schsFnpo");

//영상의 총 재생시간 변수
let schs_endpo = document.querySelector("#board_wrap_endpo").getAttribute("schsEnpo");

let ccim_NO = document.querySelector("#board_wrap_ccim_NO").getAttribute("ccimNo");
let occ_NO = document.querySelector("#board_wrap_occ_NO").getAttribute("occNo");

function onYouTubeIframeAPIReady() {
    player = new YT.Player('youtubeVideo', {
        height: '500',
        width: '850',
        videoId: CCIM_videoID,
        events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange,
            'onPlayerPlaybackRateChange': onPlayerPlaybackRateChange
        }
    });
}

//마지막 재생위치에서로 이동해서 플레이
function onPlayerReady(event) {
    event.target.playVideo(); // 플레이어 재생
    player.seekTo(schs_fnpo); // 마지막으로 이동
    RUN_TM = event.target.getDuration(); //재생시간 총 시간에서 5초를 뺌
    schs_endpo = event.target.getDuration(); // 영상의 총 재생 시간을 가져옴
}

// 일정시간간격 반복할 함수(저장용)
let recordInterval;
let finishInterval;

function onPlayerStateChange(event) {
    if (event.data === YT.PlayerState.PLAYING) {
        if (player.getCurrentTime() < schs_fnpo) {
            clearInterval(recordInterval);
        }

        if (event.target.getCurrentTime() > Number(schs_fnpo) + 1) {
            event.target.seekTo(schs_fnpo);
        }

        if (event.target.getCurrentTime() >= RUN_TM) {
            player.pauseVideo();
            player.seekTo(schs_fnpo);
        }
        if (recordInterval) clearInterval(recordInterval);
        if (finishInterval) clearInterval(finishInterval);

        finishPosition();
        finishInterval = setInterval(finishPosition, 1000);

        //5초마다 MAX_POSI와 현재 시간을 저장한다
        if (player.getCurrentTime() > schs_fnpo) {
            recordInterval = setInterval(updatePosition, 5000);
        }
    }

    //일시정지중에는 반복을 멈춘다
    //일시정지한 시간을 기록한다
    if (event.data === YT.PlayerState.PAUSED) {
        clearInterval(recordInterval);
        clearInterval(finishInterval);
        if (recordInterval >= schs_fnpo + 5) {
            if (event.target.getCurrentTime() <= schs_fnpo + 5) {
                updatePosition();
            }
        }
    }
    if (event.data === YT.PlayerState.ENDED) {
        event.target.seekTo(event.target.getDuration() - 1);
        event.target.pauseVideo();
    }

}

// requestPost 함수 정의, 데이터값을 post로 넘기기
function requestPost(schs_fnpo, schs_endpo) {
    schs_fnpo = Math.floor(player.getCurrentTime());
    schs_endpo = Math.floor(player.getDuration());
    ccim_NO = document.querySelector("#board_wrap_ccim_NO").getAttribute("ccimNo");
    occ_NO = document.querySelector("#board_wrap_occ_NO").getAttribute("occNo");
    //해당하는 서버 엔드포인트 URL
    if (schs_fnpo > document.querySelector("#board_wrap_fnpo").getAttribute("schsFnpo")) {
        const url = `/listenLec/savePo?ccim_NO=${ccim_NO}&occ_NO=${occ_NO}&schs_fnpo=${schs_fnpo}&schs_endpo=${schs_endpo}`;
        const data = {
            schs_fnpo: schs_fnpo,
            schs_endpo: schs_endpo
        }
        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // 데이터 형식 지정
            },
            body: JSON.stringify(data) // 객체를 JSON 문자열로 변환하여 전송
        })
            .then(response => // 특정 URL로 리다이렉트
                window.location.href = "/listenLec/lecList?occ_NO=" + occ_NO // 원하는 URL로 "/new-page" 부분을 바꿔주세요
            ) // 응답을 JSON 형식으로 파싱
            .then(data => console.log('Watch time successfully sent to the server:', data)) // 처리된 데이터를 콘솔에 출력
            .catch(error => console.error('Error:', error)); // 오류 처리
    } else {
        window.location.href = "/listenLec/lecList?occ_NO=" + occ_NO;
    }
}

//시간기록
function updatePosition() {
    schs_fnpo = Math.floor(player.getCurrentTime());
    console.log("schs_endpo========================================" + schs_endpo);
    schs_endpo = schs_endpo > schs_fnpo ? schs_endpo : schs_fnpo; // 두개 변수 비교해서 참일시, 거짓일시 리턴 값
    console.log(schs_fnpo);
    console.log(schs_endpo);
}

//영상 끝나기 x초전에 정지 (마지막 추천영상 안뜨기 위한 함수)
function finishPosition() {
    if (Math.floor(player.getCurrentTime()) >= RUN_TM) {
        player.pauseVideo();
    }
}

//재생속도가 변경될 때 1을 초과하면 1로 변경 (재생속도 빠른배속은 막는 함수)
function onPlayerPlaybackRateChange(event) {
    if (event.target.getPlaybackRate() > 1) {
        event.target.setPlaybackRate(1);
    }
}
