
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

function topYesGovernment(id) {
    $.messager.confirm({
        width: 400,
        title: '是否置顶',
        msg: '<div class="content">你确认要置顶这条记录吗?</div>',
        ok: '<i class="i-ok"></i> 确定',
        cancel: '<i class="i-close"></i> 取消',
        fn: function (r) {
            if (r) {
                $.ajax({
                    type: 'post',
                    url: '../../government/topYesGovernment.do',
                    async: false,
                    data: {
                        id: id
                    },
                    success: function (result) {
                        if (false == result.data) {
                            showByMsg('error', '最多置顶3个!');
                        } else {
                            showByMsg('info', '置顶成功!');
                        }
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

function reloadGrid() {
    $('#dataGrid').datagrid('reload');
}


function restoreTheDefault() {
    $.messager.confirm({
        width: 400,
        title: '系统提示',
        msg: '<div class="content">你确认要恢复默认吗?</div>',
        ok: '<i class="i-ok"></i> 确定',
        cancel: '<i class="i-close"></i> 取消',
        fn: function (r) {
            if (r) {
                $.ajax({
                    type: 'post',
                    url: '../../government/restoreTheDefault.do',
                    async: false,
                    success: function (result) {
                        showByMsg('info', '恢复成功!');
                        reloadGrid();
                    },
                    error: function (e) {
                        alert(e);
                    }
                });
            }
        }
    });
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

$(function () {
    //loadCategory();
    $("#search").on('click', function () {
        $("#dataGrid").datagrid('load', {
            company: $('#company').val()
        });
    });

    $("#reset").on('click', function () {
        restoreTheDefault();
    });

    $("#clear").on('click', function () {

        $("#s_title").textbox('setValue', '')
        $("#s_companyName").textbox('setValue', '')

        $('#startDate').datebox('setValue', '');
        $("#endDate").datebox('setValue', '');

    });

    
    $('#dataGridDF').datagrid({
        url: '../../vas/supplier/query/suppliers',
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
        columns: [[
            {field: 'supplierCode', title: '增值服务供应商', width: 50},
            {
                field: 'supplierName', title: '县域优先级批量设置', width: 60, formatter: function (value, data, index) {
                var str = "";
                str += '<select><option value="1">选择</option><option value="2">1级</option></select>'
                return str;
            }
            }
        ]],
    });

    
    $('#dataGridHFCZ').datagrid({
        url: '../../vas/supplier/query/suppliers',
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
        columns: [[
            {field: 'supplierCode', title: '增值服务供应商', width: 50},
            {
                field: 'supplierName', title: '县域优先级批量设置', width: 60, formatter: function (value, data, index) {
                var str = "";
                str += '<select><option value="1">选择</option><option value="2">1级</option></select>'
                return str;
            }
            }
        ]],
    });

   
    $('#dataGridSF').datagrid({
        url: '../../vas/supplier/query/suppliers',
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
        columns: [[
            {field: 'supplierCode', title: '增值服务供应商', width: 50},
            {
                field: 'supplierName',
                title: '县域优先级批量设置',
                width: 60,
                formatter: function (value, data, index) {
                    var str = "";
                    str += '<select><option value="1">选择</option><option value="2">1级</option></select>'
                    return str;
                }
            }
        ]],
    });

    
    $('#dataGridMQF').datagrid({
        url: '../../vas/supplier/query/suppliers',
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
        columns: [[
            {field: 'supplierCode', title: '增值服务供应商', width: 50},
            {
                field: 'supplierName',
                title: '县域优先级批量设置',
                width: 60,
                formatter: function (value, data, index) {
                    var str = "";
                    str += '<select><option value="1">选择</option><option value="2">1级</option></select>'
                    return str;
                }
            }
        ]],
    });
    
    $('#dataGridYXDSF').datagrid({
        url: '../../vas/supplier/query/suppliers',
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
        columns: [[
            {field: 'supplierCode', title: '增值服务供应商', width: 50},
            {
                field: 'supplierName',
                title: '县域优先级批量设置',
                width: 60,
                formatter: function (value, data, index) {
                    var str = "";
                    str += '<select><option value="1">选择</option><option value="2">1级</option></select>'
                    return str;
                }
            }
        ]],
    });
});