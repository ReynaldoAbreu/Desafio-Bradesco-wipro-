package com.reynaldoabreu.addressapi.api.resource;

import com.reynaldoabreu.addressapi.api.dto.AddressDTO;
import com.reynaldoabreu.addressapi.api.service.AddressService;
import com.reynaldoabreu.addressapi.entity.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/consulta-endereco")
public class AddressController {

    @Autowired
    private AddressService service;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public AddressDTO create(@RequestBody String cep){

        AddressDTO dto = AddressDTO.builder().cep(cep).build();

        Address entity = service.save(Address.builder().cep(dto.getCep()).build());

        return modelMapper.map(entity, AddressDTO.class);

    }

}
