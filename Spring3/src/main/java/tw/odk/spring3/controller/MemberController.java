package tw.odk.spring3.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import tw.odk.spring3.entity.Member;
import tw.odk.spring3.repository.HotelRepository;
import tw.odk.spring3.repository.MemberRepository;
import tw.odk.spring3.service.MemberService;
import tw.odk.spring3.utils.MemberForm;
import tw.odk.spring3.utils.ReadProperties;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	@Autowired
	private MemberRepository memberRepository;
	
	
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
	
	@Autowired
	@Qualifier("companyName")
	private String companyName; 
	@Value("${odk.company.tel}")
	private String companyTel;
	
	
	@RequestMapping("/status")
	public ResponseEntity<Map<String, Object>> status(HttpSession session) {
		Object member = session.getAttribute("member");
		Map<String, Object> response = new HashMap<>();
		response.put("success", member != null);
		response.put("member", member);
		response.put("companyName", companyName);
		response.put("companyTel", companyTel);
		
		return ResponseEntity.ok(response);
	}
	
	@Autowired
	private NamedParameterJdbcTemplate jdbc;
	
	@PostMapping("/{id}")
	public void test1(@PathVariable Integer id, 
			@RequestParam MultipartFile upload) {
		try {
			byte[] bytes = upload.getBytes();
			String sql = """
					UPDATE member SET icon = :icon WHERE id = :id
					""";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", id);
			params.put("icon", bytes);
			
			jdbc.update(sql, params);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	
	@PostMapping("/v2/{id}")
	public void test2(@PathVariable Long id, 
			@RequestParam MultipartFile upload) {
		try {
			byte[] bytes = upload.getBytes();
			Member member = memberRepository.findById(id).orElse(null);
			if (member != null) {
				member.setIcon(bytes);
				memberRepository.save(member);
			}
		
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	@Autowired
	private ReadProperties readProperties;
	
	@PostMapping("/test3")
	public void test3(@ModelAttribute MemberForm memberForm) {
		System.out.println(memberForm.getAccount());
		System.out.println(memberForm.getFiles().size());
		System.out.println(readProperties.getUploadDir());
		File here = new File(".");
		System.out.println(here.getAbsolutePath());
		String uploadDir = here.getAbsolutePath() + "/" + readProperties.getUploadDir();
		
		List<MultipartFile> files = memberForm.getFiles();
		
		for ( MultipartFile file : memberForm.getFiles()) {
			if (!file.isEmpty()) {
				String fname = uploadDir + file.getOriginalFilename();
				try {
					file.transferTo(new File(fname));
				}catch(Exception e) {
					System.out.println(e);
				}
			}
		}
		
	}
	
	
}
