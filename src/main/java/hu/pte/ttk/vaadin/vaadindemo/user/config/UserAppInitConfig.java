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
		RoleEntity adminRole = new RoleEntity();
		RoleEntity userRole = new RoleEntity();

		if (roleEntities.isEmpty()) {
			adminRole.setAuthority("ROLE_ADMIN");
			roleService.add(adminRole);
			userRole.setAuthority("ROLE_USER");
			roleService.add(userRole);
		}

		List<UserEntity> userEntities = userService.getAll();
		if (userEntities.isEmpty()) {
			UserEntity adminEntity = new UserEntity();
			adminEntity.setPassword(new BCryptPasswordEncoder().encode("PTEadmin1367"));
			adminEntity.setFirstName("Vaadin");
			adminEntity.setLastName("Admin");
			adminEntity.setUsername("vaadmin");
			adminEntity.setAuthorities(new ArrayList<>());
			adminEntity.getAuthorities().add(adminRole);
			userService.add(adminEntity);

			UserEntity userEntity = new UserEntity();
			userEntity.setPassword(new BCryptPasswordEncoder().encode("PTEuser1367"));
			userEntity.setFirstName("Vaadin");
			userEntity.setLastName("User");
			userEntity.setUsername("vaaduser");
			userEntity.setAuthorities(new ArrayList<>());
			userEntity.getAuthorities().add(userRole);
			userService.add(userEntity);

		}
	}
}
