package hu.pte.ttk.vaadin.vaadindemo.car.service.impl;

import hu.pte.ttk.vaadin.vaadindemo.car.entity.CarEntity;
import hu.pte.ttk.vaadin.vaadindemo.car.service.CarService;
import hu.pte.ttk.vaadin.vaadindemo.core.service.impl.CoreCRUDServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl extends CoreCRUDServiceImpl<CarEntity> implements CarService {

    @Override
    protected void updateCore(CarEntity persistedEntity, CarEntity entity) {
        System.out.println(entity);
        persistedEntity.setCarName(entity.getCarName());
        persistedEntity.setBrand(entity.getBrand());
        persistedEntity.setCarType(entity.getCarType());
        persistedEntity.setCarDoors(entity.getCarDoors());
        persistedEntity.setCarManufactured(entity.getCarManufactured());
    }

    @Override
    protected Class<CarEntity> getManagedClass() {
        return CarEntity.class;
    }
}
