package com.example.admin.repository;

import com.example.admin.entity.SysDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 字典表 Repository 接口
 * </p>
 *
 * @author example
 */
@Repository
public interface SysDictionaryRepository extends JpaRepository<SysDictionary, String>, JpaSpecificationExecutor<SysDictionary> {

}