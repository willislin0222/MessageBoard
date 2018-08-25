package com.member.controller;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.member.model.MemberService;
import com.member.model.MemberVO;

public class MemberPhoto extends HttpServlet {

	Connection con;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("image/gif");
		ServletOutputStream out = res.getOutputStream();

				String mem_id=req.getParameter("mem_id");
				MemberService memberSvc = new MemberService();
				MemberVO memberVO = memberSvc.fingByMemid(new String(mem_id));
				byte[] buf = memberVO.getMem_photo();
				if(!(buf.length == 0)){
					out.write(buf);
				}else{
					InputStream in = getServletContext().getResourceAsStream("/images/nopic.jpg");
					buf = new byte[in.available()];
					in.read(buf);
					out.write(buf);
					in.close();
				}
	}
				
}
