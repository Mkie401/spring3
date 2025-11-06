package tw.odk.spring3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tw.odk.spring3.entity.User;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@RequestMapping("/test1")
	public void test1() {
		User user = new User();
		user.setId(100);
		user.setName("brad");
		System.out.println(user.getName());
	}
}
