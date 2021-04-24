package hu.pte.ttk.vaadin.vaadindemo.brand.view;

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
import hu.pte.ttk.vaadin.vaadindemo.brand.entity.BrandEntity;
import hu.pte.ttk.vaadin.vaadindemo.brand.service.BrandService;
import hu.pte.ttk.vaadin.vaadindemo.menu.MenuComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route
public class BrandView extends VerticalLayout {

	private VerticalLayout form;
	private BrandEntity selectedBrand;
	private final Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());
	private Binder<BrandEntity> binder = new Binder<>();
	private TextField brandName;

	@Autowired
	public BrandService brandService;

	@PostConstruct
	public void init() {
		add(new MenuComponent());
		add(new Text("This is the page for BRANDS!"));

		Grid<BrandEntity> grid = new Grid<>();
		grid.addColumn(BrandEntity::getId).setHeader("Id");
		grid.addColumn(BrandEntity::getBrandName).setHeader("Brand name");
		grid.setItems(brandService.getAll());
		grid.asSingleSelect().addValueChangeListener(event -> {
			selectedBrand = event.getValue();
			binder.setBean(selectedBrand);
			form.setVisible(selectedBrand != null);
			deleteButton.setEnabled(selectedBrand != null);
		});

		addButtonBar(grid);
		add(grid);
		addForm(grid);
	}

	private void addForm(Grid<BrandEntity> grid) {
		form = new VerticalLayout();
		binder = new Binder<>(BrandEntity.class);
		HorizontalLayout nameField = new HorizontalLayout();
		brandName = new TextField();
		nameField.add(new Text("Brand name:"), brandName);
		nameField.setPadding(true);

		form.add(nameField, addSaveButton(grid));
		add(form);
		form.setVisible(false);

		binder.bindInstanceFields(this);
	}

	private void addButtonBar(Grid<BrandEntity> grid) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		//deleteButton.setDisableOnClick(true);
		deleteButton.addClickListener(buttonClickEvent -> {
			brandService.remove(selectedBrand);
			Notification.show("Deleted.");
			selectedBrand = null;
			grid.setItems(brandService.getAll());
			form.setVisible(false);
		});
		deleteButton.setEnabled(false);

		Button addButton = new Button("Add", VaadinIcon.PLUS.create());
		addButton.addClickListener(buttonClickEvent -> {
			selectedBrand = new BrandEntity();
			binder.setBean(selectedBrand);
			form.setVisible(true);
		});

		horizontalLayout.add(addButton);
		horizontalLayout.add(deleteButton);
		add(horizontalLayout);
	}

	private Button addSaveButton(Grid<BrandEntity> grid) {
		Button saveButton = new Button("Save", VaadinIcon.SAFE.create());
		saveButton.addClickListener(buttonClickEvent -> {
			if (selectedBrand.getId() == null) {
				BrandEntity brandEntity = new BrandEntity();
				brandEntity.setBrandName(selectedBrand.getBrandName());

				brandService.add(brandEntity);
				grid.setItems(brandService.getAll());
				selectedBrand = null;
				Notification.show("Saved.");
			} else {
				brandService.update(selectedBrand);
				grid.setItems(brandService.getAll());
				Notification.show("Modified.");
			}
			form.setVisible(false);
		});
		return saveButton;
	}

}
