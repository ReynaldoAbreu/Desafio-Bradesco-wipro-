package com.reynaldoabreu.addressapi.api.service.imp;

import com.reynaldoabreu.addressapi.api.service.AddressService;
import com.reynaldoabreu.addressapi.entity.Address;
import com.reynaldoabreu.addressapi.model.repository.Repository;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImp implements AddressService {

    private final Repository repository;

    public AddressServiceImp(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Address save(Address address) {
        return repository.save(address);
    }


}
