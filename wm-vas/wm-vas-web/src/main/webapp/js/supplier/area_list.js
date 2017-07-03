
var supplierCode = $.acceptParam("supplierCode");
var company = $.acceptParam("company");
company = decodeURI(company);


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
 
function areaView(supplierCode) {
    location.href = 'area_view.html?supplierCode=' + supplierCode;
}

function reloadGrid() {
    $('#dataGrid').datagrid('reload');
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
$(function () {
    //loadCategory();
    $("#search").on('click', function () {

        $("#dataGrid").datagrid('load', {
            businessCode: $('#businessCode').combobox('getValue'),
            province: $('#province').combobox('getText'),
            city: $('#city').combobox('getText'),
            countryCode: $('#area').combobox('getValue'),
            supplierCode: supplierCode,
            countryName: $('#countryName').textbox('getValue')
        });
    });


    $("#clear").on('click', function () {
        window.location.reload();
        $("#countryName").textbox('setValue', '');

    });
    $("#tt").datagrid({
        queryParams: {
            name: '小白',
            gender: true
        }
    });

    $('#dataGrid').datagrid({
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
            return data.data;
        },
        queryParams: {
            supplierCode: supplierCode
        },
        columns: [[
            {field: 'businessName', title: '业务类型', width: 50},
            {field: 'province', title: '省', width: 60},
            {field: 'city', title: '市', width: 80},
            {field: 'country', title: '县', width: 60},
            {field: 'countryName', title: '县域名称', width: 80}
        ]],
    });

});
