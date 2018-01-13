package com.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.member.model.MemberService;
import com.member.model.MemberVO;

@Controller
@MultipartConfig
@RequestMapping("/member")
public class MemberController {
	
	//前往會員登入頁面
	@RequestMapping(method = RequestMethod.GET, value = "loginMember")
	public String setMember(ModelMap model) {
		MemberVO memberVO = new MemberVO();
		model.addAttribute("memberVO", memberVO);
		return "member/login";
	}
	
	//登入會員
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
			setMember(model);
			return "member/login";
		}else if((!mem_psw.equals(memberVO.getMem_psw()))){
			model.addAttribute("error", "密碼錯誤");
			setMember(model);
			return "member/login";
		}else{
			session.setAttribute("memberVO", memberVO);
			return "message/listAllMessage";
		}
		
	}
	
	//登出會員
	@RequestMapping(method = RequestMethod.GET, value = "logout")
	public String logout(ModelMap model,HttpSession session) {
		session.invalidate();
		return "message/listAllMessage";
	}
	
	//前往會員註冊頁面
	@RequestMapping(method = RequestMethod.GET, value = "joinMember")
	public String joinMember(ModelMap model) {
		setMember(model);
		return "member/joinMember";
	}
	
	//新增會員
	@RequestMapping(method = RequestMethod.POST, value ="insert")
	public String insert(ModelMap model, 
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@RequestParam("mem_photo") MultipartFile inputfile,	
			@RequestParam("mem_id") String mem_id,
    		@RequestParam("mem_psw") String mem_psw) throws IOException{
//		if (result.hasErrors()) {
//			return "member/addMember";
//		}
		/***************************2.開始新增資料***************************************/
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = new MemberVO();
		byte[] mem_photo = inputfile.getBytes();  //取得前端傳來的圖片資料
		Date today = new Date(System.currentTimeMillis());

		memberVO.setMem_id(mem_id);
		memberVO.setMem_psw(mem_psw);
		memberVO.setMem_joindate(today);
		memberVO.setMem_photo(mem_photo);
		memberSvc.addMember(memberVO);

		/***************************3.新增完成,準備轉交(Send the Success view)***********/
		model.addAttribute("success", "- (新增成功)");
		setMember(model);
		return "member/login"; 
	}
	

//	前往修改密碼頁面
	@RequestMapping(method = RequestMethod.GET, value = "getOneForUpdate")
	public String getOneForUpdate(ModelMap model,@RequestParam("mem_no") Integer mem_no) {
		/***************************2.開始查詢資料***************************************/
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.fingPrimaryKey(mem_no);
		model.addAttribute("memberVO", memberVO);
		return "member/updateMember";
	}
	
	//新增會員資料
	@RequestMapping(method = RequestMethod.POST, value ="updateMember")
	public String updateMember(ModelMap model, 
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@RequestParam("mem_id") String mem_id,
    		@RequestParam("mem_psw") String mem_psw){
		/***************************2.開始修改資料***************************************/
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.fingByMemid(mem_id);
		memberVO.setMem_psw(mem_psw);
		memberSvc.updatedMember(memberVO);

		/***************************3.新增完成,準備轉交(Send the Success view)***********/
		model.addAttribute("success", "- (修改成功)");
		setMember(model);
		return "member/login"; 
	}
	
	//圖片上傳測試
//	@RequestMapping(method = RequestMethod.POST, value = "demo")
//    public String demo(@RequestParam("mem_photo") MultipartFile file,
//                            HttpServletResponse response,
//                            HttpServletRequest request,
//                            
//                            ==================取得資料============
//                    		@RequestParam("mem_id") String mem_id,
//                    		@RequestParam("mem_psw") String mem_psw) throws IOException {
//		

//		return null;
//    }

}
