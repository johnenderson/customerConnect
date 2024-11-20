package com.john.customerconnect.controller;

import com.john.customerconnect.controller.dto.ApiResponse;
import com.john.customerconnect.controller.dto.CreateCustomerDTO;
import com.john.customerconnect.controller.dto.PaginationResponse;
import com.john.customerconnect.controller.dto.UpdateCustomerDTO;
import com.john.customerconnect.entity.CustomerEntity;
import com.john.customerconnect.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping(path = "/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CreateCustomerDTO dto) {

        var customer = customerService.createCustomer(dto);

        return ResponseEntity.created(
                        URI.create("/customers/" + customer.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CustomerEntity>> findAll(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                               @RequestParam(name = "orderBy", defaultValue = "desc") String orderBy,
                                                               @RequestParam(name = "cpf", required = false) String cpf,
                                                               @RequestParam(name = "email", required = false) String email) {

        var pageResp = customerService.findAll(page, pageSize, orderBy, cpf, email);

        return ResponseEntity.ok(new ApiResponse<>(
                pageResp.getContent(),
                new PaginationResponse(pageResp.getNumber(), pageResp.getSize(), pageResp.getTotalElements(), pageResp.getTotalPages())
        ));

    }

    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerEntity> findById(@PathVariable("customerId") Long customerId) {

        var user = customerService.findById(customerId);

        return user.isPresent() ?
                ResponseEntity.ok(user.get()) :
                ResponseEntity.notFound().build();

    }

    @PutMapping(path = "/{customerId}")
    public ResponseEntity<CustomerEntity> updateById(@PathVariable("customerId") Long customerId,
                                                     @RequestBody UpdateCustomerDTO dto) {

        var customer = customerService.updateById(customerId, dto);

        return customer.isPresent() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();

    }

    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<CustomerEntity> deleteById(@PathVariable("customerId") Long customerId) {

        var deleted = customerService.deleteById(customerId);

        return deleted ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();

    }

}
