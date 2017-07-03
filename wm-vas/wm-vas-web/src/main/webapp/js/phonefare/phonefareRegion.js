;;
$(function(){
	loadValueFaceOptions();
	initTabsEvent();
	loadCompanyCheckBox();
	//初始化控件
	init();
});

function init() {
	$("#add_sale_region").textbox('textbox').attr('readonly',true); 
	$("#add_face_value").numberbox({
		onChange:function(newValue,oldValue){
			console.log(newValue);
		}
	});
}

//记录前一个tab的索引
var preIndex = 0;

//加载运营商列表
function loadCompanyCheckBox() {
	$.get('../../vas/phonefare/test/findCompanys.do', function(data){
		var content = ""; 
		if (data.status == 200) {
			for (var i = 0; i < data.data.rows.length;i++) {
				var obj = eval(data.data.rows[i]);
				content += "<input type='checkbox' name='companyList' class='ck_china_mobile' name='flag' value= '"+obj['id']+"'><span  style='margin-right: 10px'>"+obj['providerName']+"</span>&nbsp;&nbsp;&nbsp;&nbsp;";
			}
		$("#ck_company_arr").append(content);
            $('input[name=companyList]').click(function(){
                $(this).attr('checked','checked').siblings().removeAttr('checked');
            });

		}
	});
}

//动态加载面值选项,假设后台传参是以,逗号分隔的字符串,如30,50,100
function loadValueFaceOptions() {
	var $base = $("#phonefare_region_facevalue").children('span');
	var selectedTab = '';
	$.post("../../vas/phonefare/test/findPhoneFaceValues.do", function(data){
		var jsonArr = data.data.rows;
		//如果面值没有设置,则提示
		if (jsonArr.length == 0) {
			$('#tt').tabs('add',{
			    title:'当前还未设置任何区域的话费销售规格',
			 });
		}
		//对方案排序
		jsonArr.sort(getSortFun('asc', 'value'));
		var isSelected = false;
		var checkOptions= '';
		for (var json in jsonArr) {
			if(json==0) {
				selectedTab = jsonArr[json]['value'];
			}
		    checkOptions  += "<input name='phone_charge_way'  type='checkbox' value='"+jsonArr[json]['id']+"'><span>" + jsonArr[json]['value']+"</span>元"+"&nbsp;&nbsp;&nbsp;&nbsp;";
		    if (json == 0)
		    	isSelected = true;
		    else
		    	isSelected = false;
		    addTab('#tt', jsonArr[json]['value']+'元', isSelected);
		}
		$base.after(checkOptions);
		//设置默认选中tab颜色
		setTabsOptionsColor(0,  "background:#FF4500;background-size:20px 20px;");
		//查询默认tab对应的方案列表
		regionSalePriceList('../../vas/phonefare/test/findAllWaysByfaceValue.do', selectedTab);
	});
}



//增加tab
function addTab(id ,title0, isSelected) {
	$(id).tabs("add",{
		title:title0,
		width:20,
		selected:isSelected
	});
}

//各种tabs处理时间
function initTabsEvent() {
	//选中事件
	$("#tt").tabs({
		onSelect:function(title, index) {
			var  selectedTab = title.substr(0, title.length - 1);
			regionSalePriceList('../../vas/phonefare/test/findAllWaysByfaceValue.do', selectedTab);
            if (index != preIndex) {
            	setTabsOptionsColor(preIndex,"");
            	setTabsOptionsColor(index,"background:#FF4500;background-size:20px 20px;");
            	preIndex = index;
            }
		}
	});
}

//设置tab的标签页颜色
function setTabsOptionsColor(index, style) {
	var base = $("#tt").tabs('getTab', index);
	if (base == 'undifined' || base == null)
		return;
	var option = $(base).panel('options').tab;
	$(option).prop("style",style);
}



//面值设置
function faceValueSetting() {
	var content =  getAddContent() + findAllWays();
	$('#face_value_setting').dialog({
		closable:true,
		width:500,
		height:300,
		border:true,
		title:"面值设置",
		content:content,
		onClose:function(){
			location.reload();
		}
	});
}

function getAddContent() {
	var content = "<br><label  style='margin-left:10px;'><span>添加面值:&nbsp;</span>" +
		"<input class='easyui-numberbox' name='addFaceValue'  style='width:250px;height:35px;margin-top:30px;' id='add_face_value' data-options='required:true,min:1' >元" +
		"&nbsp;&nbsp;<a href='javascript:void(0);' class='btn btn-1' onclick='addFaceValue()'>添加</a></label>";
	return content;
}

