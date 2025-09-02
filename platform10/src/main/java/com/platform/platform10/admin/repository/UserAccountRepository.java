/**
 * 프로그램명: UserAccountRepository
 * 기능: 사용자 계정 리포지토리
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.repository;

import com.platform.platform10.admin.entity.UserAccount;
import com.platform.platform10.admin.entity.UserAccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, UserAccountId> {
}
