package com.crud.treinando.application.Curso;

import com.crud.treinando.adapter.output.Curso.CursoRepository;
import com.crud.treinando.domain.Curso.Curso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repository;

    public void insert(Curso curso) {
        repository.save(curso);
    }

    public List<Curso> findAll() {
        return repository.findAll();
    }
}
