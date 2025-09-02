/**
 * 프로그램명: DeptService
 * 기능: 부서 관련 비즈니스 로직을 처리하는 서비스
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.common.security.service;

import com.platform.common.security.entity.Dept;
import com.platform.common.security.repository.DeptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeptService {

    private final DeptRepository deptRepository;

    public List<Dept> findByCompanyId(Long companyId) {
        return deptRepository.findById_CompanyId(companyId);
    }
}
