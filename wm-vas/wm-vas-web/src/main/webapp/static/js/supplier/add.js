
$.getFormData = function (form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};

$.acceptParam = function (paras) {
    var url = location.href;
    var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
    var paraObj = {}
    for (i = 0; j = paraString[i]; i++) {
        paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=") + 1, j.length);
    }
    var returnValue = paraObj[paras.toLowerCase()];
    if (typeof(returnValue) == "undefined") {
        return "";
    } else {
        return returnValue;
    }
};
$(function () {
    initAllArea();
});
function initAllArea() {
    $('#addAreaId').combobox({
        url: "../../member/admin/getArea.do",
        valueField: 'id', 
        textField: 'name', 
        panelHeight: '200',
        editable: true,
        loadFilter: function (returnDTO) {
            if (returnDTO.status == 200) {
                return returnDTO.data;
            } else {
                return [];
            }
        }, onSelect: function (o) {
            $("#areaName").val(o.name);
        }
    });
}
function addCustomize() {
    if ($("#myform").form("validate")) {
        var data = $.getFormData($("#myform"));
        console.info(data);
        var url = "";
        $.messager.progress({
            width: 400,
            title: '',
            msg: '正在生成会员卡...'
        });
        $.post(url, data, function (returnDTO) {
            if (returnDTO.status == 200) {
                $.messager.show({
                    title: '',
                    msg: '<i class="i-l i-l-info"></i><span>' + returnDTO.msg + '</span>',
                    showType: 'fade',
                    timeout: 2400,
                    height: 150,
                    width: 360,
                    style: {
                        right: '',
                        bottom: ''
                    }
                });
                close_();
                search();
            } else {
                $.messager.show({
                    title: '',
                    msg: '<i class="i-l i-l-warning"></i><span>' + returnDTO.msg + '</span>',
                    showType: 'fade',
                    timeout: 2400,
                    height: 150,
                    width: 360,
                    style: {
                        right: '',
                        bottom: ''
                    }
                });
            }
            $.messager.progress('close');
        });
    }
}
function close_() {
    $('#dialog').dialog('close');
}