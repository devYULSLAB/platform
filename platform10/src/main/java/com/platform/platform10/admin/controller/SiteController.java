/**
 * 프로그램명: SiteController
 * 기능: 사이트 관리 컨트롤러
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.controller;

import com.platform.platform10.admin.dto.SiteRequest;
import com.platform.platform10.admin.entity.Company;
import com.platform.platform10.admin.entity.Site;
import com.platform.platform10.admin.entity.SiteId;
import com.platform.platform10.admin.service.CompanyService;
import com.platform.platform10.admin.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/sites")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;
    private final CompanyService companyService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("sites", siteService.findAllSites());
        return "admin/site/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("site", new SiteRequest());
        model.addAttribute("companies", companyService.findAllCompanies());
        return "admin/site/form";
    }

    @PostMapping
    public String save(@ModelAttribute SiteRequest request) {
        Site site = new Site();
        SiteId siteId = new SiteId(request.getCompanyId(), request.getSiteId());
        site.setId(siteId);
        Company company = companyService.findCompanyById(request.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + request.getCompanyId()));
        site.setCompany(company);
        site.setSiteName(request.getSiteName());
        site.setActive(request.getActive() != null && request.getActive());
        siteService.saveSite(site);
        return "redirect:/admin/sites";
    }

    @GetMapping("/{companyId}/{siteId}/edit")
    public String editForm(@PathVariable String companyId, @PathVariable String siteId, Model model) {
        SiteId id = new SiteId(companyId, siteId);
        Site site = siteService.findSiteById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid site Id:" + id));
        SiteRequest request = new SiteRequest();
        request.setCompanyId(site.getId().getCompanyId());
        request.setSiteId(site.getId().getSiteId());
        request.setSiteName(site.getSiteName());
        request.setActive(site.isActive());
        model.addAttribute("site", request);
        model.addAttribute("companies", companyService.findAllCompanies());
        return "admin/site/form";
    }

    @PostMapping("/{companyId}/{siteId}")
    public String update(@PathVariable String companyId, @PathVariable String siteId, @ModelAttribute SiteRequest request) {
        SiteId id = new SiteId(companyId, siteId);
        Site site = siteService.findSiteById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid site Id:" + id));
        site.setSiteName(request.getSiteName());
        site.setActive(request.getActive() != null && request.getActive());
        siteService.saveSite(site);
        return "redirect:/admin/sites";
    }

    @PostMapping("/{companyId}/{siteId}/delete")
    public String delete(@PathVariable String companyId, @PathVariable String siteId) {
        SiteId id = new SiteId(companyId, siteId);
        siteService.deleteSite(id);
        return "redirect:/admin/sites";
    }

    @GetMapping("/{companyId}/{siteId}")
    public String detail(@PathVariable String companyId, @PathVariable String siteId, Model model) {
        SiteId id = new SiteId(companyId, siteId);
        Site site = siteService.findSiteById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid site Id:" + id));
        model.addAttribute("site", site);
        return "admin/site/detail";
    }
}
