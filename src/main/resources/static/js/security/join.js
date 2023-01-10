$(document).ready(function(){

    let email = '';
    let isEmailAuth = false;
    let isUsingId = false;
    
   $('.request-auth, .resend-key').click(function(){
       const thisEl = this;
       email = $('.email-input').val();

       if(email == '') {
           alert("이메일을 입력해주세요.");
           $('.email-input').focus();
           return
       }


       $.ajax({
            url : "/mail/sendMail",
            method : "POST",
            data : {email : email},
            success : function (data) {
                if(data == "using"){
                    alert("이미 사용중인 이메일입니다.");
                    return
                }

                if($(thisEl).attr('class')=="resend-key")
                    alert("인증번호가 재전송되었습니다.");

                $('.key-input').val('');
                $('.request-auth').css('display','none');
                $('.auth-form').css('display','block');
            }
       });
   });

   $('.auth-submit').click(function () {
      const key = $('.key-input').val();

      if(key==''){
          alert("인증번호를 입력해주세요.");
          return;
      }

      $.ajax({
          url : "/mail/mailAuth",
          method : "post",
          data : {key : key, email : email},
          success : function (data) {

              if(data == "ok") {
                  alert("인증이 완료되었습니다.");
                  isEmailAuth = true;

                  $('.auth-form').css('display','none');
                  $('.email-input').attr('readonly',true);
                  $('.email-input')
                      .css('background-color','#eeeeee')
                      .css('border-bottom','1.2px solid #eeeeee')
                      .css('border-radius','10px')
                      .css('color','grey');
              }
              else
                  alert("인증번호가 일치하지 않습니다. 다시 입력해주세요.");
          }
      })

   });


   $('.email-input').change(function(){
      $('.request-auth').css('display','');
      $('.auth-form').css('display','none');
   });

   $('.id-input').change(function(){
       const id = $(this).val();

        $.ajax({
           url : "/isUsingUsername",
           method : "post",
           data : {username : id},
           success : function(isUsing){
               if(isUsing == true){
                   $('.id-input').css('border-bottom','1.2px solid red');
                   $('.id-alert-box').css('display','block');

                   isUSingId = true;
               }
               else{
                   $('.id-input').css('border-bottom','1.2px solid #c5c5c5');
                   $('.id-alert-box').css('display','none');

                   isUsingId = false;
               }
           }
        });
   });


   $('form').submit(function(){
        const mail = $('.email-input').val();
        const id = $('.id-input').val();
        const password = $('.pw-input').val();
        const nickname = $('.nickname-input').val();
        const age = $('.age-input').val()
        let isOk = true; // 공백 없는지

       $('.info-input').each(function(i){

           // 현재 value와 빈칸을 제거한 value의 길이를 비교, 빈칸이 있다면 길이 다름
            if($(this).val().length !=
                 $(this).val().replace(' ','').length){
                alert("공백은 사용될 수 없습니다.");
                isOk = false;

                $(this).val('').focus();
                return false; // 얘는 for문이 종료되는 return
            }
       });

        if(!isOk){
            return false; //얘는 공백이 있으면, submit을 종료하는 return
        }


       if(isEmailAuth == false){
            alert("이메일을 인증을 진행해주세요.");
            $('.email-input').focus();
            return false;
        }
        else if(id==''){
            alert("아이디를 입력해주세요.");
            $('.id-input').focus();
            return false;
        }
        else if(isUsingId){
            alert("다른 아이디를 입력해주세요.")
           $('.id-input').focus();
            return false;
       }
        else if(password==''){
            alert("비밀번호를 입력해주세요.");
            $('.pw-input').focus();
            return false;
        }
        else if(nickname==''){
            alert("닉네임을 입력해주세요.");
            $('.nickname-input').focus();
            return false;
        }
        else if(age.length!=6){
            alert("생년월일은 6글자여야합니다..");
            $('.age-input').focus();
            return false;
       }
        else if(parseInt(age.slice(2,4))>12 || parseInt(age.slice(4,6))>31){
            alert("생년월일이 잘못되었습니다.");
           $('.age-input').focus();
           return false;
       }

   });
});