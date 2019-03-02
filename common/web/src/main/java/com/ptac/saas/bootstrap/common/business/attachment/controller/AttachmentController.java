package com.edison.saas.bootstrap.common.business.attachment.controller;


import com.edison.saas.bootstrap.common.business.attachment.domain.Attachment;
import com.edison.saas.bootstrap.common.business.attachment.dto.AttachmentAddDto;
import com.edison.saas.bootstrap.common.business.attachment.dto.AttachmentCondition;
import com.edison.saas.bootstrap.common.business.attachment.dto.AttachmentEditDto;
import com.edison.saas.bootstrap.common.business.attachment.service.AttachmentService;
import com.edison.saas.bootstrap.common.business.attachment.valid.AttachmentValidator;
import com.edison.saas.bootstrap.common.business.attachment.vo.AttachmentVO;
import com.edison.saas.common.framework.web.controller.PageContent;
import com.edison.saas.common.framework.web.data.PageRequest;
import com.edison.saas.common.framework.web.data.PageRequestConvert;
import com.edison.saas.common.framework.web.data.PageSearchRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 管理Attachment
 * @author icode
 */
@Api(description = "Attachment", tags = "Attachment")
@RestController
@RequestMapping(value = "/common/attachment")
public class AttachmentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentController.class);


	@Autowired
	private AttachmentService attachmentService;


	@Autowired
	private AttachmentValidator attachmentValidator;

    @InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.addValidators(attachmentValidator);
	}

	/**
	 * 新增Attachment
	 * @param attachmentAddDto
	 * @return
	 */
	@ApiOperation(value = "新增", notes = "新增Attachment", httpMethod = "POST")
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
	public AttachmentVO add(@RequestBody @Valid AttachmentAddDto attachmentAddDto){
		Attachment attachment = new Attachment();
		BeanUtils.copyProperties(attachmentAddDto, attachment);

		//.attachment.sett(SecurityUtil.getAccount().getTenantId());
		attachmentService.add(attachment);

		return  initViewProperty(attachment);
	}

	/**
	 * 删除Attachment,id以逗号分隔
	 * @param idArray
	 */
	@ApiOperation(value = "删除", notes = "删除Attachment", httpMethod = "DELETE")
	@DeleteMapping(value="/{idArray}")
	public void delete(@PathVariable String idArray){

	    LOGGER.debug("delete attachment :{}", idArray);

		String[] ids = idArray.split(",");
		for (String id : ids ){
			attachmentService.delete(Long.valueOf(id));
		}

	}

	/**
	 * 更新Attachment
	 * @param attachmentEditDto
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "修改", notes = "修改产Attachment(修改全部字段,未传入置空)", httpMethod = "PUT")
	@PutMapping(value="/{id}")
	public AttachmentVO update(@RequestBody @Valid AttachmentEditDto attachmentEditDto, @PathVariable Long id){
		Attachment attachment = new Attachment();
		BeanUtils.copyProperties(attachmentEditDto, attachment);
		attachment.setId(id);
		attachmentService.merge(attachment);

		AttachmentVO vo = initViewProperty(attachment);
		return  vo;
	}

	/**
	 * 根据ID查询Attachment
	 * @param id
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据ID查询Attachment", httpMethod = "GET")
	@GetMapping(value="/{id}")
	public AttachmentVO get(@PathVariable Long id) {

		Attachment attachment = attachmentService.find(id);

		AttachmentVO vo = initViewProperty(attachment);
		return vo;
	}

	/**
	 * 查询Attachment列表
	 * @param pageSearchRequest
	 * @return
	 */
	@ApiOperation(value = "查询", notes = "根据条件查询Attachment列表", httpMethod = "POST")
	@PostMapping("/list")
	public PageContent<AttachmentVO> list(@RequestBody PageSearchRequest<AttachmentCondition> pageSearchRequest){

		PageRequest pageRequest = PageRequestConvert.convert(pageSearchRequest);

		Page<Attachment> page = attachmentService.find(pageSearchRequest.getSearchCondition(), pageRequest);

		List<AttachmentVO> voList = new ArrayList<>();
		for(Attachment attachment : page.getContent()){
			voList.add(initViewProperty(attachment));
		}

		PageContent<AttachmentVO> pageContent = new PageContent<>(voList, page.getTotalElements());
		LOGGER.debug("page Size :{}, total:{}", pageContent.getContent().size() ,pageContent.getTotal());
		return pageContent;

	}

	private AttachmentVO initViewProperty(Attachment attachment){
	    AttachmentVO vo = new AttachmentVO();

        BeanUtils.copyProperties(attachment, vo);

	    //初始化其他对象
        return vo;
	}


}
