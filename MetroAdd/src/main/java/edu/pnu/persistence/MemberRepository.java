package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.Member;


// JPA 인터페이스 이용시 레파지토리는 자동으로 빈에 등록되어있다. 
public interface MemberRepository extends JpaRepository<Member, String> {

	Optional<Member> findByUsername(String username);
	
}
