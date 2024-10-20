package com.example.italiandreambackend.Repository;

import com.example.italiandreambackend.Entity.DocumentsBourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface DocumentsBourseRepository  extends JpaRepository<DocumentsBourse, Integer > {
}
