package hu.pte.ttk.vaadin.vaadindemo.user.service;

import hu.pte.ttk.vaadin.vaadindemo.core.service.CoreCRUDService;
import hu.pte.ttk.vaadin.vaadindemo.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends CoreCRUDService<UserEntity>, UserDetailsService {

	List<UserEntity> findAllByUsername(String name);
}