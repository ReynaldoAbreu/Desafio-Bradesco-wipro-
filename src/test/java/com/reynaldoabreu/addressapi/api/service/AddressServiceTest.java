package com.reynaldoabreu.addressapi.api.service;

import com.reynaldoabreu.addressapi.api.service.imp.AddressServiceImp;
import com.reynaldoabreu.addressapi.entity.Address;
import com.reynaldoabreu.addressapi.model.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AddressServiceTest {
    AddressService service;

    @MockBean
    Repository repository;

    @BeforeEach
    public void setUp(){
        this.service = new AddressServiceImp(repository);
    }

    @DisplayName("Deve salvar um endereço com sucesso")
    @Test
    public void saveAddressTest(){

        Address address = Address.builder()
                .cep("0000-0000")
                .rua("ouvidor")
                .complemento("lado-par")
                .bairro("centro")
                .cidade("São Paulo")
                .estado("SP")
                .frete(1.0)
                .build();

        Mockito.when( repository.save(address) ).thenReturn(Address.builder()
                .cep("0000-0000")
                .rua("ouvidor")
                .complemento("lado-par")
                .bairro("centro")
                .cidade("São Paulo")
                .estado("SP")
                .frete(1.0)
                .build());

        Address savedAddress = service.save(address);

        assertThat(savedAddress.getCep()).isEqualTo("0000-0000");
        assertThat(savedAddress.getRua()).isEqualTo("ouvidor");
        assertThat(savedAddress.getComplemento()).isEqualTo("lado-par");
        assertThat(savedAddress.getBairro()).isEqualTo("centro");
        assertThat(savedAddress.getCidade()).isEqualTo("São Paulo");
        assertThat(savedAddress.getEstado()).isEqualTo("SP");
        assertThat(savedAddress.getFrete()).isEqualTo(1.0);

    }


}
