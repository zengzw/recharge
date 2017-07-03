var show_ = " — ";
/**
 * 重载表格
 */
function reloadGrid() {
    $('#dataGrid').datagrid('reload');
}


/**
 * 根据显示类型和显示文件返回提示
 * @param showType
 * @param msg
 */
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
/**
 * 添加页面跳转
 */
function add() {
    location.href = 'add.html';
}
/**
 * 参数判断
 * @param str
 * @returns {Boolean}
 */
function isNotBlank(str) {
    if (undefined === str || "" === str || null === str) {
        return false;
    } else {
        return true;
    }
}
/**
 * 判断是否登录
 */
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
/**
 * 获取表格当前页的行
 * @returns
 */
function getRowsCount() {
    var rows = $('#dataGrid').datagrid('getRows');
    return rows.length;
}

function getRowByIndex(index) {
    var row = $('#dataGrid').datagrid('getData').rows[index];
    return row;
}
//加载分类下拉框
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

//加载省
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
// 加载市
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

// 加载县域列表
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
                editable: false,
                onChange: function () {
                    var areaId = $('#countryName').combobox('getValue');
                    initShop(areaId);
                }
            });
        }
    });
}
//获取县域id
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

//加载县域下面的网点
function initShop(areaId) {
    $.ajax({
        url: '../../common/getShopByInputAreaId.do',
        data: {
        	areaId: areaId
        },
        async: false,
        cache: false,
        processData: true, // 转换data成查询字符串。默认值为true
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

//加载第一级
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
//加载第二级
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
	//获取县域信息
	
    initProvince();
    //loadLevel1();
    //搜索
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
            'hcpOrderCode': $('#hcpOrderCode').textbox('getValue'),  //订单编号
            'supplierOrderId': $('#supplierOrderId').textbox('getValue'),	//供应商订单编号
            'supplierName': $('#supplierName').textbox('getValue'),	//服务供应商
            'startDate': $('#startDate').datebox('getValue'), //开始-下单时间
            'endDate': $('#endDate').datebox('getValue'), //结束-下单时间
            'cpStartDate': $('#cpStartDate').datebox('getValue'), //开始-出票时间
            'cpEndDate': $('#cpEndDate').datebox('getValue'), //结束-出票时间
            'userName': $('#userName').textbox('getValue'),	//乘车人
            'idCardId': $('#idCardId').textbox('getText'),	//乘车人身份证
            'linkName': $('#linkName').textbox('getText'), //购票联系人姓名
            'linkPhone': $('#linkPhone').textbox('getText'), //购票联系人电话
            'payStatus': $('#payStatus').combobox('getValue'), //订单状态
            'storeName': $('#storeName').combobox('getText'), //所属区域-网点
            'status': $('#status').textbox('getValue'),  //退款状态
            'province': $('#province').combobox('getText'),  //省
            'city': $('#city').combobox('getText'),  //市
            'countryName': $('#countryName').combobox('getText')  //县
        });
    });
    //清空
    $("#clear").on('click', function () {
        window.location.reload();
    });
  //导出
    $("#export").on('click', function () {
        var url = "../../hcp/exportOrderReportsByPlatform.do?";
        url = url + "hcpOrderCode=" + $('#hcpOrderCode').textbox('getValue');
        url = url + "&supplierOrderId=" + $('#supplierOrderId').textbox('getValue');
        url = url + "&startDate=" + $('#startDate').datebox('getValue');
        url = url + "&endDate=" + $('#endDate').datebox('getValue');
        url = url + "&cpStartDate=" + $('#cpStartDate').datebox('getValue');
        url = url + "&cpEndDate=" + $('#cpEndDate').datebox('getValue');
        
        url = url + "&userName=" + $('#userName').textbox('getValue');
        url = url + "&idCardId=" + $('#idCardId').textbox('getValue');
        url = url + "&linkName=" + $('#linkName').textbox('getValue');
        url = url + "&linkPhone=" + $('#linkPhone').textbox('getValue');
        url = url + "&payStatus=" + $('#payStatus').combobox('getValue');
        url = url + "&storeName=" + $('#storeName').textbox('getText');
        url = url + "&status=" + $('#status').combobox('getValue');
        url = url + "&supplierName=" + $('#supplierName').textbox('getValue');
        url = url + "&province=" + $('#province').combobox('getText');
        url = url + "&city=" + $('#city').combobox('getText');
        url = url + "&countryName=" + $('#countryName').combobox('getText');
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
        striped: false, // 设置为true将交替显示行背景
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
            {field: 'supplierName', title: '服务供应商', width: 100, align: 'center'},
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
            {field: 'province', title: '省', width: 150, align: 'center'},
            {field: 'city', title: '市', width: 150, align: 'center'},
            {field: 'countryName', title: '下单县域', width: 150, align: 'center'},
            {field: 'storeName', title: '下单网点', width: 150, align: 'center'}
        ]],
    });

});
