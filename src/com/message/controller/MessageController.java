package com.message.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.message.model.MessageService;
import com.message.model.MessageVO;

@Controller
@MultipartConfig
@RequestMapping("/message")
public class MessageController {
	
	//前往新增留言頁面
	@RequestMapping(method = RequestMethod.GET, value = "addMessage")
	public String addMessage(ModelMap model,HttpSession session) {
		MemberVO memberVO = new MemberVO();
		MessageVO messageVO = new MessageVO();
//		判斷是否有登入會員，如果沒有自動導向登入頁面
		memberVO = (MemberVO) session.getAttribute("memberVO");
		if(memberVO == null){
			MemberVO memberVO1 = new MemberVO();
			model.addAttribute("memberVO", memberVO1);
			return "member/login";
		}else{
			model.addAttribute("messageVO", messageVO);
			return "message/addMessage";
		}
	}
	//新增留言
	@RequestMapping(method = RequestMethod.POST, value = "insert")
	public String insert(ModelMap model,HttpSession session,
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@RequestParam("mes_title") String mes_title,
			@RequestParam("mes_text") String mes_text) {
			/***************************2.開始新增資料***************************************/
			MessageService messageSvc = new MessageService();
			MessageVO messageVO = new MessageVO();
			messageVO.setMemberVO((MemberVO)session.getAttribute("memberVO"));
			messageVO.setMes_title(mes_title);
			messageVO.setMes_text(mes_text);
			messageVO.setMes_date(new Timestamp(System.currentTimeMillis()));
			messageSvc.addMessage(messageVO);
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			return "message/listAllMessage";
		
		
	}
	
	//登入會員(給message導向登入頁面用)
	@RequestMapping(method = RequestMethod.POST, value ="login")
	public String login(ModelMap model,HttpSession session, 
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@RequestParam("mem_id") String mem_id,
	   		@RequestParam("mem_psw") String mem_psw) throws IOException{
		/***************************2.開始查詢資料***************************************/
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.fingByMemid(mem_id);
		/***************************3.新增完成,準備轉交(Send the Success view)***********/
		if(memberVO.getMem_no() == null){
			model.addAttribute("error", "查無帳號");
			MemberVO memberVO1 = new MemberVO();
			model.addAttribute("memberVO", memberVO1);
			return "member/login";
		}else if((!mem_psw.equals(memberVO.getMem_psw()))){
			model.addAttribute("error", "密碼錯誤");
			MemberVO memberVO1 = new MemberVO();
			model.addAttribute("memberVO", memberVO1);
			return "member/login";
		}else{
			session.setAttribute("memberVO", memberVO);
			return "message/listAllMessage";
		}
			
	}

}

