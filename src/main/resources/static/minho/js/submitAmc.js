Dropzone.autoDiscover = false;


$(document).ready(function () {

    // Dropzone 설정
    var dropzone = new Dropzone("#dropzoneForm", {
        url:"/submit/submitForm",
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
        formData.append("files", file);
        if (!formData.has("occ_no")) {
            formData.append("user_no", document.getElementsByName("user_no")[0].value);
            formData.append("occ_no", document.getElementsByName("occ_no")[0].value);
            formData.append("amc_no", document.getElementsByName("amc_no")[0].value);

            // 중복으로 amc_at을 추가하지 않도록 확인
            if (!formData.has("submit_ct")) {
                formData.append("submit_ct", document.getElementsByName("submit_ct")[0].value);
            }
        }
    });
    dropzone.on("success", function(file, response) {
        // 업로드가 완료된 후의 동작
        // 서버에서 전달받은 응답(response)를 확인하여 추가 동작 수행 가능

        // 폼을 서버에 제출
        $("#insert_form").submit();
        window.location.href = '/amc/amcView' + '?amc_no=' + document.getElementsByName("amc_no")[0].value;

    });
// 기타 Dropzone 이벤트 등록 가능
    $("#insert_form").submit(function (event) {
        event.preventDefault();
        event.stopPropagation();
        dropzone.processQueue(); // Dropzone에 파일 업로드 수행
    });



});

function submitAmfi() {
    if (confirm("파일 업로드가 완료되었습니다. 내용을 확인하셨나요?")) {
        // 확인 버튼을 눌렀을 때의 동작
        let dropzone = Dropzone.forElement("#dropzoneForm");
        dropzone.processQueue();
    } else {
        // 취소 버튼을 눌렀을 때의 동작 (예를 들어, 다른 동작 수행)
        console.log("사용자가 확인하지 않고 취소했습니다.");
    }


}