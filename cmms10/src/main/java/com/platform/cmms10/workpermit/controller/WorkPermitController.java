/**
 * 프로그램명: WorkPermitController
 * 기능: 작업허가서 관련 웹 요청을 처리하는 컨트롤러
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.workpermit.controller;

import com.platform.cmms10.code.service.CodeService;
import com.platform.cmms10.workpermit.entity.WorkPermit;
import com.platform.cmms10.workpermit.service.WorkPermitService;
import com.platform.common.security.service.DeptService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/work-permits")
@RequiredArgsConstructor
public class WorkPermitController {

    private final WorkPermitService workPermitService;
    private final CodeService codeService;
    private final DeptService deptService;

    // A helper method to get companyId from session.
    // This would be replaced by a CompanyContextInterceptor in a real scenario.
    private Long getCompanyId(HttpSession session) {
        Object companyId = session.getAttribute("companyId");
        if (companyId == null) {
            // For testing purposes, default to 1 if not set.
            // A real interceptor would throw an error or redirect.
            session.setAttribute("companyId", 1L);
            return 1L;
        }
        return (Long) companyId;
    }

    @GetMapping
    public String list(Model model, @PageableDefault(size = 10) Pageable pageable, HttpSession session) {
        Long companyId = getCompanyId(session);
        Page<WorkPermit> workPermitPage = workPermitService.listWorkPermits(companyId, pageable);
        model.addAttribute("workPermitPage", workPermitPage);
        return "workpermit/workpermitList";
    }

    private void addDropdownDataToModel(Model model, Long companyId) {
        model.addAttribute("permitTypes", codeService.findByCompanyIdAndCodeType(companyId, "PERMIT_TYPE"));
        model.addAttribute("departments", deptService.findByCompanyId(companyId));
    }

    @GetMapping("/new")
    public String form(Model model, HttpSession session) {
        Long companyId = getCompanyId(session);
        model.addAttribute("workPermit", new WorkPermit());
        addDropdownDataToModel(model, companyId);
        return "workpermit/workpermitForm";
    }

    @GetMapping("/{permitId}")
    public String detail(@PathVariable Long permitId, Model model, HttpSession session) {
        Long companyId = getCompanyId(session);
        WorkPermit workPermit = workPermitService.findWorkPermitById(companyId, permitId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid work permit ID:" + permitId));
        model.addAttribute("workPermit", workPermit);
        return "workpermit/workpermitDetail";
    }

    @GetMapping("/{permitId}/edit")
    public String editForm(@PathVariable Long permitId, Model model, HttpSession session) {
        Long companyId = getCompanyId(session);
        WorkPermit workPermit = workPermitService.findWorkPermitById(companyId, permitId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid work permit ID:" + permitId));
        model.addAttribute("workPermit", workPermit);
        addDropdownDataToModel(model, companyId);
        return "workpermit/workpermitForm";
    }

    @PostMapping
    public String save(@ModelAttribute WorkPermit workPermit, HttpSession session, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
        Long companyId = getCompanyId(session);
        workPermitService.saveWorkPermit(workPermit, companyId, user.getUsername());
        redirectAttributes.addFlashAttribute("message", "Work permit saved successfully!");
        return "redirect:/work-permits";
    }

    @PostMapping("/{permitId}")
    public String update(@PathVariable Long permitId, @ModelAttribute WorkPermit workPermit, HttpSession session, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails user) {
        Long companyId = getCompanyId(session);
        workPermit.getId().setPermitId(permitId);
        workPermitService.saveWorkPermit(workPermit, companyId, user.getUsername());
        redirectAttributes.addFlashAttribute("message", "Work permit updated successfully!");
        return "redirect:/work-permits";
    }

    @PostMapping("/{permitId}/delete")
    public String delete(@PathVariable Long permitId, HttpSession session, RedirectAttributes redirectAttributes) {
        Long companyId = getCompanyId(session);
        workPermitService.deleteWorkPermit(companyId, permitId);
        redirectAttributes.addFlashAttribute("message", "Work permit deleted successfully!");
        return "redirect:/work-permits";
    }
}
