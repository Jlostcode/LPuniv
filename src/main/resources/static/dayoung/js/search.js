function serchList(){
    let selectOption = document.getElementById("selectOption").value;
    let searchFind = document.getElementById("search").value; // 수정된 부분
    console.log(searchFind);
    console.log(selectOption);
    let error = false;

    if(!searchFind){
        error=true;
        alert("검색어를 입력해주세요.")
    }

    if(!error){
        $.ajax({
            type: 'GET',
            url: '/dayoung/list',
            data: {
                selectOption: selectOption,
                serchFind: searchFind, // 수정된 변수명
            },
            success: function(data) {
                window.location.href="/dayoung/list";
            },
            error: function(xhr, status, error) {
                console.error('Error:', error);
            }
        });
    }
}
