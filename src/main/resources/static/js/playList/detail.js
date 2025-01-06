$(function () {

    init();

    function getParameterByName(name) {
        let url = new URL(window.location.href);  // 현재 URL 가져오기
        return url.searchParams.get(name);  // ?id=5 → '5' 반환
    }

    function init() {
        let playListId = getParameterByName('id');  // URL에서 'id' 추출

        if (!playListId) {
            console.error("ID 파라미터가 없습니다.");
            return;
        }

        $.ajax({
            url: '/playList/selectAll/API',
            method: "GET",
            data: { id: playListId },  // id를 API에 전달
            success: (data) => {
                console.log(data)
                $('.playlist-list').html(''); // 기존 테이블 내용 삭제
                if (data.length !== 0) {
                    data.forEach(item => {
                        let id = item['id'];
                        let title = item['title'];
                        let singer = item['singer']; //가수
                        let tjNumber = item['tjNumber']; // th번
                        let kyNumber = item['kyNumber']; // ky번
                        let createTime = item['createTime']; //동록일


                        output(id,title, singer,tjNumber,kyNumber, createTime);
                    });
                } else {
                    let tag = `<div>내용이 없습니다</div>`; // 잘못 닫힌 div 수정
                    $('.playlist-list').append(tag);
                }
            }
        });

    }

    function output(id, title, singer,tjNumber,kyNumber, createTime) {
        let tag = `
                <div  class="${id} playlist-item">
                    <div class="title-artist">
                        <a class="title" href="#">${title}</a>
                        <a class="artist" href="#">${singer}</a>
                    </div>
                    <div class="ky-tj">
                        <p class="ky">금영 : <span>${kyNumber}</span></p>
                        <p class="tj">태진 : <span>${tjNumber}</span></p>
                    </div>
                    <div class="icon-btn add" >
                        <img src="/images/playList/delete.png" alt="삭제아이콘" class="delete" data-seq="${id}">
                    </div>
                </div>
                    
                    `;
        $('.playlist-list').append(tag);

// 삭제 버튼 클릭 시

        let selectedSeqq = null;

        $(".delete").off("click").on("click", function () {
            selectedSeqq = $(this).attr('data-seq');  // 전역 변수에 저장

            $("#admin-user-modal-delete")[0].classList.remove("hidden");
        });
        $("#modalApplyButton-delete").on("click", function() {

            let sendData = { "id": selectedSeqq };

            $.ajax({
                url: '/playList/delete/song',
                method: 'GET',
                data: sendData,  // JSON.stringify 제거
                success: () => {
                    init();
                    $("#admin-user-modal-delete")[0].classList.add("hidden");
                }
            });
        });



    }
    $("#modalCloseButton-delete").on("click", () => {
        $("#admin-user-modal-delete")[0].classList.add("hidden");
    });

})