// 代金券开奖js
var cashCouponLotteryManager = {

    // “选择”代金券事件
    selectDaiJinJuan:function (orderCode, chargeAmount) {

        // 获取可用代金券列表
        var daiJinJuanData;
        var status = 0;
        var errorMsg = '';
        $.ajax({
            url: '../../../../vas/activity/queryCashCouponList.do',
            data:{
                orderCode:orderCode
            },
            async: false,//同步
            cache: false,
            success: function (data) {
                console.log("---->queryCashCouponList:"+JSON.stringify(data));

                status = data.status;
                errorMsg = data.msg;

                if (data.status == 200) {
                    daiJinJuanData = data.data;
                }
            }
        });

        if(status != 200){
            alert(errorMsg);
            return ;
        }

        var currentId = $("#submitDaiJinJuanId"+orderCode).text();
        console.log("---->id:"+currentId);
        var addFlag = true;

        // 自动加减总数
        if(null != daiJinJuanData && daiJinJuanData.length > 0){

            var daiJinJuanMoneyArray = $("span[id^='submitDaiJinJuanId']");
            for(var j=0;j<daiJinJuanMoneyArray.length;j++){

                if(daiJinJuanMoneyArray[j].innerText != ''){
                    for(var i=0;i<daiJinJuanData.length;i++){
                        if(daiJinJuanMoneyArray[j].innerText == daiJinJuanData[i].id){
                            daiJinJuanData[i].count = daiJinJuanData[i].count - 1;
                            if(daiJinJuanData[i].count == 0){
                                daiJinJuanData.splice(i, 1);
                                continue ;
                            }
                        }

                        if(addFlag && currentId == daiJinJuanData[i].id){
                            daiJinJuanData[i].count = daiJinJuanData[i].count + 1;
                            addFlag = false;
                        }


                    }
                }
            }

        }

        if(null == daiJinJuanData || daiJinJuanData.length == 0){
            alert("无可用代金券");
            return ;
        }

        // 组装table内容
        var dcontent = "<table width='100%'>";
        dcontent += "<tr style='height: 30px;'>";
        dcontent += "<td colspan='2' align='center'>订单编号: "+orderCode+"</td>";
        dcontent += "<td colspan='2'>充值金额: "+chargeAmount+"元</td>";
        dcontent += "<td colspan='3' align='center'><font color='red'>注：“确定”操作后对应优惠券有效张数临时减1</font></td>";
        dcontent += "</tr>";
        dcontent += "<tr style='height:30px;background: #E0ECFF;font-size: 100%' align='center'>";
        dcontent += "<th width='15%' >优惠券活动名称</th>";
        dcontent += "<th width='15%' >适用范围</th>";
        dcontent += "<th width='15%' >优惠区域</th>";
        dcontent += "<th width='10%' >面额</th>";
        dcontent += "<th width='20%' >使用时间范围</th>";
        dcontent += "<th width='15%' >有效张数</th>";
        dcontent += "<th width='10%' >操作</th>";
        dcontent += "<tr>";

        if(null != daiJinJuanData && daiJinJuanData.length > 0){
            for(var i=0;i<daiJinJuanData.length;i++){
                if(daiJinJuanData[i].count > 0){
                    dcontent += "<tr style='height:25px;font-size: 100%;background: #f0f8ff;' align='center'>";
                    dcontent += "<td width='15%'>"+daiJinJuanData[i].name+"</td>";
                    dcontent += "<td width='15%'>"+daiJinJuanData[i].range+"</td>";
                    dcontent += "<td width='15%'>"+daiJinJuanData[i].area+"</td>";
                    dcontent += "<td width='10%'>"+daiJinJuanData[i].amount+"元</td>";
                    dcontent += "<td width='20%'>"+daiJinJuanData[i].dateRange+"</td>";
                    dcontent += "<td width='15%'>"+daiJinJuanData[i].count+"</td>";
                    dcontent += "<td width='10%'><a href='#' onclick=cashCouponLotteryManager.selectOneDaiJinJuan('"+orderCode+"','"
                        +daiJinJuanData[i].id+"','"
                        +daiJinJuanData[i].name+"','"
                        +daiJinJuanData[i].amount+"','"
                        +daiJinJuanData[i].range+"','"
                        +daiJinJuanData[i].area+"','"
                        +daiJinJuanData[i].dateRange+"','"
                        +daiJinJuanData[i].count+"');>确定</a></td>";
                    dcontent += "<tr>";
                }

            }
        }

        dcontent += "</table>";

        $('#id_select_daiJinJuan').dialog({
            closable:true,
            left:($(window).width()-1200)/2,
            top:150,
            width:1100,
            height:600,
            modal: true,
            title: "选择代金券",
            content: dcontent,
            onClose:function(){
                $('#id_select_dai').empty();
            }
        });


    },



    /**
     * 选一种代金券，中奖金额总计动态生成
     * @param orderCode
     * @param id
     * @param name
     * @param amount
     * @param range
     * @param area
     * @param dateRange
     * @param count
     */
    selectOneDaiJinJuan : function (orderCode, id, name, amount, range, area, dateRange, count) {

        $('#id_select_daiJinJuan').dialog("close")

        $("#submitDaiAmount"+orderCode).text(name+"  (面额: "+amount+"元)");

        $("#submitDaiJinJuanAmount"+orderCode).text(amount);
        $("#submitDaiJinJuanId"+orderCode).text(id);
        $("#submitDaiJinJuanName"+orderCode).text(name);
        $("#submitDaiJinJuanRange"+orderCode).text(range);
        $("#submitDaiJinJuanRegion"+orderCode).text(area);
        $("#submitDaiJinJuanUseRange"+orderCode).text(dateRange);
        $("#submitDaiJinJuanCount"+orderCode).text(count);


        var daiJinJuanMoneyArray = $("span[id^='submitDaiJinJuanAmount']");

        var totalMoney = 0;
        for(var i=0;i<daiJinJuanMoneyArray.length;i++){
            if(daiJinJuanMoneyArray[i].innerText != ''){
                totalMoney = parseInt(totalMoney) + parseInt(daiJinJuanMoneyArray[i].innerText);
            }
        }

        $("#submitDaiJinJuanTotalMoney").text(totalMoney);
    },



    // 点击“代金券开奖”事件
    coming2meDaiJinJuan : function (){

        var selectRow = $("#id_order_change_datagrid2").datagrid("getChecked");
        if(null == selectRow || selectRow.length == 0){
            alert("请选择需开奖的订单");
            return ;
        }

        if(selectRow.length > 20){
            alert("已勾选20条订单，每次最多可对20条订单进行开奖");
            return ;
        }

        var content = "<div width='100%'><table width='100%' align='center' class='easyui-table' style='border: 3px;'>";


        content += "<tr align='center' style='height: 30px;' bgcolor='#add8e6'>" +
            "<td width='4%' align='center' style='background: white;'></td>" +
            "<td width='18%' align='center'>订单编号</td>" +
            "<td width='7%'>充值面额</td>" +
            "<td width='15%' align='center'>代金券</td>" +
            "<td width='17%'>下单县域</td>" +
            "<td width='18%'>下单网点</td>" +
            "<td width='8%'>当前网点参与量</td>" +
            "<td width='5%' align='center'>操作</td>" +
            "<td width='8%' align='center' style='background: white;'></td>" +
            "</tr>";


        // submitYourMoney和spanError不能修改，很多地方会依赖此字符串
        for(var i=0;i<selectRow.length;i++){
            content += "<tr align='center' style='height: 38px;' bgcolor='#f0f8ff'>" +
                            "<td width='4%' align='center' style='background: white;'></td>" +
                            "<td width='18%' align='center'>"+selectRow[i].orderCode+"</td>" +
                            "<td width='7%'>"+selectRow[i].chargeAmount+"元</td>" +
                            "<td width='15%' align='center'><span style='width:100px; height:30px;' id='submitDaiAmount"+selectRow[i].orderCode+"' ><font color='red'>还未选择中奖代金券...</font></span>" +
                                    "<span id='submitDaiJinJuanAmount"+selectRow[i].orderCode+"' style='display: none;'></span>" +
                                    "<span id='submitDaiJinJuanId"+selectRow[i].orderCode+"' style='display: none;'></span>" +
                                    "<span id='submitDaiJinJuanName"+selectRow[i].orderCode+"' style='display: none;'></span>" +
                                    "<span id='submitDaiJinJuanRange"+selectRow[i].orderCode+"' style='display: none;'></span>" +
                                    "<span id='submitDaiJinJuanRegion"+selectRow[i].orderCode+"' style='display: none;'></span>" +
                                    "<span id='submitDaiJinJuanUseRange"+selectRow[i].orderCode+"' style='display: none;'></span>" +
                                    "<span id='submitDaiJinJuanCount"+selectRow[i].orderCode+"' style='display: none;'></span></td>" +
                            "<td width='17%'>"+selectRow[i].areaName+"</td>" +
                            "<td width='18%'>"+selectRow[i].storeName+"</td>" +
                            "<td width='8%'>"+selectRow[i].count+"</td>" +
                            "<td width='5%' align='center'><a href='javascript:void(0);' onclick=cashCouponLotteryManager.selectDaiJinJuan('"+selectRow[i].orderCode +"','"+ selectRow[i].chargeAmount +"');>选择</a></td>" +
                            "<td width='8%' align='center' style='background: white;'><font color='red'><span id='spanDaiJinJuanError"+selectRow[i].orderCode+"'></span></font></td>" +
                        "</tr>";
        }

        content += "</table>";

        
        content += "<div id='div_btns' class='dialog-button' style='height: 100px;'>" +
                        "<table align='center' width='100%'>" +
                            "<tr >" +
                                "<td align='center'>" +
                                    "<font color='red'>提示：还有未设置中奖金额的订单\\中奖金额总计不可超过开奖订单量值的范围</font><br>" +
                                    "<a  class='btn'   onclick='javascript:$(\"#id_phone_lottery_setting_daiJinJuan\").dialog(\"close\")'><i class='i-close'></i>取消</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                                    "<a  class='btn btn-1' onclick='cashCouponLotteryManager.doLotteryDaiJinJuan()'><i class='i-ok'></i>确定</a>"
                                "</td>" +
                            "</tr>"
                        "</table>"+
                    "</div>";

        $('#id_phone_lottery_setting_daiJinJuan').dialog({
            closable:true,
            left:($(window).width()-1600)/2,
            top:50,
            width:1600,
            height:800,
            modal: true,

            title:"<table width='100%'>" +
                    "<tr >" +
                        "<td align='center'>代金券中奖金额设置</td>" +
                        "<td align='center' width='40%'></td>" +
                        "<td align='left' width='30%'><font color='red'>已选中奖金额总计：<span id='submitDaiJinJuanTotalMoney' style='width: 50px;'>0</span>元</font></td>" +
                    "</tr>" +
                  "</table>",

            buttons:'#div_btns',
            content:content,
            onClose:function(){
                $("#div_btns").remove();
                $('#add_sale_region_div').empty();
            }
        });



    },


    // 代金券开奖
    doLotteryDaiJinJuan : function () {

        var daiMoneyArray = $("span[id^='submitDaiAmount']");

        // 验证代金券不能为空
        for(var i=0;i<daiMoneyArray.length;i++){
            var errorSpanId = daiMoneyArray[i].id.substring('submitDaiAmount'.length);
            if(daiMoneyArray[i].innerText == "还未选择中奖代金券..."){
                $("#spanDaiJinJuanError" + errorSpanId).text("请选择代金券")
                return ;
            } else {
                $("#spanDaiJinJuanError" + errorSpanId).text("")
            }
        }

        // 验证开奖金额不能超过总记录数
        var totalMoney = $("#submitDaiJinJuanTotalMoney").text();
        var selectRows = $("#showNumbers").text();
        if(parseInt(totalMoney) > parseInt(selectRows)){
            alert("中奖金额总计不可超过开奖订单量值的范围");
            return ;
        }

        $.messager.confirm({
            width: 500,
            title: '修改提示',
            msg: '<div class="content">确定要把选中的订单设为已中奖吗？<br>开奖后所筛选的其他订单会转为未中奖状态。</div>',
            ok: '<i class="i-ok"></i> 确定',
            cancel: '<i class="i-close"></i> 取消',
            fn: function (r) {
                if (r) {
                    closeMyDialog("#id_phone_lottery_setting_daiJinJuan");

                    cashCouponLotteryManager.ajaxLoading();


                    var selectRow = $("#id_order_change_datagrid2").datagrid("getChecked");
                    var lotteryIds = "";
                    for(var i=0;i<selectRow.length;i++){
                        var lotteryMoney = $("#submitDaiJinJuanAmount" + selectRow[i].orderCode).text();
                        var cashCouponId = $("#submitDaiJinJuanId" + selectRow[i].orderCode).text();
                        var name = $("#submitDaiJinJuanName" + selectRow[i].orderCode).text();
                        var range = $("#submitDaiJinJuanRange" + selectRow[i].orderCode).text();
                        var region = $("#submitDaiJinJuanRegion" + selectRow[i].orderCode).text();
                        var useRange = $("#submitDaiJinJuanUseRange" + selectRow[i].orderCode).text();
                        var count = $("#submitDaiJinJuanCount" + selectRow[i].orderCode).text();

                        lotteryIds += selectRow[i].id + "@" + lotteryMoney + "@" + cashCouponId + "@"
                        + name + "@" + range + "@" + region + "@" + useRange + "@" + count + ",";
                    }
                    var rows = $("#id_order_change_datagrid2").datagrid("getRows");
                    var ids = '';
                    for(var i=0;i<rows.length;i++){
                        ids += rows[i].id + ",";
                    }


                    $.ajax({
                        url:'../../../../vas/activity/setCashCouponLottery.do',
                        type:'post',
                        data:{
                            'lotteryIds':lotteryIds,
                            'ids':ids
                        },
                        beforeSend: function () {

                        },

                        success:function(data){
                            if (data.status == 200) {
                                info("开奖成功");
                                cashCouponLotteryManager.ajaxLoadEnd();
                                freesearch();

                            } else {
                                alert(data.msg);
                                cashCouponLotteryManager.ajaxLoadEnd();
                            }
                        }
                    });

                } else {
                    return ;
                }
            }
        });

    },
    ajaxLoading :function (){
        $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
        $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 250) / 2,top:($(window).height() - 75) / 2});
    },

    ajaxLoadEnd :function (){
        $(".datagrid-mask").remove();
        $(".datagrid-mask-msg").remove();
    }

}