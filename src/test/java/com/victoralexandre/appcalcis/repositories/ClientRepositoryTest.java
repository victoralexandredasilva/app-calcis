package com.victoralexandre.appcalcis.repositories;

import com.victoralexandre.appcalcis.model.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import jakarta.persistence.EntityManager;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class ClientRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    ClientRepository clientRepository;

    @Test
    @DisplayName("Deve pegar um cliente do BD com sucesso")
    void findClientByNameWithSuccess() {
        String name = "jEssiCA";
        String search = "j";
        this.createCLient(name.toUpperCase(), "45999999999", "jessica_test@gmail.com", "Rua das Flores 65 - Jardins");

        List<Client> clients = clientRepository.findClientByName(search.toUpperCase());

        clients.forEach(System.out :: println);

        assertThat(clients).isNotEmpty();
        assertThat(clients.get(0).getName()).isEqualTo(name);
    }

    private Client createCLient(String name, String phone, String email, String adress) {
        Client newClient = new Client(name, phone, email, adress);

        entityManager.persist(newClient);
        entityManager.flush();

        return newClient;
    }
}
