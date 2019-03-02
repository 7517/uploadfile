package com.edison.saas.bootstrap.security.app.business.authorize.domain;


import com.edison.saas.platform.services.platform.business.security.account.vo.AccountPasswordVO;
import com.edison.saas.platform.services.platform.business.security.account.vo.AccountVO;
import com.edison.saas.platform.services.platform.business.security.resource.vo.ResourceVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gonghongrui on 2017/1/10.
 */
public class SecurityUser implements UserDetails {

	private AccountVO account;
	private String password;
	private Set<SecurityAuthority> authorities = new HashSet<>();

	public SecurityUser(AccountVO account, List<ResourceVO> resourceList){
		this.account = account;

		if(CollectionUtils.isNotEmpty(resourceList)) {
			for (ResourceVO resource : resourceList) {
				authorities.add(new SecurityAuthority(resource.getUrl()));
			}

		}
	}

	public SecurityUser(AccountVO account){

		this.account = account;

	}

	public SecurityUser(AccountVO account, AccountPasswordVO accountPassword, List<ResourceVO> resourceList){

		this(account, resourceList);
		this.password = accountPassword.getPassword();

	}

	public AccountVO getAccount() {
		return account;
	}
	public void setAccount(AccountVO account) {
		this.account = account;
	}

	@Override
	public Collection<SecurityAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return account.getAccountName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return account.getEnable();
	}

	public void setAuthorities(Set<SecurityAuthority> authorities) {
		this.authorities = authorities;
	}
	public void addAuthority(SecurityAuthority securityAuthority){
		this.authorities.add(securityAuthority);
	}
}
