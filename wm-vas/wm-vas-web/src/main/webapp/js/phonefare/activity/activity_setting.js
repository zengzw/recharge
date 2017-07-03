$(function(){
	
	activitySettingManager.init();
	
});

var URL = {
	save:'/app/vas//activity/save',
	back:'/views/activity/list_activity.html'
}

var receiveShopsMng;
var selectAreaIds="";
var selectButton= 1;

var activitySettingManager = {
		init:function(){
			 receiveShopsMng = new receiveShopsManager(); //创建自定义县域数据管理器
			 
			 this.bindEvent();
		},
		
		bindEvent:function(){
			$("#receiveScore_1").click(function(){
				$('#JDF_selectAreaId').show();
				selectButton = 2;
			});
			
			 $("#receiveScore_0").click(function(){
		         receiveShopsMng.clearReceiveShops();
		         $('#JDF_selectAreaId').hide();
		         selectButton = 1;
		      });
			
			
			
			$('#JDF_selectArea_Button').on('click',function(){
				 openCountyDialog();
			})
			
			
			//保存
			$("#id_save").on('click',function(){
				if(!activitySettingManager.validate()){
					return;
				}
				
				if(selectButton == 2 && receiveShopsMng.getReceiveShops().length == 0){
					commonTools.errorMsgDialog("请选择县域");
					return;
				}
				
				commonTools.confirmDialog("确认要保存活动地区设置吗?",function(f){
					console.log('--------'+f)
					if(f){
					  var formData = JSONTools.arrayToJson($("#setting_form").serializeArray());
				        formData.lstReceiveShop = receiveShopsMng.getReceiveShops();
				        
				        commonTools.postByAjax(URL.save,function(data){
				                if(data.status == 200){
				                  commonTools.showMsgDialogTime("保存成功！",URL.back,2000);
				                }else {
				                	commonTools.errorMsgDialog(data.msg);
				                }
				            },JSON.stringify(formData));
					}
					 
				});
		     
			})

			$("#id_back").on('click',function(){
				location.href= URL.back;
			})
		},
		
		
		validate:function(){
			var $beginTime = $("#startDate"),
			 $endTime = $("#endDate"),
			 $showTime = $("#checkEndTime");
		
			//删除所有错误日志
			$(".customer-error").remove();
			//错误信息
			var errorMsg = "&nbsp;<span class='customer-error' style='color:red' id='startDate_error'>{0}</span>";
			
		
			var beginTimeStr = $beginTime.datetimebox("getValue"),
			 endTimeStr = $endTime.datetimebox("getValue"),
			 showTimeStr = $showTime.datetimebox("getValue"),
			 nowTime = new Date().getTime(); //当前时间搓
			
			if(beginTimeStr == "" || beginTimeStr.length == 0){
				$beginTime.next("span").after(errorMsg.format("开始时间不能为空"));
				return false;
			}
			
		    var beginTime = new Date(beginTimeStr).getTime();
			if(beginTime < nowTime){
					$beginTime.next("span").after(errorMsg.format("开始时间不能小于当前时间"));
					return false;
		        }
		        
			
			if(endTimeStr == "" || endTimeStr.length == 0){
				$endTime.next("span").after(errorMsg.format("结束时间不能为空"));
				return false;
			}
			
			var endTime  = new Date(endTimeStr).getTime();
		    if(endTime < nowTime){
	        	$endTime.next("span").after(errorMsg.format("结束时间不能小于当前时间"));
				return false;
	        }
	        if(endTime < beginTime){
	        	$endTime.next("span").after(errorMsg.format("结束时间不能小于开始时间"));
				return false;
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
		}
		
	
}



/**
 * 自定义网点管理器
 * @param selectIds
 */
var receiveShopsManager = function(items){
    this.receiveShops = [];
    this.init(items);
};

receiveShopsManager.prototype = {
    init : function(items){
        this.setReceiveShops(items);
    },
    setReceiveShops : function(items){
        if(!items)  return;
        var cityReceivedShopList = [];
        $(items).each(function(index,item){
            var  cityReceivedShop = {};
            cityReceivedShop.areaId = item.id;
            cityReceivedShop.areaName = item.areaName;
            cityReceivedShopList.push(cityReceivedShop);
        });
        this.receiveShops = cityReceivedShopList;
    },
    loadReceiveShops : function(loadData){
        if(!loadData) return;
        var cityReceivedShopList = [];
        $(loadData).each(function(index,item){
            var  cityReceivedShop = {};
            cityReceivedShop.areaId = item.id;
            cityReceivedShop.areaName = item.areaName;

            cityReceivedShopList.push(cityReceivedShop);
        });
        this.receiveShops = cityReceivedShopList;
    }
    ,getReceiveShops : function(){
    	/*  var cityReceivedShopList = [];
    	   var  cityReceivedShop = {};
    	   cityReceivedShop.areaId =234;
           cityReceivedShop.areaName = "西藏自治区江达县县域运营中心";
           cityReceivedShopList.push(cityReceivedShop);
    	 return  cityReceivedShopList;*/
       return this.receiveShops;
    },
    clearReceiveShops : function(){
        this.receiveShops.removeAll();
    },
    getReceiveShopIdList : function(){
        var shopIdList = [];
        for(var i = 0; i < this.receiveShops.length; i++){
            shopIdList.push(this.receiveShops[i].areaId);
        }

        return shopIdList;
    }
}

/**
* 打开县域选择框
*/
var openCountyDialog  = function(){
  bigstyChk_openDialog = 0; //公共组建问题，临时初始化0解决一下
  sel_openDialog = receiveShopsMng.getReceiveShopIdList();
  selDialog(false, 1, 1, 'xy','CallBack');

}

//公共弹窗回调
function CallBack_openDialog(type){
  if(type == 'wd'){
      if(obj_openDialog.length == 0){
          commonTools.showMsgDialogTime("error","请选择网点!",null,1000,300);
          return "unclose";
      }
      receiveShopsMng.setReceiveShops(obj_openDialog);
      sel_openDialog=[];
      obj_openDialog=[];
  }else if(type == 'xy'){
      if(obj_openDialog.length == 0){
          commonTools.showMsgDialogTime("error","请选择县域!",null,1000,300);
          return "unclose";
      }
      receiveShopsMng.setReceiveShops(obj_openDialog);
      sel_openDialog=[];
      obj_openDialog=[];
  }
}
