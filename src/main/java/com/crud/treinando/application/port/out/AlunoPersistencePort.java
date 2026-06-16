package com.crud.treinando.application.port.out;

import java.util.List;
import java.util.Optional;

import com.crud.treinando.domain.Aluno;

public interface AlunoPersistencePort {
    Optional<Aluno> findById(Long id);

    List<Aluno> findAll();

    Aluno save(Aluno aluno);

    void delete(Aluno aluno);

    boolean existsByMatricula(String matricula);
}
