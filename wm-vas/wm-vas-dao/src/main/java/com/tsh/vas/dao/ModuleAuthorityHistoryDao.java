package com.tsh.vas.dao;


import com.dtds.platform.data.hibernate.HibernateDao;
import com.dtds.platform.util.bean.Result;
import com.tsh.vas.model.ModuleAuthorityHistoryPo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@SuppressWarnings("all")
public class ModuleAuthorityHistoryDao extends HibernateDao<ModuleAuthorityHistoryPo, Integer> {

    /**
     * 根据
     * @param result
     * @param moduleAuthorityPo
     * @return
     */
    public Result queryByAreaId(Result result, Integer areaId) {
        String hql = "from ModuleAuthorityHistoryPo where areaId = ? order by createTime desc";
        List<ModuleAuthorityHistoryPo> historyPoList = this.find (hql, areaId);
        result.setData (historyPoList);
        return result;
    }

}
