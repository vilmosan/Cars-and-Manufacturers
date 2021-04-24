package hu.pte.ttk.vaadin.vaadindemo.car.service;

import hu.pte.ttk.vaadin.vaadindemo.car.entity.CarEntity;
import hu.pte.ttk.vaadin.vaadindemo.core.service.CoreCRUDService;

import java.util.List;

public interface CarService extends CoreCRUDService<CarEntity> {

	List<CarEntity> findAllByName(String name);

}
