package com.tsh.vas.sdm.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dtds.platform.util.bean.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.excel.ExcelUtil;
import com.tsh.vas.commoms.enums.PhoneFareWaysEnum;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.enume.ResponseCode;
import com.tsh.vas.phone.service.PhoneFareBuildHelper;
import com.tsh.vas.phone.service.PhoneGoodsService;
import com.tsh.vas.vo.phone.PhoneFareWaysVo;

/**
 * 话费设置controller
 */
@Controller
@RequestMapping("/vas/phonefare")
public class PhoneFareReportController extends BaseController {

	private static final String SALE_REGION = "saleRegion";

	@Autowired
	private PhoneGoodsService phoneGoodsService;
	
	@Autowired
	PhoneFareBuildHelper phoneFareBuildHelper;

	/**
	 *
	 * @param request
	 * @param companyId
	 * @param faceValue
	 * @param saleRegionId
	 * @param publicStatus
	 * @param page
	 * @param rows
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public void exportPhoneFareWaysReports(HttpServletRequest request, String companyId, 
			Integer faceValue, String saleRegionId, Integer publicStatus, Integer page, Integer rows) {
		Result result = this.getResult();
		try {
			PhoneFareWaysVo phoneFareWaysVo = phoneFareBuildHelper.getBuildParams(companyId, faceValue, 
					saleRegionId, publicStatus);
			result = phoneGoodsService.findPhoneGoodsList(result, phoneFareWaysVo, page, rows);
			String[] fields = {"company","faceValue","salePrice",SALE_REGION,"publicStatusName"};
		    String[] titles = {"运营商","面值", "销售价", "销售区域", "发布状态"};
		    String head = "话费充值方案报表";
		    List<PhoneFareWaysVo> phoneFareWaysVos = transferToList((Pagination) result.getData());
            File file = ExcelUtil.export (null, head, fields, titles, phoneFareWaysVos, null);
            downloadExcel(request, response, file, head);
		} catch (Exception ex) {
			 logger.error ("话费充值方案报表导出:" + result.getMsg (),ex);
             result.setStatus (200);
             result.setCode (ResponseCode.DEFAULT_ERROR_CODE.getCode ());
             result.setMsg (ex.getMessage ());
		}finally {
			send(result);
		}
	}

	/**
	 * 转换为list
	 * @param pagination
	 * @return
	 */
	private List<PhoneFareWaysVo> transferToList(Pagination pagination) {
		List<PhoneFareWaysVo> ll = new ArrayList<PhoneFareWaysVo>();
		List<Map<String, Object>> mapList = (List<Map<String, Object>>) pagination.getRows();
		for (Map<String, Object> m : mapList) {
			PhoneFareWaysVo phoneFareWaysVo = new PhoneFareWaysVo();
			phoneFareWaysVo.setCompany(m.get("company").toString());
			phoneFareWaysVo.setFaceValue((Integer)m.get("faceValue"));
			phoneFareWaysVo.setSalePrice(m.get("salePrice").toString());
			if (((byte)m.get("publicStatus")) == (byte)PhoneFareWaysEnum.NORMAL.getStatus())
				phoneFareWaysVo.setPublicStatusName("已发布");		
			else if(((byte)m.get("publicStatus")) == (byte)PhoneFareWaysEnum.DEL.getStatus()) {
				phoneFareWaysVo.setPublicStatusName("未发布");		
			}
			phoneFareWaysVo.setSaleRegion(m.get(SALE_REGION) == null ?"" : m.get(SALE_REGION).toString());
			ll.add(phoneFareWaysVo);
		} 
		return ll;
	}
}
