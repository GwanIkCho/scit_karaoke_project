$(function () {

    init();

    function init(){
        $.ajax({
            url: '/playList/selectAll',
            method: "GET",
            success: (data) => {
                $('#table-add').html(''); // 기존 테이블 내용 삭제
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
                    $('#table-add').append(tag);
                }
            }
        });

    }

    function output(playListName, createTime,id,userId, isLiked) {
        console.log(isLiked)
        if(isLiked === true){
            let url = `/playList/detail?id=${id}`;
            let tag = `
                    <tr class="${id}">
                        <td><a href="${url}">$${playListName}</a></td>
                        <td>${createTime}</td>
                        <td>
                            <input type="button" class="like-check" data-seq="${id}" value="좋아요 누른상태!!">
                        </td>
                        <td>
                            <input type="button" class="delete" data-seq="${id}" value="삭제!">
                        </td>
                        <td>
                            <input type="button" class="namech" data-seq="${id}" data-name="${playListName}" value="플레이리스트 이름수정!">
                        </td>
                    </tr>
                    `;
            $('#table-add').append(tag);

        } else{
            let url = `/playList/detail?id=${id}`;
            let tag = `
                    <tr class="${id}">
                        <td><a href="${url}">$${playListName}</a></td>
                        <td>${createTime}</td>
                        <td>
                            <input type="button" class="like-check" data-seq="${id}" value="좋아요 안누름!">
                        </td>
                        <td>
                            <input type="button" class="delete" data-seq="${id}" value="삭제!">
                        </td>
                        <td>
                            <input type="button" class="namech" data-seq="${id}" data-name="${playListName}" value="플레이리스트 이름수정!">
                        </td>
                    </tr>
                    `;
            $('#table-add').append(tag);
        }


// 삭제 버튼 클릭 시
        $(".delete").on("click", function() {
            // 버튼의 data-seq 속성에서 seq 값을 가져옵니다.
            let seq = $(this).attr('data-seq');

            let sendData = { "id": seq };

            $.ajax({
                url: '/playList/delete',
                method: 'GET',
                data: sendData,  // JSON.stringify 제거
                success: () => {
                    init();
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