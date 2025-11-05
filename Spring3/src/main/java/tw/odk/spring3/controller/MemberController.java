package tw.odk.spring3.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import tw.odk.spring3.entity.Member;
import tw.odk.spring3.service.MemberService;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	
	/*
	 * request:/exist?account=xxx
	 * response: true/false
	 */
	
	@GetMapping("/exist")
	public ResponseEntity<Boolean> checkAccount(@RequestParam String account) {
		boolean isExist = service.checkAccount(account);
		return ResponseEntity.ok(isExist);
	}
	
	
	/* request: Member => {}
	 * response: {"success":"true/false"} 
	 */
	@PostMapping("/register")
	public ResponseEntity<Map<String, Boolean>> register(@RequestBody Member member) {
		
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		
		map.put("success", service.register(member));
		
		return ResponseEntity.ok(map);
	}
	
	/*
	 * request: {account:xx, passwd:xxx}
	 * response: {"success":true/false}
	 */
	@PostMapping("/login")
	public  ResponseEntity<Map<String, Boolean>> login(@RequestBody Map<String, String> body) {
		
		String account = body.get("account");
		String passwd = body.get("passwd");
		
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		//map.put("success", service.login(account, passwd));
		map.put("success", service.loginV2(account, passwd));
		
		return ResponseEntity.ok(map);
	}
	
	@PostMapping("/loginV3")
	public  ResponseEntity<Map<String, Boolean>> login(
			@RequestBody Map<String, String> body,
			HttpSession session) {
		String account = body.get("account");
		String passwd = body.get("passwd");
		Member member = service.loginV3(account, passwd);
		if (member != null) {
			session.setAttribute("member", member);
		}else {
			session.invalidate();
		}
		
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		map.put("success", member != null);
		return ResponseEntity.ok(map);
		
	}
	
	@RequestMapping("/logout")
	public ResponseEntity<Map<String, String>> logout(HttpSession session) {
		
		session.invalidate();
		
		Map<String, String> map = new HashMap<>();
		map.put("success", "ok");
		return ResponseEntity.ok(map);
		
	}
	
	
}
