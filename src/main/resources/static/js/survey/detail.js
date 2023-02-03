$(document).ready(function(){
    $('.do-btn').click(function(){
       location.href = "/survey/participate/"+surveyId;
    });

    $('.want-btn').click(function (){
       $.ajax({
           url : "/survey/wantSurvey",
           method : "POST",
           data : {id : surveyId},
           success : function (data){
               if (data==true)
                   alert("관심목록에 추가되었습니다.");
               else
                   alert("관심목록에 이미 추가된 설문입니다.")
           }
       }) ;
    });

});