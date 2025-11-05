package tw.odk.spring3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
	
	
	public boolean login(String account, String passwd) {
										// 有就是有 沒有就是 null
		Member member = repository.findByAccount(account).orElse(null);
		if (member != null && BCrypt.checkpw(passwd, member.getPasswd())) {
			return true;
		}
		return false;
	}
	
	public boolean loginV2(String account, String passwd) {
		Member member = new Member();
		member.setAccount(account);
		
		Example<Member> ex = Example.of(member);
		if (repository.exists(ex)) {
			List<Member> members = repository.findAll(ex);
			if (BCrypt.checkpw(passwd, members.get(0).getPasswd())) {
				return true;
			}
		}
		return false;
	}
	
	
	
}
