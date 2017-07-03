package com.tsh.vas.phone.constroller;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.bean.ReturnDTO;
import com.tsh.vas.controller.BaseController;
import com.tsh.vas.phone.service.VasMobileManagerService;
import com.tsh.vas.vo.phone.PhoneMobileManagerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * 号段管理接口定义
 * Created by Administrator on 2017/5/2 002.
 */
@Controller
@RequestMapping("/vas/phone/mobile/manager/")
public class MobileManagerController extends BaseController {

    @Autowired
    private VasMobileManagerService vasMobileManagerService;

    /**
     * 分页查询
     * @param page
     * @param rows
     * @param mobileManagerVo
     * @return
     */
    @RequestMapping(value = "/pageList.do")
    @ResponseBody
    public ReturnDTO pageList(int page, int rows, @ModelAttribute PhoneMobileManagerVo mobileManagerVo){
        Result result = this.getResult();
        try {
            Page page_num = new Page(page,rows);
            result =  vasMobileManagerService.pageList(result, page_num, mobileManagerVo);
        } catch (Exception e) {
            result = this.error(result, e);
        } finally {
            this.send(result);
        }
        return result.DTO();
    }

    /**
     * 新增
     * @param mobileManagerVo
     * @return
     */
    @RequestMapping(value = "/save.do")
    @ResponseBody
    public ReturnDTO save(@ModelAttribute PhoneMobileManagerVo mobileManagerVo){
        Result result = this.getResult();
        try {
            result =  vasMobileManagerService.save(result, mobileManagerVo);
        } catch (Exception e) {
            result = this.error(result, e);
            result.setMsg(e.getMessage());
        } finally {
            this.send(result);
        }
        return result.DTO();
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete.do")
    @ResponseBody
    public ReturnDTO delete(String ids){
        Result result = this.getResult();
        try {
            result =  vasMobileManagerService.delete(result, ids);
        } catch (Exception e) {
            result = this.error(result, e);
        } finally {
            this.send(result);
        }
        return result.DTO();
    }
}
