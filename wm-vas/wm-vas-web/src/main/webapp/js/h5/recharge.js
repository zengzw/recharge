/* 销售价 */
var mRealMoney;
/* 面值 */
var mSaleMoney;
var isLimitNumber = false;
var mJoinStatus = true;
var isUseCoupon = false;
var selectCoupon;
var mGoodId;
var mPhoneNumber;
var hasValueFace = false;
var token;
var mSubBusinessCode;
var openId;
var TIME_OUT = 5000;
var loading_time_out = 100;

var recharge = {
    getQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    },
    init          : function () {
        token = Tools.getToken();
        openId = Tools.getOpenId();
        // token = "mbr_tk_43_17895_b297a2019d0243b6b0b545d4ce683116";
        recharge.initInput();
        recharge.getDefaultValueFace();
        recharge.getSwiperData();
        recharge.initJoinActivity();
        recharge.initCoupon();
        recharge.initButton();
        recharge.initBack();
        recharge.initHistoryView();
    },
    /* 返回按钮 */
    initBack      : function () {
        $('.go-back').on('click', function () {
            // window.location.href = Tools.getMainUrl();
            history.go(-1);
        })
    },
    /* 发起支付 */
    pay           : function () {
        if (!$('#btn-pay').hasClass('btn-active')) {
            return;
        }
        $('#btn-pay').removeClass('btn-active');
        setTimeout(function () {
            $('#btn-pay').addClass('btn-active');
        }, 5000);
        var params = {
            "businessCode"   : "MPCZ",
            "goodsId"        : mGoodId,
            "joinMoney"      : mJoinStatus ? 1 : 0,
            "joinStatus"     : mJoinStatus ? 1 : 0,
            "originalAmount" : mRealMoney,
            "realAmount"     : isUseCoupon ? ( parseFloat(mRealMoney) - parseFloat(selectCoupon.money) > 0 ? parseFloat(mRealMoney) - parseFloat(selectCoupon.money) : 0) : mRealMoney,
            "rechargePhone"  : mPhoneNumber,
            "saleAmount"     : mSaleMoney,
            "subBusinessCode": mSubBusinessCode,
            "openUserId"     : openId,
            "couponId"       : isUseCoupon ? selectCoupon.recordID : "",
        };
        $.ajax({
            type    : 'POST',
            url     : '/app/vas/phone/gx/pay',
            data    : {
                token    : token,
                params   : JSON.stringify(params),
                reqSource: 'b2c',
            },
            dataType: 'json',
            timeout : TIME_OUT,
            success : function (data) {
                if (data.status != 200) {
                    $.toast("充值出现问题了", "text");
                    return;
                }
                if (!Tools.isEmpty(data.data.weixPayUrl)) {
                    var a = {
                        "number"   : mPhoneNumber,
                        "valuaFace": mSaleMoney,
                        "orderCode": data.data.orderCode,
                    };
                    var url = encodeURI(data.data.weixPayUrl + "&orderType=11&token=" + token + "&callBackUrl=" + location.origin + "/views/h5/phone/pay_success.html?params=" + JSON.stringify(a));
                    console.log(data.data);
                    location.href = url;
                }
            },
            error   : function (xhr, type) {
                $.toast("充值出现问题了", "text");
            }

        });
    },

    getUsableCouponListHtml: function (list) {
        console.log("------usable coupon list:" + JSON.stringify(list));
        var htmlStr = "";
        $.each(list, function (index, item) {
            htmlStr += "<div class='coupon-item'>" +
                "<img class='coupon-item-img' src='/static/h5/res/coupon-bg.png'>" +
                "<div class='coupon-item-left'>" +
                "<p class='coupon-item-left-symbol'>¥</p>" +
                "<p class='coupon-money-class'>" + item.money + "</p>" +
                "<div class='coupon-item-left-text'>" +
                "<p class='coupon-item-text'>" + item.useLinmit + "</p>" +
                "<p class='coupon-item-text' style='color: #f23030;'>" + item.useScope + "</p>" +
                "<p class='coupon-item-text-small'>时间: " + item.useBeginTime.replace("-", ".").replace("-", ".") +
                "-" + item.useEndTime.replace("-", ".").replace("-", ".") + "</p>" +
                "</div>" +
                "</div>" +
                "<div class='coupon-item-right'>";

            if (selectCoupon.recordID == item.recordID) {
                htmlStr += "<p class='coupon-item-btn' >当<br/>前<br/>所<br/>选</p>";
            } else {
                htmlStr += "<p class='coupon-item-btn' style='background: #ff6363;color: white;' onclick='recharge.useCoupon(" + JSON.stringify(item) + ");'>选<br/>择<br/>使<br/>用</p>";
            }

            htmlStr += "</div></div>";

        });
        return htmlStr;
    },

    /* 选择优惠券 */
    useCoupon: function (item) {
        console.log("------》 select:" + JSON.stringify(item));
        selectCoupon = item;
        recharge.setPayValue();
        $.closePopup();
    },

    queryUsableCouponList: function () {
        $.showLoading();
        setTimeout(function () {
            recharge.showUsableCouponList();
        }, loading_time_out);
    },
    
    /* 查询可以使用的优惠券 */
    showUsableCouponList: function () {
        
        $("#couponListDiv").html("");

        $.ajax({
            type    : 'GET',
            url     : "/app/vas/phone/coupon/list",
            data    : {
                mobile: mPhoneNumber,
                price : mRealMoney,
                token : token,
            },
            dataType: 'json',
            async   : false,
            timeout : TIME_OUT,
            success : function (data) {
                $.hideLoading();
                console.log("usable couponList:" + JSON.stringify(data.data));
                if (data.status == 200) {
                    // 判断是否为空
                    if (!Tools.isEmpty(data.data)) {
                        $("#couponListDiv").append(recharge.getUsableCouponListHtml(data.data));
                    }
                } else {
                    $.toast("请求失败!", "text");
                }
            },
            error   : function (xhr, type) {
                console.log(xhr);
                console.log(type);

                $.hideLoading();
                $.toast("网络出状况了!", "text");
            }
        });
    },

    /* 获取优惠券列表 */
    getCouponList: function (phone, amount) {

        $.ajax({
            type    : 'GET',
            url     : "/app/vas/phone/coupon/list",
            data    : {
                mobile: phone,
                price : amount,
                token : token,
            },
            dataType: 'json',
            async   : false,
            timeout : TIME_OUT,
            success : function (data) {
                $.hideLoading();
                console.log("couponList:" + JSON.stringify(data.data));
                if (data.status == 200) {
                    // 判断是否为空
                    if (!Tools.isEmpty(data.data)) {

                        // 展示优惠券div
                        $("#youhui-div").show();
                        isUseCoupon = true;
                        selectCoupon = null;

                        //取到期时间最近的一张优惠券
                        for (var i = 0; i < data.data.length; i++) {
                            if (Tools.isEmpty(selectCoupon)) {
                                selectCoupon = data.data[i];
                            } else {
                                if (selectCoupon.useEndTime > data.data[i].useEndTime) {
                                    selectCoupon = data.data[i];
                                } else if (selectCoupon.useEndTime == data.data[i].useEndTime) {
                                    if (parseFloat(selectCoupon.money) < parseFloat(data.data[i].money)) {
                                        selectCoupon = data.data[i];
                                    }
                                }
                            }
                        }
                        console.log("selectCoupon:" + JSON.stringify(selectCoupon));
                    } else {
                        isUseCoupon = false;
                        selectCoupon = null;
                    }
                } else {
                    $.toast("请求失败!", "text");
                }
            },
            error   : function (xhr, type) {
                console.log(xhr);
                console.log(type);

                $.hideLoading();
                $.toast("网络出状况了!", "text");
            }
        });
    },

    /**
     * 设置支付金额
     */
    setPayValue: function () {
        // 判断是否有优惠券使用
        if (isUseCoupon) {
            $("#youhui-div").show();
            var payAmount = mRealMoney - selectCoupon.money;
            if (payAmount < 0) {
                payAmount = 0;
            }
            if (mJoinStatus) {
                payAmount = parseFloat(payAmount) + 1;
            }
            $("#btn-pay").text("立即支付(" + Tools.returnFloat(payAmount) + "元)");
            $("#btn-coupon-join").text("优惠券减 " + Tools.returnFloat(selectCoupon.money) + "元 >")
        } else {
            if (Tools.isEmpty(selectCoupon)) {
                $("#youhui-div").hide();
            }
            if(!Tools.isEmpty(mRealMoney)){
                var payAmount = mRealMoney;
                if (mJoinStatus) {
                    payAmount = parseFloat(payAmount) + 1;
                }
                if (payAmount < 0) {
                    payAmount = 0;
                }
                $("#btn-pay").text("立即支付(" + Tools.returnFloat(payAmount) + "元)");
            }

        }
    },

    /* 获取充值面额 */
    getValueFace       : function (phone) {
        hasValueFace = false;
        $.showLoading();
        $.ajax({
            type    : 'GET',
            url     : "/app/vas/phone/value/" + phone,
            data    : {
                token: token,
            },
            dataType: 'json',
            timeout : TIME_OUT,
            success : function (data) {
                $.hideLoading();
                $("#grid_view").html('');
                if (Tools.isEmpty(data.data) || Tools.isEmpty(data.data.lstPhoneValue)) {
                    recharge.emptyView();
                    return;
                }
                $.each(data.data.lstPhoneValue, function (index, item) {
                    $("#grid_view").append('<div class="item-value-face" align="center" id = "' + item.goodId + '">' +
                        '            <p class="text-value-face" style="font-size: 0.5rem">' + item.value + '元</p>' +
                        '            <p class="text-value-face">售价:' + item.sellPrice + '元</p>' +
                        '        </div>');

                });

                recharge.initValueFace();

                $("#area").text(data.data.locationVo.provinceName + "-" + data.data.locationVo.type);
                mPhoneNumber = phone;
                mSubBusinessCode = data.data.locationVo.subBusinessCode;
            },
            error   : function (xhr, type) {
                console.log(xhr);
                console.log(type);

                mPhoneNumber = '';
                $.hideLoading();
                $.toast("网络出状况了!", "text");
                $("#grid_view").html('');
                recharge.emptyView();
            }
        });
    },
    /* 没有面额空视图 */
    emptyView          : function () {
        $("#grid_view").append('<div style=" height: 2.8rem; \n' +
            'width: 100%; \n' +
            'display: flex; \n' +
            'flex-direction: column; \n' +
            'justify-content: center; \n' +
            'align-items: center; \n' +
            'align-content: center; \n' +
            'border: 1px solid #999999;" ><p style="font-size: 0.36rem">对不起，当前手机号所属地区</p><p style="font-size: 0.36rem">还没有可支持的充值面额</p></div>');
    },
    /* 检验号码 */
    checkNumber        : function (phone) {
        if (!(/^1[34578]\d{9}$/.test(phone))) {
            $.toast("手机号码有误，请重填!", "text");
            isLimitNumber = false;

        } else {
            isLimitNumber = true;
        }
        return isLimitNumber;
    },
    /* 面额的选择事件 */
    initValueFace      : function () {
        var lastView = [];
        var lastBg;
        $(".item-value-face").on("click", function () {
            if (!isLimitNumber) {
                $.toast("请输入正确的手机号码!", "text");
                return;
            }

            if (this != lastBg) {
                var arr = [];
                arr = this.querySelectorAll("p");
                for (var i = 0; i < lastView.length; i++) {
                    $(lastView[i]).removeClass("text-value-face-active");
                    $(lastBg).removeClass("item-value-face-active");
                }
                for (var i = 0; i < arr.length; i++) {
                    $(arr[i]).addClass("text-value-face-active");
                    $(this).addClass("item-value-face-active");
                }
                mSaleMoney = arr[0].innerText.substring(0, arr[0].innerText.length - 1);
                mRealMoney = arr[1].innerText.substring(3, arr[1].innerText.length - 1);
                lastView = arr;

                $.showLoading();
                setTimeout(function () {
                    recharge.getCouponList(mPhoneNumber, mRealMoney);
                    recharge.setPayValue();
                }, loading_time_out);

            }
            lastBg = this;
            mGoodId = $(this).attr('id');
            hasValueFace = true;
            recharge.checkPay();

        });
    },
    /* 输入框的事件 */
    initInput          : function () {
        $('#area').hide();
        $("#input_clear").on('click', function () {
            isLimitNumber = false;
            $("#area").text('');
            $("#input_number").val('');
            $("#input_number").focus();
            $(this).hide();
            recharge.checkPay();
        });

        $("#input_number")
            .on('blur', function () {
                setTimeout(function () {
                    $("#input_clear").hide();
                }, 300);
                if (!Tools.isEmpty($("#area").text())) {
                    $("#area").show();
                }
            })
            .on('input', function () {
                if ($(this).val().length > 11) {
                    $(this).val($(this).val().substring(0, 11));
                } else if ($(this).val().length == 11) {
                    if (recharge.checkNumber($(this).val())) {
                        recharge.getValueFace($(this).val());
                    } else {
                        isLimitNumber = false;
                    }
                } else {
                    isLimitNumber = false;
                }
                ;
                recharge.checkPay();
                if ($(this).val().length) $("#input_clear").show();
                $('#area').hide();
            }).focus(function () {
            if ($(this).val().length > 0) {
                $("#input_clear").show();
            }
            $('#area').hide();
        });
    },
    /* 支付按钮设置 */
    initButton         : function () {
        $('#btn-pay').on('click', function (e) {
            if (mRealMoney != null && isLimitNumber) {
                recharge.pay();
            }
        });

    },
    /* 获取默认面额 */
    getDefaultValueFace: function () {
        $.showLoading();
        $.ajax({
            type    : 'GET',
            url     : "/app/vas/phone/value",
            data    : {
                token: token,
            },
            dataType: 'json',
            timeout : TIME_OUT,
            success : function (data) {
                $.hideLoading();
                $("#grid_view").html('');
                if (Tools.isEmpty(data.data)) {
                    recharge.emptyView();
                    return;
                }
                $.each(data.data, function (index, item) {
                    $("#grid_view").append(' <div class="item-value-face" align="center">' +
                        '            <p class="text-value-face" style="font-size: 0.5rem">' + item.value + '元</p>' +
                        '        </div>');

                });

                recharge.initValueFace();
            },
            error   : function (xhr, type) {
                $.hideLoading();
                recharge.emptyView();
                $.toast("网络出状况了!", "text");
            }
        })
    },
    /* 获取中奖轮播 */
    getSwiperData      : function () {
        $.ajax({
            type    : 'GET',
            url     : "/app/vas/phone/order/activity/list",
            data    : {
                params: "{\"searchInfo\":\"\",\"page\":1,\"type\":\"-1\",\"rows\":20}",
                token : token,
            },
            dataType: 'json',
            timeout : TIME_OUT,
            success : function (data) {
                $(".swiper-wrapper").html('');
                if (Tools.isEmpty(data.data)) {
                    $(".swiper-container").hide();
                    return;
                }
                $.each(data.data, function (index, item) {
                    var str;
                    if (Tools.isEmpty(item.couponName)) {
                        str = " <p class='swiper-slide' style='line-height: 1.1rem;height: 1.1rem;background-color: #fff3e6 ;padding-left: 0.5rem ;' >恭喜" + item.chargeMobile + "参与活动获得" + item.lotteryTypeName + item.winningAmount + "元</p>";
                    } else {
                        str = " <p class='swiper-slide' style='line-height: 1.1rem;height: 1.1rem;background-color: #fff3e6 ;padding-left: 0.5rem ;' >恭喜" + item.chargeMobile + "参与活动获得" + item.couponName + "</p>";
                    }
                    $(".swiper-wrapper").append(str);
                });
                var mySwiper = new Swiper('.swiper-container', {
                    autoplay                    : 5000,//可选选项，自动滑动
                    direction                   : 'vertical',
                    // autoHeight: true,
                    autoplayDisableOnInteraction: false,
                    loop                        : true,
                });
            },
            error   : function (xhr, type) {
                $(".swiper-container").hide();
            }
        })
    },
    /* 准备活动按钮 默认参加 */
    initJoinActivity   : function () {
        $('#btn-join').on('click', function () {
            $(this).addClass('btn-activity-join');
            $('#btn-not-join').removeClass('btn-activity-join');
            mJoinStatus = true;
            recharge.setPayValue();
        });
        $('#btn-not-join').on('click', function () {
            $(this).addClass('btn-activity-join');
            $('#btn-join').removeClass('btn-activity-join');
            mJoinStatus = false;
            recharge.setPayValue();
        });
    },
    /* 优惠券按钮事件初始化 */
    initCoupon         : function () {
        $('#btn-coupon-join').on('click', function () {
            $(this).addClass('btn-activity-join');
            $('#btn-coupon-not-join').removeClass('btn-activity-join');
            isUseCoupon = true;
            recharge.setPayValue();
        });
        $('#btn-coupon-not-join').on('click', function () {
            $(this).addClass('btn-activity-join');
            $('#btn-coupon-join').removeClass('btn-activity-join');
            isUseCoupon = false;
            recharge.setPayValue();
        });
    },

    /* 控制支付按钮 */
    checkPay       : function () {
        if (isLimitNumber && hasValueFace) {
            $('#btn-pay').addClass('btn-active');
        } else {
            $('#btn-pay').removeClass('btn-active');
        }
    },
    /* 控制活动显示隐藏 */
    initActivity   : function () {
        $('.swiper-container').hide();
        $('#activity').hide();
        mJoinStatus = false;
    },
    /* 历史记录视图 */
    initHistoryView: function () {
        $('#btn-history').on('click', function () {
            $('#history-view').popup();
        })
        $('.history-cancel').on('click', function () {
            $.closePopup();
        })
        $('#history-list').html('');
        var tempList = localStorage.getItem("PHONE_HISTORY_LIST");
        if (!Tools.isEmpty(tempList)) {
            $.each(tempList.split(','), function (index, item) {
                $('#history-list').append('<p class="history-item">' + item + '</p>')
            })
        } else {
            $('#btn-history').hide();
        }
        $(".history-item").on('click', function () {
            $("#input_number").val($(this).text());
            isLimitNumber = true;
            recharge.getValueFace( $("#input_number").val());
            $.closePopup();
        })


    }
};
$(function () {
    recharge.init();
    // recharge.initActivity();
    if (!Tools.isEmpty($("#input_number").val())) {
        window.location.reload();
    }
});