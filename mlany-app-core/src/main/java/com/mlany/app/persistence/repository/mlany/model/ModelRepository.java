package com.mlany.app.persistence.repository.mlany.model;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.mlany.model.Model;

@Repository
public interface ModelRepository extends CrudRepository<Model, Long>, JpaSpecificationExecutor<Model> {
	List<Model> findByFamily(String family);
}