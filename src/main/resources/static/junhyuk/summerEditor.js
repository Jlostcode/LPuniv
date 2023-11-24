
    var dropzone = document.getElementById('dropzone');
    var fileInput = document.getElementById('fileInput');
    var fileList = document.getElementById('fileList');
    var uploadedFiles = [];

    dropzone.addEventListener('click', function() {
    fileInput.click();
});

    fileInput.addEventListener('change', function(event) {
    handleFiles(event.target.files);
});

    dropzone.addEventListener('dragover', function(event) {
    event.preventDefault();
    event.stopPropagation();
    dropzone.style.border = "2px solid #000";
});

    dropzone.addEventListener('dragleave', function(event) {
    dropzone.style.border = "2px dashed #ccc";
});

    dropzone.addEventListener('drop', function(event) {
    event.preventDefault();
    event.stopPropagation();
    dropzone.style.border = "2px dashed #ccc";
    handleFiles(event.dataTransfer.files);
});

    function handleFiles(newFiles) {
    Array.from(newFiles).forEach(function(file) {
        uploadedFiles.push(file);
    });
    updateFileList();
}

    function updateFileList() {

    fileList.style.display = uploadedFiles.length > 0 ? 'block' : 'none';
    fileList.innerHTML = '';
    uploadedFiles.forEach(function(file, index) {
    var fileItem = document.createElement('div');
    fileItem.className = 'file-item';
    fileItem.textContent = file.name;

    var deleteBtn = document.createElement('span');
    deleteBtn.className = 'delete-btn';
    deleteBtn.textContent = 'X';
    deleteBtn.onclick = function() {
    uploadedFiles.splice(index, 1);
    updateFileList();
};

    fileItem.appendChild(deleteBtn);
    fileList.appendChild(fileItem);
});
}


    /*에디터*/

    // 에디터 초기화
    $('#edittor').summernote({
        placeholder: '내용을 입력해주세요',
        tabsize: 2,
        height: 450
    });
function clickNull() {
    // 폼 제출 이벤트 핸들러
    document.getElementById('createForm').addEventListener('submit', function(e) {
    var input_title = document.getElementById('input_title').value.trim();
    var occ_content = $('#edittor').summernote('code').trim();

    var errorMessages = [];
    if (!input_title) {
    errorMessages.push('제목을 입력하세요.');
}
    if (!occ_content || occ_content === '<p><br></p>') {
    errorMessages.push('내용을 입력하세요.');
}

    if (errorMessages.length > 0) {
    e.preventDefault();
    alert(errorMessages.join('\n'));
}

});
}
