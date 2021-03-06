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
            data: arr, 
            valueField: 'id',
            textField: 'categoryName',
            multiple: false,
            editable: false,
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
        processData: true,
        success: function (data) {
            $('#countryName').combobox({
                data: data,
                valueField: 'id',
                textField: 'areaName',
                editable: false,
                onChange: function () {
                    var areaId = $('#countryName').combobox('getValue');
                    initShop(areaId);
                }
            });
        }
    });
}
function getAreaId(){
	$.ajax({
		url: '../../common/getLoginInfo.do',
		async: false,
        success: function (data) {
        	console.info(data.bizId);
        	initShop(data.bizId);
        }
	});
}
function initShop(areaId) {
    $.ajax({
        url: '../../common/getShopByInputAreaId.do',
        data: {
        	areaId: areaId
        },
        async: false,
        cache: false,
        processData: true,
        success: function (data) {
        	console.error(data);
        	
            $('#storeName').combobox({
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
	getAreaId();
    //initProvince();
    //loadLevel1();
    
    $("#search").on('click',function(){
    	
    	if(!$('#governmentSearchForm').form('validate')){
      		 $.messager.alert({
                   title:'警告提示',
                   width:400,
                   msg:'<div class="content">开始时间不得大于结束时间,请重新选择</div>',
                   ok:'<i class="i-ok"></i> 确定',
                   icon:'warning'
      		 });
      		 return;
       	}
    	
        $("#dataGrid").datagrid('load', {
            'hcpOrderCode': $('#hcpOrderCode').textbox('getValue'),
            'supplierOrderId': $('#supplierOrderId').textbox('getValue'),
            //'supplierName': $('#supplierName').textbox('getValue'),
            'startDate': $('#startDate').datebox('getValue'),
            'endDate': $('#endDate').datebox('getValue'),
            'cpStartDate': $('#cpStartDate').datebox('getValue'),
            'cpEndDate': $('#cpEndDate').datebox('getValue'), 
            'sqStartDate': $('#sqStartDate').datebox('getValue'), 
            'sqEndDate': $('#sqEndDate').datebox('getValue'), 
            'userName': $('#userName').textbox('getValue'),
            'idCardId': $('#idCardId').textbox('getValue'),	 
            'linkName': $('#linkName').textbox('getValue'), 
            'linkPhone': $('#linkPhone').textbox('getValue'),  
            'payStatus': $('#payStatus').combobox('getValue'),  
            'storeName': $('#storeName').combobox('getText'),  
            'status': $('#status').combobox('getValue')  
        });
    });
    
    $("#clear").on('click', function () {
        window.location.reload();
    });
    
    $("#export").on('click', function () {
        var url = "../../hcp/exportOrderReportsByPlatform.do?";
        url = url + "hcpOrderCode=" + $('#hcpOrderCode').textbox('getValue');
        url = url + "&supplierOrderId=" + $('#supplierOrderId').textbox('getValue');
        url = url + "&startDate=" + $('#startDate').datebox('getValue');
        url = url + "&endDate=" + $('#endDate').datebox('getValue');
        url = url + "&cpStartDate=" + $('#cpStartDate').datebox('getValue');
        url = url + "&cpEndDate=" + $('#cpEndDate').datebox('getValue');
        url = url + "&sqStartDate=" + $('#sqStartDate').datebox('getValue');
        url = url + "&sqEndDate=" + $('#sqEndDate').datebox('getValue');
        url = url + "&userName=" + $('#userName').textbox('getValue');
        url = url + "&idCardId=" + $('#idCardId').textbox('getValue');
        url = url + "&linkName=" + $('#linkName').textbox('getValue');
        url = url + "&linkPhone=" + $('#linkPhone').textbox('getValue');
        url = url + "&payStatus=" + $('#payStatus').combobox('getValue');
        url = url + "&storeName=" + $('#storeName').textbox('getText');
        url = url + "&status=" + $('#status').combobox('getValue');
        
        window.location.href = url;
    });

    $('#dataGrid').datagrid({
        url: '../../hcp/getHcpOrderInfosPage.do',
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
            //{field: 'subBusinessName', title: '服务分类', width: 100, align: 'center'},
            {field: 'hcpOrderCode', title: '订单编号', width: 150, align: 'center'},
            {field: 'supplierOrderId', title: '供应商订单编号', width: 100, align: 'center'},
            {field: 'trainCode', title: '车次', width: 100, align: 'center'},
            {field: 'stationStartTime', title: '开车时间', width: 100, align: 'center'},
            {field: 'fromStation', title: '出发地', width: 100, align: 'center'},
            {field: 'arriveStation', title: '目的地', width: 100, align: 'center'},
            {field: 'seatTypeStr', title: '坐席', width: 100, align: 'center'},
            {field: 'ticketTypeStr', title: '票种', width: 100, align: 'center'},
            {field: 'userName', title: '乘车人', width: 100, align: 'center'},
            {field: 'idCardId', title: '乘车人身份证', width: 100, align: 'center'},
            {field: 'payStatusStr', title: '订单状态', width: 100, align: 'center'},
            {field: 'refundStatusStr', title: '退票状态', width: 150, align: 'center'},
            {field: 'statusStr', title: '退款状态', width: 150, align: 'center'},
            {field: 'createTimeStr', title: '支付时间', width: 100, align: 'center'},
            {field: 'cpDateTimeStr', title: '出票时间', width: 100, align: 'center'},
            {field: 'sqDateTimeStr', title: '申请退票时间', width: 100, align: 'center'},
            {field: 'originalAmount', title: '支付金额', width: 150, align: 'center'},
            {field: 'costingAmount', title: '票价', width: 150, align: 'center'},
            {field: 'linkName', title: '购票联系人姓名', width: 100, align: 'center'},
            {field: 'linkPhone', title: '购票联系人电话', width: 150, align: 'center'},
            {field: 'storeName', title: '下单网点', width: 150, align: 'center'}
        ]],
    });

});
