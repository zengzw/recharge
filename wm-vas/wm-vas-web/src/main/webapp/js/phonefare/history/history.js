$(function(){
	var areaId = URI.getQueryString("areaId");
	console.log('------'+areaId)
    var url = '../../../../vas/auth/history/list.do?areaId='+areaId;
    init(url);

    Date.prototype.format = function (format) {
        var args = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
            "S": this.getMilliseconds()
        };
        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var i in args) {
            var n = args[i];
            if (new RegExp("(" + i + ")").test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
        }
        return format;
    };
});

var  URI = {
	    getQueryString : function(name){
	        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	        var r = window.location.search.substr(1).match(reg);
	        if(r!=null) return  unescape(r[2]); return null;
	    },goto : function(href){
	        href = href || window.location.href;
	        window.location.href = href;
	    },refresh : function(){
	        window.location.reload();
	    }
	}

function init(url) {
    $("#id_history_datagrid").datagrid({
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
        queryParams:{'mobileNum':'','mobileShort':''},
        columns:[
            [
                {field:'startTimeStr',title:'开始时间',width: '10%', align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'endTimeStr',title:'结束时间',width: '10%', align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'showEndTimeStr',title:'中奖显示截止时间',width: '10%', align:'center',formatter:function(value,row, index) {
                    return value;
                }},
                {field:'createTimeStr',title:'操作时间',width: '10%', align:'center',formatter:function(value,row, index) {
                	return value;
                }}
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
        	if(data.rows.length > 0){
        		$("#areaNameString").text(data.rows[0].areaName);
        	}
        },

    });

}