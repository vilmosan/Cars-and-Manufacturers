package hu.pte.ttk.vaadin.vaadindemo.author.service.impl;

import hu.pte.ttk.vaadin.vaadindemo.author.entity.AuthorEntity;
import hu.pte.ttk.vaadin.vaadindemo.author.service.AuthorService;
import hu.pte.ttk.vaadin.vaadindemo.core.service.impl.CoreCRUDServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends CoreCRUDServiceImpl<AuthorEntity> implements AuthorService {

    @Override
    protected void updateCore(AuthorEntity persistedEntity, AuthorEntity entity) {
        persistedEntity.setFirstName(entity.getFirstName());
        persistedEntity.setLastName(entity.getLastName());
    }

    @Override
    protected Class<AuthorEntity> getManagedClass() {
        return AuthorEntity.class;
    }
}
