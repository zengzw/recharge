var show_ = " — ";


function reloadGrid() {
    $('#dataGrid').datagrid('reload');
}

function showByMsg(showType, msg) {
    $.messager.show({
        title: '',
        msg: '<i class="i-l i-l-' + showType + '"></i><span>' + msg + '</span>',
        showType: 'fade',
        timeout: 1000,
        height: 150,
        width: 300,
        style: {
            right: '',
            bottom: ''
        }
    });
}
 
function add() {
    location.href = 'add.html';
}
 
function isNotBlank(str) {
    if (undefined === str || "" === str || null === str) {
        return false;
    } else {
        return true;
    }
}
 
function isLogin() {
    $.ajax({
        type: 'post',
        url: '/government/isLogin.do',
        success: function (result) {
            console.log(result);
            if (result) {
                alert('未登录!');
            }
        },
        error: function (e) {
            alert(e);
        }
    })
}
 
function getRowsCount() {
    var rows = $('#dataGrid').datagrid('getRows');
    return rows.length;
}

function getRowByIndex(index) {
    var row = $('#dataGrid').datagrid('getData').rows[index];
    return row;
}
 
function loadCategory() {
    $.getJSON('/infoCategory/getInfoCategoryByUserIdAndServerTypeUse.do', function (json) {
        var data = json;
        var arr = [];
        arr.push({'id': -1, 'categoryName': '全部'});
        for (var i = 0; i < data.length; i++) {
            arr.push({'id': data[i].id, 'categoryName': data[i].categoryName});
        }
        $('#category').combobox({
            data: arr, //此为重点
            valueField: 'id',
            textField: 'categoryName',
            multiple: false, //不允许多选
            editable: false,//禁止编辑
            onLoadSuccess: function () {

            },
            onSelect: function (record) {
                $("#dataGrid").datagrid('load', {
                    categoryName: record.categoryName
                });
            }
        });
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
                    initCity(provinceId);
                }
            });

        }
    });
}
 
function initCity(provinceId) {
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
                    initArea(cityId);
                }
            });
        }
    });
}

 
function initArea(cityId) {
    var province = $('#province').combobox('getText');
    var city = $('#city').combobox('getText');
    $.ajax({
        url: '../../common/queryAreasByProvinceAndCityAndArea.do',
        data: {
            provinceName: province,
            cityName: city
        },
        async: false,
        cache: false,
        processData: true, // 转换data成查询字符串。默认值为true
        success: function (data) {
            $('#countryName').combobox({
                data: data,
                valueField: 'id',
                textField: 'areaName',
                editable: false
            });
        }
    });
}
 
function loadLevel1() {
    $.ajax({
        url: '../../valueAddedServicesOther/getBusinessInfoParent.do',
        async: false,
        success: function (data) {
            $('#level1').combobox({
                data: data,
                valueField: 'businessCode',
                textField: 'businessName',
                editable: false
                /*,
                onChange: function () {
                    var parentCode = $('#level1').combobox('getValue');
                    loadLevel2(parentCode);
                }*/
            });

        }
    });
}
 
function loadLevel2(parentCode) {
    $.ajax({
        url: '../../valueAddedServicesOther/getBusinessInfoByParentByParent.do',
        data: {parentCode: parentCode},
        async: false,
        success: function (data) {
            $('#level2').combobox({
                data: data,
                valueField: 'businessCode',
                textField: 'businessName',
                editable: false
            });
        }
    });
}

