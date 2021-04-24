package hu.pte.ttk.vaadin.vaadindemo.car.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import hu.pte.ttk.vaadin.vaadindemo.brand.entity.BrandEntity;
import hu.pte.ttk.vaadin.vaadindemo.brand.service.BrandService;
import hu.pte.ttk.vaadin.vaadindemo.car.entity.CarEntity;
import hu.pte.ttk.vaadin.vaadindemo.car.service.CarService;
import hu.pte.ttk.vaadin.vaadindemo.menu.MenuComponent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route
public class CarView extends VerticalLayout {

	private VerticalLayout form;
	private CarEntity selectedCar;
	private final Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());
	private Binder<CarEntity> binder = new Binder<>();
	private TextField carName;
	private ComboBox<BrandEntity> brand;
	private TextField carType;
	private NumberField carDoors;
	private NumberField carManufactured;

	@Autowired
	public CarService carService;

	@Autowired
	public BrandService brandService;

	@PostConstruct
	public void init() {
		add(new MenuComponent());
		add(new Text("This is the page for CARS!"));

		Grid<CarEntity> grid = new Grid<>();
		grid.addColumn(CarEntity::getId).setHeader("Id");
		grid.addColumn(CarEntity::getCarName).setHeader("Name");
		grid.addColumn(carEntity -> carEntity.getBrand().getBrandName()).setHeader("Brand");
		grid.addColumn(CarEntity::getCarType).setHeader("Type");
		grid.addColumn(CarEntity::getCarDoors).setHeader("Doors");
		grid.addColumn(CarEntity::getCarManufactured).setHeader("Manufactured");
		grid.setItems(carService.getAll());
		grid.asSingleSelect().addValueChangeListener(event -> {
			selectedCar = event.getValue();
			binder.setBean(selectedCar);
			form.setVisible(selectedCar != null);
			deleteButton.setEnabled(selectedCar != null);
		});

		addButtonBar(grid);
		add(grid);
		addForm(grid);
	}

	private void addForm(Grid<CarEntity> grid) {
		form = new VerticalLayout();
		binder = new Binder<>(CarEntity.class);
		HorizontalLayout nameField = new HorizontalLayout();
		carName = new TextField();
		nameField.add(new Text("Name:"), carName);
		nameField.setPadding(true);

		HorizontalLayout brandField = new HorizontalLayout();
		brand = new ComboBox<>();
		brand.setItems(brandService.getAll());
		brand.setItemLabelGenerator(BrandEntity::getBrandName);
		brandField.add(new Text("Brand:"), brand);
		brandField.setPadding(true);

		HorizontalLayout typeField = new HorizontalLayout();
		carType = new TextField();
		typeField.add(new Text("Type:"), carType);
		typeField.setPadding(true);

		HorizontalLayout doorsField = new HorizontalLayout();
		carDoors = new NumberField();
		doorsField.add(new Text("Doors:"), carDoors);
		doorsField.setPadding(true);

		HorizontalLayout manufacturedField = new HorizontalLayout();
		carManufactured = new NumberField();
		manufacturedField.add(new Text("Manufactured:"), carManufactured);
		manufacturedField.setPadding(true);

		form.add(nameField, brandField, typeField, doorsField, manufacturedField, addSaveButton(grid));
		add(form);
		form.setVisible(false);

		binder.bindInstanceFields(this);
	}

	private void addButtonBar(Grid<CarEntity> grid) {
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		//deleteButton.setDisableOnClick(true);
		deleteButton.addClickListener(buttonClickEvent -> {
			carService.remove(selectedCar);
			Notification.show("Deleted.");
			selectedCar = null;
			grid.setItems(carService.getAll());
			form.setVisible(false);
		});
		deleteButton.setEnabled(false);

		Button addButton = new Button("Add", VaadinIcon.PLUS.create());
		addButton.addClickListener(buttonClickEvent -> {
			selectedCar = new CarEntity();
			binder.setBean(selectedCar);
			form.setVisible(true);
		});

		horizontalLayout.add(addButton);
		horizontalLayout.add(deleteButton);
		add(horizontalLayout);
	}

	private Button addSaveButton(Grid<CarEntity> grid) {
		Button saveButton = new Button("Save", VaadinIcon.SAFE.create());
		saveButton.addClickListener(buttonClickEvent -> {
			if (selectedCar.getId() == null) {
				CarEntity carEntity = new CarEntity();
				carEntity.setCarName(selectedCar.getCarName());
				carEntity.setBrand(selectedCar.getBrand());
				carEntity.setCarType(selectedCar.getCarType());
				carEntity.setCarDoors(selectedCar.getCarDoors());
				carEntity.setCarManufactured(selectedCar.getCarManufactured());

				carService.add(carEntity);
				grid.setItems(carService.getAll());
				selectedCar = null;
				Notification.show("Saved.");
			} else {
				carService.update(selectedCar);
				grid.setItems(carService.getAll());
				Notification.show("Modified.");
			}
			form.setVisible(false);
		});
		return saveButton;
	}

}
