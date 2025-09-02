/**
 * 프로그램명: Role
 * 기능: 역할 엔티티
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "role", schema = "platform",
        uniqueConstraints = @UniqueConstraint(columnNames = {"company_id", "role_name"}))
@Getter
@Setter
public class Role {

    @EmbeddedId
    private RoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "role_name", nullable = false, length = 100)
    private String roleName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
