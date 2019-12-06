$(function(){
    $("#lottery_grid").datagrid({
        onClickRow:function(index,data) {
            onLotteryRowClick(index, data);
        }
    })

    $("#join_grid").datagrid({
        onClickRow:function(index,data){

        }
    });

    $("#prize_grid").datagrid({
        onClickRow:function(index,data){
            onPrizeClickRow(index,data);
        }
    });

    $("#win_grid").datagrid({
        onClickRow:function(index,data){

        }
    });



})


function onLotteryRowClick(index,data){
    var param = {}
    param['lottery_id'] = data.id

    grid.reloadWithData("prize_grid",param)
    grid.reloadWithData("win_grid",param)
    grid.reloadWithData("join_grid",param)

}


function onPrizeClickRow(index,data){
    var lottery_id = grid.getCurrentSelectRowData("lottery_grid").id
    var prize_id = data.id
    var param={}
    param['lottery_id'] = lottery_id
    param['prize_id'] = prize_id
    grid.reloadWithData("win_grid",param)

}

function refresh(){
    dialog.refresh()
}



function deleteUser(){
    var row = grid.getCurrentSelectRowData("join_grid")
    if (row){
        $.messager.confirm('Confirm','确定删除？',function(r){
            var data={}
            var lotteryRow = grid.getCurrentSelectRowData("lottery_grid")
            data['id'] = row.id;
            data['lottery_id'] =lotteryRow.id
            $.ajax({
                type: "POST",
                contentType: "application/json;charset=UTF-8",
                url: "lottery/deleteUser",
                data: JSON.stringify(data),
                success: function (result) {
                    var result = eval("("+result+")")
                    if(result.code == "0"){
                        message.info(result.message);

                        grid.reloadGrid("lottery_grid")
                        grid.reloadGrid("join_grid")
                        grid.reloadGrid("prize_grid")
                        grid.reloadGrid("win_grid")
                    }else{
                        message.error(result.message);
                    }
                }
            });
        });
    }else{
        message.warning("请选择一条需要删除的数据！");
    }
}