//生成方案
function findAllWays() {
	var ways =  $("input[name='phone_charge_way']");
	var content = "<br><br><label  style='margin-left:10px;float:left'><span>已有面值:&nbsp;</span>";
	content += "<div style='float:right' id='phone_fare_way_used'>";
	for (var i = 0; i < ways.length; i++) {
		//console.log(ways[i].value);
		if (i!=0 && i % 4 == 0)
			content += "<br><br>";
		var faceValue = $(ways[i]).next('span').text();
	    content += "<label id='delway_"+ways[i].value+"' style='margin-left:15px;border:1px solid grey;background:#d0d0d0'><span>"+faceValue+"</span>元"
	                +"<a href='javascript:void(0);' onclick='delWay("+ways[i].value+","+faceValue+")'><i class='i-close'/></a></label>";
	}
	content += "</div>";
	return content;
}
//根据面值删除特定的方案
function delWay(id, faceValue) {
	var index = id;
	$.get('../../vas/phonefare/test/delWayById.do', {'id':id, 'faceValue':faceValue},function(data) {
		if (data.status == 200) {
			var id = "#delway_"+index;
			var br = $(id).prevAll('br');
			var nextNode =  $(id).next("label");
			if (br.length > 0 && nextNode.length == 0) {
				for (var i=0;i<br.length;i++)
					$(br[i]).remove();
			}
			$(id).remove();
			
		}else {
			alert(data.msg);
		}
	});
}
//添加方案
function addFaceValue() {
	//添加的面值
	var addFaceValue = $("#add_face_value").numberbox('getValue');
	if (addFaceValue <= 0 || addFaceValue > 1000){
		alert("请输入1-1000范围内的面值");
		return ;
	}

	//在div内部添加元素
	var labelNum =  $("#phone_fare_way_used").children("label");	
	
	$.post('../../vas/phonefare/test/addFaceValueWays.do',{'faceValue':addFaceValue}, function(data) {
		if (data.status == 200) {
			if (labelNum.length > 0 && labelNum.length % 4 == 0) {
					$("#phone_fare_way_used").append("<br><br>");
			}
			var po = data.data;
			$("#phone_fare_way_used").append("<label id='delway_"+po.id+"' style='margin-left:15px;border:1px solid grey;background:#d0d0d0'><span>"+po.value+"</span>元"
		            						 +"<a href='javascript:void(0);' onclick='delWay("+po.id+","+po.value+")'><i class='i-close'/></a></label>");
		} else {
			alert(data.msg);
		}
	});
	
	
}

//销售区域编辑
function editSaleRegion() {
	//$.get('../../vas/phonefare/test/findAddedProvince.do', function(data){
	//	if (data.status == 200) {
			var content = getAllSaleRegion(0 , true);
			//添加按钮
			content += "<div id='div_btns' class='dialog-button'><a  class='btn'   onclick='javascript:$(\"#add_sale_region_div\").dialog(\"close\")'>" +
					"<i class='i-close'></i>取消</a>&nbsp;&nbsp;<a  class='btn btn-1' onclick='addedProvince2()'><i class='i-ok'></i>确定</a></div>";
			
			$('#add_sale_region_div').dialog({
				closable:true,
				left:100,
				top:100,
				width:500,
				height:300,
				title:"销售地区",
				buttons:'#div_btns',
				content:content,
				onClose:function(){
					$("#div_btns").remove();
					$('#add_sale_region_div').empty();
				}
			});
	//	}
	//});
}

//添加省份
function addedProvince2() {
	var provinces = $("#add_sale_region_div").find("input:checked[type='checkbox']");
	var content = "";
	for (var i=0;i<provinces.length;i++) {
		content += $(provinces[i]).val()+'、';
	}
	
	if (content.length > 0)
		content = content.substring(0, content.length-1);
	
	//清除原有内容
	$("#add_sale_region").textbox('setValue', '');
	$("#add_sale_region").textbox('setValue', content);
	//关闭dialog
	$("#add_sale_region_div").dialog("close");
	
}


