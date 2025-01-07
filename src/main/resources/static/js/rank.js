$(function () {
    let currentSongId = null;
    init();

    function init(){
        $.ajax({
            url: '/rank/all',
            method: "GET",
            success: (data) => {
                $('.playlist').html(''); // 기존 테이블 내용 삭제
                console.log(data)
                if (data.length !== 0) {
                    data.forEach(item => {
                        let id = item['id'];
                        let title = item['title'];
                        let singer = item['singer'];
                        let tjNumber = item['tjNumber'];
                        let kyNumber = item['kyNumber'];
                        let isLiked = item['liked'];

                        output(id, title, singer, tjNumber, kyNumber, isLiked);
                    });
                } else {
                    let tag = `<div>내용이 없습니다</div>`;
                    $('.playlist').append(tag);
                }
            }
        });
    }

    // 이벤트 위임 방식으로 클릭 이벤트 등록
    $(".playlist").on("click", ".like-check", function() {
        let seq = $(this).attr('data-seq');
        let sendData = { "id": seq };

        $.ajax({
            url: '/rank/likeCheck',
            method: 'GET',
            data: sendData,
            success: () => {
                init();  // 성공 시 테이블 리로드
            },
            error: (xhr) => {
                if (xhr.status === 401) {
                    alert("로그인이 필요합니다.");
                    window.location.href = "/login";  // 로그인 페이지로 리다이렉트
                }
            }
        });
    });

    $(".playlist").on("click", ".open-modal", function () {
        currentSongId = $(this).attr('data-seq');
        userPlaylist();

    });

    $("#closeModal").on("click", function () {
        $("#modal")[0].classList.add("hidden");
        currentSongId = null;  // 노래 ID 초기화
    });

    function output(id, title, singer, tjNumber, kyNumber, isLiked) {
        let tag = `
        <li class="playlist-item">
            <img src="/images/pl_1.jpg" alt="썸네일">
            <div class="playlist-item-content">
                <p class="title">${title}</p>
                <p class="artist">${singer}</p>
            </div>
            <div class="icon-btn like ${isLiked ? 'active' : ''}">
                <img src="/images/playList/like_btn.png" alt="엄지 아이콘" data-seq="${id}" class="like-check">
            </div>
            <div class="icon-btn add">
                <img src="/images/playList/plus.png" alt="플레이리스트 추가 아이콘" data-seq="${id}" class="open-modal">
            </div>
        </li>
        `;
        $('.playlist').append(tag);
    }

    function userPlaylist(){
        $.ajax({
            url: '/rank/playList',
            method: "GET",
            success: (data) => {
                $('.playlist-list').html(''); // 기존 테이블 내용 삭제
                if (data.length !== 0) {
                    data.forEach(item => {
                        let playListName = item['playListName'];
                        let id = item['id'];
                        let tag = `<div class="playlist-item" data-seq="${id}">${playListName}</div>`;
                        $('.playlist-list').append(tag);
                    });
                } else {
                    let tag = `<div class="playlist-item">내용이 없습니다</div>`;
                    $('.playlist-list').append(tag);
                }
                $("#modal")[0].classList.remove("hidden");
            },
            error: (xhr) => {
                if (xhr.status === 401) {
                    alert("로그인이 필요합니다.");
                    window.location.href = "/login";
                }
            }
        });

        $(".playlist-list").off("click").on("click", ".playlist-item", function () {
            let playlistId = $(this).attr('data-seq');
            if (currentSongId) {
                let sendData = {
                    songId: currentSongId,
                    playListId: playlistId
                };

                $.ajax({
                    url: '/rank/addplaylist',
                    method: 'POST',
                    data: JSON.stringify(sendData),  // JSON 형식으로 전송
                    contentType: 'application/json',  // JSON 컨텐츠 타입 설정
                    success: () => {
                        alert("플레이리스트에 추가되었습니다.");
                        $("#modal")[0].classList.add("hidden");
                        init();
                    },
                    error: (xhr) => {
                        if (xhr.status === 401) {
                            alert("로그인이 필요합니다.");
                            window.location.href = "/login";
                        } else if (xhr.status === 409) {
                            alert(xhr.responseText);  // 중복 예외 메시지
                        } else {
                            alert("플레이리스트 추가에 실패했습니다.");
                        }
                    }
                });
            }
        });
    }

    $(".search").on("focus", function () {
        $("#modals")[0].classList.remove("hidden");  // 포커스 시 모달 표시
    }).on("blur", function () {
        $("#modals")[0].classList.add("hidden");  // 포커스 해제 시 모달 숨김
    });





});
