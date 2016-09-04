package org.baeldung.security;

import java.util.ArrayList;
import java.util.Collection;

import org.baeldung.persistence.model.MyUser;
import org.baeldung.persistence.multiple.dao.user.MyUserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    MyUserDAO myUserDAO;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final MyUser user = myUserDAO.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
        else {
            final Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, authorities);
        }
    }

}
