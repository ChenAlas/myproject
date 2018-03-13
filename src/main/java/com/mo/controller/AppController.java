package com.mo.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mo.model.ResObject;
import com.mo.model.User;
import com.mo.util.Constant;

@RestController
public class AppController {
	
	/**
	 * GET请求
	 * @param user
	 * @return
	 */
	@GetMapping("/app/login")
	public ResObject<Object> login(User user){
		System.out.println("Username"+user.getUserName());
		System.out.println("Password"+user.getPassword());
		ResObject<Object> object = new ResObject<Object>(Constant.Code01,Constant.Msg01,user,null);
		return object;
	}
	
	/**
	 * Post请求
	 * @param user
	 * @return
	 */
	@PostMapping("/app/register")
	public ResObject<Object> register(User user){
		System.out.println("UserName :" + user.getUserName());
		System.out.println("Password :" + user.getPassword());
		ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, user, null);
		return object;
	}
	
	/**
	 * 单文件上传
	 * @param user
	 * @param imageFile
	 * @param httpSession
	 * @return
	 */
	@PostMapping("/app/upload")
	public ResObject<Object> upload(User user,@RequestParam MultipartFile imageFile,HttpSession httpSession){
		System.out.println("UserName :" + user.getUserName());
		System.out.println("Password :" + user.getPassword());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String strDate = sdf.format(date);
		String fileName = strDate + imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().indexOf("."),imageFile.getOriginalFilename().length());
		String realPath = httpSession.getServletContext().getRealPath("/userfiles");
		System.out.println("realPath : "+realPath);
		try {
			FileUtils.copyInputStreamToFile(imageFile.getInputStream(),new File(realPath, fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, user, null);
		return object;
	}
	
	/**
	 * 多文件上传
	 * @param user
	 * @param imageFile
	 * @param httpSession
	 * @return
	 */
	@PostMapping("/app/uploads")
	public ResObject<Object> uploads(User user,@RequestParam MultipartFile[] imageFile,HttpSession httpSession){
		System.out.println("UserName :" +user.getUserName());
		System.out.println("Password :" +user.getPassword());
		for (MultipartFile file : imageFile) {
			if (file.isEmpty()) {
				System.out.println("文件未上传");
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				Random random = new Random();
				Date date = new Date();
				String strDate = sdf.format(date);
				String fileName = strDate  + random.nextInt(1000) + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."),file.getOriginalFilename().length());
				String realPath = httpSession.getServletContext().getRealPath("/userfiles");
				System.out.println("realPath : "+realPath);
				System.out.println("fileName : "+fileName);
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(),new File(realPath, fileName));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, user, null);
		return object;
	}
}