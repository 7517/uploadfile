package com.edison.saas.bootstrap.common.business.simpleconfig.dao;

import com.edison.saas.bootstrap.common.business.simpleconfig.domain.SimpleConfig;
import com.edison.saas.common.jpa.BaseDao;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 通用配置的数据库操作
 * @author icode
 */
@Repository("simpleConfigDao")
public interface SimpleConfigDao extends BaseDao<SimpleConfig, Long>{


    List<SimpleConfig> findByConfigType(String configType);
    SimpleConfig findByConfigTypeAndCode(String configType, String code);

    @Modifying
    int deleteByConfigType(String configType);


}
