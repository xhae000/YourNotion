$(document).ready(function(){


    $('.login-submit').click(function(){
        const username = $('.username-input').val();
        const pw = $('.pw-input').val();

        if(username == ''){
            alert("아이디를 입력해주세요.");
            $('.username-input')
                .focus();
            return false;
        }
        else if(pw == ''){
            alert("비밀번호를 입력해주세요.");
            $('.pw-input')
                .focus();
            return false;
        }

        const data = {
            username : username,
            pw : pw,
            redirect : redirect
        }

       $.ajax({
           url : "/loginProc",
           method : "post",
           contentType : "application/json",
           data : JSON.stringify(data),
           success : function(data){
               if(data == ""){ // 로그인 실패
                   $('.alert-cont').css('display','block');
               }else{ // 로그인 성공
                    location.href=data;
               }
           }
       }) ;
    });
})