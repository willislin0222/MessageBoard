package com.member.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
	public String login(HttpSession session,ModelMap model, 
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/		
			@Valid MemberVO memberVO, BindingResult result) throws IOException{	
			if(result.hasErrors()){
				return "member/login";
			}
		/***************************2.開始查詢資料***************************************/
		MemberService memberSvc = new MemberService();
		MemberVO getmemberVO = memberSvc.fingByMemid(memberVO.getMem_id());
		
		/***************************3.新增完成,準備轉交(Send the Success view)***********/
		if(getmemberVO.getMem_no() == null){
			model.addAttribute("errors", "查無帳號");
			setMember(model);
			return "member/login";
		}else if(!(memberVO.getMem_psw().equals(getmemberVO.getMem_psw()))){
			model.addAttribute("errors", "密碼錯誤");
			setMember(model);
			return "member/login";
		}else{
			session.setAttribute("memberVO", getmemberVO);
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
	public String insert(ModelMap model,@RequestParam("photo") MultipartFile inputfile, 
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@Valid MemberVO memberVO, BindingResult result) throws IOException{
			MemberService memberSvc = new MemberService();
			MemberVO checkMember = new MemberVO();
			checkMember = memberSvc.fingByMemid(memberVO.getMem_id());
			if(result.hasErrors()){
				return "member/joinMember";
			}else if(checkMember.getMem_no() != null){
				model.addAttribute("errors", "此帳號已申請過");
				return "member/joinMember";
			}
			
			
	
		/***************************2.開始新增資料***************************************/
		byte[] mem_photo = inputfile.getBytes();  //取得前端傳來的圖片資料
		Timestamp today = new Timestamp(System.currentTimeMillis());

		memberVO.setMem_id(memberVO.getMem_id());
		memberVO.setMem_psw(memberVO.getMem_psw());
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
	public String getOneForUpdate(ModelMap model,@RequestParam("mem_id") String mem_id) {
		/***************************2.開始查詢資料***************************************/
		MemberService memberSvc = new MemberService();
		MemberVO memberVO = memberSvc.fingByMemid(mem_id);
		model.addAttribute("memberVO", memberVO);
		return "member/updateMember";
	}
	
	//修改會員資料
	@RequestMapping(method = RequestMethod.POST, value ="updateMember")
	public String updateMember(ModelMap model,@RequestParam("photo") MultipartFile inputfile, 
			/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
			@Valid MemberVO memberVO, BindingResult result) throws IOException{
		/***************************2.開始修改資料***************************************/
		MemberService memberSvc = new MemberService();
		MemberVO member = memberSvc.fingByMemid(memberVO.getMem_id());
		byte[] mem_photo = inputfile.getBytes();  //取得前端傳來的圖片資料
		if(inputfile.getSize() == 0){
			member.setMem_photo(member.getMem_photo());
		}else{
			member.setMem_photo(mem_photo);
		}
		member.setMem_psw(memberVO.getMem_psw());
		memberSvc.updatedMember(member);

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
	
//	@ExceptionHandler(value = { ConstraintViolationException.class })
//	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
//	public ModelAndView handleError(HttpServletRequest req,ConstraintViolationException e) {
//	    Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
//	    StringBuilder strBuilder = new StringBuilder();
//	    for (ConstraintViolation<?> violation : violations ) {
//	          strBuilder.append(violation.getMessage() + "<br>");
//	    }
//	    String message = strBuilder.toString();
//	    return new ModelAndView("/member/login", "message", "請修正以下錯誤:<br>"+message);
//	}

}
