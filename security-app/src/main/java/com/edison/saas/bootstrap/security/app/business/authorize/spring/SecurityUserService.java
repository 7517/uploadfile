package com.edison.saas.bootstrap.security.app.business.authorize.spring;


import com.edison.saas.bootstrap.security.app.business.authorize.domain.SecurityAuthority;
import com.edison.saas.bootstrap.security.app.business.authorize.domain.SecurityUser;
import com.edison.saas.platform.services.application.client.security.AccountClient;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by gonghongrui on 2017/1/10.
 */
@Service
public class SecurityUserService implements UserDetailsService {

	@Autowired
	private AccountClient accountClient;

	/**
	 * @param username
	 * @return
	 * @throws UsernameNotFoundException
	 */
	@Override
	public SecurityUser loadUserByUsername(String username) throws UsernameNotFoundException {

		AccountVO accountVO = accountClient.getByAccountName(username).getData();
		if(accountVO == null){
			throw new UsernameNotFoundException("找不到用户:"+username);
		}

		SecurityUser securityUser = new SecurityUser(accountVO);
//		securityUser.addAuthority(new SecurityAuthority("ROLE_ACTUATOR"));

		if("actuator".equals(username)){
			securityUser.addAuthority(new SecurityAuthority("ACTUATOR"));
		}
		return securityUser;
	}

}
