package com.tsh.vas.phone.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.commons.utility.DateUtil;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.security.UserInfo;
import com.github.ltsopensource.core.json.JSON;
import com.google.common.collect.Maps;
import com.tsh.dubbo.bis.vo.ShopVO;
import com.tsh.dubbo.hlq.api.CouponApi;
import com.tsh.dubbo.hlq.enumType.CouponReceiveTypeEnum;
import com.tsh.dubbo.hlq.enumType.SysTypeEnum;
import com.tsh.dubbo.hlq.enumType.UseSceneTypeEnum;
import com.tsh.dubbo.hlq.vo.coupon.GiveCouponDTO;
import com.tsh.dubbo.hlq.vo.coupon.QueryCouponParamVO;
import com.tsh.dubbo.hlq.vo.ex.CouponVO;
import com.tsh.vas.commoms.constants.FundURLConstants;
import com.tsh.vas.commoms.exception.BusinessRuntimeException;
import com.tsh.vas.dao.phone.PhoneOrderInfoDao;
import com.tsh.vas.dao.phone.VasPhoneLotteryRecordDao;
import com.tsh.vas.dao.phone.VasPhoneOneyuanFreeDao;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.model.phone.PhoneOrderInfoPo;
import com.tsh.vas.model.phone.VasPhoneLotteryRecordPo;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreePo;
import com.tsh.vas.model.phone.VasPhoneOneyuanFreeStatisPo;
import com.tsh.vas.phone.commons.PhoneLotteryStatus;
import com.tsh.vas.phone.constants.SMSPhoneMessageTemplateConstants;
import com.tsh.vas.phone.enums.EnumLotteryType;
import com.tsh.vas.service.ShopApiService;
import com.tsh.vas.service.SmsService;
import com.tsh.vas.trainticket.commoms.HttpUtils;
import com.tsh.vas.trainticket.vo.MemberResultVo;
import com.tsh.vas.vo.charge.MemberVo;
import com.tsh.vas.vo.phone.CashCouponVo;
import com.tsh.vas.vo.phone.QueryActivityStatisticsVo;
import com.tsh.vas.vo.phone.VasPhoneOneyuanFreeVo;


@Service
@SuppressWarnings("all")
public class VasPhoneOneyuanFreeService {

    @Autowired
    private VasPhoneOneyuanFreeDao vasPhoneOneyuanFreeDao;

    @Autowired
    private PhoneOrderInfoDao phoneOrderInfoDao;

    @Autowired
    SmsService smsService;

    @Autowired
    private VasPhoneLotteryRecordDao vasPhoneLotteryRecordDao;

    @Autowired
    private CouponApi couponApi;
    
    @Autowired
    private ShopApiService shopService;


    @Autowired
    private PhoneLotteryRecordService lotteryRecordService;

    private final  static Logger logger = LoggerFactory.getLogger(VasPhoneOneyuanFreeService.class);


    /**
     * 新增
     * @param result
     * @param vasPhoneOneyuanFree
     * @return
     */
    public Result addVasPhoneOneyuanFree(Result result,VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo){
        VasPhoneOneyuanFreePo vasPhoneOneyuanFreePo = new VasPhoneOneyuanFreePo();

        if (vasPhoneOneyuanFreeVo != null) {

            copyProperties(vasPhoneOneyuanFreeVo, vasPhoneOneyuanFreePo);
        }

        result = vasPhoneOneyuanFreeDao.addVasPhoneOneyuanFree(result,vasPhoneOneyuanFreePo);
        return result;
    }



    /**
     * @param vasPhoneOneyuanFreeVo
     * @param vasPhoneOneyuanFreePo
     */
    private void copyProperties(VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo,
            VasPhoneOneyuanFreePo vasPhoneOneyuanFreePo) {
        if(vasPhoneOneyuanFreeVo.getOrderCode()!=null){
            vasPhoneOneyuanFreePo.setOrderCode(vasPhoneOneyuanFreeVo.getOrderCode());
        }
        if(vasPhoneOneyuanFreeVo.getActivityAmount()!=null){
            vasPhoneOneyuanFreePo.setActivityAmount(vasPhoneOneyuanFreeVo.getActivityAmount());
        }
        if(vasPhoneOneyuanFreeVo.getCreateTime()!=null){
            vasPhoneOneyuanFreePo.setCreateTime(vasPhoneOneyuanFreeVo.getCreateTime());
        }
        if(vasPhoneOneyuanFreeVo.getCheckStatus()!=null){
            vasPhoneOneyuanFreePo.setCheckStatus(vasPhoneOneyuanFreeVo.getCheckStatus());
        }
        if(vasPhoneOneyuanFreeVo.getCheckTime()!=null){
            vasPhoneOneyuanFreePo.setCheckTime(vasPhoneOneyuanFreeVo.getCheckTime());
        }
        if(vasPhoneOneyuanFreeVo.getLotteryStatus()!=null){
            vasPhoneOneyuanFreePo.setLotteryStatus(vasPhoneOneyuanFreeVo.getLotteryStatus());
        }
        if(vasPhoneOneyuanFreeVo.getLotteryTime()!=null){
            vasPhoneOneyuanFreePo.setLotteryTime(vasPhoneOneyuanFreeVo.getLotteryTime());
        }
        if(vasPhoneOneyuanFreeVo.getLotteryman()!=null){
            vasPhoneOneyuanFreePo.setLotteryman(vasPhoneOneyuanFreeVo.getLotteryman());
        }
        if(vasPhoneOneyuanFreeVo.getLuckyNumber()!=null){
            vasPhoneOneyuanFreePo.setLuckyNumber(vasPhoneOneyuanFreeVo.getLuckyNumber());
        }
        if(vasPhoneOneyuanFreeVo.getBizType()!=null){
            vasPhoneOneyuanFreePo.setBizType(vasPhoneOneyuanFreeVo.getBizType());
        }
        if(vasPhoneOneyuanFreeVo.getAreaId()!=null){
            vasPhoneOneyuanFreePo.setAreaId(vasPhoneOneyuanFreeVo.getAreaId());
        }
        if(vasPhoneOneyuanFreeVo.getAreaName()!=null){
            vasPhoneOneyuanFreePo.setAreaName(vasPhoneOneyuanFreeVo.getAreaName());
        }
        if(vasPhoneOneyuanFreeVo.getStoreId()!=null){
            vasPhoneOneyuanFreePo.setStoreId(vasPhoneOneyuanFreeVo.getStoreId());
        }
        if(vasPhoneOneyuanFreeVo.getStoreName()!=null){
            vasPhoneOneyuanFreePo.setStoreName(vasPhoneOneyuanFreeVo.getStoreName());
        }
        if(vasPhoneOneyuanFreeVo.getChargeMobile() != null){
            vasPhoneOneyuanFreePo.setChargeMobile(vasPhoneOneyuanFreeVo.getChargeMobile());
        }

        if(vasPhoneOneyuanFreeVo.getChargeAmount() != null){
            vasPhoneOneyuanFreePo.setChargeAmount(vasPhoneOneyuanFreeVo.getChargeAmount());
        }
    }



