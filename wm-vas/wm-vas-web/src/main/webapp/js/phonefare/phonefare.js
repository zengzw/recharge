$(function(){
   //加载充值面值方案
   loadFacevalueCombobox("#face_value", '../../vas/phonefare/test/getPhoneFareChargeWays.do', '元');
   //加载充值运营商方案
  // loadCombobox("#phone_operation", '../../vas/phonefare/test/getPhoneFareCompanies.do', '');
   //加载充值使用地区方案
   loadSaleRegionCombobox2("#sale_region");
   //初始化加载
   var url = '../../vas/phonefare/test/findAllPhoneFareWaysList.do';
   init(url);
   resetSearch();
   exportExcel();
   loadCompanyList();
});

//加载combobox的动态加载值  要求后台返回json字符串
function loadFacevalueCombobox(id ,url, option) {
	   $.ajax({  
           url:url,   
           type:'post',  
           success:function(data){  
        	   var defaultOption = [];
        	   defaultOption.push({'text':'全部','value':'0'});
        	   for (var i=0;i<data.data.rows.length;i++) {
        		   var obj = eval(data.data.rows[i]);
        		   defaultOption.push({'text':obj['value'] + option,'value':obj['value']});
        	   }

			   defaultOption = defaultOption.sort(getSortFun('asc', 'value'));
               $(id).combobox("loadData", defaultOption);
               //默认设置第一项
               $(id).combobox("select", defaultOption[0]['value']);
               //$(id).combobox({editable:false});
           }  
          });  
}

//加载combobox的动态加载值  要求后台返回json字符串
//function loadSaleRegionCombobox(id ,url, option) {
//	   $.ajax({  
//           url:url,   
//           type:'post',  
//           success:function(data){  
//        	  var defaultOption = [];
//        	   defaultOption.push({'text':'全部','value':''});
//        	   for (var i=0;i<data.data.rows.length;i++) {
//        		   var obj = eval(data.data.rows[i]);
//        		   defaultOption.push({'text':obj['areaName'] + option,'value':obj['areaId']});
//        	   }
//
//              $(id).combobox("loadData", defaultOption);
//              //默认设置第一项
//              $(id).combobox("select", defaultOption[0]['value']);
//              //$(id).combobox({editable:false});
//           }  
//          });  
//}


function loadSaleRegionCombobox2(id) {
 	  var defaultOption = [];
 	   defaultOption.push({'text':'全部','value':''});
 	   for (var index in provinceCode) {
 		   defaultOption.push({'text':index,'value':provinceCode[index]});
 	   }

       $(id).combobox("loadData", defaultOption);
       //默认设置第一项
       $(id).combobox("select", defaultOption[0]['value']);
}

