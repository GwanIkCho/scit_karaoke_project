$(function () {
    let currentPage = 0;  // 현재 페이지
    let pageSize = 10;    // 한 페이지당 데이터 수

    getRecommendations();

    // 검색어 입력 감지 (실시간 검색)
    $("#search-input").on("input", function () {
        let keyword = $(this).val();
        if (keyword.length > 0) {
            currentPage = 0;  // 새로운 검색어 입력 시 1페이지로 초기화
            search(keyword, currentPage, pageSize);
        } else {
            getRecommendations();
        }
    });

    // 검색 기능 (AJAX 요청)
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
                $('#table-add').html('');  // 기존 결과 초기화

                if (response && response.content && Array.isArray(response.content) && response.content.length > 0) {
                    // 검색 결과 테이블에 추가
                    response.content.forEach(item => {
                        let title = item['title'];
                        let singer = item['singer'];
                        let kyNumber = item['kyNumber'] || 'N/A';
                        output(title, singer, kyNumber);
                    });

                    // 페이지네이션 렌더링
                    renderPagination(response.page, response.totalPages);
                } else {
                    $('#table-add').append('<tr><td colspan="3">검색 결과가 없습니다.</td></tr>');
                    $('#pagination').html('');
                }
            }
        });
    }

    // 추천 목록 불러오기
    function getRecommendations() {
        $.ajax({
            url: '/recommendations',
            method: "POST",
            success: (data) => {
                console.log('서버에서 받은 데이터:', data);
                $('#table-add').html('');  // 기존 결과 초기화

                if (typeof data === 'string') {
                    data = JSON.parse(data);
                }

                if (Array.isArray(data) && data.length > 0) {
                    data.forEach(item => {
                        let title = item['title'];
                        let singer = item['singer'];
                        let kyNumber = item['ky_number'] || 'N/A';
                        output(title, singer, kyNumber);
                    });
                } else {
                    $('#table-add').append('<tr><td colspan="3">추천 곡이 없습니다.</td></tr>');
                }
            },
            error: (xhr) => {
                console.error('에러 발생:', xhr.responseText);
                $('#table-add').html('<tr><td colspan="3">추천 불러오기 실패</td></tr>');
            }
        });
    }

    // 검색 결과 테이블에 추가
    function output(title, singer, kyNumber) {
        let tag = `
            <tr>
                <td>${title}</td>
                <td>${singer}</td>
                <td>${kyNumber}</td>
            </tr>`;
        $('#table-add').append(tag);
    }

    // 페이지네이션 렌더링 (1~5개, 이전/다음 버튼 포함)
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
