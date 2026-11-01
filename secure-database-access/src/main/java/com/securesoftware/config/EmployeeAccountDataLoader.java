
package com.securesoftware.config;

import com.securesoftware.entity.EmployeeAccount;
import com.securesoftware.repository.EmployeeAccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeAccountDataLoader {

    @Bean
    CommandLineRunner loadEmployeeData(
            EmployeeAccountRepository repository) {

        return args -> {
            if (repository.count() == 0) {
                repository.save(new EmployeeAccount(
                        "EMP101",
                        "Sample Employee One",
                        "IT",
                        "employee1@example.com"
                ));

                repository.save(new EmployeeAccount(
                        "EMP102",
                        "Sample Employee Two",
                        "Operations",
                        "employee2@example.com"
                ));
            }
        };
    }
}
