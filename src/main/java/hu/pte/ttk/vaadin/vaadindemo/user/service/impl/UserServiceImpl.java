package hu.pte.ttk.vaadin.vaadindemo.user.service.impl;

import hu.pte.ttk.vaadin.vaadindemo.car.entity.CarEntity;
import hu.pte.ttk.vaadin.vaadindemo.core.service.impl.CoreCRUDServiceImpl;
import hu.pte.ttk.vaadin.vaadindemo.user.entity.UserEntity;
import hu.pte.ttk.vaadin.vaadindemo.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends CoreCRUDServiceImpl<UserEntity> implements UserService {
	@Override
	protected void updateCore(UserEntity persistedEntity, UserEntity entity) {
		persistedEntity.setAuthorities(entity.getAuthorities());
		persistedEntity.setUsername(entity.getUsername());
	}

	@Override
	protected Class<UserEntity> getManagedClass() {
		return UserEntity.class;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		TypedQuery<UserEntity> query = entityManager.createNamedQuery(UserEntity.FIND_USER_BY_USERNAME, UserEntity.class);
		query.setParameter("username", username);
		return query.getSingleResult();
	}

	@Override
	public List<UserEntity> findAllByUsername(String name) {
		List<UserEntity> filteredList = new ArrayList<>();
		List<UserEntity> userEntities = getAll();

		for (UserEntity userEntity : userEntities) {
			if(userEntity.getUsername().toLowerCase().contains(name.toLowerCase())) filteredList.add(userEntity);
		}
		return filteredList;
	}
}