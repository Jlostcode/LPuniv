// YouTube API 키
const apiKey = 'AIzaSyArivYMriACjf4a5097KcqUOJLmAuFi0cw';

// YouTube 동영상 ID
const CCIM_videoID = document.querySelector("#board_wrap_videoId").getAttribute("videoId");

// 동영상 플레이어 변수
let player;

// 마지막으로 기록된 시간
let lastRecordedTime = 0;

function onYouTubeIframeAPIReady() {
    player = new YT.Player('youtubeVideo', {
        height: '850',
        width: '500',
        videoId: CCIM_videoID,
        events: {
            'onReady': onPlayerReady,
            'onStateChange': onPlayerStateChange
        }
    });
}

//마지막 재생위치에서로 이동해서 플레이
function onPlayerReady(event) {
    event.target.loadVideoById(CCIM_videoID, lastRecordedTime); // 비디오id, 마지막위치
    event.target.playVideo();// 이벤트 리스너 등록
}

function onPlayerStateChange(event) {
    if (event.data === YT.PlayerState.PLAYING) {
        // 재생 중에 5초 간격으로 현재 재생 시간 기록
        setInterval(() => {
            lastRecordedTime = player.getCurrentTime();
        }, 5000);
    }
}
