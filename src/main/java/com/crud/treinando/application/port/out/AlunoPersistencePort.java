package com.crud.treinando.application.port.out;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crud.treinando.domain.Aluno;

public interface AlunoPersistencePort {
    Optional<Aluno> findById(Long id);

    Page<Aluno> findAll(Pageable pageable);

    Aluno save(Aluno aluno);

    void delete(Aluno aluno);

    boolean existsByMatricula(String matricula);
}