//添加省份
function addedProvince(index) {
	var provinces = $("#add_sale_region_div").find("input:checked[type='checkbox']");
	var content = "";
	for (var i=0;i<provinces.length;i++) {
		content += $(provinces[i]).val()+'、';
	}
	
	if (content.length > 0)
		content = content.substring(0, content.length-1);
	var pArr = content.trim().split("、");
	var provinces = '';
	var delProvinecs  = '';
	var saleRegion = $('#dataGrid2').datagrid('getRows')[index].saleRegion.split("、");
	for (var i = 0; i < pArr.length;i++) {
		if(inArray(saleRegion, pArr[i])) {
			continue;
		}
		//省名-省代码
		provinces += pArr[i]+'-'+provinceCode[pArr[i].trim()] + ',';
	}
	
	//本次取消选择的省份
	for (var i=0;i<saleRegion.length;i++) {
		if (!inArray(pArr, saleRegion[i])) {
			delProvinecs += saleRegion[i]+'-'+provinceCode[saleRegion[i].trim()]+',';
		}
	}
	
	if (delProvinecs.length > 0)
		delProvinecs = delProvinecs.substr(0, delProvinecs.length-1);
	
	if (provinces.length > 0)
		provinces = provinces.substr(0, provinces.length-1);
	
	if (delProvinecs.length == 0 && provinces.length == 0) {
		alert("区域未做修改");
		return;
	}

	var selectProvinces = $("#add_sale_region_div").find("input:checked[type='checkbox']");
	if(selectProvinces.length ==0){
		alert("区域不能为空");
		return;
	}

	var goodsId = $('#dataGrid2').datagrid('getRows')[index].id;
	$.post('../../vas/phonefare/test/addProvinces.do', {'goodsId':goodsId, 'provinces':provinces, 'delProvinces':delProvinecs}, function(data){
		if (data.status == 200) {
			$('#dataGrid2').datagrid("reload");
		} else {
			alert("已存在同运营商、同面值的地区，不可重复添加");
		}
	});
	//关闭dialog
	$("#add_sale_region_div").dialog("close");
	
}
//添加销售区域
function addSaleRegion() {
	//判断是否运营商有选择
	var ss = $("#ck_company_arr").children("input");
	var selectedCompanyId = "";
	for (var i=0;i<ss.length;i++)
		if ($(ss[i]).prop('checked')) {
			selectedCompanyId += $(ss[i]).val()+',';
	    }
	if (selectedCompanyId.length == 0) {
		alert("请选择运营商!");
		return;
	}
	selectedCompanyId = selectedCompanyId.substr(0, selectedCompanyId.length-1);
	
		
	
	//判断是否选择面值
	ss = $("#phonefare_region_facevalue").children("input[type='checkbox']");
	
	var selectedFaceValue = '';
	for (var i=0;i<ss.length;i++)
		if ($(ss[i]).is(":checked")) {
			var faceValue = $(ss[i]).next('span').text().trim();
			selectedFaceValue += faceValue+',';
	    }
	if (selectedFaceValue.length == 0) {
		alert("请选择面值!");
		return;
	}
	selectedFaceValue = selectedFaceValue.substr(0, selectedFaceValue.length-1);
	
	//判断销售区域
	var text = $("#add_sale_region").textbox('getValue');
	if (text == '') {
		alert("请添加销售区域");
		return;
	}
	
	var provinceNames = text.split('、');
	var provinceId = "";
	var province = "";
	var relationShip = "";
	for (var i=0;i<provinceNames.length;i++) {
		province += provinceNames[i].trim() + ',';
		provinceId += provinceCode[provinceNames[i].trim()] + ',';
		relationShip += provinceNames[i].trim()+'-'+provinceCode[provinceNames[i].trim()]+',';
	}
	
	provinceId = provinceId.substr(0, provinceId.length-1);
	province = province.substr(0, province.length-1);
	relationShip = relationShip.substr(0, relationShip.length-1);
	
	$.post('../../vas/phonefare/test/addSaleWays.do',{'companyIds':selectedCompanyId,'faceValues':selectedFaceValue,
		'saleRegionNames':province,'saleRegionIds':provinceId, 'relationShip':relationShip}, function(data){
		if (data.status == 200) {
			alert('添加成功');
			$('#dataGrid2').datagrid("reload");
            $("#add_sale_region").textbox('setValue', '');
		}else {
			alert(data.msg);
		}
	});
}

