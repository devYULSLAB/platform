/**
 * 프로그램명: UserAccountId
 * 기능: UserAccount 엔티티의 복합키
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserAccountId implements Serializable {

    @Column(name = "company_id", length = 5)
    private String companyId;

    @Column(name = "user_id", length = 5)
    private String userId;
}
