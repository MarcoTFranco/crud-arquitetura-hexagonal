package com.crud.treinando.adapter.output.Aluno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.treinando.domain.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
