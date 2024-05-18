package com.victoralexandre.appcalcis.services;

import com.victoralexandre.appcalcis.model.Client;
import com.victoralexandre.appcalcis.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client findClientById(Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();

    }

    public List<Client> findClientsByActiveTrue() {
        return clientRepository.findByActiveTrue();
    }

    public List<Client> findClientsByActiveFalse() {
        return clientRepository.findByActiveFalse();
    }

    public Client findFirstByName(String name) {
        return clientRepository.findFirstByName(name);
    }

    public List<Client> findClientByName(String nameClient) {
        return clientRepository.findClientByName(nameClient);
    }

    public void createClient(String name, String phone, String email, String adress) {

        Client client = new Client();
        client.setName(name);
        client.setEmail(email);
        client.setPhoneNumber(phone);
        client.setAdress(adress);

        saveClient(client);
    }

    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    public void removeClient(Long id) {

        Client client = findClientById(id);

        client.setActive(false);

        saveClient(client);
    }

    public void editClient(String name, String phone, String email, String adress, Long id) {
        Client client = findClientById(id);

        client.setId(id);
        client.setName(name);
        client.setPhoneNumber(phone);
        client.setEmail(email);
        client.setAdress(adress);

        saveClient(client)  ;
    }
}
