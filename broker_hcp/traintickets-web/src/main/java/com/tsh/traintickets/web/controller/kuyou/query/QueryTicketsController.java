package com.tsh.traintickets.web.controller.kuyou.query;

import com.alibaba.fastjson.JSON;
import com.traintickets.common.TicketsConstants;
import com.traintickets.common.TrainType;
import com.tsh.traintickets.bo.kuyou.queryTicket.QueryTicketsBO;
import com.tsh.traintickets.bo.kuyou.queryTicket.TrainData;
import com.tsh.traintickets.dal.service.kuyou.KuyouService;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsEnums;
import com.tsh.traintickets.dal.service.kuyou.httphandler.config.DynamicParamsUtils;
import com.tsh.traintickets.vo.convert.ConvertRequest2BO;
import com.tsh.traintickets.vo.convert.Convert2VO;
import com.tsh.traintickets.vo.response.QueryTicketsResponse;
import com.tsh.traintickets.vo.sign.KuyouRequestSign;
import com.tsh.traintickets.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dtds.platform.util.bean.Result;
import com.traintickets.common.BizException;
import com.tsh.traintickets.vo.BaseResponse;
import com.tsh.traintickets.vo.request.QueryTicketsRequest;
import com.tsh.traintickets.vo.ResponseBuilder;
import java.util.*;

/**
 * 查询余票接口
 * @author zhoujc
 *
 */
@Controller
@RequestMapping("/traintickets/kuyou")
public class QueryTicketsController extends BaseController {
	
	@Autowired
	KuyouService kuyouService;

	private static final String fromSatationListKey = "fromSatationList";
	private static final String arriveSatationListKey = "arriveSatationList";
	private static final String trianTypeListKey = "trianTypeList";

	@RequestMapping(value = "/queryTickets.do", method = RequestMethod.POST )
    @ResponseBody
	public BaseResponse queryTickets(QueryTicketsRequest request) {
		Result result = super.getResult ();
		try{
			this.validateParam(request);

			QueryTicketsBO queryTicketResult = kuyouService.queryTickets(ConvertRequest2BO.convertQueryTickets(request));
			QueryTicketsResponse response = new QueryTicketsResponse();
			// 判断是否为空
			if(null != queryTicketResult && TicketsConstants.SUCCESS_CODE.equals(queryTicketResult.getReturn_code())){
				Map<String, List<String>> listMap = collectArriveStationList(queryTicketResult);
				response.setArriveStationList(listMap.get(arriveSatationListKey));
				response.setFromStationList(listMap.get(fromSatationListKey));
				response.setTrainTypeList(listMap.get(trianTypeListKey));
				response.setDataList(Convert2VO.convertQueryTicket(request, queryTicketResult.getTrain_data()));
				result.setData(ResponseBuilder.buildSuccess(response));
			} else {
				result.setData(ResponseBuilder.buildSuccess(""));
			}
		} catch (BizException be) {
			logger.error(be.getMessage(), be);
			result.setData(ResponseBuilder.buildError(be.getCode(), be.getNotice()));
			this.error(result, be);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setData(ResponseBuilder.buildSuccess(""));
			this.error(result, e);
		} finally {
			this.send(result);
		}

		return result.getData();
	}

	private void validateParam(QueryTicketsRequest request) {
		if(StringUtils.isEmpty(request.getArriveStation())){
			super.throwParamException("arriveStation is null");
		}
		if(StringUtils.isEmpty(request.getFromStation())){
			super.throwParamException("fromStation is null");
		}
		if(StringUtils.isEmpty(request.getTravelTime())){
			super.throwParamException("travelTime is null");
		}

		if("true".equals(DynamicParamsUtils.getParam(DynamicParamsEnums.isValidateSign))) {
			logger.info("------------------> valiate sign:" + DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey));
			super.validateSign(request.getSignKey());

			String sign = KuyouRequestSign.signQueryTickets(request, DynamicParamsUtils.getParam(DynamicParamsEnums.kuyou_signKey));
			if(!request.getSignKey().equals(sign)){
				super.throwParamException("signKey validate error");
			}
		}

	}

	private Map<String, List<String>> collectArriveStationList(QueryTicketsBO queryTicketResult){
		Map<String, List<String>> listMap = new HashMap<String, List<String>>();
		List<String> fromSatationList = new ArrayList<String>();
		List<String> arriveSatationList = new ArrayList<String>();
		List<String> trianTypeList = new ArrayList<String>();
		listMap.put(fromSatationListKey, fromSatationList);
		listMap.put(arriveSatationListKey, arriveSatationList);
		listMap.put(trianTypeListKey, trianTypeList);
		if(null != queryTicketResult.getTrain_data() && !queryTicketResult.getTrain_data().isEmpty()){
			for(TrainData trainData : queryTicketResult.getTrain_data()){
				if(!fromSatationList.contains(trainData.getFrom_station())){
					fromSatationList.add(trainData.getFrom_station());
				}
				if(!arriveSatationList.contains(trainData.getArrive_station())){
					arriveSatationList.add(trainData.getArrive_station());
				}
				String trainType = trainData.getTrain_code().substring(0, 1);
				if(StringUtils.isNotEmpty(trainType)){
					if(null != TrainType.get(trainType)){
						if(!trianTypeList.contains(TrainType.get(trainType).getDesc())){
							trianTypeList.add(TrainType.get(trainType).getDesc());
						}
					} else {
						if(!trianTypeList.contains(TrainType.Q.getDesc())){
							trianTypeList.add(TrainType.Q.getDesc());
						}
						logger.info("-----trainType is q !!!" + trainType);
					}
				} else {
					logger.error("-----trainType error!!!" + trainData.getTrain_code());
				}
			}
		}
		return listMap;
	}

}
