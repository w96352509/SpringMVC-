package com.study.springmvc.login.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.springmvc.login.entity.User;
import com.study.springmvc.login.repository.UserDao;
import com.study.springmvc.login.validate.UserVaildate;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserVaildate vaildate;
	
	@GetMapping("/")
	public String index(@ModelAttribute User user, Model model , HttpSession session) {
		// 沒有 session 紀錄的成功 1 則回首頁
		if(session.getAttribute("ok") == null) {
			model.addAttribute("_method", "POST");
			return "login";
		}
		// 取得存在 seeion 中的 user 資訊
		model.addAttribute("_method", "POST");
		Object user2 = session.getAttribute("user");
		model.addAttribute("user", userDao.findByName(user2.toString()).get());	
		return "ok";
	}

	@PostMapping("/")
	public String login(@Valid @ModelAttribute User user, BindingResult result, Model model , HttpSession session) {
        // 驗證方法帶入
		vaildate.validate(user, result);
		// 有錯回首頁
		if(result.hasErrors()) {
        	model.addAttribute("_method", "POST");
        	return "login";
        }
		// 正確則將 user 導入前端 , session 記錄成功 1 , 和 user 資料
		model.addAttribute("user", userDao.findByName(user.getName()).get());
		session.setAttribute("ok" ,"1");
		session.setAttribute("user",userDao.findByName(user.getName()).get().getName());
		return "ok";
	}

	@GetMapping("/add")
	public String add(@ModelAttribute User user, Model model) {
		model.addAttribute("_method", "POST");
		return "add";
	}

	@PostMapping("/add")
	public String addUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("_method", "POST");
			return "add";
		}
		userDao.save(user);
		return "redirect:../";
	}
	
	@GetMapping("/remove")
	public String remove(HttpSession session) {
		session.invalidate();
	    return "redirect:../";
	}

}
