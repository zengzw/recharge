var dataGridManager = {
    init : function(){
        $('#dataGrid').datagrid({
            method: "POST",
            loadFilter:function(data){
                //console.log(data);
                return data;
            },
            columns:[[{title:'订单信息',colspan:2},
                      {title:'佣金分成信息',colspan:7}],
                [{field:'salePrice',title:'销售价',width:210,align:'center'},
                {field:'costingAmount',title:'成本价',width:210,align:'center'},
                {field:'platformRatio',title:'平台分成比',width:200,align:'center'},
                {field:'platformCommission',title:'平台佣金(元)',width:200,align:'center'},
                {field:'countyRatio',title:'县域分成比',width:200,align:'center'},
                {field:'areaDividedRatio',title:'县域佣金比',width:200,align:'center'},
                {field:'countyCommission',title:'县域佣金(元)',width:200,align:'center'},
                {field:'shopRatio',title:'网点比例',width:200,align:'center'},
                {field:'shopCommission',title:'网点佣金(元)',width:200,align:'center'}
            ]]
        });
    }
};

$(function(){
    $.ajax({
        type: "get",
        async: false,
        url: '/vas/train/order/billsDetails?orderCode=' + URI.getQueryString("orderCode"),

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
