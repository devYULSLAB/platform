/**
 * 프로그램명: UserRoleRepository
 * 기능: UserRole 엔티티에 대한 데이터 접근 리포지토리
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.common.security.repository;

import com.platform.common.security.entity.UserRole;
import com.platform.common.security.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
