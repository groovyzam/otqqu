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
                                <span>1</span>
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


function search(){
    let keyword = document.getElementById("searchInput").value;
    var display = document.getElementById("searchDIV");

    display.style.display='block';
    $.ajax({
        type : "POST",
        url  : "search",
        data : {"keyword":keyword},
        dataType : "json",
        success: function (data){

            var append_node = "";

            for(i=0; i<data.length;i++) {
                console.log("성공");
                append_node+= `<a href='hView?Hid="+data[i].hid+"'>`;
                append_node += `<div  class="search_box_wrap">`;
                if(data[i].hfile == null){
                    append_node += "<img width='28px' height='28px' class='search_image' src='/profile/defaultProfile.png' style='border-radius: 100%'>";
                }
                else{
                    append_node += "<img width='28px' height='28px' class='search_image' src='/profile/"+data[i].hfile+"' style='border-radius: 100%'>";
                }

                append_node += "<span>"+data[i].hid+"</span>";
                append_node += "</div>";
                append_node += "</a>"
            }

            if(append_node == ""){
                display.style.display='none';
            }
            $('html').click(function (e){
                console.log("클릭");
                var target = event.target;
                if(!$(e.target).hasClass("search_list")){
                    display.style.display='none';
                }
            });
            $("#searchDIV").html(append_node);

        },
        error : function (){
            console.log("search실패");
        }
    });
}