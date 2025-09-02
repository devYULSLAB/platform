/**
 * 프로그램명: UserAccountService
 * 기능: 사용자 계정 서비스
 * 생성자: devYULSLAB
 * 생성일: 2025-02-28
 * 변경일: 2025-02-28
 */
package com.platform.platform10.admin.service;

import com.platform.platform10.admin.entity.UserAccount;
import com.platform.platform10.admin.entity.UserAccountId;
import com.platform.platform10.admin.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;

    public List<UserAccount> findAllUserAccounts() {
        return userAccountRepository.findAll();
    }

    public Optional<UserAccount> findUserAccountById(UserAccountId userAccountId) {
        return userAccountRepository.findById(userAccountId);
    }

    @Transactional
    public UserAccount saveUserAccount(UserAccount userAccount) {
        // In a real application, you would encode the password here
        return userAccountRepository.save(userAccount);
    }

    @Transactional
    public void deleteUserAccount(UserAccountId userAccountId) {
        userAccountRepository.deleteById(userAccountId);
    }
}
