/**
 * 프로그램명: WorkPermit
 * 기능: 작업허가서(Work Permit) 엔티티
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.workpermit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "work_permit")
@Getter
@Setter
public class WorkPermit {

    @EmbeddedId
    private WorkPermitId id;

    @Column(name = "permit_name", nullable = false)
    private String permitName;

    @Column(name = "requestor_name", nullable = false)
    private String requestorName;

    @Column(name = "status")
    private String status; // e.g., REQUESTED, APPROVED, REJECTED, CLOSED

    @Column(name = "permit_type_code")
    private String permitTypeCode;

    @Column(name = "dept_id")
    private Long deptId;

    @Column(name = "created_by")
    private String createdBy;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Default constructor
    public WorkPermit() {
        this.id = new WorkPermitId();
    }
}