//得到销售区域
function getAllSaleRegion(index, isMain) {
	
	var content = "";
	//var addedProvinces = data.split(",");
	var selectedProvince = '';
	if (isMain) {
		//在销售区域添加文本框获取内容
		selectedProvince = $("#add_sale_region").textbox('getValue');
	} else {
		//从数据列表的中获取数据
		selectedProvince = $('#dataGrid2').datagrid("getRows")[index].saleRegion.trim();
	}
	var selectedProvinceArr = [];
	if (selectedProvince.length > 0) {
		selectedProvinceArr = selectedProvince.split('、');
	}
	
	content = "<div style='margin-left:10px; margin-top:20px;'><input type='checkbox' class='sale_region_china' onclick='selectedChina()' value='全国'/><label>&nbsp;全国<lable></div>";
    content += "<div id='tsh-title'><h3></h3><div class='title-border'></div></div>"; 
    var temp = "";
    for (var index in provinceTable) {
    	temp += "<label style='margin-left:10px;'>"+index+"<label>";
    	temp += "<br>" ;
    	var arr = provinceTable[index].split(",");
    	temp += "<div style='margin-left:30px;'>";
    	
    	for (var i = 0; i < arr.length; i++) {
    	//	if (matchAddedProvince(addedProvinces, arr[i])) {
    	//		continue;
    	//	}
    		content += temp;
    		temp = "";
    		if (i != 0 && i % 5 == 0)
    			content += "<br>";
    		if (inArray(selectedProvinceArr, arr[i].trim())) {
    			content += "<input type='checkbox' class='sale_region_china_"+i+"' name='sale_region_china_province' value='"+arr[i]+"' checked='checked'/><label>&nbsp;"+arr[i]+"<lable>";
    		} else {
    			content += "<input type='checkbox' class='sale_region_china_"+i+"' name='sale_region_china_province' value='"+arr[i]+"'/><label>&nbsp;"+arr[i]+"<lable>";
    		}
    		
    	}
    	if (temp != "") {
    		temp = "";
    	}else {
    		content += "</div>";
    	    content += "<br>";
    	}
    }
	
	return content;
}

//选中销售区域的全国
function selectedChina() {
	var isSeleted = $(".sale_region_china").is(":checked");
	if (isSeleted) {
		//全部选中
		$("[name='sale_region_china_province']").prop('checked', 'checked');
	} else {
		//取消全选
		$("input:checkbox[name='sale_region_china_province']:checked").removeAttr("checked");;
	}
}

