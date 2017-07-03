var mPage = 1;
var initLoadMore ;


var orderListManager = {

    // 查询全部
    queryAll: function () {
        var mobile = $("#inputMobile").val();
        $("#typeId").val("0");
        mPage = 1;
        orderListManager.doQuery(mobile, "0", mPage);
    },

    // 查询处理中
    queryWait: function () {
        var mobile = $("#inputMobile").val();
        $("#typeId").val("1");
        mPage = 1;
        orderListManager.doQuery(mobile, "1", mPage);
    },

    // 查询成功
    querySuccess: function () {
        var mobile = $("#inputMobile").val();
        $("#typeId").val("2");
        mPage = 1;
        orderListManager.doQuery(mobile, "2", mPage);
    },

    // 查询失败
    queryFail: function () {
        var mobile = $("#inputMobile").val();
        $("#typeId").val("3");
        mPage = 1;
        orderListManager.doQuery(mobile, "3", mPage);
    },

    // 动态生成订单状态的颜色
    getColor: function (payStatus) {

        if (payStatus == 1) {
            return "color='#ff6363'";

        } else if (payStatus == 2) {
            return "color='#ff6363'";

        } else if (payStatus == 3) {
            return "color='#ff6363'";

        } else if (payStatus == 4) {
            return "color='#ff6363'";

        } else if (payStatus == 5) {
            return "color='green'";

        } else if (payStatus == 6) {
            return "color='gray'";

        } else if (payStatus == 7) {
            return "color='gray'";

        } else {
            return "color='gray'";

        }
        return "color='gray'";
    },

    jumb2OrderDetail: function (orderCode, token, openId) {
        window.location.href = "order_detail.html?orderCode=" + orderCode + "&token=" + token + "&openId=" + openId;
    },

    jump2MyOrderPage: function () {
        window.location.href = Tools.getMyOrderPageUrl();
    },

    // 获取无结果页面内容
    getDataHtml: function (list) {
        var dataStr = "";
        $.each(list, function (index, item) {
            dataStr +=
                "<div style='background: #eeeeee;height: 10px;width:100%;'></div>" +
                "<div onclick=orderListManager.jumb2OrderDetail('" + item.phoneOrderCode + "','" + orderListManager.getQueryString("token") + "','" + orderListManager.getQueryString("openId") + "')>" +
                "<div class='one'>" +
                "<div class='one-one'>" +
                "<p class='icon-dingdan phone_middle'/>" +
                "<p class='item-text' style='padding-left: 0.2rem;'>" + item.phoneOrderCode + "</p>" +
                "</div>" +
                "<p class='item-text' style='padding-right: 0.2rem;'><font " + orderListManager.getColor(item.payStatus) + ">" + item.payStatusStr + "</font></p>" +
                "</div>" +
                "<div class='two'>" +
                "<div class='two-one'>" +
                "<div class='two-two'>" +
                "<img src='/static/h5/res/phone.png' class='phone'/>" +
                "<p class='item-text-one'>话费充值</p>" +
                "</div>" +
                "<div class='two-three'>" +
                "<p class='item-text'>充值手机: <font color='#ff6363'>" + item.rechargePhone + "</font></p>" +
                "<p class='item-text'>充值面额: " + item.saleAmount + "元</p>" +
                "</div>" +
                "</div>" +
                "<img src='/static/h5/res/arrow.png' style='width:0.2rem;'/>" +

                "</div>" +
                "<div class='four'>" +
                "<p class='item-text' style='padding-right: 0.2rem;'>支付金额: <font color='ff6363'>￥" + item.realAmount + "</font></p>" +
                "</div>" +
                "</div>";

        });

        return dataStr;
    },

    // 动态获取数据的html
    getEmptyHtml: function (token, openId) {
        var emptyHtml = "<div style='background: #eeeeee;align-content: center'>" +
            "<div style='height: 1.6rem;'></div>" +
            "<center>" +
            "<img class='weui-media-box__thumb' src='/static/h5/res/order_empty.png' style='width:4rem;height: 3rem;text-align: center'>" +
            "<div style='height: 0.6rem;'></div>" +
            "<div><font color='#999999'' style='font-size: 12px;'>暂时还没有相关的订单...</font></div>" +
            "<div style='height: 1rem;'></div>" +
            "<a href='recharge.html?token=" + token + "&openId=" + openId + "' class='btn-gogo'>去逛逛</a>" +
            "</center>" +
            "<div style='height: 9rem;'></div>" +
            "</div>";
        return emptyHtml;
    },

    getQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    },

    // 查询后台
    doQuery: function (mobile, type, page) {
        $('.dropload-down').show();
        initLoadMore.noData(false);
        initLoadMore.lock();
        initLoadMore.resetload();

        if(mobile != null && mobile != '' && !orderListManager.checkNumber(mobile)){
            $('.dropload-down').hide();
            $("#inputMobile").val('');
            return ;
        }

        var token = orderListManager.getQueryString("token");
        var openId = orderListManager.getQueryString("openId");

        // token = 'auc_jsession_8a5624504f1105c3c30515d9df13fa72275ca1595c32a678e378a5850eedde17f6097ee84caf8308';
        // openId = '111111111111';

        if(token == null || token == '' || token == 'null' ||
            openId == '' || openId == null || openId == 'null'){
            $('.dropload-down').hide();
            $.toast("请重新登录", "text");
            return ;
        }



        $.ajax({
            type: 'GET',
            url: "/app/vas/phone/gx/myOrderList",
            async: false,
            data: {
                token: token,
                openId: openId,
                mobile: mobile,
                type: type,
                page: page
            },
            dataType: 'json',
            timeout: 3000,
            success: function (data) {
                if (page == 1) {
                    $("#dataPage").html('');
                }
                if (data.status == 200) {
                    $("#currentPageId").val(data.code);
                    var dataStr = "";
                    if (page == 1) {
                        if (data.data == null || data.data == '' || data.data.length == 0) {
                            $("#dataPage").html('');
                            $("#dataPage").append(orderListManager.getEmptyHtml(token, openId));
                            $('.dropload-down').hide();
                        }
                    }
                    dataStr = orderListManager.getDataHtml(data.data);
                    $("#dataPage").append(dataStr);
                }
                if (data.data == null || data.data == '' || data.data.length < 5) {
                    $('.dropload-down').hide();
                } else {
                    $('.dropload-down').show();
                }

                initLoadMore.unlock();
                initLoadMore.resetload();

            },
            error: function (xhr, type) {
                $("#dataPage").html('');
                $.toast("网络出状况了!", "text");
            }

        })
    },

    // 初始化下拉事件
    initPullToRefresh: function () {
        $('#pull-data-page').dropload({
            autoLoad: false,
            scrollArea: window,
            domUp: {
                domClass: 'dropload-up',
                domRefresh: '<div class="dropload-refresh">↓下拉刷新</div>',
                domUpdate: '<div class="dropload-update">↑释放更新</div>',
                domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
            },
            threshold: 50,
            loadUpFn: function (me) {

                $('.dropload-down').show();
                var mobile = $("#inputMobile").val();

                var typeId = $("#typeId").val();
                if (typeId == null || typeId == '') {
                    typeId = "0";
                }
                orderListManager.doQuery(mobile, typeId, 1);
                if (me != null) {
                    me.resetload();
                    me.unlock();
                }
            },

        });
    },


    checkNumber: function (phone) {
        if (!(/^1[34578]\d{9}$/.test(phone))) {
            $.toast("手机号码有误，请重填!", "text");
            return false;

        } else {
            return true;
        }
        return false;
    },
};



