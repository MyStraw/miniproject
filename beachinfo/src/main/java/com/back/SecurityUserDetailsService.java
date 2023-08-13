package com.back;
//package edu.pnu.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import edu.pnu.domain.Member;
//import edu.pnu.persistence.MemberRepository;
//
//// DB에 있는 사용자 정보 로드
//@Service
//public class SecurityUserDetailsService implements UserDetailsService {
//	@Autowired
//	private MemberRepository memRepo;
//	
//	
//	// username을 기준으로 사용자 정보 조회 
//	// 조회된 정보로 UserDetails 객체 반환 
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	Member member = memRepo.findById(username).orElseThrow(()->
//		new UsernameNotFoundException("Not Found!")); // 사용자를 찾지 못한 경우
//	return new User(member.getUsername(), member.getPassword(), member.getAuthorities());
//	}
//
//}
