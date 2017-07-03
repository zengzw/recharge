package com.tsh.vas.sdm.test;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.phone.service.PhoneFareBuildHelper;
import com.tsh.vas.phone.service.PhoneGoodsAreaService;
import com.tsh.vas.phone.service.PhoneGoodsService;
import com.tsh.vas.phone.service.PhoneServiceProviderService;
import com.tsh.vas.phone.service.PhoneValueService;
import com.tsh.vas.vo.phone.PhoneFareWaysVo;
import com.tsh.vas.vo.phone.PhoneGoodsAreaVo;
import com.tsh.vas.vo.phone.PhoneGoodsVo;

/**
 * 话费充值方案设置测试类
 */
@Controller
@RequestMapping("/vas/phonefare/test/")
public class PhoneFareChargeController extends BaseController {
	
	private static final String DEL_MSG = "删除充值方案异常:";

	@Autowired
	private PhoneGoodsService phoneGoodsService;
	@Autowired
	private PhoneGoodsAreaService phoneGoodsAreaService;
	@Autowired
	private PhoneValueService  phoneValueService;
	@Autowired
	private PhoneServiceProviderService phoneServiceProviderService;
	@Autowired
	private PhoneFareBuildHelper phoneFareBuildHelper;

	/**
	 *
	 * @param phoneFareWayId
	 * @param publicStatus
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO modifyPublicStatus(Long phoneFareWayId, Integer publicStatus) {
		Result result = this.getResult();
		try {
			logger.info("修改话费充值方案<"+phoneFareWayId+">"+"状态:<"+publicStatus+">");
			PhoneGoodsVo pv = new PhoneGoodsVo();
			pv.setId(phoneFareWayId);
			pv.setStatus(publicStatus);
			result = phoneGoodsService.updatePhoneGoods(result, pv);
		} catch (Exception ex) {
			 logger.error ("修改发布状态异常:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg ("操作失败");
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 *
	 * @param request
	 * @param companyId
	 * @param faceValue
	 * @param saleRegionId
	 * @param publicStatus
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO findAllPhoneFareWaysList(HttpServletRequest request, String companyId, 
			Integer faceValue, String saleRegionId, Integer publicStatus, Integer page, Integer rows) {
		Result result = this.getResult();
		try {
			logger.info("查询话费充值方案列表,匹配条件 companyId:"+companyId+",faceValue:"+faceValue
					+",saleRegionId:"+saleRegionId+",publicStatus:"+publicStatus);
			PhoneFareWaysVo phoneFareWaysVo = phoneFareBuildHelper.getBuildParams(companyId, faceValue, 
					saleRegionId, publicStatus);
			result = phoneGoodsService.findPhoneGoodsList(result, phoneFareWaysVo, page, rows);
		} catch (Exception ex) {
			 logger.error ("获取手机话费充值方案列表异常:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg ("操作失败");
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO findPhoneFaceValues(HttpServletRequest request) {
		Result result = this.getResult();
		try {
			Page page = new Page();
			page.setPageSize(160);
			result = phoneValueService.queryPhoneValueList(result, page, null);
		} catch (Exception ex) {
			 logger.error ("获取手机话费面值方案列表异常:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg ("操作失败");
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 *
	 * @param request
	 * @param id
	 * @param faceValue
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ReturnDTO delWayById(HttpServletRequest request, Long id, String faceValue) {
		Result result = this.getResult();
		try {
			logger.info("触发删除面值id<"+id+">方案");
			//查询是否有该面值匹配的充值方案
			result = phoneGoodsService.getPhoneWaysCount(result, "", 
					faceValue, "", "");
			int count = result.getData();
			if (count > 0) {
				result.setMsg("请先删除对应面值已设置的区域方案");
				result.setStatus(Result.STATUS_ERROR);
				return result.DTO();
			}
			result = phoneValueService.deletePhoneValueById(result, id);
		} catch (Exception ex) {
			 logger.error (DEL_MSG + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg (ex.getMessage ());
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 *
	 * @param request
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ReturnDTO delPhoneFareWays(HttpServletRequest request, Long goodsId){
		Result result = this.getResult();
		try {
			logger.info("触发删除话费方案:<"+goodsId+">");
			result = phoneGoodsService.deletePhoneGoods(result, goodsId);
			int count = result.getData();
			if (count == 1) {
				result = phoneGoodsAreaService.deletePhoneGoodsAreaByGoodsId(result, goodsId);
				count += (int)result.getData();
			}
			if (count < 2) {
				result.setMsg("删除失败");
				result.setStatus(Result.STATUS_ERROR);
			} else {
				result.setMsg("删除成功");
			}
		} catch (Exception ex) {
			 logger.error (DEL_MSG + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg ("删除失败");
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 *
	 * @param request
	 * @param goodIds
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ReturnDTO batchDelPhoneFareWays(HttpServletRequest request, String goodIds){
		Result result = this.getResult();
		try {
			logger.info("触发删除话费方案:<"+goodIds+">");
			result = phoneGoodsService.deletePhoneGoods(result, goodIds);
			int count = result.getData();
			if (count > 0) {
				result = phoneGoodsAreaService.batchDeletePhoneGoodsAreaByGoodsId(result, goodIds);
				count += (int)result.getData();
			}
			if (count <= 0) {
				result.setMsg("删除失败");
				result.setStatus(Result.STATUS_ERROR);
			} else {
				result.setMsg("删除成功");
			}
		} catch (Exception ex) {
			 logger.error (DEL_MSG + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg ("删除失败");
		}finally {
			send(result);
		}
		return result.DTO();
	}


	/**
	 *
	 * @param faceValue
	 * @param goodIds
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody 
	public ReturnDTO batchUpdateSalePrice(Integer faceValue, String goodIds) {
		Result result = this.getResult();
		try {
			logger.info("话费充值方案<"+goodIds+">"+",同步:"+faceValue);
			PhoneGoodsVo vo = new PhoneGoodsVo();
			vo.setSaleAmount(new BigDecimal(faceValue));
			result = phoneGoodsService.updatePhoneGoodsByIds(result, vo, goodIds);
		} catch (Exception ex) {
			 logger.error ("批量同步销售价:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg (ex.getMessage ());
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 * 增加面值方案
	 * @param request
	 * @param faceValue
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody 
	public ReturnDTO addFaceValueWays(HttpServletRequest request, Integer faceValue)  {
		Result result = this.getResult();
		try {
			logger.info("添加面值方案 <"+faceValue+">");
			//判断是否存在该面值
			int count = phoneValueService.getPhoneValueByPhoneValue(result, faceValue).getData();
			if (count > 0) {
				result.setStatus(Result.STATUS_ERROR);
				result.setMsg("面值已经添加");
				return result.DTO();
			}
				
			result = phoneValueService.addPhoneValue(result, phoneFareBuildHelper.getPhoneValueVo(faceValue));
		} catch (Exception ex) {
			 logger.error ("添加面值方案异常:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg (ex.getMessage ());
		}finally {
			send(result);
		}
		return result.DTO();
	}


	/**
	 *
	 * @param companyIds
	 * @param faceValues
	 * @param saleRegionNames
	 * @param saleRegionIds
	 * @param relationShip
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO addSaleWays(String companyIds, String faceValues,
			String saleRegionNames, String saleRegionIds, String relationShip) {
		Result result = this.getResult();
		try {
			logger.info("添加话费销售方案,运营商id:"+companyIds+",面值faceValue:"+faceValues
					+",销售区域Name:"+saleRegionNames+",销售区域id:"+saleRegionIds);
			result = phoneGoodsService.getPhoneWaysCount(result, companyIds, faceValues, 
					saleRegionNames, saleRegionIds);
			int count = result.getData();
			if (count > 0) {
				result.setMsg("已存在同运营商、同面值的地区，不可重复添加");
				result.setStatus(Result.STATUS_ERROR);
				return result.DTO();
			}
			result = phoneGoodsService.addSaleWays(result, companyIds, faceValues, 
					saleRegionNames, saleRegionIds, relationShip);
		} catch (Exception ex) {
			 logger.error ("添加销售方案:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg (ex.getMessage ());
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 *
	 * @param request
	 * @param faceValue
	 * @param rows
	 * @param page
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO findAllWaysByfaceValue(HttpServletRequest request, String faceValue, Integer rows, Integer page) {
		Result result = this.getResult();
		try {
			logger.info("根据面值查询方案"+faceValue+",rows:"+rows+",page:"+page);
			PhoneFareWaysVo phoneFareWaysVo = phoneFareBuildHelper.getBuildParams(null, Integer.valueOf(faceValue.trim()), 
					null, -1);
			result = phoneGoodsService.findPhoneGoodsList(result, phoneFareWaysVo, page, rows);
		} catch (Exception ex) {
			 logger.error ("查询话费充值方案列表:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg ("操作失败");
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 *
	 * @param request
	 * @param goodId
	 * @param salePrice
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO updateSalePriceByFaceValue(HttpServletRequest request, Long goodId, String salePrice) {
		Result result = this.getResult();
		try {
			logger.info("更新销售价,商品id:"+goodId+",销售价:"+salePrice);
			PhoneGoodsVo phoneGoodsVo = new PhoneGoodsVo();
			phoneGoodsVo.setId(goodId);
			phoneGoodsVo.setSaleAmount(new BigDecimal(salePrice));
			result = phoneGoodsService.updatePhoneGoods(result, phoneGoodsVo);
		} catch (Exception ex) {
			 logger.error ("更新销售价:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg (ex.getMessage ());
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public ReturnDTO findCompanys(HttpServletRequest request) {
		Result result = this.getResult();
		try {
			Page page = new Page();
			result = phoneServiceProviderService.queryPhoneServiceProviderList(result, page, null);
		} catch (Exception ex) {
			 logger.error ("查询运营商列表:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg (ex.getMessage ());
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 *
	 * @param goodsId
	 * @param provinces
	 * @param delProvinces
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO addProvinces(Long goodsId, String provinces, String delProvinces) {
		Result result = this.getResult();
		try {
			logger.info("添加销售区域 goodsId:"+goodsId+",省份:"+provinces+",del:" + delProvinces);

			//添加省份
			if (!StringUtils.isEmpty(provinces)) {
				List<PhoneGoodsAreaVo> ll = phoneFareBuildHelper.getBuidParams2(goodsId, provinces);
				result = phoneGoodsAreaService.batchSavePhoneGoodsArea(result, ll);
			}
			//删除省份
			if (StringUtils.isNotEmpty(delProvinces)) {
				String params = phoneFareBuildHelper.getBuidParams3(goodsId, delProvinces);
				result = phoneGoodsAreaService.deletePhoneGoodsAreaByGoodsIdAndAreaId(result, goodsId, params);
			}
		} catch (Exception ex) {
			 logger.error ("添加销售区域:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg ("修改失败");
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ReturnDTO getPhoneFareChargeWays(HttpServletRequest request) {
		Result result = this.getResult();
		try {
			Page page = new Page();
			page.setPageSize(100);
			result = phoneValueService.queryPhoneValueList(result, page, null);
		} catch (Exception ex) {
			 logger.error ("获取手机话费面值方案列表异常:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg (ex.getMessage ());
             result.setMsg ("操作失败");
		}finally {
			send(result);
		}
		return result.DTO();
	}

	/**
	 * 批量修改发布状态
	 * @param status
	 * @param goodIds
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody 
	public ReturnDTO batchUpdatePublicStatus(Integer status, String goodIds) {
		Result result = this.getResult();
		try {
			logger.info("话费充值方案<"+goodIds+">"+",发布状态:"+status);
		    result = phoneGoodsService.batchUpdatePublicStatus(result, status, goodIds); 
		} catch (Exception ex) {
			 logger.error ("批量同步发布状态:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg (ex.getMessage ());
		}finally {
			send(result);
		}
		return result.DTO();
	}
	
}
