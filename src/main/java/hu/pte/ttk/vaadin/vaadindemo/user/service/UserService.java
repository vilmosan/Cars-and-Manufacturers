package hu.pte.ttk.vaadin.vaadindemo.user.service;

import hu.pte.ttk.vaadin.vaadindemo.core.service.CoreCRUDService;
import hu.pte.ttk.vaadin.vaadindemo.user.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends CoreCRUDService<UserEntity>, UserDetailsService {

}