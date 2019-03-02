package com.edison.saas.bootstrap.common.business.simpleconfig.service;


import com.edison.saas.bootstrap.common.business.simpleconfig.dao.SimpleConfigTypeDao;
import com.edison.saas.bootstrap.common.business.simpleconfig.dao.SimpleConfigTypeSpecification;
import com.edison.saas.bootstrap.common.business.simpleconfig.domain.SimpleConfigType;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigTypeCondition;
import com.edison.saas.common.jpa.GenericCrudService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("simpleConfigTypeService")
public class SimpleConfigTypeService  extends GenericCrudService<SimpleConfigType, Long, SimpleConfigTypeCondition, SimpleConfigTypeDao> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleConfigTypeService.class);


	@Autowired
	private SimpleConfigService simpleConfigService;

	@Transactional
	public void merge(SimpleConfigType simpleConfigType){

		SimpleConfigType old = dao.findOne(simpleConfigType.getId());

		String oldTypeCode = old.getTypeCode();

		if(!StringUtils.equals(oldTypeCode, simpleConfigType.getTypeCode())){
			simpleConfigService.updateTypeCode(oldTypeCode, simpleConfigType.getTypeCode());
		}
		dao.save(simpleConfigType);

	}

	@Transactional
	public void delete(String id){
		SimpleConfigType simpleConfigType = dao.findByTypeCode(id);
		simpleConfigService.deleteByConfigType(simpleConfigType.getTypeCode());
	}

	@Override
	public Specification<SimpleConfigType> getSpecification(SimpleConfigTypeCondition condition) {
		return new SimpleConfigTypeSpecification(condition);
	}

	public SimpleConfigType findByCode(String code){

		return dao.findByTypeCode(code);

	}

	public Sort getDefaultSort(){

		Sort sort = new Sort(Sort.Direction.DESC , SimpleConfigType.PROPERTY_TYPE_NAME);
		return sort;
	}
}