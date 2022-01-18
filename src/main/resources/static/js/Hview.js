

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
                append_node+= `<a href='hView?Hid=${data[i].hid}'>`;
                append_node += `<div  class="search_box_wrap">`;
                if(data[i].hfile == null){
                    append_node += "<img width='28px' height='28px' class='search_image' src='/profile/defaultProfile.png' style='border-radius: 100%'>";
                }
                else{
                    append_node += `<img width='28px' height='28px' class='search_image' src='/profile/${data[i].hfile}' style='border-radius: 100%'>`;
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