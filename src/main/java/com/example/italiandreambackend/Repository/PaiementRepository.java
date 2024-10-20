package com.example.italiandreambackend.Repository;

import com.example.italiandreambackend.Entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement , Long> {

}
