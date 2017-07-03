$(function(){
	listActivityManager.init();
});


var listActivityManager = {

		/**
		 * 初始化
		 */
		init:function(){

			cityManger.init();
			this.getListData();
			this.bindEven();
		},

		/**
		 * 活动URL
		 * @returns {___anonymous228_273}
		 */
		URL:function(){
			return {
				list:'/app/vas/phone/activity/list',
				settings:'/views/activity/activity_setting.html',
				update:'/app/vas/activity/update',
				history:'/views/phonefare/history/history.html'
			}
		},

		/**
		 * 获取列表数据
		 */
		getListData:function(){
			$('#id_activity').datagrid({
				url: this.URL().list,
				striped:false,
				fitColumns:true,
				fixed:true,
				checkOnSelect: false,
				selectOnCheck:true,
				pagination:true,
				method: "GET",
				fitColumns:true,//true只适应列宽，防止出现水平滚动条；默认为false
				//rownumbers:true,
				//pageList: [10, 20, 50],
				pageSize: 20,
				loadFilter:function(data){
					return data;
				},
				columns:[[
				          {field:'id', title:'选择', align:'center', checkbox:true},
				          {field:'areaName',title:'活动县域',width:90,align:'center'},
				          {field:'beginTimeStr',title:'开始时间',width:90,align:'center'},
				          {field:'endTimeStr',title:'结束时间',width:120,align:'center'},
				          {field:'checkEndTimeStr',title:'中奖显示截止时间',width:80,align:'center'},
				          {field:'strActivityStatus',title:'活动状态',width:80,align:'center'},
				          {field:'operation',title:'操作',width:80,align:'center',formatter:function(value,row, index) {
				        	  // return "<a href='javascript:void(0)' target='_blank' onClick='listActivityManager.queryHistory("+row.areaId+")'>查看</a>";
				        	  return "<a href='../../../views/phonefare/history/history.html?areaId="+row.areaId+"'>查看</a>";
				          }}
				          ]],
				          onLoadError: function() {
				        	  $.messager.alert('操作提示', '获取信息失败...请联系管理员!', 'error');
				          },
				          onLoadSuccess:function(data){
				        	  console.log(data)
				          },
				          toolbar:'#tb'
			});

			pageopt = $('#id_activity').datagrid('getPager').data("pagination").options;
		},


		/**
		 * 绑定事件
		 */
		bindEven:function(){
			$("#id_search").on('click',function(){
				if(listActivityManager.validateParam()){
					var searchFormData = JSONTools.arrayToJson($("#search_form").serializeArray());
					$("#id_activity").datagrid("load", {"params" : JSON.stringify(searchFormData)});
				}
			});


			$("#id_clear").on('click',function(){
				$("#province").combobox("setValue", '');
				$("#city").combobox("setValue", '');
				$("#area").combobox("setValue", '');
				$("#id_selectStatus").combobox("setValue", '-1');

				listActivityManager.reload_list();
				$('#id_batch_update').hide();
			});


			$("#id_setting").on('click',function(){
				location.href= listActivityManager.URL().settings;
			})


			// 控制按钮的显示
			$("#id_selectStatus").combobox({  
				onChange:function(v){  
					if(v == -1){
						$("#id_batch_update").hide();
					}else{
						$("#id_batch_update").show();
					}

					$("#id_search").click();
				}
			})  

			//批量编辑
			$("#id_batch_update").on('click',function(){
				$('#startDate').datetimebox('setValue', '');
				$('#endDate').datetimebox('setValue', '');
				$('#checkEndTime').datetimebox('setValue', '');
				$("#startDate").datetimebox( {disabled: false });
				//删除所有错误日志
				$(".customer-error").remove();

				var checkedItems = $('#id_activity').datagrid('getChecked');
				if(checkedItems.length == 0){
					commonTools.errorMsgDialog("请选择要修改的项");
					return;
				}

				//开始中，隐藏开始时间
				var status = $("#id_selectStatus").combobox("getValue");
				if(status == 1){
					$("#id_tr_start").hide();
				}else{
					$("#id_tr_start").show();
				}


				$('#id_update_dialog').dialog({
					title: '话费活动时间编辑',
					width: 420,
					height: 230,
					closed: false,
					cache: false,
					modal: true
				});
			})


			/*
			 * dialog-save 保存按钮
			 */
			$("#id_save").bind('click',function(){
				if(!listActivityManager.validate_update_params()){
					return;
				}

				var checkedItems = $('#id_activity').datagrid('getChecked');
				if(checkedItems.length == 0){
					commonTools.errorMsgDialog("请选择要修改的选项");
					return;
				}

				var ids = [];
				$.each(checkedItems, function(index, item){
					ids.push(item.id);
				});               
				var updateIds = ids.join(",");

				var formData = JSONTools.arrayToJson($("#update_form").serializeArray());
				formData.updateIds = updateIds;
				commonTools.postByAjax(listActivityManager.URL().update,function(data){
					if(data.status == 200){
						commonTools.successMsgDialog("保存成功！");
						$('#id_update_dialog').dialog("close");
						//重新加载列表
						listActivityManager.reload_list();
					}else {
						commonTools.errorMsgDialog(data.msg);
					}
				},JSON.stringify(formData));
			})

		},

		/**
		 * 参数校验
		 * @returns
		 */
		validateParam:function(){
			return $("#search_form").form('enableValidation').form('validate');
		},

		/**
		 * 修改参数校验
		 */
		validate_update_params:function(){
			var $beginTime = $("#startDate"),
			$endTime = $("#endDate"),
			$showTime = $("#checkEndTime");
			var status = $("#id_selectStatus").combobox("getValue");//当前选中状态

			//删除所有错误日志
			$(".customer-error").remove();
			//错误信息
			var errorMsg = "&nbsp;<span class='customer-error' style='color:red' id='startDate_error'>{0}</span>";


			var beginTimeStr = $beginTime.datetimebox("getValue"),
			endTimeStr = $endTime.datetimebox("getValue"),
			showTimeStr = $showTime.datetimebox("getValue"),
			nowTime = new Date().getTime(); //当前时间搓

			//状态位进行中，不判断开始时间，或者不跟开始时间做判断
			if(status != 1){
				if(beginTimeStr == "" || beginTimeStr.length == 0){
					$beginTime.next("span").after(errorMsg.format("开始时间不能为空"));
					return false;
				}

				var beginTime = new Date(beginTimeStr).getTime();
				if(beginTime < nowTime){
					$beginTime.next("span").after(errorMsg.format("开始时间不能小于当前时间"));
					return false;
				}

			}

			if(endTimeStr == "" || endTimeStr.length == 0){
				$endTime.next("span").after(errorMsg.format("结束时间不能为空"));
				return false;
			}

			var endTime  = new Date(endTimeStr).getTime();
			if(status != 1){
				if(endTime < beginTime){
					$endTime.next("span").after(errorMsg.format("结束时间不能小于开始时间"));
					return false;
				}
			}
			if(showTimeStr == "" || showTimeStr.length == 0){
				$showTime.next("span").after(errorMsg.format("显示截止时间不能为空"));
				return false;
			}


			var  showTime = new Date(showTimeStr).getTime();
			if(showTime < nowTime ){
				$showTime.next("span").after(errorMsg.format("结束时间不能大于开始时间"));
				return false;
			}
			if(showTime < endTime ){
				$showTime.next("span").after(errorMsg.format("中奖显示截止时间不能小于结束时间"));
				return false;
			}

			return true;
		},

		/**
		 * 查看历史
		 */
		queryHistory:function(id){
			location.href = this.URL().history +"?areaId="+id;
		},

		/**
		 * 重新加载列表
		 */
		reload_list:function(){
			var searchFormData = JSONTools.arrayToJson($("#search_form").serializeArray());
			$("#id_activity").datagrid("load", {"params" : JSON.stringify(searchFormData)});
		}

};



