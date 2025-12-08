package com.example.admin.service.impl;

import com.example.admin.entity.SysDepartment;
import com.example.admin.repository.SysDepartmentRepository;
import com.example.admin.service.ISysDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 部门服务实现类
 *
 * @author example
 */
@Service
public class SysDepartmentServiceImpl implements ISysDepartmentService {

    @Autowired
    private SysDepartmentRepository sysDepartmentRepository;

    @Override
    public SysDepartment findById(String id) {
        Optional<SysDepartment> optionalDepartment = sysDepartmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    @Override
    public List<SysDepartment> findAll() {
        return sysDepartmentRepository.findAll();
    }

    @Override
    public SysDepartment saveDepartment(SysDepartment department) {
        return sysDepartmentRepository.save(department);
    }

    @Override
    public SysDepartment updateDepartment(SysDepartment department) {
        return sysDepartmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(String id) {
        sysDepartmentRepository.deleteById(id);
    }

    @Override
    public void deleteBatch(List<String> ids) {
        sysDepartmentRepository.deleteAllById(ids);
    }

}