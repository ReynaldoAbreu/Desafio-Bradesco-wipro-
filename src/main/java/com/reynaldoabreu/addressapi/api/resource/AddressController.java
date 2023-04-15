package com.reynaldoabreu.addressapi.api.resource;

import com.reynaldoabreu.addressapi.api.dto.AddressDTO;
import com.reynaldoabreu.addressapi.api.exception.ApiErrors;
import com.reynaldoabreu.addressapi.api.service.AddressService;
import com.reynaldoabreu.addressapi.entity.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/v1/consulta-endereco")
public class AddressController {

    @Autowired
    private AddressService service;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO create(@RequestBody @Valid AddressDTO dto){

        String cep = dto.getCep();
        if (cep == null || cep.isEmpty()) {
            throw new IllegalArgumentException("CEP inválido");
        }

        Address entity = modelMapper.map(dto, Address.class);
        entity = service.save(entity);

        return modelMapper.map(entity, AddressDTO.class);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException ex){

        BindingResult bindingResult = ex.getBindingResult();
        return new ApiErrors(bindingResult);

    }

}
