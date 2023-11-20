
// JavaScript 코드
document.addEventListener('DOMContentLoaded', function () {
    let buttons = document.querySelectorAll('.toggle-details-btn');

    buttons.forEach(function (button) {
        button.addEventListener('click', function () {
            let titleInfo = button.getAttribute('data-ccim-title');
            let contentInfo = button.getAttribute('data-ccim-content');
            let videoIDInfo = button.getAttribute('data-ccim-videoID');
            let rtInfo = button.getAttribute('data-ccim-rt');

            document.getElementById('ccim_title_info').innerText = titleInfo;
            let contentInfoElement = document.getElementById('ccim_content_info');
            contentInfoElement.innerHTML = parseMarkdown(contentInfo);
            document.getElementById('ccim_videoID_info').innerText = videoIDInfo;
            document.getElementById('ccim_rt_info').innerText = rtInfo;

        });
    });
});
// Function to parse Markdown and return HTML
function parseMarkdown(markdown) {
    var parser = new DOMParser();
    var doc = parser.parseFromString('<div>' + markdown + '</div>', 'text/html');
    return doc.body.firstChild.innerHTML;
}
