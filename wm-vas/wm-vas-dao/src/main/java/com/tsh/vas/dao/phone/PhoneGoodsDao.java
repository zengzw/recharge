package com.tsh.vas.dao.phone;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Page;
import com.dtds.platform.util.bean.Pagination;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.phone.PhoneGoodsPo;
import com.tsh.vas.model.trainticket.HcpOrderInfos;
import com.tsh.vas.vo.trainticket.HcpOrderInfoReportsVo;

@Repository
@SuppressWarnings("all")
public class PhoneGoodsDao extends HibernateDao<PhoneGoodsPo, Long> {
    /**
     * 新增
     * @param result
     * @param phoneGoods
     * @return
     */
    public Result addPhoneGoods(Result result,PhoneGoodsPo phoneGoods)throws Exception{
        this.save(phoneGoods);
        return result;
    }

    /**
     * 批量新增
     * @param result
     * @param phoneGoods
     * @return
     */
    public Result batchSavePhoneGoods(Result result, List<PhoneGoodsPo> phoneGoods_list) throws Exception {
        result.setData(this.batchSave(phoneGoods_list));
        return result;
    }

    /**
     * 删除
     * @param id 标识
     * @return
     */
    public Result deletePhoneGoods(Result result, Long id) throws Exception {
        int count = this.updateHql("delete PhoneGoodsPo where id=?",id);
        result.setData(count);
        return result;
    }
    
    /**
     * 批量删除
     * @param id 标识 以逗号分割的字符串 如 "'1','3'"
     * @return
     */
    public Result batchDeletePhoneGoods(Result result, String ids) throws Exception {
        int count = this.updateHql("delete PhoneGoodsPo where id in("+ids+")");
        result.setData(count);
        return result;
    }



    /**
     * 批量删除
     * @param result
     * @param phoneGoods
     * @return
     */
    public Result batchDelPhoneGoods(Result result, List<PhoneGoodsPo> phoneGoods_list)throws Exception{
        this.batchDelete(phoneGoods_list);
        return result;
    }


    /**
     * 批量删除ById
     * @param result
     * @param phoneGoods
     * @return
     */
    public Result batchDelPhoneGoodsByIds(Result result,Integer[] ids)throws Exception{
        int count = 0;
        for(Integer id : ids){
            this.delete(id);
            count ++;
        }
        result.setData(count);
        return result;
    }



    /**
     * 清空表 
     * @param result
     * @return
     */
    public Result clearPhoneGoods(Result result) {
        String sql = " truncate table phone_goods ";
        int count = this.getSession().createSQLQuery(sql).executeUpdate();
        result.setData(count);
        return result;
    }



    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getPhoneGoodsById(Result result,Long id) throws Exception{
        PhoneGoodsPo phoneGoodsPo = this.get(id);
        result.setData(phoneGoodsPo);
        return result;
    }

    public Result findSameType(Result result, String supplierCode, Integer value) {
        String sql = " from PhoneGoodsPo where supplierCode = ? and phoneValue = ?";
        List<PhoneGoodsPo> list = this.find(sql, supplierCode, value);
        result.setData(list);
        return result;
    }

    /**
     * 根据ID获取 
     * @param result
     * @return
     */
    public Result getAvailablePhoneGoodsById(Result result,Long id) throws Exception{
        String hql = "from PhoneGoodsPo t where id=? and status=1";
        
        PhoneGoodsPo phoneGoods = this.findUnique(hql,id);
        result.setData(phoneGoods);
        return result;
      
    }


    /**
     * 根据条件获取 列表
     * @param result
     * @param page
     * @param screenAdvertisementQuery
     * @return
     */
    public Result queryPhoneGoodsList(Result result,Page page,PhoneGoodsPo phoneGoodsPo){
        Criteria criteria = this.getSession().createCriteria(PhoneGoodsPo.class);
        if(null != phoneGoodsPo){
            if(phoneGoodsPo.getSupplierCode()!=null){
                criteria.add(Restrictions.eq("supplierCode", phoneGoodsPo.getSupplierCode()));
            }

            if(phoneGoodsPo.getCreater()!=null){
                criteria.add(Restrictions.eq("creater", phoneGoodsPo.getCreater()));
            }

            if(phoneGoodsPo.getCreateTime()!=null){
                criteria.add(Restrictions.eq("createTime", phoneGoodsPo.getCreateTime()));
            }
            if(phoneGoodsPo.getModifyer()!=null){
                criteria.add(Restrictions.eq("modifyer", phoneGoodsPo.getModifyer()));
            }

            if(phoneGoodsPo.getModifyTime()!=null){
                criteria.add(Restrictions.eq("modifyTime", phoneGoodsPo.getModifyTime()));
            }
            if(phoneGoodsPo.getRemark()!=null){
                criteria.add(Restrictions.eq("remark", phoneGoodsPo.getRemark()));
            }
        }
        Pagination pagination = this.findPagination(page, criteria);
        result.setData(pagination);
        return result;
    }
    
