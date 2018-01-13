package com.message.controller;

import java.sql.Date;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.member.model.MemberVO;
import com.message.model.MessageService;
import com.message.model.MessageVO;

@Controller
@MultipartConfig
@RequestMapping("/message")
public class MessageController {
	
	//前往新增留言頁面
	@RequestMapping(method = RequestMethod.GET, value = "addMessage")
	public String addMessage(ModelMap model) {
		MessageVO messageVO = new MessageVO();
		model.addAttribute("messageVO", messageVO);
		return "message/addMessage";
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
			messageVO.setMes_date(new Date(System.currentTimeMillis()));
			messageSvc.addMessage(messageVO);
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			return "message/listAllMessage";
		
		
	}

}

