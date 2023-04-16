package com.reynaldoabreu.addressapi.model.repository;

import com.reynaldoabreu.addressapi.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository extends JpaRepository<Address, Long> {

}
