$(document).ready(function(){

            $('.survey-btn').each(function(){

                  if($(this).data("is-in-session") == false){
                        $(this)
                            .css('background-color','#adadad')
                            .text("참여 기간이 지난 설문입니다");

                        return;
                  }

                  const target_gender = $(this).data('gender');
                  const target_startAge = Number($(this).data('start-age'));
                  const target_endAge = Number($(this).data('end-age'));

                  if(gender == null) // 비회원이면 null 들어옴 -> default 설정
                        return;

                  let isAvailable = true;

                  if(target_gender != "irr" && target_gender != gender)
                        isAvailable = false;
                  else if (age < target_startAge || age > target_endAge)
                        isAvailable = false;


                  if (!isAvailable) { // 참여할 수 없는 설문일 때 (성별조건, 나이조건에 의해)
                        $(this)
                            .css('background-color','#8BB7FF')
                            .text("설문 대상이 아닙니다");
                  }
            });


            $('.survey-btn').click(function(){
                  location.href = "/survey/"+$(this).data('id');
            });
});
