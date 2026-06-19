package com.crud.treinando.application.port.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crud.treinando.domain.Curso;

public interface CursoPersistencePort {
    Page<Curso> findAll(Pageable pageable);

    Optional<Curso> findById(Long id);

    Curso save(Curso curso);
}
