/**
 * 프로그램명: UserAccount
 * 기능: 사용자 계정 엔티티
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
@Table(name = "user_account", schema = "platform",
        uniqueConstraints = @UniqueConstraint(columnNames = {"company_id", "username"}))
@Getter
@Setter
public class UserAccount {

    @EmbeddedId
    private UserAccountId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("companyId")
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(nullable = false, length = 100)
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "company_id", referencedColumnName = "company_id", insertable = false, updatable = false),
            @JoinColumn(name = "site_id", referencedColumnName = "site_id", insertable = false, updatable = false)
    })
    private Site site;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "company_id", referencedColumnName = "company_id", insertable = false, updatable = false),
            @JoinColumn(name = "site_id", referencedColumnName = "site_id", insertable = false, updatable = false),
            @JoinColumn(name = "dept_id", referencedColumnName = "dept_id", insertable = false, updatable = false)
    })
    private Dept dept;

    @Column(length = 100)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
