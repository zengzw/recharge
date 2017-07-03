/*
 * Copyright (c) 2013, FPX and/or its affiliates. All rights reserved.
 * Use, Copy is subject to authorized license.
 */
/**
 * <h3>瑞通供应商业务逻辑</h3>
 * 
 * <p> <b> 1,瑞通callback 注意：</b>
 *    <p>- 瑞通下单状态返回成功 或 失败，就不会有callback了。除了其他状态（处理中）才有callback通知。</p>
 *    <p>- 代码层面中，为了兼容性，需要模拟callback回调。</p>
 *  </p>
 *
 *<p>
 *  2, 供货价，供货价是人工对的。这个变更需要脚本。
 *</p>
 *
 *<p> 3,没有归属地查询接口，需要查询本地号码库</p>
 *
 *
 *<p> 4,订单查询接口有时间限制 </p>
 *
 *
 * @author zengzw
 * @date 2017年5月2日
 */
package com.tsh.phone.recharge.rtpay;











