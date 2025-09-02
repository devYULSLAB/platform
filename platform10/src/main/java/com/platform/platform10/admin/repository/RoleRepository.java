/**
 * 프로그램명: RoleRepository
 * 기능: 역할 리포지토리
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.repository;

import com.platform.platform10.admin.entity.Role;
import com.platform.platform10.admin.entity.RoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, RoleId> {
}
