package com.tsh.vas.phone.service;

import java.util.ArrayList;
import java.util.List;

import com.tsh.vas.dao.phone.PhoneGoodsDao;
import com.tsh.vas.model.phone.PhoneGoodsPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Result;
import com.dtds.platform.util.security.UserInfo;
import com.tsh.vas.dao.phone.PhoneGoodsAreaDao;
import com.tsh.vas.model.phone.PhoneGoodsAreaPo;
import com.tsh.vas.vo.phone.PhoneGoodsAreaVo;


@Service
@SuppressWarnings("all")
public class PhoneGoodsAreaService {
    @Autowired
    private PhoneGoodsAreaDao phoneGoodsAreaDao;
    @Autowired
    private PhoneGoodsDao phoneGoodsDao;
    @Autowired
    PhoneFareBuildHelper phoenBuildHelper;

    /**
     * 新增
     * @param result
     * @param phoneGoodsArea
     * @return
     */
    public Result addPhoneGoodsArea(Result result,PhoneGoodsAreaVo phoneGoodsAreaVo)throws Exception{
        PhoneGoodsAreaPo phoneGoodsAreaPo = new PhoneGoodsAreaPo();

        if (phoneGoodsAreaVo != null) {
            if(phoneGoodsAreaVo.getGoodsId()!=null){
                phoneGoodsAreaPo.setGoodsId(phoneGoodsAreaVo.getGoodsId());
            }
            if(phoneGoodsAreaVo.getAreaId()!=null){
                phoneGoodsAreaPo.setAreaId(phoneGoodsAreaVo.getAreaId());
            }
            if(phoneGoodsAreaVo.getAreaName()!=null){
                phoneGoodsAreaPo.setAreaName(phoneGoodsAreaVo.getAreaName());
            }
        }

        result = phoneGoodsAreaDao.addPhoneGoodsArea(result,phoneGoodsAreaPo);
        return result;
    }



