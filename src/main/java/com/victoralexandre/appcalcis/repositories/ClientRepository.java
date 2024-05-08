package com.victoralexandre.appcalcis.repositories;

import com.victoralexandre.appcalcis.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByActiveTrue();
    List<Client> findByActiveFalse();

    Client findFirstByName(String name);

    @Query("SELECT c FROM Client c WHERE c.name like %?1%")
    List<Client> findClientByName(String name);
}