$(function () {
    initLoadMore =
        $('#pull-data-page').dropload({
            autoLoad: false,
            scrollArea: window,
            domDown: {
                domClass: 'dropload-down',
                domRefresh: '<div class="dropload-refresh">↑上拉加载更多</div>',
                domLoad: '<div class="dropload-load"><span class="loading"></span>加载中</div>',
                domNoData: '<div class="dropload-noData">暂无数据</div>'
            },
            loadDownFn: function (me) {
                mPage ++ ;
                var mobile = $("#inputMobile").val();
                orderListManager.doQuery(mobile, $("#typeId").val(), mPage);
            },
            threshold: 50,
            domUp: {
                domClass: 'dropload-up',
                domRefresh: '<div class="dropload-refresh">↓下拉刷新</div>',
                domUpdate: '<div class="dropload-update">↑释放更新</div>',
                domLoad: '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
            },
            loadUpFn: function (me) {

                $('.dropload-down').show();
                var mobile = $("#inputMobile").val();

                var typeId = $("#typeId").val();
                if (typeId == null || typeId == '') {
                    typeId = "0";
                }
                mPage = 1;
                orderListManager.doQuery(mobile, typeId, mPage);
                if (me != null) {
                    me.resetload();
                    me.unlock();
                }
            },
        });


    $('#queryOrderButton').on('click', function () {
        var mobile = $("#inputMobile").val();

        var typeId = $("#typeId").val();
        if (typeId == null || typeId == '') {
            typeId = "0";
        }
        mPage = 1;
        orderListManager.doQuery(mobile, typeId, mPage);

    });


    orderListManager.doQuery("", "0", mPage);

    // orderListManager.initPullToRefresh();

});

