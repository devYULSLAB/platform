/**
 * 프로그램명: WorkPermitController
 * 기능: 작업허가서 관련 웹 요청을 처리하는 컨트롤러
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.workpermit.controller;

import com.platform.cmms10.workpermit.entity.WorkPermit;
import com.platform.cmms10.workpermit.service.WorkPermitService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/work-permits")
@RequiredArgsConstructor
public class WorkPermitController {

    private final WorkPermitService workPermitService;

    // Mock session attributes for now
    private static final String MOCK_COMPANY_ID = "companyId";
    private static final String MOCK_USERNAME = "username";

    private void mockSession(HttpSession session) {
        if (session.getAttribute(MOCK_COMPANY_ID) == null) {
            session.setAttribute(MOCK_COMPANY_ID, 1L); // Example companyId
        }
        if (session.getAttribute(MOCK_USERNAME) == null) {
            session.setAttribute(MOCK_USERNAME, "mockUser"); // Example username
        }
    }

    @GetMapping
    public String list(Model model, @PageableDefault(size = 10) Pageable pageable, HttpSession session) {
        mockSession(session);
        Long companyId = (Long) session.getAttribute(MOCK_COMPANY_ID);
        Page<WorkPermit> workPermitPage = workPermitService.listWorkPermits(companyId, pageable);
        model.addAttribute("workPermitPage", workPermitPage);
        return "workpermit/workpermitList";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("workPermit", new WorkPermit());
        return "workpermit/workpermitForm";
    }

    @GetMapping("/{permitId}")
    public String detail(@PathVariable Long permitId, Model model, HttpSession session) {
        mockSession(session);
        Long companyId = (Long) session.getAttribute(MOCK_COMPANY_ID);
        WorkPermit workPermit = workPermitService.findWorkPermitById(companyId, permitId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid work permit ID:" + permitId));
        model.addAttribute("workPermit", workPermit);
        return "workpermit/workpermitDetail";
    }

    @GetMapping("/{permitId}/edit")
    public String editForm(@PathVariable Long permitId, Model model, HttpSession session) {
        mockSession(session);
        Long companyId = (Long) session.getAttribute(MOCK_COMPANY_ID);
        WorkPermit workPermit = workPermitService.findWorkPermitById(companyId, permitId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid work permit ID:" + permitId));
        model.addAttribute("workPermit", workPermit);
        return "workpermit/workpermitForm";
    }

    @PostMapping
    public String save(@ModelAttribute WorkPermit workPermit, HttpSession session, RedirectAttributes redirectAttributes) {
        mockSession(session);
        Long companyId = (Long) session.getAttribute(MOCK_COMPANY_ID);
        String username = (String) session.getAttribute(MOCK_USERNAME);

        // The service layer will enforce companyId and audit fields
        workPermitService.saveWorkPermit(workPermit, companyId, username);

        redirectAttributes.addFlashAttribute("message", "Work permit saved successfully!");
        return "redirect:/work-permits";
    }

    @PostMapping("/{permitId}")
    public String update(@PathVariable Long permitId, @ModelAttribute WorkPermit workPermit, HttpSession session, RedirectAttributes redirectAttributes) {
        mockSession(session);
        Long companyId = (Long) session.getAttribute(MOCK_COMPANY_ID);
        String username = (String) session.getAttribute(MOCK_USERNAME);

        // Ensure the ID from the path is set in the object
        workPermit.getId().setPermitId(permitId);

        workPermitService.saveWorkPermit(workPermit, companyId, username);

        redirectAttributes.addFlashAttribute("message", "Work permit updated successfully!");
        return "redirect:/work-permits";
    }

    @PostMapping("/{permitId}/delete")
    public String delete(@PathVariable Long permitId, HttpSession session, RedirectAttributes redirectAttributes) {
        mockSession(session);
        Long companyId = (Long) session.getAttribute(MOCK_COMPANY_ID);
        workPermitService.deleteWorkPermit(companyId, permitId);
        redirectAttributes.addFlashAttribute("message", "Work permit deleted successfully!");
        return "redirect:/work-permits";
    }
}
