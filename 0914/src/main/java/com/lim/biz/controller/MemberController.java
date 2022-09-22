package com.lim.biz.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lim.biz.member.MemberVO;
import com.lim.biz.member.impl.MemberDAO;

@Controller

public class MemberController {
	@RequestMapping(value="/login.do",method=RequestMethod.GET)
	public String login() {
		return "login.jsp";
	}
	
	@RequestMapping(value="/signin.do",method=RequestMethod.GET)
	public String signin() {
		System.out.println("signin GET방식");
		return "signin.jsp";
	}
	
	@RequestMapping(value="/login.do", method=RequestMethod.POST)
	public String selectOne(ModelAndView mav,MemberVO mVO,MemberDAO mDAO,HttpSession session){

		mVO=mDAO.selectOne(mVO);
		if(mVO==null) {
			return "login.jsp";
		}
		else {
			session.setAttribute("member", mVO.getMid());
			
			return "redirect:main.do";
		}

	}
	@RequestMapping(value="/logout.do", method=RequestMethod.GET)
	public String logout(HttpSession session) {

		session.invalidate();
		return "redirect:main.do";

	}
	
	
	@RequestMapping(value="/signin.do",method=RequestMethod.POST)
	public String signin(MemberVO mVO,MemberDAO mDAO) {
		mDAO.insert(mVO);
		
		return "redirect:main.do";

	}
	@RequestMapping(value="/update.do",method=RequestMethod.POST)
	public String update(MemberVO vo,MemberDAO dao,HttpSession session){
		if(vo != null) {
			
			dao.update(vo);
			
			session.setAttribute("member", vo);
		}
		else {
			return"signin.jsp";
		}
		return "redirect:main.do";
	}
	@RequestMapping(value="/delete.do",method=RequestMethod.GET)
	public String delete(MemberVO vo,MemberDAO dao,HttpSession session ) {

//		vo = (MemberVO)session.getAttribute("member"); 
		vo = dao.selectOne(vo);

		if(vo != null) {
			
			dao.delete(vo);
			session.invalidate();
		}
		return "redirect:main.do";
	}
	@RequestMapping("/mypage.do")
	public String mypage(@ModelAttribute("member")MemberVO vo,Model model,MemberDAO dao,HttpSession session) {
		
		if(vo != null) {
			model.addAttribute("member", vo);
			
			return "mypage.jsp";
		}
		else {
			return "main.do";
		}	
		
	}
	
	

}
