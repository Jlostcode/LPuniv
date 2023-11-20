
    // JavaScript 코드
    document.addEventListener('DOMContentLoaded', function () {
    let buttons = document.querySelectorAll('.toggle-details-btn');

    buttons.forEach(function (button) {
    button.addEventListener('click', function () {
    let titleInfo = button.getAttribute('data-occ-title');
    let contentInfo = button.getAttribute('data-occ-content');
    let teachInfo = button.getAttribute('data-occ-teachInfo');
    let kakaoInfo = button.getAttribute('data-occ-kakao');
    let rotInfo = button.getAttribute('data-occ-rot');
    let dateInfo = button.getAttribute('data-occ-date');
    let MdateInfo = button.getAttribute('data-occ-Mdate');
    let occNOInfo = button.getAttribute('data-occ-NO');

    document.getElementById('occ_title_info').innerText = titleInfo;
    let contentInfoElement = document.getElementById('occ_content_info');
    contentInfoElement.innerHTML = parseMarkdown(contentInfo);
    let teachInfoElement = document.getElementById('occ_teachInfo_info');
    teachInfoElement.innerHTML = parseMarkdown(teachInfo);
    document.getElementById('occ_kakao_info').innerText = kakaoInfo;
    document.getElementById('occ_rot_info').innerText = rotInfo;
    document.getElementById('occ_date_info').innerText = dateInfo;
    document.getElementById('occ_Mdate_info').innerText = MdateInfo;
});
});
});
    // Function to parse Markdown and return HTML
    function parseMarkdown(markdown) {
    var parser = new DOMParser();
    var doc = parser.parseFromString('<div>' + markdown + '</div>', 'text/html');
    return doc.body.firstChild.innerHTML;
}
