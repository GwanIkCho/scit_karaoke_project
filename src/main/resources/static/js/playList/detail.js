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
                $('#table-add').html(''); // 기존 테이블 내용 삭제
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
                    $('#table-add').append(tag);
                }
            }
        });

    }

    function output(id, title, singer,tjNumber,kyNumber, createTime) {
        let tag = `
                    <tr class="${id}">
                        <td>${title}</td>
                        <td>${singer}</td>
                        <td>${tjNumber}</td>
                        <td>${kyNumber}</td>
                        <td>${createTime}</td>
                        <td>
                            <input type="button" class="delete" data-seq="${id}" value="삭제!">
                        </td>
                    </tr>
                    `;
        $('#table-add').append(tag);

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



    }

})