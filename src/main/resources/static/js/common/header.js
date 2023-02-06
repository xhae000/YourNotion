$(document).ready(function(){
    $('.bar-title').click(()=>location.href="/");

    $('.participated-survey').click(()=>location.href="/survey/participatedSurvey");
    $('.wanted-survey').click(()=>location.href="/survey/wantedSurvey");
    $('.made-survey').click(()=>location.href = "/survey/madeSurvey");

    $('.login-btn').click(()=>location.href="/login");
    $('.join-btn').click(()=>location.href="/join");
});