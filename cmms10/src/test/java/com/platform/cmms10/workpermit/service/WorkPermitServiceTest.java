/**
 * 프로그램명: WorkPermitServiceTest
 * 기능: WorkPermitService의 단위 테스트
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.workpermit.service;

import com.platform.cmms10.workpermit.entity.WorkPermit;
import com.platform.cmms10.workpermit.entity.WorkPermitId;
import com.platform.cmms10.workpermit.repository.WorkPermitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkPermitServiceTest {

    @Mock
    private WorkPermitRepository workPermitRepository;

    @InjectMocks
    private WorkPermitService workPermitService;

    private WorkPermit workPermit;
    private final Long companyId = 1L;
    private final Long permitId = 100L;
    private final String username = "testUser";

    @BeforeEach
    void setUp() {
        WorkPermitId id = new WorkPermitId(companyId, permitId);
        workPermit = new WorkPermit();
        workPermit.setId(id);
        workPermit.setPermitName("Test Permit");
    }

    @Test
    void listWorkPermits_shouldReturnPageOfWorkPermits() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<WorkPermit> expectedPage = new PageImpl<>(Collections.singletonList(workPermit));
        when(workPermitRepository.findById_CompanyId(companyId, pageable)).thenReturn(expectedPage);

        // When
        Page<WorkPermit> result = workPermitService.listWorkPermits(companyId, pageable);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getPermitName()).isEqualTo("Test Permit");
        verify(workPermitRepository).findById_CompanyId(companyId, pageable);
    }

    @Test
    void findWorkPermitById_shouldReturnWorkPermit() {
        // Given
        when(workPermitRepository.findById_CompanyIdAndId_PermitId(companyId, permitId)).thenReturn(Optional.of(workPermit));

        // When
        Optional<WorkPermit> result = workPermitService.findWorkPermitById(companyId, permitId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get().getId().getPermitId()).isEqualTo(permitId);
        verify(workPermitRepository).findById_CompanyIdAndId_PermitId(companyId, permitId);
    }

    @Test
    void saveWorkPermit_shouldSetCompanyIdAndAuditFields() {
        // Given
        WorkPermit newPermit = new WorkPermit();
        newPermit.setPermitName("New Permit");
        when(workPermitRepository.save(any(WorkPermit.class))).thenReturn(newPermit);

        // When
        WorkPermit savedPermit = workPermitService.saveWorkPermit(newPermit, companyId, username);

        // Then
        assertThat(savedPermit).isNotNull();
        assertThat(savedPermit.getId().getCompanyId()).isEqualTo(companyId);
        assertThat(savedPermit.getCreatedBy()).isEqualTo(username);
        assertThat(savedPermit.getUpdatedBy()).isEqualTo(username);
        verify(workPermitRepository).save(newPermit);
    }

    @Test
    void deleteWorkPermit_shouldCallRepositoryDelete() {
        // Given
        when(workPermitRepository.findById_CompanyIdAndId_PermitId(companyId, permitId)).thenReturn(Optional.of(workPermit));
        doNothing().when(workPermitRepository).delete(workPermit);

        // When
        workPermitService.deleteWorkPermit(companyId, permitId);

        // Then
        verify(workPermitRepository).findById_CompanyIdAndId_PermitId(companyId, permitId);
        verify(workPermitRepository).delete(workPermit);
    }
}
