package com.tsh.vas.trainticket.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.trainticket.HcpRefundTicketDao;
import com.tsh.vas.model.trainticket.HcpRefundTicketPo;
import com.tsh.vas.vo.trainticket.HcpRefundTicketVo;


/**
 * 退票 服务类
 *
 * @author zengzw
 * @date 2016年11月23日
 */
@Service
public class HcpRefundTicketService {
    @Autowired
    private HcpRefundTicketDao hcpRefundTicketDao;

    /**
     * 新增退票接口对象
     * @param result
     * @param hcpRefundTicket
     * @return
     */
    public Result addHcpRefundTicket(Result result,HcpRefundTicketVo hcpRefundTicketVo)throws Exception{
        HcpRefundTicketPo hcpRefundTicketPo = new HcpRefundTicketPo();

        if (hcpRefundTicketVo != null) {
            copyProperties(hcpRefundTicketVo, hcpRefundTicketPo);
        }

        result = hcpRefundTicketDao.addHcpRefundTicket(result,hcpRefundTicketPo);
        return result;
    }



    /**
     * @param hcpRefundTicketVo
     * @param hcpRefundTicketPo
     */
    private void copyProperties(HcpRefundTicketVo hcpRefundTicketVo, HcpRefundTicketPo hcpRefundTicketPo) {
        if(hcpRefundTicketVo.getHcpOrderCode()!=null){
            hcpRefundTicketPo.setHcpOrderCode(hcpRefundTicketVo.getHcpOrderCode());
        }
        if(hcpRefundTicketVo.getRefundCode()!=null){
            hcpRefundTicketPo.setRefundCode(hcpRefundTicketVo.getRefundCode());
        }
        if(hcpRefundTicketVo.getRealAmount()!=null){
            hcpRefundTicketPo.setRealAmount(hcpRefundTicketVo.getRealAmount());
        }
        if(hcpRefundTicketVo.getRefundAmount()!=null){
            hcpRefundTicketPo.setRefundAmount(hcpRefundTicketVo.getRefundAmount());
        }
        if(hcpRefundTicketVo.getSupplierRecord()!=null){
            hcpRefundTicketPo.setSupplierRecord(hcpRefundTicketVo.getSupplierRecord());
        }
        if(hcpRefundTicketVo.getMyRecord()!=null){
            hcpRefundTicketPo.setMyRecord(hcpRefundTicketVo.getMyRecord());
        }
        if(hcpRefundTicketVo.getCreateTime()!=null){
            hcpRefundTicketPo.setCreateTime(hcpRefundTicketVo.getCreateTime());
        }
        if(hcpRefundTicketVo.getRemark()!=null){
            hcpRefundTicketPo.setRemark(hcpRefundTicketVo.getRemark());
        }
        if(hcpRefundTicketVo.getRefundStatus()!=null){
            hcpRefundTicketPo.setRefundStatus(hcpRefundTicketVo.getRefundStatus());
        }
    }



    /**
     * 保存 退票接口对象 带User对象
     * @param result
     * @return
     */
    public Result saveHcpRefundTicket(Result result,HcpRefundTicketVo hcpRefundTicketVo,UserInfo user) throws Exception {
        if(hcpRefundTicketVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Integer id = hcpRefundTicketVo.getId();
        result = hcpRefundTicketDao.getHcpRefundTicketById(result,id);
        HcpRefundTicketPo hcpRefundTicketPo  = (HcpRefundTicketPo)result.getData();

        if (hcpRefundTicketPo != null) {
            copyProperties(hcpRefundTicketVo, hcpRefundTicketPo);
        }else{
            hcpRefundTicketPo = new HcpRefundTicketPo();
            copyProperties(hcpRefundTicketVo, hcpRefundTicketPo);
            result = hcpRefundTicketDao.addHcpRefundTicket(result,hcpRefundTicketPo);
        }
        return result;
    }



    /**
     * 保存 退票接口对象
     * @param result
     * @return
     */
    public Result saveHcpRefundTicket(Result result,HcpRefundTicketVo hcpRefundTicketVo) throws Exception {
        if(hcpRefundTicketVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Integer id = hcpRefundTicketVo.getId();
        result = hcpRefundTicketDao.getHcpRefundTicketById(result,id);
        HcpRefundTicketPo hcpRefundTicketPo  = (HcpRefundTicketPo)result.getData();

        if (hcpRefundTicketPo != null) {
            copyProperties(hcpRefundTicketVo, hcpRefundTicketPo);
        }else{
            hcpRefundTicketPo = new HcpRefundTicketPo();
            copyProperties(hcpRefundTicketVo, hcpRefundTicketPo);
            result = hcpRefundTicketDao.addHcpRefundTicket(result,hcpRefundTicketPo);
        }
        return result;
    }


    /**
     * 批量删除退票接口对象
     * @param result
     * @param hcpRefundTicket
     * @return
     */
    public Result batchDelHcpRefundTicket(Result result, List<HcpRefundTicketVo> hcpRefundTicket_list)throws Exception{
        List<HcpRefundTicketPo> list = new ArrayList<HcpRefundTicketPo>(); 
        hcpRefundTicketDao.batchDelete(list);
        return result;
    }


    /**
     * 批量删除退票接口对象ByIds
     * @param result
     * @param hcpRefundTicket
     * @return
     */
    public Result batchDelHcpRefundTicketByIds(Result result,Integer[] ids)throws Exception{
        hcpRefundTicketDao.batchDelHcpRefundTicketByIds(result,ids);
        return result;
    }


    /**
     * 根据条件获取 退票接口对象列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Result queryHcpRefundTicketList(Result result,Page page,HcpRefundTicketVo hcpRefundTicketVo){
        HcpRefundTicketPo hcpRefundTicketPo = new HcpRefundTicketPo();
        result = hcpRefundTicketDao.queryHcpRefundTicketList(result,page,hcpRefundTicketPo);
        return result;
    }


    /**
     * 根据条件获取 退票接口对象列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Result queryHcpRefundTicketList(Result result,Page page,HcpRefundTicketVo hcpRefundTicketVo,UserInfo user){
        HcpRefundTicketPo hcpRefundTicketPo = new HcpRefundTicketPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = hcpRefundTicketDao.queryHcpRefundTicketList(result,page,hcpRefundTicketPo);
        return result;
    }


    /**
     * 根据ID获取 退票接口对象 带User对象
     * @param result
     * @return
     */
    public Result getHcpRefundTicketById(Result result,Integer id,UserInfo user) throws Exception{
        result = hcpRefundTicketDao.getHcpRefundTicketById(result,id);
        return result;
    }


    /**
     * 根据ID获取 退票接口对象
     * @param result
     * @return
     */
    public Result getHcpRefundTicketById(Result result,Integer id) throws Exception{
        result = hcpRefundTicketDao.getHcpRefundTicketById(result,id);
        return result;
    }


    /**
     * 根据订单号获取退票信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryByOrderCode(Result result, String orderCode){
        return hcpRefundTicketDao.queryByOrderCode(result, orderCode);
    }



    /**
     * 根据退票订单号获取退票信息
     * 
     * @param result
     * @param orderCode
     * @return
     * @throws Exception
     */
    public Result queryByReturnTicketCode(Result result, String returnTicketCode){
        return hcpRefundTicketDao.queryByReturnTicketCode(result, returnTicketCode);
    }

}
