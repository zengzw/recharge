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
    $.ajax({
        url: '../../common/queryStoresByAreaId.do',
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

/**
 * 保留2位小数
 * @param x
 * @returns {Number}
 */
function returnFloat(value){
    var value=Math.round(parseFloat(value)*100)/100;
    var xsd=value.toString().split(".");
    if(xsd.length==1){
        value=value.toString()+".00";
        return value;
    }
    if(xsd.length>1){
        if(xsd[1].length<2){
            value=value.toString()+"0";
        } return value;
    }
}

$(function () {
    initProvince();
    loadLevel1();
    loadMoney();
    $("#clear").on('click', function () {
        window.location.reload();
    });
    $("#search").on('click', function () {
        //退款状态：0.初始状态，未发生退款，1.退款中 。2.已退款，3，退款失败
        //支付状态：1：待支付。2：支付中。3：缴费中。4：交易成功，5：交易失败，6：交易关闭，7：支付异常，8，缴费异常，9，撤销
        $("#dataGrid").datagrid('load', {
            'phoneOrderCode': $('#phoneOrderCode').textbox('getValue'),  //订单编号
            'supplierOrderId': $('#supplierOrderId').textbox('getValue'),	//供应商订单编号
            'supplierName': $('#supplierName').textbox('getValue'),	//服务供应商
            'startDate': $('#startDate').datebox('getValue'), //下单开始时间
            'endDate': $('#endDate').datebox('getValue'), //下单结束时间
            'memberMobile' : $('#memberMobile').textbox('getValue'),	//充值手机号
            'payUserName' : $('#payUserName').textbox('getValue'),	//充值联系人姓名
            'mobile' : $('#mobile').textbox('getValue'),	//充值联系人电话
            'payStatus': $('#payStatus').combobox('getValue'), //订单状态
            'saleAmount' : $('#saleAmount').combobox('getValue'), //充值金额
            'province': $('#province').combobox('getText'), //省份
            'city': $('#city').combobox('getText'), //市
            'countryCode': $('#area').combobox('getValue'),	//县域中心
            'storeCode': $('#store').combobox('getValue'), //充值缴费账号
            'refundStatus':$('#refundStatus').combobox('getValue'), //退款状态,
            'sources':$('#sources').combobox('getValue')
        });
    });
    
    $("#export").on('click', function () {
        var url = "../../phone/exportOrderReportsByPlatform.do?";
        url = url + "phoneOrderCode=" + $('#phoneOrderCode').textbox('getValue');
        url = url + "&supplierOrderId=" + $('#supplierOrderId').textbox('getValue');
        url = url + "&supplierName=" + $('#supplierName').textbox('getValue');
        url = url + "&startDate=" + $('#startDate').datebox('getValue');
        url = url + "&endDate=" + $('#endDate').datebox('getValue');
        url = url + "&memberMobile=" + $('#memberMobile').textbox('getValue');
        url = url + "&payUserName=" + $('#payUserName').textbox('getValue');
        url = url + "&mobile=" + $('#mobile').textbox('getValue');
        url = url + "&payStatus=" + $('#payStatus').combobox('getValue');
        url = url + "&saleAmount=" + $('#saleAmount').combobox('getValue');
        url = url + "&countryCode=" + $('#area').combobox('getValue');
        url = url + "&province=" + $('#province').combobox('getText');
        url = url + "&city=" + $('#city').combobox('getText');
        url = url + "&storeCode=" + $('#store').combobox('getValue');
        url = url + "&refundStatus=" + $('#refundStatus').combobox('getValue');
        url = url + "&sources=" + $('#sources').combobox('getValue');
        window.location.href = url;
    });

    $('#dataGrid').datagrid({
        url: '../../phone/getPhoneOrderInfoReportsPageForPlatform.do',
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
            //{field: 'id', title: '序号', width: 50, align: 'center'},
            //{field: 'businessName', title: '增值服务类型', width: 100, align: 'center'},
            {field: 'sources', title: '订单来源', width: 100, align: 'center',
            	formatter: function (value, data, index) {
            		if(value == "TSH" || value == ""){
            			return "淘实惠";
            		}else if(value == "GX"){
            			return "顾乡";
            		}
            		
            		return "";
            		
                }},
            {field: 'phoneOrderCode', title: '订单编号', width: 150, align: 'center'},
            {field: 'openPlatformNo', title: '外部订单编号', width: 100, align: 'center',
            	 formatter: function (value, data, index) {
                     if (isNotBlank(value)) {
                         return value;
                     } else {
                         return "-";
                     }
                 }
            },
            {field: 'supplierOrderId', title: '供应商订单编号', width: 100, align: 'center',
	           	 formatter: function (value, data, index) {
	                 if (isNotBlank(value)) {
	                     return value;
	                 } else {
	                     return "-";
	                 }
	             }
            },
            {field: 'supplierName', title: '服务供应商', width: 100, align: 'center',
            	 formatter: function (value, data, index) {
	                 if (isNotBlank(value)) {
	                     return value;
	                 } else {
	                     return "-";
	                 }
	             }
            },
            {field: 'rechargePhone', title: '充值手机号', width: 100, align: 'center'},
            {field: 'payStatus', title: '订单状态', width: 100, align: 'center'},
            {field: 'createTime', title: '支付时间', width: 100, align: 'center'},
            {field: 'rechargeSuccssTime', title: '订单成功时间', width: 100, align: 'center',
            	 formatter: function (value, data, index) {
	                 if (isNotBlank(value)) {
	                     return value;
	                 } else {
	                     return "-";
	                 }
	             }
            },
            {field: 'payUserName', title: '充值联系人姓名', width: 100, align: 'center',
            	 formatter: function (value, data, index) {
	                 if (isNotBlank(value)) {
	                     return value;
	                 } else {
	                     return "-";
	                 }
	             }
            },
            {field: 'mobile', title: '充值联系人电话', width: 150, align: 'center',
            	 formatter: function (value, data, index) {
	                 if (isNotBlank(value)) {
	                     return value;
	                 } else {
	                     return "-";
	                 }
	             }
            },
            {field: 'countryName', title: '下单县域中心', width: 200, align: 'center'},
            {field: 'countryCode', title: '下单县域编号', width: 100, align: 'center'},
            {field: 'storeName', title: '下单网点', width: 200, align: 'center'},
            {field: 'storeNo', title: '下单网点编号', width: 100, align: 'center'},
            {field: 'saleAmount', title: '充值面额', width: 100, align: 'center'},

            {field: 'originalAmount', title: '销售价', width: 100, align: 'center'},
            {field: 'platformYH', title: '平台优惠', width: 100, align: 'center',
                formatter: function (value, data, index) {
                    var m = returnFloat(data.originalAmount - data.realAmount);
                    if(m == 0){
                        return "—";
                    } else {
                        return m;
                    }

            }},

            {field: 'realAmount', title: '支付金额', width: 100, align: 'center'},
            {field: 'costingAmount', title: '成本价', width: 100, align: 'center',
                formatter: function (value, data, index) {
                if (value == null || value == '') {
                    return "—";
                } else {
                    return value;
                }
            }},
            {field: 'tdAmount', title: '提点金额', width: 100, align: 'center'},
            {
                field: 'status',
                title: '退款状态',
                width: 100,
                align: 'center',
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

            {field: 'platformDividedRatio', title: '平台分成比(%)', width: 100, align: 'center'},
            {field: 'platformDividedRatioStr', title: '平台佣金（元）', width: 100, align: 'center'},
            {field: 'areaDividedRatio', title: '县域分成比(%)', width: 100, align: 'center'},
            {field: 'areaCommissionRatio', title: '县域佣金比(%)', width: 100, align: 'center'},
            {field: 'areaCommissionRatioStr', title: '县域佣金（元）', width: 100, align: 'center'},
            {field: 'storeCommissionRatio', title: '网点佣金比(%)', width: 100, align: 'center'},
            {field: 'storeCommissionRatioStr', title: '网点佣金（元）', width: 100, align: 'center'}
        ]],
    });

});
