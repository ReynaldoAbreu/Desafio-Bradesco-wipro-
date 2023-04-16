package com.reynaldoabreu.addressapi.api.service.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reynaldoabreu.addressapi.api.dto.AddressDTO;
import com.reynaldoabreu.addressapi.api.service.AddressService;
import com.reynaldoabreu.addressapi.entity.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class AddressControllerTest {

    static String ADDRESS_API = "/v1/consulta-endereco";

    @Autowired
    MockMvc mvc;

    @MockBean
    AddressService service;


    @DisplayName("Deve Criar um endereço com sucesso")
    @Test
    public void createAddressTest() throws Exception {

        Address savedAddress = Address.builder()
                .cep("0000-0000")
                .rua("ouvidor")
                .complemento("lado-par")
                .bairro("centro")
                .cidade("São Paulo")
                .estado("SP")
                .frete(1.0)
                .build();

        AddressDTO dto = AddressDTO.builder()
                .cep(savedAddress.getCep())
                .rua(savedAddress.getRua())
                .bairro(savedAddress.getBairro())
                .complemento(savedAddress.getComplemento())
                .cidade(savedAddress.getCidade())
                .estado(savedAddress.getEstado())
                .frete(savedAddress.getFrete())
                .build();

        BDDMockito.given(service.save(Mockito.any(Address.class))).willReturn(savedAddress);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ADDRESS_API)
                .contentType( MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("cep").value(dto.getCep()))
                .andExpect(jsonPath("rua").value(dto.getRua()))
                .andExpect(jsonPath("complemento").value(dto.getComplemento()))
                .andExpect(jsonPath("bairro").value(dto.getBairro()))
                .andExpect(jsonPath("cidade").value(dto.getCidade()))
                .andExpect(jsonPath("estado").value(dto.getEstado()))
                .andExpect(jsonPath("frete").value(dto.getFrete()));

    }

    @DisplayName("Deve lançar erro de validação se o cep não for valido")
    @Test
    public void saveInvalidAddressTest() throws Exception{

        AddressDTO dto = new AddressDTO();

        String json = new ObjectMapper().writeValueAsString(dto.getCep());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(ADDRESS_API)
                .contentType( MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest());

    }

}
