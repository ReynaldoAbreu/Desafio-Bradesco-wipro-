package com.reynaldoabreu.addressapi.api.service.imp;

import com.google.gson.Gson;
import com.reynaldoabreu.addressapi.api.exception.AddressNotFoundException;
import com.reynaldoabreu.addressapi.api.service.AddressService;
import com.reynaldoabreu.addressapi.entity.Address;
import com.reynaldoabreu.addressapi.model.repository.AddressExternApi;
import com.reynaldoabreu.addressapi.model.repository.Repository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Service
public class AddressServiceImp implements AddressService {

    private final Repository repository;

    public AddressServiceImp(Repository repository) {
        this.repository = repository;
    }

     public Address save(Address address) throws Exception {
        try {
            return addressSearch(address);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("Erro ao buscar endereço", ex);
        }
    }

    public Address addressSearch(Address address) throws Exception {
        System.out.println(address.getCep());
        URL url = new URL("https://viacep.com.br/ws/"+address.getCep()+"/json/");
        System.out.println(url);
        URLConnection connection = url.openConnection();
        try (InputStream is = connection.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            String cep = "";
            StringBuilder jsonCep = new StringBuilder();
            while ((cep = reader.readLine()) != null) {
                jsonCep.append(cep);
            }

            System.out.println(jsonCep);

            AddressExternApi addressExternApi = new Gson().fromJson(jsonCep.toString(), AddressExternApi.class);

            address.setCep(addressExternApi.getCep());
            address.setRua(addressExternApi.getLogradouro());
            address.setBairro(addressExternApi.getBairro());
            address.setComplemento(addressExternApi.getComplemento());
            address.setCidade(addressExternApi.getLocalidade());
            address.setEstado(addressExternApi.getUf());
            address.setFrete(addressExternApi.getFrete());

            return address;
        }
    }

}
