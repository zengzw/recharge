$(function(){

});

var SearchType  = {
		OrderStatus:1,
		OrderChangeStutus:2
};


/**
 * 搜索订单状态
 * @param orderType -- 订单类型
 * @param orderNo -- 订单号
 */
function serach(orderType, orderNo, searchType) {
	if (searchType == SearchType.OrderStatus) {
		$.post('../../../vas/order/getOrderStatus.do',{'orderType':orderType, 'orderNo':orderNo}, function(data){
			if (data.status == 200) {
				$("#id_order_status_table").children("tbody").empty();
				var content = "";
				if(null != data.data){
					content += "<tr>";
					content += "<td>"+data.data.orderTypeName+"</td>";
					content += "<td>"+data.data.orderNo+"</td>";
					content += "<td>"+data.data.payBalance+"</td>";
					content += "<td>"+data.data.createTimeStr+"</td>";
					content += "<td>"+data.data.orderStatusName+"</td>";
					content += "<td>"+data.data.refundStatusName+"</td>";

					// 判断订单状态
					if ((data.data.orderStatus == 'SUCCESS' || data.data.orderStatus == 'CHARGE_FAIL' || data.data.orderStatus == 'PHONE_CHARGING')
						&& data.data.refundExists == '0' && data.data.changeExists == '0') {

						//设置隐藏域
						$("#hidden_order_no").val(data.data.orderNo);
						$("#hidden_order_status").val(data.data.orderStatus);
						$("#hidden_order_status_name").val(data.data.orderStatusName);
						$("#hidden_order_refund_status").val(data.data.refundStatusName);
						$("#hidden_supplier_order_status").val(data.data.supplierOrderStatus);
						$("#hidden_supplier_order_status_name").val(data.data.supplierOrderStatusName);

						content += "<td><a href='javascript:void(0);' onclick='modifyStatus()'>状态变更</a></td>";

					} else {
						//不能修改
						content += "<td>----</td>";
					}
					content += "</tr>";
					$("#id_order_status_table").append(content);
				} else {
					info("查无此记录");
				}

			}
		});
	} else if (searchType == SearchType.OrderChangeStutus) {
		$.post('../../../vas/order/getOrderChangeStatus.do',{'orderType':orderType, 'orderNo':orderNo}, function(data){
			//清空所有元素
			$("#id_order_change_table").children("tbody").empty();
			var rows = data.data;
			var content = "";
			if(null != rows){
				for (var i = 0; i<rows.length;i++) {
					var json = rows[i];
					content += "<tr>";
					content += "<td>"+json.orderCode+"</td>";
					content += "<td>"+json.beforeStatus+"</td>";
					content += "<td>"+json.afterStatus+"</td>";
					content += "<td>"+json.remark+"</td>";
					content += "<td>"+json.creater+"</td>";
					content += "<td>"+json.updateTime+"</td>";
					content += "</tr>";
				}
				$("#id_order_change_table").append(content);
				content = "";
			}

		});
	}
}



/**
 * 搜索订单
 */
function search1() {
	//订单类型
	var orderType = $("#id_order_type").combobox("getValue");
	//订单号
	var orderNo = $("#id_order_no").textbox("getValue");

	if(null == orderType || orderType == ''){
		alert("订单类型不能为空");
		return ;
	}
	if(null == orderNo || orderNo == ''){
		alert("订单编号不能为空");
		return ;
	}
	serach(orderType, orderNo, SearchType.OrderStatus);
	serach(orderType, orderNo, SearchType.OrderChangeStutus);
}

/**
 * 订单状态修改
 */
