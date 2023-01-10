$(document).ready(function(){


    $('.login-submit').click(function(){
        const username = $('.username-input').val();
        const pw = $('.pw-input').val();

        const data = {
            username : username,
            pw : pw
        }

       $.ajax({
           url : "/loginProc",
           method : "post",
           contentType : "application/json",
           data : JSON.stringify(data),
           success : function(data){
               console.log(data.token);
           }
       }) ;
    });
})