/**
 * 城市管理
 */
var cityManger = {
		init:function(){
			this.initProvince();
		},

		initProvince:function() {
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
							if(provinceId != ''){
								cityManger.initCity(provinceId);
							} else {
								cityManger.initCity(-1);
							}

						}
					});

				}
			});
		},

		initCity:function(provinceId) {
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
						editable: false,
						onChange: function () {
							var cityId = $('#city').combobox('getValue');
							if(cityId != ''){
								cityManger.initArea(cityId);
							} else {
								cityManger.initArea(-1);
							}

						}
					});
				}
			});
		},

		initArea:function(cityId) {
			var province = $('#province').combobox('getText');
			var city = $('#city').combobox('getText');
			if(city == ''){
				city = -1;
			}
			$.ajax({
				url: '../../../common/queryAreasByProvinceAndCityAndArea.do',
				data: {
					provinceName: province,
					cityName: city
				},
				async: false,
				cache: false,
				processData: true, // 转换data成查询字符串。默认值为true
				success: function (data) {
					$('#area').combobox({
						data: data,
						valueField: 'id',
						textField: 'areaName',
						editable: false,
						onChange: function () {
							//cityManger.initStore();
						}
					});
				}
			});
		},

		initStore:function() {
			var area = $('#area').combobox('getValue');
			$.ajax({
				url: '../../../common/queryStoresByAreaId.do',
				data: {
					areaId : area
				},
				async: false,
				cache: false,
				processData: true, // 转换data成查询字符串。默认值为true
				success: function (data) {
					$('#store').combobox({
						data: data,
						valueField: 'id',
						textField: 'shopName',
						editable: false
					});
				}
			});
		}


}
