package com.edison.saas.bootstrap.application.startcheck;


import com.edison.saas.bootstrap.application.business.security.service.AccountRibbonService;
import com.edison.saas.bootstrap.application.business.security.service.AccountRoleRelationRibbonService;
import com.edison.saas.bootstrap.application.business.security.service.RoleRibbonService;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountAddDto;
import com.edison.saas.platform.services.platform.business.security.account.dto.AccountRoleRelationAddDto;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountRoleRelationVO;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import com.edison.saas.platform.services.platform.business.security.role.dto.RoleAddDto;
import com.edison.saas.platform.services.platform.business.security.role.vo.RoleVO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 检查角色的初始化配置
 */
@Component
@Order(value=1)
@Slf4j
public class ApplicationAccountRoleConfigCheckService implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAccountRoleConfigCheckService.class);

	@Value("${application.code:-1}")
	private Long appCode;

	@Value("${application.displayName:未设置}")
	private String displayName;

	@Autowired
    private AccountRibbonService accountService;


	@Autowired
	private RoleRibbonService roleService;

	@Autowired
	private AccountRoleRelationRibbonService accountRoleRelationService;

    @Override
    public void run(String... args) throws Exception {

		checkAccount();
		checkRole();

		AccountRoleRelationAddDto accountRoleRelation = new AccountRoleRelationAddDto();
		accountRoleRelation.setAccount(appCode);
		accountRoleRelation.setRole(appCode);
		accountRoleRelation.setId(appCode);
		accountRoleRelation.setTid(1L);
		check(accountRoleRelation);

    }

    public void checkAccount(){

		AccountAddDto account = new AccountAddDto();
		account.setId(appCode);
		account.setTid(1L);
		account.setAccountName("app"+appCode);
		account.setName("应用管理员");
		account.setNickName("应用管理员("+displayName+")");
		account.setEnable(true);
		account.setExpired(false);
		account.setLocked(false);
		account.setDeleted(false);
		account.setInitPwd("123456");

		check(account);

	}

	private void check(AccountAddDto target){
		LOGGER.info("[check]:{}", target);
		AccountVO entity = null;
		try {
			entity = accountService.find(target.getId());
		}catch(Throwable e){
			log.error(e.getMessage(), e);
		}
		if (entity == null) {
			accountService.add(target);
		}
	}

	public void checkRole(){
		RoleAddDto role = new RoleAddDto();
		role.setId(appCode);
		role.setName("应用管理员");
		role.setCode("APP_ADMIN");
		role.setTid(1L);
		role.setAppCode(appCode+"");
		role.setDescription("应用管理员");

		RoleVO entity = null;
		try {
			entity = roleService.find(appCode);
		}catch(Throwable e){
			log.error(e.getMessage(), e);
		}
		if (entity == null) {
			roleService.add(role);
		}
	}


	private void check(AccountRoleRelationAddDto target){
		LOGGER.info("[check]:{}", target);

		AccountRoleRelationVO entity = null;
		try {
			entity = accountRoleRelationService.find(target.getId());
		}catch(Throwable e){
			log.error(e.getMessage(), e);
		}
		if (entity == null) {
			accountRoleRelationService.add(target);
		}
	}
}