/**
 * 프로그램명: UserAccountController
 * 기능: 사용자 계정 관리 컨트롤러
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.controller;

import com.platform.platform10.admin.dto.UserAccountRequest;
import com.platform.platform10.admin.entity.*;
import com.platform.platform10.admin.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/user-accounts")
@RequiredArgsConstructor
public class UserAccountController {

    private final UserAccountService userAccountService;
    private final CompanyService companyService;
    private final SiteService siteService;
    private final DeptService deptService;
    // PasswordEncoder would be injected here in a real app

    @GetMapping
    public String list(Model model) {
        model.addAttribute("userAccounts", userAccountService.findAllUserAccounts());
        return "admin/useraccount/list";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("userAccount", new UserAccountRequest());
        model.addAttribute("companies", companyService.findAllCompanies());
        model.addAttribute("sites", siteService.findAllSites());
        model.addAttribute("depts", deptService.findAllDepts());
        return "admin/useraccount/form";
    }

    @PostMapping
    public String save(@ModelAttribute UserAccountRequest request) {
        UserAccount userAccount = new UserAccount();
        UserAccountId userAccountId = new UserAccountId(request.getCompanyId(), request.getUserId());
        userAccount.setId(userAccountId);

        Company company = companyService.findCompanyById(request.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company Id:" + request.getCompanyId()));
        userAccount.setCompany(company);

        if (request.getSiteId() != null && !request.getSiteId().isEmpty()) {
            SiteId siteId = new SiteId(request.getCompanyId(), request.getSiteId());
            Site site = siteService.findSiteById(siteId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid site Id:" + siteId));
            userAccount.setSite(site);
        }

        if (request.getDeptId() != null && !request.getDeptId().isEmpty()) {
            DeptId deptId = new DeptId(request.getCompanyId(), request.getSiteId(), request.getDeptId());
            Dept dept = deptService.findDeptById(deptId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid dept Id:" + deptId));
            userAccount.setDept(dept);
        }

        userAccount.setUsername(request.getUsername());
        // In a real app: userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        userAccount.setPassword(request.getPassword()); // Storing plain text for now as per sample data
        userAccount.setPhone(request.getPhone());
        userAccount.setEmail(request.getEmail());
        userAccount.setActive(request.getActive() != null && request.getActive());

        userAccountService.saveUserAccount(userAccount);
        return "redirect:/admin/user-accounts";
    }

    @GetMapping("/{companyId}/{userId}/edit")
    public String editForm(@PathVariable String companyId, @PathVariable String userId, Model model) {
        UserAccountId id = new UserAccountId(companyId, userId);
        UserAccount userAccount = userAccountService.findUserAccountById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user account Id:" + id));

        UserAccountRequest request = new UserAccountRequest();
        request.setCompanyId(userAccount.getId().getCompanyId());
        request.setUserId(userAccount.getId().getUserId());
        request.setUsername(userAccount.getUsername());
        if (userAccount.getSite() != null) {
            request.setSiteId(userAccount.getSite().getId().getSiteId());
        }
        if (userAccount.getDept() != null) {
            request.setDeptId(userAccount.getDept().getId().getDeptId());
        }
        request.setPhone(userAccount.getPhone());
        request.setEmail(userAccount.getEmail());
        request.setActive(userAccount.isActive());

        model.addAttribute("userAccount", request);
        model.addAttribute("companies", companyService.findAllCompanies());
        model.addAttribute("sites", siteService.findAllSites());
        model.addAttribute("depts", deptService.findAllDepts());
        return "admin/useraccount/form";
    }

    @PostMapping("/{companyId}/{userId}")
    public String update(@PathVariable String companyId, @PathVariable String userId, @ModelAttribute UserAccountRequest request) {
        UserAccountId id = new UserAccountId(companyId, userId);
        UserAccount userAccount = userAccountService.findUserAccountById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user account Id:" + id));

        // Update logic here...
        userAccount.setUsername(request.getUsername());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            // In a real app: userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
             userAccount.setPassword(request.getPassword());
        }

        if (request.getSiteId() != null && !request.getSiteId().isEmpty()) {
            SiteId siteId = new SiteId(request.getCompanyId(), request.getSiteId());
            Site site = siteService.findSiteById(siteId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid site Id:" + siteId));
            userAccount.setSite(site);
        } else {
            userAccount.setSite(null);
        }

        if (request.getDeptId() != null && !request.getDeptId().isEmpty()) {
            DeptId deptId = new DeptId(request.getCompanyId(), request.getSiteId(), request.getDeptId());
            Dept dept = deptService.findDeptById(deptId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid dept Id:" + deptId));
            userAccount.setDept(dept);
        } else {
            userAccount.setDept(null);
        }

        userAccount.setPhone(request.getPhone());
        userAccount.setEmail(request.getEmail());
        userAccount.setActive(request.getActive() != null && request.getActive());

        userAccountService.saveUserAccount(userAccount);
        return "redirect:/admin/user-accounts";
    }

    @PostMapping("/{companyId}/{userId}/delete")
    public String delete(@PathVariable String companyId, @PathVariable String userId) {
        UserAccountId id = new UserAccountId(companyId, userId);
        userAccountService.deleteUserAccount(id);
        return "redirect:/admin/user-accounts";
    }

    @GetMapping("/{companyId}/{userId}")
    public String detail(@PathVariable String companyId, @PathVariable String userId, Model model) {
        UserAccountId id = new UserAccountId(companyId, userId);
        UserAccount userAccount = userAccountService.findUserAccountById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user account Id:" + id));
        model.addAttribute("userAccount", userAccount);
        return "admin/useraccount/detail";
    }
}
