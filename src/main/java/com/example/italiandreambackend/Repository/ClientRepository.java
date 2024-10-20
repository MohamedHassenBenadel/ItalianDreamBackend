package com.example.italiandreambackend.Repository;

import com.example.italiandreambackend.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client,String> {
    Optional<Client> findClientByEmail(String email);
    Optional<Client> findByCin(Integer cin);
    Optional<Client> findByEmail(String email);

}
