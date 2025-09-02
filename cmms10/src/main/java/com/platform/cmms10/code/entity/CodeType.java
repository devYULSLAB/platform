/**
 * 프로그램명: CodeType
 * 기능: 공통 코드 타입(분류)를 정의하는 엔티티
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.cmms10.code.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "code_type")
@Getter
@Setter
public class CodeType {

    @EmbeddedId
    private CodeTypeId id;

    @Column(name = "code_type_name")
    private String codeTypeName;

}
