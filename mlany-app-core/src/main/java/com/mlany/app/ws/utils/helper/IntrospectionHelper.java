package com.mlany.app.ws.utils.helper;

import org.springframework.data.repository.CrudRepository;

import com.mlany.app.persistence.entity.mlany.MlanyEntity;
import com.mlany.app.ws.exception.MlanyApplicationException;

/**
 * 
 * Helper for introspection operations.
 *
 */
public final class IntrospectionHelper {

	private IntrospectionHelper() {
		/* Constructor of helper class must be private */
	}

	public static <T extends MlanyEntity> T findOne(CrudRepository<T, Long> repo, Long id) {
		return id != null && repo != null ? repo.findById(id).orElse(null) : null;
	}

	public static <T extends MlanyEntity> T findMandatory(CrudRepository<T, Long> repo, Long id) {
		T entity = findOne(repo, id);
		if (entity == null) {
			throw new MlanyApplicationException("Unable to find entity in "
					+ (repo != null ? repo.getClass().getSimpleName() : "null repo") + "for: " + id);
		}
		return entity;
	}
}
