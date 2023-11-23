// Dropzone.autoDiscover = false;
//
//
// $(document).ready(function () {
//
//     // Dropzone 설정
//     var dropzone = new Dropzone("#dropzoneForm", {
//         url:"/amc/modify",
//         method: "post",
//         autoProcessQueue: false, // 자동으로 보내기. true : 파일 업로드 되자마자 서버로 요청, false : 서버에는 올라가지 않은 상태.
//         paramName: "files",  // 파일 파라미터 이름
//         uploadMultiple: true,  // 다중 파일 업로드 활성화
//         maxFiles: 5,  // 최대 업로드 파일 수
//         maxFilesize: 5,  // 최대 파일 크기 (MB)
//         parallelUploads: 5,  // 병렬 업로드 수
//         dictDefaultMessage: "파일을 여기에 드래그하세요 또는 클릭하세요. 최대 파일 갯수 : 5개",  // 기본 메시지
//         dictRemoveFile: "파일 삭제",  // 파일 삭제 버튼 텍스트
//         addRemoveLinks: true,  // 파일 추가/삭제 링크 표시 여부
//         dictMaxFilesExceeded: "더 이상 파일을 업로드할 수 없습니다.",  // 최대 파일 개수 초과 시 메시지
//         init: function () {
//             this.on("maxfilesexceeded", function(file) {
//                 // 최대 파일 업로드 개수 초과 시 동작
//                 alert("최대 5개까지만 업로드 가능합니다.");
//                 this.removeFile(file); // 초과된 파일 제거
//             });
//             this.on("complete", function (file) {
//                 // 업로드가 완료된 후의 동작
//                 if (this.getUploadingFiles().length === 0 && this.getQueuedFiles().length === 0) {
//                     // 모든 파일 업로드가 완료되었을 때, 추가 동작을 수행하거나 폼 제출 등
//                     this.removeAllFiles(); // 모든 파일 제거
//                 }
//             });
//         }
//     });
// // Dropzone의 파일 업로드와 관련된 이벤트 핸들러 등록
//     dropzone.on("sending", function (file, xhr, formData) {
//         // 파일이 업로드되기 전의 동작
//         // 추가적인 데이터를 formData에 추가할 수 있음
//         console.log(file);
//         if (!formData.has("occ_no")) {
//             formData.append("occ_no", document.getElementsByName("occ_no")[0].value);
//         }
//         if (!formData.has("ccim_no")) {
//             formData.append("ccim_no", document.getElementsByName("ccim_no")[0].value);
//         }
//
//         if (!formData.has("amc_at")) {
//             formData.append("amc_at", document.getElementsByName("amc_at")[0].value);
//         }
//         // 중복으로 amc_ac을 추가하지 않도록 확인
//         if (!formData.has("amc_ac")) {
//             formData.append("amc_ac", document.getElementsByName("amc_ac")[0].value);
//         }
//
//         formData.append("files", file);
//
//             console.log(document.getElementsByName("occ_no")[0].value);
//             console.log(document.getElementsByName("ccim_no")[0].value);
//             console.log(document.getElementsByName("amc_at")[0].value);
//             console.log(document.getElementsByName("amc_ac")[0].value);
//             console.log(document.getElementsByName("amc_no")[0].value);
//
//
//     });
//     dropzone.on("success", function(file, response) {
//         // 업로드가 완료된 후의 동작
//         // 서버에서 전달받은 응답(response)를 확인하여 추가 동작 수행 가능
//
//         // 폼을 서버에 제출
//         $("#modify_form").submit();
//         window.location.href = '/amc' + '?occ_no=' + document.getElementsByName("occ_no")[0].value + '&ccim_no=' + document.getElementsByName("ccim_no")[0].value;
//
//     });
// // 기타 Dropzone 이벤트 등록 가능
//     $("#modify_form").submit(function (event) {
//         event.preventDefault();
//         event.stopPropagation();
//         dropzone.processQueue(); // Dropzone에 파일 업로드 수행
//     });
//
//
//
// });
//
// function submitFormModify() {
//     let modifyForm = document.getElementById("modify_form");
//     let dropzone = Dropzone.forElement("#dropzoneForm");
//     console.log("submitFormModify called");
//     // Dropzone 큐에 파일이 있는지 확인
//     if (dropzone.files.length > 0) {
//         Swal.fire({
//             title: "파일이 등록되었습니다.",
//             text: "파일이 등록되어 있습니다. 과제 등록하시겠습니까?",
//             icon: "info",
//             showCancelButton: true,
//             confirmButtonText: "예",
//             cancelButtonText: "아니오"
//         }).then((result) => {
//             if (result.isConfirmed) {
//                 // 확인을 눌렀을 때의 동작 (예를 들어, 과제 등록)
//                 // 파일이 없으면 추가적인 유효성 검사 수행
//                 let amcAtValue = document.getElementsByName("amc_at")[0].value;
//                 let amcAcValue = document.getElementsByName("amc_ac")[0].value;
//
//                 // amc_at나 amc_ac이 비어있으면 알림 표시 및 제출 막기
//                 if (amcAtValue.trim() === '' && amcAcValue.trim() === '' && amcAcValue.trim() === '<p><br></p>') {
//                     showAlert("과제명과 과제 내용은 필수 입력 항목입니다.");
//                 } else {
//                     // 필수 입력이 모두 완료되었을 때 폼 제출
//                     let dropzone = Dropzone.forElement("#dropzoneForm");
//                     dropzone.processQueue();
//                     modifyForm.submit();
//                 }
//                 console.log("과제 등록");
//                 // 여기에 추가적인 동작을 추가하십시오.
//             } else {
//                 // 아니오를 눌렀을 때의 동작 (예를 들어, 다른 동작 수행)
//                 console.log("사용자가 아니오를 선택했습니다.");
//             }
//         });
//
//     } else {
//         // 파일이 없으면 추가적인 유효성 검사 수행
//         let amcAtValue = document.getElementsByName("amc_at")[0].value;
//         let amcAcValue = document.getElementsByName("amc_ac")[0].value;
//
//         // amc_at나 amc_ac이 비어있으면 알림 표시 및 제출 막기
//         if (amcAtValue.trim() === '' && amcAcValue.trim() === '' && amcAcValue.trim() === "<p><br></p>") {
//             showAlert("과제명과 과제 내용은 필수 입력 항목입니다.");
//         } else {
//             // 필수 입력이 모두 완료되었을 때 폼 제출
//             modifyForm.submit();
//         }
//     }
// }
//
// function deleteAmfi(amfiNO) {
//     $.ajax({
//         type: 'GET',
//         url: '/amfi/amfi_delete',
//         data: { amfi_no: document.getElementsByName("amfi_no")[0].value },
//         success: function (response) {
//             console.log(response); // 서버 응답을 콘솔에 출력
//                 // 여기에서 적절한 방식으로 amfiDto를 사용
//             let amfiTag = "#" + amfiNO;
//                 $(amfiTag).remove();
//                 console.log(amfiNO)
//                 // 삭제 완료 알림
//                 showAlert("삭제 완료");
//         },
//         error: function (error) {
//             console.error('Error fetching data:', error);
//         }
//     });
// }
//
// function showAlert(message) {
//     Swal.fire({
//         icon: 'warning',
//         iconColor:'#12192c',
//         title: '알림',
//         text: message,
//         confirmButtonColor: '#3085d6',
//         confirmButtonText: '확인'
//     });
// }
//


