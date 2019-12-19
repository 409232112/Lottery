$(function(){
    //查询
    $("#btn_query").click(function(){
        query()
    });

    $("#setDecide").click(function(){
        setDecide()
    });

    $("#setBlack").click(function(){
        setBlack()
    });



    //刷新表格
    $("#btn_refresh").click(function(){
        refresh()
    });

    $('#lottery_id').combobox({
        onChange: function(param){
            query();
        }
    });


    $("#lottery_id").combobox({
        url:"lottery/findAllLottery",//获取数据
        // 向服务器请求的模式
        method : "get",
        valueField: 'id',
        textField: 'lottery_name',
    })

    $("#prize_grid").datagrid({
        onClickRow:function(index,data){
            onClickRow(data);
        },
        onLoadSuccess:function(data){
            $('.makdDecideQRCode').linkbutton({plain:true,iconCls:"icon-standard-code"});
        },
        onBeforeLoad: function (param){
            $('#prize_grid').datagrid('unselectAll');
        }
    });
})


function query(){
    var data =  form.getFormValues("lottery_form");
    grid.reloadWithData("prize_grid",data)

    $("#tb").css("display","block");
    $("#decide_grid").datagrid({
        queryParams: {
            t_lottery_id:field.getFieldValue("lottery_id")
        }
    });


    $("#black_grid").datagrid({
        queryParams: {
            t_lottery_id:field.getFieldValue("lottery_id")
        }
    });


    $("#undecide_grid").datagrid({

        queryParams: {
            t_lottery_id:field.getFieldValue("lottery_id")
        }
    });
    $('#undecide_grid').datagrid('unselectAll');


}

function refresh(){
    dialog.refresh()
}

function onClickRow(row){
    $('#decide_grid').datagrid('unselectAll');
    $("#decide_grid").datagrid({
        queryParams: {
            decide_t_prize_id: row.id,
            t_lottery_id:field.getFieldValue("lottery_id")
        }
    });


}

function handle(val,row,index){
    var btn = '<a class="makdDecideQRCode" onclick="makeDecideQRCode(\''+row.id+'\',\''+row.t_lottery_id+'\')">生成奖项内定邀请二维码</a>'
    return btn;
}

function makeDecideQRCode(prize_id,t_lottery_id){
    window.open("lottery/getDecideQRCode/"+t_lottery_id+"/"+prize_id+"/"+$('#lottery_id').combobox('getText'))
}


function setDecide(){
    var data = grid.getCurrentSelectRowData("undecide_grid")
    if(data == null){
        message.warning("请选择一个需要内定的人！")
        return
    }
    var prizeData = grid.getCurrentSelectRowData("prize_grid")
    if(prizeData == null){
        message.warning("请先选中一行内定的奖项！")
        return
    }
    if(prizeData['prize_count']==$("#decide_grid").datagrid("getRows").length){
        $.messager.confirm('Confirm','内定人员数已超过奖品数，抽奖将从内定人员中随机抽出。<br>是否确定？',function(r){
            if (r){
                var param={}
                param['id'] = data.id
                param['decide_t_prize_id'] = prizeData.id
                $.ajax({
                    type: "POST",
                    contentType: "application/json;charset=UTF-8",
                    url: "lottery/setDecide",
                    data: JSON.stringify(param),
                    success: function (result) {
                        result = eval("("+result+")")
                        if(result.code == "0"){
                            message.info(result.message)
                            $('#decide_grid').datagrid('reload');
                            $('#undecide_grid').datagrid('reload');
                        }else{
                            message.info(result.message)
                        }
                    }
                });
            }
        });
    }else{
        var param={}
        param['id'] = data.id
        param['decide_t_prize_id'] = prizeData.id
        $.ajax({
            type: "POST",
            contentType: "application/json;charset=UTF-8",
            url: "lottery/setDecide",
            data: JSON.stringify(param),
            success: function (result) {
                result = eval("("+result+")")
                if(result.code == "0"){
                    message.info(result.message)
                    $('#decide_grid').datagrid('reload');
                    $('#undecide_grid').datagrid('reload');
                }else{
                    message.info(result.message)
                }
            }
        });
    }
}

function setUnDecide(){
    var data = grid.getCurrentSelectRowData("decide_grid")
    if(data == null){
        message.warning("请选择一个需要取消内定的人！")
        return
    }
    var param={}
    param['id'] = data.id
    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "lottery/setUnDecide",
        data: JSON.stringify(param),
        success: function (result) {
            result = eval("("+result+")")
            if(result.code == "0"){
                message.info(result.message)
                $('#decide_grid').datagrid('reload');
                $('#undecide_grid').datagrid('reload');
            }else{
                message.info(result.message)
            }
        }
    });
}


function searchData(value){
    var data={}
    data["username"] = value
    data["t_lottery_id"] =  field.getFieldValue("lottery_id")
    grid.reloadWithData("undecide_grid",data);
}



function setBlack() {
    var data = grid.getCurrentSelectRowData("undecide_grid")
    if (data == null) {
        message.warning("请选择一个需要拉黑的人！")
        return
    }
    $.messager.confirm('Confirm','拉黑将无法中奖，是否确定？',function(r) {
        if (r) {
            var param = {}
            param['id'] = data.id
            $.ajax({
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                url: "lottery/setBlack",
                data: JSON.stringify(param),
                success: function (result) {
                    result = eval("(" + result + ")")
                    if (result.code == "0") {
                        message.info(result.message)
                        $('#black_grid').datagrid('reload');
                        $('#undecide_grid').datagrid('reload');
                    } else {
                        message.info(result.message)
                    }
                }
            });
        }
    })

}

function setUnBlack() {
    var data = grid.getCurrentSelectRowData("black_grid")
    if (data == null) {
        message.warning("请选择一个取消拉黑的人！")
        return
    }
    var param = {}
    param['id'] = data.id
    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "lottery/setUnBlack",
        data: JSON.stringify(param),
        success: function (result) {
            result = eval("(" + result + ")")
            if (result.code == "0") {
                message.info(result.message)
                $('#black_grid').datagrid('reload');
                $('#undecide_grid').datagrid('reload');
                $('#black_grid').datagrid('unselectAll');
            } else {
                message.info(result.message)
            }
        }
    });
}

