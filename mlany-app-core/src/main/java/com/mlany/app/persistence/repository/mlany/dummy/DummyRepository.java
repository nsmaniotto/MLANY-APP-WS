package com.mlany.app.persistence.repository.mlany.dummy;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.mlany.dummy.Dummy;

@Repository
public interface DummyRepository extends CrudRepository<Dummy, Long>, JpaSpecificationExecutor<Dummy> {

	public Optional<Dummy> findById(Long id);

}