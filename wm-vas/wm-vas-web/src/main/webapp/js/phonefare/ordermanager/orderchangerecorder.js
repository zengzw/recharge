$(function(){
	var url = '/vas/order/queryChangeOrderPage';
	init(url,null);
	$("#search").on('click',function(){
		search();
	})
	$("#reset").on('click',function(){
		resetSearch();
	})
	$("#export").on('click',function(){
		exportExcel();
	})
});

function init(url,data) {
	$("#id_order_change_datagrid").datagrid({
		url : url,
		queryParams : data,
		fitColumns:true,
		pagination:true,
		method: "POST",
		nowrap:false,
		fitColumns:true,//true只适应列宽，防止出现水平滚动条；默认为false
		pageSize: 10,
//		method : "get", // 请求数据的方法
		loadMsg : '数据加载中,请稍候......',
		idField : 'id',
  		columns:[
        [
            // {field:'id', title:'选择',width:50, align:'center'},
		    {field:'orderType',title:'订单类型',width:150,align:'center',formatter:function(value,row, index) {
		    	
		    	if(value == "MPCZ"){
		    		return "话费充值";
		    	}else if(value == "DJDF"){
		    		return "缴电费";
		    	}else if(value == "HCP"){
		    		return "火车票";
		    	}
		    	return value;
	    	 }},
		    {field:'orderCode',title:'订单号',width:150,align:'center',formatter:function(value,row, index) {
		    	return value;
	    	 }},
		    {field:'payMoney',title:'支付金额(元)',width:200,align:'center',formatter:function(value,row, index) {
		    	return value;
		    }},
		    {field:'beforeStatus',title:'原订单状态',width:200,align:'center'},
		    {field:'afterStatus',title:'变更后订单状态',width:200,align:'center'},
		    {field:'remark',title:'变更原因',width:200,align:'center',
		        	  formatter: function(value, row, index) {
		        	  return value;
		        	}},
        	 {field:'creater',title:'操作人',width:300,align:'center',
	        	  formatter: function(value, row, index) {
	        	  return value;
	        	}},
	        	
        	 {field:'updateTime',title:'操作时间',width:300,align:'center',
	        	  formatter: function(value, row, index) {
	        	  return value;
	        	}},	
		   ]
  		],
  		onLoadError: function() {
  			$.messager.alert('操作提示', '获取信息失败...请联系管理员!', 'error');
  		},
  		onLoadSuccess:function(data){
  		},
      toolbar:'#tb'
  	});
	
    pageopt = $('#id_order_change_datagrid').datagrid('getPager').data("pagination").options;
}

//条件搜索
function search() {
    var searchFormData = JSONTools.arrayToJson($("#searchForm").serializeArray());
	$('#id_order_change_datagrid').datagrid("load",{"searchInfo" : JSON.stringify(searchFormData)});
}
//重置搜索
function resetSearch() {
	$("#searchForm").form('reset');
	$("#id_order_change_datagrid").datagrid('load',{});
}

//导出excel
function exportExcel() {
	var rows = $("#id_order_change_datagrid").datagrid("getRows");
	if (rows == null || rows.length == 0) {
		alert("未有任何记录");
		return;
	}
	
	var _orderType = $("#id_order_type").combobox("getValue");
	var _orderNo = $("#id_order_no").textbox("getValue");
	
	window.location.href="/vas/order/exportOrderChangeReports?orderType="+_orderType+"&orderNo="+_orderNo+"&page="+1+"&rows="+100000;
}