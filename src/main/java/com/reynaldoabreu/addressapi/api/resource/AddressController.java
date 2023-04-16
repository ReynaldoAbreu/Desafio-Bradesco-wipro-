package com.reynaldoabreu.addressapi.api.resource;

import com.reynaldoabreu.addressapi.api.dto.AddressDTO;
import com.reynaldoabreu.addressapi.api.exception.AddressNotFoundException;
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

@RestController
@RequestMapping("/v1/consulta-endereco")
public class AddressController {

    @Autowired
    private AddressService service;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public String status(){

        return "ok";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public AddressDTO create(@RequestBody @Valid AddressDTO dto) throws Exception {
        if (dto.getCep() == null || dto.getCep().isEmpty()) {
            throw new AddressNotFoundException("CEP inválido");
        }

        Address entity = new Address();
        entity.setCep(dto.getCep());
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