var pageopt;
function init(url) {
	$("#dataGrid").datagrid({
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
	    queryParams:{'publicStatus':-1},
  		columns:[
        [
            {field:'id', title:'选择',width:20, align:'center', checkbox:true},
		    {field:'company',title:'运营商',width:150,align:'center'},
		    {field:'faceValue',title:'面值(元)',width:150,align:'center',formatter:function(value,row, index) {
	    	    	//return ((value) / 100).toFixed(2);
		    	   return value;
	    	 }},
		    {field:'salePrice',title:'销售价(元)',width:200,align:'center',formatter:function(value,row, index) {
		    	    //return ((value) / 100).toFixed(2);
		    	   return value;
		    }},
		    {field:'saleRegion',title:'销售区域',width:350,align:'center',formatter:function(value,row,index){
		        	return value;
		          }},
		    {field:'publicStatus',title:'发布状态',width:200,align:'center',formatter:function(value, row, index){
		    		return paublicStatus[value];
		    }},
		    {field:'operator',title:'操作',width:150,align:'center',
		        	  formatter: function(value, row, index) {
		        		var operation = '操作未定义';
		        		if (row['publicStatus'] == 1)
		        			operation = '取消发布';
		        		else if (row['publicStatus'] == 0) 
		        			operation = '发布';
		        		return '<a class="btn btn-l" href="javascript:;" onclick="view('+index+')"><i class="i-op i-op-set"></i>'+operation+'</a>';
		        	}},
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
	
    pageopt = $('#dataGrid').datagrid('getPager').data("pagination").options;
}


//操作方法定义
function view(id) {
	  var row = $('#dataGrid').datagrid('getData').rows[id];
	  if (row == '') {
		  alert("数据行不存在");
		  return;
	  }
	  
	  var publicStatus = row['publicStatus'];
	  var updateStatus = 0;
	  if (publicStatus == 1)
	  	updateStatus = 0;
	  else if (publicStatus == 0) 
	  	updateStatus = 1;
	  
	  //修改状态
	  $.ajax({  
          url:'../../vas/phonefare/test/modifyPublicStatus.do', 
          data:{"phoneFareWayId":row['id'], "publicStatus":updateStatus},
          dataType:"json",
          type:'post',  
          success:function(data){
        	  if (data.status == 200) {
        		  $('#dataGrid').datagrid('reload');
        		  $.messager.alert('操作提示', data.msg, 'info');
        	  }
        	  
          }
	  });
	  
}

//加载运营商列表
function loadCompanyList() {
	$.get('../../vas/phonefare/test/findCompanys.do', function(data){
		var content = [{'text':'全部', 'value':''}];
		if (data.status == 200) {
			for (var i = 0; i < data.data.rows.length;i++) {
				var obj = eval(data.data.rows[i]);
				content.push({'text':obj['providerName'],'value':obj['id']});
			}
		$("#phone_operation").combobox('loadData', content);
		$("#phone_operation").combobox("select", content[0]['value']);
		}
	});
}

//搜索方法
function search1() {
    var _company = $('#phone_operation').combobox("getValue");
	var _faceValue =$("#face_value").combobox("getValue");
	var _saleRegion = $("#sale_region").combobox("getValue");
	var _publicStatus = $("#public_status").combobox("getValue");
	
	$('#dataGrid').datagrid("load",{companyId:_company,faceValue:_faceValue,saleRegionId:_saleRegion, publicStatus:_publicStatus});
}

//重置搜索条件差查询
function resetSearch() {
	 $('#search-phone-resetting').click(function(){ 
		    //重置 
		    var _company  = $('#phone_operation').combobox("getData");
			$('#phone_operation').combobox('select',  _company[0].value);
			var _faceValue =$("#face_value").combobox("getData");
			$("#face_value").combobox('select',  _faceValue[0].value);
			var _saleRegion = $("#sale_region").combobox("getData");
			$("#sale_region").combobox('select',  _saleRegion[0].value);
			var _publicStatus = $("#public_status").combobox("getData");
			$("#public_status").combobox('select',  _publicStatus[0].value);
			
			$('#dataGrid').datagrid("load",{companyId:_company[0].value,faceValue: _faceValue[0].value,saleRegionId:_saleRegion[0].value, publicStatus:_publicStatus[0].value});
	 });	
}

//导出Excel
function exportExcel() {
	 $("#export-excel").click(function(){
		 var allrows = $("#dataGrid").datagrid("getRows");
		 if (allrows == null || allrows.length == 0) {
		        $.messager.alert('操作提示', '没有数据', 'error');
		        return;
		    } else {
		    	var _company = $('#phone_operation').combobox("getValue");
				var _faceValue =$("#face_value").combobox("getValue");
				var _saleRegion = $("#sale_region").combobox("getValue");
				var _publicStatus = $("#public_status").combobox("getValue");
		        window.location.href = "../../vas/phonefare/exportPhoneFareWaysReports.do?companyId="+_company+"&faceValue="+_faceValue+"&saleRegionId="+_saleRegion+"&publicStatus="
		        	                         +_publicStatus+"&page="+1+"&rows="+1000000;
		    }
	 });
}
//批量发布
function bacthPublic(status) {
	var rows = $("#dataGrid").datagrid("getSelections");
	if (rows == null || rows.length == 0 ){
		alert("未选中任何数据");
		return;
	}
	
	var goodIds = "";
	for (var i = 0; i < rows.length;i++) {
		goodIds += rows[i].id;
		goodIds += ",";
	}
	
	if (goodIds.length > 0) 
		goodIds = goodIds.substr(0, goodIds.length - 1);
	
	
	$.get("../../vas/phonefare/test/batchUpdatePublicStatus.do", {'status':status, 'goodIds':goodIds}, function(data){
		if (data.status == 200) {
			 $("#dataGrid").datagrid("reload");
			alert("修改成功");
		} else {
			alert("修改失败");
		}
		
	});
}