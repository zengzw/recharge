$(function(){

    //加载充值面值方案
    loadPhoneOneyuanFacevalueCombobox("#id_charge_amount", '../../../../vas/phonefare/test/getPhoneFareChargeWays.do', '元');


    var url = '../../../../vas/activity/queryVasPhoneOneyuanFreeList.do';
    init(url);
});

//加载combobox的动态加载值  要求后台返回json字符串
function loadPhoneOneyuanFacevalueCombobox(id ,url, option) {
    $.ajax({
        url:url,
        type:'post',
        success:function(data){
            var defaultOption = [];
            defaultOption.push({'text':'全部','value':''});
            for (var i=0;i<data.data.rows.length;i++) {
                var obj = eval(data.data.rows[i]);
                defaultOption.push({'text':obj['value'] + option,'value':obj['value']});
            }

            defaultOption = defaultOption.sort(getSortFun('asc', 'value'));
            $(id).combobox("loadData", defaultOption);
            //默认设置第一项
            $(id).combobox("select", defaultOption[0]['value']);
            //$(id).combobox({editable:false});
        }
    });
}

// 加载数据
function init(url) {
    $("#id_order_change_datagrid2").datagrid({
        url:url,
        singleSelect: false, //是否单选
        pagination: false, //分页控件
        // pageSize:15,
        // pageList:[30,15],
        autoRowHeight: true,
        fit: true,
        striped: true, //设置为true将交替显示行背景
        fitColumns: true,//设置是否滚动条
        nowrap: false,
        remotesort: true,
        checkOnSelect:false,
        selectOnCheck:true,
        method: "POST", //请求数据的方法
        loadMsg: '数据加载中,请稍候......',
        idField:'',
        queryParams:{'orderNo':''},
        columns:[
            [
                {field:'id', title:'选择', align:'center', checkbox:true },
                {field:'orderCode',title:'订单编号', width:'15%', align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'luckyNumber',title:'幸运号', width:'5%',align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'chargeMobile',title:'充值手机号', width:'10%', align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'createTimeString',title:'下单时间', width:'15%', align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'chargeAmount',title:'充值面额', width:'5%', align:'center',formatter:function(value,row, index) {
                    return value+"元";
                }},
                {field:'areaName',title:'下单县域',width:'20%', align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'storeName',title:'下单网点',width:'20%', align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'count',title:'当前网点参与量', width:'10%', align:'center',formatter:function(value,row, index) {
                    return value;
                }}

            ]
        ],
        loadFilter: function(returnDTO){
            if(returnDTO.status==200){
                return returnDTO.data;
            }else{
                return [];
            }
        },
        onLoadError: function() {
            $.messager.alert('操作提示', '获取信息失败...请联系管理员!', 'error');
        },
        onLoadSuccess:function(data){
            console.log(data);
            $("#showNumbers").text(data.total);
            $("input[type='checkbox']")[0].hidden = true;
        },
        onSelect: function(rowIndex,row){
            var selectRow = $("#id_order_change_datagrid2").datagrid("getChecked");

            if(selectRow.length > 19){
                alert("最多选择20条");
                $("input[type='checkbox']")[rowIndex+1].prop("checked", false);
        }

            

        },

        toolbar:'#tb'
    });

}


//条件搜索
function freesearch() {
    var lotteryCount = $("#id_lottery_count").textbox("getValue");
    var chargeAmount = $("#id_charge_amount").textbox("getValue");
    $('#id_order_change_datagrid2').datagrid("load",
        {
            lotteryCount:lotteryCount,
            chargeAmount:chargeAmount
        });
}

//重置搜索
function resetSearch() {
    $("#id_lottery_count").combobox("setValue", '5');
    $("#id_charge_amount").combobox("setValue", '');


    $('#id_order_change_datagrid2').datagrid("load", {orderCode:''});
}

// 动态获取可选鼓励金
function getDynamicSelectMoney(value){
    var moneyArray = [2,3,5,10,30,50];
    var content = "<option value='-1'>请选择</option>";
    for(var i=0;i<moneyArray.length;i++){
        if(parseInt(value) >= parseInt(moneyArray[i])){
            content += "<option value='"+moneyArray[i]+"'>"+moneyArray[i]+"元</option>"
        }
    }
    return content;
}



// 修改鼓励金开奖金额，动态计算总计金额
function changeSubmitMoney(value){
    var moneyArray = $("select[id^='submitYourMoney']");
    var totalMoney = 0;
    for(var i=0;i<moneyArray.length;i++){
        if(parseInt(moneyArray[i].value) > 0){
            totalMoney = parseInt(totalMoney) + parseInt(moneyArray[i].value);
        }
    }

    $("#submitTotalMoney").text(totalMoney);
}


