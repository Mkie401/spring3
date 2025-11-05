package tw.odk.spring3.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import tw.odk.spring3.entity.Member;
import tw.odk.spring3.repository.MemberRepository;
import tw.odk.spring3.utils.BCrypt;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository repository; 
	
	public boolean checkAccount(String account) {
		return repository.existsByAccount(account);
	}
	
	public boolean register(Member member) {
		if (repository.existsByAccount(member.getAccount())) {	
			return false;
		}
		member.setPasswd(BCrypt.hashpw(member.getPasswd(), BCrypt.gensalt()));
		Member saveMember = repository.save(member);
		return saveMember != null;
	}
	
	
	
}