    /**
     * 保存  带User对象
     * @param result
     * @return
     */
    public Result saveVasPhoneOneyuanFree(Result result,VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo,UserInfo user) throws Exception {
        if(vasPhoneOneyuanFreeVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Integer id = vasPhoneOneyuanFreeVo.getId();
        result = vasPhoneOneyuanFreeDao.getVasPhoneOneyuanFreeById(result,id);
        VasPhoneOneyuanFreePo vasPhoneOneyuanFreePo  = (VasPhoneOneyuanFreePo)result.getData();

        if (vasPhoneOneyuanFreePo != null) {
            copyProperties(vasPhoneOneyuanFreeVo, vasPhoneOneyuanFreePo);
        }else{
            vasPhoneOneyuanFreePo = new VasPhoneOneyuanFreePo();
            copyProperties(vasPhoneOneyuanFreeVo, vasPhoneOneyuanFreePo);
            result = vasPhoneOneyuanFreeDao.addVasPhoneOneyuanFree(result,vasPhoneOneyuanFreePo);
        }
        return result;
    }



    /**
     * 保存 
     * @param result
     * @return
     */
    public Result saveVasPhoneOneyuanFree(Result result,VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo) throws Exception {
        if(vasPhoneOneyuanFreeVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Integer id = vasPhoneOneyuanFreeVo.getId();
        result = vasPhoneOneyuanFreeDao.getVasPhoneOneyuanFreeById(result,id);
        VasPhoneOneyuanFreePo vasPhoneOneyuanFreePo  = (VasPhoneOneyuanFreePo)result.getData();

        if (vasPhoneOneyuanFreePo != null) {
            copyProperties(vasPhoneOneyuanFreeVo, vasPhoneOneyuanFreePo);
        }else{
            vasPhoneOneyuanFreePo = new VasPhoneOneyuanFreePo();
            copyProperties(vasPhoneOneyuanFreeVo, vasPhoneOneyuanFreePo);
            result = vasPhoneOneyuanFreeDao.addVasPhoneOneyuanFree(result,vasPhoneOneyuanFreePo);
        }
        return result;
    }


    /**
     * 批量新增
     * @param result
     * @param vasPhoneOneyuanFree
     * @return
     */
    public Result batchSaveVasPhoneOneyuanFree(Result result, List<VasPhoneOneyuanFreeVo> vasPhoneOneyuanFree_list) throws Exception {
        List<VasPhoneOneyuanFreePo> list = new ArrayList<VasPhoneOneyuanFreePo>();
        result = vasPhoneOneyuanFreeDao.batchSaveVasPhoneOneyuanFree(result,list);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deleteVasPhoneOneyuanFree(Result result, Integer id) throws Exception {
        result = vasPhoneOneyuanFreeDao.deleteVasPhoneOneyuanFree(result,id);
        return result;
    }


    /**
     * 批量删除
     * @param result
     * @param vasPhoneOneyuanFree
     * @return
     */
    public Result batchDelVasPhoneOneyuanFree(Result result, List<VasPhoneOneyuanFreeVo> vasPhoneOneyuanFree_list)throws Exception{
        List<VasPhoneOneyuanFreePo> list = new ArrayList<VasPhoneOneyuanFreePo>(); 
        vasPhoneOneyuanFreeDao.batchDelete(list);
        return result;
    }


    /**
     * 批量删除ByIds
     * @param result
     * @param vasPhoneOneyuanFree
     * @return
     */
    public Result batchDelVasPhoneOneyuanFreeByIds(Result result,Integer[] ids)throws Exception{
        vasPhoneOneyuanFreeDao.batchDelVasPhoneOneyuanFreeByIds(result,ids);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryVasPhoneOneyuanFreeList(Result result,Page page,VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo){
        Pagination pagination =  vasPhoneOneyuanFreeDao.queryVasPhoneOneyuanFreePageList(result,page,vasPhoneOneyuanFreeVo).getData();
        if(null != pagination && null != pagination.getRows() && !pagination.getRows().isEmpty()){
            List<VasPhoneOneyuanFreePo> list = (List<VasPhoneOneyuanFreePo>) pagination.getRows();
            List<VasPhoneOneyuanFreeVo> voList = new ArrayList<>();
            for(VasPhoneOneyuanFreePo po : list){
                voList.add(this.po2Vo(po));
            }
            pagination.setRows(voList);
        }

        return result;
    }


    /**
     * 根据条件获取 列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryVasPhoneOneyuanFreeList(Result result,Page page,VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo,UserInfo user){
        /**
         *自行匹配需要查询的字段及值
         **/
        vasPhoneOneyuanFreeVo.setCheckStatus(PhoneLotteryStatus.CHECKED);
        Pagination pagination = vasPhoneOneyuanFreeDao.queryVasPhoneOneyuanFreePageList(result,page,vasPhoneOneyuanFreeVo).getData();
        if(null != pagination && null != pagination.getRows() && !pagination.getRows().isEmpty()){
            List<VasPhoneOneyuanFreePo> list = (List<VasPhoneOneyuanFreePo>) pagination.getRows();
            List<VasPhoneOneyuanFreeVo> voList = new ArrayList<>();
            for(VasPhoneOneyuanFreePo po : list){
                VasPhoneOneyuanFreeVo activityVo =  this.po2Vo(po);

              //如果是代金券，获取代金券名称
                if(po.getLotteryType() != null && po.getLotteryType().equals(EnumLotteryType.D.name())){
                    VasPhoneLotteryRecordPo lotteryRecordPo = lotteryRecordService.queryLotterRecordByOrderNo(result, activityVo.getOrderCode()).getData();
                    if(lotteryRecordPo != null){
                        activityVo.setCouponName(lotteryRecordPo.getName());
                    }
                }

                voList.add(activityVo);
            }
            pagination.setRows(voList);
        }
        result.setData(pagination);

        return result;
    }
    /**
     * 根据条件获取 列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryVasPhoneOneyuanFreeListByOrder(Result result,Page page,VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo){
        /**
         *自行匹配需要查询的字段及值
         **/
        Order order = Order.desc("lotteryTime");
        Pagination pagination = vasPhoneOneyuanFreeDao.queryVasPhoneOneyuanFreePageList(result,page,vasPhoneOneyuanFreeVo,order).getData();

        return result;
    }


    /**
     * 根据ID获取  带User对象
     * @param result
     * @return
     */
    public Result getVasPhoneOneyuanFreeById(Result result,Integer id,UserInfo user) throws Exception{
        result = vasPhoneOneyuanFreeDao.getVasPhoneOneyuanFreeById(result,id);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getVasPhoneOneyuanFreeById(Result result,Integer id) throws Exception{
        result = vasPhoneOneyuanFreeDao.getVasPhoneOneyuanFreeById(result,id);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updateVasPhoneOneyuanFree(Result result,VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo) throws Exception {
        Integer id = vasPhoneOneyuanFreeVo.getId();
        result = vasPhoneOneyuanFreeDao.getVasPhoneOneyuanFreeById(result,id);
        VasPhoneOneyuanFreePo vasPhoneOneyuanFreePo  = (VasPhoneOneyuanFreePo)result.getData();
        if (vasPhoneOneyuanFreePo != null) {
            copyProperties(vasPhoneOneyuanFreeVo, vasPhoneOneyuanFreePo);
        }
        return result;
    }


    /**
     * 批量更新 
     * @param result
     * @return
     */
    public Result batchUpdateVasPhoneOneyuanFree(Result result,List<VasPhoneOneyuanFreeVo> vasPhoneOneyuanFree_list) throws Exception {
        List<VasPhoneOneyuanFreePo> list = new ArrayList<VasPhoneOneyuanFreePo>(); 
        vasPhoneOneyuanFreeDao.batchUpdateVasPhoneOneyuanFree(result,list);
        return result;
    }




    /**
     * 根据 订单号查询活动单
     *
     * @param orderCode
     * @return
     */
    public VasPhoneOneyuanFreePo queryByOrderNo(String orderCode){
        return vasPhoneOneyuanFreeDao.queryByOrderNo(orderCode);
    }


    /**
     * 根据订单号修改支付状态
     *
     * @param payStatus
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public void updateAuditStatus(Result result, String orderCode, Date date,Integer status){

        if(StringUtils.isEmpty(orderCode) || status ==  null){
            throw  new BusinessRuntimeException("","确实必要参数");
        }

        vasPhoneOneyuanFreeDao.updateAuditStatus(result,orderCode,date,status);
    }

    /**
     * 代金券开奖
     * @param result
     * @param lotteryIds
     * @param ids
     * @return
     * @throws Exception
     */
    public Result setCashCouponLottery(Result result, String lotteryIds, String ids) throws Exception{

        if(null != lotteryIds && StringUtils.isNotBlank(ids)){
            logger.info("------> setCashCouponLottery lotteryIds:" + lotteryIds );
            logger.info("------> setCashCouponLottery ids:" + ids );

            UserInfo userInfo = result.getUserInfo();
            // 获取操作人账号
            String lotteryman = "";
            if(null != userInfo){
                lotteryman = userInfo.getLoginName();
            }

            // 校验开奖金额是否超出
            String [] idArray = ids.split(",");
            if(getLotteryMoney(lotteryIds) > idArray.length){
                throw new Exception("开奖金额有问题");
            }

            // 解析传入参数成List<CashCouponVo>
            List<CashCouponVo> cashCouponVoList = this.getCashCouponVoListFromParams(result, lotteryIds);

            logger.info("------> setCashCouponLottery list:" + JSON.toJSONString(cashCouponVoList) );

            if(!cashCouponVoList.isEmpty()){
                // 拼装参数
                List<GiveCouponDTO> giveCouponDTOList = new ArrayList<>();
                for(CashCouponVo cashVo : cashCouponVoList){
                    GiveCouponDTO couponDTO = new GiveCouponDTO();
                    couponDTO.setCouponRuleId(cashVo.getId().longValue());
                    couponDTO.setExtend(cashVo.getOrderCode());
                    couponDTO.setShopId(cashVo.getStoreId());
                    couponDTO.setUserId(cashVo.getUserId());
                    giveCouponDTOList.add(couponDTO);
                }

                // 调用代金券开奖接口
                List<GiveCouponDTO> couponDTOList = this.requestCouponApi(result, giveCouponDTOList);

                if(null != couponDTOList && !couponDTOList.isEmpty()){

                    // 解析出代金券码
                    // 如果出现代金券为空，则“开奖失败”
                    // 开奖要么全部成功，要么全部失败
                    cashCouponVoList = this.getCashCoupon(cashCouponVoList, couponDTOList);

                    logger.info("------> setCashCouponLottery list【2】:" + JSON.toJSONString(cashCouponVoList) );

                    // 开奖的本地操作
                    this.doLotteryLocalOperate(result, idArray, lotteryman, cashCouponVoList);
                } else {

                    throw new Exception("开奖失败");
                }

            }


        }
        return result;
    }

    /**
     * 代金券set到VO中
     * @param cashCouponVoList
     * @param couponDTOList
     * @return
     */
    private List<CashCouponVo> getCashCoupon(List<CashCouponVo> cashCouponVoList, List<GiveCouponDTO> couponDTOList) throws Exception{
        for(CashCouponVo cashVo : cashCouponVoList){
            if(null != couponDTOList && !couponDTOList.isEmpty()){
                logger.info("-------------> convert499:" + JSON.toJSONString(couponDTOList));
                logger.info("-------------> convert499:" + couponDTOList.get(0).getClass());
            }

            for(GiveCouponDTO giveCouponDTO : couponDTOList){
                if(cashVo.getOrderCode().equals(giveCouponDTO.getExtend())){
                    if(null != giveCouponDTO.getCoupon() && StringUtils.isNotBlank(giveCouponDTO.getCoupon().getCouponNumber())){
                        cashVo.setCashCoupon(giveCouponDTO.getCoupon().getCouponNumber());
                        cashVo.setDateRange(giveCouponDTO.getCoupon().getUseBeginTime()+"至"+giveCouponDTO.getCoupon().getUseEndTime());
                        cashVo.setAmount(String.valueOf(giveCouponDTO.getCoupon().getMoney()));
                        continue;
                    } else {
                        throw new Exception("开奖失败");
                    }

                }
            }
        }
        return cashCouponVoList;
    }

    /**
     * 代金券开奖，本地数据库操作
     * @param result
     * @param idArray
     * @param lotteryman
     * @param couponDTOList
     */
    private void doLotteryLocalOperate(Result result, String [] idArray, String lotteryman, List<CashCouponVo> cashCouponVoList){

        // sql修改所有记录为“未中奖”状态
        vasPhoneOneyuanFreeDao.update2Lottery(result, idArray, PhoneLotteryStatus.LOTTERY_FAIL, lotteryman, getNewBatchId(result));


        // 操作”中奖“记录
        for(CashCouponVo cashCouponVo : cashCouponVoList){

            if(StringUtils.isNotBlank(cashCouponVo.getCashCoupon())) {

                // 设置中奖记录为“中奖”状态
                VasPhoneOneyuanFreePo phonePo = vasPhoneOneyuanFreeDao.getVasPhoneOneyuanFreeById(result, cashCouponVo.getActivityId()).getData();
                if(null != phonePo){
                    phonePo.setLotteryStatus(PhoneLotteryStatus.LOTTERY);
                    phonePo.setWinningAmount(cashCouponVo.getAmount());
                    phonePo.setCashCoupon(cashCouponVo.getCashCoupon());
                    phonePo.setLotteryType(EnumLotteryType.D.toString());
                    phonePo.setLotteryTime(new Date());
                }


                // 查询订单信息
                PhoneOrderInfoPo chargeInfo = phoneOrderInfoDao.queryByOrderCode(result, phonePo.getOrderCode()).getData();
                if(null != chargeInfo){

//                    //代金券金额改成代金券名称， modify by 2017-6-8
//                    VasPhoneLotteryRecordPo lotteryRecordPo =  lotteryRecordService.queryLotterRecordByOrderNo(result, phonePo.getOrderCode()).getData();

                    // 对中奖用户发送短信
                    String userMessage = String.format(SMSPhoneMessageTemplateConstants.LOTTERY_CASH_COUPON_USER,
                            phonePo.getChargeMobile(),
                            cashCouponVo.getName(),
                            cashCouponVo.getDateRange(),
                            phonePo.getOrderCode());

                    logger.info("------> coupon lottery send sms:" + userMessage);
                    smsService.sendSms(phonePo.getChargeMobile(), userMessage);
                    
                    
                    // 对中奖网点老板发送短信
                    String bossMessage = String.format(SMSPhoneMessageTemplateConstants.LOTTERY_CASH_COUPON_STORE, phonePo.getChargeMobile(), cashCouponVo.getName(), phonePo.getOrderCode());
                    logger.info("------> coupon send boss:" + bossMessage);
                    sendSmsToStore(bossMessage,chargeInfo.getBizId());


                }

                // 保存中奖历史记录
                this.saveLotterRecord(cashCouponVo);

                logger.info("------> setCashCouponLottery 【success】:" + JSON.toJSONString(cashCouponVo) );
            } else {
                logger.info("------> setCashCouponLottery 【fail】:" + JSON.toJSONString(cashCouponVo) );
            }
        }
    }

    
    /**
     * 给网点老板发短信
     * 
     * @param smsTemp
     * @param bizId
     */
    private void sendSmsToStore(String smsTemp,Long bizId){
        Result result = new Result();
        ShopVO shopVo = shopService.getShop(result, bizId);
        logger.info("##--> query store mobile, bizId:{}, shopVo:{}",bizId, JSON.toJSONString(shopVo));
        if(shopVo != null && StringUtils.isNotBlank(shopVo.getContactTel())){
            smsService.sendSms(shopVo.getContactTel(), smsTemp);
        }else{
            logger.info("##--> 网点老板手机号码为空，不发短信。bizId:{}",bizId);
        }
    }

    /**
     *  添加代金券历史记录
     * @param cashCouponVo
     */
    private void saveLotterRecord(CashCouponVo cashCouponVo){
        VasPhoneLotteryRecordPo recordPo = new VasPhoneLotteryRecordPo();
        recordPo.setOrderCode(cashCouponVo.getOrderCode());
        recordPo.setCashCoupon(cashCouponVo.getCashCoupon());
        recordPo.setName(cashCouponVo.getName());
        recordPo.setRange(cashCouponVo.getRange());
        recordPo.setRegion(cashCouponVo.getArea());
        recordPo.setAmount(cashCouponVo.getAmount());
        recordPo.setDateRange(cashCouponVo.getDateRange());
        recordPo.setCount(cashCouponVo.getCount());
        recordPo.setCreateTime(DateUtil.date2String(new Date()));
        logger.info("---------》 saveLotterRecord:" + JSON.toJSONString(recordPo));
        vasPhoneLotteryRecordDao.save(recordPo);
    }

    /**
     * 调用代金券开奖接口
     * @param result
     * @param giveCouponDTOList
     * @return
     * @throws Exception
     */
    private List<GiveCouponDTO> requestCouponApi(Result result, List<GiveCouponDTO> giveCouponDTOList) throws Exception{
        logger.info("-------> setCashCouponLottery couponApi params【1】:" + JSON.toJSONString(giveCouponDTOList));
        List<GiveCouponDTO> couponDTOList = null;
        try {
            couponDTOList = couponApi.giveCoupon(result, giveCouponDTOList).getData();
        } catch (Exception e){
            logger.error("setCashCouponLottery couponApi error【2】" + e.getMessage(), e);
            throw new Exception("开奖失败");
        }
        logger.info("-------> setCashCouponLottery couponApi return【3】:" + JSON.toJSONString(couponDTOList));
        return couponDTOList;
    }


    /**
     * 解析传入的代金券参数，组装成List
     * @param result
     * @param lotteryIds
     * @return
     * @throws Exception
     */
    private List<CashCouponVo> getCashCouponVoListFromParams(Result result, String lotteryIds) throws Exception{

        List<CashCouponVo> cashCouponVoList = new ArrayList<>();

        String lotteryMsg [] = lotteryIds.split(",");
        for(String lotterMan : lotteryMsg) {
            String msg[] = lotterMan.split("@");
            CashCouponVo vo = new CashCouponVo();
            vo.setActivityId(Integer.valueOf(msg[0]));
            vo.setAmount(msg[1]);
            vo.setId(Integer.valueOf(msg[2]));
            vo.setName(msg[3]);
            vo.setRange(msg[4]);
            vo.setArea(msg[5]);
            vo.setDateRange(msg[6]);
            vo.setCount(Integer.valueOf(msg[7]));

            // 查询活动记录
            VasPhoneOneyuanFreePo phonePo = vasPhoneOneyuanFreeDao.getVasPhoneOneyuanFreeById(result, Integer.valueOf(msg[0])).getData();
            if(null != phonePo){
                vo.setOrderCode(phonePo.getOrderCode());
            } else {
                throw new Exception("中奖记录不存在");
            }

            // 查询订单记录
            PhoneOrderInfoPo orderInfo = phoneOrderInfoDao.queryByOrderCode(result, phonePo.getOrderCode()).getData();
            if(null == orderInfo){
                throw new Exception("订单记录不存在");
            }

            vo.setStoreId(Long.valueOf(orderInfo.getStoreCode()));
            vo.setUserId(this.queryMemberVo(result, orderInfo.getRechargePhone(), orderInfo.getPhoneOrderCode()));

            cashCouponVoList.add(vo);
        }

        return cashCouponVoList;
    }


    /**
     * 开奖接口
     * @param result
     * @param lotteryIds         中奖ID
     * @param ids               当前批次所有ID
     * @return
     */
    public Result setLottery(Result result, String lotteryIds, String ids) throws Exception{

        if(null != lotteryIds && StringUtils.isNotBlank(ids)){

            logger.info("------> setLottery lotteryIds:" + lotteryIds );
            logger.info("------> setLottery ids:" + ids );

            UserInfo userInfo = result.getUserInfo();
            // 获取操作人账号
            String lotteryman = "";
            if(null != userInfo){
                lotteryman = userInfo.getLoginName();
            }


            // 先修改所有记录为“未中奖”（包括中奖记录）
            String [] idArray = ids.split(",");
            if(getLotteryMoney(lotteryIds) > idArray.length){
                throw new Exception("开奖金额有问题");
            }
            // 修改所有记录为“未中奖”状态
            vasPhoneOneyuanFreeDao.update2Lottery(result, idArray, PhoneLotteryStatus.LOTTERY_FAIL, lotteryman, getNewBatchId(result));


            // 设置中奖记录为“中奖”状态
            String lotteryMsg [] = lotteryIds.split(",");
            for(String lotterMan : lotteryMsg){
                String msg [] = lotterMan.split("@");
                VasPhoneOneyuanFreePo phonePo = vasPhoneOneyuanFreeDao.getVasPhoneOneyuanFreeById(result, Integer.valueOf(msg[0])).getData();
                if(null != phonePo){
                    phonePo.setLotteryStatus(PhoneLotteryStatus.LOTTERY);
                    phonePo.setWinningAmount(msg[1]);
                    if(msg[1].equals(phonePo.getChargeAmount().toString())){
                        phonePo.setLotteryType(EnumLotteryType.M.toString());
                    } else {
                        phonePo.setLotteryType(EnumLotteryType.G.toString());
                    }

                } else {
                    throw new Exception("中奖记录不存在");
                }

                // 查询订单信息
                PhoneOrderInfoPo chargeInfo = phoneOrderInfoDao.queryByOrderCode(result, phonePo.getOrderCode()).getData();
                if(null != chargeInfo){

                    // 对中奖用户发送短信
                    String userMessage = String.format(SMSPhoneMessageTemplateConstants.LOTTERY_MESSAGE_USER, phonePo.getChargeMobile(), msg[1], phonePo.getOrderCode());
                    logger.info("------> lottery send:" + userMessage);
                    smsService.sendSms(phonePo.getChargeMobile(), userMessage);

                    // 对中奖网点老板发送短信
                    String bossMessage = String.format(SMSPhoneMessageTemplateConstants.LOTTERY_MESSAGE_BOSS, phonePo.getChargeMobile(), msg[1], phonePo.getOrderCode());
                    logger.info("------> lottery send boss:" + bossMessage);
                    sendSmsToStore(bossMessage,chargeInfo.getBizId());

                } else {
                    throw new Exception("订单编号不存在");
                }




            }

        }

        return result;
    }


    /**
     * 获取最新的批次Id
     * @param result
     * @return
     */
    private String getNewBatchId(Result result){
        // 获取新的批次号
        Integer maxBatchId = vasPhoneOneyuanFreeDao.queryMaxBatchId(result).getData();
        String newBatchId = "1";
        if(null != maxBatchId){
            newBatchId = String.valueOf(Integer.valueOf(maxBatchId) + 1);
        }
        return newBatchId;
    }


    /**
     * 获取开奖金额
     * @param lotteryIds
     * @return
     */
    private Integer getLotteryMoney(String lotteryIds){
        Integer money = 0;
        String lotteryMsg [] = lotteryIds.split(",");
        for(String lotterMan : lotteryMsg){
            String msg [] = lotterMan.split("@");
            money += Integer.valueOf(msg[1]);
        }
        return money;
    }

    public Result queryLotteryAmountList(Result result){
        return vasPhoneOneyuanFreeDao.queryLotteryAmountList(result);
    }


    public Result queryCashCouponList(Result result, String orderCode) throws Exception{

        //        List<CashCouponVo> testList = new ArrayList<>();
        //        testList = this.getTestData(testList);
        //        result.setData(testList);
        //        return result;

        Result contentResult = result;
        logger.info("-----》 queryCashCouponList:" + orderCode);

        if(StringUtils.isNotBlank(orderCode)){

            PhoneOrderInfoPo orderInfo = phoneOrderInfoDao.queryByOrderCode(result, orderCode).getData();
            if(null == orderInfo){
                throw new Exception("订单记录不存在");
            }

            logger.info("-----》 queryCashCouponList:" + JSON.toJSONString(orderInfo));

            QueryCouponParamVO paramVO = new QueryCouponParamVO();
            paramVO.setUserId(this.queryMemberVo(contentResult, orderInfo.getRechargePhone(), orderInfo.getPhoneOrderCode()));
            paramVO.setShopId(Long.valueOf(orderInfo.getStoreCode()));
            paramVO.setReceiveType(CouponReceiveTypeEnum.AUTO_GIVE);
            paramVO.setSysType(SysTypeEnum.B2C);
            paramVO.setUseSceneTypes(Arrays.asList(UseSceneTypeEnum.RECHARGE));

            logger.info("-----》 queryCashCouponList couponApi request:{}",JSON.toJSONString(paramVO));

            List<CouponVO> couponVOList = null;
            try {
                couponVOList = couponApi.findCouponList(result, paramVO).getData();
            } catch (Exception e) {
                logger.error(e.getMessage() + "--》 查询代金券接口报错:" + orderCode, e);
                throw new Exception("获取代金券失败");
            }


            logger.info("-----》 queryCashCouponList:{},list:{}", orderCode, JSON.toJSONString(couponVOList));

            List<CashCouponVo> cashCouponVoList = new ArrayList<>();
            if(null != couponVOList && !couponVOList.isEmpty()){
                for(CouponVO couponVO : couponVOList){
                    // 过滤掉不是“话费充值”的优惠券  2017-6-29
                    if(couponVO.getScopeType().intValue() != 9){
                        continue;
                    }

                    CashCouponVo cashVo = new CashCouponVo();
                    cashVo.setId(couponVO.getRuleID().intValue());
                    if(StringUtils.isNotBlank(couponVO.getActivityName())){
                        cashVo.setName(couponVO.getActivityName().replace(" ", ""));
                    } else {
                        cashVo.setName("没有返回");
                    }
                    cashVo.setRange(couponVO.getUseScope());
                    cashVo.setArea(couponVO.getAreaTypeNames());
                    cashVo.setAmount(String.valueOf(couponVO.getMoney().intValue()));
                    if(null == couponVO.getUseBeginTime() && null == couponVO.getUseEndTime()){
                        cashVo.setDateRange("开奖后生效");
                    } else {
                        cashVo.setDateRange(couponVO.getUseBeginTime() + "至" + couponVO.getUseEndTime());
                    }
                    cashVo.setCount(couponVO.getTotalAmount()-couponVO.getAchieveaAmount());
                    cashCouponVoList.add(cashVo);
                }

            } else {


            }

            logger.info("-----》 queryCashCouponList【2】:{},list:{}", orderCode, JSON.toJSONString(couponVOList));
            result.setData(cashCouponVoList);

        }


        return result;
    }

    private List<CashCouponVo> getTestData(List<CashCouponVo> cashCouponVoList){
        //查不到生成测试数据
        CashCouponVo cashVo1 = new CashCouponVo();
        cashVo1.setId(1);
        cashVo1.setName("代金券A 5元".replace(" ", ""));
        cashVo1.setRange("全场通用");
        cashVo1.setArea("全部县域");
        cashVo1.setAmount("5");
        cashVo1.setDateRange("2017-06-01至2017-07-01");
        cashVo1.setCount(3);
        cashCouponVoList.add(cashVo1);


        CashCouponVo cashVo2 = new CashCouponVo();
        cashVo2.setId(2);
        cashVo2.setName("代金券A3元");
        cashVo2.setRange("全场通用");
        cashVo2.setArea("全部县域");
        cashVo2.setAmount("3");
        cashVo2.setDateRange("2017-06-01至2017-07-01");
        cashVo2.setCount(12);
        cashCouponVoList.add(cashVo2);

        CashCouponVo cashVo3 = new CashCouponVo();
        cashVo3.setId(3);
        cashVo3.setName("代金券A2元");
        cashVo3.setRange("全场通用");
        cashVo3.setArea("全部县域");
        cashVo3.setAmount("2");
        cashVo3.setDateRange("2017-06-01至2017-07-01");
        cashVo3.setCount(33);
        cashCouponVoList.add(cashVo3);

        CashCouponVo cashVo4 = new CashCouponVo();
        cashVo4.setId(4);
        cashVo4.setName("代金券A1元");
        cashVo4.setRange("全场通用");
        cashVo4.setArea("全部县域");
        cashVo4.setAmount("1");
        cashVo4.setDateRange("2017-06-01至2017-07-01");
        cashVo4.setCount(11);
        cashCouponVoList.add(cashVo4);

        return cashCouponVoList;
    }

    /**
     * 获取会员ID
     * @param result
     * @param mobile
     * @return
     * @throws Exception
     */
    private Long queryMemberVo(Result result, String mobile, String orderCode) throws Exception{
        MemberResultVo memberResultVo = new MemberResultVo();
        UserInfo userInfo = result.getUserInfo();
        //获取会员信息
        Map<String, Object> mbrParam = Maps.newHashMap ();
        mbrParam.put ("reqSource", "b2c");
        mbrParam.put ("sysType", 2);
        mbrParam.put ("token", userInfo.getSessionId ());
        mbrParam.put ("mobile", mobile);
        String mbrUrl = TshDiamondClient.getInstance ().getConfig ("mbr-url") + FundURLConstants.ACC_MEMEBER_INFO;
        String mbrResponse = HttpUtils.doPost(mbrUrl, mbrParam);

        logger.info("----->获取会员的信息:{}, orderCode:{}", mbrResponse, orderCode);

        if (StringUtils.isEmpty (mbrResponse)) {
            logger.error ("获取会员信息异常, orderCode:" + orderCode );
            throw new Exception("获取代金券异常");
        }
        ReturnDTO returnDTO = JSONObject.parseObject (mbrResponse, ReturnDTO.class);
        if (200 != returnDTO.getStatus()) {
            logger.error ("获取会员信息异常, orderCode:" + orderCode);
            throw new Exception ("获取代金券异常");
        }

        MemberVo memberVo = JSONObject.parseObject (JSONObject.toJSONString (returnDTO.getData ()), MemberVo.class);

        if(null != memberVo){
            return memberVo.getId();
        }

        throw new Exception ("获取会员信息异常");
    }

    /**
     *
     * @param result
     * @param lotteryCount
     * @param chargeAmount
     * @return
     */
    public Result queryVasPhoneOneyuanFreeList(Result result, String lotteryCount, String chargeAmount) {
        if(StringUtils.isBlank(lotteryCount)){
            lotteryCount = "5";
        }
        List<VasPhoneOneyuanFreeStatisPo> poList = vasPhoneOneyuanFreeDao.queryVasPhoneOneyuanFreeList(result,
                lotteryCount,
                chargeAmount,
                PhoneLotteryStatus.LOTTERY_WAIT,
                PhoneLotteryStatus.CHECKED).getData();

        if(null != poList && !poList.isEmpty()){
            List<VasPhoneOneyuanFreeVo> voList = new ArrayList<>();
            for(VasPhoneOneyuanFreeStatisPo po : poList){
                voList.add(this.po2Vo(po));
            }
            result.setData(voList);
        }
        return result;
    }

    /**
     *
     * @param po
     * @return
     */
    private VasPhoneOneyuanFreeVo po2Vo(VasPhoneOneyuanFreePo po){
        VasPhoneOneyuanFreeVo vo = new VasPhoneOneyuanFreeVo();

        if(po ==null){
            return vo;                    
        }
        vo.setId((int)po.getId());

        if(null != po.getActivityAmount()) {
            vo.setActivityAmount(po.getActivityAmount());
        }
        if(null != po.getAreaName()){
            vo.setAreaName(po.getAreaName());
        }

        if(null != po.getBizType()) {
            vo.setBizType(po.getBizType());
        }

        if(null != po.getChargeAmount()) {
            vo.setChargeAmount(po.getChargeAmount());
        }

        if(null != po.getChargeMobile()) {
            vo.setChargeMobile(po.getChargeMobile());
        }

        if(null != po.getCreateTime()) {
            vo.setCreateTimeStr(DateUtil.date2String(po.getCreateTime()));
            vo.setCreateTimeString(DateUtil.date2String(po.getCreateTime()));
        }

        if(null != po.getLotteryman()) {
            vo.setLotteryman(po.getLotteryman());
        }


        if(null != po.getLotteryStatus()) {
            vo.setLotteryStatus(po.getLotteryStatus());
            if(PhoneLotteryStatus.LOTTERY_WAIT == po.getLotteryStatus().intValue()){
                vo.setLotteryStatusStr("未开奖");
            }
            if(PhoneLotteryStatus.LOTTERY == po.getLotteryStatus().intValue()){
                vo.setLotteryStatusStr("已中奖");
            }
            if(PhoneLotteryStatus.LOTTERY_FAIL == po.getLotteryStatus().intValue()){
                vo.setLotteryStatusStr("未中奖");
            }
        }



        if(null != po.getLotteryTime()) {
            vo.setLotteryTimeStr(DateUtil.date2String(po.getLotteryTime()));
            vo.setLotteryTimeString(DateUtil.date2String(po.getLotteryTime()));
        }

        if(null != po.getLuckyNumber()) {
            vo.setLuckyNumber(po.getLuckyNumber());
        }

        if(null != po.getOrderCode()) {
            vo.setOrderCode(po.getOrderCode());
        }


        if(null != po.getStoreName()) {
            vo.setStoreName(po.getStoreName());
        }

        if(null != po.getWinningAmount() && StringUtils.isNotBlank(po.getWinningAmount())){
            vo.setWinningAmount(po.getWinningAmount()+"元");
        } else {
            vo.setWinningAmount("-");
        }

        if(StringUtils.isNotBlank(po.getCashCoupon())){
            vo.setCashCoupon(po.getCashCoupon());
        }

        if(StringUtils.isNotBlank(po.getLotteryType())){
            vo.setLotteryType(po.getLotteryType());
            vo.setLotteryTypeName(EnumLotteryType.getEnume(po.getLotteryType()).getName());
        }

        return vo;
    }

    /**
     *
     * @param po
     * @return
     */
    private VasPhoneOneyuanFreeVo po2Vo(VasPhoneOneyuanFreeStatisPo po){
        VasPhoneOneyuanFreeVo vo = new VasPhoneOneyuanFreeVo();

        if(po ==null){
            return vo;
        }
        vo.setId((int)po.getId());
        if(StringUtils.isNotBlank(po.getCount())){
            vo.setCount(po.getCount());
        }

        if(null != po.getActivityAmount()) {
            vo.setActivityAmount(po.getActivityAmount());
        }
        if(null != po.getAreaName()){
            vo.setAreaName(po.getAreaName());
        }

        if(null != po.getBizType()) {
            vo.setBizType(po.getBizType());
        }

        if(null != po.getChargeAmount()) {
            vo.setChargeAmount(po.getChargeAmount());
        }

        if(null != po.getChargeMobile()) {
            vo.setChargeMobile(po.getChargeMobile());
        }

        if(null != po.getCreateTime()) {
            vo.setCreateTimeStr(DateUtil.date2String(po.getCreateTime()));
            vo.setCreateTimeString(DateUtil.date2String(po.getCreateTime()));
        }

        if(null != po.getLotteryman()) {
            vo.setLotteryman(po.getLotteryman());
        }


        if(null != po.getLotteryStatus()) {
            vo.setLotteryStatus(po.getLotteryStatus());
        }


        if(null != po.getLotteryTime()) {
            vo.setLotteryTimeStr(DateUtil.date2String(po.getLotteryTime()));
        }

        if(null != po.getLuckyNumber()) {
            vo.setLuckyNumber(po.getLuckyNumber());
        }

        if(null != po.getOrderCode()) {
            vo.setOrderCode(po.getOrderCode());
        }


        if(null != po.getStoreName()) {
            vo.setStoreName(po.getStoreName());
        }
        return vo;
    }

    /**
     * 修改幸运号
     * 
     * @param result
     * @param id
     * @param luckNumber
     * @return
     */
    public Result updateLuckNumById(Result result,int id,String luckNumber){
        return vasPhoneOneyuanFreeDao.updateLuckNumById(result, id, luckNumber);
    }


    /**
     * 活动参与统计
     * 
     * @param result
     * @param param
     * @return
     */
    public Result queryPageActivityStatistics(Result result,QueryActivityStatisticsVo param){
        param.setPage((param.getPage() - 1 ) * param.getRows());
        param.setRows(param.getRows());

        return vasPhoneOneyuanFreeDao.queryPageActivityStatistics(result, param);
    }


    /**
     * 统计导出
     * 
     * @param result
     * @param param
     * @return
     */
    public Result queryExportActivityStatistics(Result result,QueryActivityStatisticsVo param){

        return vasPhoneOneyuanFreeDao.queryExportActivityStatistics(result, param);
    }
}
