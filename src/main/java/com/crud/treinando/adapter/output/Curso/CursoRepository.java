package com.crud.treinando.adapter.output.Curso;

import com.crud.treinando.domain.Curso.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
