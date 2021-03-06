package com.hdac.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hdac.auth.Auth;
import com.hdac.auth.AuthService;
import com.hdac.common.BeanUtil;
import com.hdac.common.JsonUtil;
import com.hdac.service.member.MemberService;
import com.hdac.service.member.MemberServiceImpl;

@Controller
public class HomeController
{
	@RequestMapping(value="/home.hdac", method=RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView view = new ModelAndView("home/home");

		Map<String, Object> resultMap = new HashMap<String, Object>();

		MemberService service = (MemberService)BeanUtil.getBean(MemberServiceImpl.class);
		resultMap.put("list", service.getMemberList());

		Auth auth = AuthService.getAuth();
		long userNo = auth.getAuth(request, response);

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);

		resultMap.put("user", service.getMember(paramMap));

		view.addObject("data", JsonUtil.toJsonString(resultMap).toString());

		return view;
	}
}