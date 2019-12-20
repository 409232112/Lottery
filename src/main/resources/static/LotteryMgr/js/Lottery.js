document.oncontextmenu = new Function("event.returnValue=false");
document.onselectstart = new Function("event.returnValue=false");

var runTimes = 0


var totalLevel=0;
var alreadyLevel=0;

var currentPrize,levelNum,currentPrizeId,prePrizeId;
var prizes=[]
var t_lottery_user_ids=[]
var lottery_nums=[]
var usernames=[]
var decideNosMap={}
var decideNums=[]
var blackNums;
var decide_num=null


var totalJoin //参加人数
var runing = true;
var num = 0; //随机数存储
var t;//循环调用
var pdnum//参加人数判断是否抽取完

$(function(){
    initData();
    initPage();

});
function initData(){
    $.ajax({
        type: "get",
        contentType: "application/json;charset=UTF-8",
        url: "lottery/lotteryData/getData/"+getQueryString("lottery"),
        async: false,
        success: function (result) {
            result = eval("("+result+")")

            if(result.code == "0"){
                var data = result.data;
                prizes = data.prizes
                var alreadyCount = 0
                if(data.currentPrizeId!=null){
                    prePrizeId = data.currentPrizeId
                    for(var i=0;i<prizes.length;i++){


                        var num_list = prizes[i].lottery_num;
                        var len = 0
                        for(var j=0;j<num_list.length;j++){
                            if(num_list[j]!="??"){
                                len++;
                            }
                        }
                        if(len == num_list.length){
                            alreadyCount++;
                        }


                        if(prizes[i].id==prePrizeId){
                            var num_list = prizes[i].lottery_num;
                            for(var j=0;j<num_list.length;j++){
                                if(num_list[j]!="??"){
                                    runTimes ++;
                                }
                            }
                            if(runTimes == num_list.length){
                                runTimes=0;
                            }
                        }

                    }
                }
                var lotteryImg = data.lotteryImg
                $(".bg").attr("style","position:fixed; bottom:0px; left:0px; width:100%; height:100%; text-align:center; background:url(../files/"+lotteryImg+") center top no-repeat; background-size:cover;")

                decideNosMap = data.decideNosMap
                blackNums = data.blackNums
                t_lottery_user_ids=data.t_lottery_user_ids
                lottery_nums=data.lottery_nums
                usernames=data.usernames
                totalLevel = prizes.length-alreadyCount;
                totalJoin = lottery_nums.length;
                pdnum = totalJoin

                for(var i =0;i<totalLevel;i++){
                    if(i==(totalLevel-1)){

                        currentPrize=prizes[i].prize_name
                        $("#prizename").html(currentPrize);
                        $(".prize").css("height",Number(currentPrize.length+0.5)+"rem")
                        levelNum=prizes[i].prize_count

                        currentPrizeId = prizes[i].id
                    }
                }
                makeNum()
            }else{
                alert(result.message)
            }
        }
    });

}

function initPage(){
    var prizeLength = 0;

    for(var j =0;j<prizes.length;j++){

        var lottery_num = prizes[j].lottery_num;
        var count = lottery_num.length;
        var prize_name =prizes[j].prize_name;
        var lines = parseInt((count-1)/5)


        $("#prize").append('<div class="p1a" style="margin-bottom:'+Number(lines*0.5+0.7)+'rem" id="'+prize_name+'">'+prize_name+'：</div>');
        var lineFlag = 0;
        for(var i=0,m =0;i<count;i++,m++){
            if(i!=0 && i%5==0){
                prizeLength = prizeLength+0.5;
                m=0;
                lineFlag = 1;
            }
            if(lineFlag == 1){
                var top = Number(1*prizeLength+0.5)
            }else{
                var top = Number(1*prizeLength+0.5)
            }
            $("#"+prize_name).append('<span id="'+prize_name+i+'" style="top:'+top+'rem;left:'+Number(m*0.6+0.2)+'rem">'+lottery_num[i]+'</span>');

        }
        prizeLength++;
    }
    $(".zjmd").css("height",prizeLength+1+"rem")

    $("#totalJoin").html("参加抽奖人数：【"+totalJoin+"】")


    if(currentPrize == undefined){
        $("#btnzf").css("display","none")
        $("#btnqr").css("display","none")
        $("#btntxt").css("display","none")
        $(".num").html("")
        $("#prizename").html("抽奖结束")
        $(".prize").css("height","4.5rem")
    }
}


