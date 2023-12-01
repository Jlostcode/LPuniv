

document.getElementById('file-drop-area').addEventListener('dragover', function (e) {
    e.preventDefault();
    e.stopPropagation();
    this.style.border = '2px dashed #28a745';
});

document.getElementById('file-drop-area').addEventListener('dragleave', function (e) {
    e.preventDefault();
    e.stopPropagation();
    this.style.border = '2px dashed #ccc';
});

document.getElementById('file-drop-area').addEventListener('drop', function (e) {
    e.preventDefault();
    e.stopPropagation();
    this.style.border = '2px dashed #ccc';
    console.log(e);
    // 드래그 앤 드랍한 파일 처리 로직을 여기에 추가
    const fileInput = document.getElementById('file');
    fileInput.files = e.dataTransfer.files;
    handleFiles(e.dataTransfer.files);
});

function openFileExplorer() {
    document.getElementById('file').click();
}

document.getElementById('file').addEventListener('change', function () {
    // 파일 선택 시 처리 로직을 여기에 추가
    console.log(this.files)
    handleFiles(this.files);
});

function handleFiles(files) {
    // 선택된 파일에 대한 처리 로직을 여기에 추가
    console.log(files);

    // 선택된 파일 목록을 표시할 엘리먼트에 출력
    const selectedFileListElement = document.getElementById('selected-file-list');
    selectedFileListElement.innerHTML = ''; // 기존 목록 비우기
    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        console.log('File:', file.name, file.size, file.type);
        for (const file of files) {
            const listItem = document.createElement('li');
            listItem.textContent = file.name;

            // 삭제 버튼 추가
            const deleteButton = document.createElement('button');
            deleteButton.textContent = '삭제';
            deleteButton.addEventListener('click', function () {
                // 선택된 파일 목록에서 파일 삭제
                listItem.remove();
            });

            listItem.appendChild(deleteButton);
            selectedFileListElement.appendChild(listItem);

        }
    }
}

function validateForm() {
    var fileInput = document.getElementById('file');

    if (fileInput.file.length === 0) {
        alert('파일이 선택되지 않았습니다.');
        return false; // 폼 전송을 중단합니다.
    }else {
        return true; // 파일이 선택되었으면 폼을 제출합니다.
    }


}
