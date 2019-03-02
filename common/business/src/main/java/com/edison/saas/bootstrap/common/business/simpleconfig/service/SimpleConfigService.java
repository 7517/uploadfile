package com.edison.saas.bootstrap.common.business.simpleconfig.service;


import com.edison.saas.bootstrap.common.business.simpleconfig.dao.SimpleConfigDao;
import com.edison.saas.bootstrap.common.business.simpleconfig.dao.SimpleConfigSpecification;
import com.edison.saas.bootstrap.common.business.simpleconfig.domain.SimpleConfig;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigCondition;
import com.edison.saas.common.jpa.GenericCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("simpleConfigService")
public class SimpleConfigService  extends GenericCrudService<SimpleConfig, Long, SimpleConfigCondition, SimpleConfigDao> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleConfigService.class);


	public void updateTypeCode(String oldTypeCode, String newTypeCode){

		List<SimpleConfig> simpleConfigs = dao.findByConfigType(oldTypeCode);

		for(SimpleConfig simpleConfig : simpleConfigs){
			simpleConfig.setConfigType(newTypeCode);
		}

		this.merge(simpleConfigs);
	}

	@Override
	public Specification<SimpleConfig> getSpecification(SimpleConfigCondition condition) {
		return new SimpleConfigSpecification(condition);
	}

	List<SimpleConfig> findByConfigType(String typeCode){
		return dao.findByConfigType(typeCode);
	}

	public int deleteByConfigType(String typeCode){
		return dao.deleteByConfigType(typeCode);
	}

	public SimpleConfig findByConfigTypeAndCode(String typeCode, String code){
		return dao.findByConfigTypeAndCode(typeCode, code);
	}

	public Sort getDefaultSort(){

		Sort sort = new Sort(Sort.Direction.ASC , SimpleConfig.PROPERTY_V_INDEX);
		return sort;
	}
}