//大奖开始停止
function start() {
    if ($("#btntxt").hasClass("btn_none")) {
        alert("请先确认或作废中奖号！");
        return false;
    }/************判断开始按钮是否可用************/
    var zjnum = $('.list').find('p');
    if(zjnum.length == pdnum){
        alert('没有可抽的人了！');
    }else{
        if (runing) {
            setDecide();
            startNum();
        } else {

            stop();
        }
    }
}
function setDecide(){
    if(decideNums.length!=0){
        //decide_num =Math.floor(Math.random() * decideNums.length);
        decide_num =0
    }
}
//循环参加名单
function startNum() {
    if(prePrizeId!=currentPrizeId){
        prePrizeId = currentPrizeId
        $.ajax({
            type: "get",
            contentType: "application/json;charset=UTF-8",
            url: "lottery/lotteryData/setCurrentPrize/"+currentPrizeId+"/"+getQueryString("lottery")
        });
    }



    runing = false;
    $('#btntxt').removeClass('start').addClass('stop');
    $('#btntxt').html('停止');
    $('.conbox p').html("");
    $('.num').html("");
    num = Math.floor(Math.random() * totalJoin);
    var hasNum = false;
    for (var i = 0; i < totalJoin; i++) {
        if (lottery_nums[i] != "") {
            hasNum = true;
            break;
        }
    }
    if (!hasNum) {
        alert("没有号码可以抽了！！");
        return false;
    }
    while (lottery_nums[num] == "") {
        num = Math.floor(Math.random() * totalJoin);

    }


    $('.num').html(lottery_nums[num]);
    t = setTimeout(startNum, 0);
    $(".turntable .img").css("animation","5s linear 0s normal none infinite rotate");
    $(".turntable .img").css("animation-play-state","running");
}

//停止跳动
function stop() {
    runing = true;

    if( decide_num != null){
        num = getListIndexByValue(lottery_nums,decideNums[decide_num])
        $('.num').html(lottery_nums[num]);
    }


    $('#btntxt').removeClass('stop').addClass('start');
    $('#btntxt').html('继续抽奖');
    clearInterval(t);
    t = 0;
    $('.conbox').html("<p> "+usernames[num]+"</p>");
    $(".lucknum span:last,.conbox p:last").addClass("span");
    $('.confirmbox').show();

    $("#btnzf").css("display","block")
    $("#btnqr").css("display","block")
    $("#btntxt").css("display","none")
    $('.lucknum').css('display','none');
    $(".turntable .img").css("animation-play-state","paused");

}



