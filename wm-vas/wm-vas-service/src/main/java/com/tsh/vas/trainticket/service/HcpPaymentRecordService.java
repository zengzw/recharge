package com.tsh.vas.trainticket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.trainticket.HcpPaymentRecordDao;
import com.tsh.vas.model.trainticket.HcpPaymentRecordPo;
import com.tsh.vas.vo.trainticket.HcpPaymentRecordVo;



/**
 * 支付记录服务
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Service
@SuppressWarnings("all")
public class HcpPaymentRecordService {
    @Autowired
    private HcpPaymentRecordDao hcpPaymentRecordDao;

    /**
     * 新增招工接口对象
     * @param result
     * @param hcpPaymentRecord
     * @return
     */
    public Result addHcpPaymentRecord(Result result,HcpPaymentRecordVo hcpPaymentRecordVo)throws Exception{
        HcpPaymentRecordPo hcpPaymentRecordPo = new HcpPaymentRecordPo();

        if (hcpPaymentRecordVo != null) {
            copyProperties(hcpPaymentRecordVo, hcpPaymentRecordPo);
        }

        result = hcpPaymentRecordDao.addHcpPaymentRecord(result,hcpPaymentRecordPo);
        return result;
    }



    /**
     * @param hcpPaymentRecordVo
     * @param hcpPaymentRecordPo
     */
    private void copyProperties(HcpPaymentRecordVo hcpPaymentRecordVo, HcpPaymentRecordPo hcpPaymentRecordPo) {
        if(hcpPaymentRecordVo.getPayType()!=null){
            hcpPaymentRecordPo.setPayType(hcpPaymentRecordVo.getPayType());
        }
        if(hcpPaymentRecordVo.getHcpOrderCode()!=null){
            hcpPaymentRecordPo.setHcpOrderCode(hcpPaymentRecordVo.getHcpOrderCode());
        }
        if(hcpPaymentRecordVo.getPayAmount()!=null){
            hcpPaymentRecordPo.setPayAmount(hcpPaymentRecordVo.getPayAmount());
        }
        if(hcpPaymentRecordVo.getPayRecord()!=null){
            hcpPaymentRecordPo.setPayRecord(hcpPaymentRecordVo.getPayRecord());
        }
        if(hcpPaymentRecordVo.getPayWay()!=null){
            hcpPaymentRecordPo.setPayWay(hcpPaymentRecordVo.getPayWay());
        }
        if(hcpPaymentRecordVo.getCreateTime()!=null){
            hcpPaymentRecordPo.setCreateTime(hcpPaymentRecordVo.getCreateTime());
        }
        if(hcpPaymentRecordVo.getRemark()!=null){
            hcpPaymentRecordPo.setRemark(hcpPaymentRecordVo.getRemark());
        }
    }



    /**
     * 保存 招工接口对象 带User对象
     * @param result
     * @return
     */
    public Result saveHcpPaymentRecord(Result result,HcpPaymentRecordVo hcpPaymentRecordVo,UserInfo user) throws Exception {
        if(hcpPaymentRecordVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Integer id = hcpPaymentRecordVo.getId();
        result = hcpPaymentRecordDao.getHcpPaymentRecordById(result,id);
        HcpPaymentRecordPo hcpPaymentRecordPo  = (HcpPaymentRecordPo)result.getData();

        if (hcpPaymentRecordPo != null) {
            copyProperties(hcpPaymentRecordVo, hcpPaymentRecordPo);
        }else{
            hcpPaymentRecordPo = new HcpPaymentRecordPo();
            copyProperties(hcpPaymentRecordVo, hcpPaymentRecordPo);
            result = hcpPaymentRecordDao.addHcpPaymentRecord(result,hcpPaymentRecordPo);
        }
        return result;
    }



    /**
     * 保存 招工接口对象
     * @param result
     * @return
     */
    public Result saveHcpPaymentRecord(Result result,HcpPaymentRecordVo hcpPaymentRecordVo) throws Exception {
        if(hcpPaymentRecordVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Integer id = hcpPaymentRecordVo.getId();
        result = hcpPaymentRecordDao.getHcpPaymentRecordById(result,id);
        HcpPaymentRecordPo hcpPaymentRecordPo  = (HcpPaymentRecordPo)result.getData();

        if (hcpPaymentRecordPo != null) {
            copyProperties(hcpPaymentRecordVo, hcpPaymentRecordPo);
        }else{
            hcpPaymentRecordPo = new HcpPaymentRecordPo();
            copyProperties(hcpPaymentRecordVo, hcpPaymentRecordPo);
            result = hcpPaymentRecordDao.addHcpPaymentRecord(result,hcpPaymentRecordPo);
        }
        return result;
    }


    /**
     * 批量新增招工接口对象
     * @param result
     * @param hcpPaymentRecord
     * @return
     */
    public Result batchSaveHcpPaymentRecord(Result result, List<HcpPaymentRecordVo> hcpPaymentRecord_list) throws Exception {
        List<HcpPaymentRecordPo> list = new ArrayList<HcpPaymentRecordPo>();
        result = hcpPaymentRecordDao.batchSaveHcpPaymentRecord(result,list);
        return result;
    }

    /**
     * 删除招工接口对象
     * @param id 招工接口对象标识
     * @return
     */
    public Result deleteHcpPaymentRecord(Result result, Integer id) throws Exception {
        result = hcpPaymentRecordDao.deleteHcpPaymentRecord(result,id);
        return result;
    }


    /**
     * 批量删除招工接口对象
     * @param result
     * @param hcpPaymentRecord
     * @return
     */
    public Result batchDelHcpPaymentRecord(Result result, List<HcpPaymentRecordVo> hcpPaymentRecord_list)throws Exception{
        List<HcpPaymentRecordPo> list = new ArrayList<HcpPaymentRecordPo>(); 
        hcpPaymentRecordDao.batchDelete(list);
        return result;
    }


    /**
     * 批量删除招工接口对象ByIds
     * @param result
     * @param hcpPaymentRecord
     * @return
     */
    public Result batchDelHcpPaymentRecordByIds(Result result,Integer[] ids)throws Exception{
        hcpPaymentRecordDao.batchDelHcpPaymentRecordByIds(result,ids);
        return result;
    }


    /**
     * 根据条件获取 招工接口对象列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryHcpPaymentRecordList(Result result,Page page,HcpPaymentRecordVo hcpPaymentRecordVo){
        HcpPaymentRecordPo hcpPaymentRecordPo = new HcpPaymentRecordPo();
        result = hcpPaymentRecordDao.queryHcpPaymentRecordList(result,page,hcpPaymentRecordPo);
        return result;
    }


    /**
     * 根据条件获取 招工接口对象列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryHcpPaymentRecordList(Result result,Page page,HcpPaymentRecordVo hcpPaymentRecordVo,UserInfo user){
        HcpPaymentRecordPo hcpPaymentRecordPo = new HcpPaymentRecordPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = hcpPaymentRecordDao.queryHcpPaymentRecordList(result,page,hcpPaymentRecordPo);
        return result;
    }


    /**
     * 根据ID获取 招工接口对象 带User对象
     * @param result
     * @return
     */
    public Result getHcpPaymentRecordById(Result result,Integer id,UserInfo user) throws Exception{
        result = hcpPaymentRecordDao.getHcpPaymentRecordById(result,id);
        return result;
    }


    /**
     * 根据ID获取 招工接口对象
     * @param result
     * @return
     */
    public Result getHcpPaymentRecordById(Result result,Integer id) throws Exception{
        result = hcpPaymentRecordDao.getHcpPaymentRecordById(result,id);
        return result;
    }


    /**
     * 更新 招工接口对象
     * @param result
     * @return
     */
    public Result updateHcpPaymentRecord(Result result,HcpPaymentRecordVo hcpPaymentRecordVo) throws Exception {
        Integer id = hcpPaymentRecordVo.getId();
        result = hcpPaymentRecordDao.getHcpPaymentRecordById(result,id);
        HcpPaymentRecordPo hcpPaymentRecordPo  = (HcpPaymentRecordPo)result.getData();
        if (hcpPaymentRecordPo != null) {
            copyProperties(hcpPaymentRecordVo, hcpPaymentRecordPo);
        }
        return result;
    }


    /**
     * 批量更新 招工接口对象
     * @param result
     * @return
     */
    public Result batchUpdateHcpPaymentRecord(Result result,List<HcpPaymentRecordVo> hcpPaymentRecord_list) throws Exception {
        List<HcpPaymentRecordPo> list = new ArrayList<HcpPaymentRecordPo>(); 
        hcpPaymentRecordDao.batchUpdateHcpPaymentRecord(result,list);
        return result;
    }

}
