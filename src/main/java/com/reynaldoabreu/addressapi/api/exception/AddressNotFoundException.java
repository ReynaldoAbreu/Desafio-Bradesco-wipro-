package com.reynaldoabreu.addressapi.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AddressNotFoundException extends RuntimeException  {
    public AddressNotFoundException(String messageError) {
        super(messageError);
    }
}
