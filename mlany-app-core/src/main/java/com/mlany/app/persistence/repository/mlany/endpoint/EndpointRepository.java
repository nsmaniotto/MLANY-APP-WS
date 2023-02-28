package com.mlany.app.persistence.repository.mlany.endpoint;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.mlany.endpoint.Endpoint;

@Repository
public interface EndpointRepository extends CrudRepository<Endpoint, Long>, JpaSpecificationExecutor<Endpoint> {

}