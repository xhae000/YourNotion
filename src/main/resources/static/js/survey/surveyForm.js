$(document).ready(function(){

    $('.submit-btn').click(function(){
        let isFilled = true;

        $('.sub-option').each(function(){
               if($(this).val()==''){
                    alert("주관식 답변을 입력해주세요.");
                    $(this).focus();
                    isFilled = false;
                }
        });

        if(isFilled)
            $('form').submit();

    });



});