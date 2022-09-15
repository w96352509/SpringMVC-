package com.study.springmvc.login.validate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.study.springmvc.login.entity.User;
import com.study.springmvc.login.repository.UserDao;

@Component
public class UserVaildate implements Validator {

	@Autowired
	private UserDao userDao;
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target; // 輸入的使用者資訊
		if(user.getName() == null || user.getPassword() == null) {
		       errors.rejectValue("name", "name.Empty", "帳號密碼不可空白");
		}else {
			// 從資料庫找使用者
			Optional<User> Daologinuser = userDao.findByName(user.getName());
			// 如果無值
			if(!Daologinuser.isPresent()){
				 errors.rejectValue("name", "name.Error", "無使用者");
			}else {
				// 有使用者則密碼比對
				if(!(Daologinuser.get().getPassword().equals(user.getPassword()))) {
					 errors.rejectValue("password", "password.Error", "密碼錯誤");	
				}
			}
		}
	}
}
