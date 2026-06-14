package com.crud.treinando.adapter.output.curso;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.crud.treinando.domain.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
