/**
 * 프로그램명: RoleService
 * 기능: 역할 서비스
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.service;

import com.platform.platform10.admin.entity.Role;
import com.platform.platform10.admin.entity.RoleId;
import com.platform.platform10.admin.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> findRoleById(RoleId roleId) {
        return roleRepository.findById(roleId);
    }

    @Transactional
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public void deleteRole(RoleId roleId) {
        roleRepository.deleteById(roleId);
    }
}
