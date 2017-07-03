//页面加载时，立即加载数据
$(function() {
	var initMenu = function(data){
		$("#accordion").html('');
		$.each(data,function(i,v){
			var content = '';
			if(v.childNode != null){
				$.each(v.childNode,function(j,k){
					var path = '<a href="javascript:void(0);"  src="'+k.menuPath+'?v=1.0.0"  class="cs-navi-tab" title="'+k.menuName+'" >'+k.menuName+'</a>';
					if(k.openType && k.openType == '_blank'){
						path = '<a href="'+k.menuPath+'?v=1.0.0" target="_blank" class="cs-navi-tab" id="mall" title="'+k.menuName+'">'+k.menuName+'</a>';
					}
					content += path;
				});
			}
			$("#accordion").accordion("add",{
			    title: v.menuName,
			    content: content
			});
		});

		$('.cs-navi-tab').click(function() {
			var $this = $(this);
			var href = $this.attr('src');
			var title = $this.text();
			addTab(title, href);
		});
	}

	var initUI = function(){
		$("#accordion").html('<div class="loading "><p class="bg-warning " style="padding:10px;">菜单加载中...</p></div>');
		var url = "../../diamondMenu.do?method=getWebPlatMenu";
		var dataModel = {};
		dataModel.sysCode = 'common-demo';
		dataModel.platCode = 'all';
		$.ajax({
			url:url,
			traditional:true,
			type:'get',
			async:true,
			data:dataModel,
			cache:false,
			success:function(data){
				initMenu(data);
			},error:function(data){
				$.messager.alert('提示', '操作失败!', 'error');
			}
		});
	}
	initUI();
});
