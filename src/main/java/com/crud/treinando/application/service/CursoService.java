package com.crud.treinando.application.service;

import com.crud.treinando.adapter.output.curso.CursoRepository;
import com.crud.treinando.domain.Curso;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public void insert(Curso curso) {
        repository.save(curso);
    }

    public List<Curso> findAll() {
        return repository.findAll();
    }
}
