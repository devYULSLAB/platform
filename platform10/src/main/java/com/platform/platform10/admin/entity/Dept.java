/**
 * 프로그램명: Dept
 * 기능: 부서 엔티티
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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dept", schema = "platform")
@Getter
@Setter
public class Dept {

    @EmbeddedId
    private DeptId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "company_id", referencedColumnName = "company_id", insertable = false, updatable = false),
            @JoinColumn(name = "site_id", referencedColumnName = "site_id", insertable = false, updatable = false)
    })
    private Site site;

    @Column(name = "dept_name", nullable = false, length = 100)
    private String deptName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "company_id", referencedColumnName = "company_id", insertable = false, updatable = false),
            @JoinColumn(name = "site_id", referencedColumnName = "site_id", insertable = false, updatable = false),
            @JoinColumn(name = "parent_dept_id", referencedColumnName = "dept_id", insertable = false, updatable = false)
    })
    private Dept parentDept;

    @OneToMany(mappedBy = "parentDept")
    private List<Dept> childDepts = new ArrayList<>();

    @Column(name = "dept_level")
    private Integer deptLevel;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
