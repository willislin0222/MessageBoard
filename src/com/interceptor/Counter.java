package com.interceptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class Counter implements HandlerInterceptor{

	private int count;
	private String name = "/counter.txt";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		File filepath = new File(request.getServletContext().getRealPath("/counter"));
		FileReader fr = new FileReader(filepath + name);
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			this.count = Integer.valueOf(br.readLine());
		}
		fr.close();
		System.out.println("取出目前網站訪問:" + count + "人");

		// 把計數器的值增加 1
		count++;
				
		// 輸出計數器
	   System.out.println("網站訪問人數："+ count + "人");
	   session.setAttribute("count", count);
				
		return true;
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.out.println("first postHandler");
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {	
		//建立檔案
		//判斷路徑是否存在，不存在就建立路徑
        File savepath = new File(request.getServletContext().getRealPath("/counter"));
        if(!savepath.exists()){
         	savepath.mkdirs();
        }
        
        //寫入檔案的路徑與檔名
        FileWriter fw = new FileWriter(savepath + name);
        
        //寫入
        fw.write(String.valueOf(count));
        System.out.println("將目前訪問網站:" + count + "人寫入檔案" );
        fw.flush();
		fw.close();
		
		
	}

}
