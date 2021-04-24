package hu.pte.ttk.vaadin.vaadindemo.core.service.impl;

import hu.pte.ttk.vaadin.vaadindemo.core.entity.CoreEntity;
import hu.pte.ttk.vaadin.vaadindemo.core.service.CoreCRUDService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class CoreCRUDServiceImpl<T extends CoreEntity> implements CoreCRUDService<T> {

	@Autowired
	protected EntityManager entityManager;

	@Override
	public void add(T entity) {
		System.out.println(entity);
		entityManager.persist(entity);
	}

	@Override
	public void remove(T entity) {
		entityManager.remove(findById(entity.getId()));
	}

	@Override
	public List<T> getAll() {
		return entityManager.createQuery("SELECT n FROM " + getManagedClass().getSimpleName() + " n", getManagedClass()).getResultList();
	}

	@Override
	public void update(T entity) {
		T persistedEntity = findById(entity.getId());
		updateCore(persistedEntity, entity);
		entityManager.merge(persistedEntity);
	}

	@Override
	public T findById(Long id) {
		return entityManager.find(getManagedClass(), id);
	}

	protected abstract void updateCore(T persistedEntity, T entity);

	protected abstract Class<T> getManagedClass();
}
