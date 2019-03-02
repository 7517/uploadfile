package com.edison.saas.bootstrap.common.business.attachment.service;


import com.edison.saas.bootstrap.common.business.attachment.dao.AttachmentDao;
import com.edison.saas.bootstrap.common.business.attachment.dao.AttachmentSpecification;
import com.edison.saas.bootstrap.common.business.attachment.domain.Attachment;
import com.edison.saas.bootstrap.common.business.attachment.dto.AttachmentCondition;
import com.edison.saas.common.jpa.GenericCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service("attachmentService")
public class AttachmentService  extends GenericCrudService<Attachment, Long, AttachmentCondition, AttachmentDao> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentService.class);

	@Override
	public Specification<Attachment> getSpecification(AttachmentCondition condition) {
		return new AttachmentSpecification(condition);
	}

	public Sort getDefaultSort(){

		Sort sort = new Sort(Sort.Direction.DESC , Attachment.PROPERTY_NAME);
		return sort;
	}
}