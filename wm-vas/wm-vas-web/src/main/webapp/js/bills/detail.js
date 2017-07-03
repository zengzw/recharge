var dataGridManager = {
    init : function(){
        $('#dataGrid').datagrid({
            striped:true,
            fitColumns:true,
            fixed:true,
            singleSelect:false,
            rownumbers:false,
            method: "POST",
            nowrap:true,
            fitColumns:true,//true只适应列宽，防止出现水平滚动条；默认为false
            //rownumbers:true,
            loadFilter:function(data){
                //console.log(data);
                return data;
            },
            columns:[[
                //提点分成信息
                {field:'salePrice',title:'销售价',width:80,align:'center'},
                {field:'commissionRatio',title:'提点比例',width:80,align:'center'},
                {field:'countyRatio',title:'县域比例',width:80,align:'center'},
                {field:'countyCommission',title:'县域佣金',width:80,align:'center'},
                {field:'shopRatio',title:'网点比例',width:80,align:'center'},
                {field:'shopCommission',title:'网点佣金',width:80,align:'center'}
            ]]
        });
    }
};

$(function(){
    $.ajax({
        type: "get",
        async: false,
        url: '../../vas/charge/billsDetails?chargeCode=' + URI.getQueryString("chargeCode"),

        success: function (data) {
            var  order = data.data;
            var  commissionInfoList = order.commissionInfoList;

            $("#chargeCode").text(order.chargeCode);
            $("#businessCodeName").text(order.businessCodeName);
            $("#paymentAmount").text(order.paymentAmount);
            $("#supplierName").text(order.supplierName);
            $("#saleCountyName").text(order.saleCountyName);
            $("#shopName").text(order.shopName);

            dataGridManager.init();
            $("#dataGrid").datagrid("loadData",commissionInfoList);
        }
    });


});

var  URI = {
    getQueryString : function(name){
        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if(r!=null) return  unescape(r[2]); return null;
    },goto : function(href){
        href = href || window.location.href;
        window.location.href = href;
    },refresh : function(){
        window.location.reload();
    }
}
