package com.tsh.vas.sdm.service.supplier;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.exception.FunctionException;
import com.google.common.collect.Lists;
import com.tsh.broker.utils.DateUtils;
import com.tsh.broker.utils.HttpXmlClient;
import com.tsh.broker.utils.ObjectMapUtils;
import com.tsh.dubbo.bis.api.AreaApi;
import com.tsh.dubbo.bis.vo.AreaInfoVO;
import com.tsh.openpf.utils.ServRegisterSignUtils;
import com.tsh.openpf.vo.common.ResponseWrapper;
import com.tsh.openpf.vo.request.BizzServiceRequest;
import com.tsh.vas.dao.BusinessInfoDao;
import com.tsh.vas.dao.SupplierAreaBusinessDao;
import com.tsh.vas.dao.SupplierBusinessDao;
import com.tsh.vas.dao.SupplierInfoDao;
import com.tsh.vas.diamond.TshDiamondClient;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.enume.SupplierCheckStatus;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.SupplierAreaBusiness;
import com.tsh.vas.model.SupplierBusiness;
import com.tsh.vas.model.SupplierInfo;
import com.tsh.vas.utils.GenerateOrderNumber;
import com.tsh.vas.vo.supplier.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Iritchie.ren on 2016/9/19.
 */
@Service
public class SupplierService {
    
    private Logger logger = Logger.getLogger (getClass ());
    
    @Autowired
    private SupplierInfoDao supplierInfoDao;
    @Autowired
    private SupplierBusinessDao supplierBusinessDao;
    @Autowired
    private BusinessInfoDao businessInfoDao;
    @Autowired
    private SupplierAreaBusinessDao supplierAreaBusinessDao;
    @Autowired
    private AreaApi areaApi;

    /**
     * 根据供应商编码查询对象
     *
     * @param result
     * @param shopSupplierNo
     * @return
     * @throws Exception
     */
    public Result getObjectByShopSupplierNo(Result result, String shopSupplierNo) throws Exception {
        return supplierInfoDao.getObjectByShopSupplierNo (result, shopSupplierNo);
    }
    
