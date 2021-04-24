package hu.pte.ttk.vaadin.vaadindemo.author.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import hu.pte.ttk.vaadin.vaadindemo.author.entity.AuthorEntity;
import hu.pte.ttk.vaadin.vaadindemo.author.service.AuthorService;
import hu.pte.ttk.vaadin.vaadindemo.menu.MenuComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route
public class AuthorView extends VerticalLayout {

    private VerticalLayout form;
    private AuthorEntity selectedAuthor;
    private Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());
    private Binder<AuthorEntity> binder = new Binder<>();
    private TextField firstName;
    private TextField lastName;

    @Autowired
    public AuthorService authorService;

    @PostConstruct
    public void init() {
        add(new MenuComponent());
        add(new Text("Ez a szerzők oldala"));

        Grid<AuthorEntity> grid = new Grid<>();
        grid.addColumn(AuthorEntity::getId).setHeader("Id");
        grid.addColumn(AuthorEntity::getFirstName).setHeader("First name");
        grid.addColumn(AuthorEntity::getLastName).setHeader("Last name");
        grid.setItems(authorService.getAll());
        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedAuthor = event.getValue();
            binder.setBean(selectedAuthor);
            form.setVisible(selectedAuthor != null);
            deleteButton.setEnabled(selectedAuthor != null);
        });

        addButtonBar(grid);

        add(grid);

        addForm(grid);
    }

    private void addForm(Grid<AuthorEntity> grid) {
        form = new VerticalLayout();
        binder = new Binder<>(AuthorEntity.class);
        HorizontalLayout nameField = new HorizontalLayout();
        firstName = new TextField();
        nameField.add(new Text("First name:"), firstName);
        nameField.setPadding(true);

        HorizontalLayout authorField = new HorizontalLayout();
        lastName = new TextField();
        authorField.add(new Text("Last name:"), lastName);
        authorField.setPadding(true);

        form.add(nameField, authorField, addSaveButton(grid));
        add(form);
        form.setVisible(false);

        binder.bindInstanceFields(this);
    }

    private void addButtonBar(Grid<AuthorEntity> grid) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        //deleteButton.setDisableOnClick(true);
        deleteButton.addClickListener(buttonClickEvent -> {
            authorService.remove(selectedAuthor);
            Notification.show("Sikeres törlés");
            selectedAuthor = null;
            grid.setItems(authorService.getAll());
            form.setVisible(false);
        });
        deleteButton.setEnabled(false);

        Button addButton = new Button("Add", VaadinIcon.PLUS.create());
        addButton.addClickListener(buttonClickEvent -> {
            selectedAuthor = new AuthorEntity();
            binder.setBean(selectedAuthor);
            form.setVisible(true);
        });

        horizontalLayout.add(addButton);
        horizontalLayout.add(deleteButton);
        add(horizontalLayout);
    }


    private Button addSaveButton(Grid<AuthorEntity> grid){
        Button saveButton = new Button("Save", VaadinIcon.SAFE.create());
        saveButton.addClickListener(buttonClickEvent -> {
            if(selectedAuthor.getId() == null){
                AuthorEntity authorEntity = new AuthorEntity();
                authorEntity.setFirstName(selectedAuthor.getFirstName());
                authorEntity.setLastName(selectedAuthor.getLastName());

                authorService.add(authorEntity);
                grid.setItems(authorService.getAll());
                selectedAuthor = null;
                Notification.show("Sikeres mentés.");
            }
            else{
                authorService.update(selectedAuthor);
                grid.setItems(authorService.getAll());
                Notification.show("Sikeres módosítás.");
            }
            form.setVisible(false);
        });
        return saveButton;
    }




}
