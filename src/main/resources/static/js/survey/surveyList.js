$(document).ready(function(){

            const url = new URL(window.location.href);
            let nowPage = url.searchParams.get("page");
            nowPage = nowPage==null ? 0 : nowPage;

            $('#'+(nowPage))
                .css('color','#62A2FF')
                .css('border',"0.1px solid #62A2FF")
                .css('border-bottom','3px solid #62A2FF');

            $('.page-btn').click(function(){
                location.href = "/surveyList?page="+($(this).attr('id'));
            });

            $('.prev-btn').click(function(){
                const prev = nowPage - (nowPage%10) - 10;
                location.href = "/surveyList?page="+prev;
            });

            $('.next-btn').click(function (){
               const next = nowPage - (nowPage%10) + 10;
            });

            $('.survey-btn').each(function(){
                  if($(this).data("is-in-session") == false){
                        $(this)
                            .css('background-color','#adadad')
                            .text("참여 기간이 지난 설문입니다");
                        return;
                  }
            });

            $('.survey-btn').click(function(){
                  location.href = "/survey/"+$(this).data('id');
            });
});