    /**
     * 供应商入驻信息，入驻可服务的业务添加
     *
     * @param result
     * @param applyInfoVo
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result addSupplier(Result result, ApplyInfoVo applyInfoVo) throws Exception {
        logger.info (JSONObject.toJSONString (applyInfoVo));
        
        //获取供应商信息,保存供应商信息
        SupplierInfo supplierInfo = getSupplierInfo (applyInfoVo);
        supplierInfo = this.supplierInfoDao.add (result, supplierInfo).getData ();

        //向开放平台注册供应商
        BizzServiceRequest bizzServiceRequest = new BizzServiceRequest ();
        bizzServiceRequest.setBusinessId (supplierInfo.getSupplierCode ());
        bizzServiceRequest.setTimestamp (DateUtils.format (new Date (), "yyyy-MM-dd HH:mm:ss"));
        String signed = ServRegisterSignUtils.signCreateBusinessService (bizzServiceRequest, TshDiamondClient.getInstance ().getConfig ("openpf.signKey"));
        bizzServiceRequest.setSigned (signed);
        Map<String, String> map = ObjectMapUtils.toStringMap (bizzServiceRequest);
        String url = TshDiamondClient.getInstance ().getConfig ("openpf-web") + "/openpf/createBizzService.do";
        String response = HttpXmlClient.post (url, map);
        if (StringUtils.isEmpty (response)) {
            logger.error ("向开放注册供应商信息异常");
            throw new FunctionException (result, "向开放注册供应商信息异常");
        }
        ResponseWrapper responseWrapper = JSONObject.parseObject (response, ResponseWrapper.class);
        if (responseWrapper.getStatus () != 0) {
            logger.error ("向开放注册供应商信息异常为：" + responseWrapper.getMessage ());
            throw new FunctionException (result, responseWrapper.getMessage ());
        }

        //获取供应商服务业务信息
        List<SupplierBusiness> supplierBusinessList = getSupplierBusinesses (applyInfoVo, supplierInfo);
        //保存供应商服务业务范围
        this.supplierBusinessDao.addSupplierBusiness (result, supplierBusinessList).getData ();
        logger.info ("保存供应商服务业务范围成功");
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (supplierInfo);
        return result;
    }

    private SupplierInfo getSupplierInfo(ApplyInfoVo applyInfoVo) {
        String supplierCode = "ZZFW" + GenerateOrderNumber.getOrderNumber ();
        SupplierInfo supplierInfo = new SupplierInfo ();
        supplierInfo.setShopSupplierId (applyInfoVo.getShopSupplierId ());
        supplierInfo.setShopSupplierNo (applyInfoVo.getShopSupplierNo ());
        supplierInfo.setSupplierName (applyInfoVo.getSupplierName ());
        supplierInfo.setCompany (applyInfoVo.getCompany ());
        supplierInfo.setEmail (applyInfoVo.getEmail ());
        supplierInfo.setMobile (applyInfoVo.getMobile ());
        supplierInfo.setTelphone (applyInfoVo.getTelphone ());
        supplierInfo.setApplyExplain (applyInfoVo.getApplyExplain ());
        supplierInfo.setCreateTime (new Date ());
        supplierInfo.setCheckTime (new Date ());
        supplierInfo.setCheckStatus (SupplierCheckStatus.CHECK_PENDING.getCode ());
        supplierInfo.setSupplierCode (supplierCode);
        return supplierInfo;
    }

    /**
     * 查询供应商列表
     *
     * @param result
     * @param queryParamVo
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result findSuppliers(Result result, QuerySupplierVo queryParamVo) throws Exception {
        Pagination pagination = this.supplierInfoDao.query (result, queryParamVo).getData ();
        List<QuerySupplierResult> querySupplierResultList = getQuerySupplierResults (result, pagination);
        pagination.setRows (querySupplierResultList);
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (pagination);
        return result;
    }

    private List<QuerySupplierResult> getQuerySupplierResults(Result result, Pagination pagination) throws Exception {
        List<QuerySupplierResult> querySupplierResultList = Lists.newArrayList ();
        for (Object item : pagination.getRows ()) {
            SupplierInfo supplierInfo = (SupplierInfo) item;
            List<String> businessCodes = this.supplierBusinessDao.getBusinessesCodeBySupplierCode (result, supplierInfo.getSupplierCode ()).getData ();
            if (businessCodes.isEmpty ()) {
                continue;
            }
            List<BusinessInfo> businessInfoList = this.businessInfoDao.getByBusinessInfos (result, businessCodes).getData ();
            if (businessInfoList == null) {
                businessInfoList = Lists.newArrayList ();
            }
            QuerySupplierResult querySupplierResult = getQuerySupplierResult (supplierInfo, businessInfoList);
            querySupplierResultList.add (querySupplierResult);
        }
        return querySupplierResultList;
    }

    private QuerySupplierResult getQuerySupplierResult(SupplierInfo supplierInfo, List<BusinessInfo> businessInfoList) {
        List<String> businessNameList = Lists.newArrayList ();
        //获取供应商业务列表，拼接成“缴电费|缴水费”
        for (BusinessInfo businessInfo : businessInfoList) {
            businessNameList.add (businessInfo.getBusinessName ());
        }
        String businessNames = StringUtils.join (businessNameList.toArray (), "|");
        QuerySupplierResult querySupplierResult = new QuerySupplierResult ();
        querySupplierResult.setApplyExplain (supplierInfo.getApplyExplain ());
        querySupplierResult.setCheckStatus (supplierInfo.getCheckStatus ());
        querySupplierResult.setCheckTime (supplierInfo.getCheckTime ());
        querySupplierResult.setCompany (supplierInfo.getCompany ());
        querySupplierResult.setCreateTime (supplierInfo.getCreateTime ());
        querySupplierResult.setEmail (supplierInfo.getEmail ());
        querySupplierResult.setId (supplierInfo.getId ());
        querySupplierResult.setMobile (supplierInfo.getMobile ());
        querySupplierResult.setSupplierCode (supplierInfo.getSupplierCode ());
        querySupplierResult.setSupplierName (supplierInfo.getSupplierName ());
        querySupplierResult.setTelphone (supplierInfo.getTelphone ());
        querySupplierResult.setBusinessNames (businessNames);
        querySupplierResult.setSupplierAreaDesc ("...");
        return querySupplierResult;
    }

    /**
     * 获取供应商的服务区域
     *
     * @param result
     * @param supplierCode
     * @return
     * @author iritchie.ren
     */
    public Result findAreas(Result result, String supplierCode, String businessCode) throws Exception {
        List<SupplierAreaBusiness> supplierAreaBusinessList = this.supplierAreaBusinessDao.findBySupplierCodeAndBusinessCode (result, supplierCode, businessCode).getData ();
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (supplierAreaBusinessList);
        return result;
    }