//确认号码
$('#btnqr').on('click', function () {
    $("#"+currentPrize+Number(runTimes)).html($(".num").html())

    //lottery_nums[$.inArray(lottery_nums[num], lottery_nums)] = "";
    //usernames[$.inArray(usernames[num], usernames)] = "";



    if( decide_num != null){
        decideNums.splice(decide_num,1);
        decide_num = null;
    }



    $("#btnzf").css("display","none")
    $("#btnqr").css("display","none")
    $("#btntxt").css("display","block")


    var data={}
    data['t_prize_id']=currentPrizeId
    data['id']=t_lottery_user_ids[num]
    $.ajax({
        type: "post",
        contentType: "application/json;charset=UTF-8",
        url: "lottery/lotteryData/setUserPrize",
        data: JSON.stringify(data)
    });
    lottery_nums.splice($.inArray(lottery_nums[num], lottery_nums),1)
    usernames.splice($.inArray(usernames[num], usernames),1)
    t_lottery_user_ids.splice(num,1)
    totalJoin--;

    runTimes++;
    if (runTimes >= levelNum) {
        if(alreadyLevel==totalLevel-1){
            alert("所有奖项已经抽取完毕！")
            $("#btnzf").css("display","none")
            $("#btnqr").css("display","none")
            $("#btntxt").css("display","none")
            $("#prizename").html("抽奖结束")
            $(".prize").css("height","4.5rem")
        }else{
            alert(currentPrize+"已经抽选完毕！")
        }
        alreadyLevel++;
        runTimes=0

        for(var i =0;i<prizes.length;i++){
            if(i==(totalLevel-(alreadyLevel+1))){
                currentPrize=prizes[i].prize_name
                $("#prizename").html(currentPrize);
                $(".prize").css("height",Number(currentPrize.length+0.5)+"rem")
                levelNum=prizes[i].prize_count
                currentPrizeId=prizes[i].id
            }
        }
        makeNum()
        $('#btntxt').html('抽取'+currentPrize);
    }

})

//作废号码
$('#btnzf').on('click', function () {

    lottery_nums[$.inArray(lottery_nums[num], lottery_nums)] = "";
    usernames[$.inArray(usernames[num], usernames)] = "";
    if( decide_num != null){
        decideNums.splice(decide_num,1);
        decide_num = null;
    }
    $("#btnzf").css("display","none")
    $("#btnqr").css("display","none")
    $("#btntxt").css("display","block")
    $('.conbox p').html("");
    $('.num').html("");
})


function getListIndexByValue(datas,value){
    for(var i=0;i<datas.length;i++){
        if(datas[i] == value){
            return i;
        }
    }
}


function makeNum(){

    decideNums = decideNosMap[currentPrizeId]

    if(decideNums == undefined){
        decideNums=[]
    }
    var lottery_num =[]

    for(var i=0;i<prizes.length;i++) {
        if (prizes[i].id == currentPrizeId) {
            lottery_num = prizes[i].lottery_num
        }
    }
    var lottery_num_other=[]

    for(var key in decideNosMap){
        if (key != currentPrizeId) {
            lottery_num_other = lottery_num_other.concat(decideNosMap[key])
        }

    }


    var addCount = 0

    for(var i=0;i<lottery_num.length;i++){
        if(lottery_num[i] == "??"){
            addCount++;
        }
    }

    addCount = addCount - decideNums.length
    if(addCount<0){
        addCount=0;
    }
    var ramdomList =[]

    var addCountNoBlack = lottery_nums.length - lottery_num_other.length - blackNums.length


    if(addCount<=addCountNoBlack){
        var count = addCount;
    }else{
        var count =addCountNoBlack
    }



    var m=0;
    for(var i=0;i<count;){
        var random = Math.floor(Math.random() * totalJoin);
        if(!ramdomList.includes(random) && !decideNums.includes(lottery_nums[random]) && lottery_nums[random]!=""  && !lottery_num_other.includes(lottery_nums[random]) && !blackNums.includes(lottery_nums[random])){
            decideNums.push(lottery_nums[random])
            ramdomList.push(random)
            i++
        }
        if(i>=totalJoin){
            break
        }
        m++
        if(m>100){
            console.info("break")
            break
        }
    }
    decideNums.sort(randomsort);


    for(var i=0;i<addCount - addCountNoBlack;i++){

        var random = Math.floor(Math.random() * blackNums.length);
        if(blackNums[random]!=undefined){
            decideNums.push(blackNums[random])
            blackNums.splice(random,1)
        }
    }




    //console.info(decideNums)
}

function randomsort(a, b) {
    return Math.random()>.5 ? -1 : 1;
}


