package com.tsh.traintickets.web.controller;

import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.dtds.platform.web.controller.PlatformController;
import com.dtds.platform.web.userinfo.UserUtil;
import com.traintickets.common.BizException;
import com.traintickets.common.ResponseCode;
import com.traintickets.common.TicketsConstants;
import org.apache.commons.lang.StringUtils;

/**
 * @author zhoujc
 *
 */
public class BaseController extends PlatformController {

    @Override
    public Result getResult() {
        UserInfo userInfo = UserUtil.getUserInfo(request);
        return new Result(request, userInfo, "traintickets");
    }

    protected void throwParamException(String notice) throws BizException{
        logger.error("-------> param exception: " + notice);
        throw new BizException(ResponseCode.PARAM_NOT_EMPTY, notice);
    }

    protected void throwParamException() throws BizException{
        throw new BizException(ResponseCode.PARAM_NOT_EMPTY);
    }

    protected void writeCallBackMsg(boolean isSuccess){
        try{
            if(isSuccess){
                logger.info("------------------->>> orderCallback SUCCESS");
                super.getWriter().write(TicketsConstants.SUCCESS);
            } else {
                logger.info("------------------->>> orderCallback FAILURE");
                super.getWriter().write(TicketsConstants.FAILURE);
            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
    }

    protected void validateSign(String sing){
        if(StringUtils.isEmpty(sing)){
            throw new BizException(ResponseCode.PARAM_NOT_EMPTY, "signKey is null");
        }
    }
}
