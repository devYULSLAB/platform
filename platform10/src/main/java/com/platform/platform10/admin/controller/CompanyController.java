/**
 * 프로그램명: CompanyController
 * 기능: 회사 관리 컨트롤러
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.controller;

import com.platform.platform10.admin.dto.CompanyRequest;
import com.platform.platform10.admin.entity.Company;
import com.platform.platform10.admin.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("companies", companyService.findAllCompanies());
        return "admin/company/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("company", new CompanyRequest());
        return "admin/company/form";
    }

    @PostMapping
    public String save(@ModelAttribute CompanyRequest request) {
        Company company = new Company();
        company.setCompanyId(request.getCompanyId());
        company.setCompanyName(request.getCompanyName());
        company.setActive(request.getActive() != null && request.getActive());
        companyService.saveCompany(company);
        return "redirect:/admin/companies";
    }

    @GetMapping("/{companyId}/edit")
    public String editForm(@PathVariable String companyId, Model model) {
        Company company = companyService.findCompanyById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + companyId));
        CompanyRequest request = new CompanyRequest();
        request.setCompanyId(company.getCompanyId());
        request.setCompanyName(company.getCompanyName());
        request.setActive(company.isActive());
        model.addAttribute("company", request);
        return "admin/company/form";
    }

    @PostMapping("/{companyId}")
    public String update(@PathVariable String companyId, @ModelAttribute CompanyRequest request) {
        Company company = companyService.findCompanyById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + companyId));
        company.setCompanyName(request.getCompanyName());
        company.setActive(request.getActive() != null && request.getActive());
        companyService.saveCompany(company);
        return "redirect:/admin/companies";
    }

    @PostMapping("/{companyId}/delete")
    public String delete(@PathVariable String companyId) {
        companyService.deleteCompany(companyId);
        return "redirect:/admin/companies";
    }

    @GetMapping("/{companyId}")
    public String detail(@PathVariable String companyId, Model model) {
        Company company = companyService.findCompanyById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + companyId));
        model.addAttribute("company", company);
        return "admin/company/detail";
    }
}
