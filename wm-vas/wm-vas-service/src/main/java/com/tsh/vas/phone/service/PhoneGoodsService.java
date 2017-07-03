package com.tsh.vas.phone.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.commoms.enums.PhoneFareWaysEnum;
import com.tsh.vas.dao.phone.PhoneGoodsDao;
import com.tsh.vas.model.phone.PhoneGoodsPo;
import com.tsh.vas.model.phone.PhoneServiceProviderPo;
import com.tsh.vas.vo.phone.PhoneFareWaysVo;
import com.tsh.vas.vo.phone.PhoneGoodsAreaVo;
import com.tsh.vas.vo.phone.PhoneGoodsVo;


@Service
@SuppressWarnings("all")
public class PhoneGoodsService {
    @Autowired
    private PhoneGoodsDao phoneGoodsDao;

    @Autowired
    PhoneFareBuildHelper phoneFareBuildHelper;
    
    @Autowired
    PhoneServiceProviderService serviceProviderService;
    @Autowired
    PhoneGoodsAreaService phoneGoodsAreaService;
    
   
    /**
     * 新增
     * @param result
     * @param phoneGoods
     * @return
     */
    public Result addPhoneGoods(Result result,PhoneGoodsVo phoneGoodsVo)throws Exception{
        PhoneGoodsPo phoneGoodsPo = new PhoneGoodsPo();

        if (phoneGoodsVo != null) {
            BeanUtils.copyProperties(phoneGoodsPo, phoneGoodsVo);
        }

        result = phoneGoodsDao.addPhoneGoods(result,phoneGoodsPo);
        return result;
    }





