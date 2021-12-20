package com.github.dmitriydb.etda.webapp.service;

import com.github.dmitriydb.etda.model.EtdaModel;
import com.github.dmitriydb.etda.model.simplemodel.dao.SimpleDaoFactory;
import com.github.dmitriydb.etda.security.SecurityRole;
import com.github.dmitriydb.etda.security.User;
import com.github.dmitriydb.etda.webapp.security.CustomPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.github.dmitriydb.etda.model.SimpleModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private SimpleDaoFactory daoFactory = new SimpleDaoFactory();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!daoFactory.getUserDao().isUserExists(username)){
            throw new UsernameNotFoundException("User " + username + " not exists!");
        }
        User u = daoFactory.getUserDao().getUserByName(username);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        String roleName = SecurityRole.EXISTING_ROLES_IDS.values()[u.getSecurityRole().getId().intValue()].toString();
        logger.info("Loaded role name {}", roleName);
        grantedAuthorities.add(new SimpleGrantedAuthority(roleName));
        UserDetails result = new org.springframework.security.core.userdetails.User( u.getName(), u.getPassword(), true, true, true, true, grantedAuthorities);
        return result;
    }
}
