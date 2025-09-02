/**
 * 프로그램명: DeptRepository
 * 기능: Dept 엔티티에 대한 데이터 접근 리포지토리
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.common.security.repository;

import com.platform.common.security.entity.Dept;
import com.platform.common.security.entity.DeptId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends JpaRepository<Dept, DeptId> {
    List<Dept> findById_CompanyId(Long companyId);
}
