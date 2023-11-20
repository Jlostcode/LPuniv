$(['#editor','#editor2']).summernote({
    placeholder: '제목을 입력해주세요',
    tabsize: 2,
    height: 450

}).catch(error => {
    console.error(error);
});
