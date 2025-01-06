$(function () {

    init();

    function init(){
        $.ajax({
            url: '/rank/all',
            method: "GET",
            success: (data) => {
                $('.playlist-list').html(''); // 기존 테이블 내용 삭제
                if (data.length !== 0) {
                    data.forEach(item => {
                        let id = item['id'];
                        let title = item['title'];
                        let singer = item['singer'];
                        let tjNumber = item['tjNumber'];
                        let kyNumber = item['kyNumber'];

                        output(id, title,singer,tjNumber, kyNumber);
                    });
                } else {
                    let tag = `<div>내용이 없습니다</div>`; // 잘못 닫힌 div 수정
                    $('.playlist-list').append(tag);
                }
            }
        });

    }

})