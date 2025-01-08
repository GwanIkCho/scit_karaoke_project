$(function () {
    let currentSongId = null;
    let currentPage = 0;  // 현재 페이지
    let pageSize = 10;    // 한 페이지당 데이터 수
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
        $("#modals")[0].classList.remove("hidden");
        $("#modalOverlay")[0].classList.remove("hidden");
        if ($("#search-input").val() ===""){
            getRecommendations();
        }// 포커스 시 모달 표시
        $("#search-input").on("input", function () {
            let keyword = $(this).val();
            if (keyword.length > 0) {
                currentPage = 0;  // 새로운 검색어 입력 시 1페이지로 초기화
                search(keyword, currentPage, pageSize);
            } else {
                getRecommendations();
            }
        });
    })

    $(document).on("mousedown", function (e) {
        // 모달창 영역 외부 클릭 시 모달 닫기
        if (!$(e.target).closest(".modal-content, .search").length) {
            $(".modals")[0].classList.add("hidden");
            $("#modalOverlay")[0].classList.add("hidden");  // 오버레이도 함께 숨기기
        }
    });


    function search(keyword, page, size) {
        $.ajax({
            url: '/playList/searchAPI',
            method: "GET",
            data: {
                keyword: keyword,
                page: page,  // 현재 페이지 정보 반영
                size: size
            },
            success: (response) => {
                console.log(response);
                $('#target').html('');  // 기존 결과 초기화

                if (response && response.content && Array.isArray(response.content) && response.content.length > 0) {
                    // 검색 결과 테이블에 추가
                    response.content.forEach(item => {
                        let title = item['title'];
                        let singer = item['singer'];
                        outputs(title, singer);
                    });

                    // 페이지네이션 렌더링
                    renderPagination(response.page, response.totalPages);
                } else {
                    $('#target').append('<tr><td colspan="3">검색 결과가 없습니다.</td></tr>');
                    $('#pagination').html('');
                }
            }
        });
    }

    function getRecommendations() {
        $.ajax({
            url: '/recommendations',
            method: "POST",
            success: (data) => {
                console.log('서버에서 받은 데이터:', data);
                $('#target').html('');  // 기존 결과 초기화

                if (typeof data === 'string') {
                    data = JSON.parse(data);
                }

                if (Array.isArray(data) && data.length > 0) {
                    data.forEach(item => {
                        let title = item['title'];
                        let singer = item['singer'];
                        outputs(title, singer);
                    });
                } else {
                    $('#target').append('<tr><td colspan="3">추천 곡이 없습니다.</td></tr>');
                }
            },
            error: (xhr) => {
                console.error('에러 발생:', xhr.responseText);
                $('#target').html(`
                <tr>
                    <td colspan="3" style="text-align: center;">
                        로그인 해주시면<br>노래를 추천해드립니다!<br>
                        <a href="/login" style="color: blue; text-decoration: underline;">로그인 하러 가기</a>
                    </td>
                </tr>
            `);
            }
        });
    }

    function outputs(title, singer) {
        let tag = `
            <div class="search-item">
                <span class="title">${title}</span>
                <span class="artist">${singer}</span>
            </div>
            `;
        $('#target').append(tag);
    }

    function renderPagination(currentPage, totalPages) {
        $('#pagination').html('');  // 기존 페이지네이션 초기화

        let startPage = Math.max(0, currentPage - 2);
        let endPage = Math.min(totalPages - 1, startPage + 4);

        // 이전(«) 버튼
        if (currentPage > 0) {
            $('#pagination').append(`<button onclick="goToPage(${currentPage - 1})">«</button>`);
        }

        // 페이지 번호 (1~5)
        for (let i = startPage; i <= endPage; i++) {
            let btn = `<button onclick="goToPage(${i})" ${i === currentPage ? 'disabled' : ''}>${i + 1}</button>`;
            $('#pagination').append(btn);
        }

        // 다음(») 버튼
        if (currentPage < totalPages - 1) {
            $('#pagination').append(`<button onclick="goToPage(${currentPage + 1})">»</button>`);
        }
    }

    // 페이지 이동
    window.goToPage = function (page) {
        currentPage = page;
        let keyword = $('#search-input').val();
        if (keyword.length > 0) {
            search(keyword, currentPage, pageSize);
        }
    }




});
