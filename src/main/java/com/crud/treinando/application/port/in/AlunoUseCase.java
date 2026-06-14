package com.crud.treinando.application.port.in;

import java.util.List;

import com.crud.treinando.domain.Aluno;

public interface AlunoUseCase {

    Aluno insert(String nome, String matricula, Long idCurso);

    Aluno findById(Long id);

    List<Aluno> findAll();

    Aluno update(Long id, String nome, Long idCurso);

    void delete(Long id);
}
