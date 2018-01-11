package com.example.demo.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Created by jh on 2018-01-10.
 */
public class BeforeLoginFilter extends GenericFilterBean {

	@Override public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		System.out.println("This is a filter before UsernamePasswordAuthenticationFilter");
		filterChain.doFilter(servletRequest,servletResponse);
	}
}
