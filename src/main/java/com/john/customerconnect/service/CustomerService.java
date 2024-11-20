package com.john.customerconnect.service;

import com.john.customerconnect.controller.dto.CreateCustomerDTO;
import com.john.customerconnect.controller.dto.UpdateCustomerDTO;
import com.john.customerconnect.entity.CustomerEntity;
import com.john.customerconnect.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.util.StringUtils.hasText;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerEntity createCustomer(CreateCustomerDTO dto) {

        var entity = new CustomerEntity();
        entity.setFullName(dto.fullName());
        entity.setCpf(dto.cpf());
        entity.setEmail(dto.email());
        entity.setCellPhone(dto.cellPhone());

        return customerRepository.save(entity);
    }

    public Page<CustomerEntity> findAll(Integer page,
                                        Integer pageSize,
                                        String orderBy,
                                        String cpf,
                                        String email) {

        var pageRequest = getPageRequest(page, pageSize, orderBy);

        return findWithFilter(cpf, email, pageRequest);
    }

    private Page<CustomerEntity> findWithFilter(String cpf, String email, PageRequest pageRequest) {
        if (hasText(cpf) && hasText(email)) {
            return customerRepository.findByCpfAndEmail(cpf, email, pageRequest);
        }

        if (hasText(cpf)) {
            return customerRepository.findByCpf(cpf, pageRequest);
        }

        if (hasText(email)) {
            return customerRepository.findByEmail(email, pageRequest);
        }

        return customerRepository.findAll(pageRequest);
    }

    private PageRequest getPageRequest(Integer page, Integer pageSize, String orderBy) {
        var direction = Sort.Direction.DESC;
        if (orderBy.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        }

        return PageRequest.of(page, pageSize, direction, "createdAt");
    }

    public Optional<CustomerEntity> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<CustomerEntity> updateById(Long customerId, UpdateCustomerDTO dto) {

        var customer = customerRepository.findById(customerId);

        updateFields(dto, customer);

        return customer;
    }

    private void updateFields(UpdateCustomerDTO dto, Optional<CustomerEntity> customer) {
        if(customer.isPresent()) {

            if(hasText(dto.fullname())){
                customer.get().setFullName(dto.fullname());
            }

            if(hasText(dto.email())){
                customer.get().setEmail(dto.email());
            }

            if(hasText(dto.cellPhone())){
                customer.get().setCellPhone(dto.cellPhone());
            }

            customerRepository.save(customer.get());
        }
    }

    public boolean deleteById(Long customerId) {

        var exists = customerRepository.existsById((customerId));

        if (exists){
            customerRepository.deleteById(customerId);
        }

        return exists;
    }
}
