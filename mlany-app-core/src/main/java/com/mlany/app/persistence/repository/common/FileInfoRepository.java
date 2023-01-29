package com.mlany.app.persistence.repository.common;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mlany.app.persistence.entity.common.FileInfo;

@Repository
public interface FileInfoRepository extends CrudRepository<FileInfo, Long>, JpaSpecificationExecutor<FileInfo> {

}