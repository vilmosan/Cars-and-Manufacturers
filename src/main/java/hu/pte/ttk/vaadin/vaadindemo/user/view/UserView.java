package hu.pte.ttk.vaadin.vaadindemo.user.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import hu.pte.ttk.vaadin.vaadindemo.car.entity.CarEntity;
import hu.pte.ttk.vaadin.vaadindemo.menu.MenuComponent;
import hu.pte.ttk.vaadin.vaadindemo.user.entity.RoleEntity;
import hu.pte.ttk.vaadin.vaadindemo.user.entity.UserEntity;
import hu.pte.ttk.vaadin.vaadindemo.user.service.RoleService;
import hu.pte.ttk.vaadin.vaadindemo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;

// http://localhost:8080/user
@Route
public class UserView extends VerticalLayout {
	private VerticalLayout form;
	private UserEntity selectedUser;
	private Binder<UserEntity> binder;
	private TextField firstName;
	private TextField lastName;
	private TextField username;
	private PasswordField password;
	private ComboBox<RoleEntity> comboBox;
//	private MultiselectComboBox<RoleEntity> comboBox;

	private TextField filterNameField = new TextField();

	private final Button deleteBtn = new Button("Delete", VaadinIcon.TRASH.create());

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@PostConstruct
	public void init() {
		add(new MenuComponent());
		add(new Text("This is the page for USERS"));
		Grid<UserEntity> grid = new Grid<>();
		grid.setItems(userService.getAll());
		grid.addColumn(UserEntity::getId).setHeader("Id").setSortable(true);
		grid.addColumn(UserEntity::getFirstName).setHeader("First name").setSortable(true);
		grid.addColumn(UserEntity::getLastName).setHeader("Last name").setSortable(true);
		grid.addColumn(UserEntity::getUsername).setHeader("Username").setSortable(true);
		grid.addColumn(userEntity -> {
					if (userEntity.getAuthorities() != null) {
						StringBuilder builder = new StringBuilder();
						userEntity.getAuthorities().forEach(roleEntity -> {
							builder.append(roleEntity.getAuthority()).append(",");
						});
						return builder.toString();
					}
					return "";
				}
		).setHeader("Authority").setSortable(true);
		grid.asSingleSelect().addValueChangeListener(event -> {
			selectedUser = event.getValue();
			binder.setBean(selectedUser);
			form.setVisible(selectedUser != null);
			deleteBtn.setEnabled(selectedUser != null);

		});

		configureFilter(grid);

		grid.asSingleSelect().addValueChangeListener(event -> {
			selectedUser = event.getValue();
			binder.setBean(selectedUser);
			form.setVisible(selectedUser != null);
			deleteBtn.setEnabled(selectedUser != null);
		});
		addButtonBar(grid);
		add(filterNameField, grid);
		addForm(grid);
	}

	private void configureFilter(Grid<UserEntity> grid) {
		filterNameField.setPlaceholder("Filter by username...");
		filterNameField.setClearButtonVisible(true);
		filterNameField.setValueChangeMode(ValueChangeMode.ON_CHANGE);
		filterNameField.addValueChangeListener(e -> {
					if (filterNameField.getValue().isEmpty()) {
						grid.setItems(userService.getAll());
					} else {
						grid.setItems(userService.findAllByUsername(filterNameField.getValue()));
					}
				}
		);
	}

