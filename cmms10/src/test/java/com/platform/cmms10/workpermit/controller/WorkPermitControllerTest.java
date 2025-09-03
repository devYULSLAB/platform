/**
 * 프로그램명: WorkPermitControllerTest
 * 기능: WorkPermitController의 통합 테스트
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.workpermit.controller;

import com.platform.cmms10.code.entity.Code;
import com.platform.cmms10.code.entity.CodeId;
import com.platform.cmms10.code.service.CodeService;
import com.platform.cmms10.workpermit.entity.WorkPermit;
import com.platform.cmms10.workpermit.entity.WorkPermitId;
import com.platform.cmms10.workpermit.service.WorkPermitService;
import com.platform.common.security.entity.Dept;
import com.platform.common.security.entity.DeptId;
import com.platform.common.security.service.DeptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkPermitController.class)
class WorkPermitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean private WorkPermitService workPermitService;
    @MockitoBean private CodeService codeService;
    @MockitoBean private DeptService deptService;

    private WorkPermit workPermit;
    private List<Code> mockPermitTypes;
    private List<Dept> mockDepartments;
    private final Long companyId = 1L;
    private final Long permitId = 100L;

    @BeforeEach
    void setUp() {
        // Mock WorkPermit
        WorkPermitId id = new WorkPermitId(companyId, permitId);
        workPermit = new WorkPermit();
        workPermit.setId(id);
        workPermit.setPermitName("Live Fire Test");
        workPermit.setStatus("APPROVED");
        workPermit.setPermitTypeCode("HOT_WORK");
        workPermit.setDeptId(1L);

        // Mock Permit Types (Code)
        Code permitType1 = new Code();
        permitType1.setId(new CodeId(companyId, "HOT_WORK"));
        permitType1.setCodeName("Hot Work Permit");
        mockPermitTypes = Collections.singletonList(permitType1);

        // Mock Departments
        Dept dept1 = new Dept();
        dept1.setId(new DeptId(companyId, 1L));
        dept1.setDeptName("Maintenance");
        mockDepartments = Collections.singletonList(dept1);
    }

    @Test
    @WithMockUser(username = "testuser")
    void testListWorkPermits() throws Exception {
        Page<WorkPermit> page = new PageImpl<>(Collections.singletonList(workPermit));
        given(workPermitService.listWorkPermits(eq(companyId), any(PageRequest.class))).willReturn(page);

        mockMvc.perform(get("/work-permits").sessionAttr("companyId", companyId))
                .andExpect(status().isOk())
                .andExpect(view().name("workpermit/workpermitList"))
                .andExpect(model().attributeExists("workPermitPage"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testShowNewForm() throws Exception {
        given(codeService.findByCompanyIdAndCodeType(companyId, "PERMIT_TYPE")).willReturn(mockPermitTypes);
        given(deptService.findByCompanyId(companyId)).willReturn(mockDepartments);

        mockMvc.perform(get("/work-permits/new").sessionAttr("companyId", companyId))
                .andExpect(status().isOk())
                .andExpect(view().name("workpermit/workpermitForm"))
                .andExpect(model().attributeExists("workPermit", "permitTypes", "departments"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testShowEditForm() throws Exception {
        given(workPermitService.findWorkPermitById(companyId, permitId)).willReturn(Optional.of(workPermit));
        given(codeService.findByCompanyIdAndCodeType(companyId, "PERMIT_TYPE")).willReturn(mockPermitTypes);
        given(deptService.findByCompanyId(companyId)).willReturn(mockDepartments);

        mockMvc.perform(get("/work-permits/{id}/edit", permitId).sessionAttr("companyId", companyId))
                .andExpect(status().isOk())
                .andExpect(view().name("workpermit/workpermitForm"))
                .andExpect(model().attributeExists("workPermit", "permitTypes", "departments"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void testSaveWorkPermit() throws Exception {
        given(workPermitService.saveWorkPermit(any(WorkPermit.class), eq(companyId), eq("testuser"))).willReturn(workPermit);

        mockMvc.perform(post("/work-permits")
                        .with(csrf()) // Add CSRF token for POST requests
                        .sessionAttr("companyId", companyId)
                        .param("permitName", "New Permit")
                        .param("requestorName", "Test User")
                        .param("permitTypeCode", "HOT_WORK")
                        .param("deptId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/work-permits"));
    }
}
