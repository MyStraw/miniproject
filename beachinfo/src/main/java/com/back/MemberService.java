package com.back;
//package edu.pnu.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.User.UserBuilder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import edu.pnu.domain.Member;
//import edu.pnu.persistence.MemberRepository;
//
//@Service
//public class MemberService {
//	
//	@Autowired
//	private MemberRepository memberRepo;
//	
//	
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<Member> user = memberRepo.findByUsername(username); 
//
//		UserBuilder builder = null;
//		if (user.isPresent()) {
//			Member currentMember = user.get();
//			builder = org.springframework.security.core.userdetails.User.withUsername(username);
//			builder.password(currentMember.getPassword());
//			builder.roles(currentMember.getRole());
//		} else {
//			throw new UsernameNotFoundException("User not found.");
//		}
//
//		return builder.build();	    
//	}
//}
