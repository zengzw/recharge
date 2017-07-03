/**
 * Created by dengjd on 2016/6/3.
 */

/** ---------------  原生数组对象扩展区域 -------------------  **/

/**
 * 获取元素下标
 * @param val
 * @returns {number}
 */
Array.prototype.indexOf = function (val) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) return i;
    }
    return -1;
};

/**
 * 判断值是否存在数组中
 * @param val
 * @returns {boolean}
 */
Array.prototype.contains = function (obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}

/**
 * 移除数组中的某个元素
 * @param val
 */
Array.prototype.remove = function (val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};

/**
 * 移除数组全部元素
 */
Array.prototype.removeAll = function () {
    this.splice(0, this.length);
};


/** ---------------  原生字符对象扩展区域 -------------------  **/

/**
 * 替换字符串占位
 * @returns {String}
 */
String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};
/**
 * 是否为空白字符串
 * @returns {boolean}
 */
String.prototype.isEmpty = function(){
    return this.trim().length == 0;
}

String.prototype.trim = function() {
	  return this.replace(/^\s*((?:[\S\s]*\S)?)\s*$/, '$1');
}

/** 统一资源管理器工具类 **/
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
/**
 * JSON 处理工具类
 * @type {{arrayJson: Function}}
 */
var JSONTools = {
    arrayToJson : function(array){
        var serializeObj={};
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    },arrayToListJson : function(array){
        var serializeObj={};
        $(array).each(function(){
            if(serializeObj[this.name]){
                if($.isArray(serializeObj[this.name])){
                    serializeObj[this.name].push(this.value);
                }else{
                    serializeObj[this.name]=[serializeObj[this.name],this.value];
                }
            }else{
                serializeObj[this.name]=this.value;
            }
        });
        return serializeObj;
    }
};

/** ---------------  自定义区域 -------------------  **/
var commonTools = {
    errorMsgDialog: function (msg) {
        $.messager.alert({
            title: '错误提示',
            msg: '<div class="content">{0}</div>'.format(msg),
            ok: '<i class="i-ok"></i> 确定',
            icon: 'error'
        });
    },
    successMsgDialog: function (msg) {
        $.messager.alert({
            title: '成功提示',
            msg: '<div class="content">{0}</div>'.format(msg),
            ok: '<i class="i-ok"></i> 确定',
            icon: 'info'
        });
    },confirmDialog: function (msg,callback) {
        $.messager.confirm({
            title : "确认提示",
            msg:'<div class="content">{0}</div>'.format(msg),
            fn:callback
        });
    },
    showMsgDialogTime:function(msg,url,timeout){
        $.messager.show({
            title: '',
            showType:'fade',
            msg:'<i class="i-l i-l-info"></i><span>{0}</span>'.format(msg),
            timeout:timeout,
            height:150,
            width:350,
            style:{
                right:'',
                bottom:''
            }
        });
        if(url){
            setTimeout(function(){
                window.location.href=url;
            },timeout);
        }
  },

getByJsonp: function (requestUrl,callback,isAsync) {
        isAsync = isAsync || false;
        $.ajax({
            type: "get",
            async: isAsync,
            url: requestUrl,
            dataType: "jsonp",
            success: function (data) {
                callback(data);
            }
        });
    },getByAjax: function (requestUrl,callback,isAsync) {
        isAsync = isAsync || false;
        $.ajax({
            type: "get",
            async: isAsync,
            url: requestUrl,
            success: function (data) {
                callback(data);
            }
        });
    },
    postByJsonp: function (requestUrl,callback,params,isAsync) {
        params= params || null;
        isAsync = isAsync || false;
        $.ajax({
            type: "post",
            async: isAsync,
            url: requestUrl,
            data:params,
            dataType: "jsonp",
            success: function (data) {
                callback(data);
            }
        });
    },
    postByAjax: function (requestUrl,callback,params,isAsync) {
        isAsync = isAsync || false;
        $.ajax({
            type: "post",
            async: isAsync,
            url: requestUrl,
            data:params,
            headers : {
                'Content-Type' : 'application/json;charset=utf-8'
            },
            success: function (data) {
                callback(data);
            }
        });
    }
};


Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
        // millisecond
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}
var  DateTools =  {
    formatDate : function(value,pattern) {
        if (value == null || value == '') {
            return '';
        }
        var dt;
        if (value instanceof Date) {
            dt = value;
        } else {
            dt = new Date(value);
        }

        return dt.format(pattern); //扩展的Date的format方法(上述插件实现)
    }
};
