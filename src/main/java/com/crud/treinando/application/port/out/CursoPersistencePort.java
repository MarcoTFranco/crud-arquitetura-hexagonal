package com.crud.treinando.application.port.out;

import java.util.List;
import java.util.Optional;

import com.crud.treinando.domain.Curso;

public interface CursoPersistencePort {
    List<Curso> findAll();

    Optional<Curso> findById(Long id);

    Curso save(Curso curso);
}
