package com.tsh.vas.sdm.service.area;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Result;
import com.google.common.collect.Lists;
import com.tsh.vas.dao.BusinessInfoDao;
import com.tsh.vas.dao.BusinessStoreShareDao;
import com.tsh.vas.dao.SupplierAreaBusinessDao;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.BusinessStoreShare;
import com.tsh.vas.vo.area.AreaBusinessProfitVo;
import com.tsh.vas.vo.area.BusinessStoreShareVo;

/**
 * Created by Iritchie.ren on 2016/9/28.
 */
@Service
public class AreaService {

    @Autowired
    private SupplierAreaBusinessDao supplierAreaBusinessDao;
    @Autowired
    private BusinessStoreShareDao businessStoreShareDao;
    @Autowired
    private BusinessInfoDao businessInfoDao;

    /**
     * 查询县域的服务业务
     *
     * @param result
     * @param countryCode
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result queryBusinessByAreaProfit(Result result, String countryCode) throws Exception {
        //查询县域的所有县域供应商的业务信息
        List<HashMap<String, String>> businessCodeList = this.supplierAreaBusinessDao.queryBusinessByArea (result, countryCode).getData ();
        List<AreaBusinessProfitVo> areaBusinessProfitVoList = Lists.newArrayList ();
        for (HashMap<String, String> item : businessCodeList) {
            AreaBusinessProfitVo areaBusinessProfitVo = new AreaBusinessProfitVo ();
            BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, item.get ("businessCode")).getData ();
            BusinessStoreShare businessStoreShare = this.businessStoreShareDao.queryByCountryCodeAndBusinessCode (result, item.get ("countryCode"), item.get ("businessCode")).getData ();
            areaBusinessProfitVo.setCountryCode (item.get ("countryCode"));
            areaBusinessProfitVo.setCountryName (item.get ("countryName"));
            areaBusinessProfitVo.setBusinessCode (item.get ("businessCode"));
            areaBusinessProfitVo.setBusinessName (businessInfo.getBusinessName ());
            if (businessStoreShare != null) {
                areaBusinessProfitVo.setCountryShareRatio (businessStoreShare.getCountryShareRatio ());
                areaBusinessProfitVo.setStoreShareRatio (businessStoreShare.getStoreShareRatio ());
            } else {
                areaBusinessProfitVo.setCountryShareRatio (0.00);
                areaBusinessProfitVo.setStoreShareRatio (0.00);
            }
            areaBusinessProfitVoList.add (areaBusinessProfitVo);
        }
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (areaBusinessProfitVoList);
        return result;
    }

    /**
     * 修改县域，网点利润分成
     *
     * @param result
     * @param businessStoreShareVo
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result updateBusinessByAreaProfit(Result result, BusinessStoreShareVo businessStoreShareVo) throws Exception {
        BusinessStoreShare businessStoreShare = this.businessStoreShareDao.queryByCountryCodeAndBusinessCode (result, businessStoreShareVo.getCountryCode (), businessStoreShareVo.getBusinessCode ()).getData ();
        if (businessStoreShare == null) {
            businessStoreShare = new BusinessStoreShare ();
            businessStoreShare.setCountryCode (businessStoreShareVo.getCountryCode ());
            businessStoreShare.setBusinessCode (businessStoreShareVo.getBusinessCode ());
            businessStoreShare.setCountryName (businessStoreShareVo.getCountryName ());
        }
        businessStoreShare.setCountryShareRatio (businessStoreShareVo.getCountryShareRatio ());
        businessStoreShare.setStoreShareRatio (businessStoreShareVo.getStoreShareRatio ());
        this.businessStoreShareDao.updateOrAddBusinessStoreShare (result, businessStoreShare);

        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (businessStoreShare);
        return result;
    }
}
