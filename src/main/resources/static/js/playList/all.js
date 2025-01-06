$(function () {

    init();

    function init(){
        $.ajax({
            url: '/playList/selectAll',
            method: "GET",
            success: (data) => {
                $('.playlist-list').html(''); // 기존 테이블 내용 삭제
                if (data.length !== 0) {
                    data.forEach(item => {
                        let id = item['id'];
                        let userId = item['userId'];
                        let playListName = item['playListName'];
                        let createTime = item['createTime'];
                        let isLiked = item['liked'];

                        output(playListName, createTime,id,userId, isLiked);
                    });
                } else {
                    let tag = `<div>내용이 없습니다</div>`; // 잘못 닫힌 div 수정
                    $('.playlist-list').append(tag);
                }
            }
        });

    }

    function output(playListName, createTime,id,userId, isLiked) {
        console.log(isLiked)
        if(isLiked === true){
            let url = `/playList/intoplaylist?id=${id}`;
            let tag = `
                <div class="${id} playlist-item">
                    <div><a href="${url}">${playListName}</a></div>
                    <div class="icon-btn active">
                        <img src="/images/playList/like_btn.png" alt="삭제아이콘" data-seq="${id}" class="like-check">
                    </div>
                    <div class="icon-btn like">
                        <img class="namech " src="/images/playList/edit.png" alt="이름 수정" 
                                data-seq="${id}" data-name="${playListName}">
                    </div>
                    <div class="icon-btn add">
                        <img src="/images/playList/delete.png" alt="삭제아이콘" data-seq="${id}">
                    </div>
                </div>
            `
            $('.playlist-list').append(tag);

        } else{
            let url = `/playList/intoplaylist?id=${id}`;
            let tag = `
                <div class="${id} playlist-item">
                    <div><a href="${url}">${playListName}</a></div>
                    <div class="icon-btn">
                        <img src="/images/playList/like_btn.png" alt="삭제아이콘" data-seq="${id}" class="like-check">
                    </div>
                    <div class="icon-btn like">
                        <img class="namech " src="/images/playList/edit.png" alt="이름 수정" 
                                data-seq="${id}" data-name="${playListName}">
                    </div>
                    <div class="icon-btn add">
                        <img src="/images/playList/delete.png" alt="삭제아이콘" data-seq="${id}"
                                class="delete">
                    </div>
                </div>
            `
            $('.playlist-list').append(tag);
        }


// 삭제 버튼 클릭 시

        let selectedSeqq = null;

        $(".delete").off("click").on("click", function () {
            selectedSeqq = $(this).attr('data-seq');  // 전역 변수에 저장

            $("#modal-text-delete").val(playListName);
            $("#admin-user-modal-delete")[0].classList.remove("hidden");
        });



        $("#modalApplyButton-delete").on("click", function() {
            // 버튼의 data-seq 속성에서 seq 값을 가져옵니다.
            let sendData = { "id": selectedSeqq };

            $.ajax({
                url: '/playList/delete',
                method: 'GET',
                data: sendData,  // JSON.stringify 제거
                success: () => {
                    init();
                    $("#admin-user-modal-delete")[0].classList.add("hidden");
                }
            });
        });

        let selectedSeq = null;  // 전역 변수 선언

        $(".namech").off("click").on("click", function () {
            selectedSeq = $(this).attr('data-seq');  // 전역 변수에 저장
            let playListName = $(this).attr('data-name');

            $("#modal-text-add").val(playListName);
            $("#admin-user-modal-add")[0].classList.remove("hidden");
        });

        $("#modalApplyButton-add").off("click").on("click", function () {
            let updatedName = $("#modal-text-add").val();
            let sendData = {
                "id": selectedSeq,  // 전역 변수 사용
                "playListName": updatedName
            };

            $.ajax({
                url: '/playList/namech',
                method: 'POST',
                contentType: "application/json",  // Content-Type을 JSON으로 설정
                data: JSON.stringify(sendData),
                success: () => {
                    init();
                    $("#admin-user-modal-add")[0].classList.add("hidden");
                    $("#modal-text-add").val("");  // 모달 열릴 때 초기화
                }
            });
        });

        $(".like-check").off("click").on("click", function() {
            let seq = $(this).attr('data-seq');
            let sendData = { "id": seq };

            $.ajax({
                url: '/playList/likeCheck',
                method: 'GET',
                data: sendData,
                success: () => {
                    init();  // 성공 시 테이블 리로드
                }
            });
        });

    }

    $("#modalCloseButton-delete").on("click", () => {
        $("#admin-user-modal-delete")[0].classList.add("hidden");
    });

    $("#modalCloseButton-add").on("click", () => {
        $("#admin-user-modal-add")[0].classList.add("hidden");
    });


    $("#add-btn").on("click", () => {
        $("#admin-user-modal")[0].classList.remove("hidden");
    });

    $("#modalCloseButton").on("click", ()=>{
        $("#admin-user-modal")[0].classList.add("hidden");
    });

    $("#modalApplyButton").on("click", () => {

        let playListName = $("#modal-text").val();


        let sendData = {
            playListName: playListName
        };
        $.ajax({
            url: "/playList/add",
            type: "POST",
            contentType: "application/json",  // Content-Type을 JSON으로 설정
            data: JSON.stringify(sendData),
            success: (resp) => {
                init();
                $("#admin-user-modal")[0].classList.add("hidden");
                $("#modal-text").val("");
            }
        });
    });


})