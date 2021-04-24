package hu.pte.ttk.vaadin.vaadindemo.book.service.impl;

import hu.pte.ttk.vaadin.vaadindemo.book.entity.BookEntity;
import hu.pte.ttk.vaadin.vaadindemo.book.service.BookService;
import hu.pte.ttk.vaadin.vaadindemo.core.service.impl.CoreCRUDServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends CoreCRUDServiceImpl<BookEntity> implements BookService {

    @Override
    protected void updateCore(BookEntity persistedEntity, BookEntity entity) {
        persistedEntity.setAuthor(entity.getAuthor());
        persistedEntity.setIsbn(entity.getIsbn());
        persistedEntity.setName(entity.getName());
    }

    @Override
    protected Class<BookEntity> getManagedClass() {
        return BookEntity.class;
    }
}
