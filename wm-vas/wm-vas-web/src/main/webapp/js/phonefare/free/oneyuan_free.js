$(function(){

    //加载充值面值方案
    loadPhoneFacevalueCombobox("#id_charge_amount", '../../../../vas/phonefare/test/getPhoneFareChargeWays.do', '元');


    initProvince();
    initLotterAmountList();

    var url = '../../../../vas/activity/queryVasPhoneOneyuanFreePage.do';
    init(url);
});

//加载combobox的动态加载值  要求后台返回json字符串
function loadPhoneFacevalueCombobox(id ,url, option) {
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


function initProvince() {
    $.ajax({
        url: WebHelper.GetAddress(),
        data: {cid: 0},
        dataType: 'jsonp',
        async: false,
        success: function (data) {
            $('#province').combobox({
                data: data,
                valueField: 'id',
                textField: 'name',
                editable: false,
                onChange: function () {
                    var provinceId = $('#province').combobox('getValue');
                    if(provinceId != ''){
                        initCity(provinceId);
                    } else {
                        initCity(-1);
                    }

                }
            });

        }
    });
}

function initCity(provinceId) {
    if(provinceId != ''){
        $.ajax({
            url: WebHelper.GetAddress(),
            data: {cid: provinceId},
            dataType: 'jsonp',
            async: false,
            success: function (data) {
                $('#city').combobox({
                    data: data,
                    valueField: 'id',
                    textField: 'name',
                    editable: false,
                    onChange: function () {

                        var cityId = $('#city').combobox('getValue');
                        if(cityId != ''){
                            initArea(cityId);
                        } else {
                            initArea(-1);
                        }
                    }
                });
            }
        });
    }

}

function initArea(cityId) {
    var province = $('#province').combobox('getText');
    var city = $('#city').combobox('getText');
    if(city == ''){
        city = -1;
    }

    $.ajax({
        url: '../../../common/queryAreasByProvinceAndCityAndArea.do',
        data: {
            provinceName: province,
            cityName: city
        },
        async: false,
        cache: false,
        processData: true, // 转换data成查询字符串。默认值为true
        success: function (data) {
            $('#area').combobox({
                data: data,
                valueField: 'id',
                textField: 'areaName',
                editable: false,
                onChange: function () {
                    // var aredId = $('#area').combobox('getValue');
                    // alert(areaId);
                    initStore();
                }
            });
        }
    });

}

function initStore() {
    var area = $('#area').combobox('getValue');
    if(area == ''){
        area = -1;
    }
    $.ajax({
        url: '../../../common/queryStoresByAreaId.do',
        data: {
            areaId : area
        },
        async: false,
        cache: false,
        processData: true, // 转换data成查询字符串。默认值为true
        success: function (data) {
            $('#store').combobox({
                data: data,
                valueField: 'id',
                textField: 'shopName',
                editable: false
            });
        }
    });
}

function initLotterAmountList() {
    $.ajax({
        url: '../../../../vas/activity/queryLotteryAmountList.do',
        data: {},
        async: false,
        cache: false,
        processData: true, // 转换data成查询字符串。默认值为true
        success: function (data) {
            var amountList = [];

            var optionAll = new Object();
            optionAll.name = "全部";
            optionAll.value = "";
            amountList[0] = optionAll;

for(var i=0;i<data.data.length;i++) {
    var option = new Object();
    option.name = data.data[i] + "元";
    option.value = data.data[i];
    amountList[i+1] = option;
}
$('#id_winning_amount').combobox({
    data: amountList,
    valueField: 'value',
    textField: 'name',
    editable: false
});
}
});
}


function init(url) {
    $("#id_order_change_datagrid").datagrid({
        url:url,
        singleSelect: false, //是否单选
        pagination: true, //分页控件
        pageSize:15,
        pageList:[30,15],
        autoRowHeight: true,
        fit: true,
        striped: false, //设置为true将交替显示行背景
        fitColumns: true,//设置是否滚动条
        nowrap: false,
        remotesort: true,
        checkOnSelect: true,
        selectOnCheck:true,
        method: "POST", //请求数据的方法
        loadMsg: '数据加载中,请稍候......',
        idField:'',
        queryParams:{'orderNo':'','orderType':''},
        columns:[
            [
                {field:'orderCode',title:'订单编号',width:150,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'luckyNumber',title:'幸运号',width:60,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'chargeMobile',title:'充值手机号',width:80,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'createTimeString',title:'下单时间',width:100,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'areaName',title:'下单县域',width:120,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'storeName',title:'下单网点',width:150,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'chargeAmount',title:'充值面额',width:60,align:'center',formatter:function(value,row, index) {
                    return value+"元";
                }},

                {field:'lotteryTypeName',title:'中奖类型',width:50,align:'center',formatter:function(value,row, index) {
                    if(null == value || value == ''){
                        return "-";
                    } else {
                        return value;
                    }
                }},
                {field:'cashCoupon',title:'中奖代金券码',width:80,align:'center',formatter:function(value,row, index) {
                    if(null == value || value == ''){
                        return "-";
                    } else {
                        return value;
                    }
                }},
                {field:'couponName',title:'代金券名称',width:80,align:'center',formatter:function(value,row, index) {
                	if(null == value || value == ''){
                		return "-";
                	} else {
                		return value;
                	}
                }},


                {field:'winningAmount',title:'中奖金额',width:50,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'lotteryStatusStr',title:'开奖状态',width:60,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'lotteryTimeString',title:'开奖时间',width:100,align:'center',formatter:function(value,row, index) {
                    if(null == value || value == ''){
                        return "-";
                    } else {
                        return value;
                    }
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

        },
        toolbar:'#tb'
    });

    pageopt = $('#id_order_change_datagrid').datagrid('getPager').data("pagination").options;
}

//条件搜索
function freesearch() {
    var orderCode = $("#id_order_code").val();
    var luckyNumber = $("#id_lucky_number").val();
    var chargeMobile = $("#id_charge_mobile").val();
    var chargeAmount = $("#id_charge_amount").textbox("getValue");
    var lotteryStatus = $("#id_lottery_status").textbox("getValue");

    var areaId = $("#area").textbox("getValue");
    var storeId = $("#store").textbox("getValue");

    var winningAmount = $("#id_winning_amount").textbox("getValue");

    var startCreateTime = $("#startDate").datebox("getValue");
    var endCreateTime = $("#endDate").datebox("getValue");
    var lotteryStartTime = $("#lottery_start_date").datebox("getValue");
    var lotteryEndTime = $("#lottery_end_date").datebox("getValue");
    var lotteryType = $("#id_lottery_type").textbox("getValue");
    var cashCoupon = $("#id_cash_coupon").val();

    $('#id_order_change_datagrid').datagrid("load",
        {
            orderCode:orderCode,
            luckyNumber:luckyNumber,
            chargeMobile:chargeMobile,
            chargeAmount:chargeAmount,
            lotteryStatus:lotteryStatus,
            areaId:areaId,
            storeId:storeId,
            winningAmount:winningAmount,
            startCreateTime:startCreateTime,
            endCreateTime:endCreateTime,
            lotteryType:lotteryType,
            cashCoupon:cashCoupon,
            lotteryStartTime:lotteryStartTime,
            lotteryEndTime:lotteryEndTime,
            

        });
}

//重置搜索
function resetSearch() {

    $("#id_order_code").textbox("setValue", '');
    $("#id_lucky_number").textbox("setValue", '');
    $("#id_charge_mobile").textbox("setValue", '');
    $("#id_charge_amount").combobox("setValue", '');
    $("#id_winning_amount").combobox("setValue", '');
    $("#id_lottery_status").combobox("setValue", '-1');

    $("#id_cash_coupon").textbox("setValue", '');
    $("#id_lottery_type").combobox("setValue", '');

    $("#province").combobox("setValue", '');
    $("#city").combobox("setValue", '');
    $("#area").combobox("setValue", '');
    $("#store").combobox("setValue", '');

    $("#startDate").textbox("setValue", '');
    $("#endDate").textbox("setValue", '');
    $("#lottery_start_date").textbox("setValue", '');
    $("#lottery_end_date").textbox("setValue", '');

    $('#id_order_change_datagrid').datagrid("load", {orderCode:''});
    $("#city").combobox("setValue", '');
    $("#area").combobox("setValue", '');
}

function opernYouMouth() {
    window.location.href = "lottery.html";
}

//导出excel
function exportExcel() {
    var rows = $("#id_order_change_datagrid").datagrid("getRows");
    if (rows == null || rows.length == 0) {
        alert("未有任何记录");
        return;
    }

    var orderCode = $("#id_order_code").val();
    var luckyNumber = $("#id_lucky_number").val();
    var chargeMobile = $("#id_charge_mobile").val();
    var chargeAmount = $("#id_charge_amount").textbox("getValue");
    var lotteryStatus = $("#id_lottery_status").textbox("getValue");

    var areaId = $("#area").textbox("getValue");
    var storeId = $("#store").textbox("getValue");

    var winningAmount = $("#id_winning_amount").textbox("getValue");

    var startCreateTime = $("#startDate").datebox("getValue");
    var endCreateTime = $("#endDate").datebox("getValue");
    var lotteryStartTime = $("#lottery_start_date").datebox("getValue");
    var lotteryEndTime = $("#lottery_end_date").datebox("getValue");

    window.location.href="./../../../vas/activity/exportReports.do?orderCode="+orderCode+"&luckyNumber="+luckyNumber+
        "&chargeMobile="+chargeMobile+"&chargeAmount="+chargeAmount+"&lotteryStatus="+lotteryStatus+
        "&winningAmount="+winningAmount+"&startCreateTime="+startCreateTime+"&endCreateTime="+endCreateTime+
        "&areaId="+areaId+"&storeId="+storeId+"&page="+1+"&rows="+100000
        +"&lotteryStartTime="+lotteryStartTime+"&lotteryEndTime="+lotteryEndTime;
}