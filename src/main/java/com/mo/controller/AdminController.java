package com.mo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mo.model.Admin;
import com.mo.service.AdminService;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	/**
	 * 登陆跳转
	 * @param model
	 * @return
	 */
   @GetMapping("/login")
   public String login(Model model) {
	   model.addAttribute("projectName","MO");
	   return "login";
   }
   /**
    * 登陆操作
    * @param admin
    * @param model
    * @param httpSession
    * @return
    */
    @PostMapping("/login")
    public String loginPost(Admin admin,Model model,HttpSession httpSession) {
	   Admin postAdmin = adminService.findByNameAndPassword(admin);
	   if(postAdmin != null) {
		 httpSession.setAttribute("admin", postAdmin);
		 return "redirect:dashboard";
	   } else {
		 model.addAttribute("error", "用户名或密码错误");
		 return "login";
	   }
    }
    
    /**
     * 首页
     * @param model
     * @return
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
    	return "dashboard";
    }
   
    /**
     * 注册
     * @param model
     * @return
     */
    @GetMapping("/register")
    public String register(Model model) {
	   model.addAttribute("projectName","MO");
	   return "register";
    }
}
