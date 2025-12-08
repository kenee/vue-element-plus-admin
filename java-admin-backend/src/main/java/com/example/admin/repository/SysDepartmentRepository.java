package com.example.admin.repository;

import com.example.admin.entity.SysDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 部门表 Repository 接口
 * </p>
 *
 * @author example
 */
@Repository
public interface SysDepartmentRepository extends JpaRepository<SysDepartment, String>, JpaSpecificationExecutor<SysDepartment> {

}