package main.repository;

import main.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findAuthorByNameOrId(String name, Long id);
}
