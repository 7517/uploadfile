package com.edison.saas.bootstrap.common.business.simpleconfig.dao;

import com.edison.saas.bootstrap.common.business.simpleconfig.domain.SimpleConfigType;
import com.edison.saas.common.jpa.BaseDao;
import org.springframework.stereotype.Repository;


/**
 * 通用配置类型的数据库操作
 * @author icode
 */
@Repository("simpleConfigTypeDao")
public interface SimpleConfigTypeDao extends BaseDao<SimpleConfigType, Long>{

    SimpleConfigType findByTypeCode(String typeCode);

}
