/**
 * 프로그램명: CompanyService
 * 기능: 회사 서비스
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.service;

import com.platform.platform10.admin.entity.Company;
import com.platform.platform10.admin.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<Company> findCompanyById(String companyId) {
        return companyRepository.findById(companyId);
    }

    @Transactional
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    @Transactional
    public void deleteCompany(String companyId) {
        companyRepository.deleteById(companyId);
    }
}
