/**
 * 프로그램명: DeptController
 * 기능: 부서 관리 컨트롤러
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.controller;

import com.platform.platform10.admin.dto.DeptRequest;
import com.platform.platform10.admin.entity.Dept;
import com.platform.platform10.admin.entity.DeptId;
import com.platform.platform10.admin.entity.Site;
import com.platform.platform10.admin.entity.SiteId;
import com.platform.platform10.admin.service.DeptService;
import com.platform.platform10.admin.service.SiteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/depts")
@RequiredArgsConstructor
public class DeptController {

    private final DeptService deptService;
    private final SiteService siteService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("depts", deptService.findAllDepts());
        return "admin/dept/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("dept", new DeptRequest());
        model.addAttribute("sites", siteService.findAllSites());
        model.addAttribute("depts", deptService.findAllDepts());
        return "admin/dept/form";
    }

    @PostMapping
    public String save(@ModelAttribute DeptRequest request) {
        Dept dept = new Dept();
        DeptId deptId = new DeptId(request.getCompanyId(), request.getSiteId(), request.getDeptId());
        dept.setId(deptId);

        SiteId siteId = new SiteId(request.getCompanyId(), request.getSiteId());
        Site site = siteService.findSiteById(siteId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid site Id:" + siteId));
        dept.setSite(site);

        if (request.getParentDeptId() != null && !request.getParentDeptId().isEmpty()) {
            DeptId parentDeptId = new DeptId(request.getCompanyId(), request.getSiteId(), request.getParentDeptId());
            Dept parentDept = deptService.findDeptById(parentDeptId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid parent dept Id:" + parentDeptId));
            dept.setParentDept(parentDept);
        }

        dept.setDeptName(request.getDeptName());
        dept.setDeptLevel(request.getDeptLevel());
        dept.setActive(request.getActive() != null && request.getActive());
        deptService.saveDept(dept);
        return "redirect:/admin/depts";
    }

    @GetMapping("/{companyId}/{siteId}/{deptId}/edit")
    public String editForm(@PathVariable String companyId, @PathVariable String siteId, @PathVariable String deptId, Model model) {
        DeptId id = new DeptId(companyId, siteId, deptId);
        Dept dept = deptService.findDeptById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dept Id:" + id));
        DeptRequest request = new DeptRequest();
        request.setCompanyId(dept.getId().getCompanyId());
        request.setSiteId(dept.getId().getSiteId());
        request.setDeptId(dept.getId().getDeptId());
        request.setDeptName(dept.getDeptName());
        if (dept.getParentDept() != null) {
            request.setParentDeptId(dept.getParentDept().getId().getDeptId());
        }
        request.setDeptLevel(dept.getDeptLevel());
        request.setActive(dept.isActive());

        model.addAttribute("dept", request);
        model.addAttribute("sites", siteService.findAllSites());
        model.addAttribute("depts", deptService.findAllDepts());
        return "admin/dept/form";
    }

    @PostMapping("/{companyId}/{siteId}/{deptId}")
    public String update(@PathVariable String companyId, @PathVariable String siteId, @PathVariable String deptId, @ModelAttribute DeptRequest request) {
        DeptId id = new DeptId(companyId, siteId, deptId);
        Dept dept = deptService.findDeptById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dept Id:" + id));

        if (request.getParentDeptId() != null && !request.getParentDeptId().isEmpty()) {
            DeptId parentDeptId = new DeptId(request.getCompanyId(), request.getSiteId(), request.getParentDeptId());
            Dept parentDept = deptService.findDeptById(parentDeptId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid parent dept Id:" + parentDeptId));
            dept.setParentDept(parentDept);
        } else {
            dept.setParentDept(null);
        }

        dept.setDeptName(request.getDeptName());
        dept.setDeptLevel(request.getDeptLevel());
        dept.setActive(request.getActive() != null && request.getActive());
        deptService.saveDept(dept);
        return "redirect:/admin/depts";
    }

    @PostMapping("/{companyId}/{siteId}/{deptId}/delete")
    public String delete(@PathVariable String companyId, @PathVariable String siteId, @PathVariable String deptId) {
        DeptId id = new DeptId(companyId, siteId, deptId);
        deptService.deleteDept(id);
        return "redirect:/admin/depts";
    }

    @GetMapping("/{companyId}/{siteId}/{deptId}")
    public String detail(@PathVariable String companyId, @PathVariable String siteId, @PathVariable String deptId, Model model) {
        DeptId id = new DeptId(companyId, siteId, deptId);
        Dept dept = deptService.findDeptById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid dept Id:" + id));
        model.addAttribute("dept", dept);
        return "admin/dept/detail";
    }
}
