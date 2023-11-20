// // DIV에 YouTube Iframe API 플레이어 호출
// function onYouTubeIframeAPIReady() {
//         let videoID = $(this).data('video-id');
//         console.log('dfdfd' + videoId);
//         player = new YT.Player('youtubeVideo', {	// 호출할 위치의 div 컴포넌트 id
//         width: '340',					// 가로 사이즈
//         height: '250',					// 세로 사이즈
//         videoId: videoID,				// 비디오ID
//         playerVars: {
//             disablekb: 1,  				// 키보드 입력 제한
//             rel: 0
//         }
//         // events: {
//         //     'onReady': onPlayerReady,
//         //     'onStateChange': onPlayerStateChange,
//         //     'onPlaybackRateChange': onPlayerPlaybackRateChange
//         // }
//     });
// }
// // 플레이어가 준비되었을 때 실행되는 콜백 함수
// function onPlayerReady(event) {
//     // 플레이어가 준비되면 이곳에 코드를 추가합니다.
//     // 예: event.target.playVideo();
// }
//
// // 플레이어 상태가 변경될 때 실행되는 콜백 함수
// function onPlayerStateChange(event) {
//     // 플레이어 상태가 변경될 때 이곳에 코드를 추가합니다.
//     // 예: 동영상이 종료될 때의 처리
// }

function onYouTubeIframeAPIReady() {
    let cards = document.querySelectorAll('.card');

    cards.forEach(function(card) {
        let videoId = card.querySelector('.youtube-video').getAttribute('data-video-id');
        let cardId = card.querySelector('.youtube-video').getAttribute('id'); // 'card-' 제거
        console.log(videoId);
        console.log(cardId);
        // YouTube 플레이어를 동적으로 생성하고 해당 카드에 연결합니다.
            player = new YT.Player(cardId, {
            width: '340',
            height: '250',
            videoId: videoId,
            playerVars: {
                disablekb: 1,
                rel: 0
            }
        });
    });
}

