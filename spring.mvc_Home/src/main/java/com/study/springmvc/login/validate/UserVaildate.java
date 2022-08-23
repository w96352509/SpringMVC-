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
		User user = (User)target;
		if(user.getName() == null || user.getPassword() == null) {
		       errors.rejectValue("name", "name.Empty", "帳號密碼不可空白");
		}else {
			Optional<User> loginuser = userDao.findByName(user.getName());
			if(!loginuser.isPresent()){
				 errors.rejectValue("name", "name.Error", "無使用者");
			}else {
				if(!(loginuser.get().getPassword().equals(user.getPassword()))) {
					 errors.rejectValue("password", "password.Error", "密碼錯誤");	
				}
			}
		}
	}
}
