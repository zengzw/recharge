;$(function(){
	var Process=function(){
		var _this=this;
		var proDom=$(".tsh-process");
		var length=_this.getNum(proDom);
		var promptArr=_this.getPrompt(proDom);
		var colorKeep=this.getColor(proDom);
		var processListW=(100/length)+"%";
		var strDom="<div class='tsh-processList'><div class='tsh-processNum'></div><div class='tsh-processPrompt'></div></div>";
		var from=$(".form");
		for(var i=0;i<length;i++){
			proDom.append(strDom);
			$(".tsh-processNum").eq(i).text(i+1);
			$(".tsh-processPrompt").eq(i).text(promptArr[i]);
		}
		var processListDom=$(".tsh-processList");
		$(".tsh-processList").eq("0").addClass("current");
		$(".tsh-processNum").eq("0").addClass("current");
		$(".tsh-processPrompt").eq("0").addClass("current");
		_this.getWidth(processListDom,processListW);
		$(".next").click(function(){
			var listNum=$(this).attr("data-num");
			_this.next(listNum,from,colorKeep);
		})
		$(".prev").click(function(){
			var listNum=$(this).attr("data-num");
			var listNumTrue=listNum-2;
			_this.prev(listNumTrue,from,colorKeep);
		})
	}
//	window["Process"]=Process;
	//开始定义方法
	Process.prototype={
		getColor:function(param){//颜色是否保持
			var color=param.attr("data-colorKeep");
			return color;
		},
		getNum:function(param){//获取过程步骤数
			var length=param.attr("data-num");
			return length;
		},
		getWidth:function(param_1,param_2){//设置processList宽度
			param_1.css({
				width:param_2
			})
		},
		getPrompt:function(param){//获取提示语
			var promptArr=param.attr("data-prompt").split(",")
			return promptArr;
		},
		next:function(param_1,param_2,param_3){//下一步
			param_2.children("table").hide();
			param_2.children("table").eq(param_1).show();
			if(param_3=="true"){
				$(".tsh-processList").eq(param_1).addClass("current");
				$(".tsh-processNum").eq(param_1).addClass("current");
				$(".tsh-processPrompt").eq(param_1).addClass("current");
			}else{
				$(".tsh-processList").removeClass("current");
				$(".tsh-processNum").removeClass("current");
				$(".tsh-processPrompt").removeClass("current");
				$(".tsh-processList").eq(param_1).addClass("current");
				$(".tsh-processNum").eq(param_1).addClass("current");
				$(".tsh-processPrompt").eq(param_1).addClass("current");
			}	
		},
		prev:function(param_1,param_2,param_3){//上一步
			param_2.children("table").hide();
			param_2.children("table").eq(param_1).show();
			if(param_3=="true"){
				$(".tsh-processList").eq(param_1+1).removeClass("current");
				$(".tsh-processNum").eq(param_1+1).removeClass("current");
				$(".tsh-processPrompt").eq(param_1+1).removeClass("current");
			}else{
				$(".tsh-processList").removeClass("current");
				$(".tsh-processNum").removeClass("current");
				$(".tsh-processPrompt").removeClass("current");
				$(".tsh-processList").eq(param_1).addClass("current");
				$(".tsh-processNum").eq(param_1).addClass("current");
				$(".tsh-processPrompt").eq(param_1).addClass("current");
			}
		}
	}
	new Process();
}(jQuery))