	private void addForm(Grid<UserEntity> grid) {
		form = new VerticalLayout();
		binder = new Binder<>(UserEntity.class);

		HorizontalLayout firstNameField = new HorizontalLayout();
		firstName = new TextField();
		firstNameField.add(new Text("First name"), firstName);

		HorizontalLayout lastNameField = new HorizontalLayout();
		lastName = new TextField();
		lastNameField.add(new Text("Last name"), lastName);

		HorizontalLayout nameField = new HorizontalLayout();
		username = new TextField();
		nameField.add(new Text("Username"), username);

		HorizontalLayout passwordField = new HorizontalLayout();
		password = new PasswordField();
		passwordField.add(new Text("Password"), password);

		HorizontalLayout authorityField = new HorizontalLayout();
		comboBox = new ComboBox<>();
		comboBox.setItems(roleService.getAll());
		comboBox.setItemLabelGenerator(RoleEntity::getAuthority);
		authorityField.add(new Text("Authorities"), comboBox);
//		List<RoleEntity> roleEntities = roleService.getAll();
//		comboBox = new MultiselectComboBox<>();
//		comboBox.setWidth("100%");
//		comboBox.setPlaceholder("Select authorities...");
//		if (!roleEntities.isEmpty()){
//			comboBox.setItems(roleEntities);
//		}
		authorityField.add(new Text("Authorities"), comboBox);

		form.add(firstNameField, lastNameField, nameField, authorityField, passwordField, addSaveBtn(grid));
		add(form);
		form.setVisible(false);
		binder.bindInstanceFields(this);
	}

	private Button addSaveBtn(Grid<UserEntity> grid) {
		Button saveBtn = new Button("Save", VaadinIcon.SAFE.create());
		saveBtn.addClickListener(buttonClickEvent -> {
			boolean selectedNameAvailable = isNameAvailable(selectedUser.getUsername());
			boolean paramsNotNull = isParamsNotNull(selectedUser);

			if (selectedNameAvailable && paramsNotNull) {
				if (selectedUser.getId() == null) {
					UserEntity userEntity = new UserEntity();
					userEntity.setFirstName(selectedUser.getFirstName());
					userEntity.setLastName(selectedUser.getLastName());
					userEntity.setUsername(selectedUser.getUsername());
					userEntity.setAuthorities(Collections.singletonList(comboBox.getValue()));
					userEntity.setPassword(new BCryptPasswordEncoder().encode(selectedUser.getPassword()));
					userService.add(userEntity);
					grid.setItems(userService.getAll());
					Notification notification = new Notification(
							"Saved. You can now login with the username of '" + selectedUser.getUsername() + "' and the password of '" + selectedUser.getPassword() + "'.", 10000, Notification.Position.BOTTOM_STRETCH);
					notification.open();
					selectedUser = null;
				} else {
					userService.update(selectedUser);
					grid.setItems(userService.getAll());
					Notification.show("Modified.");
				}
				form.setVisible(false);
			} else {
				if (!selectedNameAvailable) Notification.show("Username already taken. Choose a different name!");
				if (!paramsNotNull) Notification.show("Some input parameters are empty. Please fill the fields!!");
			}
		});
		return saveBtn;

	}

	private void addButtonBar(Grid<UserEntity> grid) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		deleteBtn.addClickListener(buttonClickEvent -> {
			userService.remove(selectedUser);
			Notification.show("Deleted.");
			selectedUser = null;
			grid.setItems(userService.getAll());
			form.setVisible(false);

		});
		deleteBtn.setEnabled(false);

		Button addBtn = new Button("Add", VaadinIcon.PLUS.create());
		addBtn.addClickListener(buttonClickEvent -> {
			selectedUser = new UserEntity();
			binder.setBean(selectedUser);
			form.setVisible(true);

		});
		horizontalLayout.add(deleteBtn);
		horizontalLayout.add(addBtn);
		add(horizontalLayout);
	}

	private boolean isNameAvailable(String chosenName) {
		List<UserEntity> userEntities = userService.getAll();

		for (UserEntity userEntity : userEntities) {
			if (userEntity.getUsername().equals(chosenName)) return false;
		}

		return true;
	}

	private boolean isParamsNotNull(UserEntity selectedUser) {
		if (selectedUser != null) {
			return !selectedUser.getFirstName().equals("") && !selectedUser.getLastName().equals("") && !selectedUser.getUsername().equals("") && !selectedUser.getPassword().equals("");
		}

		return true;
	}
}

