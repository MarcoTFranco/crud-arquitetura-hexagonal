package com.crud.treinando.application.port.in;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.crud.treinando.domain.Aluno;

public interface AlunoUseCase {

    Aluno insert(String nome, String matricula, Long idCurso);

    Aluno findById(Long id);

    Page<Aluno> findAll(Pageable pageable);

    Aluno update(Long id, String nome, Long idCurso);

    void delete(Long id);
}
