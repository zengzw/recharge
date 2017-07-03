package com.tsh.openpf.service;

import com.dtds.platform.util.bean.Result;
import com.tsh.broker.utils.BeanConvertor;
import com.tsh.broker.utils.Md5Digest;
import com.tsh.broker.utils.RandomUtils;
import com.tsh.openpf.dao.ServiceRegisterDao;
import com.tsh.openpf.po.ServiceRegister;
import com.tsh.openpf.utils.RegexValidateUtils;
import com.tsh.openpf.vo.ServiceRegisterVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 * ServiceRegisteService
 *
 * @author dengjd
 * @date 2016/10/11
 */
@Service
public class ServRegisterService {

    @Resource
    private ServiceRegisterDao serviceRegisterDao;
    /**
     * 注册供应商服务
     * @author Administrator <br>
     * @Date 2016年11月9日<br>
     * @param bizzId
     * @param remark
     * @return
     * @throws Exception
     */
    public Result addBusinessService(String bizzId,String  remark) throws Exception {
        //判断供应商是否存在
        boolean isExist = serviceRegisterDao.isRegisterServiceExists(bizzId);
        if(isExist) throw new RuntimeException("供应商已经存在");

        String signKey = generateSignKey(bizzId);
        ServiceRegister serviceRegister = new ServiceRegister();
        serviceRegister.setBusinessId(bizzId);
        serviceRegister.setSignKey(signKey);
        serviceRegister.setRemark(remark);
        serviceRegister.setCreateTime(System.currentTimeMillis());
        serviceRegister.setUpdateTime(System.currentTimeMillis());
        serviceRegisterDao.save(serviceRegister);

        Result result= new Result();
        result.setData(serviceRegister);

        return result;
    }

    public Result registerServiceAddr(String bizzId,String serviceAddr){
        Result result = new Result();

        if(StringUtils.isBlank(serviceAddr)) throw new IllegalArgumentException("服务地址不能为空");
        if(!RegexValidateUtils.isValidUrl(serviceAddr)) throw new IllegalArgumentException("服务地址格式不合法");
        ServiceRegister serviceRegister = serviceRegisterDao.findRegisterServiceByBizzNo(bizzId);
        if(serviceRegister == null) throw new RuntimeException("供应商不存在");

        serviceRegister.setServiceAddr(serviceAddr);
        serviceRegister.setUpdateTime( System.currentTimeMillis());
        serviceRegisterDao.update(serviceRegister);

        return result;
    }



    public Result queryBusinessService(String bizzNo) throws Exception {
        Result result = new Result();
        ServiceRegister serviceRegister = serviceRegisterDao.findRegisterServiceByBizzNo(bizzNo);
        if(serviceRegister != null) {
            ServiceRegisterVo serviceRegisterVo = new ServiceRegisterVo();
            BeanConvertor.copyProperties(serviceRegister, serviceRegisterVo);
            result.setData(serviceRegisterVo);
        }

        return result;
    }


    /**
     *  查询供应商信心
     *  
     * @param bizzNo
     * @param bizzCode
     * @return
     * @throws Exception
     */
    public Result queryBusinessService(String bizzNo,String bizzCode) throws Exception {
        Result result = new Result();
        ServiceRegister serviceRegister = serviceRegisterDao.findRegisterServiceByBizzNo(bizzNo,bizzCode);
        if(serviceRegister != null) {
            ServiceRegisterVo serviceRegisterVo = new ServiceRegisterVo();
            BeanConvertor.copyProperties(serviceRegister, serviceRegisterVo);
            result.setData(serviceRegisterVo);
        }
        
        return result;
    }
    
    /**
     *  查询供应商信心
     *  
     * @param bizzNo
     * @param bizzCode
     * @return
     * @throws Exception
     */
    public Result queryListBusinessService(String bizzCode) throws Exception {
        Result result = new Result();
        String bizzNo = "";
        List<ServiceRegister> lstServiceRegister = serviceRegisterDao.findListRegisterServiceByBizzNo(bizzNo,bizzCode);
        List<ServiceRegisterVo> lstVo = new ArrayList<ServiceRegisterVo>();
        
        if(!CollectionUtils.isEmpty(lstServiceRegister)) {
            for(ServiceRegister serviceRegister:lstServiceRegister){
                ServiceRegisterVo serviceRegisterVo = new ServiceRegisterVo();
                BeanConvertor.copyProperties(serviceRegister, serviceRegisterVo);
                lstVo.add(serviceRegisterVo);
            }
            
        }
        
        result.setData(lstVo);
        return result;
    }


    /**
     * 生成16位签名秘钥(供应商编号+随机数 截取16位)
     * @author Administrator <br>
     * @Date 2016年11月9日<br>
     * @param bizzNo
     * @return
     * @throws Exception
     */
    private  String  generateSignKey(String bizzNo) throws Exception {
        String baseContent = bizzNo + RandomUtils.generateRandomNo(4);
        return Md5Digest.encryptMD5(baseContent).substring(0,16);
    }


}
