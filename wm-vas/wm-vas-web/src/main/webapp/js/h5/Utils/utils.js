/**
 * Created by Hlc on 2017/6/9.
 * JavaScript的工具类
 */
var Tools = {

    /**
     * 判断字符串是否为空
     * @param str
     * @returns {boolean}
     */
    isEmpty: function (str) {
        return (str == "" || str == undefined || str == null || str == "[]") ? true : false;
    },

    /**
     * 判断集合是否为空
     * @param list
     */
    isEmptyList: function (list) {
        return (list == "" || list == undefined || list == null || str == "[]" || list.length == 0) ? true : false;
    },

    /**
     * 查询链接带参
     * @param name
     * @returns {null}
     */
    getQueryString: function (param) {
        var reg = new RegExp("(^|&)" + param + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    },
    /**
     * 获取token
     */
    getToken: function () {
        var token = sessionStorage.getItem("token");
        if (Tools.isEmpty(token)) {
            token = Tools.getQueryString("token");
        }
        sessionStorage.setItem("token", token);
        return token;
    },
    /**
     * 获取OpenId
     */
    getOpenId: function () {
        var openId = sessionStorage.getItem("openId");
        if (Tools.isEmpty(openId)) {
            openId = Tools.getQueryString("openId");
        }
        sessionStorage.setItem("openId", openId);
        return openId;
    },

    /**
     * 保留2位小数
     * @param x
     * @returns {Number}
     */
    returnFloat: function returnFloat(value){
        var value=Math.round(parseFloat(value)*100)/100;
        var xsd=value.toString().split(".");
        if(xsd.length==1){
            value=value.toString()+".00";
            return value;
        } 
        if(xsd.length>1){ 
            if(xsd[1].length<2){
                value=value.toString()+"0";
            } return value;
        }
    },

    /**
     * 检验手机号码
     * @param phone
     * @returns {boolean}
     */
    checkNumber: function (phone) {
        return (/^1[34578]\d{9}$/.test(phone));
    },

    getMyOrderPageUrl : function () {
        return "http://testfwc.testtsh365.cn/personal/perscenter.html";
        // return "http://fwc.tsh365.cn/personal/perscenter.html";//正式环境地址
    },
    getMainUrl : function () {
        return "http://testfwc.testtsh365.cn/index.html";
        // return "http://fwc.tsh365.cn/index.html"; //正式环境地址
    }
}