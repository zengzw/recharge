package com.tsh.vas.dao.phone;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneGoodsAreaPo;

@Repository
@SuppressWarnings("all")
public class PhoneGoodsAreaDao extends HibernateDao<PhoneGoodsAreaPo, Long> {
    /**
     * 新增
     * @param result
     * @param phoneGoodsArea
     * @return
     */
    public Result addPhoneGoodsArea(Result result,PhoneGoodsAreaPo phoneGoodsArea)throws Exception{
        this.save(phoneGoodsArea);
        return result;
    }

    /**
     * 批量新增
     * @param result
     * @param phoneGoodsArea
     * @return
     */
    public Result batchSavePhoneGoodsArea(Result result, List<PhoneGoodsAreaPo> phoneGoodsArea_list) throws Exception {
        this.batchSave(phoneGoodsArea_list);
        result.setData(null);
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneGoodsArea(Result result, Integer id) throws Exception {
        int count = this.updateHql("delete PhoneGoodsAreaPo where id=?",id);
        result.setData(count);
        return result;
    }

    /**
     * 删除
     * @param goodsId 标识
     * @return
     */
    public Result deletePhoneGoodsAreaByGoodsId(Result result, Long id) throws Exception {
        int count = this.updateHql("delete PhoneGoodsAreaPo where goodsId=?",id);
        result.setData(count);
        return result;
    }
    
    /**
     * 批量删除
     * @param goodsId 标识  格式如下 "'2','3'"
     * @return
     */
    public Result batchDeletePhoneGoodsAreaByGoodsId(Result result, String goodsId) throws Exception {
        int count = this.updateHql("delete PhoneGoodsAreaPo where goodsId in("+goodsId+")");
        result.setData(count);
        return result;
    }


    public Result deletePhoneGoodsAreaByGoodsIdAndAreaId(Result result, Long goodsId, String provinceIds) {
    	int count = this.updateHql("delete PhoneGoodsAreaPo t where t.goodsId=? and t.areaId in("+provinceIds+")",goodsId);
        result.setData(count);
        return result;
    }

    /**
     * 清空表 
     * @param result
     * @return
     */
    public Result clearPhoneGoodsArea(Result result) {
        String sql = " truncate table phone_goods_area ";
        int count = this.getSession().createSQLQuery(sql).executeUpdate();
        result.setData(count);
        return result;
    }


    /**
     * 更新 
     * @param result
     * @return
     */
    public Result updatePhoneGoodsArea(Result result,PhoneGoodsAreaPo phoneGoodsAreaPo) throws Exception {
        StringBuffer hql = new StringBuffer();
        hql.append("update PhoneGoodsAreaPo set ");

        if(phoneGoodsAreaPo.getGoodsId()!=null){
            hql.append("goodsId = ").append(phoneGoodsAreaPo.getGoodsId());
        }
        if(phoneGoodsAreaPo.getAreaId()!=null){
            hql.append("areaId = ").append(phoneGoodsAreaPo.getAreaId());
        }
        if(phoneGoodsAreaPo.getAreaName()!=null){
            hql.append("areaName = ").append(phoneGoodsAreaPo.getAreaName());
        }

        hql.append("where id = ?");
        int count = this.updateHql(hql.toString(),phoneGoodsAreaPo.getId());
        result.setData(count);
        return result;
    }


    /**
     * 批量更新 
     * @param result
     * @return
     */
    public Result batchUpdatePhoneGoodsArea(Result result,List<PhoneGoodsAreaPo> phoneGoodsArea_list) throws Exception {
        this.batchUpdate(phoneGoodsArea_list);
        result.setData(null);
        return result;
    }


    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneGoodsAreaById(Result result,Long id) throws Exception{
        PhoneGoodsAreaPo phoneGoodsAreaPo = this.get(id);
        result.setData(phoneGoodsAreaPo);
        return result;
    }

    /**
     * 根据ID获取
     * @param result
     * @return
     */
    public Result getPhoneGoodsAreaByGoodIds(Result result, List<Long> goodIdList) throws Exception{
        if(null != goodIdList){
            String idsStr = "";
            for(Long id : goodIdList){
                idsStr += id + ",";
            }
            idsStr = idsStr.substring(0, idsStr.length()-1);

            List<PhoneGoodsAreaPo> phoneGoodsAreaPoList = this.find(" from PhoneGoodsAreaPo where goodsId in (" +idsStr+ ")");
            result.setData(phoneGoodsAreaPoList);
        }


        return result;
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneGoodsAreaList(Result result,Page page,PhoneGoodsAreaPo phoneGoodsAreaPo){
        Criteria criteria = this.getSession().createCriteria(PhoneGoodsAreaPo.class);
        if(null != phoneGoodsAreaPo){
            if(phoneGoodsAreaPo.getGoodsId()!=null){
                criteria.add(Restrictions.eq("goodsId", phoneGoodsAreaPo.getGoodsId()));
            }
            if(phoneGoodsAreaPo.getAreaId()!=null){
                criteria.add(Restrictions.eq("areaId", phoneGoodsAreaPo.getAreaId()));
            }
            if(phoneGoodsAreaPo.getAreaName()!=null){
                criteria.add(Restrictions.eq("areaName", phoneGoodsAreaPo.getAreaName()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }

}