Dropzone.autoDiscover = false;


$(document).ready(function () {

    // Dropzone 설정
    var dropzone = new Dropzone("#dropzoneForm", {
        url:"/amc/modify",
        method: "post",
        autoProcessQueue: false, // 자동으로 보내기. true : 파일 업로드 되자마자 서버로 요청, false : 서버에는 올라가지 않은 상태.
        paramName: "files",  // 파일 파라미터 이름
        uploadMultiple: true,  // 다중 파일 업로드 활성화
        maxFiles: 5,  // 최대 업로드 파일 수
        maxFilesize: 5,  // 최대 파일 크기 (MB)
        parallelUploads: 5,  // 병렬 업로드 수
        dictDefaultMessage: "파일을 여기에 드래그하세요 또는 클릭하세요. 최대 파일 갯수 : 5개",  // 기본 메시지
        dictRemoveFile: "파일 삭제",  // 파일 삭제 버튼 텍스트
        addRemoveLinks: true,  // 파일 추가/삭제 링크 표시 여부
        dictMaxFilesExceeded: "더 이상 파일을 업로드할 수 없습니다.",  // 최대 파일 개수 초과 시 메시지
        init: function () {
            this.on("maxfilesexceeded", function(file) {
                // 최대 파일 업로드 개수 초과 시 동작
                alert("최대 5개까지만 업로드 가능합니다.");
                this.removeFile(file); // 초과된 파일 제거
            });
            this.on("complete", function (file) {
                // 업로드가 완료된 후의 동작
                if (this.getUploadingFiles().length === 0 && this.getQueuedFiles().length === 0) {
                    // 모든 파일 업로드가 완료되었을 때, 추가 동작을 수행하거나 폼 제출 등
                    this.removeAllFiles(); // 모든 파일 제거
                }
            });
        }
    });
// Dropzone의 파일 업로드와 관련된 이벤트 핸들러 등록
    dropzone.on("sending", function (file, xhr, formData) {
        // 파일이 업로드되기 전의 동작
        // 추가적인 데이터를 formData에 추가할 수 있음
        console.log(file);


        if (!formData.has("occ_no")) {
            formData.append("occ_no", document.getElementsByName("occ_no")[0].value);
        }
        if (!formData.has("ccim_no")) {
            formData.append("ccim_no", document.getElementsByName("ccim_no")[0].value);
        }
        if (!formData.has("amc_no")) {
            formData.append("amc_no", document.getElementsByName("amc_no")[0].value);
        }
        if (!formData.has("amc_at")) {
            formData.append("amc_at", document.getElementsByName("amc_at")[0].value);
        }
        // 중복으로 amc_ac을 추가하지 않도록 확인
        if (!formData.has("amc_ac")) {
            formData.append("amc_ac", document.getElementsByName("amc_ac")[0].value);
        }

        formData.append("files", file);

        console.log(document.getElementsByName("occ_no")[0].value);
        console.log(document.getElementsByName("ccim_no")[0].value);
        console.log(document.getElementsByName("amc_at")[0].value);
        console.log(document.getElementsByName("amc_ac")[0].value);

    });
    dropzone.on("success", function(file, response) {
        // 업로드가 완료된 후의 동작
        // 서버에서 전달받은 응답(response)를 확인하여 추가 동작 수행 가능

        // 폼을 서버에 제출
        window.location.href = '/amc' + '?occ_no=' + document.getElementsByName("occ_no")[0].value + '&ccim_no=' + document.getElementsByName("ccim_no")[0].value;

    });
// 기타 Dropzone 이벤트 등록 가능
    $("#modify_form").submit(function (event) {
        event.preventDefault();
        event.stopPropagation();
        dropzone.processQueue(); // Dropzone에 파일 업로드 수행
    });



});

