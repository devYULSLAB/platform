/**
 * 프로그램명: Company Controller
 * 기능: 플랫폼 관리 - 회사 정보 CRUD 컨트롤러
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.controller;

import com.platform.common.security.entity.Company;
import com.platform.common.security.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/admin/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyRepository companyRepository;

    /**
     * 회사 목록 조회
     */
    @GetMapping
    public String list(Pageable pageable, Model model) {
        Page<Company> page = companyRepository.findAll(pageable);
        model.addAttribute("page", page);
        return "admin/company/companyList";
    }

    /**
     * 회사 상세 정보 조회
     */
    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
            return "admin/company/companyDetail";
        } else {
            // Or a proper error page
            return "redirect:/admin/companies";
        }
    }

    /**
     * 새 회사 등록 폼
     */
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("company", new Company());
        return "admin/company/companyForm";
    }

    /**
     * 회사 정보 수정 폼
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable("id") Long id, Model model) {
        Optional<Company> company = companyRepository.findById(id);
        if (company.isPresent()) {
            model.addAttribute("company", company.get());
            return "admin/company/companyForm";
        } else {
            return "redirect:/admin/companies";
        }
    }

    /**
     * 회사 정보 저장 (생성)
     */
    @PostMapping
    public String save(@ModelAttribute Company company) {
        companyRepository.save(company);
        return "redirect:/admin/companies";
    }

    /**
     * 회사 정보 수정
     */
    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute Company company) {
        // Ensure the ID is set for the update
        company.setCompanyId(id);
        companyRepository.save(company);
        return "redirect:/admin/companies";
    }

    /**
     * 회사 정보 삭제
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        companyRepository.deleteById(id);
        return "redirect:/admin/companies";
    }
}
