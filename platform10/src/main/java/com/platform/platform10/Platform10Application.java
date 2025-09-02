/**
 * 프로그램명: Platform10 Main Application
 * 기능: 플랫폼 관리자 애플리케이션의 메인 진입점.
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// Scan for components, repositories, and entities in common modules
@ComponentScan(basePackages = {"com.platform.platform10", "com.platform.common"})
@EnableJpaRepositories(basePackages = {"com.platform.common.security.repository"})
@EntityScan(basePackages = {"com.platform.common.security.entity"})
public class Platform10Application {

    public static void main(String[] args) {
        SpringApplication.run(Platform10Application.class, args);
    }

}
