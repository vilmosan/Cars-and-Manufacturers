package hu.pte.ttk.vaadin.vaadindemo.user.service.impl;

import hu.pte.ttk.vaadin.vaadindemo.core.service.impl.CoreCRUDServiceImpl;
import hu.pte.ttk.vaadin.vaadindemo.user.entity.RoleEntity;
import hu.pte.ttk.vaadin.vaadindemo.user.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends CoreCRUDServiceImpl<RoleEntity> implements RoleService {
    @Override
    protected void updateCore(RoleEntity persistedEntity, RoleEntity entity) {
        persistedEntity.setAuthority(entity.getAuthority());
    }

    @Override
    protected Class<RoleEntity> getManagedClass() {
        return RoleEntity.class;
    }
}
