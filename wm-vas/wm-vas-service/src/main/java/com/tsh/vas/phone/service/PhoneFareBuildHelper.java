package com.tsh.vas.phone.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.tsh.vas.model.phone.PhoneGoodsAreaPo;
import com.tsh.vas.model.phone.PhoneGoodsPo;
import com.tsh.vas.vo.phone.PhoneFareWaysVo;
import com.tsh.vas.vo.phone.PhoneGoodsAreaVo;
import com.tsh.vas.vo.phone.PhoneGoodsVo;
import com.tsh.vas.vo.phone.PhoneValueVo;

@Service
public class PhoneFareBuildHelper {
	
	/**
	 * 构建实体VO
	 * @param faceValue
	 * @return
	 */
	public PhoneValueVo getPhoneValueVo(Integer faceValue) {
		PhoneValueVo  phoneValueVo = new PhoneValueVo();
		phoneValueVo.setCreateTime(new Date());
		phoneValueVo.setValue(faceValue);
		return phoneValueVo;
	}
	
	/**
	 * 构建实体参数.
	 * @return
	 */
	public PhoneFareWaysVo getBuildParams(String companyId, 
			Integer faceValue, String saleRegionId, Integer publicStatus) {
		PhoneFareWaysVo phoneFareWaysVo = new PhoneFareWaysVo();
		phoneFareWaysVo.setCompany(companyId);
		phoneFareWaysVo.setFaceValue(faceValue);
		phoneFareWaysVo.setSaleRegion(saleRegionId);
		phoneFareWaysVo.setPublicStatus(publicStatus);
		return phoneFareWaysVo;
	}
	
	/**
	 * @param goodsId
	 * @param provinces -- 逗号分割的 '省名-省代码,'
	 * @return
	 */
	public List<PhoneGoodsAreaVo> getBuidParams2(Long goodsId, String provinces) {
		String[] arr = provinces.split(",");
		List<PhoneGoodsAreaVo> pgavs = new ArrayList<PhoneGoodsAreaVo>();
		for (String a : arr) {
			PhoneGoodsAreaVo pgav = new PhoneGoodsAreaVo();
			pgav.setGoodsId(goodsId);
			pgav.setAreaName(a.substring(0, a.indexOf('-')));
			pgav.setAreaId(a.substring(a.indexOf('-')+1));
			pgavs.add(pgav);
		}
		return pgavs;
	}
	
	/**
	 * @param goodsId
	 * @param provinces -- 逗号分割的 '省名-省代码,'
	 * @return 省代码字符串  "'1','2'"
	 */
	public String getBuidParams3(Long goodsId, String src) {
		String[] arr = src.split(",");
		StringBuilder sbr = new StringBuilder();
		for (String a : arr) {
			sbr.append("'");
			sbr.append(a.substring(a.indexOf("-")+1));
			sbr.append("'");
			sbr.append(",");
		}
		return sbr.substring(0, sbr.length() - 1).toString();
	}
	
	/**
	 * 将以逗号分隔的字符串转为添加'',如"a,b,c" => "'a','b','c'"
	 */
	public String Transfer(String src) {
		if (StringUtils.isEmpty(src))
			return "";
		String[] arr = src.split(",");
		StringBuilder sbr = new StringBuilder();
		for (String str : arr) {
			sbr.append("'");
			sbr.append(str);
			sbr.append("'");
			sbr.append(",");
		}
		return sbr.substring(0, sbr.length()-1).toString();
	}
	
	public String listTransfer(Collection<String> ll) {
		StringBuilder sbr = new StringBuilder();
		for (String s : ll) {
			sbr.append("'");
			sbr.append(s);
			sbr.append("'");
			sbr.append(",");
		}
		return sbr.substring(0, sbr.length() - 1);
	}
	
	public List<?> voToPo(List<?> list, Class<?> clazz) throws IllegalAccessException, 
	InvocationTargetException, NoSuchMethodException {
		if (clazz == null)
			throw new IllegalArgumentException("类型clzz"+clazz+"未定义");
		if (clazz.equals(PhoneGoodsAreaPo.class)) {
			return toPhoneGoodsAreaPoList((List<PhoneGoodsAreaVo>)list);
		} else if (clazz.equals(PhoneGoodsPo.class)) {
			return toPhoneGoodsPoList((List<PhoneGoodsVo>)list);
		}
		return null;
	}
	
	
	public List<?> poToVo(List<?> list, Class<?> clazz) throws IllegalAccessException,
				InvocationTargetException, NoSuchMethodException {
		if (clazz == null)
			throw new IllegalArgumentException("类型clzz"+clazz+"未定义");
		if (clazz.equals(PhoneGoodsVo.class)) {
			return toPhoneGoodsVoList((List<PhoneGoodsPo>)list);
		}
		return null;
	}
	
	private List<PhoneGoodsAreaPo> toPhoneGoodsAreaPoList(List<PhoneGoodsAreaVo> vos) throws IllegalAccessException,
	InvocationTargetException, NoSuchMethodException {
		List<PhoneGoodsAreaPo> pgavps = new ArrayList<PhoneGoodsAreaPo>();
		for (PhoneGoodsAreaVo pgav : vos) {
			PhoneGoodsAreaPo pgavp = new PhoneGoodsAreaPo();
			PropertyUtils.copyProperties(pgavp, pgav);
			pgavps.add(pgavp);
		}
		return pgavps;
	}
	
	private List<PhoneGoodsVo> toPhoneGoodsVoList(List<PhoneGoodsPo> pgps) throws IllegalAccessException,
		InvocationTargetException, NoSuchMethodException {
		List<PhoneGoodsVo> pgvs = new ArrayList<PhoneGoodsVo>();
		for (PhoneGoodsPo pgp : pgps) {
			PhoneGoodsVo pgv = new PhoneGoodsVo();
			PropertyUtils.copyProperties(pgv, pgp);
			pgvs.add(pgv);
		}
		return pgvs;
	}
	
	private List<PhoneGoodsPo> toPhoneGoodsPoList(List<PhoneGoodsVo> vos) throws IllegalAccessException,
	InvocationTargetException, NoSuchMethodException {
		List<PhoneGoodsPo> pgavps = new ArrayList<PhoneGoodsPo>();
		for (PhoneGoodsVo pgav : vos) {
			PhoneGoodsPo pgavp = new PhoneGoodsPo();
			PropertyUtils.copyProperties(pgavp, pgav);
			pgavps.add(pgavp);
		}
		return pgavps;
	}
	
}
