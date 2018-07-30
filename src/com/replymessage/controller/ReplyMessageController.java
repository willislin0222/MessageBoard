package com.replymessage.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.member.model.MemberService;
import com.member.model.MemberVO;
import com.message.model.MessageVO;
import com.replymessage.model.ReplyMessageService;
import com.replymessage.model.ReplyMessageVO;

@Controller
@MultipartConfig
@RequestMapping("/replymessage")
public class ReplyMessageController {
	
	
	@RequestMapping(method = RequestMethod.GET, value = "addReplyMessage")
	public String addReplyMessage(ModelMap model,HttpSession session,@RequestParam("mes_no") Integer mes_no) {
		MemberVO memberVO = new MemberVO();
		MessageVO messageVO = new MessageVO();
		ReplyMessageVO replymessageVO = new ReplyMessageVO();
//		判斷是否有登入會員，如果沒有自動導向登入頁面
		memberVO = (MemberVO) session.getAttribute("memberVO");
		if(memberVO == null){
			MemberVO memberVO1 = new MemberVO();
			model.addAttribute("memberVO", memberVO1);
			return "member/login";
		}else{
			messageVO.setMes_no(mes_no);
			replymessageVO.setMessageVO(messageVO);
			model.addAttribute("replymessageVO", replymessageVO);
			return "replymessage/addReplyMessage";
		}
	}
//	//前往回復留言頁面
//	@RequestMapping(method = RequestMethod.GET, value = "addReplyMessage")
//	public String addReplyMessage(ModelMap model,@RequestParam("mes_no") Integer mes_no,HttpSession session) {
//		ReplyMessageVO replymessageVO = new ReplyMessageVO();
//		MessageVO messageVO = new MessageVO();
//		messageVO.setMes_no(mes_no);
//		MemberVO memberVO =(MemberVO)session.getAttribute("memberVO");
//		replymessageVO.setMessageVO(messageVO);
//		replymessageVO.setMemberVO(memberVO);
//		model.addAttribute("replymessageVO", replymessageVO);
//		return "replymessage/addReplyMessage";
//	}
	//新增回復
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST, value = "insert")
	public Map<String,Object> insert(ModelMap model,HttpServletRequest request,HttpSession session,
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@RequestParam("mes_no") Integer mes_no,@Valid ReplyMessageVO replyMessageVO,BindingResult result) {
			Map<String, Object> map = new HashMap<String, Object>();  
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			MessageVO messageVO =new MessageVO();
			messageVO.setMes_no(mes_no);
			if(memberVO == null){
				map.put("result", "nologin");
				return map;
			}else if(result.hasErrors()){
				map.put("result", "empty");
				return map;
			}
			/***************************2.開始新增資料***************************************/
			ReplyMessageService replyMessageSvc = new ReplyMessageService();
			//取得今日日期
			Timestamp replydata = new Timestamp(System.currentTimeMillis());
			replyMessageVO.setMemberVO(memberVO);
			replyMessageVO.setMessageVO(messageVO);
			replyMessageVO.setRep_date(replydata);
			replyMessageSvc.addReplyMessage(replyMessageVO);
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			return map;
			
			
	}
	
	//刪除增回復
	@RequestMapping(method = RequestMethod.POST, value = "delete")
	public String delete(ModelMap model,
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@RequestParam("rep_no") Integer rep_no) {
			/***************************2.開始刪除資料***************************************/
			ReplyMessageService replyMessageSvc = new ReplyMessageService();
			ReplyMessageVO replyMmessageVO = replyMessageSvc.findPrimaryKey(rep_no);
			if(replyMmessageVO != null){
				replyMessageSvc.delete(rep_no);
			}
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			return "message/listAllMessage";
			
			
	}
	
	//取得修改資料
	@RequestMapping(method = RequestMethod.GET, value = "getupdate")
	public String getupdate(ModelMap model,
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@RequestParam("rep_no") Integer rep_no) {
			/***************************2.開始取得修改除資料***************************************/
			ReplyMessageService replyMessageSvc = new ReplyMessageService();
			ReplyMessageVO replyMessageVO = new ReplyMessageVO();
			replyMessageVO = replyMessageSvc.findPrimaryKey(rep_no);
			model.addAttribute("replyMessageVO", replyMessageVO);
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			return "replymessage/upateReplyMessage";
			
			
	}
	
	//修改資料
	@RequestMapping(method = RequestMethod.POST, value = "update")
	public String update(ModelMap model,
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@Valid ReplyMessageVO ReplyMessageVO) {
			/***************************2.開始取得修改除資料***************************************/
			ReplyMessageService replyMessageSvc = new ReplyMessageService();
			replyMessageSvc.updateReplyMessage(ReplyMessageVO);
			model.addAttribute("ReplyMessageVO", ReplyMessageVO);
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			return "message/listAllMessage";
			
			
	}
	
	
	//登入會員(給replymessage導向登入頁面用)
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