    /**
     * 保存  带User对象
     * @param result
     * @return
     */
    public Result savePhoneGoodsArea(Result result,PhoneGoodsAreaVo phoneGoodsAreaVo,UserInfo user) throws Exception {
        if(phoneGoodsAreaVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phoneGoodsAreaVo.getId();
        result = phoneGoodsAreaDao.getPhoneGoodsAreaById(result,id);
        PhoneGoodsAreaPo phoneGoodsAreaPo  = (PhoneGoodsAreaPo)result.getData();

        if (phoneGoodsAreaPo != null) {
            if(phoneGoodsAreaVo.getGoodsId()!=null){
                phoneGoodsAreaPo.setGoodsId(phoneGoodsAreaVo.getGoodsId());
            }
            if(phoneGoodsAreaVo.getAreaId()!=null){
                phoneGoodsAreaPo.setAreaId(phoneGoodsAreaVo.getAreaId());
            }
            if(phoneGoodsAreaVo.getAreaName()!=null){
                phoneGoodsAreaPo.setAreaName(phoneGoodsAreaVo.getAreaName());
            }
        }else{
            phoneGoodsAreaPo = new PhoneGoodsAreaPo();
            if(phoneGoodsAreaVo.getGoodsId()!=null){
                phoneGoodsAreaPo.setGoodsId(phoneGoodsAreaVo.getGoodsId());
            }
            if(phoneGoodsAreaVo.getAreaId()!=null){
                phoneGoodsAreaPo.setAreaId(phoneGoodsAreaVo.getAreaId());
            }
            if(phoneGoodsAreaVo.getAreaName()!=null){
                phoneGoodsAreaPo.setAreaName(phoneGoodsAreaVo.getAreaName());
            }
            result = phoneGoodsAreaDao.addPhoneGoodsArea(result,phoneGoodsAreaPo);
        }
        return result;
    }



    /**
     * 保存 
     * @param result
     * @return
     */
    public Result savePhoneGoodsArea(Result result,PhoneGoodsAreaVo phoneGoodsAreaVo) throws Exception {
        if(phoneGoodsAreaVo == null){
            result.setData("参数为空，保存失败");
            return result;
        }

        Long id = phoneGoodsAreaVo.getId();
        result = phoneGoodsAreaDao.getPhoneGoodsAreaById(result,id);
        PhoneGoodsAreaPo phoneGoodsAreaPo  = (PhoneGoodsAreaPo)result.getData();

        if (phoneGoodsAreaPo != null) {
            if(phoneGoodsAreaVo.getGoodsId()!=null){
                phoneGoodsAreaPo.setGoodsId(phoneGoodsAreaVo.getGoodsId());
            }
            if(phoneGoodsAreaVo.getAreaId()!=null){
                phoneGoodsAreaPo.setAreaId(phoneGoodsAreaVo.getAreaId());
            }
            if(phoneGoodsAreaVo.getAreaName()!=null){
                phoneGoodsAreaPo.setAreaName(phoneGoodsAreaVo.getAreaName());
            }
        }else{
            phoneGoodsAreaPo = new PhoneGoodsAreaPo();
            if(phoneGoodsAreaVo.getGoodsId()!=null){
                phoneGoodsAreaPo.setGoodsId(phoneGoodsAreaVo.getGoodsId());
            }
            if(phoneGoodsAreaVo.getAreaId()!=null){
                phoneGoodsAreaPo.setAreaId(phoneGoodsAreaVo.getAreaId());
            }
            if(phoneGoodsAreaVo.getAreaName()!=null){
                phoneGoodsAreaPo.setAreaName(phoneGoodsAreaVo.getAreaName());
            }
            result = phoneGoodsAreaDao.addPhoneGoodsArea(result,phoneGoodsAreaPo);
        }
        return result;
    }


    /**
     * 批量新增
     * @param result
     * @param phoneGoodsArea
     * @return
     */
    public Result batchSavePhoneGoodsArea(Result result, List<PhoneGoodsAreaVo> phoneGoodsArea_list) throws Exception {
        List<PhoneGoodsAreaPo> list = (List<PhoneGoodsAreaPo>) phoenBuildHelper.voToPo(phoneGoodsArea_list,
        		PhoneGoodsAreaPo.class);
        if(null != list && !list.isEmpty()){
            for(PhoneGoodsAreaPo phoneGoodsAreaPo : list){
                PhoneGoodsPo goods = phoneGoodsDao.getPhoneGoodsById(result, phoneGoodsAreaPo.getGoodsId()).getData();
                if(null != goods){
                    List<PhoneGoodsPo> sameGoodsList = phoneGoodsDao.findSameType(result, goods.getSupplierCode(), goods.getPhoneValue()).getData();
                    List<Long> goodsIdList = new ArrayList<>();
                    for(PhoneGoodsPo goodsPo : sameGoodsList){
                        goodsIdList.add(goodsPo.getId());
                    }
                    List<PhoneGoodsAreaPo> areaPoList = phoneGoodsAreaDao.getPhoneGoodsAreaByGoodIds(result, goodsIdList).getData();
                    if(null != areaPoList){
                        for(PhoneGoodsAreaPo areaPo : areaPoList){
                            if(areaPo.getAreaId().equals(phoneGoodsAreaPo.getAreaId())){
                                result.setStatus(300);
                                return result;
                            }
                        }
                    }
                }
            }

            result = phoneGoodsAreaDao.batchSavePhoneGoodsArea(result,list);
        }

        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneGoodsArea(Result result, Integer id) throws Exception {
        result = phoneGoodsAreaDao.deletePhoneGoodsArea(result,id);
        return result;
    }

    /**
     * @param result
     * @param goodsId 商品id
     * @param provinceIds 省份代码 "'1','2','3'";
     * @return
     * @throws Exception
     */
    public Result  deletePhoneGoodsAreaByGoodsIdAndAreaId(Result result, Long goodsId, String provinceIds) throws Exception {
    	result = phoneGoodsAreaDao.deletePhoneGoodsAreaByGoodsIdAndAreaId(result, goodsId, provinceIds);
    	return result;
    }
    
    public Result deletePhoneGoodsAreaByGoodsId(Result result, Long id) throws Exception {
    	return phoneGoodsAreaDao.deletePhoneGoodsAreaByGoodsId(result, id);
    }

    
    /**
     * 批量删除
     * @param result
     * @param goodIds 格式如下 "'2','3'"
     * @return
     * @throws Exception 
     */
    public Result batchDeletePhoneGoodsAreaByGoodsId(Result result, String goodIds) throws Exception {
    	return phoneGoodsAreaDao.batchDeletePhoneGoodsAreaByGoodsId(result, goodIds);
    }
    
    /**
     * 批量删除
     * @param result
     * @param phoneGoodsArea
     * @return
     */
    public Result batchDelPhoneGoodsArea(Result result, List<PhoneGoodsAreaVo> phoneGoodsArea_list)throws Exception{
        List<PhoneGoodsAreaPo> list = new ArrayList<PhoneGoodsAreaPo>(); 
        phoneGoodsAreaDao.batchDelete(list);
        return result;
    }



    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneGoodsAreaList(Result result,Page page,PhoneGoodsAreaVo phoneGoodsAreaVo){
        PhoneGoodsAreaPo phoneGoodsAreaPo = new PhoneGoodsAreaPo();
        result = phoneGoodsAreaDao.queryPhoneGoodsAreaList(result,page,phoneGoodsAreaPo);
        return result;
    }


    /**
     * 根据条件获取 列表 带User
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneGoodsAreaList(Result result,Page page,PhoneGoodsAreaVo phoneGoodsAreaVo,UserInfo user){
        PhoneGoodsAreaPo phoneGoodsAreaPo = new PhoneGoodsAreaPo();
        /**
         *自行匹配需要查询的字段及值
         **/
        result = phoneGoodsAreaDao.queryPhoneGoodsAreaList(result,page,phoneGoodsAreaPo);
        return result;
    }


    /**
     * 根据ID获取  带User对象
     * @param result
     * @return
     */
    public Result getPhoneGoodsAreaById(Result result,Long id,UserInfo user) throws Exception{
        result = phoneGoodsAreaDao.getPhoneGoodsAreaById(result,id);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneGoodsAreaById(Result result,Long id) throws Exception{
        result = phoneGoodsAreaDao.getPhoneGoodsAreaById(result,id);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhoneGoodsArea(Result result,PhoneGoodsAreaVo phoneGoodsAreaVo) throws Exception {
        Long id = phoneGoodsAreaVo.getId();
        result = phoneGoodsAreaDao.getPhoneGoodsAreaById(result,id);
        PhoneGoodsAreaPo phoneGoodsAreaPo  = (PhoneGoodsAreaPo)result.getData();
        if (phoneGoodsAreaPo != null) {
            if(phoneGoodsAreaVo.getGoodsId()!=null){
                phoneGoodsAreaPo.setGoodsId(phoneGoodsAreaVo.getGoodsId());
            }
            if(phoneGoodsAreaVo.getAreaId()!=null){
                phoneGoodsAreaPo.setAreaId(phoneGoodsAreaVo.getAreaId());
            }
            if(phoneGoodsAreaVo.getAreaName()!=null){
                phoneGoodsAreaPo.setAreaName(phoneGoodsAreaVo.getAreaName());
            }
        }
        return result;
    }


    /**
     * 批量更新 
     * @param result
     * @return
     */
    public Result batchUpdatePhoneGoodsArea(Result result,List<PhoneGoodsAreaVo> phoneGoodsArea_list) throws Exception {
        List<PhoneGoodsAreaPo> list = new ArrayList<PhoneGoodsAreaPo>(); 
        phoneGoodsAreaDao.batchUpdatePhoneGoodsArea(result,list);
        return result;
    }

}
