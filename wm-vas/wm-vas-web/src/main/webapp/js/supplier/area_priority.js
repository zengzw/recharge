
function del(id) {
    $.messager.confirm({
        width: 400,
        title: '删除提示',
        msg: '<div class="content">你确认要删除这条记录吗?</div>',
        ok: '<i class="i-ok"></i> 确定',
        cancel: '<i class="i-close"></i> 取消',
        fn: function (r) {
            if (r) {
                $.ajax({
                    type: 'post',
                    url: '../../government/deleteGovernment.do',
                    async: false,
                    data: {
                        id: id
                    },
                    success: function (result) {
                        showByMsg('info', '删除成功!');
                        reloadGrid();
                    },
                    error: function (e) {
                        alert(e);
                    }
                });
            } else {

            }
        }
    });
}
 
function view(supplierCode) {
    location.href = 'view.html?supplierCode=' + supplierCode;
}
 
function edit(supplierCode) {
    location.href = 'edit.html?supplierCode=' + supplierCode;
}
 
function areaView(supplierCode, company) {
    location.href = 'area_list.html?supplierCode=' + supplierCode + "&company=" + company;
}

 
function DJDFprioritySetting(businessCode, countryCode) {
    location.href = 'service_area_priority_setting.html?businessCode=' + businessCode + "&countryCode=" + countryCode;
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
 
function loadDataGridDF(){
	$('#dataGridDF').datagrid({
        url: '../../vas/area/supplier/areas',
        striped: false,
        fitColumns: true,
        singleSelect: true,
        rownumbers: false,
        pagination: true,
        method: "GET",
        //rownumbers:true,
        //pageList: [10, 20, 50],
        pageSize: 10,
        loadFilter: function (data) {
            console.log(data);
            return data.data;
        },
        queryParams: {
            businessCode: 'DJDF'
        },
        columns: [[
            {field: 'province', title: '省', width: 50},
            {field: 'city', title: '市', width: 60},
            {field: 'countryName', title: '县域中心', width: 120},
            {field: 'supplierStr', title: '增值服务供应商优先级排序', width: 500},
            {
                field: 'cz',
                title: '操作',
                align: 'center',
                width: 200,
                fitColumns: true,
                formatter: function (value, data, index) {
                    var temp = "";
                    temp += '<a class="btn btn-l" style="color:#7b9be6" href="javascript:;" onclick="DJDFprioritySetting(' + "'" + data.businessCode + "'" + ',' + data.countryCode + ')"><i></i>优先级设置</a>';
                    return temp;
                }
            }
        ]],
    });
}
 
function loadDataGridHCP(){
	$('#dataGridHCP').datagrid({
        url: '../../vas/area/supplier/areas',
        striped: false,
        fitColumns: true,
        singleSelect: true,
        rownumbers: false,
        pagination: true,
        method: "GET",
        //rownumbers:true,
        //pageList: [10, 20, 50],
        pageSize: 10,
        loadFilter: function (data) {
            console.log(data);
            return data.data;
        },
        queryParams: {
            businessCode: 'HCP'
        },
        columns: [[
            {field: 'province', title: '省', width: 50},
            {field: 'city', title: '市', width: 60},
            {field: 'countryName', title: '县域中心', width: 120},
            {field: 'supplierStr', title: '增值服务供应商优先级排序', width: 500},
            {
                field: 'cz',
                title: '操作',
                align: 'center',
                width: 200,
                fitColumns: true,
                formatter: function (value, data, index) {
                    var temp = "";
                    temp += '<a class="btn btn-l" style="color:#7b9be6" href="javascript:;" onclick="DJDFprioritySetting(' + "'" + data.businessCode + "'" + ',' + data.countryCode + ')"><i></i>优先级设置</a>';
                    return temp;
                }
            }
        ]],
    });
}

function loadDataGridMPCZ(){
	$('#dataGridMPCZ').datagrid({
        url: '../../vas/area/supplier/areas',
        striped: false,
        fitColumns: true,
        singleSelect: true,
        rownumbers: false,
        pagination: true,
        method: "GET",
        //rownumbers:true,
        //pageList: [10, 20, 50],
        pageSize: 10,
        loadFilter: function (data) {
            console.log(data);
            return data.data;
        },
        queryParams: {
            businessCode: 'MPCZ'
        },
        columns: [[
            {field: 'province', title: '省', width: 50},
            {field: 'city', title: '市', width: 60},
            {field: 'countryName', title: '县域中心', width: 120},
            {field: 'supplierStr', title: '增值服务供应商优先级排序', width: 500},
            {
                field: 'cz',
                title: '操作',
                align: 'center',
                width: 200,
                fitColumns: true,
                formatter: function (value, data, index) {
                    var temp = "";
                    temp += '<a class="btn btn-l" style="color:#7b9be6" href="javascript:;" onclick="DJDFprioritySetting(' + "'" + data.businessCode + "'" + ',' + data.countryCode + ')"><i></i>优先级设置</a>';
                    return temp;
                }
            }
        ]],
    });
}


$(function(){
	loadDataGridDF();
	loadDataGridHCP();
	loadDataGridMPCZ();
	
    $("#search").on('click', function () {
        $("#dataGrid").datagrid('load', {
            company: $('#company').val()
        });
    });

    $("#clear").on('click', function () {

        $("#s_title").textbox('setValue', '')
        $("#s_companyName").textbox('setValue', '')

        $('#startDate').datebox('setValue', '');
        $("#endDate").datebox('setValue', '');

    });
    
    $('#search_DJDF').on('click', function () {
        $("#dataGridDF").datagrid('load', {
            province: $('#province_DJDF').combobox('getText'),
            city: $('#city_DJDF').combobox('getText'),
            countryName: $('#area_DJDF').combobox('getText'),
            businessCode: 'DJDF'
        });
    });

    $('#clear_DJDF').on('click', function () {
    	loadDataGridDF();
    	$('#province_DJDF').combobox('clear');
    	$('#city_DJDF').combobox('clear');
    	$('#area_DJDF').combobox('clear');
    });

    $('#search_HCP').on('click', function () {
        $("#dataGridHCP").datagrid('load', {
            province: $('#province_HCP').combobox('getText'),
            city: $('#city_HCP').combobox('getText'),
            countryName: $('#area_HCP').combobox('getText'),
            businessCode: 'HCP'
        });
    });

    $('#clear_HCP').on('click', function () {
    	loadDataGridHCP();
    	$('#province_HCP').combobox('clear');
    	$('#city_HCP').combobox('clear');
    	$('#area_HCP').combobox('clear');
    });
    //==话费充值
    $('#search_MPCZ').on('click', function () {
        $("#dataGridMPCZ").datagrid('load', {
            province: $('#province_MPCZ').combobox('getText'),
            city: $('#city_MPCZ').combobox('getText'),
            countryName: $('#area_MPCZ').combobox('getText'),
            businessCode: 'MPCZ'
        });
    });

    $('#clear_MPCZ').on('click', function () {
    	loadDataGridMPCZ();
    	$('#province_MPCZ').combobox('clear');
    	$('#city_MPCZ').combobox('clear');
    	$('#area_MPCZ').combobox('clear');
    });
     
	$('#tt').tabs({
	    border:false,
	    onSelect:function(title,index){
	       if('缴电费' == title){
	    	   loadDataGridDF();
	       }else if('火车票' == title){
	    	   loadDataGridHCP();
	       }else if('话费充值' == title){
	    	   loadDataGridMPCZ();
	       }
	    }
	});
});
