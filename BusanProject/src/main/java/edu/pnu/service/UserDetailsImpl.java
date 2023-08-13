package edu.pnu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.pnu.entity.Busanuser;
import edu.pnu.repository.BusanUserRepository;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private BusanUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Busanuser> option = userRepository.findById(username);
        if (!option.isPresent()) {
        	return null;
        }
        Busanuser user = option.get();
        return new User(
        		user.getUsername(),
        		user.getPassword(),
        		user.getAuthorities()
        );
    }
}

