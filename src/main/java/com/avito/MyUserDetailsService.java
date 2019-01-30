package com.avito;

import com.DAO.repositories.HolderRepository;
import com.DAO.services.BrandService;
import com.DAO.services.HolderService;
import com.DAO.services.YearService;
import com.cars_annot.Brand;
import com.cars_annot.Holder;
import com.cars_annot.Role;
import com.cars_annot.Year;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private HolderService holderService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Holder holder = holderService.findByLogin(s);
        List<GrantedAuthority> authorities = buildUserAuthority(holder.getRoles());
        return buildUserForAuthentication(holder, authorities);
    }

    private User buildUserForAuthentication(Holder holder,
                                            List<GrantedAuthority> authorities) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String encoded = "{noop}" + holder.getPassword();
        return new User(holder.getLogin(),
                encoded, true,
                true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<Role> roles) {

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>();
        for (Role role : roles) {
            Result.add(new SimpleGrantedAuthority(role.getRole()));
        }
        return Result;
    }
}