    /**
     * 批量新增
     * @param result
     * @param phoneGoods
     * @return
     */
    public Result batchSavePhoneGoods(Result result, List<PhoneGoodsVo> phoneGoods_list) throws Exception {
    	List<PhoneGoodsPo> list = (List<PhoneGoodsPo>) phoneFareBuildHelper.voToPo(phoneGoods_list, PhoneGoodsPo.class);
        result = phoneGoodsDao.batchSavePhoneGoods(result,list);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneGoods(Result result, Long id) throws Exception {
        result = phoneGoodsDao.deletePhoneGoods(result,id);
        return result;
    }

    /**
     * 批量删除
     * @param ids 标识 以逗号分割的字符串 如 "'1','3'"
     * @return
     */
    public Result deletePhoneGoods(Result result, String ids) throws Exception {
        result = phoneGoodsDao.batchDeletePhoneGoods(result,ids);
        return result;
    }

    /**
     * 批量删除
     * @param result
     * @param phoneGoods
     * @return
     */
    public Result batchDelPhoneGoods(Result result, List<PhoneGoodsVo> phoneGoods_list)throws Exception{
        List<PhoneGoodsPo> list = new ArrayList<PhoneGoodsPo>(); 
        phoneGoodsDao.batchDelete(list);
        return result;
    }


    /**
     * 批量删除ByIds
     * @param result
     * @param phoneGoods
     * @return
     */
    public Result batchDelPhoneGoodsByIds(Result result,Integer[] ids)throws Exception{
        phoneGoodsDao.batchDelPhoneGoodsByIds(result,ids);
        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneGoodsList(Result result,Page page,PhoneGoodsVo phoneGoodsVo){
        PhoneGoodsPo phoneGoodsPo = new PhoneGoodsPo();
        result = phoneGoodsDao.queryPhoneGoodsList(result,page,phoneGoodsPo);
        return result;
    }


    /**
     * 根据条件获取 列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneGoodsList(Result result,Page page,PhoneGoodsVo phoneGoodsVo,UserInfo user){
        PhoneGoodsPo phoneGoodsPo = new PhoneGoodsPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = phoneGoodsDao.queryPhoneGoodsList(result,page,phoneGoodsPo);
        return result;
    }




    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneGoodsById(Result result,long id) throws Exception{
        result = phoneGoodsDao.getPhoneGoodsById(result,id);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhoneGoods(Result result,PhoneGoodsVo phoneGoodsVo) throws Exception {
        long id = phoneGoodsVo.getId();
        result = phoneGoodsDao.getPhoneGoodsById(result,id);
        PhoneGoodsPo phoneGoodsPo  = (PhoneGoodsPo)result.getData();
        if (phoneGoodsPo != null) {
            if(phoneGoodsVo.getSupplierCode()!=null){
                phoneGoodsPo.setSupplierCode(phoneGoodsVo.getSupplierCode());
            }
            if(phoneGoodsVo.getPhoneValue() !=null ){
                phoneGoodsPo.setPhoneValue(phoneGoodsVo.getPhoneValue());
            }
            if(phoneGoodsVo.getSaleAmount()!=null){
                phoneGoodsPo.setSaleAmount(phoneGoodsVo.getSaleAmount());
            }
            if(phoneGoodsVo.getStatus() !=null){
                phoneGoodsPo.setStatus(phoneGoodsVo.getStatus());
            }
            if(phoneGoodsVo.getCreater()!=null){
                phoneGoodsPo.setCreater(phoneGoodsVo.getCreater());
            }

            if(phoneGoodsVo.getModifyTime()!=null){
                phoneGoodsPo.setModifyTime(phoneGoodsVo.getModifyTime());
            }
            if(phoneGoodsVo.getRemark()!=null){
                phoneGoodsPo.setRemark(phoneGoodsVo.getRemark());
            }
        }
        return result;
    }
    
    /**
     * 批量更新某个设置的字段
     * @param result
     * @param phoneGoodsVo -- 更新的字段实体
     * @param ids  -- 主键id
     * @return
     * @throws Exception
     */
    public Result updatePhoneGoodsByIds(Result result,PhoneGoodsVo phoneGoodsVo, String ids) throws Exception {
    	PhoneGoodsPo po = new PhoneGoodsPo();
    	PropertyUtils.copyProperties(po, phoneGoodsVo);
    	return phoneGoodsDao.batchUpdate(result, po, ids);
    }
    
    /**
     * 查询话费充值方案列表
     * @param result
     * @param phoneFareWaysVo
     * @param page
     * @param pageSize
     * @return
     */
    public Result findPhoneGoodsList(Result result, PhoneFareWaysVo phoneFareWaysVo,
    		Integer page, Integer pageSize) {
    	return phoneGoodsDao.findPhoneFareWays(result, phoneFareWaysVo.getCompany(), 
    			phoneFareWaysVo.getFaceValue(),phoneFareWaysVo.getSaleRegion(), phoneFareWaysVo.getPublicStatus(), 
    			page, pageSize);
    }
    
    /**
     * 匹配话费充值方案
     * @param result
     * @param companyIds
     * @param faceValues
     * @param saleRegionNames
     * @param saleRegionIds
     * @return
     */
    public Result getPhoneWaysCount(Result result, String companyIds, String faceValues,
			String saleRegionNames, String saleRegionIds) {
    	result = phoneGoodsDao.getPhoneWaysCount(result, phoneFareBuildHelper.Transfer(companyIds), 
    			phoneFareBuildHelper.Transfer(faceValues), phoneFareBuildHelper.Transfer(saleRegionNames), 
    			phoneFareBuildHelper.Transfer(saleRegionIds));
    	return result;
    }
    
    /**
     * 
     * @param result
     * @param value - 面值,以逗号分割的字符串 如 "'a','b'"
     * @param code - 运营商代码 以逗号分割的字符串 "'code0','code1'"
     * @return
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public Result getPhoneGoodsByValueAndCode(Result result, String value, String code) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {	
    	List<PhoneGoodsPo> pgvs = phoneGoodsDao.getPhoneGoodsByValueAndCode(result, value, code).getData();
    	result.setData(phoneFareBuildHelper.poToVo((List<PhoneGoodsPo>)pgvs, PhoneGoodsVo.class));
    	return result;
    }
    
    /**
     * 添加话费充值方案 
     * @param relationShip  销售区域-销售区域id 映射字符串
     * @throws Exception 
     */
    public Result addSaleWays_OLD(Result result, String companyIds, String faceValues,
			String saleRegionNames, String saleRegionIds, String relationShip) throws Exception {
    	//查询运营商信息 companyIds
    	result = serviceProviderService.batchQueryPhoneServiceProviderByIds(result, phoneFareBuildHelper.Transfer(companyIds));
    	List<PhoneServiceProviderPo> pspps = result.getData();
    	Map<Long, String> supportMap = new HashMap<Long, String>();
    	for (PhoneServiceProviderPo ps : pspps) {
    		supportMap.put(ps.getId(), ps.getProviderCode());
    	}
    	
    	//添加话费方案
    	//判断该营销方案是否存在 根据运营商code 和 面值
    	result = this.getPhoneGoodsByValueAndCode(result, phoneFareBuildHelper.Transfer(faceValues), 
    			phoneFareBuildHelper.listTransfer(supportMap.values()));
    	List<PhoneGoodsVo> pgvs = result.getData();
    	if (pgvs.size() == 0) {
    	//每次添加充值销售方案,都会生成对应运营商、面值和销售区域的
		UserInfo userInfo = result.getUserInfo();
    	String[] arr = faceValues.split(",");
    	pgvs = new ArrayList<PhoneGoodsVo>();
    	//方案总记录计算方式: 面值总数 * 运营商总数
    	for (String code : supportMap.values()) {
	    	for (String a : arr) {
	    		PhoneGoodsVo pgv = new PhoneGoodsVo();
	    		pgv.setPhoneValue(Integer.valueOf(a));
	    		Date date = new Date();
	    		pgv.setCreateTime(date);
	    		pgv.setModifyTime(date);
	    		pgv.setSaleAmount(new BigDecimal(a));
	    		pgv.setCreateId(Integer.valueOf(userInfo.getUserId().toString()));
	    		pgv.setCreater(userInfo.getUserName());
	    		//默认未发布
	    		pgv.setStatus(PhoneFareWaysEnum.DEL.getStatus());
	    		pgv.setSupplierCode(code);
	    		pgvs.add(pgv);
	    	}
    	}
    	result = this.batchSavePhoneGoods(result, pgvs);
    	pgvs = (List<PhoneGoodsVo>) phoneFareBuildHelper.poToVo((List<PhoneGoodsPo>)result.getData(), PhoneGoodsVo.class);
    	} else {
    		StringBuilder sbr = new StringBuilder();
    		for (PhoneGoodsVo pgv : pgvs) {
    			sbr.append(pgv.getId());
    			sbr.append(",");
    		}
    		phoneGoodsDao.batchUpdate(result, PhoneFareWaysEnum.DEL.getStatus(), sbr.substring(0, sbr.length()-1).toString());
    	}
    	//添加方案销售区域
    	//总的销售区域记录数 = 总的充值方案记录 * 总的销售地区记录
    	Map<String, String> map = new HashMap<String, String>();
    	String[] relationShipArr = relationShip.split(",");
    	for (String a : relationShipArr) {
    		map.put(a.substring(a.indexOf("-")+1), a.substring(0, a.indexOf("-")));
    	}
    	List<PhoneGoodsAreaVo> pgavs = new ArrayList<PhoneGoodsAreaVo>();
    	for (Map.Entry<String, String> entry : map.entrySet()) {
    		for (PhoneGoodsVo pgv : pgvs) {
    			PhoneGoodsAreaVo pgav = new PhoneGoodsAreaVo();
    			pgav.setAreaId(entry.getKey());
    			pgav.setAreaName(entry.getValue());
    			pgav.setGoodsId(pgv.getId());
    			pgavs.add(pgav);
        	}
    	}
    	result = phoneGoodsAreaService.batchSavePhoneGoodsArea(result, pgavs);
    	result.setData(null);
    	return result;
    }
    
    /**
     * 添加话费充值方案 
     * @param relationShip  销售区域-销售区域id 映射字符串
     * @throws Exception 
     */
    public Result addSaleWays(Result result, String companyIds, String faceValues,
			String saleRegionNames, String saleRegionIds, String relationShip) throws Exception {
    	//查询运营商信息 companyIds
    	result = serviceProviderService.batchQueryPhoneServiceProviderByIds(result, phoneFareBuildHelper.Transfer(companyIds));
    	List<PhoneServiceProviderPo> pspps = result.getData();
    	Map<Long, String> supportMap = new HashMap<Long, String>();
    	for (PhoneServiceProviderPo ps : pspps) {
    		supportMap.put(ps.getId(), ps.getProviderCode());
    	}
    	
    	//添加话费方案
    	//每次添加充值销售方案,都会生成对应运营商、面值和销售区域的
		UserInfo userInfo = result.getUserInfo();
    	String[] arr = faceValues.split(",");
    	List<PhoneGoodsVo> pgvs = new ArrayList<PhoneGoodsVo>();
    	//方案总记录计算方式: 面值总数 * 运营商总数
    	for (String code : supportMap.values()) {
	    	for (String a : arr) {
	    		PhoneGoodsVo pgv = new PhoneGoodsVo();
	    		pgv.setPhoneValue(Integer.valueOf(a));
	    		Date date = new Date();
	    		pgv.setCreateTime(date);
	    		pgv.setModifyTime(date);
	    		pgv.setSaleAmount(new BigDecimal(a));
	    		pgv.setCreateId(Integer.valueOf(userInfo.getUserId().toString()));
	    		pgv.setCreater(userInfo.getUserName());
	    		//默认未发布
	    		pgv.setStatus(PhoneFareWaysEnum.DEL.getStatus());
	    		pgv.setSupplierCode(code);
	    		pgvs.add(pgv);
	    	}
    	}
    	
    	//添加方案销售区域
    	//总的销售区域记录数 = 总的充值方案记录 * 总的销售地区记录
    	Map<String, String> map = new HashMap<String, String>();
    	String[] relationShipArr = relationShip.split(",");
    	for (String a : relationShipArr) {
    		map.put(a.substring(a.indexOf("-")+1), a.substring(0, a.indexOf("-")));
    	}
    	List<PhoneGoodsAreaVo> pgavs = new ArrayList<PhoneGoodsAreaVo>();
    	for (PhoneGoodsVo pgv : pgvs) {
			PhoneGoodsPo phoneGoods = new PhoneGoodsPo();
			PropertyUtils.copyProperties(phoneGoods, pgv);
			phoneGoodsDao.addPhoneGoods(result, phoneGoods).getData();

    		for (Map.Entry<String, String> entry : map.entrySet()) {

    			PhoneGoodsAreaVo pgav = new PhoneGoodsAreaVo();
    			pgav.setAreaId(entry.getKey());
    			pgav.setAreaName(entry.getValue());
    			pgav.setGoodsId(phoneGoods.getId());
    			pgavs.add(pgav);
        	}
    		phoneGoodsAreaService.batchSavePhoneGoodsArea(result, pgavs);
    		//清空列表
    		pgavs.clear();
    		result.setData(null);
    	}
    	return result;
    }
    
    
    /**
     * 
     * @param status -- 发布状态
     * @param goodIds -- 以逗号分割的id字符串
     * @return
     */
    public Result batchUpdatePublicStatus(Result result, Integer status, String goodIds) {
    	return phoneGoodsDao.batchUpdate(result, status, goodIds);
    }
    
    
    
    public Result getAvailablePhoneGoodsById(Result result,Long id) throws Exception{
        return phoneGoodsDao.getAvailablePhoneGoodsById(result, id);
    }
    
    
    public Result queryListGoodsByAreaIdAndProviceCode(Result result,Integer areaId,String providerCode){
        return phoneGoodsDao.queryListGoodsByAreaIdAndProviceCode(result, areaId, providerCode);
    }
    
    
}

