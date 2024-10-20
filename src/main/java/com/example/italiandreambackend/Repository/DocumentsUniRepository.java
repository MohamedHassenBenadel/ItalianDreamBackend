package com.example.italiandreambackend.Repository;

import com.example.italiandreambackend.Entity.DocumentsBourse;
import com.example.italiandreambackend.Entity.DocumentsUni;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentsUniRepository extends JpaRepository<DocumentsUni, Integer > {
}
