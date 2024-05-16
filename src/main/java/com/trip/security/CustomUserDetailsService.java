package com.trip.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trip.member.model.dto.MemberDto;
import com.trip.member.model.mapper.MemberMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDto member = memberMapper.findById(username);
        if (member == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(member.getId(), member.getPassword(), new ArrayList<>());
    }
}
