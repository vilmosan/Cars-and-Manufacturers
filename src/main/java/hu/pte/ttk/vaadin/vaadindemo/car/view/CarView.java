package hu.pte.ttk.vaadin.vaadindemo.car.view;

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
    private Button deleteButton = new Button("Delete", VaadinIcon.TRASH.create());
    private Binder<CarEntity> binder = new Binder<>();
    private TextField nameField;
    private ComboBox<BrandEntity> brand;
    private TextField typeField;
    private TextField doorsField;
    private TextField manufacturedField;

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
        grid.addColumn(carEntity -> carEntity.getBrandId().getBrandName()).setHeader("Brand");
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
        HorizontalLayout nameLayout = new HorizontalLayout();
        nameField = new TextField();
        nameLayout.add(new Text("Name:"), nameField);
        nameLayout.setPadding(true);

        HorizontalLayout brandLayout = new HorizontalLayout();
        brand = new ComboBox<>();
        brand.setItems(brandService.getAll());
        brand.setItemLabelGenerator(BrandEntity::getBrandName);
        brandLayout.add(new Text("Brand:"), brand);
        brandLayout.setPadding(true);

        HorizontalLayout typeLayout = new HorizontalLayout();
        typeField = new TextField();
        typeLayout.add(new Text("Type:"), typeField);
        typeLayout.setPadding(true);

        HorizontalLayout doorsLayout = new HorizontalLayout();
        doorsField = new TextField();
        doorsLayout.add(new Text("Doors:"), doorsField);
        doorsLayout.setPadding(true);

        HorizontalLayout manufacturedLayout = new HorizontalLayout();
        manufacturedField = new TextField();
        manufacturedLayout.add(new Text("Doors:"), manufacturedField);
        manufacturedLayout.setPadding(true);

        form.add(nameLayout, brandLayout, typeLayout, doorsLayout, manufacturedLayout, addSaveButton(grid));
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


    private Button addSaveButton(Grid<CarEntity> grid){
        Button saveButton = new Button("Save", VaadinIcon.SAFE.create());
        saveButton.addClickListener(buttonClickEvent -> {
            if(selectedCar.getId() == null){
                CarEntity carEntity = new CarEntity();
                carEntity.setCarName(selectedCar.getCarName());
                carEntity.setBrandId(selectedCar.getBrandId());
                carEntity.setCarType(selectedCar.getCarType());
                carEntity.setCarDoors(selectedCar.getCarDoors());
                carEntity.setCarManufactured(selectedCar.getCarManufactured());

                carService.add(carEntity);
                grid.setItems(carService.getAll());
                selectedCar = null;
                Notification.show("Saved.");
            }
            else{
                carService.update(selectedCar);
                grid.setItems(carService.getAll());
                Notification.show("Modified.");
            }
            form.setVisible(false);
        });
        return saveButton;
    }

}
