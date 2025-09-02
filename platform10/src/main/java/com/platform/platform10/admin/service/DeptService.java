/**
 * 프로그램명: DeptService
 * 기능: 부서 서비스
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.service;

import com.platform.platform10.admin.entity.Dept;
import com.platform.platform10.admin.entity.DeptId;
import com.platform.platform10.admin.repository.DeptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeptService {

    private final DeptRepository deptRepository;

    public List<Dept> findAllDepts() {
        return deptRepository.findAll();
    }

    public Optional<Dept> findDeptById(DeptId deptId) {
        return deptRepository.findById(deptId);
    }

    @Transactional
    public Dept saveDept(Dept dept) {
        return deptRepository.save(dept);
    }

    @Transactional
    public void deleteDept(DeptId deptId) {
        deptRepository.deleteById(deptId);
    }
}
