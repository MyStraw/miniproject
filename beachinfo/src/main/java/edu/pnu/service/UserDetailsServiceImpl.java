package edu.pnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private MemberRepository repository;
	@Autowired
	private PasswordEncoder passwordEncoder;
//
//	@Autowired
//	public UserDetailsServiceImpl(MemberRepository repository, PasswordEncoder passwordEncoder) {
//		this.repository = repository;
//		this.passwordEncoder = passwordEncoder;
//	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Member> opt = repository.findByUsername(username);
		if (!opt.isPresent()) {
			throw new UsernameNotFoundException("User not found.");
		}
		Member m = opt.get();
		return new User(m.getUsername(), m.getPassword(), m.getAuthorities());
	}

	public void saveMember(Member member) {
		String encodedPassword = passwordEncoder.encode(member.getPassword());
		member.setPassword(encodedPassword);
		repository.save(member);
	}
}