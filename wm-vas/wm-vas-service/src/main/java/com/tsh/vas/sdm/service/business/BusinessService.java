package com.tsh.vas.sdm.service.business;

import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Result;
import com.google.common.collect.Lists;
import com.tsh.vas.dao.BusinessInfoDao;
import com.tsh.vas.dao.SupplierBusinessDao;
import com.tsh.vas.dao.SupplierInfoDao;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.SupplierBusiness;
import com.tsh.vas.model.SupplierInfo;
import com.tsh.vas.vo.business.BusinessSupplierQueryVo;
import com.tsh.vas.vo.business.SupplierBusinessVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iritchie.ren on 2016/9/27.
 */
@Service
public class BusinessService {

    @Autowired
    private SupplierBusinessDao supplierBusinessDao;
    @Autowired
    private SupplierInfoDao supplierInfoDao;
    @Autowired
    private BusinessInfoDao businessInfoDao;

    /**
     * 查询服务业务所属供应商的列表
     *
     * @param result
     * @param businessSupplierQueryVo
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result findBusinessSuppliers(Result result, BusinessSupplierQueryVo businessSupplierQueryVo) throws Exception {
        //查询供应商信息列表
        List<SupplierInfo> supplierInfoList = this.supplierInfoDao.findBySupplierName (result, businessSupplierQueryVo.getCompany ()).getData ();
        if (null != supplierInfoList && supplierInfoList.size () > 0) {
            List<String> supplierCodeList = Lists.newArrayList ();
            for (SupplierInfo item : supplierInfoList) {
                supplierCodeList.add (item.getSupplierCode ());
            }
            //查询供应商的业务信息列表
            List<SupplierBusiness> supplierBusinessList = this.supplierBusinessDao.findByBusinessCodeAndSuppliers (result, businessSupplierQueryVo.getBusinessCode (), supplierCodeList).getData ();
            List<SupplierBusinessVo> supplierBusinessVoList = Lists.newArrayList ();
            for (SupplierBusiness item : supplierBusinessList) {
                SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, item.getSupplierCode ()).getData ();
                SupplierBusinessVo supplierBusinessVo = JSONObject.toJavaObject (JSONObject.parseObject (JSONObject.toJSONString (item)), SupplierBusinessVo.class);
                supplierBusinessVo.setSupplierName (supplierInfo.getCompany ());
                supplierBusinessVoList.add (supplierBusinessVo);
            }
            result.setCode (ResponseCode.OK_CODE.getCode ());
            result.setData (supplierBusinessVoList);
        } else {
            List<SupplierBusinessVo> supplierBusinessVoList = Lists.newArrayList ();
            result.setData (supplierBusinessVoList);
        }
        return result;
    }

    /**
     * 修改业务分成
     *
     * @param result
     * @param supplierBusinessVo
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result updateProfit(Result result, SupplierBusinessVo supplierBusinessVo) throws Exception {
        SupplierBusiness supplierBusiness = this.supplierBusinessDao.queryBySupplierCodeAndBusinessCode (result, supplierBusinessVo.getSupplierCode (), supplierBusinessVo.getBusinessCode ()).getData ();
        supplierBusiness.setShareWay (supplierBusinessVo.getShareWay ());
        supplierBusiness.setTotalShareRatio (supplierBusinessVo.getTotalShareRatio ());
        supplierBusiness.setPlatformShareRatio (supplierBusinessVo.getPlatformShareRatio ());
        supplierBusiness.setAreaShareRatio (supplierBusinessVo.getAreaShareRatio ());
        supplierBusiness = this.supplierBusinessDao.updateSupplierBusiness (result, supplierBusiness).getData ();
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (supplierBusiness);
        return result;
    }

    /**
     * 查询所有的业务
     *
     * @param result
     * @return
     * @author iritchie.ren
     */
    public Result findAllBusiness(Result result) throws Exception {
        List<BusinessInfo> businessInfoList = this.businessInfoDao.findAll (result).getData ();
        List<BusinessInfo> returnInfos = new ArrayList<BusinessInfo>();
        for(BusinessInfo businessInfo : businessInfoList){
        	if("DJDF".equals(businessInfo.getBusinessCode())){
        		returnInfos.add(businessInfo);
        	}
        	if("HCP".equals(businessInfo.getBusinessCode())){
        		returnInfos.add(businessInfo);
        	}
        	if("MPCZ".equals(businessInfo.getBusinessCode())){
                returnInfos.add(businessInfo);
            }
        }
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (returnInfos);
        return result;
    }

    /**
     * 获取父级服务
     *
     * @param result
     * @return
     * @Date 2016年10月14日<br>
     */
    public Result getBusinessInfoParent(Result result) throws Exception {
        List<BusinessInfo> businessInfoList = businessInfoDao.getBusinessInfoParent (result).getData ();
        result.setData (businessInfoList);
        return result;
    }

    /**
     * 根据父级获取子级服务
     *
     * @param result
     * @param parentCode
     * @return
     * @Date 2016年10月14日<br>
     */
    public Result getBusinessInfoByParentByParent(Result result, String parentCode) throws Exception {
        return businessInfoDao.getBusinessInfoByParent (result, parentCode);
    }
    
    
    public Result getByBusinessCode(Result result, String businessCode) throws Exception {
        return businessInfoDao.getByBusinessCode(result, businessCode);
    }
    
}
