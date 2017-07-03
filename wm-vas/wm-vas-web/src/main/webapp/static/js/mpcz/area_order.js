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
 
function initShop(){
	$.ajax({
        url: '../../valueAddedServicesOther/getShopByAreaId.do',
        success: function (data) {
        	console.info(data);
            $('#shopName').combobox({
                data: data,
                valueField: 'id',
                textField: 'shopName',
                editable: false
            });

        }
    });
}

function loadMoney(){
    $.ajax({
        url: '../../vas/phonefare/test/findPhoneFaceValues.do',
        type: 'post',
        async: true,
        success: function (data) {
            var sortData = data.data.rows.sort(getSortFun('asc', 'value'));
            $('#saleAmount').combobox({
                data: sortData,
                valueField: 'value',
                textField: 'value',
                editable: false
            });
        },
        complete:function(){
            $('#saleAmount').combobox('setValues', ['请选择','-1']);
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
    // initProvince();
    loadLevel1();
    initShop();
    loadMoney();
    $("#search").on('click', function () {
        $("#dataGrid").datagrid('load', {
            'phoneOrderCode': $('#phoneOrderCode').textbox('getValue'),  //订单编号
            'startDate': $('#startDate').datebox('getValue'), //下单开始时间
            'endDate': $('#endDate').datebox('getValue'), //下单结束时间
            'memberMobile' : $('#memberMobile').textbox('getValue'),	//充值手机号
            'payUserName' : $('#payUserName').textbox('getValue'),	//充值联系人姓名
            'mobile' : $('#mobile').textbox('getValue'),	//充值联系人电话
            'payStatus': $('#payStatus').combobox('getValue'), //订单状态
            'saleAmount' : $('#saleAmount').combobox('getValue'), //充值金额
            'storeCode': $('#shopName').combobox('getValue'), //网点名称
            'refundStatus':$('#refundStatus').combobox('getValue') //退款状态
        });
    });

    $("#clear").on('click', function () {
        window.location.reload();
    });

    $("#export").on('click', function () {
        var url = "../../phone/exportOrderReportsByPlatform.do?";
        url = url + "phoneOrderCode=" + $('#phoneOrderCode').textbox('getValue');
        url = url + "&startDate=" + $('#startDate').textbox('getValue');
        url = url + "&endDate=" + $('#endDate').textbox('getValue');
        url = url + "&memberMobile=" + $('#memberMobile').textbox('getValue');
        url = url + "&payUserName=" + $('#payUserName').textbox('getValue');
        url = url + "&mobile=" + $('#mobile').textbox('getValue');
        url = url + "&payStatus=" + $('#payStatus').combobox('getValue');
        url = url + "&saleAmount=" + $('#saleAmount').combobox('getValue');
        url = url + "&storeCode=" + $('#shopName').textbox('getValue');
        url = url + "&refundStatus=" + $('#refundStatus').combobox('getValue');
        window.location.href = url;
    });


    $('#dataGrid').datagrid({
        url: '../../phone/getPhoneOrderInfoReportsPageForArea.do',
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
            {field: 'phoneOrderCode', title: '订单编号', width: 150, align: 'center'},
            {field: 'rechargePhone', title: '充值手机号', width: 100, align: 'center'},
            {field: 'payStatus', title: '订单状态', width: 100, align: 'center'},
            {field: 'createTime', title: '支付时间', width: 100, align: 'center'},
            {field: 'rechargeSuccssTime', title: '订单成功时间', width: 100, align: 'center'},
            {field: 'payUserName', title: '充值联系人姓名', width: 100, align: 'center'},
            {field: 'mobile', title: '充值联系人电话', width: 150, align: 'center'},
            {field: 'storeName', title: '下单网点', width: 200, align: 'center'},
            {field: 'storeNo', title: '下单网点编号', width: 200, align: 'center'},
            {field: 'saleAmount', title: '充值面额', width: 200, align: 'center'},
            {field: 'originalAmount', title: '订单金额', width: 200, align: 'center'},
            {field: 'platformYH', title: '平台优惠', width: 200, align: 'center',
                formatter: function (value, data, index) {
                    return data.originalAmount - data.realAmount;

                }},
            {field: 'realAmount', title: '支付金额', width: 100, align: 'center'},
            {
                field: 'status',
                title: '退款状态',
                width: 100,
                align: 'center'
            },
            {
                field: 'praRealAmount',
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
                field: 'refundAmountCode',
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
            {field: 'areaCommissionRatio', title: '县域佣金比(%)', width: 100, align: 'center'},
            {field: 'areaCommissionRatioStr', title: '县域佣金（元）', width: 100, align: 'center'},
            {field: 'storeCommissionRatio', title: '网点佣金比(%)', width: 100, align: 'center'},
            {field: 'storeCommissionRatioStr', title: '网点佣金（元）', width: 100, align: 'center'}
        ]],
    });

});
