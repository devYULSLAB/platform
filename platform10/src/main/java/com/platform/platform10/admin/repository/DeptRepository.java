/**
 * 프로그램명: DeptRepository
 * 기능: 부서 리포지토리
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.repository;

import com.platform.platform10.admin.entity.Dept;
import com.platform.platform10.admin.entity.DeptId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<Dept, DeptId> {
}
