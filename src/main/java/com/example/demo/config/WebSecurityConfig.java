package com.example.demo.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.demo.filter.QQAuthenticationFilter;
import com.example.demo.filter.QQAuthenticationManager;

/**
 * Created by jh on 2018-01-09.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 匹配 "/" 路径，不需要权限即可访问
	 * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
	 * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
	 * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
	 * 默认启用 CSRF
	 */
	@Override public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/user/**").hasRole("USER")
				.and()
				.formLogin().loginPage("/login").defaultSuccessUrl("/user")
				.and()
				.logout().logoutUrl("/logout").logoutSuccessUrl("/login");

		// 在 UsernamePasswordAuthenticationFilter 前添加 BeforeLoginFilter
		http.addFilterBefore(qqAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		// 在 CsrfFilter 后添加 AfterCsrfFilter
//		http.addFilterAfter(new AfterCsrfFilter(), CsrfFilter.class);

	}


	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		builder.inMemoryAuthentication().withUser("ytl").password("pwd").roles("USER");
	}

	private QQAuthenticationFilter qqAuthenticationFilter() {
		QQAuthenticationFilter authenticationFilter = new QQAuthenticationFilter("/login/qq");
		SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
		successHandler.setAlwaysUseDefaultTargetUrl(true);
		successHandler.setDefaultTargetUrl("/user");
		authenticationFilter.setAuthenticationManager(new QQAuthenticationManager());
		authenticationFilter.setAuthenticationSuccessHandler(successHandler);
		return authenticationFilter;
	}
 }
