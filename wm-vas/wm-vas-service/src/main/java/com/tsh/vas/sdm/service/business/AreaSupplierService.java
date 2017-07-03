package com.tsh.vas.sdm.service.business;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.google.common.collect.Lists;
import com.tsh.vas.dao.BusinessInfoDao;
import com.tsh.vas.dao.SupplierAreaBusinessDao;
import com.tsh.vas.dao.SupplierInfoDao;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.model.BusinessInfo;
import com.tsh.vas.model.SupplierAreaBusiness;
import com.tsh.vas.model.SupplierInfo;
import com.tsh.vas.vo.business.BusinessAreaDetailsVo;
import com.tsh.vas.vo.business.QueryAreaParam;
import com.tsh.vas.vo.business.SupplierOrderParamVo;

/**
 * Created by Iritchie.ren on 2016/9/23.
 */
@Service
public class AreaSupplierService {

    private Logger logger = Logger.getLogger (getClass ());

    @Autowired
    private SupplierAreaBusinessDao supplierAreaBusinessDao;
    @Autowired
    private SupplierInfoDao supplierInfoDao;
    @Autowired
    private BusinessInfoDao businessInfoDao;

    /**
     * 查询服务业务的服务县域列表
     *
     * @param result
     * @param queryAreaParam
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result findBusinessAreas(Result result, QueryAreaParam queryAreaParam) throws Exception {
        Pagination pagination = this.supplierAreaBusinessDao.findBusinessAreasByParam (result, queryAreaParam).getData ();
        List<BusinessAreaDetailsVo> businessAreaDetailses = Lists.newArrayList ();
        for (Object item : pagination.getRows ()) {
            SupplierAreaBusiness supplierAreaBusiness = (SupplierAreaBusiness) item;
            BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, supplierAreaBusiness.getBusinessCode ()).getData ();
            String businessName = businessInfo.getBusinessName ();
            BusinessAreaDetailsVo businessAreaDetailsVo = new BusinessAreaDetailsVo ();
            businessAreaDetailsVo.setBusinessCode (supplierAreaBusiness.getBusinessCode ());
            businessAreaDetailsVo.setBusinessName (businessName);
            businessAreaDetailsVo.setProvince (supplierAreaBusiness.getProvince ());
            businessAreaDetailsVo.setCity (supplierAreaBusiness.getCity ());
            businessAreaDetailsVo.setCountry (supplierAreaBusiness.getCountry ());
            businessAreaDetailsVo.setCountryCode (supplierAreaBusiness.getCountryCode ());
            businessAreaDetailsVo.setCountryName (supplierAreaBusiness.getCountryName ());
            List<SupplierAreaBusiness> supplierAreaBusinessList = this.supplierAreaBusinessDao.findByBusinessCodeAndCountryCode (result, supplierAreaBusiness.getBusinessCode (), supplierAreaBusiness.getCountryCode ()).getData ();
            List<String> suppliers = Lists.newArrayList ();
            for (SupplierAreaBusiness supplierAreaBusiness1 : supplierAreaBusinessList) {
                SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, supplierAreaBusiness1.getSupplierCode ()).getData ();
                suppliers.add (supplierAreaBusiness1.getSupplierOrder () + "." + supplierInfo.getCompany ());
            }
            String supplierStr = StringUtils.join (suppliers.toArray (), "|");
            businessAreaDetailsVo.setSupplierStr (supplierStr);
            businessAreaDetailses.add (businessAreaDetailsVo);
        }
        result.setCode (ResponseCode.OK_CODE.getCode ());
        pagination.setRows (businessAreaDetailses);
        result.setData (pagination);
        return result;
    }

    /**
     * 查询县域的供应商详情列表
     *
     * @param result
     * @param businessCode
     * @param countryCode
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result queryBusinessAreaDetails(Result result, String businessCode, String countryCode) throws Exception {
        List<SupplierAreaBusiness> supplierAreaBusinessList = this.supplierAreaBusinessDao.findByBusinessCodeAndCountryCode (result, businessCode, countryCode).getData ();
        //通过第一个参数获取共有的信息。
        SupplierAreaBusiness supplierAreaBusinessTemp = supplierAreaBusinessList.get (0);
        BusinessAreaDetailsVo businessAreaDetailsVo = new BusinessAreaDetailsVo ();
        BusinessInfo businessInfo = this.businessInfoDao.getByBusinessCode (result, supplierAreaBusinessTemp.getBusinessCode ()).getData ();
        String businessName = businessInfo.getBusinessName ();
        businessAreaDetailsVo.setBusinessCode (supplierAreaBusinessTemp.getBusinessCode ());
        businessAreaDetailsVo.setProvince (supplierAreaBusinessTemp.getProvince ());
        businessAreaDetailsVo.setCity (supplierAreaBusinessTemp.getCity ());
        businessAreaDetailsVo.setCountryCode (supplierAreaBusinessTemp.getCountryCode ());
        businessAreaDetailsVo.setCountryName (supplierAreaBusinessTemp.getCountryName ());
        businessAreaDetailsVo.setBusinessName (businessName);

        List<BusinessAreaDetailsVo.Supplier> supplierList = Lists.newArrayList ();
        for (SupplierAreaBusiness item : supplierAreaBusinessList) {
            BusinessAreaDetailsVo.Supplier supplier = new BusinessAreaDetailsVo.Supplier ();
            SupplierInfo supplierInfo = this.supplierInfoDao.queryBySupplierCode (result, item.getSupplierCode ()).getData ();
            String company = supplierInfo.getCompany ();
            supplier.setSupplierCode (item.getSupplierCode ());
            supplier.setCompany (company);
            supplier.setSupplierOrder (item.getSupplierOrder ());
            supplier.setId (item.getId ());
            supplierList.add (supplier);
        }
        businessAreaDetailsVo.setSuppliers (supplierList);
        result.setCode (ResponseCode.OK_CODE.getCode ());
        result.setData (businessAreaDetailsVo);
        return result;
    }

    /**
     * 修改供应商在县域下的排序
     *
     * @param result
     * @param supplierOrderParamVos
     * @return
     * @throws Exception
     * @author iritchie.ren
     */
    public Result updateSupplierOrder(Result result, List<SupplierOrderParamVo> supplierOrderParamVos) throws Exception {
        List<SupplierAreaBusiness> supplierAreaBusinessList = Lists.newArrayList ();
        for (SupplierOrderParamVo item : supplierOrderParamVos) {
            SupplierAreaBusiness supplierAreaBusiness = this.supplierAreaBusinessDao.queryByBusinessCodeAndCountryCodeAndSupplierCode (result, item).getData ();
            supplierAreaBusiness.setSupplierOrder (item.getSupplierOrder ());
            supplierAreaBusinessList.add (supplierAreaBusiness);
        }
        this.supplierAreaBusinessDao.updateOrder (result, supplierAreaBusinessList);
        result.setData (supplierAreaBusinessList);
        return result;
    }

    /**
     * 上移
     *
     * @param result
     * @param id
     * @param sortField
     * @param sortFieldUp
     * @return
     */   
    public Result moveUp(Result result, Long id, Integer sortField, Integer sortFieldUp) {
        SupplierAreaBusiness supplierAreaBusiness = supplierAreaBusinessDao.getGovernmentBySortField (result, sortFieldUp).getData ();
        supplierAreaBusiness.setSupplierOrder (sortField);
        //根据主键id更新排序字段
        supplierAreaBusinessDao.updateSortFieldById (result, id, sortFieldUp);
        return result;
    }

    /**
     * 下移
     *
     * @param result
     * @param id
     * @param sortField
     * @param sortFieldDown
     */
    public Result moveDown(Result result, Long id, Integer sortField, Integer sortFieldDown) {
        SupplierAreaBusiness supplierAreaBusiness = supplierAreaBusinessDao.getGovernmentBySortField (result, sortFieldDown).getData ();
        supplierAreaBusiness.setSupplierOrder (sortField);
        //根据主键id更新排序字段
        supplierAreaBusinessDao.updateSortFieldById (result, id, sortFieldDown);
        return result;
    }
}
