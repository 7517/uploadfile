package com.edison.saas.bootstrap.common.business.simpleconfig.dao;

import com.edison.saas.bootstrap.common.business.simpleconfig.domain.SimpleConfigType;
import com.edison.saas.bootstrap.common.business.simpleconfig.dto.SimpleConfigTypeCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class SimpleConfigTypeSpecification implements Specification<SimpleConfigType>{

	private SimpleConfigTypeCondition condition;

	public SimpleConfigTypeSpecification(SimpleConfigTypeCondition condition){
		this.condition = condition;
	}

	@Override
	public Predicate toPredicate(Root<SimpleConfigType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicateList = new ArrayList<>();

		if(condition==null){
			return null;
		}

		tryAddTypeNamePredicate(predicateList, root, cb);
		tryAddTypeCodePredicate(predicateList, root, cb);


		Predicate[] pre = new Predicate[predicateList.size()];
		pre = predicateList.toArray(pre);
		return cb.and(pre);
    }


	private void tryAddTypeNamePredicate(List<Predicate> predicateList, Root<SimpleConfigType> root, CriteriaBuilder cb){
		if(StringUtils.isNotEmpty(condition.getTypeName())){
			predicateList.add(cb.like(root.get(SimpleConfigType.PROPERTY_TYPE_NAME).as(String.class), "%"+condition.getTypeName()+"%"));
		}
	}
	private void tryAddTypeCodePredicate(List<Predicate> predicateList, Root<SimpleConfigType> root, CriteriaBuilder cb){
		if(StringUtils.isNotEmpty(condition.getTypeCode())){
			predicateList.add(cb.like(root.get(SimpleConfigType.PROPERTY_TYPE_CODE).as(String.class), "%"+condition.getTypeCode()+"%"));
		}
	}
}


