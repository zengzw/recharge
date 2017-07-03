package com.tsh.vas.phone.constroller;
import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.dtds.platform.util.excel.ExcelUtil;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.phone.util.StringUtils;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.model.phone.ActivityStatisticsPo;
import com.tsh.vas.phone.service.VasPhoneOneyuanFreeService;
import com.tsh.vas.vo.phone.QueryActivityStatisticsVo;
import com.tsh.vas.vo.phone.VasPhoneOneyuanFreeVo;

/**
 * 一元免单接口定义
 */
@Controller
@RequestMapping("/vas/activity")
public class VasPhoneOneyuanFreeController extends BaseController{



    private final static Logger LOGGER = LoggerFactory.getLogger(VasPhoneOneyuanFreeController.class);

    @Autowired
    private VasPhoneOneyuanFreeService vasPhoneOneyuanFreeService;


    /**
     * 保存
     * @param vasPhoneOneyuanFreeVo
     * @return
     */
    @RequestMapping(value = "/saveVasPhoneOneyuanFree.do")
    @ResponseBody
    public Result saveVasPhoneOneyuanFree(@ModelAttribute VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo)  {
        Result result = this.getResult();
        try {
            UserInfo user = result.getUserInfo();
            result = vasPhoneOneyuanFreeService.saveVasPhoneOneyuanFree(result, vasPhoneOneyuanFreeVo,user);
        } catch (Exception e) {
            result = this.error(result, e);
        }finally {
            this.send(result);
        }
        return result;
    }


    /**
     * 根据ID查询的数据
     * @param id
     * @return
     */
    @RequestMapping(value = "/getVasPhoneOneyuanFreeById.do")
    @ResponseBody
    public Result getVasPhoneOneyuanFreeById(Integer id){
        Result result = this.getResult();
        UserInfo user = result.getUserInfo();
        try {
            result = vasPhoneOneyuanFreeService.getVasPhoneOneyuanFreeById(result,id,user);
        } catch (Exception e) {
            result = this.error(result, e);
        }finally {
            this.send(result);
        }
        return result;
    }



    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delVasPhoneOneyuanFreeById.do")
    @ResponseBody
    public Result delVasPhoneOneyuanFreeById(Integer id){
        Result result = this.getResult();
        try {
            result = vasPhoneOneyuanFreeService.deleteVasPhoneOneyuanFree(result,id);
        } catch (Exception e) {
            result = this.error(result, e);
        }finally {
            this.send(result);
        }
        return result;
    }


    /**
     * 返回一元免单列表分页数据
     * @param page
     * @param rows
     * @param vasPhoneOneyuanFreeVo
     * @return
     */
    @RequestMapping(value = "/queryVasPhoneOneyuanFreePage.do")
    @ResponseBody
    public ReturnDTO queryVasPhoneOneyuanFreePage(int page, int rows, @ModelAttribute VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo){
        Result result = this.getResult();
        UserInfo user = result.getUserInfo();
        try {
            Page page_num = new Page(page,rows);
            result =  vasPhoneOneyuanFreeService.queryVasPhoneOneyuanFreeList(result,page_num,vasPhoneOneyuanFreeVo,user);
        } catch (Exception e) {
            result = this.error(result, e);
        }finally {
            this.send(result);
        }

        return result.DTO();
    }

    /**
     * 查询中奖金额列表
     * @return
     */
    @RequestMapping(value = "/queryLotteryAmountList.do")
    @ResponseBody
    public ReturnDTO queryLotteryAmountList(){
        Result result = this.getResult();
        try {
            result =  vasPhoneOneyuanFreeService.queryLotteryAmountList(result);
        } catch (Exception e) {
            result = this.error(result, e);
        }finally {
            this.send(result);
        }

        return result.DTO();
    }

    /**
     * 查询可用代金券列表
     * @return
     */
    @RequestMapping(value = "/queryCashCouponList.do")
    @ResponseBody
    public ReturnDTO queryCashCouponList(String orderCode){
        Result result = this.getResult();
        try {
            result =  vasPhoneOneyuanFreeService.queryCashCouponList(result, orderCode);
        } catch (Exception e) {
            result = this.error(result, e);
            result.setMsg(e.getMessage());
            result.setStatus (500);

        } finally {
            this.send(result);
        }
        return result.DTO();
    }

    /**
     * 获取开奖列表
     * @param lotteryCount
     * @param chargeAmount
     * @return
     */
    @RequestMapping(value = "/queryVasPhoneOneyuanFreeList.do")
    @ResponseBody
    public ReturnDTO queryVasPhoneOneyuanFreeList(String lotteryCount, String chargeAmount){
        Result result = this.getResult();
        try {
            result =  vasPhoneOneyuanFreeService.queryVasPhoneOneyuanFreeList(result, lotteryCount, chargeAmount);
        } catch (Exception e) {
            result = this.error(result, e);
        }finally {
            this.send(result);
        }

        return result.DTO();
    }


