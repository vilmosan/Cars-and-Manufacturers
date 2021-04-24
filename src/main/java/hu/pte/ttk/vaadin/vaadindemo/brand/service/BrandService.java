package hu.pte.ttk.vaadin.vaadindemo.brand.service;

import hu.pte.ttk.vaadin.vaadindemo.brand.entity.BrandEntity;
import hu.pte.ttk.vaadin.vaadindemo.core.service.CoreCRUDService;

import java.util.List;

public interface BrandService extends CoreCRUDService<BrandEntity> {

	List<BrandEntity> findAllByName(String name);
}
