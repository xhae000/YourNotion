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


        let answers = [];

        for(let i=0;i<parseInt($('#question-size').text());i++) {
            let value = $("[name='answer_"+i+"']:checked").val();
            if (typeof(value) == 'undefined')
                value = $("[name='answer_"+i+"']").val();

            answers[i] = value;
            alert(value);
        }

        $('[name=answer]').val(answers);

        if(isFilled)
            $('form').submit();

    });



});