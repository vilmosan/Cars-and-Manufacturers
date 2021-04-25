package hu.pte.ttk.vaadin.vaadindemo.user.config;

import hu.pte.ttk.vaadin.vaadindemo.brand.entity.BrandEntity;
import hu.pte.ttk.vaadin.vaadindemo.brand.service.BrandService;
import hu.pte.ttk.vaadin.vaadindemo.car.entity.CarEntity;
import hu.pte.ttk.vaadin.vaadindemo.car.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;

@Configuration
public class CarsAppInitConfig {

	@Autowired
	private BrandService brandService;
	@Autowired
	private CarService carService;

	@PostConstruct
	private void init() {
		List<BrandEntity> brandEntities = brandService.getAll();
		BrandEntity brandEntity1 = new BrandEntity();
		BrandEntity brandEntity2 = new BrandEntity();
		BrandEntity brandEntity3 = new BrandEntity();
		BrandEntity brandEntity4 = new BrandEntity();
		BrandEntity brandEntity5 = new BrandEntity();
		CarEntity carEntity1 = new CarEntity();
		CarEntity carEntity2 = new CarEntity();
		CarEntity carEntity3 = new CarEntity();

		if (brandEntities.isEmpty()) {

			brandEntity1.setBrandName("Toyota");
			brandService.add(brandEntity1);
			brandEntity2.setBrandName("Suzuki");
			brandService.add(brandEntity2);
			brandEntity3.setBrandName("BMW");
			brandService.add(brandEntity3);
			brandEntity4.setBrandName("Mazda");
			brandService.add(brandEntity4);
			brandEntity5.setBrandName("Mitsubishi");
			brandService.add(brandEntity5);
		}

		List<CarEntity> carEntities = carService.getAll();
		if (carEntities.isEmpty()) {
			carEntity1.setCarName("Yaris");
			carEntity1.setBrand(brandEntity1);
			carEntity1.setCarType("1.3");
			carEntity1.setCarDoors(3);
			carEntity1.setCarManufactured(2009);
			carService.add(carEntity1);

			carEntity2.setCarName("Swift");
			carEntity2.setBrand(brandEntity2);
			carEntity2.setCarType("1.6 Sport");
			carEntity2.setCarDoors(4);
			carEntity2.setCarManufactured(2010);
			carService.add(carEntity2);

			carEntity3.setCarName("Lancer");
			carEntity3.setBrand(brandEntity5);
			carEntity3.setCarType("2.0 EVO");
			carEntity3.setCarDoors(5);
			carEntity3.setCarManufactured(2004);
			carService.add(carEntity3);
		}
	}
}
