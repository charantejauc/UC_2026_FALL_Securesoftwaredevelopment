
package com.securesoftware.controller;

import com.securesoftware.entity.EmployeeAccount;
import com.securesoftware.service.EmployeeAccountService;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeAccountController {

    private final EmployeeAccountService service;

    public EmployeeAccountController(
            EmployeeAccountService service) {
        this.service = service;
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeAccount> getEmployee(
            @PathVariable
            @Pattern(regexp = "EMP[0-9]{3,6}")
            String employeeId) {

        return service.getEmployeeById(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }
}
