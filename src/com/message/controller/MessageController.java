package com.message.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

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
import com.message.model.MessageService;
import com.message.model.MessageVO;

@Controller
@RequestMapping("/message")
public class MessageController {
	
	
	//前往留言頁面
	@RequestMapping(method = RequestMethod.GET, value = "index")
	public String index(ModelMap model,HttpSession session) {
		return "message/listAllMessage";
	}
	
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
	@ResponseBody 
	@RequestMapping(method = RequestMethod.POST, value = "insert")
	public Map<String, Object> insert(ModelMap model,HttpSession session,HttpServletRequest request,
			@RequestParam("requestURL") String requestURL,@RequestParam("whichPage") String whichPage,
			@RequestParam("pageNumber") String pageNumber,
			@Valid MessageVO messageVO,BindingResult result) {
	    	/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			Map<String, Object> map = new HashMap<String, Object>();  
			MemberVO memberVO = (MemberVO)session.getAttribute("memberVO");
			if(memberVO == null){
				map.put("result", "nologin");
				return map;
			}else if(result.hasErrors()){
				map.put("result", "empty");
				return map;
			}
			/***************************2.開始新增資料***************************************/
		    
			MessageService messageSvc = new MessageService();
//			MessageVO messageVO = new MessageVO();
			messageVO.setMemberVO((MemberVO)session.getAttribute("memberVO"));
			messageVO.setMes_title(messageVO.getMes_title());
			messageVO.setMes_text(messageVO.getMes_text());
			messageVO.setMes_date(new Timestamp(System.currentTimeMillis()));
			messageSvc.addMessage(messageVO);
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			map.put("result", "success");
			map.put("whichPage", whichPage);
			map.put("requestURL", requestURL);
			map.put("pageNumber", pageNumber);
			return map;
			
		
	}
	
	//刪除留言
	@RequestMapping(method = RequestMethod.GET, value = "delete")
	public String delete(ModelMap model,HttpSession session,
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@RequestParam("mes_no") Integer mes_no) {
			/***************************2.開始新增資料***************************************/
			MessageService messageSvc = new MessageService();
			MessageVO messageVO = messageSvc.findPrimaryKey(mes_no);
			if(messageVO != null){
				messageSvc.delete(mes_no);
			}
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			return "message/listAllMessage";
		
		
	}
	
	//取得修改資料
	@RequestMapping(method = RequestMethod.GET, value = "getupdate")
	public String getupdate(ModelMap model,
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@RequestParam("mes_no") Integer mes_no) {
			/***************************2.開始取得修改除資料***************************************/
			MessageService MessageSvc = new MessageService();
			MessageVO messageVO = new MessageVO();
			messageVO = MessageSvc.findPrimaryKey(mes_no);
			model.addAttribute("messageVO", messageVO);
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			return "message/updateMessage";
			
			
	}
	
	//修改留言
	@RequestMapping(method = RequestMethod.POST, value = "update")
	public String update(ModelMap model,
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@Valid MessageVO messageVO) {
			/***************************2.開始取得修改除資料***************************************/
			MessageService MessageSvc = new MessageService();
			MessageSvc.updateMessage(messageVO);
			model.addAttribute("messageVO", messageVO);
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

