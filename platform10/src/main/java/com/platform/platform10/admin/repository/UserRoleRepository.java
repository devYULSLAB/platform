/**
 * 프로그램명: UserRoleRepository
 * 기능: 사용자-역할 매핑 리포지토리
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.repository;

import com.platform.platform10.admin.entity.UserRole;
import com.platform.platform10.admin.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {
}
