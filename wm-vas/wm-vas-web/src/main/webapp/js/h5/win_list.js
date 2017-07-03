/**
 * Created by Hlc on 2017/5/27.
 */
var mPage = 1;
var mNumber = "";
var token;

var winList = {
    init: function () {
        token = Tools.getToken();
        // token = "mbr_tk_43_17895_49c4304313c448de8f169221594bb023";
        winList.getWinList(null, "", mPage);
        winList.initPullToRefresh();
        winList.initButton();
        winList.initInput();
    },
    /* 获取中奖列表 */
    getWinList: function (me, number, page) {
        $.ajax({
            type: 'GET',
            url: "/app/vas/phone/order/activity/list",
            data: {
                params: '{"searchInfo":"' + number + '","page":' + page + ',"type":"-1","rows":15}',
                token: token
            },
            dataType: 'json',
            timeout: 2000,
            success: function (data) {
                $.hideLoading();
                $("#list").html('');
                if (Tools.isEmpty(data.data) && page == 1) {
                    if (Tools.isEmpty(mNumber)) {
                        winList.showNoData("当前还没有手机号中奖");
                    }else {
                        winList.showNoData("当前手机号还没有中奖记录哦");
                    }


                    $('.dropload-down').hide();
                    if (me != null) {
                        me.resetload();
                        me.unlock();
                    }
                    return;
                }
                $('.dropload-down').show();
                $.each(data.data, function (index, item) {
                    winList.fillData(item);
                });
                if (me != null) {
                    me.resetload();
                    me.unlock();
                }
            },
            error: function (xhr, type) {
                $.toast("网络出状况了!", "text");
                me.resetload();
            }
        })
    },
    /* 下拉刷新 上拉加载 */
    initPullToRefresh: function () {
        $('#win-list').dropload({
            autoLoad: false,
            scrollArea: window,
            domUp: {
                domClass: 'dropload-up',
                domRefresh: '<div class="dropload-refresh" style="font-size: 0.36rem">↓下拉刷新</div>',
                domUpdate: '<div class="dropload-update" style="font-size: 0.36rem">↑释放更新</div>',
                domLoad: '<div class="dropload-load" style="font-size: 0.36rem"><span class="loading"></span>加载中...</div>'
            },
            domDown: {
                domClass: 'dropload-down',
                domRefresh: '<div class="dropload-refresh" style="font-size: 0.36rem">↑上拉加载更多</div>',
                domLoad: '<div class="dropload-load" style="font-size: 0.36rem"><span class="loading"></span>加载中</div>',
                domNoData: '<div class="dropload-noData" style="font-size: 0.36rem">暂无数据</div>'
            },
            threshold: 50,
            loadUpFn: function (me) {
                $('#input_number').val('');
                mNumber = "";
                mPage = 1;
                winList.getWinList(me, "", mPage);
            },
            loadDownFn: function (me) {
                mPage++;
                // 拼接HTML
                $.ajax({
                    type: 'GET',
                    url: "/app/vas/phone/order/activity/list",
                    data: {
                        params: '{"searchInfo":"' + mNumber + '","page":' + mPage + ',"type":"-1","rows":15}',
                        token: token,
                    },
                    dataType: 'json',
                    timeout: 2000,
                    success: function (data) {
                        if (Tools.isEmpty(data.data)) {
                            me.noData(true);
                        }
                        $.each(data.data, function (index, item) {
                            winList.fillData(item);
                        });
                        if (data.data.length < 15) {
                            me.noData(true);
                        }
                        me.unlock();
                        me.resetload();
                    },
                    error: function (xhr, type) {
                        me.resetload();
                    }
                });
            },
        });
    },

    /* 填充数据 */
    fillData: function (item) {
        $("#list").append('<div class="win-item">' +
            '<p class="win-item-text">充值中奖手机号：<span style="color: #FF4400;">' + item.chargeMobile + '</span>（幸运号：' + item.luckyNumber + '）</p>' +
            ('<p class="win-item-text">充值订单号：' + item.orderCode + '</p>') +
            '<p class="win-item-text">充值金额：' + item.chargeAmount + '元</p>' +
            '<p class="win-item-text">获得' + item.lotteryTypeName + '：' + item.winningAmount + '元 ' + (Tools.isEmpty(item.useDateRange) ? "" : " (使用时间：" + item.useDateRange + ")") + '</p>' +
            (Tools.isEmpty(item.couponName) ? '' : '<p class="win-item-text">奖品名称：' + item.couponName + '</p>') +
            '<p class="win-item-text">中奖日期：' + item.lotteryTimeStr + '</p>' +
            '</div>')
    },

    /* 搜索按钮 */
    initButton: function () {
        $('.search-btn').on('click', function () {
            mNumber = $('#input_number').val();
            if (!Tools.checkNumber(mNumber)) {
                $.toast("手机号码有误，请重填!", "text");
                mNumber = "";
                return;
            }
            mPage = 1;
            winList.getWinList(null, mNumber, mPage);
        });
    },
    /* 输入框 */
    initInput: function () {

        $("#input_clear").on('click', function () {
            $("#input_number").val('');
            $("#input_number").focus();
            $(this).hide();
        });

        $("#input_number")
            .on('blur', function () {
                setTimeout(function () {
                    $("#input_clear").hide();
                }, 300);
            })
            .on('input', function () {
                if ($(this).val().length > 11) {
                    $(this).val($(this).val().substring(0, 11));
                }
                if ($(this).val().length) $("#input_clear").show();
            }).focus(function () {
            if ($(this).val().length > 0) {
                $("#input_clear").show();
            }
            $('#area').hide();
        });
    },
    /* 空视图 */
    showNoData: function (str) {
        $("#list").html('<div align="center">' +
            '<img style="width: 3.8rem;height: 3.3rem;" src="/static/h5/res/order_empty.png">' +
            '<p>'+str+'</p></div>');
    //    当前手机号还没有中奖记录哦
    },
};
$(function () {
    winList.init();
});