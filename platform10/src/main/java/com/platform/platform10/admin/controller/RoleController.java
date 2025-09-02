/**
 * 프로그램명: RoleController
 * 기능: 역할 관리 컨트롤러
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.controller;

import com.platform.platform10.admin.dto.RoleRequest;
import com.platform.platform10.admin.entity.Company;
import com.platform.platform10.admin.entity.Role;
import com.platform.platform10.admin.entity.RoleId;
import com.platform.platform10.admin.service.CompanyService;
import com.platform.platform10.admin.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final CompanyService companyService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("roles", roleService.findAllRoles());
        return "admin/role/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("role", new RoleRequest());
        model.addAttribute("companies", companyService.findAllCompanies());
        return "admin/role/form";
    }

    @PostMapping
    public String save(@ModelAttribute RoleRequest request) {
        Role role = new Role();
        RoleId roleId = new RoleId(request.getCompanyId(), request.getRoleId());
        role.setId(roleId);

        Company company = companyService.findCompanyById(request.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + request.getCompanyId()));
        role.setCompany(company);
        role.setRoleName(request.getRoleName());

        roleService.saveRole(role);
        return "redirect:/admin/roles";
    }

    @GetMapping("/{companyId}/{roleId}/edit")
    public String editForm(@PathVariable String companyId, @PathVariable String roleId, Model model) {
        RoleId id = new RoleId(companyId, roleId);
        Role role = roleService.findRoleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));

        RoleRequest request = new RoleRequest();
        request.setCompanyId(role.getId().getCompanyId());
        request.setRoleId(role.getId().getRoleId());
        request.setRoleName(role.getRoleName());

        model.addAttribute("role", request);
        model.addAttribute("companies", companyService.findAllCompanies());
        return "admin/role/form";
    }

    @PostMapping("/{companyId}/{roleId}")
    public String update(@PathVariable String companyId, @PathVariable String roleId, @ModelAttribute RoleRequest request) {
        RoleId id = new RoleId(companyId, roleId);
        Role role = roleService.findRoleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));

        role.setRoleName(request.getRoleName());
        roleService.saveRole(role);
        return "redirect:/admin/roles";
    }

    @PostMapping("/{companyId}/{roleId}/delete")
    public String delete(@PathVariable String companyId, @PathVariable String roleId) {
        RoleId id = new RoleId(companyId, roleId);
        roleService.deleteRole(id);
        return "redirect:/admin/roles";
    }

    @GetMapping("/{companyId}/{roleId}")
    public String detail(@PathVariable String companyId, @PathVariable String roleId, Model model) {
        RoleId id = new RoleId(companyId, roleId);
        Role role = roleService.findRoleById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id:" + id));
        model.addAttribute("role", role);
        return "admin/role/detail";
    }
}
