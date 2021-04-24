package hu.pte.ttk.vaadin.vaadindemo.book.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import hu.pte.ttk.vaadin.vaadindemo.brand.entity.AuthorEntity;
import hu.pte.ttk.vaadin.vaadindemo.brand.service.AuthorService;
import hu.pte.ttk.vaadin.vaadindemo.book.entity.BookEntity;
import hu.pte.ttk.vaadin.vaadindemo.book.service.BookService;
import hu.pte.ttk.vaadin.vaadindemo.menu.MenuComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route
public class BookView extends VerticalLayout {

    private VerticalLayout form;
    private BookEntity selectedBook;
    private Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());
    private Binder<BookEntity> binder = new Binder<>();
    private TextField name;
    private ComboBox<AuthorEntity> author;
    private TextField isbn;

    @Autowired
    public BookService bookService;

    @Autowired
    public AuthorService authorService;

    @PostConstruct
    public void init() {
        add(new MenuComponent());
        add(new Text("Ez a könyvek oldala"));

        Grid<BookEntity> grid = new Grid<>();
        grid.addColumn(BookEntity::getId).setHeader("Id");
        grid.addColumn(BookEntity::getName).setHeader("Name");
        grid.addColumn(bookEntity -> bookEntity.getAuthor().getFirstName() + " " + bookEntity.getAuthor().getLastName()).setHeader("Author");
        grid.addColumn(BookEntity::getIsbn).setHeader("ISBN");
        grid.setItems(bookService.getAll());
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedBook = event.getValue();
            binder.setBean(selectedBook);
            form.setVisible(selectedBook != null);
            deleteButton.setEnabled(selectedBook != null);
        });

        addButtonBar(grid);

        add(grid);

        addForm(grid);
    }

    private void addForm(Grid<BookEntity> grid) {
        form = new VerticalLayout();
        binder = new Binder<>(BookEntity.class);
        HorizontalLayout nameField = new HorizontalLayout();
        name = new TextField();
        nameField.add(new Text("Name:"), name);
        nameField.setPadding(true);

        HorizontalLayout authorField = new HorizontalLayout();
        author = new ComboBox<>();
        author.setItems(authorService.getAll());
        author.setItemLabelGenerator(authorEntity -> authorEntity.getFirstName() + " " + authorEntity.getLastName());
        authorField.add(new Text("Author:"), author);
        authorField.setPadding(true);

        HorizontalLayout isbnField = new HorizontalLayout();
        isbn = new TextField();
        isbnField.add(new Text("ISBN:"), isbn);
        isbnField.setPadding(true);
        form.add(nameField, authorField, isbnField, addSaveButton(grid));
        add(form);
        form.setVisible(false);

        binder.bindInstanceFields(this);
    }

    private void addButtonBar(Grid<BookEntity> grid) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        //deleteButton.setDisableOnClick(true);
        deleteButton.addClickListener(buttonClickEvent -> {
            bookService.remove(selectedBook);
            Notification.show("Sikeres törlés");
            selectedBook = null;
            grid.setItems(bookService.getAll());
            form.setVisible(false);
        });
        deleteButton.setEnabled(false);

        Button addButton = new Button("Add", VaadinIcon.PLUS.create());
        addButton.addClickListener(buttonClickEvent -> {
            selectedBook = new BookEntity();
            binder.setBean(selectedBook);
            form.setVisible(true);
        });

        horizontalLayout.add(addButton);
        horizontalLayout.add(deleteButton);
        add(horizontalLayout);
    }


    private Button addSaveButton(Grid<BookEntity> grid){
        Button saveButton = new Button("Save", VaadinIcon.SAFE.create());
        saveButton.addClickListener(buttonClickEvent -> {
            if(selectedBook.getId() == null){
                BookEntity bookEntity = new BookEntity();
                bookEntity.setName(selectedBook.getName());
                bookEntity.setAuthor(selectedBook.getAuthor());
                bookEntity.setIsbn(selectedBook.getIsbn());

                bookService.add(bookEntity);
                grid.setItems(bookService.getAll());
                selectedBook = null;
                Notification.show("Sikeres mentés.");
            }
            else{
                bookService.update(selectedBook);
                grid.setItems(bookService.getAll());
                Notification.show("Sikeres módosítás.");
            }
            form.setVisible(false);
        });
        return saveButton;
    }

}
