
package com.securesoftware.service;

import com.securesoftware.entity.EmployeeAccount;
import com.securesoftware.repository.EmployeeAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import java.util.Optional;

@Service
public class EmployeeAccountService {

    private final EmployeeAccountRepository repository;

    public EmployeeAccountService(
            EmployeeAccountRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public Optional<EmployeeAccount> getEmployeeById(
            String employeeId) {
        return repository.findByEmployeeId(employeeId);
    }
}