//区域销售价方案列表搜索
function regionSalePriceList(url, faceValue) {
	$("#dataGrid2").datagrid({
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
  		checkOnSelect: false,
  		selectOnCheck:true,
  		method: "POST", //请求数据的方法
  		loadMsg: '数据加载中,请稍候......',
  		idField:'',
	    queryParams:{'faceValue':faceValue},
  		columns:[
        [

            {field:'id', title:'选择',width:20, align:'center', checkbox:true},
		    {field:'company',title:'运营商',width:150,align:'center'},
		    {field:'faceValue',title:'面值(元)',width:150,align:'center',formatter:function(value,row, index) {
	    	    	//return ((value) / 100).toFixed(2);
		    	return value;
	    	 }},
		    {field:'salePrice',title:'销售价(元)',width:200,align:'center',formatter:function(value,row, index) {
		    	   // return ((value) / 100).toFixed(2);
		    	return value;
		    }},
		    {field:'saleRegion',title:'销售区域',width:200,align:'center',formatter:function(value,row,index){
		        	return value;
		          }},
		    {field:'publicStatus',title:'发布状态',width:100,align:'center',formatter:function(value, row, index){
		    		return paublicStatus[value];
		    }},
		    {field:'operator',title:'操作',width:300,align:'center',
		        	  formatter: function(value, row, index) {
		        	   var content = "";
		        	   if (row.publicStatus == 0) {
		        		   //未发布
		        		   content = '<a class="btn btn-l" href="javascript:;" onclick="regionSaleSet('+index+')"><i class="i-op i-op-set"></i>'+"销售价设置"+'</a>&nbsp;'+
	        		                 '<a class="btn btn-l" href="javascript:;" onclick="regionSet('+index+')"><i class="i-op i-op-set"></i>'+"区域设定"+'</a>&nbsp;' +
	        		                 '<a class="btn btn-l" href="javascript:;" onclick="del('+index+')"><i class="i-op i-op-del"></i>'+"删除"+'</a>&nbsp;';
		        	   }else if (row.publicStatus==1) {
		        		   content = '<a class="btn btn-l btn-8" "><i class="i-op i-op-set btn-8"></i>'+"销售价设置"+'</a>&nbsp;'+
	        		                 '<a class="btn btn-l btn-8" "><i class="i-op i-op-set btn-8"></i>'+"区域设定"+'</a>&nbsp;' +
	        		                 '<a class="btn btn-l btn-8" "><i class="i-op i-op-del btn-8"></i>'+"删除"+'</a>&nbsp;';
		        	   }	  
		        		return content;
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
            var count = $("input[name='phone_charge_way']").length + $("input[name='companyList']").length;
            for ( var i = 0; i < data.rows.length; i++) {
                //根据status值使复选框 不可用
                if (data.rows[i].publicStatus == 1) {
                    $("input[type='checkbox']")[i + count + 1].disabled = true;
                    $("input[type='checkbox']")[i + count + 1].readOnly = true;
                    $("input[type='checkbox']")[i + count + 1].hidden = true;
                }

            }
        },
        onSelectAll : function(rows) {
            //根据status值  全选时某些行不选中
            $.each(rows, function(i, n) {
                if (n.publicStatus == 1) {
                    $('#dataGrid2').datagrid('unselectRow', i);
                }
            });
        },
        toolbar:'#tb'
  	});
	
    pageopt = $('#dataGrid2').datagrid('getPager').data("pagination").options;
}

function regionSaleSet(index) {
	var row = $("#dataGrid2").datagrid("getRows");
	if (row == '')
		alert("未选中数据行!");
	//拼装显示
	var content = "<label id='region_sale_price_setting_faceValue' style='margin-left:10px;'>面值:&nbsp;&nbsp;</label><span>"+row[index].faceValue+"</span>元";
	content += "<br><br>";
	content += "<label style='margin-left:10px;'>运营商:&nbsp;&nbsp;</label><span>"+row[index].company+"</span>";
	content += "<br><br>";
	content += "<label style='margin-left:10px;'>销售区域:&nbsp;&nbsp;</label>";
	content += "<span>";
	var regionArr = row[index].saleRegion.split(",");
	for (var i=0; i < regionArr.length; i++) {
		if (i !=0 && i% 5== 0)
			content += "<br><br>";
		content += regionArr[i] + ",";
	}
	if (regionArr.length > 0)
		content = content.substr(0, content.length - 1);
	content += "</span>";
	content += "<br><br>";
	content += "<label id='region_sale_price' style='margin-left:10px;'>销售价:&nbsp;&nbsp;</label>";
	content += "<input class='easyui-textbox' style='width:50px;height:25px;' value="+row[index].salePrice+">";
	content += "<input class='goods_id_class' id='id_goods_id' type='hidden' value="+row[index].id+">";
	content += "<div id='div_region_sale_set' class='dialog-button'><a  class='btn'   onclick='closeDialog(\"#region_sale_price_setting\")'>" +
			   "<i class='i-close'></i>取消</a>&nbsp;&nbsp;<a href='javascript:void(0);' onclick='regionSalePriceSet()' class='btn btn-1'><i class='i-ok'></i>确定</a></div>";
	
	
	$("#region_sale_price_setting").dialog({
		title:'销售价设置',
		content:content,
		width:300,
		height:250,
		closable: false,
		buttons:'#div_region_sale_set',
		onClose:function(){
			$('#div_region_sale_set').remove();
			$('#region_sale_price_setting').empty();
			$("#dataGrid2").datagrid('reload');
		}
	});
}

//消息框关闭
function closeDialog(o) {
	$(o).dialog('close');
}

//区域销售价格设置
function regionSalePriceSet() {
	var goodsId = $("#id_goods_id").val();
	var salePrice = $("#region_sale_price").next().val();
    if(salePrice <= 0){
        alert("区域价应在1-1000范围");
        return ;
    }

    $.messager.confirm({
        width: 400,
        title: '修改提示',
        msg: '<div class="content">你确认要修改这条记录吗?</div>',
        ok: '<i class="i-ok"></i> 确定',
        cancel: '<i class="i-close"></i> 取消',
        fn: function (r) {
            if (r) {
                $.post('../../vas/phonefare/test/updateSalePriceByFaceValue.do',{'goodId':goodsId, 'salePrice':salePrice}, function(data){
                    if (data.status == 200) {
                        info("设置成功");
                        closeDialog("#region_sale_price_setting");

                    } else {
                        alert("操作失败");
                    }
                });

            } else {
                return ;
            }
        }
    });



}

function regionSet(index) {
	
	var content = getAllSaleRegion(index , false);
	//添加按钮
	content += "<div id='div_btns' class='dialog-button'><a  class='btn'   onclick='javascript:$(\"#add_sale_region_div\").dialog(\"close\")'>" +
			"<i class='i-close'></i>取消</a>&nbsp;&nbsp;<a  class='btn btn-1' onclick='addedProvince("+index+")'><i class='i-ok'></i>确定</a></div>";
	
	$('#add_sale_region_div').dialog({
		closable:true,
		left:100,
		top:100,
		width:500,
		height:300,
		title:"销售地区",
		buttons:'#div_btns',
		content:content,
		onClose:function(){
			$("#div_btns").remove();
			$('#add_sale_region_div').empty();
		}
	});
}

function del(index) {
	var rows = $('#dataGrid2').datagrid("getRows");
	if (rows == '') {
		alert("选中任何数据");
		return;
	}
	//提示
	$.messager.confirm({
		title:'提示',
		style:{
			    left:300, // 与左边界的距离
			    top:300 // 与顶部的距离
			    },
	    msg:"<div class='content'>确定要删除所选的区域设置吗?</div>",
	    ok:'确认',
	    cancel:'取消',
	    fn:function(data) {
	    	if (data) {
	    		//确认OK
	    		var goodsId = rows[index].id;
	    		$.get('../../vas/phonefare/test/delPhoneFareWays.do', {'goodsId':goodsId}, function(data){
	    			if (data.status == 200) {
	    				$('#dataGrid2').datagrid("reload");
	    			}
	    			alert(data.msg);
	    		});
	    	}
	    }
	});
	
	
}
//批量删除方案
function batchDel() {
	var rows = $("#dataGrid2").datagrid("getSelections");

	if (rows.length == 0) {
		alert("未选择记录");
		return;
	}
	
	var goodIds = "";
	
	for (var i=0;i<rows.length;i++) {
		var row = rows[i];
        if(row.publicStatus == 0) {
            goodIds += row.id + ",";
        }
	}

	if (goodIds.length > 0)
		goodIds = goodIds.substr(0, goodIds.length-1);
	
	
	//提示
	$.messager.confirm({
		title:'提示',
		style:{
			    left:300, // 与左边界的距离
			    top:300 // 与顶部的距离
			    },
	    msg:"<div class='content'>确定要删除所选的区域设置吗?</div>",
	    ok:'确认',
	    cancel:'取消',
	    fn:function(data) {
	    	if (data) {
	    		//确认OK
	    		$.get('../../vas/phonefare/test/batchDelPhoneFareWays.do', {'goodIds':goodIds}, function(data){
	    			if (data.status == 200) {
	    				info(data.msg);
	    				$("#dataGrid2").datagrid("reload");
	    			}
	    			else 
	    				alert(data.msg);
	    		});
	    	}
	    }
	});
	
}

//批量更新
function batchUpdate() {
	
	var rows = $("#dataGrid2").datagrid("getSelections");
	if (rows.length == 0) {
		alert("未选择记录");
		return;
	}
	
	var faceValue = rows[0].faceValue;
	
	var goodIds = '';
	
	for (var i=0;i<rows.length;i++) {
		var row = rows[i];
		goodIds += row.id + ",";
	}
	
	if (goodIds.length > 0)
		goodIds = goodIds.substr(0, goodIds.length-1);
	
	//提示
	$.messager.confirm({
		title:'提示',
		style:{
			    left:300, // 与左边界的距离
			    top:300 // 与顶部的距离
			    },
	    msg:"<div class='content'>确定要把所选区域的销售价批量同步为面值吗?</div>",
	    ok:'确认',
	    cancel:'取消',
	    fn:function(data) {
	    	if (data) {
	    		//确认OK
	    		$.get('../../vas/phonefare/test/batchUpdateSalePrice.do', {'goodIds':goodIds, 'faceValue':faceValue}, function(data){
	    			if (data.status == 200) {
	    				info(data.msg);
	    				$("#dataGrid2").datagrid("reload");
	    			}
	    			else 
	    				alert(data.msg);
	    		});
	    	}
	    }
	});
	
} 

function alert(msg) {
	$.messager.alert({
		title:'系统提示',
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