    /**
     * 设置中奖
     * @param lotteryIds
     * @param ids
     * @return
     */
    @RequestMapping(value = "/setLottery.do")
    @ResponseBody
    public ReturnDTO setLottery(String lotteryIds, String ids){

        Result result = this.getResult();
        try {
            result =  vasPhoneOneyuanFreeService.setLottery(result, lotteryIds, ids);
        } catch (Exception e) {
            result = this.error(result, e);
            result.setStatus (500);
            result.setMsg (e.getMessage());
        } finally {
            this.send(result);
        }

        return result.DTO();
    }

    /**
     * 设置代金券中奖
     * @param lotteryIds
     * @param ids
     * @return
     */
    @RequestMapping(value = "/setCashCouponLottery.do")
    @ResponseBody
    public ReturnDTO setCashCouponLottery(String lotteryIds, String ids){

        Result result = this.getResult();
        try {
            result =  vasPhoneOneyuanFreeService.setCashCouponLottery(result, lotteryIds, ids);
        } catch (Exception e) {
            result = this.error(result, e);
            result.setStatus (500);
            result.setMsg (e.getMessage());
        } finally {
            this.send(result);
        }

        return result.DTO();
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
    @RequestMapping(value = "/exportReports.do")
    public ReturnDTO exportReports(int page, int rows, @ModelAttribute VasPhoneOneyuanFreeVo vasPhoneOneyuanFreeVo){
        Result result = this.getResult();
        UserInfo user = result.getUserInfo();
        try {
            Page page_num = new Page(page,rows);
            Pagination pagination =  vasPhoneOneyuanFreeService.queryVasPhoneOneyuanFreeList(result,page_num,vasPhoneOneyuanFreeVo,user).getData();
            List<VasPhoneOneyuanFreeVo> voList = (List<VasPhoneOneyuanFreeVo>) pagination.getRows();
            if(null != voList && !voList.isEmpty()){
                String[] fields = new String[]{"orderCode","luckyNumber", "chargeMobile","createTimeString", "areaName", "storeName", "chargeAmount", "lotteryTypeName", "cashCoupon","couponName", "winningAmount", "lotteryStatusStr", "lotteryTimeString"};
                String[] titles = new String[]{"订单编号","幸运号", "充值手机号", "下单时间", "下单县域", "下单网点", "充值面额", "中奖类型", "中奖代金券码","代金券名称", "中奖金额", "开奖状态", "开奖时间"};

                String head = "话费免单列表";
                File file = ExcelUtil.export (null, head, fields, titles, voList, null);
                downloadExcel (request, response, file, head);
            }

        } catch (Exception e) {
            result = this.error(result, e);
        }finally {
            this.send(result);
        }

        return ReturnDTO.OK ();
    }


    /**
     * 活动参与统计列表
     * @param page
     * @param rows
     * @param params
     * @return
     */
    @RequestMapping(value = "/statistics/list")
    @ResponseBody
    public Pagination listActivityStatistics(int page, int rows,String params){
        Result result = this.getResult();
        LOGGER.info("---------请求参数:{}",params);

        QueryActivityStatisticsVo queryParams  = new QueryActivityStatisticsVo();
        if(!StringUtils.isEmpty(params)){
            queryParams = JSON.parseObject(params, QueryActivityStatisticsVo.class);
        }

        queryParams.setPage(page);
        queryParams.setRows(rows);


        Pagination pagination = vasPhoneOneyuanFreeService.queryPageActivityStatistics(result, queryParams).getData();
        return pagination;
    }

    /**
     * 参与活动总数 导出
     * @param params
     * @return
     */
    @RequestMapping(value = "/statistics/export")
    public ReturnDTO exportActivityStatistics(QueryActivityStatisticsVo params){
        LOGGER.info("#/statistics/export---->请求参数:{}",JSON.toJSONString(params));

        Result result = this.getResult();
        try {
            List<ActivityStatisticsPo> lstActivityStatistics =  vasPhoneOneyuanFreeService.queryExportActivityStatistics(result,params).getData();
            String[] fields = new String[]{"areaName","areaCount", "storeName","storeCount",};
            String[] titles = new String[]{"县域名称","县域参与人数", "网点名称", "网点参与人数"};

            String head = "话费抽奖参与网点排名列表";
            File file = ExcelUtil.export (null, head, fields, titles, lstActivityStatistics, null);
            downloadExcel (request, response, file, head);

        } catch (Exception e) {
            LOGGER.error("导出失败:",e);
        }

        return ReturnDTO.OK ();
    }



}
