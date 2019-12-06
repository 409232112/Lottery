$(function(){

    
    field.invisible("id")
    field.invisible("mode")
    $("#delids").val()
    form.disabledForm("lottery_form");

    button.disabledButton("btn_save");
    button.disabledButton("btn_reset");
    button.disabledButton("btn_cancel");

    //新增按钮
    $("#btn_insert").click(function(){
        insert();
    });

    //修改
    $("#btn_update").click(function(){
        update()

    });

    //保存
    $("#btn_save").click(function(){
        save()
    });

    //取消按钮
    $("#btn_cancel").click(function(){
        cancel()
    });

    //删除
    $("#btn_del").click(function(){
        del()
    });

    //清空表单
    $("#btn_reset").click(function(){
        reset()
    });

    //刷新表格
    $("#btn_refresh").click(function(){
        refresh()
    });

    //表格行单击事件
    $("#lottery_grid").datagrid({
        onClickRow:function(index,data){
            onRowClick(index,data);
        },
        onLoadSuccess:function(data){
            $('.setPrize').linkbutton({plain:true,iconCls:"icon-standard-coins"});
            $('.showLotteryPage').linkbutton({plain:true,iconCls:"icon-standard-medal-gold-1"});
            $('.showQRCode').linkbutton({plain:true,iconCls:"icon-standard-code"});
            $('.resetLottery').linkbutton({plain:true,iconCls:"icon-standard-arrow-undo"});
        }
    })

})



function insert(){
    form.resetForm("lottery_form")
    form.enabledForm("lottery_form");
    button.onInsertClick();

}
function update(){
    var row = grid.getCurrentSelectRowData("lottery_grid")
    if (row){
        button.onUpdateClick();
        form.enabledForm("lottery_form");
    }else{
        message.warning('请选择需要修改的数据！')
    }
}
function save(){
    $('#lottery_form').form('submit',{
        url: 'lottery/save',
        onSubmit: function(){
            return $(this).form('validate');
        },
        success: function(result){
            result = eval('('+result+')');
            if(result.code=="0"){
                message.info(result.message)
                grid.reloadGrid("lottery_grid")
                form.resetForm("lottery_form")
                form.disabledForm("lottery_form")
                button.onSaveClick();
            }else{
                message.error(result.message)
            }
        }
    });
}
function del(){
    var row = grid.getCurrentSelectRowData("lottery_grid")
    if (row){
        $.messager.confirm('Confirm','确定删除？',function(r){
            if (r){
                $.get('lottery/delete/'+row.id,function(result){
                    var result = eval('('+result+')');
                    if(result.code=="0"){
                        message.info(result.message)
                        grid.reloadGrid("lottery_grid")
                        form.resetForm("lottery_form")
                    }else{
                        message.error(result.message)
                    }
                });
            }
        });
    }
}

function cancel(){
    form.disabledForm("lottery_form");
    button.onCancelClick();
    data = grid.getCurrentSelectRowData("lottery_grid");
    form.setFormValues("lottery_form",data);
}

function reset(){
    form.resetForm("lottery_form")
}

function onRowClick(index,data){
    form.setFormValues("lottery_form",data);
}

function refresh(){
    dialog.refresh()
}

function handle(val,row,index){
    var btn = '<a class="setPrize" onclick="setPrize(\''+row.id+'\',\''+row.lottery_name+'\')">奖项设置</a>' +
        '<a class="showLotteryPage" onclick="showLotteryPage(\''+row.id+'\',\''+row.prize_total_count+'\')">打开抽奖页面</a>' +
        '<a class="showQRCode" onclick="showQRCode(\''+row.id+'\',\''+row.lottery_name+'\')">查看邀请二维码</a>' +
        '<a class="resetLottery" onclick="resetLottery(\''+row.id+'\')">重置抽奖</a>'
    return btn;
}


function setPrize(lottery_id,lottery_name){
    $('#window').window({
        title: lottery_name+"奖品设置",
        iconCls: 'icon-standard-coins'
    });
    $("#prize_div").show();
    $('#window').window('open');

    $("#prize_grid").datagrid({
        queryParams: {
            lottery_id: lottery_id,
        },
        onDblClickCell:function(rowIndex, field, value){
            var rows = $('#prize_grid').datagrid('getRows');
            var num = rows.length

            for(var i=0;i<num;i++){
                if(i==rowIndex){
                    $('#prize_grid').datagrid('beginEdit',i);
                    var ed = $(this).datagrid('getEditor', {index:i,field:field});
                    $(ed.target).focus();
                }else{
                    $('#prize_grid').datagrid('endEdit',i);
                }
            }
        },
        onAfterEdit: function (rowIndex, rowData, changes){
            if(rowData.model!="insert"){
                rowData.model = "update";
                $("#prize_grid").datagrid('refreshRow', rowIndex);
            }
        },
        onClickRow:function(rowIndex){
            var rows = $('#prize_grid').datagrid('getRows');
            var num = rows.length
            for(var i=0;i<num;i++){
                $('#prize_grid').datagrid('endEdit',i);
            }
        }
    });

}


