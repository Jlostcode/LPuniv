document.addEventListener('DOMContentLoaded', function () {
    let buttons = document.querySelectorAll('.delete-btn');

    buttons.forEach(function (button) {
        button.addEventListener('click', function () {
            let occnoInfo = button.getAttribute('data-occ-no');
            let ccimnoInfo = button.getAttribute('data-ccim-no');
            let ccimtitleInfo = button.getAttribute('data-ccim-title');

            // SweetAlert을 사용하여 사용자 입력 받기
            Swal.fire({
                title: "'과제명 :" + ccimtitleInfo + "' \n 해당 챕터를 삭제하시겠습니까?",
                icon: 'question',
                showCancelButton: true,
                confirmButtonText: '삭제',
                cancelButtonText: '취소',
                input: 'text',
                inputPlaceholder: '삭제를 원하시면 "' + ccimtitleInfo + '"를 입력하세요.',
                inputValidator: (value) => {
                    // 입력값 검증 함수
                    return value === ccimtitleInfo ? undefined : '입력이 올바르지 않습니다.';
                }
            }).then((result) => {
                if (result.isConfirmed) {
                    // 사용자가 확인 버튼을 눌렀을 경우
                    $.ajax({
                        type: 'GET',
                        url: '/ccim/ccim_delete',
                        data: { occ_no:occnoInfo, ccim_no: ccimnoInfo, ccim_title:ccimtitleInfo },
                        success: function (response) {
                            let occ_no = response.occ_no;
                            let ccim_title = response.ccim_title;

                            // 삭제 완료 메시지
                            Swal.fire({
                                title: "'과제명 :" + ccim_title + "' 해당 과제를 삭제하였습니다.",
                                icon: 'success',
                            }).then((result) => {
                                if (result.isConfirmed) {
                                    // 확인 버튼을 눌렀을 때 페이지 이동
                                    window.location.href = '/ccim' + '?occ_NO=' + occnoInfo;
                                }
                            });

                        },
                        error: function (error) {
                            console.error('Error fetching data:', error);
                        }
                    });
                } else {
                    // 사용자가 취소 버튼을 눌렀을 경우
                    Swal.fire({
                        title: "삭제를 취소했습니다.",
                        icon: 'info',
                    });
                }
            });
        });
    });
});
