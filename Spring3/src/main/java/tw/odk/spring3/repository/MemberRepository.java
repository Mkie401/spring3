package tw.odk.spring3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tw.odk.spring3.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
	boolean existsByAccount(String account);
	
	 Optional<Member> findByAccount(String account);
}
