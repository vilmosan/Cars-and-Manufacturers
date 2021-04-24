package hu.pte.ttk.vaadin.vaadindemo.brand.service.impl;

import hu.pte.ttk.vaadin.vaadindemo.brand.entity.BrandEntity;
import hu.pte.ttk.vaadin.vaadindemo.brand.service.BrandService;
import hu.pte.ttk.vaadin.vaadindemo.core.service.impl.CoreCRUDServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl extends CoreCRUDServiceImpl<BrandEntity> implements BrandService {

	@Override
	protected void updateCore(BrandEntity persistedEntity, BrandEntity entity) {
		persistedEntity.setBrandName(entity.getBrandName());
	}

	@Override
	protected Class<BrandEntity> getManagedClass() {
		return BrandEntity.class;
	}

	@Override
	public List<BrandEntity> findAllByName(String name) {
		List<BrandEntity> filteredList = new ArrayList<>();
		List<BrandEntity> brandEntities = getAll();

		for (BrandEntity brandEntity : brandEntities) {
			if(brandEntity.getBrandName().toLowerCase().contains(name.toLowerCase())) filteredList.add(brandEntity);
		}
		return filteredList;
	}
}
