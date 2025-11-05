package tw.odk.spring3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tw.odk.spring3.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository repository; 
	
	public boolean checkAccount(String account) {
		return repository.existsByAccount(account);
	}
}
