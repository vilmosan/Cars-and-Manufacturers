package hu.pte.ttk.vaadin.vaadindemo.book.entity;

import hu.pte.ttk.vaadin.vaadindemo.author.entity.AuthorEntity;
import hu.pte.ttk.vaadin.vaadindemo.core.entity.CoreEntity;

import javax.persistence.*;

@Table(name = "book")
@Entity
public class BookEntity extends CoreEntity {

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

    @Column(name = "isbn")
    private String isbn;

    public BookEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


}
