package hu.pte.ttk.vaadin.vaadindemo.car.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
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
	private ListBox<Integer> carDoors;
	private IntegerField carManufactured;

	private TextField filterNameField = new TextField();

	@Autowired
	public CarService carService;

	@Autowired
	public BrandService brandService;

	@PostConstruct
	public void init() {
		add(new MenuComponent());
		add(new Text("This is the page for CARS!"));

		Grid<CarEntity> grid = new Grid<>();
		grid.addColumn(CarEntity::getId).setHeader("Id").setSortable(true);
		grid.addColumn(carEntity -> carEntity.getBrand().getBrandName()).setHeader("Brand").setSortable(true);
		grid.addColumn(CarEntity::getCarName).setHeader("Name").setSortable(true);
		grid.addColumn(CarEntity::getCarType).setHeader("Type").setSortable(true);
		grid.addColumn(CarEntity::getCarDoors).setHeader("Doors").setSortable(true);
		grid.addColumn(CarEntity::getCarManufactured).setHeader("Manufactured").setSortable(true);
		grid.setItems(carService.getAll());

		configureFilter(grid);

		grid.asSingleSelect().addValueChangeListener(event -> {
			selectedCar = event.getValue();
			binder.setBean(selectedCar);
			form.setVisible(selectedCar != null);
			deleteButton.setEnabled(selectedCar != null);
		});
		addButtonBar(grid);
		add(filterNameField, grid);
		addForm(grid);
	}

	private void configureFilter(Grid<CarEntity> grid) {
		filterNameField.setPlaceholder("Filter by name...");
		filterNameField.setClearButtonVisible(true);
		filterNameField.setValueChangeMode(ValueChangeMode.ON_CHANGE);
		filterNameField.addValueChangeListener(e -> {
					if (filterNameField.getValue().isEmpty()) {
						grid.setItems(carService.getAll());
					} else {
						grid.setItems(carService.findAllByName(filterNameField.getValue()));
					}
				}
		);
	}

	private void addForm(Grid<CarEntity> grid) {
		form = new VerticalLayout();
		binder = new Binder<>(CarEntity.class);

		HorizontalLayout nameField = new HorizontalLayout();
		carName = new TextField();
		nameField.add(new Text("Name:"), carName);

		HorizontalLayout brandField = new HorizontalLayout();
		brand = new ComboBox<>();
		brand.setItems(brandService.getAll());
		brand.setItemLabelGenerator(BrandEntity::getBrandName);
		brandField.add(new Text("Brand:"), brand);

		HorizontalLayout typeField = new HorizontalLayout();
		carType = new TextField();
		typeField.add(new Text("Type:"), carType);

		HorizontalLayout doorsField = new HorizontalLayout();
		carDoors = new ListBox<>();
		carDoors.setItems(2, 3, 4, 5);
		doorsField.add(new Text("Doors:"), carDoors);

		HorizontalLayout manufacturedField = new HorizontalLayout();
		carManufactured = new IntegerField();
		manufacturedField.add(new Text("Manufactured:"), carManufactured);

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
			boolean paramsNotNull = isParamsNotNull(selectedCar);

			if (paramsNotNull) {
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
			} else {
				Notification.show("Some input parameters are empty. Please fill all the fields!");
			}
		});
		return saveButton;
	}

	private boolean isParamsNotNull(CarEntity selectedCar) {
		if (selectedCar.getCarName().equals("") || selectedCar.getBrand().equals("") || selectedCar.getCarType().equals("") || selectedCar.getCarDoors().equals("") || selectedCar.getCarManufactured().equals("")) {
			return false;
		}

		return true;
	}

}
