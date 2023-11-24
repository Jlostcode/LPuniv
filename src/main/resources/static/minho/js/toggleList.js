const toggleButtons = document.querySelectorAll('.board_list_Container_toggleButton');
const listContainers = document.querySelectorAll('.board_list_Container');

toggleButtons.forEach((button, index) => {
    button.addEventListener('click', function() {
        if (listContainers[index].style.display === 'none') {
            listContainers[index].style.display = 'block'; // 리스트가 숨겨져 있다면 보이도록 변경
        } else {
            listContainers[index].style.display = 'none'; // 리스트가 보이고 있다면 숨김
        }
    });
});