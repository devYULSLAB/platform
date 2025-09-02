/**
 * 프로그램명: CodeTypeRepository
 * 기능: CodeType 엔티티에 대한 데이터 접근 리포지토리
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.code.repository;

import com.platform.cmms10.code.entity.CodeType;
import com.platform.cmms10.code.entity.CodeTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeTypeRepository extends JpaRepository<CodeType, CodeTypeId> {
}
