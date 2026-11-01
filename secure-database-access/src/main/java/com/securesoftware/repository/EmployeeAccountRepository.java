
package com.securesoftware.repository;

import com.securesoftware.entity.EmployeeAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmployeeAccountRepository
        extends JpaRepository<EmployeeAccount, Long> {

    Optional<EmployeeAccount> findByEmployeeId(
            String employeeId);
}
