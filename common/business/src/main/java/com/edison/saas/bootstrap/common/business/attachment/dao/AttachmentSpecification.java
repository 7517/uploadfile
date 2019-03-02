package com.edison.saas.bootstrap.common.business.attachment.dao;


import com.edison.saas.bootstrap.common.business.attachment.domain.Attachment;
import com.edison.saas.bootstrap.common.business.attachment.dto.AttachmentCondition;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AttachmentSpecification implements Specification<Attachment>{

	private AttachmentCondition condition;

	public AttachmentSpecification(AttachmentCondition condition){
		this.condition = condition;
	}

	@Override
	public Predicate toPredicate(Root<Attachment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		List<Predicate> predicateList = new ArrayList<>();

		if(condition==null){
			return null;
		}

		tryAddNamePredicate(predicateList, root, cb);
		tryAddTypePredicate(predicateList, root, cb);
		tryAddDisabledPredicate(predicateList, root, cb);
		tryAddNewFileNamePredicate(predicateList, root, cb);
		tryAddContentTypePredicate(predicateList, root, cb);
		tryAddSizePredicate(predicateList, root, cb);
		tryAddCreateTimePredicate(predicateList, root, cb);


		Predicate[] pre = new Predicate[predicateList.size()];
		pre = predicateList.toArray(pre);
		return cb.and(pre);
    }


	private void tryAddNamePredicate(List<Predicate> predicateList, Root<Attachment> root, CriteriaBuilder cb){
		if(StringUtils.isNotEmpty(condition.getName())){
			predicateList.add(cb.like(root.get(Attachment.PROPERTY_NAME).as(String.class), "%"+condition.getName()+"%"));
		}
	}
	private void tryAddTypePredicate(List<Predicate> predicateList, Root<Attachment> root, CriteriaBuilder cb){
		if(StringUtils.isNotEmpty(condition.getType())){
			predicateList.add(cb.like(root.get(Attachment.PROPERTY_TYPE).as(String.class), "%"+condition.getType()+"%"));
		}
	}
	private void tryAddDisabledPredicate(List<Predicate> predicateList, Root<Attachment> root, CriteriaBuilder cb){
		if (null != condition.getDisabled() ) {
			predicateList.add(cb.equal(root.get(Attachment.PROPERTY_DISABLED).as(Boolean.class), condition.getDisabled()));
		}
	}
	private void tryAddNewFileNamePredicate(List<Predicate> predicateList, Root<Attachment> root, CriteriaBuilder cb){
		if(StringUtils.isNotEmpty(condition.getNewFileName())){
			predicateList.add(cb.like(root.get(Attachment.PROPERTY_NEW_FILE_NAME).as(String.class), "%"+condition.getNewFileName()+"%"));
		}
	}
	private void tryAddContentTypePredicate(List<Predicate> predicateList, Root<Attachment> root, CriteriaBuilder cb){
		if(StringUtils.isNotEmpty(condition.getContentType())){
			predicateList.add(cb.like(root.get(Attachment.PROPERTY_CONTENT_TYPE).as(String.class), "%"+condition.getContentType()+"%"));
		}
	}
	private void tryAddSizePredicate(List<Predicate> predicateList, Root<Attachment> root, CriteriaBuilder cb){
		if (null != condition.getSize() ) {
			predicateList.add(cb.equal(root.get(Attachment.PROPERTY_SIZE).as(Long.class), condition.getSize()));
		}
	}
	private void tryAddCreateTimePredicate(List<Predicate> predicateList, Root<Attachment> root, CriteriaBuilder cb){

		if (null != condition.getCreateTime() ) {
			predicateList.add(cb.equal(root.get(Attachment.PROPERTY_CREATE_TIME).as(Date.class), condition.getCreateTime()));
		}

		if (null != condition.getCreateTimeStart() ) {
			predicateList.add(cb.greaterThanOrEqualTo(root.get(Attachment.PROPERTY_CREATE_TIME).as(Date.class), condition.getCreateTimeStart()));
		}

		if (null != condition.getCreateTimeEnd() ) {
			predicateList.add(cb.lessThan(root.get(Attachment.PROPERTY_CREATE_TIME).as(Date.class), condition.getCreateTimeEnd()));
		}
	}
}


