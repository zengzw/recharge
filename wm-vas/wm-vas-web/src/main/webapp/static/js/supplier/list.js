
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
 
function areaSet(supplierCode, company) {
    location.href = 'area_set.html?supplierCode=' + supplierCode + "&company=" + company;
}
 
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

$(function () {
    //loadCategory();
    $("#search").on('click', function () {
         
        $("#dataGrid").datagrid('load', {
            company: $('#company').val(),
            supplierCode: $('#supplierCode').val(),
            businessName: $('#businessName').val()
        });
    });

    $("#reset").on('click', function () {
        restoreTheDefault();
    });

    $("#clear").on('click', function () {

        $("#supplierCode").textbox('setValue', '');
        $("#company").textbox('setValue', '');

        $('#dataGrid').datagrid("load", {});
    });


    $('#dataGrid').datagrid({
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
            {field: 'supplierCode', title: '服务商编号', width: 50},
            {field: 'supplierName', title: '联系人', width: 60},
            {field: 'company', title: '供应商名称', width: 100},
            {field: 'email', title: '联系Email', width: 100},
            {field: 'mobile', title: '联系手机', width: 80},
            {field: 'telphone', title: '联系座机', width: 80},
            {field: 'checkTimeStr', title: '创建时间', width: 80},
            {field: 'businessNames', title: '对接的业务', width: 80},
            {
                field: 'cz',
                title: '操作',
                align: 'center',
                width: 200,
                fitColumns: true,
                formatter: function (value, data, index) {

                    var temp = "";

                    temp += '<a class="btn btn-l" style="color:#7b9be6" href="javascript:;" onclick="view(' + "'"+data.supplierCode+"'" + ')"><i></i>查看</a>';

                    temp += '<a class="btn btn-l" style="color:#7b9be6" href="javascript:;" onclick="edit(' + "'"+data.supplierCode+"'" + ')"><i></i>编辑</a>';

                    temp += '<a class="btn btn-l" style="color:#7b9be6" href="javascript:;" onclick="areaView(' + "'"+data.supplierCode+"'" + ',' + "'" + encodeURIComponent(data.company) + "'" + ')"><i></i>业务地区查看</a>';

                    temp += '<a class="btn btn-l" style="color:#7b9be6" href="javascript:;" onclick="areaSet(' + "'"+data.supplierCode+"'" + ',' + "'" + encodeURIComponent(data.company) + "'" + ')"><i></i>业务地区设置</a>';

                    return temp;

                }
            }
        ]],
    });

});