function modifyStatus() {
	var content = "";
	//订单号
	var orderNo = $("#hidden_order_no").val();
	var orderStatus = $("#hidden_order_status").val();
	var orderStatusName = $("#hidden_order_status_name").val();
	var orderRefundStatus = $("#hidden_order_refund_status").val();
	var orderType = $("#id_order_type").combobox("getValue");
	var supplierOrderStatus = $("#hidden_supplier_order_status").val();
	var supplierOrderStatusName = $("#hidden_supplier_order_status_name").val();

	//组装信息
	content = "<label>&nbsp;&nbsp;订单号:&nbsp;&nbsp;</label><span style=';'>"+orderNo+"</span>";
	content += "<br>";
	content += "<label >&nbsp;&nbsp;当前订单状态:&nbsp;&nbsp;</label><span style=';'>"+orderStatusName+"</span>";
	content += "<br>";
	content += "<label >&nbsp;&nbsp;当前退款状态:&nbsp;&nbsp;</label><span style=';'>"+orderRefundStatus+"</span>";
	content += "<br>";


	// 充值中需要显示供应商订单状态
	if(supplierOrderStatus != '' && supplierOrderStatus != null){
		content += "<label >&nbsp;&nbsp;供应商当前订单状态:&nbsp;&nbsp;</label><span style=';'>"+supplierOrderStatusName+"</span>";
		content += "<br><br>";
	}


	content +="<label>&nbsp;&nbsp;可变更的订单状态:&nbsp;&nbsp;</label><select  id='modify_order_status' class='easyui-combobox' style='width:100px;height: 30px;' panelHeight='55'>";

	// （话费和缴电费）订单判断，复杂的
	if(orderStatus == 'SUCCESS'){
		if(orderType == 'DJDF'){
			content += "<option value='FAILURE'>交易失败</option>";//缴电费订单状态描述
		} else if(orderType == "MPCZ"){
			content += "<option value='FAILURE'>充值失败</option>";//话费订单状态描述
		}

	} else if(orderStatus == 'CHARGE_FAIL'){
		if(orderType == 'DJDF'){
			content += "<option value='SUCCESS'>交易成功</option>";
			content += "<option value='FAILURE'>交易失败</option>";
		} else if(orderType == "MPCZ"){
			content += "<option value='FAILURE'>充值失败</option>";//话费：扣款失败只能转充值失败
		}

	} else if(orderStatus == 'PHONE_CHARGING'){ //充值中
		if(supplierOrderStatus == 'SUCCESS'){
			content += "<option value='SUCCESS'>充值成功</option>";
		} else if (supplierOrderStatus == 'FAILURE'){
			content += "<option value='FAILURE'>充值失败</option>";
		} else {

		}
	}
	content += "</select>";
	content += "<br><br>";
	content += "<div style='width:350px;height:90px;'><div style='float:left;width:90px;height:auto;'><label style='margin-left:10px; ;margin-top:40px;'><font color='red'>*</font>变更原因:&nbsp;&nbsp;</label></div>" +
			"   <div style='float:left;width:260px;height:auto;'><textarea class='class_order_status_change_remarks'></textarea></div>";
	content += "<div id='div_btns' class='dialog-button'><a  class='btn'   onclick='closeDialog(\"#id_dialog_order_status_change\")'>" +
			   "<i class='i-close'></i>取消</a>&nbsp;&nbsp;";

	// “充值中”的话费订单，如果供应商返回的订单状态不确定，隐藏“确定”按钮
	if(orderType == "MPCZ" && supplierOrderStatus!=null && supplierOrderStatus!='' && supplierOrderStatus != 'SUCCESS' && supplierOrderStatus != 'FAILURE'){

	} else {
		content +=	"<a href='javascript:void(0);' onclick='setOrderStatus()' class='btn btn-1'><i class='i-ok'></i>确定</a></div>";
	}
	
	$("#id_dialog_order_status_change").dialog({
		title:'订单状态变更设置',
		content:content,
		width:355,
		height:320,
		closable: true,
		modal: true,
		buttons:'#div_btns',
		onClose:function(){
			$('#div_btns').remove();
			$('#id_dialog_order_status_change').empty();
		}
	});
}



//修改订单状态
function setOrderStatus() {
	$.messager.confirm({
		width: 400,
		title: '修改提示',
		msg: '<div class="content">你确认要修改这条记录吗?</div>',
		ok: '<i class="i-ok"></i> 确定',
		cancel: '<i class="i-close"></i> 取消',
		fn: function (r) {
			if (r) {
				var orderNo = $("#hidden_order_no").val();
				//获取修改说明
				var remarks = $(".class_order_status_change_remarks").val();
				if (remarks == '' || remarks.trim() == '') {
					alert("请填写变更原因");
					return;
				}

				if (remarks.length > 100) {
					alert("变更内容过长");
					return;
				}

				var modifyOrderStatus = $("#modify_order_status").combobox("getValue");


				$.post('../../../vas/order/updateOrderStatus.do', {'orderNo':orderNo, 'remarks':remarks, 'modifyOrderStatus':modifyOrderStatus}, function(data){
					if (data.status == 200) {
						info("变更成功");
						search1();
						closeDialog("#id_dialog_order_status_change");

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

//消息框关闭
function closeDialog(o) {
	$(o).dialog('close');
}