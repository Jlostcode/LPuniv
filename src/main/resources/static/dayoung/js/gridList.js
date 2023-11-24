let gridData = null;
// 사용자 데이터 가져오기
$(document).ready(function() {
    var grid1 = new tui.Grid({
        el: document.getElementById('grid1'),
        data:gridData,
        rowHeaders: ['checkbox'],
        bodyHeight: 500,
        columns: [
            // 칼럼 정의
            { header: '타입',
                name: 'user_tp',
                formatter: 'listItemText',
                editor: {
                    type: 'select',
                    options: {
                        listItems: [
                            { text: '수강생', value: 1 },
                            { text: '강사', value: 2 },
                            { text: '관리자', value: 3 }
                        ]
                    }
                },
                filter:'text'
            },
            { header: '이름',
                name: 'user_nm',
                filter:'text'},

            { header: '전화번호',
                name: 'user_tel',
                filter:'text',
                editor: 'text',
            },
            { header: '이메일주소',
                name: 'user_email',
                filter:'text',
                editor: 'text'},
            { header: '생년월일',
                name: 'user_brdt',
                filter:'text',
                editor: 'text'},
            { header: '성별',
                name: 'user_gen',
                filter:'text',
                formatter: 'listItemText',
                editor: {
                    type: 'select',
                    options: {
                        listItems: [
                            { text: '남성', value: 1 },
                            { text: '여성', value: 2 }
                        ]
                    }
                }
            },
            { header: '등록일',
                name: 'user_signdate',
                filter:'text',
                editor: 'text'
            },
            { header: '탈퇴일',
                name: 'user_deletedate',
                filter:'text',
                editor: 'text'}

            // 나머지 칼럼들...
        ],
        draggable: true
    });
    $.ajax({
        url: '/dayoung/users', // 위에서 정의한 백엔드 엔드포인트
        method: 'GET',
        dataType : "JSON",
        contentType : "application/json; charset=UTF-8",
        success: function(response) {
            gridData= response; // 그리드에 데이터 설정
            grid1.resetData(gridData); // 받은 데이터를 그리드에 설정

            $('#password').on('click',function (){
                const changePassword = grid1.getCheckedRows();
                console.log(changePassword);
                if(changePassword.length ===0){
                    alert('선택된 행이 없습니다.');
                    return;
                }
                $.ajax({
                    url: '/dayoung/changePassword',
                    method: 'POST',
                    data: JSON.stringify(changePassword),
                    contentType: 'application/json',
                    success: function(response) {
                        console.log('그리드 데이터가 성공적으로 업데이트되었습니다.');
                        alert('비밀번호가 변경되었습니다. ' +
                            '변경된 비밀번호는 1111 입니다.');
                        location.reload();
                    },
                    error: function(error) {
                        console.error('데이터 업데이트 오류:', error);
                    }
                });
            })
            $('#delete').on('click',function (){
                const deleteUser = grid1.getCheckedRows();
                console.log(deleteUser);
                if(deleteUser.length ===0){
                    alert('선택된 행이 없습니다.');
                    return;
                }
                $.ajax({
                    url: '/dayoung/deleteUser',
                    method: 'POST',
                    data: JSON.stringify(deleteUser),
                    contentType: 'application/json',
                    success: function(response) {
                        alert('삭제되었습니다.');
                        location.reload();
                    },
                    error: function(error) {
                        console.error('데이터 업데이트 오류:', error);
                    }
                });
            })


            $('#btn').on('click', function() {
                const checkedRows = grid1.getCheckedRows();
                console.log(checkedRows);
                if (checkedRows.length === 0) {
                    alert('선택된 행이 없습니다.');
                    return;
                }

                // const dataArray = allData.map(row=> {
                //     return{
                //         user_tp:row.user_tp,
                //         user_nm:row.user_nm,
                //         user_tel:row.user_tel,
                //         user_email:row.user_email,
                //         user_gen:row.user_gen,
                //         user_signdate:row.user_signdate,
                //         user_deletedate:row.user_deletedate
                //     }
                // })
                console.log(JSON.stringify(checkedRows));
                // 서버로 전체 데이터 전송
                $.ajax({
                    url: '/dayoung/update',
                    method: 'POST',
                    data: JSON.stringify(checkedRows),
                    contentType: 'application/json',
                    success: function(response) {
                        console.log('그리드 데이터가 성공적으로 업데이트되었습니다.');
                        alert('저장되었습니다.');
                        // location.reload();
                        window.location.href = '/dayoung/list';
                    },
                    error: function(error) {
                        console.error('데이터 업데이트 오류:', error);
                    }
                });
            });
        },
        error: function(error) {
            console.error('Error fetching data:', error);
        }
    });
});