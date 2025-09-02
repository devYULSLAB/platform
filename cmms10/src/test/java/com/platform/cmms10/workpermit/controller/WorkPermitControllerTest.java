/**
 * 프로그램명: WorkPermitControllerTest
 * 기능: WorkPermitController의 통합 테스트
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.workpermit.controller;

import com.platform.cmms10.workpermit.entity.WorkPermit;
import com.platform.cmms10.workpermit.entity.WorkPermitId;
import com.platform.cmms10.workpermit.service.WorkPermitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkPermitController.class)
class WorkPermitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkPermitService workPermitService;

    private WorkPermit workPermit;
    private final Long companyId = 1L;
    private final Long permitId = 100L;

    @BeforeEach
    void setUp() {
        WorkPermitId id = new WorkPermitId(companyId, permitId);
        workPermit = new WorkPermit();
        workPermit.setId(id);
        workPermit.setPermitName("Live Fire Test");
        workPermit.setStatus("APPROVED");
    }

    @Test
    void testListWorkPermits() throws Exception {
        Page<WorkPermit> page = new PageImpl<>(Collections.singletonList(workPermit));
        given(workPermitService.listWorkPermits(eq(companyId), any(PageRequest.class))).willReturn(page);

        mockMvc.perform(get("/work-permits").sessionAttr("companyId", companyId))
                .andExpect(status().isOk())
                .andExpect(view().name("workpermit/workpermitList"))
                .andExpect(model().attributeExists("workPermitPage"))
                .andExpect(model().attribute("workPermitPage", page));
    }

    @Test
    void testShowNewForm() throws Exception {
        mockMvc.perform(get("/work-permits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("workpermit/workpermitForm"))
                .andExpect(model().attributeExists("workPermit"));
    }

    @Test
    void testSaveWorkPermit() throws Exception {
        given(workPermitService.saveWorkPermit(any(WorkPermit.class), eq(companyId), eq("mockUser"))).willReturn(workPermit);

        mockMvc.perform(post("/work-permits")
                        .sessionAttr("companyId", companyId)
                        .sessionAttr("username", "mockUser")
                        .param("permitName", "New Permit")
                        .param("requestorName", "Test User"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/work-permits"));
    }

    @Test
    void testShowDetail() throws Exception {
        given(workPermitService.findWorkPermitById(companyId, permitId)).willReturn(Optional.of(workPermit));

        mockMvc.perform(get("/work-permits/{id}", permitId).sessionAttr("companyId", companyId))
                .andExpect(status().isOk())
                .andExpect(view().name("workpermit/workpermitDetail"))
                .andExpect(model().attributeExists("workPermit"));
    }
}
