package hu.pte.ttk.vaadin.vaadindemo.user.entity;

import hu.pte.ttk.vaadin.vaadindemo.core.entity.CoreEntity;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.List;

@NamedQuery(name = UserEntity.FIND_USER_BY_USERNAME, query = "SELECT u FROM UserEntity u where u.username=:username")
@Table(name = "app_user")
@Data
@Entity
public class UserEntity extends CoreEntity implements UserDetails {
	public static final String FIND_USER_BY_USERNAME = "UserEntity.findUserByUsername";

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "app_user_roles_defined",
	joinColumns = {@JoinColumn(name = "app_user.id", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name = "app_role.id", referencedColumnName = "id")}
	)
	private List<RoleEntity> authorities;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}