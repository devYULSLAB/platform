/**
 * 프로그램명: CodeService
 * 기능: 공통 코드 관련 비즈니스 로직을 처리하는 서비스
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.code.service;

import com.platform.cmms10.code.entity.Code;
import com.platform.cmms10.code.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CodeService {

    private final CodeRepository codeRepository;

    /**
     * 특정 회사와 코드 타입에 속하는 코드 목록을 조회합니다.
     *
     * @param companyId 회사 ID
     * @param codeType 코드 타입 (예: "PERMIT_TYPE")
     * @return 코드 목록
     */
    public List<Code> findByCompanyIdAndCodeType(Long companyId, String codeType) {
        return codeRepository.findAllById_CompanyIdAndCodeTypeOrderBySortOrderAsc(companyId, codeType);
    }
}
