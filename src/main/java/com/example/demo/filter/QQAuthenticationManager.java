package com.example.demo.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.domain.QQUser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.filter.QQAuthenticationFilter.clientId;

/**
 * Created by jh on 2018-01-10.
 */
public class QQAuthenticationManager implements AuthenticationManager {

	private final static List<GrantedAuthority> AUTHORITIES = new ArrayList<>();

	/**
	 * 获取 QQ 登录信息的 API 地址
	 */
	private final static String userInfoUri = "https://graph.qq.com/user/get_user_info";

	/**
	 * 获取 QQ 用户信息的地址拼接
	 */
	private final static String USER_INFO_API = "%s?access_token=%s&oauth_consumer_key=%s&openid=%s";

	static {
		AUTHORITIES.add(new SimpleGrantedAuthority("ROLE_USER"));
	}


	@Override public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (authentication.getName() != null && authentication.getCredentials() != null) {
			QQUser user = getUserInfo(authentication.getName(), (String) authentication.getCredentials());
			return new UsernamePasswordAuthenticationToken(user, null, AUTHORITIES);
		}
		throw new BadCredentialsException("Bad Credentials");
	}

	private QQUser getUserInfo(String accessToken, String openId) {
		String url = String.format(USER_INFO_API, userInfoUri,accessToken,clientId,openId);
		Document document;
		try {
			document = Jsoup.connect(url).get();
		} catch (IOException e) {
			throw new BadCredentialsException("Bad Credential!");
		}
		String resultText = document.text();
		JSONObject json = JSON.parseObject(resultText);

		QQUser qqUser = new QQUser();
		qqUser.setNickname(json.getString("nickname"));
		qqUser.setGender(json.getString("gender"));
		qqUser.setProvince(json.getString("province"));
		qqUser.setYear(json.getString("year"));
		qqUser.setAvatar(json.getString("figureurl_qq_2"));
		return qqUser;
	}
}
