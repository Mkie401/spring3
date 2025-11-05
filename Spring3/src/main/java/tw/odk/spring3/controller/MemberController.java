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
	
	
}