$(function () {
    initProvince();
    loadLevel1();
     
    $("#search").on('click', function () {
        //退款状态：0.初始状态，未发生退款，1.退款中 。2.已退款，3，退款失败
        //支付状态：1：待支付。2：支付中。3：缴费中。4：交易成功，5：交易失败，6：交易关闭，7：支付异常，8，缴费异常，9，撤销
        $("#dataGrid").datagrid('load', {
            'memberMobile': $('#memberMobile').textbox('getValue'),  //充值缴费联系人电话
            'rechargeUserName': $('#rechargeUserName').textbox('getValue'),	//充值缴费联系人姓名
            'refundStatus': $('#refundStatus').combobox('getValue'),	//退款状态
            'supplierName': $('#supplierName').textbox('getValue'),	//服务供应商
            'countryName': $('#countryName').combobox('getText'),	//县域中心
            'province': $('#province').combobox('getText'), //省份
            'city': $('#city').combobox('getText'), //市
            'rechargeUserCode': $('#rechargeUserCode').textbox('getValue'), //充值缴费账号
            'payStatus': $('#payStatus').combobox('getValue'), //订单状态
            'startDate': $('#startDate').datebox('getValue'), //下单开始时间
            'endDate': $('#endDate').datebox('getValue'), //下单结束时间
            'chargeCode': $('#chargeCode').textbox('getValue'),  //订单编号
            //'subBusinessCode': $('#level2').combobox('getValue'), //业务分类
            'businessCode': $('#level1').combobox('getValue') //增值服务类型
            
        });
    });
     
    $("#clear").on('click', function () {
        window.location.reload();
    });
     
    $("#export").on('click', function () {

        var url = "../../valueAddedServicesOther/exportOrderReportsByPlatform.do?";
        url = url + "memberMobile=" + $('#memberMobile').textbox('getValue');
        url = url + "&rechargeUserName=" + $('#rechargeUserName').textbox('getValue');
        url = url + "&refundStatus=" + $('#refundStatus').combobox('getValue');
        url = url + "&supplierName=" + $('#supplierName').textbox('getValue');
        url = url + "&countryName=" + $('#countryName').combobox('getText');
        url = url + "&province=" + $('#province').combobox('getText');
        url = url + "&city=" + $('#city').combobox('getText');
        url = url + "&rechargeUserCode=" + $('#rechargeUserCode').textbox('getValue');
        url = url + "&payStatus=" + $('#payStatus').combobox('getValue');
        url = url + "&startDate=" + $('#startDate').datebox('getValue');
        url = url + "&endDate=" + $('#endDate').datebox('getValue');
        url = url + "&chargeCode=" + $('#chargeCode').textbox('getValue');
        url = url + "&businessCode=" + $('#level1').combobox('getValue');
        
        window.location.href = url;
    });

    $('#dataGrid').datagrid({
        url: '../../valueAddedServicesOther/getOrderReportsByPlatForm.do',
        singleSelect: true,
        rownumbers: false,
        pagination: true,
        method: "GET",
        autoRowHeight: true,
        //fit : true,
        striped: false, 
        nowrap: false,
        //rownumbers:true,
        //pageList: [10, 20, 50],
        pageSize: 10,
        loadMsg: '数据加载中,请稍候......',
        loadFilter: function (data) {
            return data;
        },
        columns: [[
            {field: 'id', title: '序号', width: 50, align: 'center'},
            {field: 'businessName', title: '增值服务类型', width: 100, align: 'center'},
            //{field: 'subBusinessName', title: '服务分类', width: 100, align: 'center'},
            {field: 'chargeCode', title: '订单编号', width: 150, align: 'center'},
            {field: 'rechargeUserCode', title: '充值缴费账号', width: 100, align: 'center'},
            {
                field: 'payStatus', title: '订单状态', width: 100, formatter: function (value, data, index) {
            	if (value == 1) {
                    return "待支付";
                }else if(value == 2){
                	return "支付中";
                }else if(value == 3){
                	return "缴费中";
                }else if(value == 4){
                	return "交易成功";
                }else if(value == 5){
                	return "交易失败";
                }else if(value == 6){
                	return "交易关闭";
                }else if(value == 7){
                	return "支付异常";
                }else if(value == 8){
                	return "缴费异常";
                }else if(value == 9){
                	return "撤销";
                }else if(value == 10){
                	return "扣款成功";
                }else{
                	return "非法状态";
                }
                /*if (value == 1 || value == 2 || value == 3 || value == 10) {
                    return "充值中";
                } else if (value == 4 || value == 11 || value == 12) {
                    return "交易成功";
                } else if (value == 5 || value == 6 || value == 7 || value == 8) {
                    return "交易失败";
                }*/
            }
            },
            {field: 'createTimeStr', title: '下单时间', width: 100, align: 'center'},
            {field: 'rechargeUserName', title: '缴费户主姓名', width: 100, align: 'center'},
            {field: 'memberMobile', title: '充值缴费联系人电话', width: 150, align: 'center'},
            {field: 'memberName', title: '充值缴费联系人姓名', width: 150, align: 'center'},
            {field: 'storeName', title: '下单网点', width: 200, align: 'center'},
            {field: 'supplierName', title: '服务供应商', width: 200, align: 'center'},
            {field: 'originalAmount', title: '应缴金额（元）', width: 100, align: 'center'},
            {field: 'realAmount', title: '实缴金额（元）', width: 100, align: 'center'},
            //{field:'serialNumber',title:'流水号',width:150,align:'center'},
            {
                field: 'refundStatus',
                title: '退款状态',
                width: 100,
                align: 'center',
                formatter: function (value, data, index) {
                    if (value == 0) {
                        return show_;
                    } else if (value == 1) {
                        return "退款中";
                    } else if (value == 2) {
                        return "退款成功";
                    } else if (value == 3) {
                        return "退款失败";
                    }
                }
            },
            {
                field: 'refundAmount',
                title: '退款金额（元）',
                width: 100,
                align: 'center',
                formatter: function (value, data, index) {
                    if (isNotBlank(value)) {
                        return value;
                    } else {
                        return show_;
                    }
                }
            },
            {
                field: 'refundCode',
                title: '退款单号',
                width: 150,
                align: 'center',
                formatter: function (value, data, index) {
                    if (isNotBlank(value)) {
                        return value;
                    } else {
                        return show_;
                    }
                }
            },
            {field: 'costingAmount', title: '成本价（元）', width: 100, align: 'center'},
            {field: 'liftCoefficient', title: '提点系数（金额）/销售差额（元）', width: 300, align: 'center'},
            //{field: 'platformDividedRatio', title: '平台分成比', width: 100, align: 'center'},
            //{field: 'platformDividedYuan', title: ' 平台分成（元）', width: 100, align: 'center'},
            //{field: 'areaDividedRatio', title: '县域分成比', width: 100, align: 'center'},
            {field: 'areaCommissionRatio', title: '县域佣金比(%)', width: 100, align: 'center'},
            {field: 'areaCommissionYuan', title: '县域佣金（元）', width: 100, align: 'center'},
            {field: 'storeCommissionRatio', title: '网点佣金比(%)', width: 100, align: 'center'},
            {field: 'storeCommissionYuan', title: '网点佣金（元）', width: 100, align: 'center'}
        ]],
    });

});
