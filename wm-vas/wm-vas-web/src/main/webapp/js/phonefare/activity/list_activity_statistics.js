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

		URL:function(){
			return {
				list:'/vas/activity/statistics/list',
				exp:'/vas/activity/statistics/export'
			}
		},

		getListData:function(){
			$('#id_activity_statistics').datagrid({
				url: this.URL().list,
				striped:false,
				fitColumns:true,
				fixed:true,
				singleSelect:true,
				rownumbers:false,
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
				          {field:'id',title:'排序',width:20,align:'center',formatter:function(value,data,index){
				        	  return index + 1;
				          }},
				          {field:'areaName',title:'下单县域',width:90,align:'center'},
				          {field:'areaCount',title:'县域参与量',width:90,align:'center'},
				          {field:'storeName',title:'下单网点',width:120,align:'center'},
				          {field:'storeCount',title:'网点参与量',width:80,align:'center'}
				          ]],
				          onLoadError: function() {
				        	  $.messager.alert('操作提示', '获取信息失败...请联系管理员!', 'error');
				          },
				          onLoadSuccess:function(data){
				        	  console.log(data)
				          },
				          toolbar:'#tb'
			});

			pageopt = $('#id_activity_statistics').datagrid('getPager').data("pagination").options;
		},


		/**
		 * 绑定事件
		 */
		bindEven:function(){
			$("#id_search").on('click',function(){
				if(listActivityManager.validateParam()){
					var searchFormData = JSONTools.arrayToJson($("#search_form").serializeArray());
					$("#id_activity_statistics").datagrid("load", {"params" : JSON.stringify(searchFormData)});
				}
			});


			$("#id_clear").on('click',function(){
				$("#province").combobox("setValue", '');
				$("#city").combobox("setValue", '');
				$("#area").combobox("setValue", '');
				$("#store").combobox("setValue", '');
				$("#startDate").textbox("setValue", '');
				$("#endDate").textbox("setValue", '');
				$("#id_activity_statistics").datagrid('load',{});
			});

			$("#id_export").on('click',function(){
				location.href= listActivityManager.URL().exp + "?"+$("#search_form").serialize();
			});
		},

		/**
		 * 参数校验
		 * @returns
		 */
		validateParam:function(){
			return $("#search_form").form('enableValidation').form('validate');
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
							if(cityId!= ''){
								cityManger.initArea(cityId);
							} else {
								cityManger.initArea(-1);
							}

							cityManger.initStore();
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
							cityManger.initStore();
						}
					});
				}
			});
		},

		initStore:function() {
			var area = $('#area').combobox('getValue');
			if(area == ''){
				area = -1;
			}
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
