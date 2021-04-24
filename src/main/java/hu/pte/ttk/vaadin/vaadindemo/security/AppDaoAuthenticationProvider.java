package hu.pte.ttk.vaadin.vaadindemo.security;

import hu.pte.ttk.vaadin.vaadindemo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class AppDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Autowired
    private UserService userService;

    public AppDaoAuthenticationProvider(){
        setPasswordEncoder(new BCryptPasswordEncoder());
    }

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService){
        super.setUserDetailsService(userService);
    }
}
