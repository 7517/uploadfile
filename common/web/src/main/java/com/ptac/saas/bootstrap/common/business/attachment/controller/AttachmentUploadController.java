package com.edison.saas.bootstrap.common.business.attachment.controller;

import com.edison.saas.bootstrap.common.business.attachment.domain.Attachment;
import com.edison.saas.bootstrap.common.business.attachment.dto.FileIputResponse;
import com.edison.saas.bootstrap.common.business.attachment.service.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 管理附件
 */
@RestController
public class AttachmentUploadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentUploadController.class);

	private String uploadPath;

	@Autowired
	private AttachmentService attachementService;

	@Value("${application.uploadFilePath:/Users/gonghongrui/studio/git_aicoder/devp/apps/maintenance/web/attachment}")
	public void setUploadPath(String uploadFilePath) {
		uploadPath = uploadFilePath;
	}


	@PostMapping(value = "/common/attachment/upload")
	public @ResponseBody
    FileIputResponse upload(MultipartHttpServletRequest request, HttpServletResponse response) {
		LOGGER.debug("uploadPost called");
		Iterator<String> itr = request.getFileNames();
		MultipartFile mpf;
		List<Attachment> list = new LinkedList<>();

		Attachment attachment = new Attachment();
		//只处理第一个文件
		if (itr.hasNext()) {
			mpf = request.getFile(itr.next());

			LOGGER.debug("Uploading {}", mpf.getOriginalFilename());

			String newFilenameBase = UUID.randomUUID().toString();
			String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf('.'));
			String newFilename = newFilenameBase + originalFileExtension;
			String storageDirectory = uploadPath;
			String contentType = mpf.getContentType();

			File newFile = new File(storageDirectory + File.separator + newFilename);
			try {
				long size = mpf.getSize();
				mpf.transferTo(newFile);

				attachment.setName(mpf.getOriginalFilename());
				attachment.setType(contentType);
				attachment.setNewFileName(newFilename);
				attachment.setContentType(contentType);
				attachment.setSize(size);
				attachment.setDisabled(false);
				attachment.setCreateTime(new Date());
				attachementService.add(attachment);

				list.add(attachment);
				LOGGER.info("{} store in path:{}",attachment.getName(), newFile.getAbsolutePath());
				return FileIputResponse.success(attachment.getName(),  "120px", "common/attachment/download/"+attachment.getId(), 111, attachment.getId()+"");

			} catch(IOException e) {
				LOGGER.error("Could not upload file "+mpf.getOriginalFilename(), e);
			}
		}
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		return FileIputResponse.failure(attachment.getName(),  "120px", "common/attachment/download/"+attachment.getId(), 111, null);
	}


	/**
	 * 根据ID查询附件
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/common/attachment/download/{id}")
	public void get(@PathVariable Long id, HttpServletResponse response) {

		Attachment attachment = attachementService.find(id);
		String path = uploadPath;


		try(InputStream in = new FileInputStream(new File(path + File.separator + attachment.getNewFileName())); OutputStream out = response.getOutputStream() ) {

			response.setCharacterEncoding("UTF-8");

			response.setContentType(attachment.getContentType());
			response.setHeader("Content-Disposition", "attachment;fileName=" + new String(attachment.getName().getBytes("utf-8"), "ISO-8859-1"));

			byte[] b = new byte[2048];
			int length;
			while ((length = in.read(b)) > 0) {
				out.write(b, 0, length);
			}
			out.flush();
		}  catch (IOException e) {
			LOGGER.error("", e);
		}

		//  返回值要注意，要不然就出现下面这句错误！
		//java+getOutputStream() has already been called for this response
	}


}
