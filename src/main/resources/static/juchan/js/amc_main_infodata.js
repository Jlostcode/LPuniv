
// JavaScript 코드
document.addEventListener('DOMContentLoaded', function () {
    let buttons = document.querySelectorAll('.toggle-details-btn');

    buttons.forEach(function (button) {
        button.addEventListener('click', function () {
            let titleInfo = button.getAttribute('data-amc-at');
            let contentInfo = button.getAttribute('data-amc-ac');
            let amcnoInfo = button.getAttribute('data-amc-no');

            document.getElementById('amc_at_info').innerText = titleInfo;
            let contentInfoElement = document.getElementById('amc_ac_info');
            contentInfoElement.innerHTML = parseMarkdown(contentInfo);
// AJAX 요청
            $.ajax({
                type: 'GET',
                url: '/amfi/amfi_info',
                data: { amc_no: amcnoInfo },
                success: function (response) {
                    // 서버에서 받은 데이터를 화면에 표시
                    var amfiInfoContainer = document.getElementById('amfi_info');

                    // 기존에 있던 데이터 삭제 (새로 받은 데이터로 대체)
                    amfiInfoContainer.innerHTML = '';

                    // 데이터를 반복해서 처리
                    response.amfiDto.forEach(function (data, index) {
                        var newDiv = document.createElement('div');
                        newDiv.id = 'amfi_info_' + index;
                        newDiv.innerHTML = '<div id="amfi_og_name_info_' + index + '" style="font-weight: bold">' +
                            '<a href="/amfi/download/' + data.amfi_no + '">' + data.amfi_og_name + '</a>'+ '</div>' +
                            '<div id="amfi_date_' + index + '">' + data.amfi_date + '</div>';
                        amfiInfoContainer.appendChild(newDiv);
                    });
                },
                error: function (error) {
                    console.error('Error fetching data:', error);
                }
            });
        });
    });
});
// Function to parse Markdown and return HTML
function parseMarkdown(markdown) {
    var parser = new DOMParser();
    var doc = parser.parseFromString('<div>' + markdown + '</div>', 'text/html');
    return doc.body.firstChild.innerHTML;
}