// 点击“鼓励金开奖”事件
function coming2me() {
    var selectRow = $("#id_order_change_datagrid2").datagrid("getChecked");
    if(null == selectRow || selectRow.length == 0){
        alert("请选择需开奖的订单");
        return ;
    }

    if(selectRow.length > 20){
        alert("已勾选20条订单，每次最多可对20条订单进行开奖");
        return ;
    }

    var content = "<div width='100%'><table width='100%' align='center' class='easyui-table' style='border: 3px;'>";

    // submitYourMoney和spanError不能修改，很多地方会依赖此字符串
    for(var i=0;i<selectRow.length;i++){
        content += "<tr align='center' style='height: 40px;' bgcolor='#f0f8ff'>" +
                        "<td width='50%' align='center'>订单编号:&nbsp;"+selectRow[i].orderCode+"</td>" +
                        "<td width='20%' align='left'>充值面额:&nbsp;"+selectRow[i].chargeAmount+"元</td>" +
                        "<td width='30%' align='left'>中奖金额:&nbsp;&nbsp;<select style='width:100px; height:30px;' id='submitYourMoney"+selectRow[i].orderCode+"' onchange='changeSubmitMoney()'>" +getDynamicSelectMoney(selectRow[i].chargeAmount)+ "</select>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                                 "<font color='red'><span id='spanError"+selectRow[i].orderCode+"'></span></font></td>" +
                    "</tr>";
    }

    content += "</table>";

    content += "<div id='div_btns' class='dialog-button' style='height: 100px;'>" +
            "<table align='center' width='100%'>" +
                "<tr >" +
                    "<td align='center'>" +
                        "<font color='red'>提示：还有未设置中奖金额的订单\\中奖金额总计不可超过开奖订单量值的范围</font><br>" +
                        "<a  class='btn'   onclick='javascript:$(\"#id_phone_lottery_setting\").dialog(\"close\")'><i class='i-close'></i>取消</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                        "<a  class='btn btn-1' onclick='doLottery()'><i class='i-ok'></i>确定</a>"
                    "</td>" +
                "</tr>"
            "</table>"+
        "</div>";

    $('#id_phone_lottery_setting').dialog({
        closable:true,
        left:($(window).width()-1000)/2,
        top:50,
        width:1000,
        height:800,
        modal: true,
        title:"<table width='100%'>" +
                "<tr ><td align='center'>鼓励金中奖金额设置</td><td width='30%'></td>" +
                    "<td align='left' width='30%'><font color='red'>已选中奖金额总计：<span id='submitTotalMoney' style='width: 50px;'>0</span>元</font></td></tr></table>",
        buttons:'#div_btns',
        content:content,
        onClose:function(){
            $("#div_btns").remove();
            $('#add_sale_region_div').empty();
        }
    });


}


// 鼓励金开奖
function doLottery(){

    var moneyArray = $("select[id^='submitYourMoney']");

    // 验证开奖金额不能为空
    for(var i=0;i<moneyArray.length;i++){
        var errorSpanId = moneyArray[i].id.substring('submitYourMoney'.length);
        if(parseInt(moneyArray[i].value) < 0){
            $("#spanError" + errorSpanId).text("不能为空")
            return ;
        } else {
            $("#spanError" + errorSpanId).text("")
        }
    }

    // 验证开奖金额不能超过总记录数
    var totalMoney = $("#submitTotalMoney").text();
    var selectRows = $("#showNumbers").text();
    if(parseInt(totalMoney) > parseInt(selectRows)){
        alert("中奖金额总计不可超过开奖订单量值的范围");
        return ;
    }

    $.messager.confirm({
        width: 500,
        title: '修改提示',
        msg: '<div class="content">确定要把选中的订单设为已中奖吗？<br>开奖后所筛选的其他订单会转为未中奖状态。</div>',
        ok: '<i class="i-ok"></i> 确定',
        cancel: '<i class="i-close"></i> 取消',
        fn: function (r) {
            if (r) {
                var selectRow = $("#id_order_change_datagrid2").datagrid("getChecked");
                var lotteryIds = "";
                for(var i=0;i<selectRow.length;i++){
                    var lotteryMoney = $("#submitYourMoney" + selectRow[i].orderCode)[0].value
                    lotteryIds += selectRow[i].id + "@" + lotteryMoney + ",";
                }

                var rows = $("#id_order_change_datagrid2").datagrid("getRows");
                var ids = '';
                for(var i=0;i<rows.length;i++){
                    ids += rows[i].id + ",";
                }

                $.post('../../../../vas/activity/setLottery.do',
                    {
                        'lotteryIds':lotteryIds,
                        'ids':ids
                    },

                    function(data){
                        if (data.status == 200) {
                            info("开奖成功");
                            freesearch();
                            closeMyDialog("#id_phone_lottery_setting");

                        } else {
                            alert(data.msg);
                        }
                });
            } else {
                return ;
            }
        }
    });
}

//消息框关闭
function closeMyDialog(o) {
    $(o).dialog('close');
}
