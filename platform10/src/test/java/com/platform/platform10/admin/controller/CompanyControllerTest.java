/**
 * 프로그램명: CompanyControllerTest
 * 기능: 회사 관리 컨트롤러 통합 테스트
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.controller;

import com.platform.platform10.admin.entity.Company;
import com.platform.platform10.admin.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class CompanyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void setUp() {
        Company company = new Company();
        company.setCompanyId("C0001");
        company.setCompanyName("샘플회사");
        company.setActive(true);
        companyRepository.save(company);
    }

    @Test
    void testListCompanies() throws Exception {
        mockMvc.perform(get("/admin/companies"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/company/list"))
                .andExpect(model().attributeExists("companies"))
                .andExpect(content().string(org.hamcrest.Matchers.containsString("샘플회사")));
    }

    @Test
    void testShowNewCompanyForm() throws Exception {
        mockMvc.perform(get("/admin/companies/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/company/form"))
                .andExpect(model().attributeExists("company"));
    }

    @Test
    void testSaveCompany() throws Exception {
        mockMvc.perform(post("/admin/companies")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("companyId", "C0002")
                        .param("companyName", "New Company")
                        .param("isActive", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/companies"));

        Company company = companyRepository.findById("C0002").orElse(null);
        assertThat(company).isNotNull();
        assertThat(company.getCompanyName()).isEqualTo("New Company");
    }

    @Test
    void testShowEditCompanyForm() throws Exception {
        mockMvc.perform(get("/admin/companies/C0001/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/company/form"))
                .andExpect(model().attributeExists("company"));
    }

    @Test
    void testUpdateCompany() throws Exception {
        mockMvc.perform(post("/admin/companies/C0001")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("companyName", "Updated Company Name")
                        .param("isActive", "false"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/companies"));

        Company company = companyRepository.findById("C0001").orElse(null);
        assertThat(company).isNotNull();
        assertThat(company.getCompanyName()).isEqualTo("Updated Company Name");
        assertThat(company.isActive()).isFalse();
    }

    @Test
    void testDeleteCompany() throws Exception {
        mockMvc.perform(post("/admin/companies/C0001/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/companies"));

        assertThat(companyRepository.findById("C0001")).isEmpty();
    }

    @Test
    void testShowCompanyDetails() throws Exception {
        mockMvc.perform(get("/admin/companies/C0001"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/company/detail"))
                .andExpect(model().attributeExists("company"));
    }
}
