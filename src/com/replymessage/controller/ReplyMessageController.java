package com.replymessage.controller;

import java.sql.Date;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.member.model.MemberVO;
import com.message.model.MessageVO;
import com.replymessage.model.ReplyMessageService;
import com.replymessage.model.ReplyMessageVO;

@Controller
@MultipartConfig
@RequestMapping("/replymessage")
public class ReplyMessageController {
	
	//前往回復留言頁面
	@RequestMapping(method = RequestMethod.GET, value = "addReplyMessage")
	public String addReplyMessage(ModelMap model,@RequestParam("mes_no") Integer mes_no,HttpSession session) {
		ReplyMessageVO replymessageVO = new ReplyMessageVO();
		MessageVO messageVO = new MessageVO();
		messageVO.setMes_no(mes_no);
		MemberVO memberVO =(MemberVO)session.getAttribute("memberVO");
		replymessageVO.setMessageVO(messageVO);
		replymessageVO.setMemberVO(memberVO);
		model.addAttribute("replymessageVO", replymessageVO);
		return "replymessage/addReplyMessage";
	}
	//新增回復
	@RequestMapping(method = RequestMethod.POST, value = "insert")
	public String insert(ModelMap model,@Valid ReplyMessageVO replyMessageVO,
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@RequestParam("rep_text") String rep_text) {
			/***************************2.開始新增資料***************************************/
			ReplyMessageService replyMessageSvc = new ReplyMessageService();
			//取得今日日期
			Date replydata = new Date(System.currentTimeMillis());
			replyMessageVO.setRep_date(replydata);
			replyMessageSvc.addReplyMessage(replyMessageVO);
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			return "message/listAllMessage";
			
			
	}

}