function showLotteryPage(id,prize_total_count){
    if(prize_total_count>0){
        window.open("Lottery.html?lottery="+id)
    }else{
        message.warning("请先设置奖项！")
    }

}

function showQRCode(id,lottery_name){
    window.open("lottery/getQRCode/"+id+"/"+lottery_name)
}

function resetLottery(id){
    $.messager.confirm('Confirm','重置抽奖将清除已有的抽奖结果<br>确定重置抽奖？',function(r){
        if (r){
            $.get('lottery/resetLottery/'+id,function(result){
                var result = eval('('+result+')');
                if(result.code=="0"){
                    message.info(result.message)
                }else{
                    message.error(result.message)
                }
            });
        }
    });
}



function addRow(){
    var rows = $('#prize_grid').datagrid('getRows');
    var num = rows.length
    $('#prize_grid').datagrid('insertRow',{
        index: num,	// 索引从0开始
        row: {
            id:getUUID(),
            prize_name: '',
            prize_count: 1,
            award: '',
            seq: num+1,
            model: 'insert'
        }
    });
}


function deleteRow(){
    var data = $('#prize_grid').datagrid('getSelected');
    if(data){
        var index = $('#prize_grid').datagrid('getRowIndex',data);
        if(data.model!="insert"){
            if($("#delids").val()==""){
                $("#delids").val(data.id)
            }else{
                $("#delids").val($("#delids").val()+","+data.id)
            }
        }
        $('#prize_grid').datagrid('deleteRow',index);
    }else{
        message.warning("请选择一行数据再删除！")
    }
}


function savePrize(){

    var rows = $('#prize_grid').datagrid('getRows');
    var num = rows.length
    for(var i=0;i<num;i++){
        $('#prize_grid').datagrid('endEdit',i);
    }
    var lottery_id = grid.getCurrentSelectRowData("lottery_grid").id;
    var datas = grid.getRows("prize_grid")

    var editDataList=[]
    for(var i =0;i<datas.length;i++) {
        if (datas[i].prize_name == "") {
            message.warning("第" + Number(i + Number(1)) + "行奖项名称为空！");
            return
        }
        if (datas[i].award == "") {
            message.warning("第" + Number(i + Number(1)) + "行奖品为空！");
            return
        }
        if (datas[i].prize_count == "") {
            message.warning("第" + Number(i + Number(1)) + "行奖项数量为空！");
            return
        }
        if(isNaN(datas[i].prize_count)){
            message.warning("第" + Number(i + Number(1)) + "行奖项数量不是数字！");
            return
        }

        if (datas[i].seq == "") {
            message.warning("第" + Number(i + Number(1)) + "行抽奖序号为空！");
            return
        }
        if (isNaN(datas[i].seq )) {
            message.warning("第" + Number(i + Number(1)) + "行抽奖序号不是数字！");
            return
        }
        if (datas[i].model == "insert" || datas[i].model == "update") {
            editDataList.push(datas[i])
        }
        datas[i].lottery_id = lottery_id;
    }
    var data={}
    data['lottery_id'] = grid.getCurrentSelectRowData("lottery_grid").id;
    data['datas'] = editDataList
    var delList =  $("#delids").val().split(",");
    for(var i = 0;i<delList.length;i++){
        var delData = {}
        delData['id'] = delList[i]
        delData['model'] = "delete"
        editDataList.push(delData)
    }

    $.ajax({
        type: "POST",
        contentType: "application/json;charset=UTF-8",
        url: "lottery/addPrize",
        data: JSON.stringify(data),
        success: function (result) {
            result = eval("("+result+")")
            if(result.code == "0"){
                message.warning(result.message)
                $("#delids").val("")
                grid.clean("prize_grid");
                $('#window').window('close');
                grid.reloadGrid("lottery_grid")
            }else{
                message.warning(result.message)
            }
        }
    });


}