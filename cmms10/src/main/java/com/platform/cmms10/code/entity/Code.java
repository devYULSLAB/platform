/**
 * 프로그램명: Code
 * 기능: 공통 코드를 정의하는 엔티티
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.code.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "code")
@Getter
@Setter
public class Code {

    @EmbeddedId
    private CodeId id;

    @Column(name = "code_name")
    private String codeName;

    @Column(name = "code_type")
    private String codeType;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "company_id", referencedColumnName = "company_id", insertable = false, updatable = false),
        @JoinColumn(name = "code_type", referencedColumnName = "code_type", insertable = false, updatable = false)
    })
    private CodeType codeTypeEntity;

}