    public Result findPhoneFareWays(Result result, String company, Integer phoneValue, String saleRegion,
    	   int publicStatus, int page, int pageSize) {
    	String baseSql = "select t1.id as id ,t3.provider_name as company, t1.phone_value as faceValue, t1.sale_amount as salePrice, "
    			+ " group_concat(t2.area_name separator '、')as saleRegion, t1.status as publicStatus  from (wmvas.phone_goods t1 left join wmvas.phone_goods_area t2 on t1.id=t2.goods_id)"
    			+ " left join wmvas.phone_service_provider t3 on t1.supplier_code = t3.provider_code where 1=1 ";
    	//运营商
    	if (StringUtils.isNotEmpty(company)) {
            baseSql += " and t3.id = "+Long.valueOf(company);
    	}
    	//话费面值
    	if (phoneValue != null && phoneValue > 0) {
            baseSql += " and t1.phone_value = "+phoneValue;
    	}
    	//销售区域
    	if (StringUtils.isNotEmpty(saleRegion)) {
            baseSql += " and t2.area_id='"+saleRegion+"'";
    	}
    	//发布状态,-1代表匹配所有状态
    	if (publicStatus == -1) {
            baseSql += " and t1.status in(0, 1)";
    	} else {
            baseSql += " and t1.status = "+publicStatus;
    	}
        baseSql += " group by t1.id ";

        String querySql = baseSql +  " order by t1.create_time desc, t1.id, t3.id limit ?,? ";

        logger.info("---------> query page:" + querySql);

    	List<Map<String, Object>>list = this.queryForList(querySql, pageSize * (page - 1), pageSize);

        List<Map<String, Object>> countList = this.queryForList(baseSql);


        Pagination pagination = new Pagination();
        pagination.setTotal(countList.size());
        pagination.setRows(list);
    	result.setData(pagination);
    	return result;
    }


    /**
     *  根据地区ID，服务商code 获取商品信息
     * 
     * @param result
     * @param areaId 归属地ID
     * @param 服务商Code
     * @return
     */
    public Result queryListGoodsByAreaIdAndProviceCode(Result result,Integer areaId,String providerCode){
        StringBuffer sql = new StringBuffer ();
        sql.append("select pg.* from phone_goods pg left join phone_goods_area pga");
        sql.append(" on pg.id = pga.goods_id");
        sql.append(" where pg.supplier_code='"+providerCode+"' and pga.area_id="+areaId);
        sql.append(" and pg.status=1 order by pg.phone_value asc"); //已发布的

        Session session = this.getSession ();
        SQLQuery sqlQuery = session.createSQLQuery (sql.toString()).addEntity (PhoneGoodsPo.class);
        result.setData (sqlQuery.list ());
        return result;
    }
    
    
    
    /**
     * 匹配话费充值方案的记录数
     * @param result
     * @param companyIds
     * @param faceValues
     * @param saleRegionNames
     * @param saleRegionIds
     * @return
     */
    public Result getPhoneWaysCount(Result result, String companyIds, String faceValues,
			String saleRegionNames, String saleRegionIds) {
    	String sql = "select count(*) from ((phone_goods t2 left join phone_value t1 on t1.value=t2.phone_value) "
    			+ " left join phone_goods_area t3 on t3.goods_id = t2.id) left join phone_service_provider t4 "
    			+ " on t4.provider_code = t2.supplier_code where 1=1 ";
    	
    	if (StringUtils.isNotEmpty(companyIds)) {
    		sql += " and t4.id in("+companyIds+")";
    	}
    	
    	if (StringUtils.isNotEmpty(faceValues)) {
    		sql += " and t1.value in("+faceValues+")";
    	}
    	
    	if (StringUtils.isNotEmpty(saleRegionNames)) {
    		sql += " and t3.area_name in("+saleRegionNames+")";
    	}
    	
    	if (StringUtils.isNotEmpty(saleRegionIds)) {
    		sql += " and t3.area_id in ("+saleRegionIds+")" ;
    	}
    			 
    	result.setData(this.queryForInt(sql));
    	return result;
    }
    
    /**
     * 
     * @param result
     * @param value -- 面值
     * @param code -- 运营商代码
     * @return
     */
     public Result getPhoneGoodsByValueAndCode(Result result, String value, String code) {
    	 String hql = "from PhoneGoodsPo t where t.supplierCode in("+code+") and t.phoneValue in ("+value+")";
    	 result.setData(this.find(hql));
    	 return result;
     }
     
     /**
      * 批量更新
      * @param result
      * @param status -- 状态
      * @param ids -- 以逗号分割的字符串  如'1,2,3,4'
      * @return
      */
     public Result batchUpdate(Result result, int status, String ids) {
    	 result.setData(this.updateHql("Update PhoneGoodsPo t set t.status=? where t.id in("+ids+")" , status));
    	 return result;
     }
     
     /**
      * 通用批量更新
      * @param result
      * @param status -- 状态
      * @param ids -- 以逗号分割的字符串  如'1,2,3,4'
      * @return
      */
     public Result batchUpdate(Result result, PhoneGoodsPo po, String ids) {
    	 if (po == null)
    		 throw new RuntimeException("PhoneGoodsPo 类型:"+po);
    
    	 String sql = "Update PhoneGoodsPo t set ";
    	 
    	 if (po.getStatus() != null) {
    		 sql += " t.status="+po.getStatus() + ",";
    	 }
    	 
    	 if (po.getSaleAmount() != null) {
    		 sql += " t.saleAmount="+po.getSaleAmount();
    	 }	 
    	 
    	 if (sql.indexOf(",") > 0)
    		 sql = sql.substring(0, sql.length() - 1);
    	 
    	 sql += " where t.id in("+ids+")";
    	 result.setData(this.updateHql(sql));
    	 return result;
     }
}
