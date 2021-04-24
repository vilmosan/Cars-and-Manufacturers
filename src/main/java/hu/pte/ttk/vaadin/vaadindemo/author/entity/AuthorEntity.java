package hu.pte.ttk.vaadin.vaadindemo.author.entity;

import hu.pte.ttk.vaadin.vaadindemo.core.entity.CoreEntity;

import javax.persistence.*;

@Table(name = "author")
@Entity
public class AuthorEntity extends CoreEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    public AuthorEntity() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString(){
        return firstName + " " + lastName;
    }
}
