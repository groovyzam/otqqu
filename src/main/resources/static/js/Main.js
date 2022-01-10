var loading = false;    //중복실행여부 확인 변수
var page=13;   //불러올 페이지

/*nextpageload function*/
function next_load() {
    $.ajax({
        type: "POST",
        url: "/ajaxPost",
        data: {'page': page},
        dataType: 'json',
        success: function (data) {
            for (i = 0; i < data.length; i++) {
                console.log("성공");
                $('#post_list_wrap').append(
                    `<div style="text-align: center; width: 310px; height: 310px;">
                        <img src="/photo/${data[i].pfileName}" style="width: inherit; height: inherit; object-fit: cover;">
                        <a href = "pView?Pnum=${data[i].pnum}" class="post_list_grey" style="position: relative; display: block; top: -310px; left: 0; width: inherit; height: inherit; z-index: 100;" >
                            <div class="post_list_grey_text">
                                <svg xmlns = "http://www.w3.org/2000/svg" width = "20" height = "20" fill = "currentColor" class="bi bi-suit-heart-fill post_list_grey_heart" viewBox = "0 0 16 16" >
                                    <path d = "M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z" / >
                                </svg>
                                <span>${data[i].pup}</span>
                            </div>
                        </a>
                    </div>`);
            }
            page = page + 1; //페이지 증가
            loading = false;    //실행 가능 상태로 변경

        }
        , error: function (xhr, status, error) {
            console.log("실패");
        }
    });
}

//스크롤 맨 아래일 때 실행
$(window).scroll(function () {
    if ($(window).scrollTop() + 200 >= $(document).height() - $(window).height()) {
        if (!loading)    //실행 가능 상태라면?
        {
            loading = true; //실행 불가능 상태로 변경
            next_load();
        } else            //실행 불가능 상태라면?
        {

        }
    }
});

    $(document).ready(function() {
    $("#searchInput").autocomplete({
        source : function(request, response) {

            $.ajax({

                url : "/autocomplete",
                type : "post",
                dataType : "json",
                data: request,

                success : function(data) {

                    var result = data;
                    response(result);
                },

                error : function(data) {
                    alert("에러가 발생하였습니다.")
                }
            });
        }
    });
});