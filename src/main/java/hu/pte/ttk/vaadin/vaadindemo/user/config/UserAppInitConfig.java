package hu.pte.ttk.vaadin.vaadindemo.user.config;

import hu.pte.ttk.vaadin.vaadindemo.user.entity.RoleEntity;
import hu.pte.ttk.vaadin.vaadindemo.user.entity.UserEntity;
import hu.pte.ttk.vaadin.vaadindemo.user.service.RoleService;
import hu.pte.ttk.vaadin.vaadindemo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserAppInitConfig {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;

    @PostConstruct
    private void init() {
        List<RoleEntity> roleEntities = roleService.getAll();
        RoleEntity admin = new RoleEntity();
        if (roleEntities.isEmpty()) {
            admin.setAuthority("ROLE_ADMIN");
            roleService.add(admin);

        }

        List<UserEntity> userEntities = userService.getAll();
        if (userEntities.isEmpty()) {
            UserEntity entity = new UserEntity();
            entity.setPassword(new BCryptPasswordEncoder().encode("almafa123"));
            entity.setUsername("admin");
            entity.setAuthorities(new ArrayList<>());
            entity.getAuthorities().add(admin);
            userService.add(entity);

        }
    }
}
