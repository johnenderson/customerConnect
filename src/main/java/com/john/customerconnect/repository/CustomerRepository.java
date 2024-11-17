package com.john.customerconnect.repository;

import com.john.customerconnect.entity.CustomerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {


    Page<CustomerEntity> findByCpf(String cpf, PageRequest pageRequest);

    Page<CustomerEntity> findByEmail(String email, PageRequest pageRequest);

    Page<CustomerEntity> findByCpfAndEmail(String cpf, String email, PageRequest pageRequest);

}
