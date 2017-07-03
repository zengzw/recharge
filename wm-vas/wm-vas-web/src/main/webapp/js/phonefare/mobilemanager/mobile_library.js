$(function(){

    initProvince();

    var url = '../../../../vas/phone/mobile/manager/pageList.do';
    init(url);
});

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
                    editable: false
                });
            }
        });
    }
}

function init(url) {
    $("#id_mobile_manager_datagrid").datagrid({
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
        queryParams:{'mobileNum':'','mobileShort':''},
        columns:[
            [
                {field:'id', title:'选择', align:'center', checkbox:true},
                {field:'mobileSupplier',title:'运营商',width:150,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'mobileProvince',title:'省份',width:150,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'mobileCity',title:'城市',width:150,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'mobileShort',title:'号段前缀',width:150,align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'mobileNum',title:'号段',width:150,align:'center',formatter:function(value,row, index) {
                    return value;
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

    pageopt = $('#id_mobile_manager_datagrid').datagrid('getPager').data("pagination").options;
}

//条件搜索
function mobilesearch() {
    var mobileSupplier = $("#id_mobile_supplier").textbox("getValue");
    var province = $("#province").textbox("getText");
    var city = $("#city").textbox("getText");

    var mobileShort = $("#id_mobile_short").val();
    var mobileNum = $("#id_mobile_num").val();

    $('#id_mobile_manager_datagrid').datagrid("load",
        {
            mobileSupplier:mobileSupplier,
            mobileProvince:province,
            mobileCity:city,
            mobileShort:mobileShort,
            mobileNum:mobileNum
        });
}

//重置搜索
function mobilesearchreset() {

    $("#id_mobile_supplier").combobox("setValue", '');
    $("#province").combobox("setValue", '');
    // $("#city").combobox("setValue", '-1');

    $("#id_mobile_short").textbox("setValue", '');
    $("#id_mobile_num").textbox("setValue", '');

    $('#id_mobile_manager_datagrid').datagrid("load", {orderCode:''});
    initCity(-1);
}

function addNewMobileManager() {
    window.location.href = "mobile_library_add.html";
}

function batchDeleteMobileManager(){
    var selectRow = $("#id_mobile_manager_datagrid").datagrid("getChecked");
    if(null == selectRow || selectRow.length == 0){
        alert("请选择要删除的号段");
        return ;
    }

    $.messager.confirm({
        width: 400,
        title: '修改提示',
        msg: '<div class="content">你确认要删除号段吗?</div>',
        ok: '<i class="i-ok"></i> 确定',
        cancel: '<i class="i-close"></i> 取消',
        fn: function (r) {
            if (r) {
                var rows = $("#id_mobile_manager_datagrid").datagrid("getChecked");
                var ids = '';
                for(var i=0;i<rows.length;i++){
                    ids += rows[i].id + ",";
                }

                $.post('../../../../vas/phone/mobile/manager/delete.do',
                    {
                        'ids':ids
                    },

                    function(data){
                        if (data.status == 200) {
                            info("删除成功");
                            mobilesearch();

                        } else {
                            alert(data.msg);
                        }
                    });
            } else {
                return ;
            }
        }
    });

}
