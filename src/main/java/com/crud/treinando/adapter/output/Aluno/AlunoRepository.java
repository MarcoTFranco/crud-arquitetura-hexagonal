package com.crud.treinando.adapter.output.aluno;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.treinando.application.port.out.AlunoPersistencePort;
import com.crud.treinando.domain.Aluno;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>, AlunoPersistencePort {

}
