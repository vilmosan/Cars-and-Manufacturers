package hu.pte.ttk.vaadin.vaadindemo.user.entity;

import hu.pte.ttk.vaadin.vaadindemo.core.entity.CoreEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "app_role")
@Data
@Entity
public class RoleEntity extends CoreEntity implements GrantedAuthority {

	@Column(name = "authority")
	private String authority;

}