//订单类型常量数组
var orderTypeArr = {
		1:'话费充值',
		2:'交电费',
		3:'火车票'
};

//订单状态数组
var orderStatus = {
		1:'待确认',
		2:'已支付',
		3:'未提交'
};

//退款状态

var refundStatus = {
		4:'-'
};



function alert(msg) {
	$.messager.alert({
		title:'警告提示',
		msg:"<div class='content'>"+msg+"</div>",
		ok:'<i class="i-ok"></i> 确定',
		icon:'warning',
	});
}


function info(msg) {
	$.messager.alert({
		title:'成功消息',
		msg:"<div class='content'>"+msg+"</div>",
		icon:'info',
	});
}

function propmt(msg) {
	$.messager.confirm({
		title:'提示',
		style:{
			    left:300, // 与左边界的距离
			    top:300 // 与顶部的距离
			    },
	    msg:"<div class='content'>"+msg+"</div>",
	    ok:'确认',
	    cancel:'取消',
	    fn:function(data) {
	    	
	    }
	});
}