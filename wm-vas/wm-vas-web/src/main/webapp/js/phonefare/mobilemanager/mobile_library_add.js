$(function(){

    initProvince();

    $('#id_mobile_short').numberbox({
        min:0,
        precision:0,
        onChange:function (value) {
            if(value.length > 3){
                var newValue = $("#id_mobile_short").val().substring(0,3);
                $("#id_mobile_short").textbox("setValue", newValue);
            }

            if(value.length > 0 && value[0] != '1'){
                $("#mobileShortError").text("号段前缀必须是1开头");
                return ;
            } else {
                $("#mobileShortError").text("");
            }
        }
    });


    $('#id_mobile_num').numberbox({
        min:0,
        precision:0,
        onChange:function (value) {
            if(value.length > 7){
                var newValue = $("#id_mobile_num").val().substring(0,7);
                $("#id_mobile_num").textbox("setValue", newValue);
            }
        }
    });

});

function initProvince() {
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
                    initCity(provinceId);
                }
            });

        }
    });
}

function initCity(provinceId) {
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
                }
            });
        }
    });
}

function saveMobileLibrary() {

    var mobileSupplier = $("#id_mobile_supplier").textbox("getValue");
    var province = $("#province").textbox("getText");
    var city = $("#city").textbox("getText");

    var mobileShort = $("#id_mobile_short").val();
    var mobileNum = $("#id_mobile_num").val();

    if(null == mobileSupplier || "" == mobileSupplier){
        $("#supplierError").text("请选择运营商");
        return ;
    } else {
        $("#supplierError").text("");
    }

    if(null == province || "" == province){
        $("#provinceError").text("请选择省份");
        return ;
    } else {
        $("#provinceError").text("");
    }

    if(null == city || "" == city){
        $("#cityError").text("请选择城市");
        return ;
    } else {
        $("#cityError").text("");
    }

    if(null == mobileShort || "" == mobileShort){
        $("#mobileShortError").text("号段前缀不能为空");
        return ;
    } else {
        $("#mobileShortError").text("");
    }

    if(mobileShort[0] != '1'){
        $("#mobileShortError").text("号段前缀必须是1开头");
        return ;
    } else {
        $("#mobileShortError").text("");
    }

    if(mobileShort.length != 3){
        $("#mobileShortError").text("号段前缀长度必须为3位");
        return ;
    } else {
        $("#mobileShortError").text("");
    }

    if(null == mobileNum || "" == mobileNum){
        $("#mobileNumError").text("号段不能为空");
        return ;
    } else {
        $("#mobileNumError").text("");
    }

    if(mobileNum.length != 7){
        $("#mobileNumError").text("号段长度必须为7位");
        return ;
    } else {
        $("#mobileNumError").text("");
    }

    if(mobileShort[0] != mobileNum[0] || mobileShort[1] != mobileNum[1] || mobileShort[2] != mobileNum[2]){
        $("#mobileNumError").text("号段前缀与号段不匹配");
        return ;
    } else {
        $("#mobileNumError").text("");
    }


    $.post('../../../../vas/phone/mobile/manager/save.do',
        {
            'mobileSupplier':mobileSupplier,
            'mobileProvince':province,
            'mobileCity':city,
            'mobileShort':mobileShort,
            'mobileNum':mobileNum
        },
        function(data){
            if (data.status == 200) {

                $.messager.confirm({
                    width: 400,
                    title: '修改提示',
                    msg: '<div class="content">新增成功，是否继续新增？</div>',
                    ok: '<i class="i-ok"></i> 确定',
                    cancel: '<i class="i-close"></i> 取消',
                    fn: function (r) {
                        if (r) {
                            $("#id_mobile_short").textbox("setValue", '');
                            $("#id_mobile_num").textbox("setValue", '');
                            return ;
                        } else {
                            goBack2();
                        }
                    }
                });

            } else {
                alert(data.msg);
            }
        });
}

function goBack2() {
    window.location.href = "mobile_library.html";
}