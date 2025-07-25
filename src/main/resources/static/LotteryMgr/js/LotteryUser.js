$(function(){
    var lottery_name = decodeURI(getQueryString("lottery_name"))
    $("#lottery_name").html("欢迎参加"+lottery_name)
    checkJoinEnd()
})

function submit(){
    var username = $("#username").val();
    if(username==null) {
        alert("请填写姓名！");
        return
    }else{
        var data={}
        data["username"] = username;
        data["lottery_id"]=getQueryString("lottery_id")
        data["decide_t_prize_id"]=getQueryString("prize_id")
        $.ajax({
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            url: "lottery/LotteryUser",
            data: JSON.stringify(data),
            success: function (result) {
                result = eval("("+result+")")
                if(result.code == "0"){
                    var username = result.data.username
                    var lottery_num = result.data.lottery_num
                    alert(username+"你的抽奖号码为【"+lottery_num+"】赶紧截图保存下来吧！")
                }else{
                    alert(result.message)
                }
            }
        });
    }
}

function checkJoinEnd(){
    var lottery_id = getQueryString("lottery_id")
    $.get('lottery/CheckJoinEnd?lotteryId='+lottery_id,function(result){
        var result = eval('('+result+')');
        if(result.code=="0"){
            var is_end = result.data.end;
            if(is_end=="1"){
                alert("抽奖报名已经结束！")
                $("#btn_submit").click(function(){
                    alert("抽奖报名已经结束！")
                });
            }else{
                $("#btn_submit").click(function(){
                    submit()
                });
            }
        }
    });
}