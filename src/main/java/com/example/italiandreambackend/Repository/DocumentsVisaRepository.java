package com.example.italiandreambackend.Repository;

import com.example.italiandreambackend.Entity.DocumentsUni;
import com.example.italiandreambackend.Entity.DocumentsVisa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsVisaRepository extends JpaRepository<DocumentsVisa, Integer > {
}
