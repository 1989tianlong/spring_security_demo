package com.example.demo.web;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.domain.QQUser;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;

/**
 * Created by jh on 2018-01-09.
 */
@Controller
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/user")
	public String user(@AuthenticationPrincipal UsernamePasswordAuthenticationToken userAuthentication, Model model){
		QQUser user = (QQUser) userAuthentication.getPrincipal();
		model.addAttribute("username", user.getNickname());
		model.addAttribute("avatar", user.getAvatar());
		return "user/user";
	}




}
