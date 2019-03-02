package com.edison.saas.bootstrap.application.startcheck;

import com.edison.saas.bootstrap.application.business.application.service.AppRibbonService;
import com.edison.saas.platform.services.platform.business.application.dto.AppAddDto;
import com.edison.saas.platform.services.platform.business.application.vo.AppVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 应用的基础信息
 */
@Component
@Order(value=1)
public class ApplicationConfigCheckService implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfigCheckService.class);

	@Value("${application.code:-1}")
	private Long appCode;

	@Value("${application.displayName:未设置}")
	private String displayName;

	@Autowired
    private AppRibbonService appService;

    @Override
    public void run(String... args) throws Exception {

    	if(-1L == appCode){
    		LOGGER.error("需要在配置文件中设置 applicationCode.");
			TimeUnit.SECONDS.sleep(1);
    		System.exit(1);
		}


    	//每个新应用, 都有个应用管理员
		AppAddDto app = new AppAddDto();
		app.setId(appCode);
		app.setCode(appCode+"");
    	app.setName(displayName);
		app.setVisible(false);
		app.setOnBoardTime(new Date());
    	check(app);


    }
    private void check(AppAddDto target){
	    LOGGER.info("[check]:{}", target);
		AppVO entity = null;
	    try{
			entity = appService.getByAppCode(Long.parseLong(target.getCode()));
		}catch (Throwable t){
	    	LOGGER.error(t.getMessage(), t);
		}

    	if(entity == null){
			appService.add(target);
	    }
    }

}