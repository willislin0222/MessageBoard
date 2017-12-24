package com.member.controller;

import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.member.model.MemberService;
import com.member.model.MemberVO;

@Controller
@MultipartConfig
@RequestMapping("/member")
public class MemberController {
	
	//檢查是否有登入會員
	@RequestMapping(method = RequestMethod.GET, value = "checklogin")
	public String checklogin(ModelMap model,HttpSession session) {
		MemberVO loginmember = (MemberVO) session.getAttribute("memberVO");
		if(loginmember == null){
			return "member/login";
		}else{
			return "message/addMessage";
		}
	}
	
	//前往會員註冊頁面
	@RequestMapping(method = RequestMethod.GET, value = "joinMember")
	public String checklogin(ModelMap model) {
		MemberVO memberVO = new MemberVO();
		model.addAttribute("memberVO", memberVO);
		return "member/joinMember";
	}
	
	//新增會員
	@RequestMapping(method = RequestMethod.POST, value ="insert")
	public String insert(@Valid MemberVO memberVO,HttpServletResponse response,
            HttpServletRequest request,@RequestPart("mem_photo") MultipartFile file, BindingResult result, ModelMap model) {

		/***************************1.接收請求參數 - 輸入格式的錯誤處理******************/
		if (result.hasErrors()) {
			return "member/addMember";
		}
		/***************************2.開始新增資料***************************************/
		MemberService memberSvc = new MemberService();
		memberSvc.addMember(memberVO);

		/***************************3.新增完成,準備轉交(Send the Success view)***********/
		model.addAttribute("success", "- (新增成功)");
		return "member/login"; 
	}
	
	//圖片上傳測試
	@RequestMapping(method = RequestMethod.POST, value = "demo")
    public String demo(@RequestParam("upload") MultipartFile file,
                            HttpServletResponse response,
                            HttpServletRequest request,
//                            ==================取得資料============
                    		@RequestParam("mem_id") String mem_id,
                    		@RequestParam("mem_psw") String mem_psw) throws IOException {
//		

		return null;
    }

}