    /**
     * 获取供应商的基本信息
     *
     * @param result
     * @param supplierCode
     * @return
     * @author iritchie.ren
     */
    public Result querySupplierInfo(Result result, String supplierCode) throws Exception {
        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, supplierCode).getData ();
        SupplierInfoVo supplierInfoVo = JSONObject.toJavaObject ((JSON) JSONObject.toJSON (supplierInfo), SupplierInfoVo.class);
        List<String> businessCodeList = this.supplierBusinessDao.getBusinessesCodeBySupplierCode (result, supplierInfo.getSupplierCode ()).getData ();
        List<String> businessNameList = Lists.newArrayList ();
        for (String item : businessCodeList) {
            BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, item).getData ();
            businessNameList.add (businessInfo.getBusinessName ());
        }
        String businessNamesStr = StringUtils.join (businessNameList.toArray (), "|");
        supplierInfoVo.setBusinesseNamesStr (businessNamesStr);
        supplierInfoVo.setBusinessCodes (businessCodeList);
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (supplierInfoVo);
        return result;
    }

    /**
     * 修改供应商的基本信息和可服务业务
     *
     * @param result
     * @param applyInfoVo
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result updateSupplierInfo(Result result, ApplyInfoVo applyInfoVo) throws Exception {
        //修改供应商信息
        SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, applyInfoVo.getSupplierCode ()).getData ();
        supplierInfo.setSupplierName (applyInfoVo.getSupplierName ());
        supplierInfo.setCompany (applyInfoVo.getCompany ());
        supplierInfo.setEmail (applyInfoVo.getEmail ());
        supplierInfo.setMobile (applyInfoVo.getMobile ());
        supplierInfo.setTelphone (applyInfoVo.getTelphone ());
        supplierInfo = this.supplierInfoDao.updateSupplierInfo (result, supplierInfo).getData ();

        //修改供应商下的服务列表先删除，再添加关系
        List<SupplierBusiness> supplierBusinessList = this.supplierBusinessDao.queryBySupplierCode (result, applyInfoVo.getSupplierCode ()).getData ();
        if (supplierBusinessList == null) {
            supplierBusinessList = Lists.newArrayList ();
        }
        List<SupplierBusiness> supplierBusinessFixableList = Lists.newArrayList ();
        if (!applyInfoVo.getBusinessCodes ().isEmpty ()) {
            supplierBusinessFixableList = this.supplierBusinessDao.queryBySupplierCodeInBusiness (result, applyInfoVo.getSupplierCode (), applyInfoVo.getBusinessCodes ()).getData ();
        }
        if (supplierBusinessFixableList == null) {
            supplierBusinessFixableList = Lists.newArrayList ();
        }

        delete:
        for (SupplierBusiness item : supplierBusinessList) {
            for (SupplierBusiness fix : supplierBusinessFixableList) {
                if (item.getBusinessCode ().equals (fix.getBusinessCode ())) {
                    continue delete;
                }
            }
            this.supplierBusinessDao.deleteBySupplierCodeAndBusinessCode (result, applyInfoVo.getSupplierCode (), item.getBusinessCode ());
        }

        //需要新增的服务业务
        List<SupplierBusiness> addSupplierBusinesses = Lists.newArrayList ();
        add:
        for (String businessCode : applyInfoVo.getBusinessCodes ()) {
            for (SupplierBusiness item : supplierBusinessFixableList) {
                if (item.getBusinessCode ().equals (businessCode)) {
                    continue add;
                }
            }
            SupplierBusiness supplierBusiness = new SupplierBusiness ();
            supplierBusiness.setSupplierCode (supplierInfo.getSupplierCode ());
            supplierBusiness.setCreateTime (new Date ());
            supplierBusiness.setBusinessCode (businessCode);
            addSupplierBusinesses.add (supplierBusiness);
        }
        this.supplierBusinessDao.addSupplierBusiness (result, addSupplierBusinesses).getData ();
        
        //根据改供应商编号和业务类型条件删除供应商业务区域表
        List<String> businessCodes = applyInfoVo.getBusinessCodes();
        if(!businessCodes.contains("DJDF")){
        	supplierAreaBusinessDao.deleteBySupplierCodeAndBusinessCode(result, applyInfoVo.getSupplierCode(), "DJDF");
        }
        if(!businessCodes.contains("HCP")){
        	supplierAreaBusinessDao.deleteBySupplierCodeAndBusinessCode(result, applyInfoVo.getSupplierCode(), "HCP");
        }
        if(!businessCodes.contains("MPCZ")){
        	supplierAreaBusinessDao.deleteBySupplierCodeAndBusinessCode(result, applyInfoVo.getSupplierCode(), "MPCZ");
        }
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (supplierInfo);
        return result;
    }

    private List<SupplierBusiness> getSupplierBusinesses(ApplyInfoVo applyInfoVo, SupplierInfo supplierInfo) {
        List<SupplierBusiness> supplierBusinessList = Lists.newArrayList ();
        for (String businessCode : applyInfoVo.getBusinessCodes ()) {
            SupplierBusiness supplierBusiness = new SupplierBusiness ();
            supplierBusiness.setSupplierCode (supplierInfo.getSupplierCode ());
            supplierBusiness.setCreateTime (new Date ());
            supplierBusiness.setBusinessCode (businessCode);
            supplierBusinessList.add (supplierBusiness);
        }
        return supplierBusinessList;
    }

    /**
     * 供应商审核状态修改
     *
     * @param result
     * @param supplierCode
     * @param checkStatus
     * @return
     * @author iritchie.ren
     */
    public Result checkoutSupplier(Result result, String supplierCode, Integer checkStatus) throws Exception {
        Integer ret = this.supplierInfoDao.updateCheckStatus (result, supplierCode, checkStatus).getData ();

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (ret);
        return result;
    }

    /**
     * 配置供应商业务的服务区域
     *
     * @param result
     * @param supplierBusinessAreaVo
     * @return
     * @author iritchie.ren
     */
    public Result businessAddArea(Result result, SupplierBusinessAreaVo supplierBusinessAreaVo) throws Exception {
        //全国供应商设置
        List<SupplierAreaBusiness> addSupplierAreaBusinesses = Lists.newArrayList ();
        for (String item : supplierBusinessAreaVo.getSupplierAreas ()) {
            if (item.equals ("-1")) {
                //先删除该供应商业务下的所有区域信息
                this.supplierAreaBusinessDao.deleteBySupplierCodeAndBusinessCode (result, supplierBusinessAreaVo.getSupplierCode (), supplierBusinessAreaVo.getBusinessCode ()).getData ();
                //全国县域的
                SupplierAreaBusiness supplierAreaBusiness = new SupplierAreaBusiness ();
                supplierAreaBusiness.setSupplierCode (supplierBusinessAreaVo.getSupplierCode ());
                supplierAreaBusiness.setBusinessCode (supplierBusinessAreaVo.getBusinessCode ());
                supplierAreaBusiness.setCountryCode (item + "");
                supplierAreaBusiness.setSupplierOrder (0);
                supplierAreaBusiness.setCreateTime (new Date ());
                supplierAreaBusiness.setProvince ("全国");
                supplierAreaBusiness.setCity ("全国");
                supplierAreaBusiness.setCountry ("全国");
                supplierAreaBusiness.setCountryName ("全国");
                addSupplierAreaBusinesses.add (supplierAreaBusiness);
                supplierAreaBusiness = supplierAreaBusinessDao.addSupplierAreaBusiness (result, supplierAreaBusiness).getData ();
                supplierAreaBusiness.setSupplierOrder (Integer.valueOf (String.valueOf (supplierAreaBusiness.getId ())));
                result.setCode (ResponseCode.OK_CODE.getCode ());
                result.setData (addSupplierAreaBusinesses);
                return result;
            }
        }

        //自定义县域供应商。供应商，服务业务，服务地区id,确定一条记录
        //供应商所有的服务区域
        List<SupplierAreaBusiness> supplierAreaBusinessList = this.supplierAreaBusinessDao.findBySupplierCodeAndBusinessCode (result, supplierBusinessAreaVo.getSupplierCode (), supplierBusinessAreaVo.getBusinessCode ()).getData ();
        if (supplierAreaBusinessList == null) {
            supplierAreaBusinessList = Lists.newArrayList ();
        }

        List<SupplierAreaBusiness> supplierAreaBusinessFixationList = Lists.newArrayList ();
        if (!supplierBusinessAreaVo.getSupplierAreas ().isEmpty ()) {
            supplierAreaBusinessFixationList = this.supplierAreaBusinessDao.findBySupplierCodeAndBusinessCodeInAreaId (result, supplierBusinessAreaVo.getSupplierCode (), supplierBusinessAreaVo.getBusinessCode (), supplierBusinessAreaVo.getSupplierAreas ()).getData ();
        }
        if (supplierAreaBusinessFixationList == null) {
            supplierAreaBusinessFixationList = Lists.newArrayList ();
        }

        //删除多余的服务区域
        delete:
        for (SupplierAreaBusiness item : supplierAreaBusinessList) {
            for (SupplierAreaBusiness fix : supplierAreaBusinessFixationList) {
                if (item.getCountryCode ().equals (fix.getCountryCode ())) {
                    continue delete;
                }
            }
            this.supplierAreaBusinessDao.deleteBySupplierCodeAndBusinessCodeAddCountryCode (result, supplierBusinessAreaVo.getSupplierCode (), supplierBusinessAreaVo.getBusinessCode (), item.getCountryCode ());
        }

        //需要新增的服务区域
        add:
        for (String areaId : supplierBusinessAreaVo.getSupplierAreas ()) {
            for (SupplierAreaBusiness item : supplierAreaBusinessFixationList) {
                if (item.getCountryCode ().equals (areaId)) {
                    continue add;
                }
            }
            SupplierAreaBusiness supplierAreaBusiness = new SupplierAreaBusiness ();
            supplierAreaBusiness.setSupplierCode (supplierBusinessAreaVo.getSupplierCode ());
            supplierAreaBusiness.setBusinessCode (supplierBusinessAreaVo.getBusinessCode ());
            supplierAreaBusiness.setCountryCode (String.valueOf (areaId));
            supplierAreaBusiness.setCreateTime (new Date ());
            result.setData (null);
            AreaInfoVO areaInfoVO = this.areaApi.getAreaInfo (result, Long.parseLong (areaId)).getData ();
            
            logger.info("调用areaApi.getAreaInfo 传递参数为：" + areaId + " 返回值为：" + areaInfoVO);
            supplierAreaBusiness.setProvince (areaInfoVO.getProvince ());
            supplierAreaBusiness.setCity (areaInfoVO.getCity ());
            supplierAreaBusiness.setCountry (areaInfoVO.getArea ());
            supplierAreaBusiness.setCountryName (areaInfoVO.getAreaName ());
            addSupplierAreaBusinesses.add (supplierAreaBusiness);
            supplierAreaBusiness = this.supplierAreaBusinessDao.addSupplierAreaBusiness(result, supplierAreaBusiness).getData();
            supplierAreaBusiness.setSupplierOrder (Integer.valueOf (String.valueOf (supplierAreaBusiness.getId ())));
        }
        
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (supplierAreaBusinessList);
        return result;
    }
    /**
     * 根据供应商编号查询业务编码
     * @param supplierCode
     * @return
     */
    public List<SupplierBusiness> getBusinessCodeBySupplierCode(String supplierCode){
    	return supplierBusinessDao.getBusinessCodeBySupplierCode(supplierCode);
    }
    
    
    public Result queryBySupplierCode(Result result, String supplierCode){
        return supplierInfoDao.queryBySupplierCode(result, supplierCode);
    }
    
    
    public Result queryBySupplierCodeAndBusinessCode(Result result, String supplierCode, String businessCode) throws Exception {
        return supplierBusinessDao.queryBySupplierCodeAndBusinessCode(result, supplierCode, businessCode);
    }
    
    
    public Result findByBusinessCode(Result result, String countryCode, String businessCode) {
        return supplierAreaBusinessDao.findByBusinessCode(result, countryCode, businessCode);
    }
}