// function submitForm() {
//     if (confirm("파일 업로드가 완료되었습니다. 내용을 확인하셨나요?")) {
//         // 확인 버튼을 눌렀을 때의 동작
//         let dropzone = Dropzone.forElement("#dropzoneForm");
//         dropzone.processQueue();
//     } else {
//         // 취소 버튼을 눌렀을 때의 동작 (예를 들어, 다른 동작 수행)
//         console.log("사용자가 확인하지 않고 취소했습니다.");
//     }
//
//
// }
function submitFormModify() {
    let dropzone = Dropzone.forElement("#dropzoneForm");
    let form = document.querySelector('form');

    if (dropzone.getQueuedFiles().length === 0) {
        // 파일이 없는 경우
        Swal.fire({
            title: "파일이 없습니다.",
            text: "등록된 파일이 없습니다. 과제만 등록하시겠습니까?",
            icon: "info",
            showCancelButton: true,
            confirmButtonText: "예",
            cancelButtonText: "아니오"
        }).then((result) => {
            if (result.isConfirmed) {
                // 확인을 눌렀을 때의 동작 (예를 들어, 과제 등록)
                console.log("과제 등록");
                if (checkNullAmc()){
                    form.submit();
                    // 여기에 추가적인 동작을 추가하십시오.
                }
            } else {
                // 아니오를 눌렀을 때의 동작 (예를 들어, 다른 동작 수행)
                console.log("사용자가 아니오를 선택했습니다.");
            }
        });
    } else {
        // 파일이 있는 경우
        Swal.fire({
            title: "파일이 추가로 등록되었습니다.",
            text: "파일이 추가로 등록되어 있습니다. 과제 등록하시겠습니까?",
            icon: "info",
            showCancelButton: true,
            confirmButtonText: "예",
            cancelButtonText: "아니오"
        }).then((result) => {
            if (result.isConfirmed) {
                // 확인을 눌렀을 때의 동작 (예를 들어, 과제 등록)
                if (checkNullAmc()){
                    dropzone.processQueue();
                    // 여기에 추가적인 동작을 추가하십시오.
                }
                console.log("과제 등록");
                // 여기에 추가적인 동작을 추가하십시오.
            } else {
                // 아니오를 눌렀을 때의 동작 (예를 들어, 다른 동작 수행)
                console.log("사용자가 아니오를 선택했습니다.");
            }
        });
    }
}
function deleteAmfi(amfiNO) {
    $.ajax({
        type: 'GET',
        url: '/amfi/amfi_delete',
        data: { amfi_no: document.getElementsByName("amfi_no")[0].value },
        success: function (response) {
            console.log(response); // 서버 응답을 콘솔에 출력
            // 여기에서 적절한 방식으로 amfiDto를 사용
            let amfiTag = "#" + amfiNO;
            $(amfiTag).remove();
            console.log(amfiNO)
            // 삭제 완료 알림
            alert("삭제 완료");
        },
        error: function (error) {
            console.error('Error fetching data:', error);
        }
    });
}
